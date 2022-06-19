package io.microlam.dynamodb.pipeline.basic;

import java.util.List;
import java.util.Map;

import io.microlam.dynamodb.pipeline.GenericResponse;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

public class QueryGenericResponse implements GenericResponse {

	QueryResponse queryResponse;
	
	QueryGenericResponse(QueryResponse queryResponse) {
		this.queryResponse = queryResponse;
	}

	@Override
	public Map<String, AttributeValue> lastEvaluatedKey() {
		return queryResponse.lastEvaluatedKey();
	}

	@Override
	public List<Map<String, AttributeValue>> items() {
		return queryResponse.items();
	}
	
	@Override
	public int count() {
		return queryResponse.count();
	}
}
