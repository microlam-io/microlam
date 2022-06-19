package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class Minus extends AbstractExpression implements Operand, Operator, Value {

	final Operand left;
	final Operand right;

	public Minus(Operand left, Operand right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		StringBuffer expr = new StringBuffer();
		String expressionLeft = operand(left, attributeValues, attributeNames, attributeNameGenerator);
		String expressionRight = operand(right, attributeValues, attributeNames, attributeNameGenerator);
		expr.append(expressionLeft);
		expr.append(" - ");
		expr.append(expressionRight);
		return expr.toString();
	}

}
