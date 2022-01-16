package io.microlam.json;

import java.util.HashMap;
import java.util.Map;

public class JsonMappingPartFilterProviderRegistry implements JsonMappingPartFilterProvider  {

	protected Map<String, JsonMappingPartFilterProvider> map;
	
	public JsonMappingPartFilterProviderRegistry() {
		this.map = new HashMap<String, JsonMappingPartFilterProvider>();
	}

	public void register(String name, JsonMappingPartFilterProvider jsonMappingPartFilterProvider) {
		map.put(name, jsonMappingPartFilterProvider);
	}
	
	@Override
	public JsonPartFilter provide(JsonMappingPart jsonMappingPart) {
		JsonMappingPartFilterProvider filterProvider = map.get(jsonMappingPart.name);
		if  (filterProvider ==  null) {
			throw new RuntimeException("Cannot found JsonMappingPartFilterProvider for name = "  + jsonMappingPart.name);
		}
		return filterProvider.provide(jsonMappingPart);
	}

}
