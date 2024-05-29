package io.reflectoring.platform.flyway;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Endpoint(id = "flywayMigrate")
public class FlywayMigrateEndpoint {

    private final Flyway flyway;
    private static final Logger log = LoggerFactory.getLogger(FlywayMigrateEndpoint.class);

    @Autowired
    public FlywayMigrateEndpoint(Flyway flyway) {
        this.flyway = flyway;
    }

    @WriteOperation
    public ResponseEntity<String> migrate() {
        try {
            log.info("Flyway migrate starting...");
            // The migrate() method returns an int in older versions of Flyway and a MigrateResult object in newer versions.
            // To make this endpoint work with older and newer versions of Flyway, we're not processing the return value.
            flyway.migrate();
            log.info("Flyway migrate completed");
            return ResponseEntity.ok("Successfully executed Flyway migrate. Check the logs for details.");
        } catch (FlywayException e) {
            log.warn("Flyway migrate failed!", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Flyway migrate failed! Check the logs for details. Error message: %s", e.getMessage()));
        }
    }

}
