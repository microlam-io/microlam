package io.microlam.json;

import jakarta.json.JsonObject;

public class JsonObjectMultipleEnricher implements JsonObjectEnricher {

	public final JsonObjectEnricher[] enrichers;
	
	public JsonObjectMultipleEnricher(JsonObjectEnricher... enrichers) {
		this.enrichers = enrichers;
	}
	
	@Override
	public JsonObject enrich(JsonObject jsonObject) {
		JsonObject  current = jsonObject;
		for(JsonObjectEnricher enricher: enrichers) {
			current = enricher.enrich(current);
		}
		return current;
	}

}
