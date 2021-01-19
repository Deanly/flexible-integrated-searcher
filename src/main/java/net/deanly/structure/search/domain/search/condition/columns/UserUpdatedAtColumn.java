package net.deanly.structure.search.domain.search.condition.columns;

import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.core.values.SearchPeriod;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;

public class UserUpdatedAtColumn implements ISearchColumn<SearchPeriod> {
    @Override
    public ColumnType type() {
        return null;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        return null;
    }

    @Override
    public SearchPeriod rawValue() {
        return null;
    }

    @Override
    public QueryServiceType[] readableServices() {
        return new QueryServiceType[0];
    }
}
