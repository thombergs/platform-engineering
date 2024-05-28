package io.reflectoring.platform.sqsproperties;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@AutoConfiguration
public class SqsQueuePropertiesAutoConfiguration {

    @Bean
    SqsQueuePropertiesRegistrar queuePropertiesRegistrar(Environment environment) {
        return new SqsQueuePropertiesRegistrar(environment);
    }

}
