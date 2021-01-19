package net.deanly.structure.search.domain.search.condition;

import net.deanly.structure.search.domain.search.core.condition.ColumnTypeGroup;
import net.deanly.structure.search.domain.search.core.condition.ISearchColumn;
import net.deanly.structure.search.domain.search.core.condition.ISearchCondition;
import org.springframework.data.domain.Pageable;

/**
 * 이 클래스는 Version1 의 도메인 모델을 표현합니다.
 */
public class UserConditionVer1 implements ISearchCondition {
    @Override
    public ISearchColumn<?>[] columns() {
        return new ISearchColumn[0];
    }

    @Override
    public ColumnTypeGroup[] requiredColumnGrouping() {
        return new ColumnTypeGroup[0];
    }

    @Override
    public Pageable getPageable() {
        return null;
    }
}
