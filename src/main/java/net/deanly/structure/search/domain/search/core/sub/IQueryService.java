package net.deanly.structure.search.domain.search.core.sub;

import net.deanly.structure.search.domain.search.condition.ColumnType;
import net.deanly.structure.search.domain.search.core.answer.SearchAnswerItem;
import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.provider.QueryServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Query Service
 * 검색 조건에 따라서, 저장소에서 데이터를 조회하는 방법을 서술하는 클래스입니다.
 * A class model interface that describes how to query from the repository.
 */
public interface IQueryService<T> {

    /**
     * @return A type that identifies the Query-Service.
     */
    QueryServiceType type();

    /**
     * @param columns Translated search criteria.
     * @param pageable Pagination.
     * @return Result value.
     */
    Page<SearchAnswerItem<T>> search(ISearchColumn<?>[] columns, Pageable pageable, ColumnType[] references);

    /**
     * @return Column ID type of repository identifier.
     */
    ColumnType identifier();

    /**
     * @return Column types that can be referenced.
     */
    ColumnType[] references();
}
