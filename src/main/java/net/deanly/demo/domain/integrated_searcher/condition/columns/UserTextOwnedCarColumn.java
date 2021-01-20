package net.deanly.demo.domain.integrated_searcher.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;

/**
 * [Text]
 * 소유 차량 이름
 * - Storage
 *  - JPA(mysql): `car_model`
 *  - Elasticsearch: `owned_car`
 */
@Data
@ToString
public class UserTextOwnedCarColumn implements ISearchColumn<String> {

    public String val;

    public UserTextOwnedCarColumn(String value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_CAR_MODEL;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        switch (queryServiceType) {
            case DB_USERS:
                return "car_model";
            case ES_USERS:
                return "owned_car";
            default:
                return null;
        }
    }

    @Override
    public String rawValue() {
        return this.val;
    }

    /**
     * Elasticsearch 와 MySQL 에서 검색 가능.
     */
    @Override
    public QueryServiceType[] readableServices() {
        return new QueryServiceType[] { QueryServiceType.ES_USERS, QueryServiceType.DB_USERS };
    }
}
