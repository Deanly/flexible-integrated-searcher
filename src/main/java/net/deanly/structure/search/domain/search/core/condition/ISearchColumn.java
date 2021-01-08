package net.deanly.structure.search.domain.search.core.condition;

import net.deanly.structure.search.domain.search.condition.ColumnType;
import net.deanly.structure.search.domain.search.provider.QueryServiceType;

public interface ISearchColumn<T> {

    /**
     * @return ColumnType representing column.
     */
    ColumnType type();

    /**
     * @return Value used for repository lookup.
     */
    T rawValue();

    /**
     * @return Array of repository types that can be queries by this column.
     */
    QueryServiceType[] readableServices();

}
