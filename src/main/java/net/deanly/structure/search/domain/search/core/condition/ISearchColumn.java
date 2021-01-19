package net.deanly.structure.search.domain.search.core.condition;

import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;

public interface ISearchColumn<T> {

    /**
     * @return ColumnType representing column.
     */
    ColumnType type();

    /**
     * @return Value of key name stored in the Repository.
     */
    String rawKey(QueryServiceType queryServiceType);

    /**
     * @return Value used for repository lookup.
     */
    T rawValue();

    /**
     * @return Array of repository types that can be queries by this column.
     */
    QueryServiceType[] readableServices();

}
