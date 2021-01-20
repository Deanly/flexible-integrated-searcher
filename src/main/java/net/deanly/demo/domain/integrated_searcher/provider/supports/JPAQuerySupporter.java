package net.deanly.demo.domain.integrated_searcher.provider.supports;

import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;
import net.deanly.demo.infrastructure.jpa.entity.UserDTO;
import net.deanly.demo.domain.integrated_searcher.core.values.SearchPeriod;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class JPAQuerySupporter {

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
    protected void patternList(Root<UserDTO> entity, ISearchColumn<?> column, List<Predicate> buffer) {
        List<?> list = this.columnRawValueToList(column);
        if (Objects.nonNull(list)) {
            buffer.add(entity.get(column.rawKey(this.type())).in(list));
        }
    }

    /**
     * column 의 값이 단일 Type 일 경우.
     */
    protected void patternSingleEqual(Root<UserDTO> entity, CriteriaBuilder criteriaBuilder, ISearchColumn<?> column, List<Predicate> buffer) {
        buffer.add(criteriaBuilder.equal(entity.get(column.rawKey(this.type())), column.rawValue()));
    }

    /**
     * column 의 값이 SearchPeriod 일 경우.
     */
    protected void patternSearchPeriod(Root<UserDTO> entity, CriteriaBuilder criteriaBuilder, ISearchColumn<?> column, List<Predicate> buffer) {
        SearchPeriod sp = (SearchPeriod) column.rawValue();
        if (Objects.isNull(sp.getEndDate())) {
            buffer.add(criteriaBuilder.greaterThanOrEqualTo(entity.get(column.rawKey(this.type())), sp.getStartDate()));
        } else if (Objects.isNull(sp.getStartDate())) {
            buffer.add(criteriaBuilder.lessThanOrEqualTo(entity.get(column.rawKey(this.type())), sp.getEndDate()));
        } else {
            buffer.add(criteriaBuilder.between(entity.get(column.rawKey(this.type())), sp.getStartDate(), sp.getEndDate()));
        }
    }

}
