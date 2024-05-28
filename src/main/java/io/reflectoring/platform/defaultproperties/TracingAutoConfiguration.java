package io.reflectoring.platform.defaultproperties;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@AutoConfiguration
@PropertySource("classpath:/io/reflectoring/platform/defaultproperties/tracing.properties")
@PropertySource(value = "classpath:/io/reflectoring/platform/defaultproperties/tracing-${ENV_NAME}.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:/io/reflectoring/platform/defaultproperties/tracing-${ENV_TYPE}.properties", ignoreResourceNotFound = true)
public class TracingAutoConfiguration {
}
