package io.microlam.dynamodb.expr;

import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public interface UpdateExpression extends Expression {

	public static SetExpression setExpression(AssignmentExpression...assignmentExpressions) {
		return new SetExpression(assignmentExpressions);
	}

	public static SetExpression setExpression(Map<String,AttributeValue> values) {
		AssignmentExpression[] sets = new AssignmentExpression[values.size()];
		int i=0;
		for(Map.Entry<String,AttributeValue> entry: values.entrySet()) {
			sets[i] = assignmentExpression(entry.getKey(), Operand.genericValue(entry.getValue()));
			i++;
		}
		return new SetExpression(sets);
	}

	public static AddExpression addExpression(AddIncrementExpression...addIncrementExpressions) {
		return new AddExpression(addIncrementExpressions);
	}
	
	public static DeleteExpression deleteExpression(DeleteValueExpression...addIncrementExpressions) {
		return new DeleteExpression(addIncrementExpressions);
	}

	public static RemoveExpression removeExpression(String...attributePaths) {
		AttributePath[] attributePaths2 = new AttributePath[attributePaths.length];
		for(int i=0; i<attributePaths.length; i++) {
			attributePaths2[i] = new AttributePath(attributePaths[i]);
		}
		return new RemoveExpression(attributePaths2);
	}

	public static SetRemoveAddDeleteExpression setRemoveAddDeleteExpression(SetExpression setExpression, RemoveExpression removeExpression, AddExpression addExpression, DeleteExpression deleteExpression) {
		return new SetRemoveAddDeleteExpression(setExpression, removeExpression, addExpression, deleteExpression);
	}

	public static AssignmentExpression assignmentExpression(String attributePath, Operand value) {
		return AssignmentExpression.create(attributePath, value);
	}

	public static AddIncrementExpression addIncrementExpression(String attributePath, ConstantValue value) {
		return AddIncrementExpression.create(attributePath, value);
	}
	
	public static DeleteValueExpression deleteValueExpression(String attributePath, ConstantValue value) {
		return DeleteValueExpression.create(attributePath, value);
	}
	
	

}
