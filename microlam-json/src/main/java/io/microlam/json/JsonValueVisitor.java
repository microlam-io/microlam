package io.microlam.json;

import java.util.List;
import java.util.Map;

import jakarta.json.JsonValue;

/**
 * Converter from a {@link JsonNode} to a new type. This is usually invoked via {@link JsonNode#visit(JsonNodeVisitor)}.
 */
public interface JsonValueVisitor<T> {
    /**
     * Invoked if {@link JsonNode#visit(JsonNodeVisitor)} is invoked on a null JSON node.
     */
    T visitNull();

    /**
     * Invoked if {@link JsonNode#visit(JsonNodeVisitor)} is invoked on a boolean JSON node.
     */
    T visitBoolean(boolean bool);

    /**
     * Invoked if {@link JsonNode#visit(JsonNodeVisitor)} is invoked on a number JSON node.
     */
    T visitNumber(String number);

    /**
     * Invoked if {@link JsonNode#visit(JsonNodeVisitor)} is invoked on a string JSON node.
     */
    T visitString(String string);

    /**
     * Invoked if {@link JsonNode#visit(JsonNodeVisitor)} is invoked on an array JSON node.
     */
    T visitArray(List<JsonValue> array);

    /**
     * Invoked if {@link JsonNode#visit(JsonNodeVisitor)} is invoked on an object JSON node.
     */
    T visitObject(Map<String, JsonValue> object);

    /**
     * Invoked if {@link JsonNode#visit(JsonNodeVisitor)} is invoked on an embedded object JSON node.
     */
    T visitEmbeddedObject(Object embeddedObject);
}
