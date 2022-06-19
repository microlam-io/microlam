package io.microlam.dynamodb.pipeline.basic;

import java.util.List;
import java.util.Map;

import io.microlam.dynamodb.pipeline.GenericResponse;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

public class ScanGenericResponse implements GenericResponse {

	ScanResponse scanResponse;
	
	ScanGenericResponse(ScanResponse scanResponse) {
		this.scanResponse = scanResponse;
	}
	
	@Override
	public Map<String, AttributeValue> lastEvaluatedKey() {
		return scanResponse.lastEvaluatedKey();
	}

	@Override
	public List<Map<String, AttributeValue>> items() {
		return scanResponse.items();
	}

	@Override
	public int count() {
		return scanResponse.count();
	}

}
