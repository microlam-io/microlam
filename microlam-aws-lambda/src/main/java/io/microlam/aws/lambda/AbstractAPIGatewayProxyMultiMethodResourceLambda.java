package io.microlam.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

public abstract class AbstractAPIGatewayProxyMultiMethodResourceLambda extends AbstractAPIGatewayProxyMultiLambda {

	@Override
	protected String mappingKey(APIGatewayProxyRequestEvent request, Context context) {
		return request.getHttpMethod() + " " + request.getResource();
	}

}
