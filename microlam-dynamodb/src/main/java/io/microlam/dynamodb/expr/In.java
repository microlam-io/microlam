package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class In implements ConditionExpression {

	final Operand target;
	final Operand[] list;

	public In(Operand target, Operand ...list) {
		this.target = target;
		this.list = list;
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		StringBuffer expr = new StringBuffer();
		String targetE = target.process(attributeValues, attributeNames, attributeNameGenerator);
		expr.append("(").append(targetE).append(")").append(" IN (");
		
		String listE;
		for(int i=0; i<list.length; i++)  {
			listE = list[i].process(attributeValues, attributeNames, attributeNameGenerator);
			if (i != 0) {
				expr.append(", ");
			}
			expr.append(listE);
		}
		expr.append(")");
		return expr.toString();
	}

}
