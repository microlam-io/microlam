package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class AttributeTypeFunction implements ConditionExpression, Function {

	final AttributePath path;
	final AttributeType type;
	
	public AttributeTypeFunction(AttributePath path, AttributeType type) {
		this.path = path;
		this.type = type;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String pathE = path.process(attributeValues, attributeNames, attributeNameGenerator);
		String typeE = type.process(attributeValues, attributeNames, attributeNameGenerator);
		StringBuffer expr = new StringBuffer();
		expr.append("attribute_type(").append(pathE).append(", ").append(typeE).append(")");
		return expr.toString();
	}

}
