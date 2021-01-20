package net.deanly.structure.search.domain.search.condition.columns;

import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;

/**
 * User Last Name
 * - storage
 *  - JPA(mysql): `last_name`
 *  - Elasticsearch: `name`
 */
public class UserNameLastColumn implements ISearchColumn<String> {

    public String val;

    public UserNameLastColumn(String value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_LAST_NAME;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        switch (queryServiceType) {
            case DB_USERS:
                return "last_name";
            case ES_USERS:
                return "name";
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
        return new QueryServiceType[] { QueryServiceType.ES_USERS, QueryServiceType.DB_USERS};
    }
}
