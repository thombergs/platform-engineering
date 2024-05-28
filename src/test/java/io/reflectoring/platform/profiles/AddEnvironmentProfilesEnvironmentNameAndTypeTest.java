package io.reflectoring.platform.profiles;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "ENV_NAME=dev-east",
        "ENV_TYPE=dev"
})
class AddEnvironmentProfilesEnvironmentNameAndTypeTest {

    @Autowired
    private Environment environment;

    @Test
    void activatesEnvironmentTypeProfile() {

        assertThat(environment.getActiveProfiles())
                .contains("dev-east")
                .contains("dev");

        // The environment name "dev-east" is more specific than the environment type "dev"
        // so it should take precedence.
        assertThat(environment.getProperty("profile-specific-property"))
                .isEqualTo("dev-east");

    }

}
