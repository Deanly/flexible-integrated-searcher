package net.deanly.demo.domain.integrated_searcher.policy;

import net.deanly.demo.domain.integrated_searcher.core.sub.ISearchPolicy;
import net.deanly.demo.domain.integrated_searcher.core.values.PriorityQueryByColumn;

public class EmptySearchPolicy implements ISearchPolicy {
    @Override
    public PriorityQueryByColumn[] prioritiesQueryByColumn() {
        return new PriorityQueryByColumn[0];
    }
}
