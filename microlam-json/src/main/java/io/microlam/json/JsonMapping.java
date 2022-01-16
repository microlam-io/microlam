package io.microlam.json;

public class JsonMapping {

	public final String pathSource;
	public final String pathDestination;
	public final JsonMappingPart[] parts;

	public JsonMapping(String pathSource, JsonMappingPart[] parts, String pathDestination) {
		this.pathSource = pathSource;
		this.pathDestination = pathDestination;
		this.parts = parts;
	}

	public JsonMapping(String pathSource, String[] filters, String pathDestination) {
		this.pathSource = pathSource;
		this.pathDestination = pathDestination;
		this.parts = new JsonMappingPart[filters.length];
		
		for(int i=0;  i<filters.length; i++) {
			this.parts[i]  =  JsonMappingPart.parse(filters[i]);
		}
	}

	public static JsonMapping parse(String specs) {
		String[] parts = specs.split("\\:");
		if (parts.length == 1) {
			return new JsonMapping(parts[0], (JsonMappingPart[])null, parts[0]);
		}
		if  (parts.length == 2)  {
			return new JsonMapping(parts[0], (JsonMappingPart[])null,  parts[1]);
		}
		if  (parts.length == 3)  {
			return new JsonMapping(parts[0], parts[1].split(","), parts[2]);
		}
		throw new RuntimeException("JsonMapping specs [" + specs + "] should have from 1 to 3 parts separated by : but had "  + parts.length + " parts.");
	}
}
