package io.microlam.json;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

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
	
	
	public JsonValue get(String jsonObject) {
		JsonValue json = Json.createParser(new StringReader(jsonObject)).getValue();
	    return get(json);
	}
	
	public JsonValue get(JsonValue result) {
//	    print('JsonPath($jsonPath).get($jsonObject)');
	    if ((parts.length == 1) && (parts[0].equals("$"))) {
	      //jsonPath is only $
	      return result;
	    }
	    JsonValue current = result; //not requiring start with dollar symbol
	    for (String part : parts) {
	      if (part.equals("$")) {
	        current = result; //do nothing
	      }
	      else if (part.endsWith("]")) {
	        String valueString = part.substring(0, part.length() - 1);
	        int valueInt = Integer.parseInt(valueString);
	        if (current.getValueType() == ValueType.ARRAY) {
	          JsonArray array = current.asJsonArray();
	          current = array.get(valueInt);
	        }
	        else {
	          return null;
	        }
	      }
	      else if ((current != null) && (current.getValueType() == ValueType.OBJECT)) {
	    	  JsonObject object =  current.asJsonObject();
	    	  current = object.get(part);
	      }
	      else {
	        return null;
	      }
	    }
	    return current;
	}
	
	  public JsonValue set(String jsonObject, JsonValue value) {
//	    print('JsonPath($jsonPath).set($jsonObject, $value)');
		  JsonValue result = Json.createParser(new StringReader(jsonObject)).getValue();;
	    return set(result, value);
	  }
	  
	  public JsonValue set(JsonValue result, JsonValue value) {
//		    print('JsonPath($jsonPath).set($jsonObject, $value)');
	    if ((parts.length == 1) && (parts[0].equals("$"))) {
	      //jsonPath is only $
	      return value;
	    }
	    JsonValue current = result;
	    JsonAccessor accessor = null;
	    for(String part : parts) {
	      if (part.equals("$") || (part.length() == 0)) {
	        current = result;
	      }
	      else if (part.endsWith("]")) {
	        String valueString = part.substring(0, part.length()-1);
	        try {
	        	int valueInt = Integer.parseInt(valueString);
		        if ((current != null) && (current.getValueType() == ValueType.ARRAY)) {
			          JsonArray array = current.asJsonArray();
			          accessor = new JsonArrayAccessor(array, valueInt);
			          JsonValue accessed = accessor.get();
			          //if (accessed != null) {
			            current = accessed;
			            continue;
			          //}
		        }
		        else {
		            current = Json.createArrayBuilder().build();
		            if (accessor != null) {
		              accessor.set(current);
		            }
		            else {
		              result = current;
		            }
		            accessor = new JsonArrayAccessor(current.asJsonArray(), valueInt);
		            accessor.set(null);
		            current = null;
		            continue;
		        }
	        }
	        catch(NumberFormatException nex) {
		        if ((current != null) && (current.getValueType() == ValueType.OBJECT)) {
			          JsonObject object = current.asJsonObject();
			          accessor = new JsonObjectAccessor(object, valueString);
			          JsonValue accessed = accessor.get();
			          //if (accessed != null) {
			            current = accessed;
			            continue;
			          //}
		        }
		        else {
		            current = Json.createObjectBuilder().build();
		            if (accessor != null) {
		              accessor.set(current);
		            }
		            else {
		              result = current;
		            }
		            accessor = new JsonObjectAccessor(current.asJsonObject(), valueString);
		            accessor.set(null);
		            current = null;
		            continue;
		        }
	        }
	      }
	      else if ((current != null) && (current.getValueType() == ValueType.OBJECT)) {
	        accessor = new JsonObjectAccessor(current.asJsonObject(), part);
	        JsonValue accessed = accessor.get();
//	        if (accessed != null) {
	            current = accessed;
	            continue;
//	        }
	      }
	      else {
	        if (accessor != null) {
	          current = Json.createObjectBuilder().build();
	          current.asJsonObject().put(part, JsonValue.NULL);
	          accessor.set(current);
	          accessor = new JsonObjectAccessor(current.asJsonObject(), part);
	          current = null;
	          continue;
	        }
	        else {
	          result = Json.createObjectBuilder().build();
	          current.asJsonObject().put(part, JsonValue.NULL);
	          accessor = new JsonObjectAccessor(result.asJsonObject(), part);
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
