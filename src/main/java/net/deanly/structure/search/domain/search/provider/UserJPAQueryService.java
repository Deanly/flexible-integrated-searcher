package net.deanly.structure.search.domain.search.provider;

import net.deanly.structure.search.domain.search.core.sub.QuerySupporter;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.core.answer.SearchAnswerItem;
import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.core.sub.IQueryService;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;
import net.deanly.structure.search.infrastructure.jpa.entity.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserJPAQueryService extends QuerySupporter implements IQueryService<Long> {
    @Override
    public QueryServiceType type() {
        return QueryServiceType.DB_USERS;
    }

    @Override
    public Page<SearchAnswerItem<Long>> search(ISearchColumn<?>[] columns, Pageable pageable, ColumnType[] references) {
        return null;
    }

    @Override
    public ColumnType identifier() {
        return ColumnType.USER_ID;
    }

    @Override
    public ColumnType[] references() {
        return new ColumnType[] { ColumnType.USER_CREATED_AT, ColumnType.USER_UPDATED_AT };
    }

    /**
     * 질의문 생성
     * @param columns 검색 조건
     * @return JPA 질의문
     */
    private Specification<UserDTO> toPredicate(ISearchColumn<?>[] columns) {
        return (entity, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> andPredicates = new ArrayList<>();
            List<Predicate> orPredicates = new ArrayList<>(); // OR 기능 생략.

            for (ISearchColumn<?> column : columns) {
                switch (column.type()) {
                    case USER_ID:
                        this.patternList(entity, column, andPredicates);
                        break;
                    case USER_FIRST_NAME:

                }
            }

            return andPredicates.stream().reduce(criteriaBuilder::and).orElseGet(criteriaBuilder::and);
        };
    }

    private void patternList(Root<UserDTO> entity, ISearchColumn<?> column, List<Predicate> buffer) {
        List<?> list = this.columnToList(column);
        if (Objects.nonNull(list)) {
            buffer.add(entity.get(column.rawKey(this.type())).in(list));
        }
    }

}
