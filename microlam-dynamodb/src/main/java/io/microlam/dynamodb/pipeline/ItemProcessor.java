package io.microlam.dynamodb.pipeline;

import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public interface ItemProcessor {

	public void process(String requestName, Map<String, AttributeValue> item);
	
}
