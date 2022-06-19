package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public abstract class AbstractExpression implements Expression  {

	public abstract String process(Map<String,AttributeValue> attributeValues, Map<String,String> attributeNames, AttributeNameGenerator attributeNameGenerator);

	String operand(Expression expression, Map<String,AttributeValue> attributeValues, Map<String,String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String exprE = expression.process(attributeValues, attributeNames, attributeNameGenerator);
		StringBuffer expr;
		if  ((expression instanceof Function) || (expression instanceof ConstantValue) || (expression instanceof AttributePath)) {
			return exprE;
		}
		else {
			expr = new StringBuffer();
			expr.append("(").append(exprE).append(")");
		}
		return expr.toString();
	}
}
