package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class SetExpression implements UpdateExpression {
	
	AssignmentExpression[] assignmentExpressions;
	
	public SetExpression(AssignmentExpression...  assignmentExpressions) {
		this.assignmentExpressions = assignmentExpressions;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		StringBuffer expr = new StringBuffer();
		expr.append("SET ");
		boolean notFirst = false;
		for(AssignmentExpression assignmentExpression: assignmentExpressions) {
			if (notFirst) {
				expr.append(", ");
			}
			String exprE = assignmentExpression.process(attributeValues, attributeNames, attributeNameGenerator);
			expr.append(exprE);
			notFirst = true;
		}
		return expr.toString();
	}

}
