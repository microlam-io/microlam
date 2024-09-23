package io.microlam.json;

import com.google.gson.Gson;
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
		Gson gson = new Gson();
		JsonElement json = gson.fromJson(jsonObject, JsonElement.class);
	    return get(json);
	}
	
	public JsonElement get(JsonElement jsonObject) {
		JsonElement current = jsonObject;
		for(int i = 0; i<jsonPaths.length; i++) {
			JsonPath jsonPath = jsonPaths[i];
			current = jsonPath.get(current);
			if (current == null) {
				return null;
			}
			if (i != jsonPaths.length-1) {
				if  ((! current.isJsonArray()) && (! current.isJsonObject())){
					if (current.isJsonPrimitive() && current.getAsJsonPrimitive().isString()) {
						JsonPrimitive currentPrimitive = (JsonPrimitive) current;
						current = currentPrimitive;
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
