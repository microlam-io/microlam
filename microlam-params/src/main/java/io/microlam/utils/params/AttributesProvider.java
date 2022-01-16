package io.microlam.utils.params;

import java.util.Map;

public interface AttributesProvider {

	public String getPrefix();
	
	public String getPrefixedParameter(String parameter);
	
	public String getStringValue(String parameter, String defaultValue);

	public String getStringValueOrThrowException(String parameter);
	
	public boolean setStringValue(String parameter, String value);

	public AttributesProvider withPrefix(String prefix);
	
	public AttributesProvider withPrefix(String prefix, boolean preload);
	
	public Map<String,String> preloadParameters();

}
