package io.reflectoring.platform.flyway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"server.port=8080", "debug=true"})
public class FlywayAutoConfigurationTest {

    private RestClient client = RestClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

    @Test
    void testFlywayMigrateEndpoint() {
        HttpStatusCode responseStatus = client.post()
                .uri("/actuator/flywayMigrate")
                .retrieve()
                .toBodilessEntity()
                .getStatusCode();

        assertThat(responseStatus).isEqualTo(HttpStatus.OK);
    }

}
