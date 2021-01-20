package net.deanly.structure.search.domain.search.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.core.values.SearchPeriod;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;

/**
 * Updated At
 * - storage
 *  - JPA(mysql): `updated_at`
 *  - Elasticsearch: `updated_at`
 */
@Data
@ToString
public class UserUpdatedAtColumn implements ISearchColumn<SearchPeriod> {

    public SearchPeriod val;

    public UserUpdatedAtColumn(SearchPeriod value) {
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
