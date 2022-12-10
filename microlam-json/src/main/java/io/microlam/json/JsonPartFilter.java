package io.microlam.json;

import java.util.List;

import javax.json.JsonValue;

public interface JsonPartFilter {

	JsonValue filter(JsonValue part, List<String> uploadedFiles, JsonValue original, JsonValue result);
	
}
