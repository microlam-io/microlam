package io.microlam.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonObjectAccessor implements JsonAccessor {

	final JsonObject map;
	final String member;
	

	public JsonObjectAccessor(JsonObject map, String member) {
		this.map = map;
		this.member = member;
	}

	@Override
	public JsonElement set(JsonElement value) {
		map.add(member, value);
		return map;
	}

	@Override
	public JsonElement get() {
		return map.get(member);
	}

}
