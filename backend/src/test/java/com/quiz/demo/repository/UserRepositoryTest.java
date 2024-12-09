package com.quiz.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import com.quiz.demo.model.User;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail() {
        // given
        User user = new User(
                null,
                "Mohamed",
                "BENIAICH",
                "mohamedbeniaich000@gmail.com",
                "osjfbjd",
                null);
        userRepository.save(user);

        // when
        User foundUser = userRepository.findByEmail("mohamedbeniaich000@gmail.com");

        // then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("mohamedbeniaich000@gmail.com");
        assertThat(foundUser.getFirstName()).isEqualTo("Mohamed");
        assertThat(foundUser.getLastName()).isEqualTo("BENIAICH");
    }

    @Test
    void testFindByEmailAndPassword() {
        // given
        User user = new User(
                null,
                "Mohamed",
                "BENIAICH",
                "mohamedbeniaich00@gmail.com",
                "osjfbjd",
                null);
        userRepository.save(user);

        // when
        User foundUser = userRepository.findByEmailAndPassword("mohamedbeniaich00@gmail.com", "osjfbjd");

        // then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("mohamedbeniaich00@gmail.com");
        assertThat(foundUser.getPassword()).isEqualTo("osjfbjd");
    }
}
