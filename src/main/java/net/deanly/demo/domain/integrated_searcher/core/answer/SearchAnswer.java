package net.deanly.demo.domain.integrated_searcher.core.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchCondition;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;
import org.springframework.data.domain.Page;

@Data
@Builder
@AllArgsConstructor
@ToString
public class SearchAnswer<T> {
    private ISearchCondition conditions;
    private Page<SearchAnswerItem<T>> values;
    private QueryServiceType[] searchPath;
}
