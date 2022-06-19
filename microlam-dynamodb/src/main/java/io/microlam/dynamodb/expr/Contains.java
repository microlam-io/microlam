package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class Contains implements ConditionExpression {

	AttributePath attributePath;
	StringValue value;
	
	public Contains(AttributePath attributePath, StringValue value) {
		this.attributePath = attributePath;
		this.value = value;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String pathE = attributePath.process(attributeValues, attributeNames, attributeNameGenerator);
		String valueE = value.process(attributeValues, attributeNames, attributeNameGenerator);
		StringBuffer expr = new StringBuffer();
		expr.append("contains(").append(pathE).append(", ").append(valueE).append(")");
		return expr.toString();
	}

}
