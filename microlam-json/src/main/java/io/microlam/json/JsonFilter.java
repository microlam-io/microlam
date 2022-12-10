package io.microlam.json;

import java.util.Map;

import javax.json.JsonValue;

public interface JsonFilter {

	JsonValue filter(JsonValue jsonElement, Map<String,String> uploadedFiles);
	
}
