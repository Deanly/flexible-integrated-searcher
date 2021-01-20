package net.deanly.demo.domain.integrated_searcher.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;


/**
 * [Check]
 * 결혼여부
 * - storage
 *  - JPA(mysql): `married`
 *  - Elasticsearch: `married`
 */
@Data
@ToString
public class UserCheckMarriedColumn implements ISearchColumn<Boolean> {

    public Boolean val;

    public UserCheckMarriedColumn(Boolean value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_MARRIED;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        return "married";
    }

    @Override
    public Boolean rawValue() {
        return this.val;
    }

    @Override
    public QueryServiceType[] readableServices() {
        return new QueryServiceType[] { QueryServiceType.ES_USERS, QueryServiceType.DB_USERS };
    }
}
