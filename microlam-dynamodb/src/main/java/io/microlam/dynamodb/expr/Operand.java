package io.microlam.dynamodb.expr;

import java.util.Map;

import io.microlam.dynamodb.DynamoDBHelper;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public interface Operand extends Expression {

	
	public static AttributePath attributePath(String attributePath) {
		return new AttributePath(attributePath);
	}

	public static GenericValue genericValue(AttributeValue value) {
		return new GenericValue(value);
	}

	public static GenericValue value(Object value) {
		return new GenericValue(DynamoDBHelper.convert(value));
	}

	public static BooleanValue booleanValue(boolean boolValue) {
		return new BooleanValue(boolValue);
	}

	public static NumberValue numberValue(Number numberValue) {
		return new NumberValue(numberValue);
	}

	public static StringValue stringValue(String stringValue) {
		return new StringValue(stringValue);
	}

	public static BinaryValue binaryValue(byte[] binaryValue) {
		return new BinaryValue(binaryValue);
	}

	public static NullValue nullValue() {
		return NullValue.instance;
	}
		
	public static ListValue binaryValue(AttributeValue[] values) {
		return new ListValue(values);
	}

	public static MapValue mapValue(Map<String,AttributeValue> value) {
		return new MapValue(value);
	}
	
	public static StringSetValue stringSetValue(String... stringValues) {
		return new StringSetValue(stringValues);
	}

	public static NumberSetValue numberSetValue(String... numberSetValues) {
		return new NumberSetValue(numberSetValues);
	}

	public static BinarySetValue binarySetValue(byte[]... binarySetValues) {
		return new BinarySetValue(binarySetValues);
	}

	public static Plus plus(Operand operand1, Operand operand2, Operand... operandOthers) {
		return new Plus(operand1, operand2, operandOthers);
	}

	public static Minus minus(Operand operand1, Operand operand2) {
		return new Minus(operand1, operand2);
	}

	public static Size size(String attributePath) {
		return new Size(Operand.attributePath(attributePath));
	}

	public static Operand ifNotExists(String attributePath, Operand operand) {
		return new IfNotExists(Operand.attributePath(attributePath), operand);
	}

	public static Operand listAppend(Operand operand1, Operand operand2) {
		return new ListAppend(operand1, operand2);
	}
}
