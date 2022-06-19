package io.microlam.dynamodb;

public interface AttributeNameGenerator {

	String generateNewAttributeName();
	
	String generateNewAttributeNameValue();

}
