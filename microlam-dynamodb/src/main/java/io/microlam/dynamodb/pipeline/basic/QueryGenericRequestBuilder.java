package io.microlam.dynamodb.pipeline.basic;

import java.util.Map;

import io.microlam.dynamodb.pipeline.GenericRequest;
import io.microlam.dynamodb.pipeline.GenericRequestBuilder;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.Select;

public class QueryGenericRequestBuilder implements GenericRequestBuilder {

	final String name;
	final QueryRequest.Builder builder;
	final String[] lastKeyAttributes;
	
	public QueryGenericRequestBuilder(String name, QueryRequest.Builder builder, String... lastKeyAttributes) {
		this.name = name;
		this.builder = builder;
		this.lastKeyAttributes = lastKeyAttributes;
	}

	@Override
	public GenericRequestBuilder exclusiveStartKey(Map<String, AttributeValue> lastkey) {
		builder.exclusiveStartKey(lastkey);
		return this;
	}

	@Override
	public GenericRequest build() {
		return new QueryGenericRequest(builder.build());
	}

	@Override
	public String[] lastKeyAttributes() {
		return lastKeyAttributes;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public GenericRequestBuilder count() {
		builder.select(Select.COUNT);
		return this;
	}
	
}
