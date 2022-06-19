package io.microlam.dynamodb.query;

import static io.microlam.dynamodb.expr.ConditionExpression.and;
import static io.microlam.dynamodb.expr.ConditionExpression.attributeExists;
import static io.microlam.dynamodb.expr.Operand.numberValue;
import static io.microlam.dynamodb.expr.Operand.stringValue;
import static io.microlam.dynamodb.expr.UpdateExpression.addExpression;
import static io.microlam.dynamodb.expr.UpdateExpression.addIncrementExpression;
import static io.microlam.dynamodb.expr.UpdateExpression.assignmentExpression;
import static io.microlam.dynamodb.expr.UpdateExpression.removeExpression;
import static io.microlam.dynamodb.expr.UpdateExpression.setExpression;
import static io.microlam.dynamodb.expr.UpdateExpression.setRemoveAddDeleteExpression;

import java.util.Map;

import org.junit.Test;

import io.microlam.aws.auth.AwsProfileRegionClientConfigurator;
import io.microlam.dynamodb.expr.ComparatorOperator;
import io.microlam.dynamodb.expr.ConditionExpression;
import io.microlam.dynamodb.expr.Operand;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemResponse;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;
import software.amazon.awssdk.services.dynamodb.model.ReturnConsumedCapacity;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

public class QueryLaunch {

	@Test
	public void testGetItem() {
	   	AwsProfileRegionClientConfigurator.setProfile("premiumpay");
	 	AwsProfileRegionClientConfigurator.setRegion(Region.EU_WEST_1);

	 	DynamoDbClient client = AwsProfileRegionClientConfigurator.getInstance().configure(DynamoDbClient.builder()).build();

		GetItemRequest request = GetItemRequestBuilder.builder()
			.tableName("TEST_GENERIC")
			.partitionKey("PK1", "pk1")
			.sortKey("SK1", "sk1")
			.projectionPaths("PK1", "email", "full_name", "sector_name", "position")
			.build();
		
		GetItemResponse response = client.getItem(request);
		Map<String,AttributeValue> item = response.item();
		System.out.println(item);
	}
	
	@Test
	public void testDeleteItem() {
	   	AwsProfileRegionClientConfigurator.setProfile("premiumpay");
	 	AwsProfileRegionClientConfigurator.setRegion(Region.EU_WEST_1);

	 	DynamoDbClient client = AwsProfileRegionClientConfigurator.getInstance().configure(DynamoDbClient.builder()).build();

	 	DeleteItemRequest request = DeleteItemRequestBuilder.builder()
			.tableName("TEST_GENERIC")
			.partitionKey("PK1", "pk1")
			.sortKey("SK1", "sk1")
			.conditionExpression(and(attributeExists("PK1"), attributeExists("SK1")))
			.build();
		
	 	DeleteItemResponse response = client.deleteItem(request);
		System.out.println(response.sdkHttpResponse().isSuccessful());
	}
	
	//@Test
	public void testQueryItem() {
	   	AwsProfileRegionClientConfigurator.setProfile("premiumpay");
	 	AwsProfileRegionClientConfigurator.setRegion(Region.EU_WEST_1);

	 	DynamoDbClient client = AwsProfileRegionClientConfigurator.getInstance().configure(DynamoDbClient.builder()).build();

	 	QueryRequest.Builder requestBuilder = QueryRequestBuilderBuilder.builder()
			.tableName("TEST_GENERIC")
			//.indexName("indexName")
			.keyConditionExpression(ConditionExpression.comparison(Operand.attributePath("PK1"), ComparatorOperator.EQUAL, Operand.stringValue("pk1")))
			.filterExpression(ConditionExpression.attributeExists("email"))
			//.consistentRead(true)
			//.exclusiveStartKey(null)
			.limit(10)
			.returnConsumedCapacity(ReturnConsumedCapacity.INDEXES)
			.scanIndexForward(true)
			.projectionPaths("email", "PK1", "SK1")
			.build();
		
	 	QueryResponse response;
	 	do {
		 	response = client.query(requestBuilder.build());
		 	for(Map<String, AttributeValue> item: response.items()) {
		 		System.out.println(item);
		 	}
		 	requestBuilder.exclusiveStartKey(response.lastEvaluatedKey());
	 	}
	 	while (! response.lastEvaluatedKey().isEmpty()) ;
	}

	@Test
	public void testUpdateItem() {
	   	AwsProfileRegionClientConfigurator.setProfile("premiumpay");
	 	AwsProfileRegionClientConfigurator.setRegion(Region.EU_WEST_1);

	 	DynamoDbClient client = AwsProfileRegionClientConfigurator.getInstance().configure(DynamoDbClient.builder()).build();

	 	UpdateItemRequest request = UpdateItemRequestBuilder.builder()
			.tableName("TEST_GENERIC")
			.partitionKey("PK1", "pk1")
			.sortKey("SK1", "sk1")
			.conditionExpression(and(attributeExists("PK1"), attributeExists("SK1")))
			.updateExpression(setRemoveAddDeleteExpression(setExpression(assignmentExpression("email", stringValue("frank.afriat@gmail.com"))), removeExpression("surname"), addExpression(addIncrementExpression("i", numberValue(1))), null))		
			.build();
	 	
	 	System.out.println(request.updateExpression());
		
	 	UpdateItemResponse response = client.updateItem(request);
		System.out.println(response.sdkHttpResponse().isSuccessful());
	}

}
