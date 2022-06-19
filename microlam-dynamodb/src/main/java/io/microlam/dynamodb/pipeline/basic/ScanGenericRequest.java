package io.microlam.dynamodb.pipeline.basic;

import io.microlam.dynamodb.pipeline.GenericRequest;
import io.microlam.dynamodb.pipeline.GenericResponse;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

public class ScanGenericRequest implements GenericRequest {
	
	ScanRequest scanRequest;

	ScanGenericRequest(ScanRequest scanRequest) {
		this.scanRequest = scanRequest; 
	}

	@Override
	public GenericResponse execute(DynamoDbClient client) {
		return new ScanGenericResponse(client.scan(scanRequest));
	}
}
