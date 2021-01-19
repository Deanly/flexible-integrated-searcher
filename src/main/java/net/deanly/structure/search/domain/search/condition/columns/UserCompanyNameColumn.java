package net.deanly.structure.search.domain.search.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;

/**
 * 회사명
 * - Storage
 *  - JPA(mysql): `company_name`
 *  - Elasticsearch: `company_name`
 */
@Data
@ToString
public class UserCompanyNameColumn implements ISearchColumn<String> {

    public String val;

    public UserCompanyNameColumn(String value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_COMPANY_NAME;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        return "company_name";
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
