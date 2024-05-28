package io.reflectoring.platform.profiles;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "ENV_NAME=dev-east",
        "ENV_TYPE=dev",
        "WORKER_GROUP=worker"
    })
class AddEnvironmentProfilesEnvironmentNameAndTypeAndWorkerGroupTest {

    @Autowired
    private Environment environment;

    @Test
    void activatesEnvironmentTypeProfile() {

        assertThat(environment.getActiveProfiles())
                .contains("dev-east")
                .contains("dev")
                .contains("worker");

        // The worker group "worker" is more specific than the environment name "dev-east" and the environment type "dev"
        // so it should take precedence.
        assertThat(environment.getProperty("profile-specific-property"))
                .isEqualTo("worker");

    }

}
