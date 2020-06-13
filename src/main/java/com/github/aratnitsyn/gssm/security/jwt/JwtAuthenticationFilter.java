package com.github.aratnitsyn.gssm.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**
     * Name header for authorization.
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Name method of authorization
     */
    public static final String AUTHORIZATION_METHOD = "Bearer ";

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService singleUserDetailsService;

    @Override
    protected void doFilterInternal(
        @Nonnull HttpServletRequest request,
        @Nonnull HttpServletResponse response,
        @Nonnull FilterChain chain
    ) throws IOException, ServletException {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if (Objects.isNull(securityContext.getAuthentication())) {
            final Authentication authentication = getAuthentication(request);
            if (Objects.nonNull(authentication)) {
                securityContext.setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        final String token = Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
                                     .map(header -> header.substring(AUTHORIZATION_METHOD.length()))
                                     .orElse(null);
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            final String username = jwtTokenProvider.extractUsername(token);
            if (StringUtils.hasText(username)) {
                final UserDetails userDetails = singleUserDetailsService.loadUserByUsername(username);
                if (Objects.nonNull(userDetails)) {
                    final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                    usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    return usernamePasswordAuthenticationToken;
                }
            }
        }
        return null;
    }
}
