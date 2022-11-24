package io.microlam.aws.lambda;

public interface BusinessOperator<I, O> {

	O process(I i);
	
}
