package net.deanly.demo.domain.integrated_searcher.condition.columns;

import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;

/**
 * [Check]
 */
public class UserCheckIsMemberColumn implements ISearchColumn<Boolean> {

    public Boolean val;

    public UserCheckIsMemberColumn(Boolean value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_IS_MEMBER;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        return "is_member";
    }

    @Override
    public Boolean rawValue() {
        return this.val;
    }

    @Override
    public QueryServiceType[] readableServices() {
        return new QueryServiceType[] { QueryServiceType.API_USERS };
    }
}
