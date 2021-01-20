package net.deanly.demo.domain.integrated_searcher.core.sub;

import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;

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
