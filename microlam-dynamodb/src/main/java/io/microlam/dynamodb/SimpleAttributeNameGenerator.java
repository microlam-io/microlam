package io.microlam.dynamodb;

public class SimpleAttributeNameGenerator implements AttributeNameGenerator {

	final protected String prefix;
	protected int i = 0;
	
	public SimpleAttributeNameGenerator(String prefix) {
		this.prefix = prefix;
	}
	
	@Override
	public String generateNewAttributeName() {
		i++;
		String name = "#" + prefix + i;
		return name;
	}

	@Override
	public String generateNewAttributeNameValue() {
		i++;
		String name = ":" + prefix + i;
		return name;
	}

}
