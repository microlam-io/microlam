package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class AttributeNotExists implements ConditionExpression, Function {

	AttributePath path;

	public AttributeNotExists(AttributePath path) {
		this.path = path;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String pathE = path.process(attributeValues, attributeNames, attributeNameGenerator);
		StringBuffer expr = new StringBuffer();
		expr.append("attribute_not_exists(").append(pathE).append(")");
		return expr.toString();
	}

}
