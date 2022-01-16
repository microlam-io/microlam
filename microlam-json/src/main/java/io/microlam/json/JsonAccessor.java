package io.microlam.json;

import com.google.gson.JsonElement;

public interface JsonAccessor {

	JsonElement set(JsonElement value);
	
	JsonElement get();
	
}
