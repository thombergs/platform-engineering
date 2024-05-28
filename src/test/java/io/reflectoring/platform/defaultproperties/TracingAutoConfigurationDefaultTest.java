package io.reflectoring.platform.defaultproperties;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

public class TracingAutoConfigurationDefaultTest {

    ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(TracingAutoConfiguration.class));

    @Test
    void setsDefaultValue() {
        runner.run(context -> {
            Environment environment = context.getBean(Environment.class);
            assertThat(environment.getProperty("management.tracing.sampling.probability"))
                    .isEqualTo("0.1");
        });
    }

    @Test
    void setsLocalEnvironmentValue() {
        runner.withPropertyValues("ENV_NAME=local")
                .run(context -> {
                    Environment environment = context.getBean(Environment.class);
                    assertThat(environment.getProperty("management.tracing.sampling.probability"))
                            .isEqualTo("1.0");
                });
    }

}
