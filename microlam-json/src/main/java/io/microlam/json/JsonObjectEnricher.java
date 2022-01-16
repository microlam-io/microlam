package io.microlam.json;

import com.google.gson.JsonObject;

public interface JsonObjectEnricher {

	JsonObject enrich(JsonObject jsonObject); 

}
