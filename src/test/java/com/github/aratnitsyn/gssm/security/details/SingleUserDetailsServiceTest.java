package com.github.aratnitsyn.gssm.security.details;

import com.github.aratnitsyn.gssm.GssmApplication;
import com.github.aratnitsyn.gssm.db.repository.common.UserRepository;
import com.github.aratnitsyn.gssm.db.schema.common.UserEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = GssmApplication.class)
@Transactional
public class SingleUserDetailsServiceTest {
    private static final String FIRST_USER__USERNAME = "first_user__username";
    private static final String SECOND_USER__USERNAME = "second_user__username";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService singleUserDetailsService;

    @BeforeEach
    public void setup() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(FIRST_USER__USERNAME);
        userEntity.setPassword(RandomStringUtils.random(60));
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
    }

    @Test
    public void assertThatUserCanBeFoundByUsername() {
        final UserDetails userDetails = singleUserDetailsService.loadUserByUsername(FIRST_USER__USERNAME);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(FIRST_USER__USERNAME);
        assertThat(userDetails.isEnabled()).isEqualTo(true);
    }

    @Test
    public void assetThatUserCanBeFoundByUsernameIgnoreCase() {
        final UserDetails userDetails = singleUserDetailsService.loadUserByUsername(FIRST_USER__USERNAME.toUpperCase());
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(FIRST_USER__USERNAME);
        assertThat(userDetails.isEnabled()).isEqualTo(true);
    }

    @Test
    public void assertThatCabByUserNotFoundByUsername() {
        assertThatExceptionOfType(UsernameNotFoundException.class)
            .isThrownBy(
                () -> singleUserDetailsService.loadUserByUsername(SECOND_USER__USERNAME.toUpperCase())
            );
    }
}
