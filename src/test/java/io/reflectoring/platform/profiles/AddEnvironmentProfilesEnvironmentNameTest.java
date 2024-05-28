package io.reflectoring.platform.profiles;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "ENV_NAME=dev-east"
})
class AddEnvironmentProfilesEnvironmentNameTest {

    @Autowired
    private Environment environment;

    @Test
    void activatesEnvironmentNameProfile() {

        assertThat(environment.getActiveProfiles())
                .contains("dev-east");

        assertThat(environment.getProperty("profile-specific-property"))
                .isEqualTo("dev-east");

    }

}
