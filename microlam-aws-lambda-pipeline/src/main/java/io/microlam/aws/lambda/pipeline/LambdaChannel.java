package io.microlam.aws.lambda.pipeline;

import com.amazonaws.services.lambda.runtime.Context;

import io.netty.channel.embedded.EmbeddedChannel;

public class LambdaChannel<I> extends EmbeddedChannel {

	public final I input;
	public final Context context;
	
	public LambdaChannel(I input, Context context) {
		this.input = input;
		this.context = context;
	}
	
}
