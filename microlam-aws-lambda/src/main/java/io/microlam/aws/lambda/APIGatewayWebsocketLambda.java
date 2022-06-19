package io.microlam.aws.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketResponse;

public interface APIGatewayWebsocketLambda extends RequestHandler<APIGatewayV2WebSocketEvent, APIGatewayV2WebSocketResponse> {

}
