package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import io.microlam.dynamodb.DynamoDBHelper;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class AttributePath implements Operand {

	final String attributePath;

	public AttributePath(String attributePath) {
		this.attributePath = attributePath;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String  newName =  DynamoDBHelper.verifyReservedName(attributePath,  attributeNames, attributeNameGenerator);
		return newName.toString();
	}

}
