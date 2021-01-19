package net.deanly.structure.search.domain.search.core;

import com.google.common.reflect.TypeToken;
import net.deanly.structure.search.domain.search.core.answer.SearchAnswer;
import net.deanly.structure.search.domain.search.core.answer.SearchAnswerItem;
import net.deanly.structure.search.domain.search.core.condition.ISearchCondition;
import net.deanly.structure.search.domain.search.core.exception.InvalidConditionException;
import net.deanly.structure.search.domain.search.core.exception.NoQueryServiceException;
import net.deanly.structure.search.domain.search.core.sub.*;
import net.deanly.structure.search.domain.search.core.sub.internal.SearchQueryWorker;
import net.deanly.structure.search.domain.search.core.sub.internal.WeightedCriteriaTranslator;
import org.springframework.data.domain.Page;

import java.lang.reflect.Type;

public abstract class Search<T> {
    private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) { };
    private final Type type = typeToken.getType();

    private final ISearchPolicy searchPolicy;
    private final IQueryServiceProvider queryServiceProvider;

    private final WeightedCriteriaTranslator translator;
    private final SearchQueryWorker queryWorker;

    public Search(ISearchPolicy policy, IQueryServiceProvider provider) {
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
        return new SearchAnswer<T>(condition, items);
    }
}
