package io.microlam.json;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public interface JsonArrayBuilder extends Builder {

	public JsonArrayBuilder add(Boolean bool);
	public JsonArrayBuilder add(String string);
	public JsonArrayBuilder add(Character character);
	public JsonArrayBuilder add(Number number);
	public JsonArrayBuilder addNull();
	public JsonArrayBuilder add(JsonElement element);
	public JsonArrayBuilder addJson(String value);

	public JsonArrayBuilder addAll(String[] strings);
	
	
	public JsonObjectBuilder endArrayAsObject();
	public JsonArrayBuilder endArrayAsArray();
	public Builder endArray();
	
	public JsonObjectBuilder addObject();
	public <T> JsonObjectBuilder addObject(Map<String, T> map);
	public JsonArrayBuilder addArray();
	
	public JsonArray buildArray();
	public JsonArray buildIntermediateArray();
}
