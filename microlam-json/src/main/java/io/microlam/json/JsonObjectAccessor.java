package io.microlam.json;

import javax.json.JsonObject;
import javax.json.JsonValue;

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
