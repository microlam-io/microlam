package io.microlam.aws.lambda.auth;

public class MethodArn {

	public final String methodArn;
	
	public final String region; 
	public final String awsAccountId; 
	public final String restApiId; 
	public final String stage; 
	public final String httpMethod; 
	public final String resource;
	public final boolean websocket;
	
	public MethodArn(String methodArn) {
		this.methodArn = methodArn;
    	String[] arnPartials = methodArn.split(":");
    	region = arnPartials[3];
    	awsAccountId = arnPartials[4];
    	String[] apiGatewayArnPartials = arnPartials[5].split("/");
    	restApiId = apiGatewayArnPartials[0];
    	stage = apiGatewayArnPartials[1];
    	httpMethod = apiGatewayArnPartials[2];
    	websocket = httpMethod.startsWith("$");
    	if (apiGatewayArnPartials.length == 4) {
    		resource = apiGatewayArnPartials[3];
    	}
    	else {
        	resource = ""; // root resource
    	}
	}

	public static String format(String region, String awsAccountId, String restApiId, String stage, String httpMethod, String resource) {
		return String.format("arn:aws:execute-api:%s:%s:%s/%s/%s/%s", region, awsAccountId, restApiId, stage, httpMethod, resource);
	}
	
	public String format(String resource) {
		return String.format("arn:aws:execute-api:%s:%s:%s/%s/%s/%s", region, awsAccountId, restApiId, stage, httpMethod, resource);
	}
	
	public String format(String httpMethod, String resource) {
		if (websocket) {
			return String.format("arn:aws:execute-api:%s:%s:%s/%s/%s", region, awsAccountId, restApiId, stage, httpMethod);
		}
		else {
			return String.format("arn:aws:execute-api:%s:%s:%s/%s/%s/%s", region, awsAccountId, restApiId, stage, httpMethod, resource);
		}
	}
	
	public String allowEverythingOnAccount() {
		return String.format("arn:aws:execute-api:%s:%s:%s/%s/%s/%s", region, awsAccountId, "*", "*", "*", "*");
	}

	public String everythingOnAccountForResource(String resource) {
		return String.format("arn:aws:execute-api:%s:%s:%s/%s/%s/%s", region, awsAccountId, "*", "*", "*", resource);
	}

}
