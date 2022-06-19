package io.microlam.dynamodb.pipeline;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public interface GenericRequest {

	GenericResponse execute(DynamoDbClient client);

}
