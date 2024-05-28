package io.reflectoring.platform.sqsproperties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AutowireCandidateQualifier;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class SqsQueuePropertiesRegistrar implements BeanDefinitionRegistryPostProcessor {

    private final SqsQueuePropertiesExtractor extractor = new SqsQueuePropertiesExtractor();
    private final Environment environment;

    public SqsQueuePropertiesRegistrar(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        List<SqsQueueProperties> queuePropertiesList = extractor.extract(toMap(environment));

        for (SqsQueueProperties queueProperties : queuePropertiesList) {
            String resourceName = queueProperties.resourceName();

            // Register each SqsQueueProperties object that was extracted from the env vars as a Spring bean.
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(SqsQueueProperties.class);
            beanDefinition.setInstanceSupplier((Supplier<SqsQueueProperties>) () -> queueProperties);
            beanDefinition.addQualifier(new AutowireCandidateQualifier(QueueProperties.class, queueProperties.resourceName()));
            registry.registerBeanDefinition("queue-" + resourceName, beanDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        //no action
    }

    private Map<String, String> toMap(Environment environment) {
        MutablePropertySources propertySources = ((ConfigurableEnvironment) environment).getPropertySources();

        List<String> propertyNames = propertySources.stream()
                .filter(EnumerablePropertySource.class::isInstance)
                .map(EnumerablePropertySource.class::cast)
                .map(EnumerablePropertySource::getPropertyNames)
                .flatMap(Arrays::stream)
                .toList();

        Map<String, String> properties = new HashMap<>();
        for (String propertyName : propertyNames) {
            String propertyValue = environment.getProperty(propertyName);
            if (propertyValue != null) {
                properties.put(propertyName, propertyValue);
            }
        }

        return properties;
    }

}
