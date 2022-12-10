package io.microlam.dynamodb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;


public class DynamoDBHelper {
	
	public final static AttributeValue ATTRIBUTE_VALUE_EMPTY = AttributeValue.builder().build();


	public static String verifyReservedName(String attribute, Map<String, String> attributeNames, AttributeNameGenerator attributeNameGenerator) {
		StringBuffer newName = new StringBuffer();
		String[] parts = attribute.split("\\.");
		for(String part : parts) {
			String partName;
			String partPrefix;
			String partArray;
			int arrayIndex = part.indexOf('[');
			if (arrayIndex >= 0) {
				partPrefix = part.substring(0, arrayIndex);
				partArray = part.substring(arrayIndex);
			}
			else {
				partPrefix = part;
				partArray = "";
			}
			if (partPrefix.startsWith("#") && attributeNames.containsKey(partPrefix)) {
				partName = partPrefix+partArray;
			}
			else if  (ReservedWords.isReserved(partPrefix)) {
				if (attributeNames.containsValue(partPrefix)) {
					partName = Utils.getFirstKey(attributeNames,partPrefix)+partArray;
				}
				else {
					String name = attributeNameGenerator.generateNewAttributeName();
					attributeNames.put(name, partPrefix);
					partName = name + partArray;
				}
			}
			else {
				partName = partPrefix + partArray;
			}
			if (newName.length() != 0) {
				newName.append('.');
			}
			newName.append(partName);
		}
		return newName.toString();
	}
	
	public static Map<String,String> createAttributeNameMap(String... attributes) {
		return updateAttributeNameMap(new HashMap<>(), attributes);
	}

	public static Map<String,String> updateAttributeNameMap(Map<String,String> map, String... attributes) {
		if (attributes.length %2 != 0) {
			throw new RuntimeException(String.format("param length must be even but was %d", attributes.length));
		}
		int size = attributes.length /2;
		for(int i=0; i<size;  i++) {
			String key = attributes[i*2];
			String value = attributes[i*2+1];			
			map.put(key, value);
		}
		return map;
	}

	public static Map<String,AttributeValue> createAttributeValueMap(Object... keys) {
		return updateAttributeValueMap(new HashMap<>(), keys);
	}
	
	public static Map<String,AttributeValue> updateAttributeValueMap(Map<String,AttributeValue> map, Object... keys) {
		if (keys.length %2 != 0) {
			throw new RuntimeException(String.format("param length must be even but was %d", keys.length));
		}
		int size = keys.length /2;
		for(int i=0; i<size;  i++) {
			String key = (String) keys[i*2];
			Object value = keys[i*2+1];
			AttributeValue attributeValue  = convert(value);
			
			map.put(key, attributeValue);
		}
		return map;
	}

	public static AttributeValue convert(Object value) {
		AttributeValue attributeValue;
		if (value instanceof AttributeValue) {
			attributeValue  = (AttributeValue) value; 
		}
		else if (value instanceof String) {
			  attributeValue = AttributeValue.builder().s((String)value).build();
			}
			else if (value instanceof Boolean) {
				attributeValue = AttributeValue.builder().bool((Boolean)value).build();
			}
			else if (value instanceof Number) {
				attributeValue = AttributeValue.builder().n(String.valueOf(value)).build();
			}
			else if (value instanceof Set) {
				//Assuming Set of String
				Set<String> set = (Set<String>) value;
				attributeValue = AttributeValue.builder().ss(set).build();
			}
			else if (value instanceof List) {
				List<Object> list = (List<Object>) value;
				List<AttributeValue> listNew = list.stream().map((Object o) -> convert(o)).collect(Collectors.toList());
				attributeValue = AttributeValue.builder().l(listNew).build();
			}
			else if (value instanceof Map) {
				Map<String,Object> map = (Map<String, Object>) value;
				Map<String,AttributeValue> mapNew = convert(map);
				attributeValue = AttributeValue.builder().m(mapNew).build();
			}
			else if (value instanceof JsonValue) {
				attributeValue = DynamoDBHelper.convert((JsonValue) value);
			}
			else {
				attributeValue = AttributeValue.builder().s(value.toString()).build();
			}
		return attributeValue;
	}
	
	public static AttributeValue convert(JsonValue value) {
		return convert(value, null);
	}

