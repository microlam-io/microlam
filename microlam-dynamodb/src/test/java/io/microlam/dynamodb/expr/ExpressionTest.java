package io.microlam.dynamodb.expr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.microlam.dynamodb.AttributeNameGenerator;
import io.microlam.dynamodb.SimpleAttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class ExpressionTest {

	@Test
	public void testExpression1() {
		Map<String,AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		Map<String,String> attributeNames = new HashMap<String, String>();
		AttributeNameGenerator attributeNameGenerator = new SimpleAttributeNameGenerator("n");	
		Expression expr  = new Comparison(new AttributePath("type"), ComparatorOperator.EQUAL, new StringValue("test"));
		String expression = expr.process(attributeValues, attributeNames, attributeNameGenerator);
		assertEquals("#n1 = :n2", expression);
		assertFalse(attributeValues.isEmpty());
		assertFalse(attributeNames.isEmpty());
		assertEquals(attributeValues.size(), 1);
		assertEquals(attributeNames.size(), 1);
		assertTrue(attributeValues.containsKey(":n2"));
		assertTrue(attributeNames.containsKey("#n1"));
		assertEquals(attributeValues.get(":n2").s(), "test");
		assertEquals(attributeNames.get("#n1"), "type");
	}
	
	@Test
	public void testExpression2() {
		Map<String,AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		Map<String,String> attributeNames = new HashMap<String, String>();
		AttributeNameGenerator attributeNameGenerator = new SimpleAttributeNameGenerator("n");	
		Comparison exprL  = new Comparison(new AttributePath("type"), ComparatorOperator.EQUAL, new StringValue("test"));
		BeginsWith exprR  = new BeginsWith(new AttributePath("value.name"), new StringValue("test2"));
		Expression expr = new Or(exprL, exprR);
		String expression = expr.process(attributeValues, attributeNames, attributeNameGenerator);
		//System.out.println(expression);
		assertEquals("(#n1 = :n2) OR begins_with(#n3.#n4, :n5)", expression);
	}
	
	@Test
	public void testExpression3() {
		Map<String,AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		Map<String,String> attributeNames = new HashMap<String, String>();
		AttributeNameGenerator attributeNameGenerator = new SimpleAttributeNameGenerator("n");	
		Minus expr  = new Minus(new AttributePath("type"), new Minus(new NumberValue(5), new NumberValue(3)));
		String expression = expr.process(attributeValues, attributeNames, attributeNameGenerator);
		//System.out.println(expression);
		assertEquals("#n1 - (:n2 - :n3)", expression);
	}
	
	@Test
	public void testExpression4() {
		Map<String,AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		Map<String,String> attributeNames = new HashMap<String, String>();
		AttributeNameGenerator attributeNameGenerator = new SimpleAttributeNameGenerator("n");	
		Plus expr  = new Plus(new AttributePath("type"), new NumberValue(5), new NumberValue(3));
		String expression = expr.process(attributeValues, attributeNames, attributeNameGenerator);
		//System.out.println(expression);
		assertEquals("#n1 + :n2 + :n3", expression);
	}

	@Test
	public void testExpression5() {
		Map<String,AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		Map<String,String> attributeNames = new HashMap<String, String>();
		AttributeNameGenerator attributeNameGenerator = new SimpleAttributeNameGenerator("n");	
		SetAction expr  = new SetAction(new AttributePath("type"),  new Plus(new NumberValue(5), new NumberValue(3)));;
		String expression = expr.process(attributeValues, attributeNames, attributeNameGenerator);
		//System.out.println(expression);
		assertEquals("#n1 = (:n2 + :n3)", expression);
	}

}
