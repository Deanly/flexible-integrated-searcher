package net.deanly.demo.domain.integrated_searcher.condition;

import lombok.Getter;
import lombok.ToString;
import net.deanly.demo.domain.integrated_searcher.condition.columns.*;
import net.deanly.demo.domain.integrated_searcher.core.condition.ColumnTypeGroup;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchCondition;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 이 클래스는 `Version 1` 검색조건 도메인 모델을 표현합니다.
 */
@Getter // 의도된 Setter 만 사용될 수 있도록, Setter 는 정의하지 않음.
@ToString
public class UserConditionVer1 implements ISearchCondition {

    public UserTextNameFirstColumn firstNameColumn;

    public UserTextNameLastColumn lastNameColumn;

    public UserTextEmailColumn emailColumn;

    public UserTextOwnedCarColumn ownedCarColumn;

    public UserTextCompanyNameColumn companyNameColumn;

    public UserDateCreatedAtColumn createdAtColumn;

    private Pageable pageable;

    @Override
    public Pageable getPageable() {
        return this.pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    // Setters (for Spring Web Binder)

    /**
     * firstName
     * Example: host.com?..&firstName=Dean
     */
    public void setFirstName(String firstName) {
        if (Objects.nonNull(firstName)) {
            this.firstNameColumn = new UserTextNameFirstColumn(firstName);
        }
    }

    /**
     * lastName
     * Example: host.com?..&lastName=Lee
     */
    public void setLastName(String lastName) {
        if (Objects.nonNull(lastName)) {
            this.lastNameColumn = new UserTextNameLastColumn(lastName);
        }
    }

    /**
     * email
     * Example: host.com?..&email=test@test.com
     */
    public void setEmail(String email) {
        if (Objects.nonNull(email)) {
            this.emailColumn = new UserTextEmailColumn(email);
        }
    }

    /**
     * ownedCar
     * Example: host.com?..&ownedCar=Legacy
     */
    public void setOwnedCar(String ownedCar) {
        if (Objects.nonNull(ownedCar)) {
            this.ownedCarColumn = new UserTextOwnedCarColumn(ownedCar);
        }
    }

    /**
     * companyName
     * Example: host.com?..&companyName=Apple
     */
    public void setCompanyName(String companyName) {
        if (Objects.nonNull(companyName)) {
            this.companyNameColumn = new UserTextCompanyNameColumn(companyName);
        }
    }

    /**
     * createdStartAt (Closed only)
     * createdEndAt (Closed only)
     * Example: host.com?..&createdStartAt=2020-01-01&createdEndAt=2020-12-31
     */
    private String _createdStartAt;
    private String _createdEndAt;

    public void setCreatedStartAt(String createdStartAt) {
        if (Objects.nonNull(createdStartAt)) {
            this._createdStartAt = createdStartAt;
            this.makeCreatedAt();
        }
    }

    public void setCreatedEndAt(String createdEndAt) {
        if (Objects.nonNull(createdEndAt)) {
            this._createdEndAt = createdEndAt;
            this.makeCreatedAt();
        }
    }
    private void makeCreatedAt() {
        if (Objects.isNull(this._createdEndAt) && Objects.isNull(this._createdStartAt)) return;
        if (Objects.isNull(this.createdAtColumn))
            this.createdAtColumn = new UserDateCreatedAtColumn();

        this.createdAtColumn.val.isClosedStart = true;
        this.createdAtColumn.val.isClosedEnd = true;
        this.createdAtColumn.val.setStartDate(this._createdStartAt);
        this.createdAtColumn.val.setEndDate(this._createdEndAt);
    }

    // Interface Implements

    @Override
    public ISearchColumn<?>[] columns() {
        List<ISearchColumn<?>> list = new ArrayList<>();
        list.add(this.firstNameColumn);
        list.add(this.lastNameColumn);
        list.add(this.emailColumn);
        list.add(this.ownedCarColumn);
        list.add(this.companyNameColumn);
        list.add(this.createdAtColumn);
        return list.stream().filter(Objects::nonNull).toArray(ISearchColumn[]::new);
    }

    @Override
    public ColumnTypeGroup[] requiredColumnGrouping() {
        List<ColumnTypeGroup> list = new ArrayList<>();
        list.add(new ColumnTypeGroup(new ColumnType[]{ ColumnType.USER_CREATED_AT }));
        return list.stream().filter(Objects::nonNull).toArray(ColumnTypeGroup[]::new);
    }
}
