package io.microlam.json;

import java.util.Comparator;

import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import jakarta.json.JsonValue.ValueType;


public class JsonObjectComparator implements Comparator<JsonObject> {

	protected String property;
	protected boolean croissant;
	
	static final Comparator<Comparable> naturalOrder = Comparator.naturalOrder();
	
	public JsonObjectComparator(String property) {
		this(property,true);
	}

	public JsonObjectComparator(String property, boolean croissant) {
		this.property = property;
		this.croissant = croissant;
	}
	
	@Override
	public int compare(JsonObject o1, JsonObject o2) {
		return (croissant)?compareCroissant(o1,o2):-compareCroissant(o1,o2);
	}
	
	public int compareCroissant(JsonObject o1, JsonObject o2) {
		JsonValue v1 = o1.get(property);
		JsonValue v2 = o2.get(property);
		if ((v1.getValueType() != ValueType.ARRAY) && (v1.getValueType() != ValueType.OBJECT)) {
			if (v1.getValueType() == ValueType.NUMBER) {
				JsonNumber vp1 = (JsonNumber) v1;
				JsonNumber vp2 = (JsonNumber) v2;
				if (vp1.numberValue().doubleValue() - vp2.numberValue().doubleValue() >= 0) {
					return +1;
				}
				else {
					return -1;
				}
			}
			else if (v1.getValueType() == ValueType.STRING) {
				JsonString vp1 = (JsonString) v1;
				JsonString vp2 = (JsonString) v2;
				return naturalOrder.compare(vp1.getString(), vp2.getString());
			}
			else {
				return -1;
			}
		}
		else {
			return -1;
		}
	}

}
