package io.reflectoring.platform.flyway;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(FlywayProperties.class)
@ConditionalOnBean(Flyway.class)
@AutoConfigureAfter(org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.class)
public class FlywayAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(FlywayAutoConfiguration.class);

    private final Flyway flyway;
    private final FlywayProperties flywayProperties;

    @Autowired
    FlywayAutoConfiguration(final Flyway flyway,
                            final FlywayProperties flywayProperties) {
        this.flyway = flyway;
        this.flywayProperties = flywayProperties;
    }

    @Bean
    public FlywayMigrateEndpoint flywayMigrateEndpoint() {
        return new FlywayMigrateEndpoint(flyway);
    }

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flywayToMigrate -> {
            if (flywayProperties.isMigrateOnStartup()) {
                flywayToMigrate.migrate();
            } else {
                log.info("Skipping Flyway migration on startup. Migrations can be run with a POST request to the /flywayMigrate actuator endpoint.");
            }
        };
    }

}
