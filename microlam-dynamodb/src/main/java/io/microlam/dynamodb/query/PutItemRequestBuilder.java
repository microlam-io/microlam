package io.microlam.dynamodb.query;

import java.util.HashMap;
import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import io.microlam.dynamodb.SimpleAttributeNameGenerator;
import io.microlam.dynamodb.expr.ConditionExpression;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ReturnConsumedCapacity;
import software.amazon.awssdk.services.dynamodb.model.ReturnItemCollectionMetrics;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;

public class PutItemRequestBuilder {
	
		private String tableName = null;
		private ReturnValue returnValue = null;
		private Map<String,AttributeValue> item = null;
		private ConditionExpression conditionExpression = null;
		private ReturnConsumedCapacity returnConsumedCapacity = null;
		private ReturnItemCollectionMetrics returnItemCollectionMetrics = null;

		private PutItemRequestBuilder() {
		}

		public PutItemRequestBuilder tableName(String tablename) {
			this.tableName = tablename;
			return this;
		}

		public PutItemRequestBuilder returnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity) {
			this.returnConsumedCapacity = returnConsumedCapacity;
			return this;
		}

		public PutItemRequestBuilder returnItemCollectionMetrics(ReturnItemCollectionMetrics returnItemCollectionMetrics) {
			this.returnItemCollectionMetrics = returnItemCollectionMetrics;
			return this;
		}

		public PutItemRequestBuilder returnValues(ReturnValue returnValue) {
			this.returnValue = returnValue;
			return this;
		}

		public PutItemRequestBuilder conditionExpression(ConditionExpression conditionExpression ) {
			this.conditionExpression = conditionExpression;
			return this;
		}
		
		public PutItemRequestBuilder item(Map<String,AttributeValue> item ) {
			this.item = item;
			return this;
		}

		public static PutItemRequestBuilder builder() {
			return new PutItemRequestBuilder();
		}
		
		public PutItemRequest build() {
			AttributeNameGenerator attributeNameGenerator = new SimpleAttributeNameGenerator("n");
			Map<String,String> attributeNames = new HashMap<String, String>();
			Map<String,AttributeValue> attributeValues = new HashMap<>();
			
			PutItemRequest.Builder builder = PutItemRequest.builder();
						
			if (item != null) {
				builder.item(item);
			}
			
			if (conditionExpression != null) {
				builder.conditionExpression(conditionExpression.process(attributeValues, attributeNames, attributeNameGenerator));
			}


			if (returnConsumedCapacity != null) {
				builder.returnConsumedCapacity(returnConsumedCapacity);
			}
			
			if (returnItemCollectionMetrics != null) {
				builder.returnItemCollectionMetrics(returnItemCollectionMetrics);
			}
			
			if (returnValue != null) {
				builder.returnValues(returnValue);
			}

			if (tableName != null) {
				builder.tableName(tableName);
			}

			if (! attributeNames.isEmpty()) {
				builder.expressionAttributeNames(attributeNames);
			}

			if (! attributeValues.isEmpty()) {
				builder.expressionAttributeValues(attributeValues);
			}

			return builder.build();
		}
	
	
}
