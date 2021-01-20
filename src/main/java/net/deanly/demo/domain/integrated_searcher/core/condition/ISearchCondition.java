package net.deanly.demo.domain.integrated_searcher.core.condition;

import org.springframework.data.domain.Pageable;

/**
 * 검색 조건 도메인 모델을 구현하는 인터페이스.
 * Class model interface that representing search conditions.
 */
public interface ISearchCondition {

    /**
     * Column fields defined in the condition are provided as an array.
     * @return Array of ISearchColumn
     */
    ISearchColumn<?>[] columns();

    /**
     * Not-Nullable fields definition.
     * Need to be matched one or more in the case of grouped columns in `ColumnTypeGroup`. (like OR).
     * And Each group must all matched. (like AND).
     *
     * @return Array of ColumnTypeGroup
     */
    ColumnTypeGroup[] requiredColumnGrouping();

    /**
     * Pagination
     * @return org.springframework.data.domain.Pageable
     */
    Pageable getPageable();

}
