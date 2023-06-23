package io.microlam.logging;

import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class OneLineSimpleFormatter extends SimpleFormatter {

    private final LogManager manager = LogManager.getLogManager();
    private final boolean oneNewLineOnly;

	public OneLineSimpleFormatter() {
        oneNewLineOnly = (manager == null)
                || getBooleanProperty(
                        this.getClass().getName()+".oneNewLineOnly", false);
	}

    public OneLineSimpleFormatter(boolean oneNewLineOnly) {
    	this.oneNewLineOnly = oneNewLineOnly;
    }

	protected boolean getBooleanProperty(String name, boolean defaultValue) {
        String val = manager.getProperty(name);
        if (val == null) {
            return defaultValue;
        }
        val = val.toLowerCase();
        if (val.equals("true") || val.equals("1")) {
            return true;
        } else if (val.equals("false") || val.equals("0")) {
            return false;
        }
        return defaultValue;
    }

	@Override
	public String format(LogRecord record) {
		String result = super.format(record);
		return (oneNewLineOnly)?result.replace('\n', '\r') + "\n":result;
	}
}
