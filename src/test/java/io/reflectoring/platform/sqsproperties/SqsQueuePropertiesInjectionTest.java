package io.reflectoring.platform.sqsproperties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        // queue 1
        "SQS_MY_QUEUE_ARN=this-is-an-arn",
        "SQS_MY_QUEUE_URL=https://sqs.eu-central-1.amazonaws.com/123456789012/very-complicated-queue-name",
        "SQS_MY_QUEUE_NAME=very-complicated-queue-name",
        "SQS_MY_QUEUE_REGION=eu-central-1",

        // queue 2
        "SQS_MY_OTHER_QUEUE_ARN=this-is-another-arn",
        "SQS_MY_OTHER_QUEUE_URL=https://sqs.us-east-1.amazonaws.com/123456789012/very-complicated-other-queue-name",
        "SQS_MY_OTHER_QUEUE_NAME=very-complicated-other-queue-name",
        "SQS_MY_OTHER_QUEUE_REGION=us-east-1"
})
public class SqsQueuePropertiesInjectionTest {

    @Autowired(required = false)
    @QueueProperties("MY_QUEUE")
    private SqsQueueProperties myQueueProperties;

    @Autowired(required = false)
    @QueueProperties("MY_OTHER_QUEUE")
    private SqsQueueProperties myOtherQueueProperties;

    @Test
    void injectsProperties(){
        assertThat(myQueueProperties).isEqualTo(new SqsQueueProperties("MY_QUEUE",
                "this-is-an-arn",
                "https://sqs.eu-central-1.amazonaws.com/123456789012/very-complicated-queue-name",
                "very-complicated-queue-name",
                "eu-central-1"));

        assertThat(myOtherQueueProperties).isEqualTo(new SqsQueueProperties("MY_OTHER_QUEUE",
                "this-is-another-arn",
                "https://sqs.us-east-1.amazonaws.com/123456789012/very-complicated-other-queue-name",
                "very-complicated-other-queue-name",
                "us-east-1"));
    }

}
