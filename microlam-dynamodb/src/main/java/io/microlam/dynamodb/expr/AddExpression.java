package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class AddExpression implements UpdateExpression {
	
	AddIncrementExpression[] addIncrementExpressions;
	
	public AddExpression(AddIncrementExpression...  addIncrementExpressions) {
		this.addIncrementExpressions = addIncrementExpressions;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		StringBuffer expr = new StringBuffer();
		expr.append("ADD ");
		boolean notFirst = false;
		for(AddIncrementExpression addIncrementExpression: addIncrementExpressions) {
			if (notFirst) {
				expr.append(", ");
			}
			String exprE = addIncrementExpression.process(attributeValues, attributeNames, attributeNameGenerator);
			expr.append(exprE);
			notFirst = true;
		}
		return expr.toString();
	}

}
