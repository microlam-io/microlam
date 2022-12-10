package io.microlam.json;

import javax.json.JsonValue;

public interface JsonAccessor {

	JsonValue set(JsonValue value);
	
	JsonValue get();
	
}
