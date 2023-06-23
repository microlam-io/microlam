package io.microlam.dynamodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class JsonLaunch {
	
	@Test
	public void testJson() {
		JsonObject object = Json.createObjectBuilder()
			.add("test1", "value1")
			.add("test2", true)
			.add("test3", 0)
			.add("test4", Json.createArrayBuilder().add(1).add(2))
			.build();
		
		AttributeValue value = DynamoDBHelper.convert(object);
		
		System.out.println("value: " + value);
		
		List<String> list = new ArrayList<>();
		list.add("test1");
		list.add("test2");
		AttributeValue v = AttributeValue.fromSs(list);
		
		HashMap<String, AttributeValue> map = new HashMap<>();
		map.put("test1", AttributeValue.builder().s("test1").build());
		map.put("test2", v);
		AttributeValue v2 = AttributeValue.builder().m(map).build();
		JsonObject metadata = DynamoDBHelper.metadata(v2);
		
		System.out.println("metadata: " + metadata);
	}

}
