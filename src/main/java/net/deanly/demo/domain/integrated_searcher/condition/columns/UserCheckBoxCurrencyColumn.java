package net.deanly.demo.domain.integrated_searcher.condition.columns;

import lombok.Data;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * [Check-Box]
 * 결제 통화
 * - storage
 *  - JPA(mysql): `payment_currency`
 *  - Elasticsearch: `payment_currency`
 */
@Data
@ToString
public class UserCheckBoxCurrencyColumn implements ISearchColumn<List<String>> {

    public final Set<Value> val = new HashSet<>();

    public UserCheckBoxCurrencyColumn(Value value) {
        this.val.add(value);
    }
    public UserCheckBoxCurrencyColumn(Collection<? extends Value> values) {
        this.val.addAll(values);
    }

    @Override
    public ColumnType type() {
        return ColumnType.USER_PAYMENT_CURRENCY;
    }

    @Override
    public String rawKey(QueryServiceType queryServiceType) {
        switch (queryServiceType) {
            case DB_USERS:
                return "paymentCurrency";
            case ES_USERS:
                return "payment_currency";
            default:
                return null;
        }
    }

    @Override
    public List<String> rawValue() {
        return this.val.stream().map(v -> v.val).collect(Collectors.toList());
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
