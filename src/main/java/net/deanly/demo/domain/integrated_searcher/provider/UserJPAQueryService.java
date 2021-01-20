package net.deanly.demo.domain.integrated_searcher.provider;

import com.google.common.base.Strings;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.core.sub.IQueryService;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;
import net.deanly.demo.domain.integrated_searcher.provider.supports.JPAQuerySupporter;
import net.deanly.demo.infrastructure.jpa.UsersRepository;
import net.deanly.demo.infrastructure.jpa.entity.UserDTO;
import net.deanly.demo.domain.integrated_searcher.core.answer.SearchAnswerItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;

public class UserJPAQueryService extends JPAQuerySupporter implements IQueryService<Long> {

    private final UsersRepository repository;

    public UserJPAQueryService(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public QueryServiceType type() {
        return QueryServiceType.DB_USERS;
    }

    @Override
    public Page<SearchAnswerItem<Long>> search(ISearchColumn<?>[] columns, Pageable pageable, ColumnType[] references) {
        return this.repository.findAll(this.toPredicate(columns), pageable).map(t -> this.convert(t, references));
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
                    case USER_PAYMENT_CURRENCY:
                        this.patternList(entity, column, andPredicates);
                        break;
                    case USER_FULL_NAME:
                        this.patternConcatString(entity, criteriaBuilder, column, andPredicates);
                        break;
                    case USER_IS_MEMBER:
                        // No supported
                        break;
                    case USER_FIRST_NAME:
                    case USER_LAST_NAME:
                    case USER_EMAIL:
                    case USER_CAR_MODEL:
                    case USER_COMPANY_NAME:
                        andPredicates.add(criteriaBuilder.like(entity.get(column.rawKey(this.type())), "%" + column.rawValue() + "%"));
                        break;
                    case USER_GENDER:
                    case USER_IP_ADDRESS:
                    case USER_MARRIED:
                        this.patternSingleEqual(entity, criteriaBuilder, column, andPredicates);
                        break;
                    case USER_BIRTHDAY:
                    case USER_CREATED_AT:
                    case USER_UPDATED_AT:
                        this.patternSearchPeriod(entity, criteriaBuilder, column, andPredicates);
                        break;
                    default:
                        break;
                }
            }

            // OR 기능 생략.
            // andPredicates.add(orPredicates.stream().reduce(criteriaBuilder::or).orElseGet(criteriaBuilder::or));
            return andPredicates.stream().reduce(criteriaBuilder::and).orElseGet(criteriaBuilder::and);
        };
    }


    /**
     * column 을 커스터마이징 하여 사용됨.
     * rawKey() -> "A,B,C".split(",") -> ["A","B","C"]
     * `WHERE CONCAT({...rawKey}) LIKE '%{rawValue}%'`
     */
    private void patternConcatString(Root<UserDTO> entity, CriteriaBuilder criteriaBuilder, ISearchColumn<?> column, List<Predicate> buffer) {
        String[] rawKeys = column.rawKey(this.type()).split(",");
        if (rawKeys.length == 1) {
            patternSingleEqual(entity, criteriaBuilder, column, buffer);
            return;
        }

        Expression<String> exp1 = null;
        String val = null;

        for (String key : rawKeys) {
            Expression<String> exp2 = null;
            try {
                if (Objects.isNull(exp1)) {
                    exp1 = entity.get(key);
                    continue;
                } else {
                    exp2 = entity.get(key);
                }
            } catch (IllegalArgumentException e) {
                if (!Strings.isNullOrEmpty(val))
                    throw e;
                val = key;
                if (Objects.isNull(exp1))
                    continue;
            }

            if (Strings.isNullOrEmpty(val)) {
                exp1 = criteriaBuilder.concat(exp1, exp2);
            } else {
                exp1 = criteriaBuilder.concat(exp1, val);
                val = null;
            }
        }

        buffer.add(criteriaBuilder.like(exp1, "%" + column.rawValue() + "%"));
    }


    /**
     * convert method
     *  (UserDTO -> SearchAnswerItem)
     * @return 검색 결과 모델
     */
    private SearchAnswerItem<Long> convert(UserDTO entity, ColumnType[] references) {
        Map<ColumnType, Object> ref = new HashMap<>();

        for (ColumnType refType : references) {
            switch (refType) {
                case USER_CREATED_AT:
                    ref.put(ColumnType.USER_CREATED_AT, entity.getCreatedAt());
                    break;
                case USER_UPDATED_AT:
                    ref.put(ColumnType.USER_UPDATED_AT, entity.getUpdatedAt());
                    break;
                default:
                    break;
            }
        }

        return new SearchAnswerItem<>(entity.getId(), ColumnType.USER_ID, ref);
    }

}
