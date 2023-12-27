package com.wanted.budgetmanagement.domain.user.repository;

import com.wanted.budgetmanagement.domain.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("유저 저장 성공")
    @Test
    void userSignUp() {
        // given
        String email = "email@gmail.com";
        String password = "password";
        User user = User.builder()
                .email(email)
                .password(password)
                .build();

        // when
        User saveUser = userRepository.save(user);

        // then
        assertAll(
                () -> assertThat(user.getId()).isEqualTo(saveUser.getId()),
                () -> assertThat(user.getEmail()).isEqualTo(saveUser.getEmail()),
                () -> assertThat(user.getPassword()).isEqualTo(saveUser.getPassword()),
                () -> assertThat(user.getRefresh_token()).isEqualTo(saveUser.getRefresh_token())
        );

    }
}
