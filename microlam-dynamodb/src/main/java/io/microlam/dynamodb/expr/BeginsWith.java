package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class BeginsWith implements ConditionExpression, Function {

	AttributePath attributePath;
	StringValue substr;
	
	public BeginsWith(AttributePath attributePath, StringValue substr) {
		this.attributePath = attributePath;
		this.substr = substr;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String pathE = attributePath.process(attributeValues, attributeNames, attributeNameGenerator);
		String substrE = substr.process(attributeValues, attributeNames, attributeNameGenerator);
		StringBuffer expr = new StringBuffer();
		expr.append("begins_with(").append(pathE).append(", ").append(substrE).append(")");
		return expr.toString();
	}

}
