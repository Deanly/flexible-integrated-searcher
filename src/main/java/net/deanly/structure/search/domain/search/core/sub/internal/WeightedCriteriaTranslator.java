package net.deanly.structure.search.domain.search.core.sub.internal;

import net.deanly.structure.search.domain.search.core.sub.ISearchPolicy;
import net.deanly.structure.search.domain.search.core.sub.QueryCoordinator;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.core.condition.ColumnTypeGroup;
import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.core.condition.ISearchCondition;
import net.deanly.structure.search.domain.search.core.exception.InvalidConditionException;
import net.deanly.structure.search.domain.search.core.exception.NoQueryServiceException;
import net.deanly.structure.search.domain.search.core.values.PriorityQueryByColumn;
import net.deanly.structure.search.domain.search.core.values.PriorityQueryType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

public final class WeightedCriteriaTranslator {

    public QueryCoordinator translate(ISearchPolicy policy, ISearchCondition condition)
            throws InvalidConditionException, NoQueryServiceException {

        this.validate(condition);

        QueryServiceType target = this.makeTargetQueryService(policy, condition);

        // validate - standard policy
        if (Objects.isNull(target)) throw new NoQueryServiceException("There is no searchable Query Service.\n" + condition);
        Pageable pageable = condition.getPageable();
        if (Objects.isNull(pageable)) pageable = this.defaultPageable();

        return new QueryCoordinator(condition.columns(), new LinkedList<>(), pageable) {
            @Override
            public QueryServiceType getQueryServiceType() {
                return target;
            }
        };
    }

    private QueryServiceType makeTargetQueryService(ISearchPolicy policy, ISearchCondition condition)
            throws NoQueryServiceException {

        QueryServiceType target = null;
        Map<QueryServiceType, Integer> weights = new HashMap<>();
        Set<QueryServiceType> avoidSets = new HashSet<>();
        Set<QueryServiceType> alwaysSets = new HashSet<>();
        Set<QueryServiceType> preferSets = new HashSet<>();

        PriorityQueryByColumn[] priorityQueryByColumns = policy.prioritiesQueryByColumn();
        if (Objects.isNull(priorityQueryByColumns)) priorityQueryByColumns = new PriorityQueryByColumn[0];
        for (PriorityQueryByColumn p: priorityQueryByColumns) {
            if (Objects.isNull(p.getColumnType())) {
                if (p.getPriorityQueryType() == PriorityQueryType.ALWAYS)
                    alwaysSets.add(p.getQueryServiceType());
                else if (p.getPriorityQueryType() == PriorityQueryType.AVOID)
                    avoidSets.add(p.getQueryServiceType());
                else if (p.getPriorityQueryType() == PriorityQueryType.PREFER)
                    preferSets.add(p.getQueryServiceType());
            }
        }

        ISearchColumn<?>[] allConditions = new LinkedList<>(Arrays.asList(condition.columns())).stream().filter(c -> !Objects.isNull(c.rawValue())).toArray(ISearchColumn[]::new);
        PriorityQueryByColumn[] avoidPriorArray = Arrays.stream(priorityQueryByColumns).filter(p -> p.getPriorityQueryType().equals(PriorityQueryType.AVOID)).toArray(PriorityQueryByColumn[]::new);
        PriorityQueryByColumn[] alwaysPriorArray = Arrays.stream(priorityQueryByColumns).filter(p -> p.getPriorityQueryType().equals(PriorityQueryType.ALWAYS)).toArray(PriorityQueryByColumn[]::new);
        PriorityQueryByColumn[] preferPriorArray = Arrays.stream(priorityQueryByColumns).filter(p -> p.getPriorityQueryType().equals(PriorityQueryType.PREFER)).toArray(PriorityQueryByColumn[]::new);

        for (ISearchColumn<?> column : allConditions) {

            for (PriorityQueryByColumn avoid : avoidPriorArray) {
                if (column.type() == avoid.getColumnType()) {
                    avoidSets.add(avoid.getQueryServiceType());
                }
            }

            for (PriorityQueryByColumn always: alwaysPriorArray) {
                if (column.type() == always.getColumnType()) {
                    alwaysSets.add(always.getQueryServiceType());
                }
            }

            for (PriorityQueryByColumn prefer: preferPriorArray) {
                if (column.type() == prefer.getColumnType()) {
                    preferSets.add(prefer.getQueryServiceType());
                }
            }

            // 언급되는 대로 바로 가중치 가산.
            this.supplyToWeightsAddAsItSays(weights, column);
        }

        // 지원하지 않는 인프라 삭제
        this.cleanUnsupportedInfraType(weights, allConditions);

        if (weights.size() == 0) throw new NoQueryServiceException("There is no searchable Query Service." +
                "\n\tWeights:" + weights +
                "\n\tTarget: null\n\t" +
                "\n\tCondition: " + condition);

        // Always
        if (alwaysSets.size() > 0) {
            target = this.decisionSearchInfraType(alwaysSets, new HashSet<>(), weights);
            if (Objects.isNull(target))
                throw new NoQueryServiceException("There is no searchable Query Service." +
                        "\n\tAlways-Prior: " + Arrays.toString(alwaysPriorArray) +
                        "\n\tWeights:" + weights +
                        "\n\tTarget: null" +
                        "\n\tCondition: " + condition);
            else return target;
        }

        // Prefer
        if (preferSets.size() > 0)
            target = this.decisionSearchInfraType(preferSets, avoidSets, weights);

        // default
        if (Objects.isNull(target))
            target = this.decisionSearchInfraType(weights.keySet(), avoidSets, weights);

        if (Objects.isNull(target))
            throw new NoQueryServiceException("There is no searchable Query Service." +
                    "\n\tAvoid-Prior: " + Arrays.toString(avoidPriorArray) +
                    "\n\tAlways-Prior: " + Arrays.toString(alwaysPriorArray) +
                    "\n\tPrefer-Prior: " + Arrays.toString(preferPriorArray) +
                    "\n\tWeights:" + weights +
                    "\n\tTarget: null" +
                    "\n\tCondition: " + condition);

        return target;
    }

