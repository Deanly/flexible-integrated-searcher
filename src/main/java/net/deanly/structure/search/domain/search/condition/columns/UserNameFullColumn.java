package net.deanly.structure.search.domain.search.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;

/**
 * User Name
 * - storage
 *  - Elasticsearch: `name`
 *  - JPA(mysql): concat(`first_name`, " ", `last_name`)
 */
@Data
@ToString
public class UserNameFullColumn implements ISearchColumn<String> {

    public String val;

    public UserNameFullColumn(String value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_FULL_NAME;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        switch (queryServiceType) {
            case ES_USERS:
                return "name";
            case DB_USERS:
                return "first_name, ,last_name";
            default:
                return null;
        }
    }

    @Override
    public String rawValue() {
        return this.val;
    }

    @Override
    public QueryServiceType[] readableServices() {
        return new QueryServiceType[] { QueryServiceType.ES_USERS, QueryServiceType.DB_USERS };
    }
}
