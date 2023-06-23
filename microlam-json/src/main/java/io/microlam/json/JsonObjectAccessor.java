package io.microlam.json;

import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public class JsonObjectAccessor implements JsonAccessor {

	final JsonObject map;
	final String member;
	

	public JsonObjectAccessor(JsonObject map, String member) {
		this.map = map;
		this.member = member;
	}

	@Override
	public JsonValue set(JsonValue value) {
		map.put(member, value);
		return map;
	}

	@Override
	public JsonValue get() {
		return map.get(member);
	}

}
