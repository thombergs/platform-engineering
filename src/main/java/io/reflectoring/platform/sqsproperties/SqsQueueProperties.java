package io.reflectoring.platform.sqsproperties;

public record SqsQueueProperties(
        String resourceName,
        String arn,
        String url,
        String queueName,
        String region
) {
}