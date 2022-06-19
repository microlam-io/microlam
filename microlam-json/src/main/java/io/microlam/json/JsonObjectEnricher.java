package io.microlam.json;

import jakarta.json.JsonObject;

public interface JsonObjectEnricher {

	JsonObject enrich(JsonObject jsonObject); 

}
