package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class Not extends AbstractExpression implements ConditionExpression {
	
	final protected ConditionExpression condition;

	public Not(ConditionExpression condition) {
		this.condition = condition;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String conditionE = operand(condition, attributeValues, attributeNames, attributeNameGenerator);
		StringBuffer expr = new StringBuffer();
		expr.append("NOT ").append(conditionE);
		return expr.toString();
	}
	
}
