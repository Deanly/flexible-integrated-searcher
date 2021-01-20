package net.deanly.demo.domain.integrated_searcher;

import net.deanly.demo.domain.integrated_searcher.condition.columns.*;
import net.deanly.demo.domain.integrated_searcher.core.condition.ColumnTypeGroup;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;
import net.deanly.demo.domain.integrated_searcher.condition.UserConditionVer1;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchCondition;
import net.deanly.demo.domain.integrated_searcher.core.exception.InvalidConditionException;
import net.deanly.demo.domain.integrated_searcher.core.exception.NoQueryServiceException;
import net.deanly.demo.domain.integrated_searcher.core.sub.ISearchPolicy;
import net.deanly.demo.domain.integrated_searcher.core.sub.QueryCoordinator;
import net.deanly.demo.domain.integrated_searcher.core.sub.internal.WeightedCriteriaTranslator;
import net.deanly.demo.domain.integrated_searcher.core.values.PriorityQueryByColumn;
import net.deanly.demo.domain.integrated_searcher.core.values.PriorityQueryType;
import net.deanly.demo.domain.integrated_searcher.core.values.SearchPeriod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

public class WeightedCriteriaTranslatorUnitTest {

    @Test
    public void test_생성() throws InvalidConditionException, NoQueryServiceException {
        WeightedCriteriaTranslator translator = new WeightedCriteriaTranslator();

        UserConditionVer1 sc = new UserConditionVer1();
        sc.createdAtColumn = new UserDateCreatedAtColumn(new SearchPeriod("2017-01-01", null));
        sc.firstNameColumn = new UserTextNameFirstColumn("Dean");

        QueryCoordinator qc = translator.translate(this.getSimplePolicy(), sc);

        System.out.println(qc);
        Assertions.assertNotNull(qc);
    }

    @Test
    public void test_throw_error_WeightedCriteria_2개이상() throws InvalidConditionException, NoQueryServiceException {
        WeightedCriteriaTranslator translator = new WeightedCriteriaTranslator();

        ISearchCondition sc = new ISearchCondition() {
            @Override
            public ISearchColumn<?>[] columns() {
                return new ISearchColumn[] {
                        new UserCheckIsMemberColumn(true), //  API
                        new UserTextNameFullColumn("Full Name") //  ES
                };
            }

            @Override
            public ColumnTypeGroup[] requiredColumnGrouping() {
                return new ColumnTypeGroup[0];
            }

            @Override
            public Pageable getPageable() {
                return null;
            }
        };

        Assertions.assertThrows(NoQueryServiceException.class, () -> translator.translate(this.getSimplePolicy(), sc));
    }

    @Test
    public void test_policy() throws InvalidConditionException, NoQueryServiceException {
        WeightedCriteriaTranslator translator = new WeightedCriteriaTranslator();

        ISearchPolicy policyDB = () -> new PriorityQueryByColumn[] {
                new PriorityQueryByColumn(ColumnType.USER_COMPANY_NAME, QueryServiceType.DB_USERS, PriorityQueryType.ALWAYS)
        };

        ISearchCondition scWithoutCompanyName = new ISearchCondition() {
            @Override
            public ISearchColumn<?>[] columns() {
                return new ISearchColumn[] {
                        new UserTextNameFirstColumn("Name"),
                };
            }

            @Override
            public ColumnTypeGroup[] requiredColumnGrouping() {
                return new ColumnTypeGroup[0];
            }

            @Override
            public Pageable getPageable() {
                return null;
            }
        };
        ISearchCondition scWithCompanyName = new ISearchCondition() {
            @Override
            public ISearchColumn<?>[] columns() {
                return new ISearchColumn[] {
                        new UserTextNameFirstColumn("Name"),
                        new UserTextCompanyNameColumn("Apple")
                };
            }

            @Override
            public ColumnTypeGroup[] requiredColumnGrouping() {
                return new ColumnTypeGroup[0];
            }

            @Override
            public Pageable getPageable() {
                return null;
            }
        };

        QueryCoordinator qc1 = translator.translate(policyDB, scWithoutCompanyName); // ES
        QueryCoordinator qc2 = translator.translate(this.getSimplePolicy(), scWithoutCompanyName); // ES

        Assertions.assertEquals(qc1.getQueryServiceType(), qc2.getQueryServiceType());

        QueryCoordinator qc3 = translator.translate(policyDB, scWithCompanyName); // DB
        QueryCoordinator qc4 = translator.translate(this.getSimplePolicy(), scWithCompanyName); // ES

        Assertions.assertNotEquals(qc3.getQueryServiceType(), qc4.getQueryServiceType());

    }

    private ISearchPolicy getSimplePolicy() {
        return () -> new PriorityQueryByColumn[0];
    }
}
