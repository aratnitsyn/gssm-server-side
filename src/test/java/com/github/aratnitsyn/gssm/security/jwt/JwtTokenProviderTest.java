package com.github.aratnitsyn.gssm.security.jwt;

import com.github.aratnitsyn.gssm.config.ApplicationProperties;
import com.github.aratnitsyn.gssm.security.AuthoritiesConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class JwtTokenProviderTest {
    private static final Long ONE_MINUTE = 60000L;

    private Key key;
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setup() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(
            "fd54a45s65fds737b9aafcb3412e07ed99b267f33413274720ddbb7f6c5e64e9f14075f2d7ed041592f0b7657baf8"));
        jwtTokenProvider = new JwtTokenProvider(new ApplicationProperties());
        ReflectionTestUtils.setField(jwtTokenProvider, "key", key, Key.class);
        ReflectionTestUtils.setField(jwtTokenProvider, "tokenValidityInMilliseconds", ONE_MINUTE, Long.class);
    }

    @Test
    public void testThrowsSignatureExceptionWhenJwtHasInvalidSignature() {
        assertThatExceptionOfType(SignatureException.class)
            .isThrownBy(() -> jwtTokenProvider.validateToken(createTokenWithDifferentSignature()));
    }

    @Test
    public void testThrowsMalformedJwtExceptionWhenJwtIsMalformed() {
        final Authentication authentication = createAuthentication();
        final String token = jwtTokenProvider.generateToken(authentication, false);
        final String invalidToken = token.substring(1);
        assertThatExceptionOfType(MalformedJwtException.class)
            .isThrownBy(() -> jwtTokenProvider.validateToken(invalidToken));
    }

    @Test
    public void testThrowsExpiredJwtExceptionWhenJwtIsExpired() {
        ReflectionTestUtils.setField(jwtTokenProvider, "tokenValidityInMilliseconds", -ONE_MINUTE, Long.class);
        final Authentication authentication = createAuthentication();
        final String token = jwtTokenProvider.generateToken(authentication, false);
        assertThatExceptionOfType(ExpiredJwtException.class).isThrownBy(() -> jwtTokenProvider.validateToken(token));
    }

    @Test
    public void testThrowsUnsupportedJwtExceptionWhenJwtIsUnsupported() {
        final String unsupportedToken = createUnsupportedToken();
        assertThatExceptionOfType(UnsupportedJwtException.class)
            .isThrownBy(() -> jwtTokenProvider.validateToken(unsupportedToken));
    }

    private String createUnsupportedToken() {
        return Jwts.builder()
                   .setPayload("payload")
                   .signWith(key, SignatureAlgorithm.HS512)
                   .compact();
    }

    private String createTokenWithDifferentSignature() {
        final Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(
            "Xfd54a45s65fds737b9aafcb3412e07ed99b267f33413274720ddbb7f6c5e64e9f14075f2d7ed041592f0b7657baf8"));
        return Jwts.builder()
                   .setSubject("anonymous")
                   .signWith(key, SignatureAlgorithm.HS512)
                   .setExpiration(new Date(new Date().getTime() + ONE_MINUTE))
                   .compact();
    }

    private Authentication createAuthentication() {
        final ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        return new UsernamePasswordAuthenticationToken("anonymous", "anonymous", grantedAuthorities);
    }
}
