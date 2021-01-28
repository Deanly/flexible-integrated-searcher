package net.deanly.demo.infrastructure.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class RestClientSearchHelper {

    private final RestHighLevelClient restHighLevelClient;

    public RestClientSearchHelper(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    /**
     * Pagination 은 최대 10,000 개까지 default 되어있음.
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/paginate-search-results.html">elasticsearch</a>
     */
    public <T> Page<T> search(Class<T> valueType, String indices, QueryBuilder qb, Pageable pageable) throws IOException {
        return this.search(valueType, indices, qb, pageable, null);
    }

    public <T> Page<T> search(Class<T> valueType, String indices, QueryBuilder qb,
                              Pageable pageable, FetchSourceContext source) throws IOException {

        SearchResponse response = this.search(indices, qb, pageable, source);
        long total = response.getHits().getTotalHits().value;
        SearchHit[] hits = response.getHits().getHits();

        List<T> result = new ArrayList<>();
        ObjectReader reader = new ObjectMapper().readerFor(valueType);
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            result.add(reader.readValue(json));
        }

        return new PageImpl<T>(result, pageable, total);
    }


    public <T> SearchResponse search(String indices, QueryBuilder qb) throws IOException {
        return this.search(indices, qb, null, null);
    }

    public <T> SearchResponse search(String indices, QueryBuilder qb, Pageable pageable) throws IOException {
        return this.search(indices, qb, pageable, null);
    }

    public <T> SearchResponse search(String indices,
                                     QueryBuilder qb,
                                     Pageable pageable,
                                     FetchSourceContext source
    ) throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(qb);

        if (!Objects.isNull(source)) {
            sourceBuilder.fetchSource(source);
        }
        if (!Objects.isNull(pageable)) {
            pageable.getSort();
            List<SortBuilder<?>> sbList = new ArrayList<>();
            for (Sort.Order order : pageable.getSort()) {
                sbList.add(new FieldSortBuilder(order.getProperty())
                        .order(order.getDirection().equals(Sort.Direction.ASC) ? SortOrder.ASC : SortOrder.DESC));
            }
            for (SortBuilder<?> sort : sbList) {
                sourceBuilder.sort(sort);
            }

            sourceBuilder.from(Optional.of(pageable.getOffset()).orElse(0L).intValue());
            sourceBuilder.size(pageable.getPageSize());
        }

        SearchRequest searchRequest = new SearchRequest(indices);
        searchRequest.source(sourceBuilder);
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }

}
