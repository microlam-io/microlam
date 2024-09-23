package io.microlam.json;

import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class JsonPath {

	final String jsonPath;
	final String[] parts;
	
	public JsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
		this.parts = jsonPath.split("[\\.\\[]");
//		if (! parts[0].equals("$")) {
//			throw new RuntimeException("Every jsonPath must start with $ but was " + parts[0]);
//		}
	}
	
	
	public JsonElement get(String jsonObject) {
		  Gson gson = new Gson();
		JsonElement json = gson.fromJson(jsonObject, JsonElement.class);
	    return get(json);
	}
	
	public JsonElement get(JsonElement result) {
//	    print('JsonPath($jsonPath).get($jsonObject)');
	    if ((parts.length == 1) && (parts[0].equals("$"))) {
	      //jsonPath is only $
	      return result;
	    }
	    JsonElement current = result; //not requiring start with dollar symbol
	    for (String part : parts) {
	      if (part.equals("$")) {
	        current = result; //do nothing
	      }
	      else if (part.endsWith("]")) {
	        String valueString = part.substring(0, part.length() - 1);
	        int valueInt = Integer.parseInt(valueString);
	        if (current.isJsonArray()) {
	          JsonArray array = current.getAsJsonArray();
	          current = array.get(valueInt);
	        }
	        else {
	          return null;
	        }
	      }
	      else if ((current != null) && (current.isJsonObject())) {
	    	  JsonObject object =  current.getAsJsonObject();
	    	  current = object.get(part);
	      }
	      else {
	        return null;
	      }
	    }
	    return current;
	}
	
	  public JsonElement set(String jsonObject, JsonElement value) {
//	    print('JsonPath($jsonPath).set($jsonObject, $value)');
		  Gson gson = new Gson();
		  JsonElement result = gson.fromJson(jsonObject, JsonElement.class);
	    return set(result, value);
	  }
	  
	  public JsonElement set(JsonElement result, JsonElement value) {
//		    print('JsonPath($jsonPath).set($jsonObject, $value)');
	    if ((parts.length == 1) && (parts[0].equals("$"))) {
	      //jsonPath is only $
	      return value;
	    }
	    JsonElement current = result;
	    JsonAccessor accessor = null;
	    for(String part : parts) {
	      if (part.equals("$") || (part.length() == 0)) {
	        current = result;
	      }
	      else if (part.endsWith("]")) {
	        String valueString = part.substring(0, part.length()-1);
	        try {
	        	int valueInt = Integer.parseInt(valueString);
		        if ((current != null) && (current.isJsonArray())) {
			          JsonArray array = current.getAsJsonArray();
			          accessor = new JsonArrayAccessor(array, valueInt);
			          JsonElement accessed = accessor.get();
			          //if (accessed != null) {
			            current = accessed;
			            continue;
			          //}
		        }
		        else {
		            current = new JsonArray();
		            if (accessor != null) {
		              accessor.set(current);
		            }
		            else {
		              result = current;
		            }
		            accessor = new JsonArrayAccessor(current.getAsJsonArray(), valueInt);
		            accessor.set(null);
		            current = null;
		            continue;
		        }
	        }
	        catch(NumberFormatException nex) {
		        if ((current != null) && (current.isJsonObject())) {
			          JsonObject object = current.getAsJsonObject();
			          accessor = new JsonObjectAccessor(object, valueString);
			          JsonElement accessed = accessor.get();
			          //if (accessed != null) {
			            current = accessed;
			            continue;
			          //}
		        }
		        else {
		            current = new JsonObject();
		            if (accessor != null) {
		              accessor.set(current);
		            }
		            else {
		              result = current;
		            }
		            accessor = new JsonObjectAccessor(current.getAsJsonObject(), valueString);
		            accessor.set(null);
		            current = null;
		            continue;
		        }
	        }
	      }
	      else if ((current != null) && (current.isJsonObject())) {
	        accessor = new JsonObjectAccessor(current.getAsJsonObject(), part);
	        JsonElement accessed = accessor.get();
//	        if (accessed != null) {
	            current = accessed;
	            continue;
//	        }
	      }
	      else {
	        if (accessor != null) {
	          current = new JsonObject();
	          current.getAsJsonObject().add(part, JsonNull.INSTANCE);
	          accessor.set(current);
	          accessor = new JsonObjectAccessor(current.getAsJsonObject(), part);
	          current = null;
	          continue;
	        }
	        else {
	          result = new JsonArray();
	          current.getAsJsonObject().add(part, JsonNull.INSTANCE);
	          accessor = new JsonObjectAccessor(result.getAsJsonObject(), part);
	          current = null;
	        }
	      }
	    }
	    if (accessor != null) {
	      accessor.set(value);
	    }
	    else {
	      result = value;
	    }
	    return result;
	  }

	
}
