package io.microlam.dynamodb.query;

import java.util.HashMap;
import java.util.Map;

import io.microlam.dynamodb.AttributeNameGenerator;
import io.microlam.dynamodb.DynamoDBHelper;
import io.microlam.dynamodb.SimpleAttributeNameGenerator;
import io.microlam.dynamodb.expr.ConditionExpression;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ReturnConsumedCapacity;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.Select;

public class ScanRequestBuilderBuilder {
	
		private Boolean consistentRead = null;
		private String tableName = null;
		private String indexName = null;
		private Map<String, AttributeValue> exclusiveStartKey = null;
		private String[] projectionPath = null;
		private ReturnConsumedCapacity returnConsumedCapacity = null;
		private ConditionExpression filterExpression = null;
		private Integer limit;
		private Select select;
		private Integer segment;
		private Integer totalSegments;

		private ScanRequestBuilderBuilder() {
		}

		public ScanRequestBuilderBuilder consistentRead(boolean consistentRead) {
			this.consistentRead = consistentRead;
			return this;
		}

		public ScanRequestBuilderBuilder tableName(String tablename) {
			this.tableName = tablename;
			return this;
		}

		public ScanRequestBuilderBuilder indexName(String indexName) {
			this.indexName = indexName;
			return this;
		}
		
		public ScanRequestBuilderBuilder exclusiveStartKey(Map<String, AttributeValue> exclusiveStartKey) {
			this.exclusiveStartKey = exclusiveStartKey;
			return this;
		}

		public ScanRequestBuilderBuilder select(Select select) {
			this.select = select;
			return this;
		}

		public ScanRequestBuilderBuilder limit(int limit) {
			this.limit = limit;
			return this;
		}

		public ScanRequestBuilderBuilder segment(int segment) {
			this.segment = segment;
			return this;
		}

		public ScanRequestBuilderBuilder totalSegments(int totalSegments) {
			this.totalSegments = totalSegments;
			return this;
		}

		public ScanRequestBuilderBuilder filterExpression(ConditionExpression filterExpression ) {
			this.filterExpression = filterExpression;
			return this;
		}

		public ScanRequestBuilderBuilder projectionPaths(String... projectionPath) {
			this.projectionPath = projectionPath;
			return this;
		}

		public ScanRequestBuilderBuilder returnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity) {
			this.returnConsumedCapacity = returnConsumedCapacity;
			return this;
		}

		
		public static ScanRequestBuilderBuilder builder() {
			return new ScanRequestBuilderBuilder();
		}
		
		public ScanRequest.Builder build() {
			AttributeNameGenerator attributeNameGenerator = new SimpleAttributeNameGenerator("n");
			Map<String,String> attributeNames = new HashMap<String, String>();
			Map<String,AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
			StringBuffer projectionExpression = new StringBuffer();
			
			ScanRequest.Builder builder = ScanRequest.builder();
			
			for(int i=0; i<projectionPath.length; i++) {
				String newName = DynamoDBHelper.verifyReservedName(projectionPath[i], attributeNames, attributeNameGenerator);
				if (i > 0) {
					projectionExpression.append(", ");
				}
				projectionExpression.append(newName);
			}
			
			if (projectionExpression.length() != 0) {
				builder.projectionExpression(projectionExpression.toString());
			}

			if (consistentRead != null) {
				builder.consistentRead(consistentRead);
			}
			if (tableName != null) {
				builder.tableName(tableName);
			}
			if (indexName != null) {
				builder.indexName(indexName);
			}
			if (filterExpression != null) {
				builder.filterExpression(filterExpression.process(attributeValues, attributeNames, attributeNameGenerator));
			}
			if (returnConsumedCapacity != null) {
				builder.returnConsumedCapacity(returnConsumedCapacity);
			}

			if (limit != null) {
				builder.limit(limit);
			}

			if (exclusiveStartKey != null) {
				builder.exclusiveStartKey(exclusiveStartKey);
			}
						
			if (select != null) {
				builder.select(select);
			}
			
			if (segment != null) {
				builder.segment(segment);
			}
			
			if (totalSegments != null) {
				builder.totalSegments(totalSegments);
			}
			
			if (! attributeNames.isEmpty()) {
				builder.expressionAttributeNames(attributeNames);
			}
			
			if (! attributeValues.isEmpty()) {
				builder.expressionAttributeValues(attributeValues);
			}
			
			return builder;
		}
	
	
}
