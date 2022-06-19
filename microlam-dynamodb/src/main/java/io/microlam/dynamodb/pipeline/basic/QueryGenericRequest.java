package io.microlam.dynamodb.pipeline.basic;

import io.microlam.dynamodb.pipeline.GenericRequest;
import io.microlam.dynamodb.pipeline.GenericResponse;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;

public class QueryGenericRequest implements GenericRequest {

	QueryRequest queryRequest;
	
	QueryGenericRequest(QueryRequest queryRequest) {
		this.queryRequest = queryRequest;
	}

	@Override
	public GenericResponse execute(DynamoDbClient client) {
		return new QueryGenericResponse(client.query(queryRequest));
	}
}
