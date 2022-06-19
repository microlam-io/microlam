package io.microlam.json;

import java.util.Map;

import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.unflattener.JsonUnflattener;

public class JsonConverter {


	public Map<String, Object> flattenAsMap(String json) {
		return JsonFlattener.flattenAsMap(json);
	}
	
	public String unFlatten(String json) {
		return JsonUnflattener.unflatten(json);
	}


	public String flatten(String json) {
		return JsonFlattener.flatten(json);
	}
}
