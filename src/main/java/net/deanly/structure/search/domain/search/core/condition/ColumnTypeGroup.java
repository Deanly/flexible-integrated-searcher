package net.deanly.structure.search.domain.search.core.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.deanly.structure.search.domain.search.condition.ColumnType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public final class ColumnTypeGroup {
    private ColumnType[] types;
}
