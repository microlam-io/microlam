package io.microlam.json;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import jakarta.json.JsonValue.ValueType;

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


	public JsonValue get(String jsonObject) {
		JsonValue json = Json.createParser(new StringReader(jsonObject)).getValue();
	    return get(json);
	}
	
	public JsonValue get(JsonValue jsonObject) {
		JsonValue current = jsonObject;
		for(int i = 0; i<jsonPaths.length; i++) {
			JsonPath jsonPath = jsonPaths[i];
			current = jsonPath.get(current);
			if (current == null) {
				return null;
			}
			if (i != jsonPaths.length-1) {
				if  ((current.getValueType() != ValueType.ARRAY) && (current.getValueType() != ValueType.OBJECT)){
					if (current.getValueType() == ValueType.STRING) {
						JsonString currentPrimitive = (JsonString) current;
						current = Json.createParser(new StringReader(currentPrimitive.getString())).getValue();
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
