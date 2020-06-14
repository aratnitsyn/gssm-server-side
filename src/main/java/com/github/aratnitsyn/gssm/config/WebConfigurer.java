package com.github.aratnitsyn.gssm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Objects;

@Component
public class WebConfigurer implements ServletContextInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfigurer.class);

    @Override
    public void onStartup(ServletContext servletContext) {

    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        if (Objects.nonNull(corsConfiguration.getAllowedOrigins()) && !corsConfiguration.getAllowedOrigins().isEmpty()) {
            LOGGER.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", corsConfiguration);
        }
        return new CorsFilter(source);
    }
}
