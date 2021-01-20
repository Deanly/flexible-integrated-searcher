package net.deanly.demo.domain.integrated_searcher.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;

import java.util.Objects;

/**
 * [Select-Box]
 * 성별
 * - Storage
 *  - JPA(mysql): `gender`
 */
@Data
@ToString
public class UserSelectBoxGenderColumn implements ISearchColumn<String> {

    public Value val;

    public UserSelectBoxGenderColumn(Value value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_GENDER;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        switch (queryServiceType) {
            case DB_USERS:
                return "gender";
            default:
                return null;
        }
    }

    @Override
    public String rawValue() {
        if (Objects.isNull(this.val))
            return null;
        return this.val.val;
    }

    @Override
    public QueryServiceType[] readableServices() {
        return new QueryServiceType[] { QueryServiceType.DB_USERS };
    }

    public enum Value {
        MALE("Male"),
        FEMALE("Female")
        ;
        String val;
        Value(String value) {
            this.val = value;
        }
    }
}
