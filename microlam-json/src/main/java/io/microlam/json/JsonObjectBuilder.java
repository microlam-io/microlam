package io.microlam.json;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface JsonObjectBuilder extends Builder {

	public JsonObjectBuilder add(String property, JsonElement value);
	public JsonObjectBuilder addJson(String property, String value);
	public <T> JsonObjectBuilder addAll(Map<String,T> map);
	public JsonObjectBuilder addAll(String[] property, String[] value);
	public JsonObjectBuilder add(String property, String value);
	public JsonObjectBuilder add(String property, Number value);
	public JsonObjectBuilder add(String property, Boolean value);
	public JsonObjectBuilder add(String property, Character value);
	public JsonObjectBuilder addNull(String property);
	public JsonObjectBuilder addObject(String property);
	public <T> JsonObjectBuilder addObject(String property, Map<String, T> map);
	public JsonArrayBuilder addArray(String property);
	public boolean isEmpty();
	
	public Builder endObject();
	public JsonObjectBuilder endObjectAsObject();
	public JsonArrayBuilder endObjectAsArray();

	public JsonObject buildObject();
	public JsonObject buildIntermediateObject();
	public Map<String, String> buildPrimitiveMap();
	public JsonObjectBuilder mergeJson(JsonObject jsonToMerge);
	public JsonObjectBuilder mergeJson(String value);
	
	
	public JsonObjectBuilder addPath(String property, String value);
	public JsonObjectBuilder addPath(String property, Number value);
	public JsonObjectBuilder addPath(String property, Boolean value);
	public JsonObjectBuilder addPath(String property, Character value);
	public JsonObjectBuilder addPath(String property, JsonElement value);
}
