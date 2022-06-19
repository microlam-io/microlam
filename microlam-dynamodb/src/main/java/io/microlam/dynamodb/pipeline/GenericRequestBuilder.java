package io.microlam.dynamodb.pipeline;

import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public interface GenericRequestBuilder {
	
	String name();

	GenericRequestBuilder exclusiveStartKey(Map<String,AttributeValue> lastkey);
	
	GenericRequest build();
	
	String[] lastKeyAttributes();
	
	GenericRequestBuilder count();
	
}
