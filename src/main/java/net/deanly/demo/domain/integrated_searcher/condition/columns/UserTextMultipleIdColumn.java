package net.deanly.demo.domain.integrated_searcher.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * [Text]
 * User ID
 * - storage
 *  - JPA(mysql): `id`
 *  - Elasticsearch: `id`
 */
@Data
@ToString
public class UserTextMultipleIdColumn implements ISearchColumn<List<Long>> {

    public List<Long> val;

    public UserTextMultipleIdColumn(String text) {
        this.val = Arrays.stream(text.split(",")).map(Long::parseLong).collect(Collectors.toList());
    }
    public UserTextMultipleIdColumn(Long value) {
        this.val = Collections.singletonList(value);
    }
    public UserTextMultipleIdColumn(List<Long> values) {
        this.val = values;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_ID;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        return "id";
    }

    @Override
    public List<Long> rawValue() {
        return this.val;
    }

    @Override
    public QueryServiceType[] readableServices() {
        return new QueryServiceType[] { QueryServiceType.DB_USERS, QueryServiceType.ES_USERS };
    }
}
