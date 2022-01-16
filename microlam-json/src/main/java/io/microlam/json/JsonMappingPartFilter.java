package io.microlam.json;

public abstract class JsonMappingPartFilter implements JsonPartFilter {

	public final JsonMappingPart jsonMappingPart;
	
	public JsonMappingPartFilter(JsonMappingPart jsonMappingPart) {
		this.jsonMappingPart = jsonMappingPart;
	}
	
}
