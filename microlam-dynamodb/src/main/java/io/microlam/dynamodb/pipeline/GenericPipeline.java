package io.microlam.dynamodb.pipeline;

public interface GenericPipeline {

	int count(int limit, String lastKeyString);
	
	GenericPipelineResult process(ItemProcessor itemProcessor, int limit, String lastKeyString);
	
}
