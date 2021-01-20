package net.deanly.demo.domain.integrated_searcher.core.sub;

import lombok.AllArgsConstructor;
import net.deanly.demo.domain.integrated_searcher.core.answer.SearchAnswerItem;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

@AllArgsConstructor
public abstract class QueryCoordinator {

    private final ISearchColumn<?>[] conditions;

    private final List<ColumnType> references;

    private final Pageable pageable;

    public abstract QueryServiceType getQueryServiceType();


    public ISearchColumn<?>[] getConditions() {
        return this.conditions;
    }

    public ColumnType[] getReferences() {
        return this.references.toArray(ColumnType[]::new);
    }

    public Pageable getPageable() {
        return this.pageable;
    }

    public void applyReferences(IQueryService<?> queryService) {
        Set<ColumnType> sets = new HashSet<>(this.references);
        Collections.addAll(sets, queryService.references());
        this.references.clear();
        this.references.addAll(sets);
    }

    /**
     * To support method `query`.
     */
    public <T> Page<SearchAnswerItem<T>> queryToSupportWithPage(IQueryService<T> queryService) {
        return queryService.search(this.conditions, this.pageable, this.getReferences());
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("QueryCoordinator(");
        sb.append("infraType=");
        sb.append(this.getQueryServiceType());
        sb.append(", condition=");
        if (Objects.isNull(this.conditions)) {
            sb.append("null");
        } else {
            sb.append("[");
            for (int i = 0; i < this.conditions.length; i++) {
                sb.append("{type=");
                sb.append(this.conditions[i].type());
                sb.append(", value=");
                sb.append(this.conditions[i].rawValue());
                if (i == this.conditions.length - 1) {
                    sb.append("}");
                } else {
                    sb.append("}, ");
                }
            }
            sb.append("]");
        }
        sb.append(", references=");
        sb.append(Arrays.toString(this.references.toArray()));
        sb.append(")");
        return sb.toString();
    }
}

