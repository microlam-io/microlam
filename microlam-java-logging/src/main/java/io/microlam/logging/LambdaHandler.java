package io.microlam.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.ErrorManager;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.LambdaRuntime;

public class LambdaHandler extends Handler {

	protected LambdaLogger lambdaLogger;
	protected ConsoleHandler consoleHandler;
	
	public LambdaHandler() {
		this(false);
	}
	
	public LambdaHandler(boolean useJsonFormatting) {
		setFormatter((useJsonFormatting)?new JsonFormatter():new OneLineSimpleFormatter());
		this.lambdaLogger = LambdaRuntime.getLogger();
		consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter((useJsonFormatting)?new JsonFormatter(true):new OneLineSimpleFormatter(true));
		consoleHandler.setLevel(getLevel());
	}
	
    @Override
    public boolean isLoggable(LogRecord record) {
        if (lambdaLogger == null || record == null) {
            return false;
        }
        return super.isLoggable(record);
    }

    
    
    
	@Override
	public void publish(LogRecord record) {
        if (!isLoggable(record)) {
            return;
        }
//		System.out.println(lambdaLogger.getClass().getName());
    	if ((lambdaLogger != null) 
    		&& (! lambdaLogger.getClass().getName().startsWith("com.amazonaws.services.lambda.runtime.LambdaRuntime$"))
    		&& (! lambdaLogger.getClass().getName().equals("com.amazonaws.services.lambda.runtime.api.client.logging.LambdaContextLogger"))) {
	        String msg;
	        try {
	            msg = getFormatter().format(record);
	        } catch (Exception ex) {
	            // We don't want to throw an exception here, but we
	            // report the exception to any registered ErrorManager.
	            reportError(null, ex, ErrorManager.FORMAT_FAILURE);
	            return;
	        }
	
	        try {
	        	lambdaLogger.log(msg);
	        } catch (Exception ex) {
	            // We don't want to throw an exception here, but we
	            // report the exception to any registered ErrorManager.
	            reportError(null, ex, ErrorManager.WRITE_FAILURE);
	        }
    	}
    	else {
	        try {
        		consoleHandler.publish(record);
	        } catch (Exception ex) {
	            // We don't want to throw an exception here, but we
	            // report the exception to any registered ErrorManager.
	            reportError(null, ex, ErrorManager.WRITE_FAILURE);
	        }
    	}
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() throws SecurityException {
	}
	
}
