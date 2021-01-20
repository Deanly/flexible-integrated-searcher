package net.deanly.demo.domain.integrated_searcher.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * [Select-Box]
 * 결제 통화
 * - storage
 *  - JPA(mysql): `payment_currency`
 *  - Elasticsearch: `payment_currency`
 */
@Data
@ToString
public class UserSelectBoxCurrencyColumn implements ISearchColumn<String> {

    public Value val;

    public UserSelectBoxCurrencyColumn(Value value) {
        this.val = value;
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_PAYMENT_CURRENCY;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        return "payment_currency";
    }

    @Override
    public String rawValue() {
        if (Objects.isNull(this.val))
            return null;
        return this.val.val;
    }

    @Override
    public QueryServiceType[] readableServices() {
        return new QueryServiceType[] { QueryServiceType.DB_USERS, QueryServiceType.ES_USERS };
    }

    public enum Value {
        USA_DOLLAR("USD"),
        EU_EURO("EUR"),
        KR_WON("KRW")
        ;
        String val;
        Value(String value) {
            this.val = value;
        }
    }
}
