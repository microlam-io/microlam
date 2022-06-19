package io.microlam.dynamodb.query;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.microlam.dynamodb.AttributeNameGenerator;
import io.microlam.dynamodb.DynamoDBHelper;
import io.microlam.dynamodb.SimpleAttributeNameGenerator;
import io.microlam.dynamodb.expr.ConditionExpression;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.ReturnConsumedCapacity;
import software.amazon.awssdk.services.dynamodb.model.Select;

public class QueryRequestBuilderBuilder {
	
	private static Logger LOGGER = LoggerFactory.getLogger(QueryRequestBuilderBuilder.class);

	
		private Boolean consistentRead = null;
		private String tableName = null;
		private String indexName = null;
		private Map<String, AttributeValue> exclusiveStartKey = null;
		private String[] projectionPath = null;
		private ReturnConsumedCapacity returnConsumedCapacity = null;
		private ConditionExpression keyConditionExpression = null;
		private ConditionExpression filterExpression = null;
		private Integer limit;
		private Boolean scanIndexForward;
		private Select select;

		private QueryRequestBuilderBuilder() {
		}

		public QueryRequestBuilderBuilder consistentRead(boolean consistentRead) {
			this.consistentRead = consistentRead;
			return this;
		}

		public QueryRequestBuilderBuilder tableName(String tablename) {
			this.tableName = tablename;
			return this;
		}

		public QueryRequestBuilderBuilder indexName(String indexName) {
			this.indexName = indexName;
			return this;
		}
		
		public QueryRequestBuilderBuilder exclusiveStartKey(Map<String, AttributeValue> exclusiveStartKey) {
			this.exclusiveStartKey = exclusiveStartKey;
			return this;
		}

		public QueryRequestBuilderBuilder scanIndexForward(boolean scanIndexForward) {
			this.scanIndexForward = scanIndexForward;
			return this;
		}

		public QueryRequestBuilderBuilder select(Select select) {
			this.select = select;
			return this;
		}

		public QueryRequestBuilderBuilder limit(int limit) {
			this.limit = limit;
			return this;
		}

		public QueryRequestBuilderBuilder keyConditionExpression(ConditionExpression keyConditionExpression ) {
			this.keyConditionExpression = keyConditionExpression;
			return this;
		}

		public QueryRequestBuilderBuilder filterExpression(ConditionExpression filterExpression ) {
			this.filterExpression = filterExpression;
			return this;
		}

		public QueryRequestBuilderBuilder projectionPaths(String... projectionPath) {
			this.projectionPath = projectionPath;
			return this;
		}

		public QueryRequestBuilderBuilder returnConsumedCapacity(ReturnConsumedCapacity returnConsumedCapacity) {
			this.returnConsumedCapacity = returnConsumedCapacity;
			return this;
		}

		
		public static QueryRequestBuilderBuilder builder() {
			return new QueryRequestBuilderBuilder();
		}
		
		public QueryRequest.Builder build() {
			AttributeNameGenerator attributeNameGenerator = new SimpleAttributeNameGenerator("n");
			Map<String,String> attributeNames = new HashMap<String, String>();
			Map<String,AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
			StringBuffer projectionExpression = new StringBuffer();
			
			QueryRequest.Builder builder = QueryRequest.builder();
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
				String projectionExpressionFull = projectionExpression.toString();				
				builder.projectionExpression(projectionExpressionFull);
				LOGGER.debug("projectionExpression: {}", projectionExpressionFull);
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
			if (keyConditionExpression != null) {
				String keyConditionExpressionFull = keyConditionExpression.process(attributeValues, attributeNames, attributeNameGenerator);
				builder.keyConditionExpression(keyConditionExpressionFull);
				LOGGER.debug("keyConditionExpression: {}", keyConditionExpressionFull);
			}
			if (filterExpression != null) {
				String filterExpressionFull = filterExpression.process(attributeValues, attributeNames, attributeNameGenerator);
				builder.filterExpression(filterExpressionFull);
				LOGGER.debug("filterExpression: {}", filterExpressionFull);
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
			
			if (scanIndexForward != null) {
				builder.scanIndexForward(scanIndexForward);
			}
			
			if (select != null) {
				builder.select(select);
			}
			
			if (! attributeNames.isEmpty()) {
				builder.expressionAttributeNames(attributeNames);
				LOGGER.debug("attributeNames: {}", attributeNames);
			}
			
			if (! attributeValues.isEmpty()) {
				builder.expressionAttributeValues(attributeValues);
				LOGGER.debug("attributeValues: {}", attributeValues);
			}
			
			return builder;
		}
	
	
}
