package net.deanly.demo.domain.integrated_searcher.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.core.values.SearchPeriod;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;

/**
 * [Date]
 * User's birthday
 * - Storage
 *  - JPA(mysql): `birthday`
 */
@Data
@ToString
public class UserDateBirthdayColumn implements ISearchColumn<SearchPeriod> {

    public SearchPeriod val;

    public UserDateBirthdayColumn(SearchPeriod value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_BIRTHDAY;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        return "birthday";
    }

    @Override
    public SearchPeriod rawValue() {
        return this.val;
    }

    /**
     * MySQL 에서만 읽을 수 있음.
     */
    @Override
    public QueryServiceType[] readableServices() {
        return new QueryServiceType[] { QueryServiceType.DB_USERS };
    }
}
