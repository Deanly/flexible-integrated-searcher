package net.deanly.demo.domain.integrated_searcher.condition.columns;

import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;

/**
 * [Text]
 * User Last Name
 * - storage
 *  - JPA(mysql): `last_name`
 *  - Elasticsearch: `name`
 */
public class UserTextNameLastColumn implements ISearchColumn<String> {

    public String val;

    public UserTextNameLastColumn(String value) {
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
                return "lastName";
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
