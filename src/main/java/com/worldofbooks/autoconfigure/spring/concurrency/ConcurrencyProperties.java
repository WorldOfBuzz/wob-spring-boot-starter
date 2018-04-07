package com.worldofbooks.autoconfigure.spring.concurrency;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Component
@ConfigurationProperties("world-of-buzz.concurrency")
@Validated
public class ConcurrencyProperties {

    @Min(value = 4, message = "The shared thread pool size can't be lower than 4.")
    private int asyncThreadPoolSize = 8;

    public int getAsyncThreadPoolSize() {
        return asyncThreadPoolSize;
    }

    public void setAsyncThreadPoolSize(int asyncThreadPoolSize) {
        this.asyncThreadPoolSize = asyncThreadPoolSize;
    }
}
