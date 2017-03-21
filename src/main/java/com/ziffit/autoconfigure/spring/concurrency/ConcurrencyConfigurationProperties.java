package com.ziffit.autoconfigure.spring.concurrency;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Component
@ConfigurationProperties("world-of-buzz.concurrency")
@Validated
public class ConcurrencyConfigurationProperties {

    @Min(value = 4, message = "The shared thread pool size can't be lower than 4.")
    private int asyncThreadPoolSize;

    public int getAsyncThreadPoolSize() {
        return asyncThreadPoolSize;
    }

    public void setAsyncThreadPoolSize(int asyncThreadPoolSize) {
        this.asyncThreadPoolSize = asyncThreadPoolSize;
    }
}
