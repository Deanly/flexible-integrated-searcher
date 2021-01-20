package net.deanly.structure.search.domain.search.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;


/**
 * 결혼여부
 * - storage
 *  - JPA(mysql): `married`
 *  - Elasticsearch: `married`
 */
@Data
@ToString
public class UserMarriedColumn implements ISearchColumn<Boolean> {

    public Boolean val;

    public UserMarriedColumn(Boolean value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_MARRIED;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        return "married";
    }

    @Override
    public Boolean rawValue() {
        return this.val;
    }

    @Override
    public QueryServiceType[] readableServices() {
        return new QueryServiceType[] { QueryServiceType.ES_USERS, QueryServiceType.DB_USERS };
    }
}
