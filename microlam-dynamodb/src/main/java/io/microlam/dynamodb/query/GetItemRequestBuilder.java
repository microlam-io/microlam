package io.microlam.dynamodb.query;

import java.util.HashMap;
import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import io.microlam.dynamodb.DynamoDBHelper;
import io.microlam.dynamodb.SimpleAttributeNameGenerator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ReturnConsumedCapacity;

public class GetItemRequestBuilder {
	
		private Boolean consistentRead = null;
		private String tableName = null;
		private Map<String, AttributeValue> key = null;
		private String[] projectionPath = null;
		private ReturnConsumedCapacity returnConsumedCapacity = null;
		private String partitionKeyName = null;
		private Object partitionKeyValue = null;
		private String sortKeyName = null;
		private Object sortKeyValue = null;

		private GetItemRequestBuilder() {
		}

		public GetItemRequestBuilder consistentRead(Boolean consistentRead) {
			this.consistentRead = consistentRead;
			return this;
		}

		public GetItemRequestBuilder tableName(String tablename) {
			this.tableName = tablename;
			return this;
		}

		public GetItemRequestBuilder partitionKey(String partitionKeyName, Object partitionKeyValue) {
			this.partitionKeyName = partitionKeyName;
			this.partitionKeyValue = partitionKeyValue;
			return this;
		}

		public GetItemRequestBuilder sortKey(String sortKeyName, Object sortKeyValue) {
			this.sortKeyName = sortKeyName;
			this.sortKeyValue = sortKeyValue;
			return this;
		}

		public GetItemRequestBuilder projectionPaths(String... projectionPath) {
			this.projectionPath = projectionPath;
			return this;
		}

		public GetItemRequestBuilder returnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity) {
			this.returnConsumedCapacity = returnConsumedCapacity;
			return this;
		}

		
		public static GetItemRequestBuilder builder() {
			return new GetItemRequestBuilder();
		}
		
		public GetItemRequest build() {
			AttributeNameGenerator attributeNameGenerator = new SimpleAttributeNameGenerator("n");
			Map<String,String> attributeNames = new HashMap<String, String>();
			StringBuffer projectionExpression = new StringBuffer();
			
			GetItemRequest.Builder builder = GetItemRequest.builder();
			
			if (projectionPath != null) {
				for(int i=0; i<projectionPath.length; i++) {
					String newName = DynamoDBHelper.verifyReservedName(projectionPath[i], attributeNames, attributeNameGenerator);
					if (i > 0) {
						projectionExpression.append(", ");
					}
					projectionExpression.append(newName);
				}
			}
			
			if (projectionExpression.length() != 0) {
				builder.projectionExpression(projectionExpression.toString());
			}
			
			if (! attributeNames.isEmpty()) {
				builder.expressionAttributeNames(attributeNames);
			}

			if (consistentRead != null) {
				builder.consistentRead(consistentRead);
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
			if (returnConsumedCapacity != null) {
				builder.returnConsumedCapacity(returnConsumedCapacity);
			}
			return builder.build();
		}
	
	
}
