package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class SetRemoveAddDeleteExpression implements UpdateExpression {
	
	SetExpression setExpression;
	RemoveExpression removeExpression;
	AddExpression addExpression;
	DeleteExpression deleteExpression;
	
	public SetRemoveAddDeleteExpression(SetExpression setExpression, RemoveExpression removeExpression, AddExpression addExpression, DeleteExpression deleteExpression) {
		this.setExpression = setExpression;
		this.removeExpression = removeExpression;
		this.addExpression = addExpression;
		this.deleteExpression = deleteExpression;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		StringBuffer expr = new StringBuffer();
		for(UpdateExpression updateExpression: new UpdateExpression[] {setExpression, removeExpression, addExpression, deleteExpression}) {
			if (expr.length() != 0) {
				expr.append(" ");
			}
			if (updateExpression != null) {
				String updateExpressionE = updateExpression.process(attributeValues, attributeNames, attributeNameGenerator);
				expr.append(updateExpressionE);
			}
		}
		return expr.toString();
	}

}
