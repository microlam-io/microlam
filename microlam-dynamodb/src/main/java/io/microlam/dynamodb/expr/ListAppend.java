package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class ListAppend implements Operand {

	Operand op1;
	Operand op2;
	
	public ListAppend(Operand  op1, Operand op2) {
		this.op1 = op1;
		this.op2 = op2;
	}
	
	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		String op1E = op1.process(attributeValues, attributeNames, attributeNameGenerator);
		String op2E = op2.process(attributeValues, attributeNames, attributeNameGenerator);
		StringBuffer expr = new StringBuffer();
		expr.append("list_append(").append(op1E).append(", ").append(op2E).append(")");
		return expr.toString();
	}

}
