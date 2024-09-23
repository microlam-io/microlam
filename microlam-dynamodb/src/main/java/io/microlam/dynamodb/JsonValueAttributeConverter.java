package io.microlam.dynamodb;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import software.amazon.awssdk.annotations.Immutable;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.annotations.ThreadSafe;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.TypeConvertingVisitor;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.EnhancedAttributeValue;
import software.amazon.awssdk.protocols.jsoncore.internal.NullJsonNode;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.utils.BinaryUtils;

/**
 * An Internal converter between JsonNode and {@link AttributeValue}.
 *
 * <p>
 * This converts the Attribute Value read from the DDB to JsonNode.
 */
@SdkInternalApi
@ThreadSafe
@Immutable
public final class JsonValueAttributeConverter implements AttributeConverter<JsonValue> {
    private static final Visitor VISITOR = new Visitor();

    private JsonValueAttributeConverter() {
    }

    public static JsonValueAttributeConverter create() {
        return new JsonValueAttributeConverter();
    }

    @Override
    public EnhancedType<JsonValue> type() {
        return EnhancedType.of(JsonValue.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.M;
    }

    @Override
    public AttributeValue transformFrom(JsonValue input) {
    	JsonValueToAttributeValueMapConverter attributeValueMapConverter = JsonValueToAttributeValueMapConverter.instance();
        return attributeValueMapConverter.visit(input);
    }

    @Override
    public JsonValue transformTo(AttributeValue input) {
        if (AttributeValue.fromNul(true).equals(input)) {
            return JsonObject.NULL;
        }
        return EnhancedAttributeValue.fromAttributeValue(input).convert(VISITOR);
    }

    private static final class Visitor extends TypeConvertingVisitor<JsonValue> {
        private Visitor() {
            super(JsonValue.class, JsonValueAttributeConverter.class);
        }

        @Override
        public JsonValue convertMap(Map<String, AttributeValue> value) {
            if (value == null) {
                return null;
            }
            Map<String, JsonValue> jsonNodeMap = new LinkedHashMap<>();
            value.entrySet().forEach(
                k -> {
                	JsonValue jsonNode = this.convert(EnhancedAttributeValue.fromAttributeValue(k.getValue()));
                    jsonNodeMap.put(k.getKey(), jsonNode == null ? JsonValue.NULL : jsonNode);
                });
            return Json.createObjectBuilder(jsonNodeMap).build();
        }

        @Override
        public JsonValue convertString(String value) {
            if (value == null) {
                return null;
            }
            return Json.createValue(value);
        }

        @Override
        public JsonValue convertNumber(String value) {
            if (value == null) {
                return null;
            }
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            try {
				return Json.createValue(numberFormat.parse(value));
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
        }

        @Override
        public JsonValue convertBytes(SdkBytes value) {
            if (value == null) {
                return null;
            }
            return Json.createValue(BinaryUtils.toBase64(value.asByteArray()));
        }

        @Override
        public JsonValue convertBoolean(Boolean value) {
            if (value == null) {
                return null;
            }
            return  (value)?JsonValue.TRUE:JsonValue.FALSE;
        }

        @Override
        public JsonValue convertSetOfStrings(List<String> value) {
            if (value == null) {
                return null;
            }
            return Json.createArrayBuilder(value.stream().map((String a) -> Json.createValue(a)).collect(Collectors.toList())).build();
        }

        @Override
        public JsonValue convertSetOfNumbers(List<String> value) {
            if (value == null) {
                return null;
            }
            return Json.createArrayBuilder(value.stream().map((String a) -> Json.createValue(a)).collect(Collectors.toList())).build();
        }

        @Override
        public JsonValue convertSetOfBytes(List<SdkBytes> value) {
            if (value == null) {
                return null;
            }
            return Json.createArrayBuilder(value.stream().map(
                sdkByte -> Json.createValue(BinaryUtils.toBase64(sdkByte.asByteArray()))
            ).collect(Collectors.toList())).build();
        }

        @Override
        public JsonValue convertListOfAttributeValues(List<AttributeValue> value) {
            if (value == null) {
                return null;
            }
            return Json.createArrayBuilder(value.stream().map(
                attributeValue -> {
                    EnhancedAttributeValue enhancedAttributeValue = EnhancedAttributeValue.fromAttributeValue(attributeValue);
                    return enhancedAttributeValue.isNull() ? NullJsonNode.instance() : enhancedAttributeValue.convert(VISITOR);
                }).collect(Collectors.toList())).build();
        }
    }
}