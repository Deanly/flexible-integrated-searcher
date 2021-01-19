package net.deanly.structure.search.domain.search.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.core.values.SearchPeriod;
import net.deanly.structure.search.domain.search.customized_types.ColumnType;
import net.deanly.structure.search.domain.search.customized_types.QueryServiceType;

/**
 * User's birthday
 * - Storage
 *  - JPA(mysql): `birthday`
 */
@Data
@ToString
public class UserBirthdayColumn implements ISearchColumn<SearchPeriod> {

    public SearchPeriod val;

    public UserBirthdayColumn(SearchPeriod value) {
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
