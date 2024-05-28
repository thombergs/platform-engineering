package io.reflectoring.platform.sqsproperties;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class SqsQueuePropertiesAutoConfigurationTest {

    ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(SqsQueuePropertiesAutoConfiguration.class));

    @Test
    void extractsQueueProperties() {
        runner.withPropertyValues(
                        // queue 1
                        "SQS_MY_QUEUE_ARN=this-is-an-arn",
                        "SQS_MY_QUEUE_URL=https://sqs.eu-central-1.amazonaws.com/123456789012/very-complicated-queue-name",
                        "SQS_MY_QUEUE_NAME=very-complicated-queue-name",
                        "SQS_MY_QUEUE_REGION=eu-central-1",

                        // queue 2
                        "SQS_MY_OTHER_QUEUE_ARN=this-is-another-arn",
                        "SQS_MY_OTHER_QUEUE_URL=https://sqs.us-east-1.amazonaws.com/123456789012/very-complicated-other-queue-name",
                        "SQS_MY_OTHER_QUEUE_NAME=very-complicated-other-queue-name",
                        "SQS_MY_OTHER_QUEUE_REGION=us-east-1")
                .run(context -> {

                    Collection<SqsQueueProperties> propertiesList = context.getBeansOfType(SqsQueueProperties.class).values();

                    assertThat(propertiesList).hasSize(2);
                    assertThat(propertiesList).contains(
                            new SqsQueueProperties("MY_QUEUE",
                                    "this-is-an-arn",
                                    "https://sqs.eu-central-1.amazonaws.com/123456789012/very-complicated-queue-name",
                                    "very-complicated-queue-name",
                                    "eu-central-1"),
                            new SqsQueueProperties("MY_OTHER_QUEUE",
                                    "this-is-another-arn",
                                    "https://sqs.us-east-1.amazonaws.com/123456789012/very-complicated-other-queue-name",
                                    "very-complicated-other-queue-name",
                                    "us-east-1")
                    );
                });
    }

}
