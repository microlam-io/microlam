package io.microlam.dynamodb.query;

import java.util.HashMap;
import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import io.microlam.dynamodb.DynamoDBHelper;
import io.microlam.dynamodb.SimpleAttributeNameGenerator;
import io.microlam.dynamodb.expr.ConditionExpression;
import io.microlam.dynamodb.expr.UpdateExpression;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ReturnConsumedCapacity;
import software.amazon.awssdk.services.dynamodb.model.ReturnItemCollectionMetrics;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

public class UpdateItemRequestBuilder {
	
		private String tableName = null;
		private ReturnValue returnValue = null;
		private Map<String, AttributeValue> key = null;
		private UpdateExpression updateExpression = null;
		private ConditionExpression conditionExpression = null;
		private ReturnConsumedCapacity returnConsumedCapacity = null;
		private ReturnItemCollectionMetrics returnItemCollectionMetrics = null;
		private String partitionKeyName = null;
		private Object partitionKeyValue = null;
		private String sortKeyName = null;
		private Object sortKeyValue = null;

		private UpdateItemRequestBuilder() {
		}

		public UpdateItemRequestBuilder tableName(String tablename) {
			this.tableName = tablename;
			return this;
		}

		public UpdateItemRequestBuilder partitionKey(String partitionKeyName, Object partitionKeyValue) {
			this.partitionKeyName = partitionKeyName;
			this.partitionKeyValue = partitionKeyValue;
			return this;
		}

		public UpdateItemRequestBuilder sortKey(String sortKeyName, Object sortKeyValue) {
			this.sortKeyName = sortKeyName;
			this.sortKeyValue = sortKeyValue;
			return this;
		}

		public UpdateItemRequestBuilder returnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity) {
			this.returnConsumedCapacity = returnConsumedCapacity;
			return this;
		}

		public UpdateItemRequestBuilder returnItemCollectionMetrics(ReturnItemCollectionMetrics returnItemCollectionMetrics) {
			this.returnItemCollectionMetrics = returnItemCollectionMetrics;
			return this;
		}

		public UpdateItemRequestBuilder returnValues(ReturnValue returnValue) {
			this.returnValue = returnValue;
			return this;
		}

		public UpdateItemRequestBuilder conditionExpression(ConditionExpression conditionExpression ) {
			this.conditionExpression = conditionExpression;
			return this;
		}
		
		public UpdateItemRequestBuilder updateExpression(UpdateExpression updateExpression ) {
			this.updateExpression = updateExpression;
			return this;
		}

		public static UpdateItemRequestBuilder builder() {
			return new UpdateItemRequestBuilder();
		}
		
		public UpdateItemRequest build() {
			AttributeNameGenerator attributeNameGenerator = new SimpleAttributeNameGenerator("n");
			Map<String,String> attributeNames = new HashMap<String, String>();
			Map<String,AttributeValue> attributeValues = new HashMap<>();
			
			UpdateItemRequest.Builder builder = UpdateItemRequest.builder();
						
			if (conditionExpression != null) {
				builder.conditionExpression(conditionExpression.process(attributeValues, attributeNames, attributeNameGenerator));
			}

			if (updateExpression != null) {
				builder.updateExpression(updateExpression.process(attributeValues, attributeNames, attributeNameGenerator));
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
			if (partitionKeyName != null) {
				key = DynamoDBHelper.createAttributeValueMap(partitionKeyName, partitionKeyValue);
				if (sortKeyName != null) {
					DynamoDBHelper.updateAttributeValueMap(key, sortKeyName, sortKeyValue);
				}
				builder.key(key);
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
