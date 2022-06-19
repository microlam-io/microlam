package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class BinaryValue implements Operand, ConstantValue {

	final protected SdkBytes value;
	
	public BinaryValue(byte[] value) {
		this.value = SdkBytes.fromByteArray(value);
	}
	
	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String name = attributeNameGenerator.generateNewAttributeNameValue();
		attributeValues.put(name, AttributeValue.builder().b(value).build());
		return name;
	}

}
