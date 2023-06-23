package io.microlam.json;

import jakarta.json.JsonValue;

public interface JsonAccessor {

	JsonValue set(JsonValue value);
	
	JsonValue get();
	
}
