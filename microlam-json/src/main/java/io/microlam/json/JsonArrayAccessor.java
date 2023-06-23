package io.microlam.json;

import jakarta.json.JsonArray;
import jakarta.json.JsonValue;

public class JsonArrayAccessor implements JsonAccessor {

	final JsonArray array;
	final int index;
	
	
	
	public JsonArrayAccessor(JsonArray array, int index) {
		this.array = array;
		this.index = index;
	}

	@Override
	public JsonValue set(JsonValue value) {
		if (index >= array.size()) {
		      for(int i=array.size(); i<index+1; i++) {
		    	  array.add(JsonValue.NULL);
		      }
		}
	    array.set(index, value);
	    return array;
	}

	@Override
	public JsonValue get() {
	    if (index >= array.size()) {
	        return JsonValue.NULL;
	      }
	      return array.get(index);
	}

}
