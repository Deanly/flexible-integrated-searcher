package net.deanly.demo.domain.integrated_searcher.core.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public final class ColumnTypeGroup {
    private ColumnType[] types;
}
