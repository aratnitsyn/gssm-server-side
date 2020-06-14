package com.github.aratnitsyn.gssm.config;

import org.springframework.boot.SpringApplication;

import java.util.HashMap;

public class DefaultProfileUtil {
    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";

    private DefaultProfileUtil() {

    }

    public static void addDefaultProfile(SpringApplication springApplication) {
        final HashMap<String, Object> properties = new HashMap<>();
        properties.put(SPRING_PROFILE_DEFAULT, SPRING_PROFILE_DEVELOPMENT);
        springApplication.setDefaultProperties(properties);
    }
}
