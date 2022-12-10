package io.microlam.aws.lambda;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2WebSocketResponse;

import javax.json.Json;
import javax.json.JsonObjectBuilder;


public abstract class AbstractAPIGatewayWebsocketProxyMultiLambda implements APIGatewayWebsocketLambda {

	private static Logger LOGGER = LoggerFactory.getLogger(AbstractAPIGatewayWebsocketProxyMultiLambda.class);

	Map<String,APIGatewayWebsocketLambda> mapping;
	
	public AbstractAPIGatewayWebsocketProxyMultiLambda() {
		mapping = new HashMap<String, APIGatewayWebsocketLambda>();
		registerAllLambda();
	}

	protected void registerLambda(String mappingKey, APIGatewayWebsocketLambda lambda) {
		mapping.put(mappingKey, lambda);
	}

	protected abstract void registerAllLambda();
	protected abstract String mappingKey(APIGatewayV2WebSocketEvent request, Context context);

	@Override
	public APIGatewayV2WebSocketResponse handleRequest(APIGatewayV2WebSocketEvent input, Context context) {
		try {
			APIGatewayWebsocketLambda lambda = null;
			String mappingKey = mappingKey(input, context);
			if (mappingKey == null) {
				JsonObjectBuilder output = Json.createObjectBuilder();
				output.add("result", "error");
				String message = "mappingKey() return null...";
				output.add("message", message);
				LOGGER.warn(message + "\n" + input);
				APIGatewayV2WebSocketResponse response = new APIGatewayV2WebSocketResponse();
				response.setStatusCode(200);
				response.setBody(output.build().toString());
				response.setIsBase64Encoded(false);
				return response;
			}
			lambda = mapping.get(mappingKey);
			if (lambda == null) {
				JsonObjectBuilder output = Json.createObjectBuilder();
				output.add("result", "error");
				String message = "Cannot found mapped lambda for mapping key [" + mappingKey + "]";
				output.add("message", message);
				APIGatewayV2WebSocketResponse response = new APIGatewayV2WebSocketResponse();
				response.setStatusCode(200);
				response.setBody(output.build().toString());
				response.setIsBase64Encoded(false);
				return response;
			}
			LOGGER.info("Entering lambda: {}" ,lambda.getClass().getName());
			LOGGER.debug("Argument: {}", input);
			APIGatewayV2WebSocketResponse result = lambda.handleRequest(input,context);
			LOGGER.debug("Return result:\n" + result);
			return result;
		}
		catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			JsonObjectBuilder output =  Json.createObjectBuilder();
			output.add("result", "error");
			String message = sw.toString();
			output.add("message", message);
			LOGGER.error("Unexpected error [{}]", input, e);
			APIGatewayV2WebSocketResponse response = new APIGatewayV2WebSocketResponse();
			response.setStatusCode(200);
			response.setBody(output.build().toString());
			response.setIsBase64Encoded(false);
			return response;
		}
	}

	
	
}
