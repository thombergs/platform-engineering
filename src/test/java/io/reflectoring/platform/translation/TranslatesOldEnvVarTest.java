package io.reflectoring.platform.translation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "OLD_ENV_VAR=important-value")
public class TranslatesOldEnvVarTest {

    @Autowired
    private Environment environment;

    @Test
    void translatesOldEnvVar() {
        assertThat(environment.getProperty("TRANSLATED_PROPERTY"))
                .isEqualTo("important-value");
    }

}
