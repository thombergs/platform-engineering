package io.reflectoring.platform.sqsproperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class SqsQueuePropertiesExtractor {

    static final Pattern GROUPING_PATTERN = Pattern.compile("SQS_(.*)_NAME");
    static final String PROPERTY_SQS_QUEUE_ARN = "SQS_%s_ARN";
    static final String PROPERTY_SQS_QUEUE_URL = "SQS_%s_URL";
    static final String PROPERTY_SQS_QUEUE_NAME = "SQS_%s_NAME";
    static final String PROPERTY_SQS_QUEUE_REGION = "SQS_%s_REGION";

    /**
     * Extracts a list of SqsQueueProperties objects from a map of properties (which contains environment
     * variables, among other configuration properties).
     */
    public List<SqsQueueProperties> extract(Map<String, String> properties) {

        Map<String, Map<String, String>> propertiesByResource = new HashMap<>();

        properties.entrySet().stream()
                // map env var name to resource name (%s in the patterns above) or null
                .map(SqsQueuePropertiesExtractor::getResourceNameFromEnvVar)
                // filter out null elements, so we only keep the resource names
                .filter(Objects::nonNull)
                // for each resource, collect all properties into the propertiesByResource map
                .forEach(resourceName -> {
                    Map<String, String> resourceProperties = extractPropertiesForResource(resourceName, properties);
                    propertiesByResource.put(resourceName, resourceProperties);
                });

        return propertiesByResource.entrySet().stream()
                .map(entry -> createSqsQueueProperties(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private static Map<String, String> extractPropertiesForResource(String resourceName, Map<String, String> properties){
        return properties.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(String.format("SQS_%s_", resourceName)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static SqsQueueProperties createSqsQueueProperties(String resourceName, Map<String, String> properties) {
        return new SqsQueueProperties(
                resourceName,
                properties.get(String.format(PROPERTY_SQS_QUEUE_ARN, resourceName)),
                properties.get(String.format(PROPERTY_SQS_QUEUE_URL, resourceName)),
                properties.get(String.format(PROPERTY_SQS_QUEUE_NAME, resourceName)),
                properties.get(String.format(PROPERTY_SQS_QUEUE_REGION, resourceName)));
    }

    private static String getResourceNameFromEnvVar(Map.Entry<String, String> entry) {
        Matcher matcher = GROUPING_PATTERN.matcher(entry.getKey());
        return matcher.matches() ? matcher.group(1) : null;
    }
}
