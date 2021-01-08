package net.deanly.structure.search.domain.search.condition;

import java.lang.reflect.Type;

public enum ColumnType {

    SAMPLE1(Long.class),
    SAMPLE2(String.class)
    ;

    private final Type type;

    ColumnType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
