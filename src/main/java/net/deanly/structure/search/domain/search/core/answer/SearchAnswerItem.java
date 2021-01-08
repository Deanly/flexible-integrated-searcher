package net.deanly.structure.search.domain.search.core.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import net.deanly.structure.search.domain.search.condition.ColumnType;

import java.util.Map;

@Data
@ToString
@AllArgsConstructor
public class SearchAnswerItem<T> {
    T id;
    ColumnType idType;
    Map<ColumnType, Object> references;
}
