package net.deanly.structure.search.domain.search.core.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import net.deanly.structure.search.domain.search.core.condition.ISearchCondition;
import org.springframework.data.domain.Page;

@Data
@Builder
@AllArgsConstructor
@ToString
public class SearchAnswer<T> {
    private ISearchCondition conditions;
    private Page<SearchAnswerItem<T>> values;
}
