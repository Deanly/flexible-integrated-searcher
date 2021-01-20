package net.deanly.demo.domain.integrated_searcher.core;

import com.google.common.reflect.TypeToken;
import net.deanly.demo.domain.integrated_searcher.core.answer.SearchAnswer;
import net.deanly.demo.domain.integrated_searcher.core.answer.SearchAnswerItem;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchCondition;
import net.deanly.demo.domain.integrated_searcher.core.exception.InvalidConditionException;
import net.deanly.demo.domain.integrated_searcher.core.exception.NoQueryServiceException;
import net.deanly.demo.domain.integrated_searcher.core.sub.IQueryServiceProvider;
import net.deanly.demo.domain.integrated_searcher.core.sub.ISearchPolicy;
import net.deanly.demo.domain.integrated_searcher.core.sub.QueryCoordinator;
import net.deanly.demo.domain.integrated_searcher.core.sub.internal.SearchQueryWorker;
import net.deanly.demo.domain.integrated_searcher.core.sub.internal.WeightedCriteriaTranslator;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;
import org.springframework.data.domain.Page;

import java.lang.reflect.Type;

public abstract class CoreSearcher<T> {
    private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) { };
    private final Type type = typeToken.getType();

    private final ISearchPolicy searchPolicy;
    private final IQueryServiceProvider queryServiceProvider;

    private final WeightedCriteriaTranslator translator;
    private final SearchQueryWorker queryWorker;

    public CoreSearcher(ISearchPolicy policy, IQueryServiceProvider provider) {
        this.searchPolicy = policy;
        this.queryServiceProvider = provider;

        this.translator = new WeightedCriteriaTranslator();
        this.queryWorker = new SearchQueryWorker();
    }

    public Type getIdentifierType() {
        return this.type;
    }

    public SearchAnswer<T> search(ISearchCondition condition) throws InvalidConditionException, NoQueryServiceException {
        QueryCoordinator qc = this.translator.translate(this.searchPolicy, condition);

        Page<SearchAnswerItem<T>> items = this.queryWorker.blockQueryPage(this.getIdentifierType(), this.queryServiceProvider, qc);
        return new SearchAnswer<T>(condition, items, new QueryServiceType[] { qc.getQueryServiceType() });
    }
}
