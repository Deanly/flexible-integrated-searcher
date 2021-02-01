package net.deanly.demo.domain.integrated_searcher.provider.query_services;

import net.deanly.demo.domain.integrated_searcher.core.answer.SearchAnswerItem;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.core.sub.IQueryService;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;
import net.deanly.demo.domain.integrated_searcher.provider.supports.ESQuerySupporter;
import net.deanly.demo.infrastructure.elasticsearch.RestClientSearchHelper;
import net.deanly.demo.infrastructure.elasticsearch.documents.UserDTO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.*;

public class UserESQueryService extends ESQuerySupporter implements IQueryService<Long> {

    private final RestClientSearchHelper restClientSearchHelper;

    public UserESQueryService(RestClientSearchHelper rcsHelper) {
        this. restClientSearchHelper = rcsHelper;
    }

    @Override
    public QueryServiceType type() {
        return QueryServiceType.ES_USERS;
    }

    @Override
    public Page<SearchAnswerItem<Long>> search(ISearchColumn<?>[] columns, Pageable pageable, ColumnType[] references) throws IOException {
        FetchSourceContext source = null;
        if (references.length > 0) {
            source = new FetchSourceContext(true, this.convertReferences(references), new String[0]);
        }

        return restClientSearchHelper.search(
                UserDTO.class,
                "users-*",
                this.toBoolQueryBuilder(columns),
                pageable,
                source).map(t -> this.convert(t, references));
    }

    @Override
    public ColumnType identifier() {
        return ColumnType.USER_ID;
    }

    @Override
    public ColumnType[] references() {
        return new ColumnType[] { ColumnType.USER_CREATED_AT, ColumnType.USER_UPDATED_AT };
    }

    private SearchAnswerItem<Long> convert(UserDTO dto, ColumnType[] references) {
        Map<ColumnType, Object> sr = new HashMap<>();

        for (ColumnType columnType : references) {
            switch (columnType) {
                case USER_ID:
                    sr.put(ColumnType.USER_ID, dto.id);
                    break;
                case USER_CREATED_AT:
                    sr.put(ColumnType.USER_CREATED_AT, dto.createdAt);
                    break;
                case USER_UPDATED_AT:
                    sr.put(ColumnType.USER_UPDATED_AT, dto.updatedAt);
                    break;
                default: break;
            }
        }

        return new SearchAnswerItem<>(dto.id, ColumnType.USER_ID, sr);
    }

    private String[] convertReferences(ColumnType[] references) {
        List<String> list = new ArrayList<>();
        for (ColumnType reference : references) {
            switch (reference) {
                case USER_ID:
                    list.add("id");
                    break;
                case USER_CREATED_AT:
                    list.add("created_at");
                    break;
                case USER_UPDATED_AT:
                    list.add("updated_at");
                    break;
                default:
                    break;
            }
        }
        return list.toArray(new String[0]);
    }

    private QueryBuilder toBoolQueryBuilder(ISearchColumn<?>[] columns) {
        BoolQueryBuilder andBqb = QueryBuilders.boolQuery();
        BoolQueryBuilder orBqb = QueryBuilders.boolQuery(); // OR 기능 생략.

        for (ISearchColumn<?> column : columns) {
            switch (column.type()) {
                case USER_ID:
                case USER_PAYMENT_CURRENCY:
                    this.patternList(andBqb, column);
                    break;
                case USER_IS_MEMBER:
                    // No supported
                    break;
                case USER_FIRST_NAME:
                case USER_LAST_NAME:
                case USER_FULL_NAME:
                case USER_EMAIL:
                case USER_CAR_MODEL:
                case USER_COMPANY_NAME:
                case USER_GENDER:
                case USER_IP_ADDRESS:
                case USER_MARRIED:
                    this.patternSingleEqual(andBqb, column);
                    break;
                case USER_BIRTHDAY:
                case USER_CREATED_AT:
                case USER_UPDATED_AT:
                    this.patternSearchPeriod(andBqb, column);
                default:
                    break;
            }
        }

        // OR 기능 생략.
        // andBqb.must(orBqb);
        return andBqb;
    }


}
