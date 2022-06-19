package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class BinarySetValue implements Operand, ConstantValue {

	SdkBytes[] values;
	
	public BinarySetValue(byte[]... values) {
		this.values = new SdkBytes[values.length];
		for(int i=0; i<values.length; i++) {
			this.values[i] = SdkBytes.fromByteArray(values[i]);
		}
	}
	
	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String name = attributeNameGenerator.generateNewAttributeNameValue();
		attributeValues.put(name, AttributeValue.builder().bs(values).build());
		return name;
	}

}
