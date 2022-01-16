package io.microlam.json;

import java.util.Comparator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

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
		JsonElement v1 = o1.get(property);
		JsonElement v2 = o2.get(property);
		if (v1.isJsonPrimitive()) {
			JsonPrimitive vp1 = v1.getAsJsonPrimitive();
			JsonPrimitive vp2 = v2.getAsJsonPrimitive();
			if (vp1.isNumber()) {
				if (vp1.getAsNumber().doubleValue() - vp2.getAsNumber().doubleValue() >= 0) {
					return +1;
				}
				else {
					return -1;
				}
			}
			else if (vp1.isString()) {
				return naturalOrder.compare(vp1.getAsString(), vp2.getAsString());
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
