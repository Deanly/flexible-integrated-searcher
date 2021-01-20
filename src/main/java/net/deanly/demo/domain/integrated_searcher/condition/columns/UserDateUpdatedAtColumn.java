package net.deanly.demo.domain.integrated_searcher.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.core.values.SearchPeriod;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;

/**
 * [Date]
 * Updated At
 * - storage
 *  - JPA(mysql): `updated_at`
 *  - Elasticsearch: `updated_at`
 */
@Data
@ToString
public class UserDateUpdatedAtColumn implements ISearchColumn<SearchPeriod> {

    public SearchPeriod val;

    public UserDateUpdatedAtColumn(SearchPeriod value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_UPDATED_AT;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        return "updated_at";
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
