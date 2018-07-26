package com.worldofbooks.autoconfigure.spring.concurrency;

import com.google.common.base.Throwables;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer {

    private static final Logger logger = LogManager.getLogger();

    private ConcurrencyProperties concurrencyProperties;

    public AsyncConfiguration(ConcurrencyProperties concurrencyProperties) {
        this.concurrencyProperties = concurrencyProperties;
    }

    @Override
    @Bean
    @Primary
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(concurrencyProperties.getAsyncThreadPoolSize() / 2);
        executor.setMaxPoolSize(concurrencyProperties.getAsyncThreadPoolSize());
        executor.setThreadNamePrefix("Spring-Async-Executor-");
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (e, method, parameters) -> logger.error(buildExceptionMessage(e, method, parameters));
    }

    private String buildExceptionMessage(Throwable e, Method method, Object... parameters) {
        StringBuilder exceptionMessage = new StringBuilder();
        exceptionMessage.append("==============Uncaught Async Exception==============");
        exceptionMessage.append(System.lineSeparator());
        exceptionMessage.append("Error message: [").append(Throwables.getStackTraceAsString(e)).append("]");
        exceptionMessage.append(System.lineSeparator());
        exceptionMessage.append("Method name: [").append(method.getName()).append("]");
        exceptionMessage.append(System.lineSeparator());
        Arrays.stream(parameters).forEach(parameter -> exceptionMessage
            .append("Calling parameter: [").append(parameter).append("]").append(System.lineSeparator()));
        return exceptionMessage.toString();
    }
}
