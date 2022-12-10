package io.microlam.json;

import javax.json.JsonObject;

public interface JsonObjectEnricher {

	JsonObject enrich(JsonObject jsonObject); 

}
