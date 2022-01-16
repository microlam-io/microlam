package io.microlam.aws.lambda.pipeline;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

@Sharable
public class APIGatewayProxyRequestEventDecoder<T> extends MessageToMessageDecoder<APIGatewayProxyRequestEvent> {

	private static Logger LOGGER = LoggerFactory.getLogger(APIGatewayProxyRequestEventDecoder.class);

	public Class<T> typeOfT;
	
	public APIGatewayProxyRequestEventDecoder(Class<T> typeOfT)  {
		this.typeOfT = typeOfT;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, APIGatewayProxyRequestEvent msg, List<Object> out) throws Exception {
		LOGGER.debug("Entering APIGatewayProxyRequestEventDecoder.decode()");
		//System.out.println("Inside decode()");
//		LambdaChannel<ApiGatewayProxyInputEvent> lambdachannel = (LambdaChannel<ApiGatewayProxyInputEvent>) ctx.channel();
//		lambdachannel.context.getAwsRequestId();
		Gson gson = new Gson();
 		out.add(gson.fromJson(msg.getBody(), typeOfT));
		LOGGER.debug("Exiting APIGatewayProxyRequestEventDecoder.decode()");
	}

}
