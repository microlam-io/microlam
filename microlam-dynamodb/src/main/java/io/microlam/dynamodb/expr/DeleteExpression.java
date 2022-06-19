package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class DeleteExpression implements UpdateExpression {
	
	DeleteValueExpression[] attributePaths;
	
	public DeleteExpression(DeleteValueExpression...  attributePaths) {
		this.attributePaths = attributePaths;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		StringBuffer expr = new StringBuffer();
		expr.append("DELETE ");
		boolean notFirst = false;
		for(DeleteValueExpression attributePath: attributePaths) {
			if (notFirst) {
				expr.append(", ");
			}
			String exprE = attributePath.process(attributeValues, attributeNames, attributeNameGenerator);
			expr.append(exprE);
			notFirst = true;
		}
		return expr.toString();
	}

}
