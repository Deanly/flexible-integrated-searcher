package net.deanly.demo.infrastructure.es;

import net.deanly.demo.infrastructure.elasticsearch.RestClientSearchHelper;
import net.deanly.demo.infrastructure.elasticsearch.documents.UserDTO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;

@SpringBootTest
public class RestClientSearchHelperUnitTests {

    @Autowired
    RestClientSearchHelper restClientSearchHelper;

    @Test
    public void test() throws IOException {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
//        qb.should(QueryBuilders.matchQuery("car_model", "S"));
//        qb.should(QueryBuilders.matchQuery("name", "test1"));

        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "created_at");

        Page<UserDTO> result = restClientSearchHelper
                .search(UserDTO.class, "users-*", qb, pageable);

        System.out.println("total: " + result.getTotalElements() + ", noe: " + result.getNumberOfElements());
        System.out.println("page: " + (result.getNumber()) + " / " + result.getTotalPages());
        for (UserDTO item: result.getContent()) {
            System.out.println(item);
        }
    }

}
