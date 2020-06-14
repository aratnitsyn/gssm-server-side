package com.github.aratnitsyn.gssm.security;

import com.github.aratnitsyn.gssm.config.Constants;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Implementation of {@link AuditorAware}.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Nonnull
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtil.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT));
    }
}
