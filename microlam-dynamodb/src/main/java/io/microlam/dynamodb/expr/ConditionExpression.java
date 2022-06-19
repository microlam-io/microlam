package io.microlam.dynamodb.expr;

public interface ConditionExpression extends Expression {
	
	public static And and(ConditionExpression conditionExpression1, ConditionExpression conditionExpression2, ConditionExpression... conditionExpressionOthers) {
		return new And(conditionExpression1, conditionExpression2, conditionExpressionOthers);
	}
	
	public static And and(ConditionExpression[] conditionExpressions) {
		return new And(conditionExpressions);
	}

	public static Or or(ConditionExpression conditionExpression1, ConditionExpression conditionExpression2, ConditionExpression... conditionExpressionOthers) {
		return new Or(conditionExpression1, conditionExpression2, conditionExpressionOthers);
	}
	
	public static Or or(ConditionExpression[] conditionExpressions) {
		return new Or(conditionExpressions);
	}	

	public static Not not(ConditionExpression conditionExpression) {
		return new Not(conditionExpression);
	}

	public static AttributeExists attributeExists(String attributePath) {
		return new AttributeExists(Operand.attributePath(attributePath));
	}

	public static AttributeNotExists attributeNotExists(String attributePath) {
		return new AttributeNotExists(Operand.attributePath(attributePath));
	}

	public static BeginsWith beginWith(String attributePath, String subStr) {
		return new BeginsWith(Operand.attributePath(attributePath), new StringValue(subStr));
	}

	public static AttributeTypeFunction attributeTypeFunction(String attributePath, AttributeType type) {
		return new AttributeTypeFunction(Operand.attributePath(attributePath), type);
	}

	public static Between between(Operand target, Operand from, Operand to) {
		return new Between(target, from, to);
	}

	public static Comparison comparison(Operand left, ComparatorOperator operator, Operand right) {
		return new Comparison(left, operator, right);
	}

	public static Contains contains(String attributePath, String value) {
		return new Contains(Operand.attributePath(attributePath), Operand.stringValue(value));
	}

	public static In in(Operand target, Operand... list) {
		return new In(target, list);
	}

}
