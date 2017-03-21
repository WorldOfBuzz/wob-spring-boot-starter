package com.ziffit.autoconfigure.database.flyway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("world-of-buzz.database.flyway")
public class FlywayConfigurationProperties {

    private boolean isCleanEnabled;

    public boolean isCleanEnabled() {
        return isCleanEnabled;
    }

    public void setIsCleanEnabled(boolean isCleanEnabled) {
        this.isCleanEnabled = isCleanEnabled;
    }
}
