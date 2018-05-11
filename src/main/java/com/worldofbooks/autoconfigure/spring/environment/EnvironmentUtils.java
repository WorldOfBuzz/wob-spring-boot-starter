package com.worldofbooks.autoconfigure.spring.environment;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Arrays;

public class EnvironmentUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private EnvironmentUtils() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        EnvironmentUtils.applicationContext = applicationContext;
    }

    public static boolean isProduction() {
        return Arrays
            .asList(applicationContext.getEnvironment().getActiveProfiles())
            .contains("prod");
    }
}
