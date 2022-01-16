package io.microlam.json;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class JsonBuilder {
	
	public final static JsonArray EMPTY_ARRAY = new JsonArray();
	
	protected static JsonConverter jsonConverter = new JsonConverter(); 
	protected Stack<Builder> stack;
	
	public JsonBuilder() {
		stack = new Stack<>();
	}
		
	public void clear() {
		stack.clear();
	}
	
	public JsonElement convert(Object object) {
		return jsonConverter.convert(object);
	}
	
	public Object convert(JsonElement element) {
		return jsonConverter.convert(element);
	}
	
	public Map<String, Object> flattenAsMap(String json) {
		return jsonConverter.flattenAsMap(json);
	}
	
	public void flattenAsMergeMapSet(Map<String, Set<Object>> merge, String json, Set<String> includekeys, Set<String> ignoreKeys, boolean ignore, Integer maxSetSize) {
		Map<String, Object> map = jsonConverter.flattenAsMap(json);
		for(Entry<String,Object> entry: map.entrySet()) {
			String key;
			Set<Object> set;
			if  (entry.getKey().endsWith("]")) {
				int crochet = entry.getKey().lastIndexOf('[');
				key = entry.getKey().substring(0,crochet);
			}
			else  {
				key = entry.getKey();
			}
			if (ignore) {
				if  (ignoreKeys.contains(key)) {	
					continue;
				}
				else {
					includekeys.add(key);
				}
			}
			else if (! includekeys.contains(key)) {
				ignoreKeys.add(key);
				continue;
			}
			if (merge.containsKey(key)) {
				set = merge.get(key);
			}
			else {
				set = new HashSet<>();
				merge.put(key, set);
			}
			if  ((maxSetSize == null) || (set.size() < maxSetSize.intValue())) {
				set.add(entry.getValue());
			}
		}
	}
	
	public void flattenAsMergeMap(Map<String, Object> merge, String json, Set<String> includekeys, Set<String> ignoreKeys, boolean ignore) {
		Map<String, Object> map = jsonConverter.flattenAsMap(json);
		for(Entry<String,Object> entry: map.entrySet()) {
			boolean simple = true;
			String key;
			if  (entry.getKey().endsWith("]")) {
				int crochet = entry.getKey().lastIndexOf('[');
				String index = entry.getKey().substring(crochet+1, entry.getKey().length()-1);
				try {
					int i  = Integer.parseInt(index);
					key = entry.getKey().substring(0,crochet);
					simple = false;
				}
				catch(NumberFormatException ex) {
					String newIndex = index;
					if (index.startsWith("\\\"") && index.endsWith("\\\"")) {
						newIndex =  index.substring(2, index.length()-2);
						if (newIndex.contains(".")) {
							newIndex = newIndex.replace(".", "%2E");
						}
					}
					key = entry.getKey().substring(0,crochet)+"."+newIndex;
					simple = true;
				}
			}
			else  {
				key = entry.getKey();
			}
			List<Object> list;
			if (ignore) {
				if  (ignoreKeys.contains(key)) {	
					continue;
				}
				else {
					includekeys.add(key);
				}
			}
			else if (! includekeys.contains(key)) {
				ignoreKeys.add(key);
				continue;
			}
			if (simple)  {
				merge.put(key, entry.getValue());
				continue;
			}
			if (merge.containsKey(key)) {
				list = (List<Object>) merge.get(key);
			}
			else {
				list = new ArrayList();
				merge.put(key, list);
			}
			list.add(entry.getValue());
		}
	}

	
	public String unFlatten(String json) {
		return jsonConverter.unFlatten(json);
	}

	public String flatten(String json) {
		return jsonConverter.flatten(json);
	}
	
	public JsonObjectBuilder objectBuilder() {
		JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilderImpl(this);
		stack.push(jsonObjectBuilder);
		//System.out.println("Add ObjectBuilder to stack...");
		return jsonObjectBuilder;
	}
	
	public JsonObjectBuilder objectBuilder(JsonObject jsonObject) {
		JsonObjectBuilder jsonObjectBuilder = new JsonObjectBuilderImpl(this, jsonObject);
		stack.push(jsonObjectBuilder);
		//System.out.println("Add ObjectBuilder to stack...");
		return jsonObjectBuilder;
	}
	
	public static JsonElement parseJson(String json) {
		return JsonParser.parseString(json);
	}
	
	public JsonElement duplicate(JsonElement jsonElement) {
		if (jsonElement.isJsonObject()) {
			JsonObject source = jsonElement.getAsJsonObject();
			JsonObject copy = new JsonObject();
			for(Entry<String, JsonElement> entry : source.entrySet()) {
				copy.add(entry.getKey(), duplicate(entry.getValue()));
			}
			return copy;
		}
		else if (jsonElement.isJsonArray()) {
			JsonArray source = jsonElement.getAsJsonArray();
			JsonArray copy = new JsonArray();
			for(JsonElement element : source) {
				copy.add(duplicate(element));
			}
			return copy;
		}
		else {
			return jsonElement;
		}
	}
	
	public JsonArrayBuilder arrayBuilder() {
		JsonArrayBuilder jsonArrayBuilder = new JsonArrayBuilderImpl(this);
		stack.push(jsonArrayBuilder);
		//System.out.println("Add ArrayBuilder to stack...");
		return jsonArrayBuilder;		
	}

	public JsonArrayBuilder arrayBuilder(JsonArray jsonArray) {
		JsonArrayBuilder jsonArrayBuilder = new JsonArrayBuilderImpl(this, jsonArray);
		stack.push(jsonArrayBuilder);
		//System.out.println("Add ArrayBuilder to stack...");
		return jsonArrayBuilder;		
	}
	
	public Builder build() {
		return stack.pop();
	}

	private JsonObjectBuilder peekJsonObjectBuilder() {
		Builder json = stack.peek();
		if (json instanceof JsonObjectBuilder) {
			return (JsonObjectBuilder) json;
		}
		throw new RuntimeException("The top of the stack is not of type JsonObjectBuilder [" + json + "]");
	}

	private JsonArrayBuilder peekJsonArrayBuilder() {
		Builder json = stack.peek();
		if (json instanceof JsonArrayBuilder) {
			return (JsonArrayBuilder) json;
		}
		throw new RuntimeException("The top of the stack is not of type JsonArrayBuilder [" + json + "]");
	}
	
	public Builder endObject() {
		Builder builder = stack.pop();
		//System.out.println("endObject from stack...");
		return builder;
	}

	public JsonObjectBuilder endObjectAsObject() {
		stack.pop();
		JsonObjectBuilder jsonObjectBuilder = peekJsonObjectBuilder();
		//System.out.println("endObjectAsObject from stack...");
		return jsonObjectBuilder;
	}

	public JsonArrayBuilder endObjectAsArray() {
		stack.pop();
		JsonArrayBuilder jsonObjectBuilder = peekJsonArrayBuilder();
		//System.out.println("endObjectAsArray from stack...");
		return jsonObjectBuilder;
	}

	public JsonObjectBuilder endArrayAsObject() {
		stack.pop();
		JsonObjectBuilder jsonObjectBuilder = peekJsonObjectBuilder();
		//System.out.println("endArrayAsObject from stack...");
		return jsonObjectBuilder;
	}

	public JsonArrayBuilder endArrayAsArray() {
		stack.pop();
		JsonArrayBuilder jsonObjectBuilder = peekJsonArrayBuilder();
		//System.out.println("endArrayAsArray from stack...");
		return jsonObjectBuilder;
	}

	public Builder endArray() {
		Builder builder = stack.pop();
		//System.out.println("endArray from stack...");
		return builder;
	}
	
	public JsonArray buildArray() {
		JsonArrayBuilder jsonObject = peekJsonArrayBuilder();
		stack.pop();
		//System.out.println("buildArray from stack...");
//		if (!stack.isEmpty()) {
//			StringBuffer buffer = new StringBuffer();
//			buffer.append("The stack is not empty...\n");
//			Enumeration<Builder> enumeration = stack.elements();
//			buffer.append("[");
//			boolean start = true;
//			while(enumeration.hasMoreElements()) {
//				if (start) {
//					buffer.append(',');
//					start = false;
//				}
//				buffer.append(enumeration.nextElement());
//			}
//			buffer.append("]");
//			throw new RuntimeException(buffer.toString());
//		}
		return jsonObject.buildIntermediateArray();
	}
	
	public JsonObject buildObject() {
		JsonObjectBuilder jsonObject = peekJsonObjectBuilder();
		stack.pop();
		//System.out.println("buildObject from stack...");
//		if (!stack.isEmpty()) {
//			StringBuffer buffer = new StringBuffer();
//			buffer.append("The stack is not empty...\n");
//			Enumeration<Builder> enumeration = stack.elements();
//			buffer.append("[");
//			boolean start = true;
//			while(enumeration.hasMoreElements()) {
//				if (start) {
//					buffer.append(',');
//					start = false;
//				}
//				buffer.append(enumeration.nextElement());
//			}
//			buffer.append("]");
//			throw new RuntimeException(buffer.toString());
//		}
		return jsonObject.buildIntermediateObject();
	}

	public static String verifyMandatoryString(JsonElement element) {
		String message;
		if (element.isJsonPrimitive()) {
			JsonPrimitive jsonPrimitive = element.getAsJsonPrimitive();
			if (jsonPrimitive.isString()) {
				return jsonPrimitive.getAsString();
			}
			else {
				message = String.format("Element is primitive but not String [%s]", jsonPrimitive.toString());
			}
		}
		else {
			message = String.format("Element is not primitive");
		}
		throw new RuntimeException(message);
	}
	
	public static String verifyMandatoryAttributeString(JsonObject body, String string) {
		String message;
		if (body.has(string)) {
			JsonElement el = body.get(string);
			if (el.isJsonPrimitive()) {
				JsonPrimitive jsonPrimitive = el.getAsJsonPrimitive();
				if (jsonPrimitive.isString()) {
					return jsonPrimitive.getAsString();
				}
				else {
					message = String.format("Attribute %s found and primitive but not String [%s]", string, jsonPrimitive.toString());
				}
			}
			else {
				message = String.format("Attribute %s found but not primitive", string);
			}
		}
		else {
			message = String.format("Attribute %s not found", string);
		}
		throw new RuntimeException(message);
	}

	public static int verifyMandatoryAttributeInt(JsonObject body, String string) {
		String message;
		if (body.has(string)) {
			JsonElement el = body.get(string);
			if (el.isJsonPrimitive()) {
				JsonPrimitive jsonPrimitive = el.getAsJsonPrimitive();
				if (jsonPrimitive.isNumber()) {
					return jsonPrimitive.getAsInt();
				}
				else {
					message = String.format("Attribute %s found and primitive but not int [%s]", string, jsonPrimitive.toString());
				}
			}
			else {
				message = String.format("Attribute %s found but not primitive", string);
			}
		}
		else {
			message = String.format("Attribute %s not found", string);
		}
		throw new RuntimeException(message);
	}
	
	public static JsonObject verifyMandatoryAttributeJsonObject(JsonObject body, String string) {
		String message;
		if (body.has(string)) {
			JsonElement el = body.get(string);
			if (el.isJsonObject()) {
				JsonObject jsonObject = el.getAsJsonObject();
					return jsonObject;
			}
			else {
				message = String.format("Attribute %s found but not JsonObject", string);
			}
		}
		else {
			message = String.format("Attribute %s not found", string);
		}
		throw new RuntimeException(message);
	}

	public static JsonObject verifyOptionalAttributeJsonObject(JsonObject body, String string, JsonObject defaultValue) {
		if (body.has(string)) {
			JsonElement el = body.get(string);
			if (el.isJsonObject()) {
				JsonObject jsonObject = el.getAsJsonObject();
				return jsonObject;
			}
			else {
				return defaultValue;
			}
		}
		else {
			return defaultValue;
		}
	}
	
	public static JsonArray verifyOptionalAttributeJsonArray(JsonObject body, String string, JsonArray  defaultValue) {
		if (body.has(string)) {
			JsonElement el = body.get(string);
			if (el.isJsonArray()) {
				JsonArray jsonObject = el.getAsJsonArray();
					return jsonObject;
			}
			else {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public static JsonArray verifyMandatoryAttributeJsonArray(JsonObject body, String string) {
		String message;
		if (body.has(string)) {
			JsonElement el = body.get(string);
			if (el.isJsonArray()) {
				JsonArray jsonObject = el.getAsJsonArray();
					return jsonObject;
			}
			else {
				message = String.format("Attribute %s found but not JsonArray", string);
			}
		}
		else {
			message = String.format("Attribute %s not found", string);
		}
		throw new RuntimeException(message);
	}
	
	public static String verifyOptionalAttributeString(JsonObject body, String attributeName, String defaultValue) {
		if (body.has(attributeName)) {
			JsonElement el = body.get(attributeName);
			if (el.isJsonPrimitive()) {
				JsonPrimitive jsonPrimitive = el.getAsJsonPrimitive();
				if (jsonPrimitive.isString()) {
					return jsonPrimitive.getAsString();
				}
			}
		}
		return defaultValue;
	}

	public static int verifyOptionalAttributeInt(JsonObject body, String attributeName, int defaultValue) {
		if (body.has(attributeName)) {
			JsonElement el = body.get(attributeName);
			if (el.isJsonPrimitive()) {
				JsonPrimitive jsonPrimitive = el.getAsJsonPrimitive();
				if (jsonPrimitive.isNumber()) {
					return jsonPrimitive.getAsInt();
				}
			}
		}
		return defaultValue;
	}

	public static boolean verifyOptionalAttributeBoolean(JsonObject body, String attributeName, boolean defaultValue) {
		if (body.has(attributeName)) {
			JsonElement el = body.get(attributeName);
			if (el.isJsonPrimitive()) {
				JsonPrimitive jsonPrimitive = el.getAsJsonPrimitive();
				if (jsonPrimitive.isBoolean()) {
					return jsonPrimitive.getAsBoolean();
				}
			}
		}
		return defaultValue;
	}

}
