package io.microlam.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketEvent;

public abstract class AbstractAPIGatewayWebsocketProxyMultiRouteKeyLambda extends AbstractAPIGatewayWebsocketProxyMultiLambda {

	@Override
	protected String mappingKey(APIGatewayV2WebSocketEvent request, Context context) {
		return request.getRequestContext().getRouteKey();
	}

}
