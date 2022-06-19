package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class And extends AbstractExpression implements ConditionExpression {

	final ConditionExpression[] expressions;

	public And(ConditionExpression[] expressions) {
		this.expressions = expressions;
	}

	public And(ConditionExpression first,  ConditionExpression next,  ConditionExpression... others) {
		this.expressions = new ConditionExpression[2+others.length];
		this.expressions[0] = first;
		this.expressions[1] = next;
		System.arraycopy(others, 0, this.expressions, 2, others.length);
	}
			
	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		StringBuffer expr = new StringBuffer();
		for(ConditionExpression expression :  expressions) {
			String expressionE = operand(expression, attributeValues, attributeNames, attributeNameGenerator);
			if (expr.length() != 0) {
				expr.append(" AND ");
			}
			expr.append(expressionE);
		}
		return expr.toString();
	}

	
}
