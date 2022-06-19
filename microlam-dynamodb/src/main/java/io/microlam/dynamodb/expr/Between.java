package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class Between extends AbstractExpression implements ConditionExpression {

	final Operand target;
	final Operand from;
	final Operand to;
	
	public Between(Operand target, Operand from, Operand to) {
		this.target = target;
		this.from = from;
		this.to = to;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String targetE = operand(target, attributeValues, attributeNames, attributeNameGenerator);
		String fromE = operand(from, attributeValues, attributeNames, attributeNameGenerator);
		String toE = operand(to, attributeValues, attributeNames, attributeNameGenerator);
		StringBuffer expr = new StringBuffer();
		expr.append(targetE).append(" BETWEEN ").append(fromE).append(" AND ").append(toE);
		return expr.toString();
	}

}
