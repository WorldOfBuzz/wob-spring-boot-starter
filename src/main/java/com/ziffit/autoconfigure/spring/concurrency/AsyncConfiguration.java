package com.ziffit.autoconfigure.spring.concurrency;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer {

    private static final Logger logger = LogManager.getLogger();

    private ConcurrencyProperties concurrencyProperties;

    public AsyncConfiguration(ConcurrencyProperties concurrencyProperties) {
        this.concurrencyProperties = concurrencyProperties;
    }

    @Override
    public Executor getAsyncExecutor() {
        return new ForkJoinPool(concurrencyProperties.getAsyncThreadPoolSize());
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (e, method, parameters) -> {
          logger.error("==============Uncaught Async Exception==============");
          logger.error("Error message: {}", e.getMessage());
          logger.error("Method name: [{}]", method.getName());
          Arrays.stream(parameters).forEach(parameter -> logger.error("Calling parameter: [{}]", parameter));
        };
    }
}
