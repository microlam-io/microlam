package io.microlam.json;

import java.util.List;
import java.util.Map;

import jakarta.json.JsonValue;

/**
 * Visitor Pattern for JsonValue may be useful.
 */
public interface JsonValueVisitor<T> {
    /**
     *  is invoked on a null JSON node.
     */
    T visitNull();

    /**
     * is invoked on a boolean JSON node.
     */
    T visitBoolean(boolean bool);

    /**
     *  is invoked on a number JSON node.
     */
    T visitNumber(String number);

    /**
     *  is invoked on a string JSON node.
     */
    T visitString(String string);

    /**
     *  is invoked on an array JSON node.
     */
    T visitArray(List<JsonValue> array);

    /**
     *  is invoked on an object JSON node.
     */
    T visitObject(Map<String, JsonValue> object);

    /**
     *  is invoked on an embedded object JSON node.
     */
    T visitEmbeddedObject(Object embeddedObject);
}
