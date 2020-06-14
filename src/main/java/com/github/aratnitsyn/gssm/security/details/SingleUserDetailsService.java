package com.github.aratnitsyn.gssm.security.details;

import com.github.aratnitsyn.gssm.db.repository.common.UserRepository;
import com.github.aratnitsyn.gssm.db.schema.common.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implements interface of {@link UserDetailsService user-sepcific data}.
 */
@Service
public class SingleUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public SingleUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        return userEntity.map(SingleUserDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));
    }
}
