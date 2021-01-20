package net.deanly.demo.infrastructure.jpa;

import net.deanly.demo.infrastructure.jpa.entity.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsersRepositoryUnitTests {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void test_access() {
        long count = usersRepository.count();
        System.out.println(count);
        Assertions.assertTrue(count > 0);
    }

    @Test
    public void test_get_first() {
        Optional<UserDTO> user = usersRepository.findById(1L);
        System.out.println(user);
        Assertions.assertNotNull(user);
    }

}
