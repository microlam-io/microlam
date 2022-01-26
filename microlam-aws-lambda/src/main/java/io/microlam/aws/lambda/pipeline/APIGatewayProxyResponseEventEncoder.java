package io.microlam.aws.lambda.pipeline;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
public class APIGatewayProxyResponseEventEncoder<T> extends MessageToMessageEncoder<T>{

	private static Logger LOGGER = LoggerFactory.getLogger(APIGatewayProxyResponseEventEncoder.class);

	protected Class<T> typeOfT;
	
	public APIGatewayProxyResponseEventEncoder(Class<T> typeOfT)  {
		super(typeOfT);
		this.typeOfT = typeOfT;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, T msg, List<Object> out) throws Exception {
		LOGGER.debug("Entering APIGatewayProxyResponseEventEncoder.encode()");
		Gson gson = new Gson();
//	    Type sooper = getClass().getGenericSuperclass();
//	    Type t = ((ParameterizedType)sooper).getActualTypeArguments()[ 0 ];
	    String body = gson.toJson(msg, typeOfT);
	    APIGatewayProxyResponseEvent apiGatewayProxyOutputEvent = new APIGatewayProxyResponseEvent();
	    apiGatewayProxyOutputEvent.withIsBase64Encoded(false);
	    apiGatewayProxyOutputEvent.withBody(body);
	    apiGatewayProxyOutputEvent.withStatusCode(200);
		out.add(apiGatewayProxyOutputEvent);
	}

}