package com.github.aratnitsyn.gssm.service.auth;

import com.github.aratnitsyn.gssm.db.repository.common.UserRepository;
import com.github.aratnitsyn.gssm.db.schema.common.UserEntity;
import com.github.aratnitsyn.gssm.security.jwt.JwtTokenProvider;
import com.github.aratnitsyn.gssm.web.message.auth.SignInRequestMessage;
import com.github.aratnitsyn.gssm.web.message.auth.SignInResponseMessage;
import com.github.aratnitsyn.gssm.web.message.auth.SignUpRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SingleAuthService implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private final static Logger LOGGER = LoggerFactory.getLogger(SingleAuthService.class);

    public SingleAuthService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtTokenProvider jwtTokenProvider,
        AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public SignInRequestMessage signIn(SignInResponseMessage requestMessage) {
        final String username = requestMessage.getUsername();
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,
                requestMessage.getPassword()
            ));
            SecurityContextHolder.getContext()
                                 .setAuthentication(authenticate);
            final String token = jwtTokenProvider.generateToken(authenticate, requestMessage.getRememberMe());
            return new SignInRequestMessage(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return new SignInRequestMessage(null);
    }

    @Override
    public UserEntity signUp(SignUpRequestMessage requestMessage) {
        final String username = requestMessage.getUsername();
        final String password = requestMessage.getPassword();
        final UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(passwordEncoder.encode(password));
        userRepository.save(userEntity);
        LOGGER.debug("Create user: {}", userEntity);
        return userEntity;
    }
}
