package io.microlam.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.unflattener.JsonUnflattener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class JsonConverter {

	protected static Gson gson = new GsonBuilder().create();

	public JsonElement convert(Object object) {
		return gson.toJsonTree(object);
	}
	
	public Object convert(JsonElement value) {
		if (value.isJsonNull()) {
			return null;
		}
		else if (value.isJsonPrimitive()) {
			JsonPrimitive primitive = value.getAsJsonPrimitive();
			if (primitive.isBoolean()) {
				return primitive.getAsBoolean();
			}
			else if (primitive.isNumber()) {
				return primitive.getAsNumber();
			}
			else if (primitive.isString()) {
				return primitive.getAsString();
			}
		}
		else if (value.isJsonObject()) {
			Map<String, Object> map = new HashMap<>();
			addJsonToMap(value.getAsJsonObject(), map);
			return map;
		}
		else if (value.isJsonArray()) {
			List<Object> list = new ArrayList<>();
			addJsonToList(value.getAsJsonArray(), list);
			return list;
		}
		throw new RuntimeException("Cannot convert [" + value + "]");
	}
	
	protected void addJsonToMap(JsonObject element, Map<String, Object> map) {
		for(Entry<String,JsonElement> attribute : element.entrySet()) {
			map.put(attribute.getKey(), convert(attribute.getValue()));	
		}
	}
	
	private void addJsonToList(JsonArray asJsonArray, List<Object> list) {
		for(int i=0; i< asJsonArray.size(); i++) {
			list.add(convert(asJsonArray.get(i)));
		}
	}

	public Map<String, Object> flattenAsMap(String json) {
		return JsonFlattener.flattenAsMap(json);
	}
	
	public String unFlatten(String json) {
		return JsonUnflattener.unflatten(json);
	}


	public String flatten(String json) {
		return JsonFlattener.flatten(json);
	}
}
