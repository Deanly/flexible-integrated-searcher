package net.deanly.structure.search.domain.search.condition.columns;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.core.values.SearchPeriod;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;

/**
 * User 생성일
 * - Storage
 *  - JPA(mysql): `created_at`
 *  - Elasticsearch: `created_at`
 */
@Data
@ToString
public class UserCreatedAtColumn implements ISearchColumn<SearchPeriod> {

    public SearchPeriod val;

    public UserCreatedAtColumn() {
        this.val = new SearchPeriod("2018-01-01", null);
    }
    public UserCreatedAtColumn(SearchPeriod value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_CREATED_AT;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        return "created_at";
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
