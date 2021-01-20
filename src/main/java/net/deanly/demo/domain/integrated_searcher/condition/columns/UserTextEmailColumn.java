package net.deanly.demo.domain.integrated_searcher.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;

/**
 * [Text]
 * Email
 * - Storage
 *  - JPA(mysql): `email`
 */
@Data
@ToString
public class UserTextEmailColumn implements ISearchColumn<String> {

    public String val;

    public UserTextEmailColumn(String value) {
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
