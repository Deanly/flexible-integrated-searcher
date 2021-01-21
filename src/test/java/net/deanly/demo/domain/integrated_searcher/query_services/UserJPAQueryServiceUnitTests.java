package net.deanly.demo.domain.integrated_searcher.query_services;

import net.deanly.demo.domain.integrated_searcher.condition.columns.*;
import net.deanly.demo.domain.integrated_searcher.core.answer.SearchAnswerItem;
import net.deanly.demo.domain.integrated_searcher.core.condition.ISearchColumn;
import net.deanly.demo.domain.integrated_searcher.core.values.SearchPeriod;
import net.deanly.demo.domain.integrated_searcher.customized_types.ColumnType;
import net.deanly.demo.domain.integrated_searcher.customized_types.QueryServiceType;
import net.deanly.demo.domain.integrated_searcher.provider.UserJPAQueryService;
import net.deanly.demo.infrastructure.jpa.UsersRepository;
import net.deanly.demo.infrastructure.jpa.entity.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserJPAQueryServiceUnitTests {
    @Autowired
    UsersRepository usersRepository;

    @Test
    public void test_properties() {
        UserJPAQueryService service = new UserJPAQueryService(usersRepository);
        Assertions.assertEquals(service.identifier(), ColumnType.USER_ID);
        Assertions.assertEquals(service.type(), QueryServiceType.DB_USERS);
    }

    @Test
    public void test_search_sample() {
        UserJPAQueryService service = new UserJPAQueryService(usersRepository);

        ISearchColumn<?>[] columns = new ISearchColumn[] {
                new UserTextNameFirstColumn("inni")
        };

        Pageable pageable = PageRequest.of(0, 10);

        List<SearchAnswerItem<Long>> result = service.search(columns, pageable, new ColumnType[0])
                .stream().sorted((a, b) -> a.getId() > b.getId() ? 1 : 0).collect(Collectors.toList());

        List<UserDTO> sample = usersRepository.findAll((e, q, b) -> b.like(e.get("firstName"), "%inni%"), pageable)
                .stream().sorted((a, b) -> a.getId() > b.getId() ? 1 : 0).collect(Collectors.toList());

        for (int i = 0; i < result.size(); i++) {
            Assertions.assertEquals(result.get(i).getId(), sample.get(i).getId());
        }
    }

    @Test
    public void test_search_customized_column() {
        UserJPAQueryService service = new UserJPAQueryService(usersRepository);

        ISearchColumn<?>[] columns = new ISearchColumn[] {
                new UserTextNameFullColumn("inni") //  ES
        };

        Pageable pageable = PageRequest.of(0, 10);

        List<SearchAnswerItem<Long>> result = service.search(columns, pageable, new ColumnType[0])
                .stream().sorted((a, b) -> a.getId() > b.getId() ? 1 : 0).collect(Collectors.toList());

        List<UserDTO> sample = usersRepository.findAll((e, q, b) -> b.like(b.concat(b.concat(e.get("firstName"), " "), e.get("lastName")), "%inni%"), pageable)
                .stream().sorted((a, b) -> a.getId() > b.getId() ? 1 : 0).collect(Collectors.toList());

        for (int i = 0; i < result.size(); i++) {
            Assertions.assertEquals(result.get(i).getId(), sample.get(i).getId());
        }
    }

    @Test
    public void test_search_list_id() {
        UserJPAQueryService service = new UserJPAQueryService(usersRepository);

        ISearchColumn<?>[] columns = new ISearchColumn[] {
                new UserTextMultipleIdColumn("1,2,3,4,100")
        };

        Pageable pageable = PageRequest.of(0, 10);

        List<SearchAnswerItem<Long>> result = service.search(columns, pageable, new ColumnType[0])
                .stream().sorted((a, b) -> a.getId() > b.getId() ? 1 : 0).collect(Collectors.toList());

        List<UserDTO> sample = usersRepository.findAll((e, q, b) -> e.get("id").in(Arrays.asList(1L,2L,3L,4L,100L)), pageable)
                .stream().sorted((a, b) -> a.getId() > b.getId() ? 1 : 0).collect(Collectors.toList());

        for (int i = 0; i < result.size(); i++) {
            Assertions.assertEquals(result.get(i).getId(), sample.get(i).getId());
        }
    }

    @Test
    public void test_search_check_box() {
        UserJPAQueryService service = new UserJPAQueryService(usersRepository);

        ISearchColumn<?>[] columns = new ISearchColumn[] {
                new UserCheckBoxCurrencyColumn(Arrays.asList(
                        UserCheckBoxCurrencyColumn.Value.EU_EURO,
                        UserCheckBoxCurrencyColumn.Value.KR_WON))
        };

        Pageable pageable = PageRequest.of(0, 10);

        List<SearchAnswerItem<Long>> result = service.search(columns, pageable, new ColumnType[0])
                .stream().sorted((a, b) -> a.getId() > b.getId() ? 1 : 0).collect(Collectors.toList());

        List<UserDTO> sample = usersRepository.findAll((e, q, b) -> e.get("paymentCurrency").in(Arrays.asList("KRW","EUR")), pageable)
                .stream().sorted((a, b) -> a.getId() > b.getId() ? 1 : 0).collect(Collectors.toList());

        for (int i = 0; i < result.size(); i++) {
            Assertions.assertEquals(result.get(i).getId(), sample.get(i).getId());
        }
    }

    @Test
    public void test_search_date() throws ParseException {
        UserJPAQueryService service = new UserJPAQueryService(usersRepository);

        ISearchColumn<?>[] columns = new ISearchColumn[] {
                new UserDateBirthdayColumn(new SearchPeriod("1986-05-05", null)),
                new UserDateCreatedAtColumn(new SearchPeriod("2018-05-05", "2019-12-10"))
        };

        Pageable pageable = PageRequest.of(0, 10);

        List<SearchAnswerItem<Long>> result = service.search(columns, pageable, new ColumnType[0])
                .stream().sorted((a, b) -> a.getId() > b.getId() ? 1 : 0).collect(Collectors.toList());

        Date date1 = (new SimpleDateFormat("yyyy-MM-dd")).parse("1986-05-05");
        Date date2 = (new SimpleDateFormat("yyyy-MM-dd")).parse("2018-05-05");
        Date date3 = (new SimpleDateFormat("yyyy-MM-dd")).parse("2019-12-10");
        List<UserDTO> sample = usersRepository.findAll((e, q, b) ->
                b.and(
                        b.greaterThanOrEqualTo(e.get("birthday"), date1),
                        b.between(e.get("createdAt"), date2, date3)
                ), pageable).stream().sorted((a, b) -> a.getId() > b.getId() ? 1 : 0).collect(Collectors.toList());

        for (int i = 0; i < result.size(); i++) {
            Assertions.assertEquals(result.get(i).getId(), sample.get(i).getId());
        }
    }
}
