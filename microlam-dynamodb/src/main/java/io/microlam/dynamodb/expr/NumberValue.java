package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class NumberValue implements Operand, ConstantValue {

	final protected Number value;
	
	public NumberValue(Number value) {
		this.value = value;
	}
	
	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String name = attributeNameGenerator.generateNewAttributeNameValue();
		attributeValues.put(name, AttributeValue.builder().n(value.toString()).build());
		return name;
	}

}
