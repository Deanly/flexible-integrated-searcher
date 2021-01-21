package net.deanly.demo.domain.integrated_searcher.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.core.values.SearchPeriod;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;

/**
 * [Date]
 * User 생성일
 * - Storage
 *  - JPA(mysql): `created_at`
 *  - Elasticsearch: `created_at`
 */
@Data
@ToString
public class UserDateCreatedAtColumn implements ISearchColumn<SearchPeriod> {

    public SearchPeriod val;

    public UserDateCreatedAtColumn() {
        this.val = new SearchPeriod("2018-01-01", null);
    }
    public UserDateCreatedAtColumn(SearchPeriod value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_CREATED_AT;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        switch (queryServiceType) {
            case DB_USERS:
                return "createdAt";
            case ES_USERS:
                return "created_at";
            default:
                return null;
        }
    }

    @Override
    public SearchPeriod rawValue() {
        return this.val;
    }

    @Override
    public QueryServiceType[] readableServices() {
        return new QueryServiceType[] { QueryServiceType.ES_USERS, QueryServiceType.DB_USERS };
    }
}
