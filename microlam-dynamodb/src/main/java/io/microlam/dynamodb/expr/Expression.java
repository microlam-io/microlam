package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public interface Expression {

	public String process(Map<String,AttributeValue> attributeValues, Map<String,String> attributeNames, AttributeNameGenerator attributeNameGenerator);
	
	
}
