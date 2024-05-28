package io.reflectoring.platform.translation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "NEW_ENV_VAR=important-value")
public class TranslatesNewEnvVarTest {

    @Autowired
    private Environment environment;

    @Test
    void translatesNewEnvVar() {
        assertThat(environment.getProperty("TRANSLATED_PROPERTY"))
                .isEqualTo("important-value");
    }

}
