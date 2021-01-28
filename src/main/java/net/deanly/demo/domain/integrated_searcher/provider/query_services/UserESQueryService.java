package net.deanly.demo.domain.integrated_searcher.provider.query_services;

import net.deanly.demo.domain.integrated_searcher.core.answer.SearchAnswerItem;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.core.sub.IQueryService;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UserESQueryService implements IQueryService<Long> {
    @Override
    public QueryServiceType type() {
        return QueryServiceType.ES_USERS;
    }

    @Override
    public Page<SearchAnswerItem<Long>> search(ISearchColumn<?>[] columns, Pageable pageable, ColumnType[] references) {
        return null;
    }

    @Override
    public ColumnType identifier() {
        return null;
    }

    @Override
    public ColumnType[] references() {
        return new ColumnType[0];
    }
}
