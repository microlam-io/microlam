package io.microlam.json;

import java.util.List;

import com.google.gson.JsonElement;

public interface JsonPartFilter {

	JsonElement filter(JsonElement part, List<String> uploadedFiles, JsonElement original, JsonElement result);
	
}
