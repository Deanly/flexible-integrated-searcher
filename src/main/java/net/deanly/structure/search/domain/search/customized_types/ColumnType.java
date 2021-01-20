package net.deanly.structure.search.domain.search.customized_types;

import java.lang.reflect.Type;
import java.util.Date;

public enum ColumnType {

    USER_ID(Long.class),
    USER_FIRST_NAME(String.class),
    USER_LAST_NAME(String.class),
    USER_FULL_NAME(String.class),
    USER_EMAIL(String.class),
    USER_GENDER(String.class),
    USER_IP_ADDRESS(String.class),
    USER_MARRIED(Boolean.class),
    USER_CAR_MODEL(String.class),
    USER_COMPANY_NAME(String.class),
    USER_BIRTHDAY(Date.class),
    USER_IS_MEMBER(Boolean.class),
    USER_CREATED_AT(Date.class),
    USER_UPDATED_AT(Date.class)
    ;

    private final Type type;

    ColumnType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
