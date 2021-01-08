package net.deanly.structure.search.domain.search.core.sub;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import net.deanly.structure.search.domain.search.core.answer.SearchAnswerItem;
import net.deanly.structure.search.domain.search.core.exception.NoQueryServiceException;
import org.springframework.data.domain.Page;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Set;

public final class SearchQueryWorker {

    public <T> Page<SearchAnswerItem<T>> blockQueryPage(Type t, IQueryServiceProvider provider, QueryCoordinator qc)
            throws NoQueryServiceException {

        IQueryService<?> unchecked = provider.access(t, qc.getQueryServiceType());

        if (Objects.isNull(unchecked))
            throw new NoQueryServiceException("The QueryServiceProvider does not provide an interface, like that '" + qc.getQueryServiceType().name() + "'");

        IQueryService<T> queryService = this.checkGenericType(t, unchecked);

        qc.applyReferences(queryService);
        return qc.queryToSupportWithPage(queryService);
    }

    /**
     * 다중 QueryCoordinator 처리가 필요할 스팩이 추가될 경우 구현.
     * @hidden
     */
    public <T> Page<SearchAnswerItem<T>>  parallelsQueryPage(Type t, IQueryServiceProvider provider, QueryCoordinator[] qc) {
        return null;
    }
    /**
     * 다중 QueryCoordinator 처리가 필요할 스팩이 추가될 경우 구현.
     * @hidden
     */
    public <T> Page<SearchAnswerItem<T>>  waterfallQueryPage(Type t, IQueryServiceProvider provider, QueryCoordinator[] qc) {
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> IQueryService<T> checkGenericType(Type type, IQueryService<?> unchecked) throws NoQueryServiceException {
        if (unchecked.identifier().getType() == type)
            return (IQueryService<T>) unchecked;
        throw new NoQueryServiceException("Incorrect is Query Service identifier type '" + type.getTypeName() + "'");
    }
}
