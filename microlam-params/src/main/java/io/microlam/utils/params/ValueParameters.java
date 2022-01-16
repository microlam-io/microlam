package io.microlam.utils.params;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueParameters {

	protected String value;
	protected Map<String,String> parameters;
	
	public ValueParameters(String value, Map<String, String> parameters) {
		this.value = value;
		this.parameters = parameters;
	}
	
	public String getValue() {
		return value;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	//value,param1=value1,param2=value2,param3=value3
	public static ValueParameters parse(String valueParameters) {
		String value = null;
		Map<String,String> params = new HashMap<>();
		String[] parameters = valueParameters.split(",");
		for(String parameter: parameters) {
			String[] keyValues = parameter.split("=");
			if (keyValues.length == 1) {
				value = keyValues[0];
			}
			else {
				String key = keyValues[0];
				String keyValue = keyValues[1];
				if ("value".equals(key)) {
					value = keyValues[1];
				}
				else {
					params.put(key, keyValue);
				}
			}
		}
		return new ValueParameters(value, params);
	}


	public static List<ValueParameters> parseList(String valueParameters) {
		List<ValueParameters> result = new ArrayList<>();
		String[] valueParams = valueParameters.split("\\|");
		for(String valueParam: valueParams) {
			result.add(parse(valueParam));
		}
		return result;
	}

}
