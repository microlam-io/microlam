package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class ListValue implements Operand, ConstantValue {

	final protected AttributeValue[] values;
	
	public ListValue(AttributeValue[] values) {
		this.values = values;
	}
	
	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String name = attributeNameGenerator.generateNewAttributeNameValue();
		attributeValues.put(name, AttributeValue.builder().l(values).build());
		return name;
	}

}
