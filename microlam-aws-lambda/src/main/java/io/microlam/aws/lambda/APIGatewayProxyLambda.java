package io.microlam.aws.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public interface APIGatewayProxyLambda extends RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

}
