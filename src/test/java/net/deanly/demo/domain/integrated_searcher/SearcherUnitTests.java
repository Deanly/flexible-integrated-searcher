package net.deanly.demo.domain.integrated_searcher;

import net.deanly.demo.domain.integrated_searcher.condition.UserConditionVer1;
import net.deanly.demo.domain.integrated_searcher.condition.columns.UserDateCreatedAtColumn;
import net.deanly.demo.domain.integrated_searcher.core.answer.SearchAnswer;
import net.deanly.demo.domain.integrated_searcher.core.exception.InvalidConditionException;
import net.deanly.demo.domain.integrated_searcher.core.exception.NoQueryServiceException;
import net.deanly.demo.domain.integrated_searcher.core.sub.IQueryServiceProvider;
import net.deanly.demo.domain.integrated_searcher.core.sub.ISearchPolicy;
import net.deanly.demo.domain.integrated_searcher.core.values.PriorityQueryByColumn;
import net.deanly.demo.domain.integrated_searcher.core.values.PriorityQueryType;
import net.deanly.demo.domain.integrated_searcher.core.values.SearchPeriod;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;
import net.deanly.demo.domain.integrated_searcher.provider.AllQueryServiceProvider;
import net.deanly.demo.infrastructure.elasticsearch.RestClientSearchHelper;
import net.deanly.demo.infrastructure.jpa.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class SearcherUnitTests {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RestClientSearchHelper restClientSearchHelper;

    @Test
    public void test_UserSearcher_DBOnly() throws InvalidConditionException, NoQueryServiceException, IOException {
//        ISearchPolicy policy = new EmptySearchPolicy();
        ISearchPolicy policy = this.getDBPolicy();
        IQueryServiceProvider provider = new AllQueryServiceProvider(usersRepository, restClientSearchHelper);
        UserSearcher searcher = new UserSearcher(policy, provider);

        UserConditionVer1 condition = new UserConditionVer1();
        condition.createdAtColumn = new UserDateCreatedAtColumn(new SearchPeriod("2018-01-01", null));

        SearchAnswer<Long> answer = searcher.search(condition);
        Assertions.assertEquals(answer.getSearchPath().length, 1);
        Assertions.assertEquals(answer.getSearchPath()[0], QueryServiceType.DB_USERS);
        System.out.println(answer.getValues());
    }

    @Test
    public void test_UserSearcher_ESOnly() throws InvalidConditionException, IOException, NoQueryServiceException {
        //        ISearchPolicy policy = new EmptySearchPolicy();
        ISearchPolicy policy = this.getESPolicy();
        IQueryServiceProvider provider = new AllQueryServiceProvider(usersRepository, restClientSearchHelper);
        UserSearcher searcher = new UserSearcher(policy, provider);

        UserConditionVer1 condition = new UserConditionVer1();
        condition.createdAtColumn = new UserDateCreatedAtColumn(new SearchPeriod("2018-01-01", null));

        SearchAnswer<Long> answer = searcher.search(condition);
        Assertions.assertEquals(answer.getSearchPath().length, 1);
        Assertions.assertEquals(answer.getSearchPath()[0], QueryServiceType.ES_USERS);
        System.out.println(answer.getValues());
    }

    private ISearchPolicy getDBPolicy() {
        return new ISearchPolicy() {
            @Override
            public PriorityQueryByColumn[] prioritiesQueryByColumn() {
                return new PriorityQueryByColumn[] {
                        new PriorityQueryByColumn(null, QueryServiceType.DB_USERS, PriorityQueryType.ALWAYS)
                };
            }
        };
    }

    private ISearchPolicy getESPolicy() {
        return new ISearchPolicy() {
            @Override
            public PriorityQueryByColumn[] prioritiesQueryByColumn() {
                return new PriorityQueryByColumn[] {
                        new PriorityQueryByColumn(null, QueryServiceType.ES_USERS, PriorityQueryType.ALWAYS)
                };
            }
        };
    }

}