	public static AttributeValue convert(JsonValue value, JsonObject metadata) {
		return convert(value, metadata, "$");
	}
	public static AttributeValue convert(JsonValue value, JsonObject metadata, String prefix) {
		AttributeValue attributeValue;
		ValueType jsonType = value.getValueType();
		switch (jsonType) {
			case ARRAY: {
				JsonArray array = value.asJsonArray();
				DynamoDBType type = DynamoDBType.List;
				if ((metadata != null) && metadata.containsKey(prefix)) {
					type = DynamoDBType.valueOf(((JsonString) metadata.get(prefix)).getString());
				}
				switch(type) {
					case StringSet: {
						List<String> arrayNew = IntStream.range(0, array.size()).mapToObj((int o) -> ((JsonString)array.get(o)).getString()).collect(Collectors.toList());
						attributeValue = AttributeValue.builder().ss(arrayNew).build();
						break;
					}
					case NumberSet: {
						List<String> arrayNew = IntStream.range(0, array.size()).mapToObj((int o) -> ((JsonNumber)array.get(o)).numberValue().toString()).collect(Collectors.toList());
						attributeValue = AttributeValue.builder().ns(arrayNew).build();
						break;
					}
					case BinarySet: {
						List<SdkBytes> arrayNew = IntStream.range(0, array.size()).mapToObj((int o) -> (SdkBytes.fromUtf8String(((JsonString)array.get(o)).getString()))).collect(Collectors.toList());
						attributeValue = AttributeValue.builder().bs(arrayNew).build();
						break;
					}	
					default: {
						List<AttributeValue> arrayNew = IntStream.range(0, array.size()).mapToObj((int o) -> convert(array.get(o), metadata, prefix+"[" + o + "]")).collect(Collectors.toList());
						attributeValue = AttributeValue.builder().l(arrayNew).build();
	
					}
				}
				break;
			}
			case FALSE: {
				attributeValue = AttributeValue.builder().bool(Boolean.FALSE).build();
				break;
			}
			case NULL: {
				attributeValue = AttributeValue.builder().nul(Boolean.TRUE).build();
				break;
			}
			case NUMBER: {
				JsonNumber number = (JsonNumber) value;
				attributeValue = AttributeValue.builder().n(String.valueOf(number)).build();
				break;			
			}
			case OBJECT: {
				JsonObject object = value.asJsonObject();
				Set<Entry<String, JsonValue>>  setOfEntries = object.entrySet();
				Map<String,AttributeValue> map = new HashMap<>();
				for(Entry<String, JsonValue> entry: setOfEntries) {
					map.put(entry.getKey(), convert(entry.getValue(), metadata, prefix+"."+entry.getKey()));
				}
				attributeValue = AttributeValue.builder().m(map).build();
				break;
			}
			case STRING:
				JsonString string = (JsonString) value;
				attributeValue = AttributeValue.builder().s(string.getString()).build();
				break;
			case TRUE:
				attributeValue = AttributeValue.builder().bool(Boolean.TRUE).build();
				break;
			default:
				throw new RuntimeException("Unexpected value: [" + value + "]");
		}
		return attributeValue;
	}
	
	private static Map<String, AttributeValue> convert(Map<String, Object> map) {
		Map<String, AttributeValue> result = new HashMap<>();
		for(Entry<String, Object> entry: map.entrySet()) {
			AttributeValue value =  convert(entry.getValue());
			result.put(entry.getKey(), value);
		}
		return result;
	}

	public static JsonObject metadata(AttributeValue value) {
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		metadata(value, "$", jsonObjectBuilder);
		return jsonObjectBuilder.build();
	}
	
	protected static void metadata(AttributeValue value, String prefix, JsonObjectBuilder jsonObjectBuilder) {
		if (value.hasSs()) {
			jsonObjectBuilder.add(prefix, DynamoDBType.StringSet.name());
			return;
		}
		else if (value.hasNs()) {
			jsonObjectBuilder.add(prefix, DynamoDBType.NumberSet.name());
			return;
		}
		else if (value.hasBs()) {
			jsonObjectBuilder.add(prefix, DynamoDBType.BinarySet.name());
			return;
		}
		else if (value.hasL()) {
			int i=0;
			for(AttributeValue attributeValue: value.l()) {
				metadata(attributeValue, prefix+ "[" + i + "]", jsonObjectBuilder);
				i++;
			}
			return;
		}
		else  if (value.hasM()) {
			Map<String,AttributeValue> map = value.m();
			for(Entry<String,AttributeValue> entry: map.entrySet()) {
				metadata(entry.getValue(), prefix+ "." + entry.getKey(), jsonObjectBuilder);
			}
			return;
		}
	}

}
