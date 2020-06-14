package com.github.aratnitsyn.gssm;

import com.github.aratnitsyn.gssm.config.ApplicationProperties;
import com.github.aratnitsyn.gssm.config.DefaultProfileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class })
public class GssmApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GssmApplication.class);

    private final Environment environment;

    public GssmApplication(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void initApplication() {
        final List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
        if (!activeProfiles.contains(DefaultProfileUtil.SPRING_PROFILE_PRODUCTION)
            && !activeProfiles.contains(DefaultProfileUtil.SPRING_PROFILE_DEVELOPMENT)) {
            LOGGER.error("");
        }
    }

    public static void main(String[] args) {
        final SpringApplication springApplication = new SpringApplication(GssmApplication.class);
        DefaultProfileUtil.addDefaultProfile(springApplication);
        springApplication.run(args)
                         .getEnvironment();
    }
}
