package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class NullValue implements Operand, ConstantValue {

	
	public final static NullValue instance = new NullValue();
	
	protected NullValue() {
	}
	
	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String name = attributeNameGenerator.generateNewAttributeNameValue();
		attributeValues.put(name, AttributeValue.builder().nul(Boolean.TRUE).build());
		return name;
	}

}
