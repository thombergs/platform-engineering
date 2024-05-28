package io.reflectoring.platform.translation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class TranslateEnvironmentVariables implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        String oldEnvVar = environment.getProperty("OLD_ENV_VAR");
        String newEnvVar = environment.getProperty("NEW_ENV_VAR");

        Map<String, Object> propertiesToAdd = new HashMap<>();
        if (newEnvVar != null) {
            propertiesToAdd.put("TRANSLATED_PROPERTY", newEnvVar);
        } else if (oldEnvVar != null) {
            propertiesToAdd.put("TRANSLATED_PROPERTY", oldEnvVar);
        }

        environment.getPropertySources().addFirst(
                new MapPropertySource("translatedProperties", propertiesToAdd));
    }
}
