package net.deanly.structure.search.domain.search.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;

/**
 * IP Address
 * - storage
 *  - JPA(mysql): `ip_address`
 */
@Data
@ToString
public class UserIpAddressColumn implements ISearchColumn<String> {

    public String val;

    public UserIpAddressColumn(String value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_IP_ADDRESS;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        switch (queryServiceType) {
            case DB_USERS:
                return "ip_address";
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
        return new QueryServiceType[] { QueryServiceType.DB_USERS };
    }
}
