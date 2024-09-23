package io.microlam.dynamodb;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.microlam.json.JsonValueVisitor;
import jakarta.json.JsonNumber;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@SdkInternalApi
public class JsonValueToAttributeValueMapConverter implements JsonValueVisitor<AttributeValue> {

    private static final JsonValueToAttributeValueMapConverter INSTANCE = new JsonValueToAttributeValueMapConverter();

    private JsonValueToAttributeValueMapConverter() {
    }

    public static JsonValueToAttributeValueMapConverter instance() {
        return INSTANCE;
    }

    
    public AttributeValue visit(JsonValue json) {
    	switch (json.getValueType()) {
		case ARRAY:
			return visitArray(json.asJsonArray());
		case FALSE:
			return visitBoolean(false);
		case NULL:
			return visitNull();
		case NUMBER:
			JsonNumber jsonNumber = (JsonNumber) json; 
			return visitNumber(jsonNumber.numberValue().toString());
		case OBJECT:
			return visitObject(json.asJsonObject());
		case STRING:
			JsonString jsonString = (JsonString) json;
			return visitString(jsonString.getString());
		case TRUE:
			return visitBoolean(true);
		default:
			return null;
    	}
    }
    
    @Override
    public AttributeValue visitNull() {
        return AttributeValue.fromNul(true);
    }

    @Override
    public AttributeValue visitBoolean(boolean bool) {
        return AttributeValue.builder().bool(bool).build();
    }

    @Override
    public AttributeValue visitNumber(String number) {
        return AttributeValue.builder().n(number).build();
    }

    @Override
    public AttributeValue visitString(String string) {
        return AttributeValue.builder().s(string).build();
    }

    @Override
    public AttributeValue visitArray(List<JsonValue> array) {
        return AttributeValue.builder().l(array.stream()
                                               .map(node -> visit(node))
                                               .collect(Collectors.toList()))
                             .build();
    }

    @Override
    public AttributeValue visitObject(Map<String, JsonValue> object) {
        return AttributeValue.builder().m((Map<String, AttributeValue>)object.entrySet().stream()
                                                .collect(Collectors.toMap(
                                                    Map.Entry::getKey,
                                                    entry -> visit(entry.getValue()),
                                                    (left, right) -> left, LinkedHashMap::new)))
                             .build();
    }

    @Override
    public AttributeValue visitEmbeddedObject(Object embeddedObject) {
        throw new UnsupportedOperationException("Embedded objects are not supported within Document types.");
    }
}