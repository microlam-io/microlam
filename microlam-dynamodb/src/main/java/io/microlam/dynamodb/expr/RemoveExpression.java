package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class RemoveExpression implements UpdateExpression {
	
	AttributePath[] attributePaths;
	
	public RemoveExpression(AttributePath...  attributePaths) {
		this.attributePaths = attributePaths;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		StringBuffer expr = new StringBuffer();
		expr.append("REMOVE ");
		boolean notFirst = false;
		for(AttributePath attributePath: attributePaths) {
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
