package net.deanly.structure.search.domain.search.core.sub;

import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class QuerySupporter {

    /**
     * Support Method
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> columnToList(ISearchColumn<?> t) {
        if (Objects.isNull(t.rawValue())) return null;
        if (t.rawValue().getClass().getTypeName().contains("SingletonList"))
            return (List<T>) t.rawValue();
        for (Class<?> anInterface : t.rawValue().getClass().getInterfaces()) {
            if (anInterface.equals(List.class)) {
                List<T> r = (List<T>)t.rawValue();
                if (r.size() == 0) return null;
                return r;
            }
        }
        return Collections.singletonList((T) t.rawValue());
    }
}
