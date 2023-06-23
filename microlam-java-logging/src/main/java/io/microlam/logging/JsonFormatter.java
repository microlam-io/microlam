package io.microlam.logging;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

public class JsonFormatter extends Formatter {
	
    private final LogManager manager = LogManager.getLogManager();
    private final boolean useInstant;
    private final boolean oneNewLineOnly;

    public JsonFormatter() {
        useInstant = (manager == null)
                || getBooleanProperty(
                        this.getClass().getName()+".useInstant", true);
        oneNewLineOnly = (manager == null)
                || getBooleanProperty(
                        this.getClass().getName()+".oneNewLineOnly", false);
    }
    
    public JsonFormatter(boolean oneNewLineOnly) {
    	this.useInstant = (manager == null)
                || getBooleanProperty(
                        this.getClass().getName()+".useInstant", true);
    	this.oneNewLineOnly = oneNewLineOnly;
    }

    public JsonFormatter(boolean useInstant, boolean oneNewLineOnly) {
    	this.useInstant = useInstant;
    	this.oneNewLineOnly = oneNewLineOnly;
    }
    
    public boolean isUseInstant() {
		return useInstant;
	}

	public boolean isOneNewLineOnly() {
		return oneNewLineOnly;
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
        StringBuilder sb = new StringBuilder(500);
        sb.append("{\n");

        final Instant instant = record.getInstant();

        sb.append("  \"date\" : \"");
        if (useInstant) {
            // If useInstant is true - we will print the instant in the
            // date field, using the ISO_INSTANT formatter.
            DateTimeFormatter.ISO_INSTANT.formatTo(instant, sb);
        } else {
            // If useInstant is false - we will keep the 'old' formating
            appendISO8601(sb, instant.toEpochMilli());
        }
        sb.append("\",\n");

        sb.append("  \"millis\" : ");
        sb.append(instant.toEpochMilli());
        sb.append(",\n");

        final int nanoAdjustment = instant.getNano() % 1000_000;
        if (useInstant && nanoAdjustment != 0) {
            sb.append("  \"nanos\" : ");
            sb.append(nanoAdjustment);
            sb.append(",\n");
        }

        sb.append("  \"sequence\" : ");
        sb.append(record.getSequenceNumber());
        sb.append(",\n");

        String name = record.getLoggerName();
        if (name != null) {
            sb.append("  \"logger\" : \"");
            escape(sb, name);
            sb.append("\",\n");
        }

        sb.append("  \"level\" : \"");
        escape(sb, record.getLevel().toString());
        sb.append("\",\n");

        if (record.getSourceClassName() != null) {
            sb.append("  \"class\" : \"");
            escape(sb, record.getSourceClassName());
            sb.append("\",\n");
        }

        if (record.getSourceMethodName() != null) {
            sb.append("  \"method\" : \"");
            escape(sb, record.getSourceMethodName());
            sb.append("\",\n");
        }

        sb.append("  \"thread\" : ");
        sb.append(record.getThreadID());

        if (record.getMessage() != null) {
            // Format the message string and its accompanying parameters.
            String message = formatMessage(record);
            sb.append(",\n  \"message\" : \"");
            escape(sb, message);
            sb.append("\"");
        }

        // If the message is being localized, output the key, resource
        // bundle name, and params.
        ResourceBundle bundle = record.getResourceBundle();
        try {
            if (bundle != null && bundle.getString(record.getMessage()) != null) {
                sb.append(",\n  \"key\" : \"");
                escape(sb, record.getMessage());
                sb.append("\",\n");
                sb.append("  \"catalog\" : \"");
                escape(sb, record.getResourceBundleName());
                sb.append("\"");
            }
        } catch (Exception ex) {
            // The message is not in the catalog.  Drop through.
        }

        Object parameters[] = record.getParameters();
        //  Check to see if the parameter was not a messagetext format
        //  or was not null or empty
        if (parameters != null && parameters.length != 0
                && record.getMessage().indexOf('{') == -1 ) {
            for (Object parameter : parameters) {
                sb.append(",\n  \"param\" : \"");
                try {
                    escape(sb, parameter.toString());
                } catch (Exception ex) {
                    sb.append("???");
                }
                sb.append("\"");
            }
        }

        if (record.getThrown() != null) {
            // Report on the state of the throwable.
            Throwable th = record.getThrown();
            sb.append(",\n  \"exception\" : {\n"); //exception
            sb.append("    \"message\" : \"");
            escape(sb, th.toString());
            sb.append("\"");
            StackTraceElement trace[] = th.getStackTrace();
            if (trace.length > 0) {
            	sb.append(",\n");
            	sb.append("    \"stacktrace\": [\n"); //stacktrace
            }
            boolean start = true;
            for (StackTraceElement frame : trace) {
            	if (!start) {
            		sb.append(",\n");
            	}
        		start = false;              
                sb.append("    {\n"); //frame
                sb.append("      \"class\" : \"");
                escape(sb, frame.getClassName());
                sb.append("\",\n");
                sb.append("      \"method\" : \"");
                escape(sb, frame.getMethodName());
                sb.append("\"");
                // Check for a line number.
                if (frame.getLineNumber() >= 0) {
                	sb.append(",\n");
                }
                if (frame.getLineNumber() >= 0) {
                    sb.append("      \"line\" : ");
                    sb.append(frame.getLineNumber());
                    sb.append("\n");
                }
                else {
                	sb.append("\n");
                }
                sb.append("    }"); //frame
            }
            if (! start) {
            	sb.append("\n    ]\n"); //stacktrace
            }
           sb.append("  }\n"); //exception
        }

        sb.append("}\n");
        String result = sb.toString();
        return (oneNewLineOnly)?result.replace('\n', '\r') + "\n":result;
	}

    // Append a two digit number.
    private void a2(StringBuilder sb, int x) {
        if (x < 10) {
            sb.append('0');
        }
        sb.append(x);
    }

    // Append the time and date in ISO 8601 format
    private void appendISO8601(StringBuilder sb, long millis) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(millis);
        sb.append(cal.get(Calendar.YEAR));
        sb.append('-');
        a2(sb, cal.get(Calendar.MONTH) + 1);
        sb.append('-');
        a2(sb, cal.get(Calendar.DAY_OF_MONTH));
        sb.append('T');
        a2(sb, cal.get(Calendar.HOUR_OF_DAY));
        sb.append(':');
        a2(sb, cal.get(Calendar.MINUTE));
        sb.append(':');
        a2(sb, cal.get(Calendar.SECOND));
    }

    // Append to the given StringBuilder an escaped version of the
    // given text string where Json special characters have been escaped.
    // For a null string we append "null"
    private void escape(StringBuilder sb, String text) {
        if (text == null) {
            text = "null";
        }
        escape(text, sb);
    }

    static void escape(String s, StringBuilder sb) {
    	final int len = s.length();
		for(int i=0;i<len;i++){
			char ch=s.charAt(i);
			switch(ch){
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
                //Reference: http://www.unicode.org/versions/Unicode5.1.0/
				if((ch>='\u0000' && ch<='\u001F') || (ch>='\u007F' && ch<='\u009F') || (ch>='\u2000' && ch<='\u20FF')){
					String ss=Integer.toHexString(ch);
					sb.append("\\u");
					for(int k=0;k<4-ss.length();k++){
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				}
				else{
					sb.append(ch);
				}
			}
		}//for
	}

}
