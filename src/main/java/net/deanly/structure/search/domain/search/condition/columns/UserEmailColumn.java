package net.deanly.structure.search.domain.search.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;

/**
 * Email
 * - Storage
 *  - JPA(mysql): `email`
 */
@Data
@ToString
public class UserEmailColumn implements ISearchColumn<String> {

    public String val;

    public UserEmailColumn(String value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_EMAIL;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        return "email";
    }

    @Override
    public String rawValue() {
        return this.val;
    }

    @Override
    public QueryServiceType[] readableServices() {
        return new QueryServiceType[] { QueryServiceType.DB_USERS };
    }
}
