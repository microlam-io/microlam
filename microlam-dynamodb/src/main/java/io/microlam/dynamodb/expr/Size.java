package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class Size implements Operand, Function {

	final AttributePath path;

	public Size(AttributePath path) {
		this.path = path;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String pathE = path.process(attributeValues, attributeNames, attributeNameGenerator);
		StringBuffer expr = new StringBuffer();
		expr.append("size(").append(pathE).append(")");
		return expr.toString();
	}
	
}
