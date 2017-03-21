package com.ziffit.autoconfigure.database.flyway;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class CleaningFlywayMigrationStrategy {

    private static final Logger logger = LogManager.getLogger();

    private FlywayConfigurationProperties flywayProperties;

    public CleaningFlywayMigrationStrategy(FlywayConfigurationProperties flywayProperties) {
        this.flywayProperties = flywayProperties;
    }

    @Bean
    public FlywayMigrationStrategy createCleaningFlywayMigrationStrategy() {
        return flyway -> {
            if (flywayProperties.isCleanEnabled()) {
                logger.debug("Cleaning database...");
                flyway.clean();
                logger.debug("Database objects have been dropped.");
            }
            logger.debug("Beginning Flyway migration...");
            flyway.migrate();
            logger.debug("Flyway migration completed.");
        };
    }
}
