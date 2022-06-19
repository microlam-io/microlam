package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class AddIncrementExpression implements Expression {

	AttributePath path;
	ConstantValue value;
	
	public AddIncrementExpression(AttributePath path, ConstantValue value) {
		this.path = path;
		this.value = value;
	}
	
	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String pathE = path.process(attributeValues, attributeNames, attributeNameGenerator);
		String valueE = value.process(attributeValues, attributeNames, attributeNameGenerator);
		StringBuffer expr = new StringBuffer();
		expr.append(pathE).append(" ").append(valueE);
		return expr.toString();
	}

	public static AddIncrementExpression create(String attributePath, ConstantValue value) {
		return new AddIncrementExpression(Operand.attributePath(attributePath), value);
	}
}
