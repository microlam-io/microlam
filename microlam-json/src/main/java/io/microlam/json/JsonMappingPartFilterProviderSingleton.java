package io.microlam.json;

public class JsonMappingPartFilterProviderSingleton implements JsonMappingPartFilterProvider {

	public  final JsonPartFilter jsonPartFilter;
	
	public JsonMappingPartFilterProviderSingleton(JsonPartFilter jsonPartFilter) {
		this.jsonPartFilter = jsonPartFilter;
	}

	@Override
	public JsonPartFilter provide(JsonMappingPart jsonMappingPart) {
		return jsonPartFilter;
	}

}

