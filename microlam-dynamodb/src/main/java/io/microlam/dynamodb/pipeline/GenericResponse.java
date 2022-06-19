package io.microlam.dynamodb.pipeline;

import java.util.List;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public interface GenericResponse {

	Map<String, AttributeValue> lastEvaluatedKey();
	
	List<Map<String, AttributeValue>> items();
	
	public int count();
}
