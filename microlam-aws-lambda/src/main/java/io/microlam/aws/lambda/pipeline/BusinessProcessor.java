package io.microlam.aws.lambda.pipeline;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public abstract class BusinessProcessor<I, O> extends MessageToMessageDecoder<I> {

	private static Logger LOGGER = LoggerFactory.getLogger(BusinessProcessor.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, I msg, List<Object> out) throws Exception {
		LOGGER.debug("Entering BusinessProcessor.decode() from class: {}", this.getClass().getSimpleName());
		O o = process(msg);
		out.add(o);
		ctx.channel().writeAndFlush(o);
	}
	
	public abstract O process(I msg);
	
	
}
