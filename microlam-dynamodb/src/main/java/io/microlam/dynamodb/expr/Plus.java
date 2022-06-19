package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class Plus extends AbstractExpression implements Operand, Operator, Value {

	final Operand[] operands;

	public Plus(Operand[] operands) {
		this.operands = operands;
	}
	
	public Plus(Operand first,  Operand next,  Operand... others) {
		this.operands = new Operand[2+others.length];
		this.operands[0] = first;
		this.operands[1] = next;
		System.arraycopy(others, 0, this.operands, 2, others.length);
	}

	@Override
	public String process(Map<String, AttributeValue> attributeValues, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		StringBuffer expr = new StringBuffer();
		for(Operand expression :  operands) {
			String expressionE = operand(expression, attributeValues, attributeNames, attributeNameGenerator);
			if (expr.length() != 0) {
				expr.append(" + ");
			}
			expr.append(expressionE);
		}
		return expr.toString();
	}

}
