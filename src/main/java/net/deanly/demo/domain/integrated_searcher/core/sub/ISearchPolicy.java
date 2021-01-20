package net.deanly.demo.domain.integrated_searcher.core.sub;

import net.deanly.demo.domain.integrated_searcher.core.values.PriorityQueryByColumn;

public interface ISearchPolicy {

    /**
     * Policy definition method that selects Query Service implementation
     * according to `SearchCondition`.
     * <blockquote><pre>
     *     // If column A is a search condition, always search using B.
     *     new PriorityQueryByColumn(ColumnType.A, QueryServiceType.B, PriorityQueryType.ALWAYS)
     * </pre></blockquote>
     * @return PriorityQueryByColumn[]
     */
    PriorityQueryByColumn[] prioritiesQueryByColumn();
}
