package io.microlam.dynamodb.query;

import java.util.HashMap;
import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import io.microlam.dynamodb.DynamoDBHelper;
import io.microlam.dynamodb.SimpleAttributeNameGenerator;
import io.microlam.dynamodb.expr.ConditionExpression;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ReturnConsumedCapacity;
import software.amazon.awssdk.services.dynamodb.model.ReturnItemCollectionMetrics;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;

public class DeleteItemRequestBuilder {
	
		private String tableName = null;
		private ReturnValue returnValue = null;
		private Map<String, AttributeValue> key = null;
		private ConditionExpression conditionExpression = null;
		private ReturnConsumedCapacity returnConsumedCapacity = null;
		private ReturnItemCollectionMetrics returnItemCollectionMetrics = null;
		private String partitionKeyName = null;
		private Object partitionKeyValue = null;
		private String sortKeyName = null;
		private Object sortKeyValue = null;

		private DeleteItemRequestBuilder() {
		}

		public DeleteItemRequestBuilder tableName(String tablename) {
			this.tableName = tablename;
			return this;
		}

		public DeleteItemRequestBuilder partitionKey(String partitionKeyName, Object partitionKeyValue) {
			this.partitionKeyName = partitionKeyName;
			this.partitionKeyValue = partitionKeyValue;
			return this;
		}

		public DeleteItemRequestBuilder sortKey(String sortKeyName, Object sortKeyValue) {
			this.sortKeyName = sortKeyName;
			this.sortKeyValue = sortKeyValue;
			return this;
		}

		public DeleteItemRequestBuilder returnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity) {
			this.returnConsumedCapacity = returnConsumedCapacity;
			return this;
		}

		public DeleteItemRequestBuilder returnItemCollectionMetrics(ReturnItemCollectionMetrics returnItemCollectionMetrics) {
			this.returnItemCollectionMetrics = returnItemCollectionMetrics;
			return this;
		}

		public DeleteItemRequestBuilder returnValues(ReturnValue returnValue) {
			this.returnValue = returnValue;
			return this;
		}

		public DeleteItemRequestBuilder conditionExpression(ConditionExpression conditionExpression ) {
			this.conditionExpression = conditionExpression;
			return this;
		}
		
		public static DeleteItemRequestBuilder builder() {
			return new DeleteItemRequestBuilder();
		}
		
		public DeleteItemRequest build() {
			AttributeNameGenerator attributeNameGenerator = new SimpleAttributeNameGenerator("n");
			Map<String,String> attributeNames = new HashMap<String, String>();
			Map<String,AttributeValue> attributeValues = new HashMap<>();
			
			DeleteItemRequest.Builder builder = DeleteItemRequest.builder();
			
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
