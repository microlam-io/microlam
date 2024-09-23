package io.microlam.json;

import java.util.Map;

import com.google.gson.JsonElement;

public interface JsonFilter {

	JsonElement filter(JsonElement jsonElement, Map<String,String> uploadedFiles);
	
}
