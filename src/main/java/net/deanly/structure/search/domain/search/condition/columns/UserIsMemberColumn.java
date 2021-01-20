package net.deanly.structure.search.domain.search.condition.columns;

import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;

public class UserIsMemberColumn implements ISearchColumn<Boolean> {

    public Boolean val;

    public UserIsMemberColumn(Boolean value) {
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
