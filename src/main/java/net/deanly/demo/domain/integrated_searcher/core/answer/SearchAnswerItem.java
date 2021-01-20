package net.deanly.demo.domain.integrated_searcher.core.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;

import java.util.Map;

@Data
@ToString
@AllArgsConstructor
public class SearchAnswerItem<T> {
    T id;
    ColumnType idType;
    Map<ColumnType, Object> references;
}
