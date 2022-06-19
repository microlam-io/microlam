package io.microlam.dynamodb.expr;

public enum ComparatorOperator {

	EQUAL(" = "), DIFFERENT(" <> "), INFERIOR_STRICT(" < "), INFERIOR_EQUAL(" <= "), SUPERIOR_STRICT(" > "), SUPERIOR_EQUAL(" >= "); 

	final String representation;
	
	private ComparatorOperator(String representation) {
		this.representation = representation;
	}
}
