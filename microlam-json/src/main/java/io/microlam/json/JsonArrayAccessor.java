package io.microlam.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;

public class JsonArrayAccessor implements JsonAccessor {

	final JsonArray array;
	final int index;
	
	
	
	public JsonArrayAccessor(JsonArray array, int index) {
		this.array = array;
		this.index = index;
	}

	@Override
	public JsonElement set(JsonElement value) {
		if (index >= array.size()) {
		      for(int i=array.size(); i<index+1; i++) {
		    	  array.add(JsonNull.INSTANCE);
		      }
		}
	    array.set(index, value);
	    return array;
	}

	@Override
	public JsonElement get() {
	    if (index >= array.size()) {
	        return JsonNull.INSTANCE;
	      }
	      return array.get(index);
	}

}
