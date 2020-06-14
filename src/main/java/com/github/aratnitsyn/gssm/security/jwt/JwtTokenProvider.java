package com.github.aratnitsyn.gssm.security.jwt;

import com.github.aratnitsyn.gssm.config.ApplicationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * Based are methods for using jwt security.
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class JwtTokenProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    private Key key;

    private final Long tokenValidityInMilliseconds;
    private final Long tokenValidityInMillisecondsForRememberMe;

    public JwtTokenProvider(ApplicationProperties applicationProperties) {
        final String base64Secret = applicationProperties.getSecurity()
                                                         .getAuthentication()
                                                         .getJwt()
                                                         .getBase64Secret();
        if (StringUtils.isEmpty(base64Secret)) {
            LOGGER.error("Error: the JWT base64 token is null. Please setting it.");
        } else {
            this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        }
        this.tokenValidityInMilliseconds = 1000 * applicationProperties
            .getSecurity()
            .getAuthentication()
            .getJwt()
            .getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe = 1000 * applicationProperties
            .getSecurity()
            .getAuthentication()
            .getJwt()
            .getTokenValidityInSecondsForRememberMe();
    }

    /**
     * Generating a token
     *
     * @param authentication authentication
     * @param rememberMe     whether to remember authenticate
     * @return token
     */
    public String generateToken(Authentication authentication, Boolean rememberMe) {
        final Date currentDate = new Date();
        final Date expirationDate = new Date(
            currentDate.getTime() + (
                rememberMe
                ? tokenValidityInMillisecondsForRememberMe
                : tokenValidityInMilliseconds
            )
        );
        return Jwts.builder()
                   .setSubject(authentication.getName())
                   .signWith(key, SignatureAlgorithm.HS512)
                   .setIssuedAt(currentDate)
                   .setExpiration(expirationDate)
                   .compact();
    }

    /**
     * Validating a token of {@link UserDetails user information}.
     *
     * @param token token
     * @return If {@code True} then a token valid otherwise {@code False}.
     */
    public boolean validateToken(String token) {
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
        return true;
    }

    /**
     * Extracting a username from the token.
     *
     * @param token token
     * @return username
     */
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Extracting a date expired from the token.
     *
     * @param token token
     * @return date expired
     */
    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    /**
     * Extracting a subject by claims.
     *
     * @param token          token
     * @param claimsResolver claims resolver
     * @param <T>            type subject
     * @return subject
     */
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracting all claims from token.
     *
     * @param token token
     * @return claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    /**
     * Checking is token expired.
     *
     * @param token token
     * @return If {@code True} then a token expired otherwise {@code False}.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
