package net.deanly.structure.search.domain.search.core.sub;

import net.deanly.structure.search.domain.search.core.values.PriorityQueryByColumn;

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
