package net.deanly.structure.search.domain.search.core.values;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.deanly.structure.search.domain.search.condition.ColumnType;
import net.deanly.structure.search.domain.search.provider.QueryServiceType;

@Data
@AllArgsConstructor
public final class PriorityQueryByColumn {

    /**
     * A type of the search condition column.
     * When this column is queried as a condition, this priority policy is activated.
     * If defined as null, always active.
     *
     * 우선 정책의 활성화 조건으로
     * 검색 조건에 정의된 `ColumnType` 이 포함된 경우, 이 우선 정책은 활성화 됩니다.
     * 만약, null 일 경우 항상 활성화됩니다.
     */
    private ColumnType columnType;

    /**
     * A type of representing Query Service.
     * The target of activation that is.
     *
     * 이 우선 정책이 활성화될 경우, 결정 대상이 되는 Query Service 의 식별 Type 입니다.
     */
    private QueryServiceType queryServiceType;

    /**
     * This is the priority policy to be applied when this object becomes the
     * target of activation.
     *
     * 정책의 종류입니다.
     */
    private PriorityQueryType priorityQueryType;
}
