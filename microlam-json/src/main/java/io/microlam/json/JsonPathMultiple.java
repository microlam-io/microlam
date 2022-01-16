package io.microlam.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JsonPathMultiple {
	
	JsonPath[] jsonPaths;

	public JsonPathMultiple(String jsonPaths, String separator) {
		String[] parts = jsonPaths.split(separator);
		this.jsonPaths = new JsonPath[parts.length];
		for(int i = 0; i<parts.length; i++) {
			this.jsonPaths[i] = new JsonPath(parts[i]);
		}
	}

	public JsonPathMultiple(String jsonPaths) {
		this(jsonPaths, "#");
	}


	public JsonElement get(String jsonObject) {
	    JsonElement json = new JsonBuilder().parseJson(jsonObject);
	    return get(json);
	}
	
	public JsonElement get(JsonElement jsonObject) {
		JsonBuilder  jsonBuilder2 = new JsonBuilder();
		JsonElement current = jsonObject;
		for(int i = 0; i<jsonPaths.length; i++) {
			JsonPath jsonPath = jsonPaths[i];
			current = jsonPath.get(current);
			if (current == null) {
				return null;
			}
			if (i != jsonPaths.length-1) {
				if  (current.isJsonPrimitive()) {
					JsonPrimitive currentPrimitive  = current.getAsJsonPrimitive();
					if (currentPrimitive.isString()) {
						current = jsonBuilder2.parseJson(currentPrimitive.getAsString());
					}
					else {
						return null;
					}
				}
				else {
					return null;
				}
			}
		}
		return current;
	}
}
