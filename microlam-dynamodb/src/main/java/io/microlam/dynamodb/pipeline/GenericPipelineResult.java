package io.microlam.dynamodb.pipeline;

public class GenericPipelineResult {

	final public int processCount;
	final public String lastKeyString;
	
	public GenericPipelineResult(int processCount, String lastKeyString) {
		this.processCount = processCount;
		this.lastKeyString = lastKeyString;
	}
		
}
