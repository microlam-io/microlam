package io.microlam.json;

import java.util.HashMap;
import java.util.Map;

public class JsonFilterRegistry implements JsonFilterResolver {

	protected Map<String,JsonFilter> registry;
	
	public JsonFilterRegistry() {
		registry = new HashMap<String, JsonFilter>();
	}
	
	public void registerJsonFilter(String name, JsonFilter jsonFilter) {
		registry.put(name, jsonFilter);
	}
	
	public JsonFilter getJsonFilter(String name) {
		return  registry.get(name);
	}
}
