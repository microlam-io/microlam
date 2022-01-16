package io.microlam.json;

public class JsonMappingPart {

	public final String name;
	public final String[] parameters;
	
	public JsonMappingPart(String name, String[] parameters) {
		this.name = name;
		this.parameters = parameters;
	}

	public static JsonMappingPart parse(String specs) {
		int indexDieze = specs.indexOf('#');
		if (indexDieze < 0) {
			return new JsonMappingPart(specs, null);
		}
		return new JsonMappingPart(specs.substring(0,indexDieze), specs.substring(indexDieze+1).split("#"));
	}


}
