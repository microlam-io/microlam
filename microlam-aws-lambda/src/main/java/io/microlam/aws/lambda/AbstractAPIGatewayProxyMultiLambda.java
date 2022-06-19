package io.microlam.aws.lambda;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;


public abstract class AbstractAPIGatewayProxyMultiLambda implements APIGatewayProxyLambda {

	private static Logger LOGGER = LoggerFactory.getLogger(AbstractAPIGatewayProxyMultiLambda.class);

	Map<String,APIGatewayProxyLambda> mapping;
	
	public AbstractAPIGatewayProxyMultiLambda() {
		mapping = new HashMap<String, APIGatewayProxyLambda>();
		registerAllLambda();
	}

	protected void registerLambda(String mappingKey, APIGatewayProxyLambda lambda) {
		mapping.put(mappingKey, lambda);
	}

	protected abstract void registerAllLambda();
	protected abstract String mappingKey(APIGatewayProxyRequestEvent request, Context context);

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		try {
			APIGatewayProxyLambda lambda = null;
			String mappingKey = mappingKey(input, context);
			if (mappingKey == null) {
				JsonObjectBuilder output = Json.createObjectBuilder();
				output.add("result", "error");
				String message = "mappingKey() return null...";
				output.add("message", message);
				LOGGER.warn(message + "\n" + input);
				APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
				response.withStatusCode(200)
					.withIsBase64Encoded(false)
					.withBody(output.build().toString());
				return response;
			}
			lambda = mapping.get(mappingKey);
			if (lambda == null) {
				JsonObjectBuilder output = Json.createObjectBuilder();
				output.add("result", "error");
				String message = "Cannot found mapped lambda for mapping key [" + mappingKey + "]";
				output.add("message", message);
				LOGGER.warn(message + "\n" + input);
				APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
				response.withStatusCode(200)
					.withIsBase64Encoded(false)
					.withBody(output.build().toString());
				return response;
			}
			LOGGER.info("Entering lambda: {}" ,lambda.getClass().getName());
			LOGGER.debug("Argument: {}", input);
			APIGatewayProxyResponseEvent result = lambda.handleRequest(input,context);
			LOGGER.debug("Return result:\n" + result);
			return result;
		}
		catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			JsonObjectBuilder output = Json.createObjectBuilder();
			output.add("result", "error");
			String message = sw.toString();
			output.add("message", message);
			LOGGER.error("Unexpected error [{}]", input, e);
			APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
			response.withStatusCode(200)
			.withIsBase64Encoded(false)
			.withBody(output.build().toString());
			return response;
		}
	}

	
	
}
