package io.reflectoring.platform.profiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Optional;

public class AddEnvironmentProfiles implements EnvironmentPostProcessor, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(AddEnvironmentProfiles.class);

    @Override
    public void postProcessEnvironment(
            ConfigurableEnvironment environment,
            SpringApplication application) {

        Optional<String> environmentName = extractEnvironmentName(environment); // example: "dev-east"
        Optional<String> environmentType = extractEnvironmentType(environment); // example: "dev"
        Optional<String> workerGroup = extractWorkerGroup(environment); // example: "queue-processor"

        logger.info("Detected environment {}, environment type {}, and worker group {}",
                environmentName,
                environmentType,
                workerGroup);

        // The order of activating profiles is important! It should go from least specific to most specific.
        // The last one wins.
        environmentType.ifPresent(environment::addActiveProfile);
        environmentName.ifPresent(environment::addActiveProfile);
        workerGroup.ifPresent(environment::addActiveProfile);
    }

    private Optional<String> extractEnvironmentName(ConfigurableEnvironment environment) {
        return Optional.ofNullable(environment.getProperty("ENV_NAME"));
    }

    private Optional<String> extractEnvironmentType(ConfigurableEnvironment environment) {
        return Optional.ofNullable(environment.getProperty("ENV_TYPE"));
    }

    private Optional<String> extractWorkerGroup(ConfigurableEnvironment environment) {
        return Optional.ofNullable(environment.getProperty("WORKER_GROUP"));
    }

    @Override
    public int getOrder() {
        // This processor needs to run before all the other EnvironmentPostProcessors so that the
        // activated profiles have an effect on the other processors.
        return ConfigDataEnvironmentPostProcessor.ORDER - 1;
    }
}
