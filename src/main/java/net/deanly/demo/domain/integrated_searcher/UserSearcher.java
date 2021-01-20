package net.deanly.demo.domain.integrated_searcher;

import net.deanly.demo.domain.integrated_searcher.core.CoreSearcher;
import net.deanly.demo.domain.integrated_searcher.core.sub.IQueryServiceProvider;
import net.deanly.demo.domain.integrated_searcher.core.sub.ISearchPolicy;

public class UserSearcher extends CoreSearcher<Long> {
    public UserSearcher(ISearchPolicy policy, IQueryServiceProvider provider) {
        super(policy, provider);
    }
}
