package io.microlam.json;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JsonArrayBuilderImpl implements JsonArrayBuilder {
	
	protected JsonBuilder jsonBuilder;
	protected JsonArray jsonArray;
	
	protected JsonArrayBuilderImpl(JsonBuilder jsonBuilder) {
		this.jsonBuilder = jsonBuilder;
		jsonArray = new JsonArray();
	}

	protected JsonArrayBuilderImpl(JsonBuilder jsonBuilder, JsonArray jsonArray) {
		this.jsonBuilder = jsonBuilder;
		this.jsonArray = jsonArray;
	}
				
	@Override
	public JsonArrayBuilder add(Boolean bool) {
		jsonArray.add(bool);
		return this;
	}

	@Override
	public JsonArrayBuilder add(String bool) {
		jsonArray.add(bool);
		return this;
	}

	@Override
	public JsonArrayBuilder add(Character bool) {
		jsonArray.add(bool);
		return this;
	}

	@Override
	public JsonArrayBuilder add(Number bool) {
		jsonArray.add(bool);
		return this;
	}

	@Override
	public JsonArrayBuilder add(JsonElement element) {
		jsonArray.add(element);
		return this;
	}

	
	@Override
	public <T> JsonObjectBuilder addObject(Map<String, T> map) {
		JsonObjectBuilder builder = addObject();
		builder.addAll(map);
		return builder;
	}
	
	@Override
	public JsonObjectBuilder addObject() {
		JsonObject newJsonObject = new JsonObject();
		jsonArray.add(newJsonObject);
		return jsonBuilder.objectBuilder(newJsonObject);
	}

	@Override
	public JsonArrayBuilder addArray() {
		JsonArray newJsonArray = new JsonArray();
		jsonArray.add(newJsonArray);
		return jsonBuilder.arrayBuilder(newJsonArray);
	}

	@Override
	public JsonArray buildArray() {
		return jsonBuilder.buildArray();
	}

	@Override
	public JsonArray buildIntermediateArray() {
		return jsonBuilder.duplicate(jsonArray).getAsJsonArray();
	}
	
	@Override
	public JsonArrayBuilder addJson(String value) {
		return add(JsonParser.parseString(value));
	}
	
	@Override
	public JsonArrayBuilder addNull() {
		jsonArray.add(JsonNull.INSTANCE);
		return this;
	}
	
	@Override
	public JsonArrayBuilder addAll(String[] values) {
		for(int i=0; i<values.length; i++)  {
			add(values[i]);
		}
		return this;
	}


	public boolean isEmpty() {
		return jsonArray.size() == 0;
	}

	@Override
	public JsonObjectBuilder endArrayAsObject() {
		return jsonBuilder.endArrayAsObject();
	}

	@Override
	public JsonArrayBuilder endArrayAsArray() {
		return jsonBuilder.endArrayAsArray();
	}

	@Override
	public Builder endArray() {
		return jsonBuilder.endArray();
	}

}
