package net.deanly.structure.search.domain.search.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;

import java.util.Collections;
import java.util.List;

/**
 * User ID
 * - storage
 *  - JPA(mysql): `id`
 *  - Elasticsearch: `id`
 */
@Data
@ToString
public class UserMultipleIdColumn implements ISearchColumn<List<Long>> {

    public List<Long> val;

    public UserMultipleIdColumn(Long value) {
        this.val = Collections.singletonList(value);
    }
    public UserMultipleIdColumn(List<Long> value) {
        this.val = value;
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
