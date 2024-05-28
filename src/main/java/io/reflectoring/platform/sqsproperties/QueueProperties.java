package io.reflectoring.platform.sqsproperties;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.FIELD})
@Documented
@Qualifier
public @interface QueueProperties {

    /**
     * The name of the queue resource for which you want to inject the environment properties.
     */
    String value();

}

