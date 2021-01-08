package net.deanly.structure.search.domain.search.core.sub;

import net.deanly.structure.search.domain.search.provider.QueryServiceType;

import java.lang.reflect.Type;

public interface IQueryServiceProvider {

    /**
     *
     * @param identifierType Search result type. Generic type defined in the core domain.
     * @param queryServiceType
     * @return
     */
    IQueryService<?> access(Type identifierType, QueryServiceType queryServiceType);
}
