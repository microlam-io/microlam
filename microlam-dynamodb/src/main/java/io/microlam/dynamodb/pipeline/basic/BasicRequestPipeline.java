package io.microlam.dynamodb.pipeline.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.microlam.dynamodb.pipeline.GenericPipeline;
import io.microlam.dynamodb.pipeline.GenericPipelineResult;
import io.microlam.dynamodb.pipeline.GenericRequestBuilder;
import io.microlam.dynamodb.pipeline.GenericResponse;
import io.microlam.dynamodb.pipeline.ItemProcessor;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class BasicRequestPipeline implements GenericPipeline {

	final DynamoDbClient client;
	final List<GenericRequestBuilder> requests;
	
	public BasicRequestPipeline(DynamoDbClient client, List<GenericRequestBuilder> requests) {
		this.client = client;
		this.requests = requests;
	}
	
	
	
	@Override
	public int count(int limit, String lastKeyString) {
		int pipelineIndex = 0;
		Map<String,AttributeValue> lastKey = null;
		int processCount = 0;

		String[] lastKeyParts = null;
		if (lastKeyString != null) {
			lastKeyParts = lastKeyString.split("#");
			pipelineIndex = Integer.parseInt(lastKeyParts[0]);
			lastKey = parseLastKey(lastKeyParts[1], requests.get(pipelineIndex).lastKeyAttributes());
		}
		while(pipelineIndex < requests.size()) {
			
			if (lastKey != null) {
				requests.get(pipelineIndex).exclusiveStartKey(lastKey);
			}
			
			GenericResponse response = requests.get(pipelineIndex).count().build().execute(client);
			int count = response.count();
			processCount += count;
			if (limit != -1) {
				if (limit <= processCount) {
					return limit;
				}
			}
			
			lastKey = response.lastEvaluatedKey();
			
			if (lastKey.isEmpty()) {
				pipelineIndex++;
				lastKey = null;				
			}
			
		}
		return processCount;
	}
	
	@Override
	public GenericPipelineResult process(ItemProcessor itemProcessor, int limit, String lastKeyString) {
		int pipelineIndex = 0;
		Map<String,AttributeValue> lastKey = null;
		int processCount = 0;

		String[] lastKeyParts = null;
		if ((lastKeyString != null) && (lastKeyString.length() > 0)) {
			lastKeyParts = lastKeyString.split("#");
			pipelineIndex = Integer.parseInt(lastKeyParts[0]);
			lastKey = parseLastKey(lastKeyParts[1], requests.get(pipelineIndex).lastKeyAttributes());
		}
		while(pipelineIndex < requests.size()) {
			
			if (lastKey != null) {
				requests.get(pipelineIndex).exclusiveStartKey(lastKey);
			}
			
			GenericResponse response = requests.get(pipelineIndex).build().execute(client);
			for(Map<String, AttributeValue> item: response.items()) {
				processCount++;
				itemProcessor.process(requests.get(pipelineIndex).name(), item);
				if (limit != -1) {
					if (limit <= processCount) {
						return new GenericPipelineResult(processCount, pipelineIndex + "#" + generateLastKey(item, requests.get(pipelineIndex).lastKeyAttributes()));
					}
				}
			}
			
			lastKey = response.lastEvaluatedKey();
			
			if (lastKey.isEmpty()) {
				pipelineIndex++;
				lastKey = null;				
			}
			
		}
		return new GenericPipelineResult(processCount, null);
	}

	
	protected static Map<String, AttributeValue> parseLastKey(String lastkey, String... attributes) {
		String[] values = lastkey.split("\\|");
		Map<String, AttributeValue> map = new HashMap<>();
		int i=0;
		for(String attribute : attributes) {
			String value = values[i];
			map.put(attribute, AttributeValue.builder().s(value).build());
			i++;
		}
		return map;
	}

	protected static String generateLastKey(Map<String, AttributeValue> item, String... attributes) {
		StringBuffer stringBuffer = new StringBuffer();
		boolean start = true;
		for(String attribute : attributes) {
			if (! start) {
				stringBuffer.append("|");
			}
			else  {
				start = false;
			}
			stringBuffer.append(item.get(attribute).s());
		}
		return stringBuffer.toString();
	}

}
