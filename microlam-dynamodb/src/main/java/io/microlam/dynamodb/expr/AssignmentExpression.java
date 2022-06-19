package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class AssignmentExpression implements Expression {

	AttributePath path;
	Operand value;
	
	public AssignmentExpression(AttributePath path, Operand value) {
		this.path = path;
		this.value = value;
	}
	
	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String pathE = path.process(attributeValues, attributeNames, attributeNameGenerator);
		String valueE = value.process(attributeValues, attributeNames, attributeNameGenerator);
		StringBuffer expr = new StringBuffer();
		expr.append(pathE).append(" = ").append(valueE);
		return expr.toString();
	}

	public static AssignmentExpression create(String attributePath, Operand value) {
		return new AssignmentExpression(Operand.attributePath(attributePath), value);
	}
}
