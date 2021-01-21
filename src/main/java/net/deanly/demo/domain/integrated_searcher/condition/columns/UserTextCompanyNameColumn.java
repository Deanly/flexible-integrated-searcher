package net.deanly.demo.domain.integrated_searcher.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;

/**
 * [Text]
 * 회사명
 * - Storage
 *  - JPA(mysql): `company_name`
 *  - Elasticsearch: `company_name`
 */
@Data
@ToString
public class UserTextCompanyNameColumn implements ISearchColumn<String> {

    public String val;

    public UserTextCompanyNameColumn(String value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_COMPANY_NAME;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        switch (queryServiceType) {
            case DB_USERS:
                return "companyName";
            case ES_USERS:
                return "company_name";
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
