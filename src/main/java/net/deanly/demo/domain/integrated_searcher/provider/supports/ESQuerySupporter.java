package net.deanly.demo.domain.integrated_searcher.provider.supports;

import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.core.values.SearchPeriod;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class ESQuerySupporter {

    protected abstract QueryServiceType type();

    /**
     * Support Method
     * Collection List 변환
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> columnRawValueToList(ISearchColumn<?> t) {
        if (Objects.isNull(t.rawValue())) return null;
        if (t.rawValue().getClass().getTypeName().contains("SingletonList"))
            return (List<T>) t.rawValue();
        for (Class<?> anInterface : t.rawValue().getClass().getInterfaces()) {
            if (anInterface.equals(List.class)) {
                List<T> r = (List<T>)t.rawValue();
                if (r.size() == 0) return null;
                return r;
            }
        }
        return Collections.singletonList((T) t.rawValue());
    }


    /// 컬럼 값 형태별 Predicate 생성 패턴

    /**
     * column 의 값이 List Type 일 경우.
     */
    protected void patternList(BoolQueryBuilder boolQueryBuilder, ISearchColumn<?> column) {
        List<?> list = this.columnRawValueToList(column);
        if (Objects.nonNull(list)) {
            BoolQueryBuilder operatorStack = QueryBuilders.boolQuery();
            for (Object o : Objects.requireNonNull(list)) {
                operatorStack.should(QueryBuilders.matchQuery(column.rawKey(this.type()), o));
            }
            boolQueryBuilder.must(operatorStack);
        }
    }

    /**
     * column 의 값이 단일 Type 일 경우.
     */
    protected void patternSingleEqual(BoolQueryBuilder boolQueryBuilder, ISearchColumn<?> column) {
        boolQueryBuilder.must(QueryBuilders.matchQuery(column.rawKey(this.type()), column.rawValue()));
    }

    /**
     * column 의 값이 SearchPeriod 일 경우.
     */
    protected void patternSearchPeriod(BoolQueryBuilder boolQueryBuilder, ISearchColumn<?> column) {
        SearchPeriod sp = (SearchPeriod) column.rawValue();
        if (Objects.isNull(sp.getEndDate())) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery(column.rawKey(this.type())).gte(sp.getStartDate()));
        } else if (Objects.isNull(sp.getStartDate())) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery(column.rawKey(this.type())).lte(sp.getEndDate()));
        } else {
            boolQueryBuilder.must(QueryBuilders.rangeQuery(column.rawKey(this.type())).gte(sp.getStartDate()).lte(sp.getEndDate()));
        }
    }
}
