package io.microlam.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonValue;

public class JsonMappings implements JsonFilter {

	protected JsonMappingPartFilterProvider jsonFilterResolver;
	protected JsonMapping[] mappings;
	
	public  JsonMappings(JsonMapping[] mappings, JsonMappingPartFilterProvider jsonFilterResolver)  {
		this.mappings = mappings;
		this.jsonFilterResolver  = jsonFilterResolver;
	}
	
	public static JsonMappings parse(String specs, JsonMappingPartFilterProvider jsonFilterResolver) {
		String[] parts = specs.split("\\|");
		JsonMapping[] result  = new JsonMapping[parts.length];
		for(int i=0; i<parts.length; i++) {
			result[i] = JsonMapping.parse(parts[i]);
		}
		return new JsonMappings(result, jsonFilterResolver);
	}

	@Override
	public JsonValue filter(JsonValue source, Map<String,String> uploadedFiles) {
		List<String> uploadFilesPart = new ArrayList<>();
		JsonValue result = null;
		for(JsonMapping jsonMapping: mappings)  {
			if (result == null) {
				if  (jsonMapping.pathDestination.startsWith("[")) {
					result = Json.createArrayBuilder().build();
				}
				else {
					result =  Json.createObjectBuilder().build();
				}
			}
			//Step 1: get the source
			JsonPath jsonPath  = new JsonPath(jsonMapping.pathSource);
			JsonValue partial = jsonPath.get(source);
			
			JsonValue transformed = partial;
			
			//Step 2:  transform the source
			if (jsonMapping.parts != null) {
				if (jsonFilterResolver == null) {
					throw new RuntimeException("JsonFilterResolver while needing to resolve filter names = " + jsonMapping.parts);
				}
				JsonValue current = partial;
				for(int i=0; i<jsonMapping.parts.length; i++) {
					JsonMappingPart jsonMappingPart = jsonMapping.parts[i];
					JsonPartFilter jsonFilter = jsonFilterResolver.provide(jsonMappingPart);
					current = jsonFilter.filter(current, uploadFilesPart, source, result);
					if (! uploadFilesPart.isEmpty()) {
						uploadedFiles.put(jsonMapping.pathDestination, uploadFilesPart.get(0));
						uploadFilesPart.clear();
					}
				}
				transformed = current;
			}
			
			//Step 3: bind to destination only when pathDestination  != "!"
			if (! "!".equals(jsonMapping.pathDestination))  {
				JsonPath jsonPathDestination  = new JsonPath(jsonMapping.pathDestination);
				jsonPathDestination.set(result, transformed);
			}
		}
		return result;
	}
	
	
}
