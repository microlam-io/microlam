package io.microlam.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;


public class JsonObjectBuilderImpl implements JsonObjectBuilder {
		
	protected JsonBuilder jsonBuilder;
	protected JsonObject jsonObject;
	
	public JsonObjectBuilderImpl(JsonBuilder jsonBuilder) {
		this.jsonBuilder = jsonBuilder;
		jsonObject = new JsonObject();
	}

	protected JsonObjectBuilderImpl(JsonBuilder jsonBuilder, JsonObject jsonObject) {
		this.jsonBuilder = jsonBuilder;
		this.jsonObject = jsonObject;
	}
	
	@Override
	public JsonObjectBuilder add(String property, JsonElement value) {
		jsonObject.add(property, value);
		return this;
	}

	@Override
	public JsonObjectBuilder add(String property, String value) {
		jsonObject.addProperty(property, value);
		return this;
	}

	@Override
	public JsonObjectBuilder add(String property, Number value) {
		jsonObject.addProperty(property, value);
		return this;
	}

	@Override
	public JsonObjectBuilder add(String property, Boolean value) {
		jsonObject.addProperty(property, value);
		return this;
	}

	@Override
	public JsonObjectBuilder addNull(String property) {
		jsonObject.add(property, JsonNull.INSTANCE);
		return this;
	}
	
	@Override
	public JsonObjectBuilder add(String property, Character value) {
		jsonObject.addProperty(property, value);
		return this;
	}

	@Override
	public <T> JsonObjectBuilder addObject(String property, Map<String, T> map) {
		JsonObjectBuilder builder = addObject(property);
		builder.addAll(map);
		return builder;
	}
	
	@Override
	public JsonObjectBuilder addObject(String property) {
		JsonObject newJsonObject = new JsonObject();
		JsonObjectBuilder newJsonObjectBuilder = jsonBuilder.objectBuilder(newJsonObject);
		jsonObject.add(property, newJsonObject);
		return newJsonObjectBuilder;
	}

	@Override
	public JsonArrayBuilder addArray(String property) {
		JsonArray newJsonArray = new JsonArray();
		JsonArrayBuilder newJsonArrayBuilder = jsonBuilder.arrayBuilder(newJsonArray);
		jsonObject.add(property, newJsonArray);
		return newJsonArrayBuilder;
	}

	//@Override
	public JsonArrayBuilder addArray(String property, Object[] array) {
		JsonArray newJsonArray = new JsonArray();
		JsonArrayBuilder newJsonArrayBuilder = jsonBuilder.arrayBuilder(newJsonArray);
		jsonObject.add(property, newJsonArray);
		return newJsonArrayBuilder;
	}

	@Override
	public Builder endObject() {
		return jsonBuilder.endObject();
	}

	@Override
	public JsonObject buildObject() {
		return jsonBuilder.buildObject();
	}

	@Override
	public JsonObject buildIntermediateObject() {
		return jsonBuilder.duplicate(jsonObject).getAsJsonObject();
	}

	@Override
	public Map<String,String> buildPrimitiveMap() {
		Map<String,String> result = new HashMap<>();
		for(Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			if (entry.getValue().isJsonPrimitive()) {
				JsonPrimitive jsonPrimitive = entry.getValue().getAsJsonPrimitive();
				String value = null;
				if (jsonPrimitive.isString()) {
					value = jsonPrimitive.getAsString();
				}
				else if (jsonPrimitive.isNumber()) {
					value = jsonPrimitive.getAsNumber().toString();
				}
				else if (jsonPrimitive.isBoolean()) {
					value = Boolean.toString(jsonPrimitive.getAsBoolean());
				}
				if (value != null) {
					result.put(entry.getKey(), value);
				}
			}
			else if (entry.getValue().isJsonNull()) {
				result.put(entry.getKey(), null);
			}
		}
		return result;
	}
	
	@Override
	public JsonObjectBuilder addJson(String property, String value) {
		return add(property, JsonParser.parseString(value));
	}


	@Override
	public JsonObjectBuilder mergeJson(String value) {
		JsonObject jsonToMerge = JsonParser.parseString(value).getAsJsonObject();
		for(Entry<String,JsonElement> jsonEntry : jsonToMerge.entrySet()) {
			add(jsonEntry.getKey(), jsonEntry.getValue());
		}
		return this;
	}
	
	@Override
	public JsonObjectBuilder mergeJson(JsonObject jsonToMerge) {
		for(Entry<String,JsonElement> jsonEntry : jsonToMerge.entrySet()) {
			if (jsonObject.has(jsonEntry.getKey()) && jsonObject.get(jsonEntry.getKey()).isJsonArray() && jsonEntry.getValue().isJsonArray()) {
				jsonObject.get(jsonEntry.getKey()).getAsJsonArray().addAll(jsonEntry.getValue().getAsJsonArray());
			}
			else {
				add(jsonEntry.getKey(), jsonEntry.getValue());
			}
		}
		return this;
	}

	@Override
	public <T> JsonObjectBuilder addAll(Map<String, T> map) {
		for(Map.Entry<String, T> entry : map.entrySet()) {
			if (entry.getValue() == null) {
				addNull(entry.getKey());
			}
			else if (entry.getValue() instanceof String) {
				add(entry.getKey(), (String) entry.getValue());
			}
			else if (entry.getValue() instanceof Number) {
				add(entry.getKey(), (Number) entry.getValue());
			}
			else if (entry.getValue() instanceof Boolean) {
				add(entry.getKey(), (Boolean) entry.getValue());
			}
			else if (entry.getValue() instanceof Character) {
				add(entry.getKey(), (Character) entry.getValue());
			}
			else if (entry.getValue() instanceof JsonElement) {
				add(entry.getKey(), (JsonElement) entry.getValue());
			}
		}
		return this;
	}

	@Override
	public boolean isEmpty() {
		return jsonObject.size() == 0;
	}

	@Override
	public JsonObjectBuilder endObjectAsObject() {
		return jsonBuilder.endObjectAsObject();
	}

	@Override
	public JsonArrayBuilder endObjectAsArray() {
		return jsonBuilder.endObjectAsArray();
	}
	
	public JsonObjectBuilder addPath(String property, String value) {
		(new JsonPath(property)).set(jsonObject, new JsonPrimitive(value));
		return this;
	}
	
	public JsonObjectBuilder addPath(String property, Number value) {
		(new JsonPath(property)).set(jsonObject, new JsonPrimitive(value));
		return this;
	}
	
	public JsonObjectBuilder addPath(String property, Boolean value) {
		(new JsonPath(property)).set(jsonObject, new JsonPrimitive(value));
		return this;
	}
	
	public JsonObjectBuilder addPath(String property, Character value) {
		(new JsonPath(property)).set(jsonObject, new JsonPrimitive(value));
		return this;
	}
	
	public JsonObjectBuilder addPath(String property, JsonElement value) {
		(new JsonPath(property)).set(jsonObject, value);
		return this;
	}

	@Override
	public JsonObjectBuilder addAll(String[] properties, String[] values) {
		for(int i=0; i<properties.length; i++)  {
			add(properties[i], values[i]);
		}
		return this;
	}

}
