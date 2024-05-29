package io.reflectoring.platform.flyway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Configurable properties for Flyway.
 */
@ConfigurationProperties("flyway")
public class FlywayProperties {
    /**
     * Whether we want to execute Flyway scripts on startup. We discourage database migration during startup because
     * it couples the risk of database migrations with the risk of deployment. So, the default value is false.
     */
    private final boolean migrateOnStartup;

    public boolean isMigrateOnStartup() {
        return migrateOnStartup;
    }

    public FlywayProperties(@DefaultValue("false") boolean migrateOnStartup) {
        this.migrateOnStartup = migrateOnStartup;
    }
}
