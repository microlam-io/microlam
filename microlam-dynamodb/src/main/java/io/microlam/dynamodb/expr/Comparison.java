package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class Comparison extends AbstractExpression implements ConditionExpression {

	final Operand  leftOperand;
	final ComparatorOperator operator;
	final Operand  rightOperand;
	
	public Comparison(Operand leftOperand, ComparatorOperator operator, Operand rightOperand) {
		this.leftOperand = leftOperand;
		this.operator = operator;
		this.rightOperand = rightOperand;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String left = leftOperand.process(attributeValues, attributeNames, attributeNameGenerator);
		String right = rightOperand.process(attributeValues, attributeNames, attributeNameGenerator);
		return left + operator.representation + right;
	}
	
}