    public void validate(ISearchCondition conditions) throws InvalidConditionException {
        ColumnTypeGroup[] requiredGroups = conditions.requiredColumnGrouping();
        ISearchColumn<?>[] columns = conditions.columns();

        for (ColumnTypeGroup requiredGroup : requiredGroups) {
            boolean ok = false;
            for (ColumnType requiredType : requiredGroup.getTypes()) {
                for (ISearchColumn<?> column : columns) {
                    if (requiredType.equals(column.type())) {
                        ok = true;
                        break;
                    }
                }
                if (ok) break; // group ok
            }
            if (!ok) {
                throw new InvalidConditionException("condition does not satisfy the required value." + requiredGroup);
            }
        }
    }

    private void supplyToWeightsAddAsItSays(Map<QueryServiceType, Integer> weights, ISearchColumn<?> column) {
        QueryServiceType[] queryServiceTypes = column.readableServices();
        for (int i = 0; i < queryServiceTypes.length; i++) {

            if (!weights.containsKey(queryServiceTypes[i]))
                weights.put(queryServiceTypes[i], 0);

            if (10 - i < 1)
                break;

            weights.put(queryServiceTypes[i], weights.get(queryServiceTypes[i]) + (10 - i));
        }
    }

    private void cleanUnsupportedInfraType(Map<QueryServiceType, Integer> weights, ISearchColumn<?>[] allConditions) {
        for (QueryServiceType infraType: (new LinkedHashMap<>(weights)).keySet()) {
            int integrity = 0;
            for (ISearchColumn<?> column: allConditions) {
                for (QueryServiceType queryServiceType : column.readableServices()) {
                    if (queryServiceType == infraType) {
                        integrity += 1;
                        break;
                    }
                }
            }
            if (integrity != allConditions.length) {
                weights.remove(infraType);
            }
        }
    }

    private QueryServiceType decisionSearchInfraType(Set<QueryServiceType> hits, Set<QueryServiceType> avoid, Map<QueryServiceType, Integer> weightsMap) {
        QueryServiceType target = null;
        Integer w = 0;
        for (QueryServiceType hit : hits) {
            if (avoid.contains(hit)) continue;
            if (weightsMap.containsKey(hit) && weightsMap.get(hit) > w) {
                target = hit;
                w = weightsMap.get(hit);
            }
        }
        return target;
    }

    private Pageable defaultPageable() {
        return PageRequest.of(0, 20);
    }
}
