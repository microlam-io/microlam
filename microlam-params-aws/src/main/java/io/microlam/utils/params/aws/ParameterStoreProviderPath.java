package io.microlam.utils.params.aws;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.microlam.aws.auth.AwsProfileRegionClientConfigurator;
import io.microlam.utils.params.AttributesProvider;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathRequest;
import software.amazon.awssdk.services.ssm.model.GetParametersByPathResponse;
import software.amazon.awssdk.services.ssm.model.Parameter;
import software.amazon.awssdk.services.ssm.model.ParameterType;
import software.amazon.awssdk.services.ssm.model.PutParameterRequest;
import software.amazon.awssdk.services.ssm.model.PutParameterResponse;

public class ParameterStoreProviderPath implements AttributesProvider {

	private static Logger LOGGER = LoggerFactory.getLogger(ParameterStoreProviderPath.class);	

	private String prefix;
	
	SsmClient ssmClient = null;
	
	private  SsmClient getSSMClient() {
		if (ssmClient == null) {
			ssmClient = AwsProfileRegionClientConfigurator.getInstance().configure(SsmClient.builder()).build();
		}
		return ssmClient;
	}
	
	public ParameterStoreProviderPath(String prefix) {
		this.prefix = prefix;
	}

	public ParameterStoreProviderPath(SsmClient ssmClient, String prefix) {
		this.prefix = prefix;
		this.ssmClient = ssmClient;
	}

	public Map<String,String> preloadParameters() {
		GetParametersByPathRequest getParametersByPathRequest = GetParametersByPathRequest.builder().path(prefix).withDecryption(Boolean.TRUE).build();
		GetParametersByPathResponse getParametersByPathResponse = getSSMClient().getParametersByPath(getParametersByPathRequest);
		Map<String,String> parameters = new HashMap<>();
		boolean again = true;
		while(again) {
			getParametersByPathResponse.parameters().forEach((Parameter parameter) -> {
				if ((prefix != null) && (prefix.length() != 0)) {
					if (parameter.name().startsWith(prefix + "/")) {
						parameters.put(parameter.name().substring(prefix.length()+1), parameter.value());
					}
				}
				else {
					parameters.put(parameter.name(), parameter.value());				
				}
			});
			if (getParametersByPathResponse.nextToken() != null) {
				getParametersByPathRequest = GetParametersByPathRequest.builder().path(prefix).withDecryption(Boolean.TRUE).nextToken(getParametersByPathResponse.nextToken()).build();
				getParametersByPathResponse = getSSMClient().getParametersByPath(getParametersByPathRequest);
			}
			else {
				again = false;
			}
		}
		return parameters;
	}
	
	@Override
	public String getStringValueOrThrowException(String parameter) {
		String prefixedName = getPrefixedParameter(parameter);
		try {
			GetParameterResponse getParameterResponse = getSSMClient().getParameter(((GetParameterRequest)  GetParameterRequest.builder()
																															 .name(prefixedName)
																															 .withDecryption(true)
																															 .build()));
			return getParameterResponse.parameter().value();
		}
		catch(Throwable th) {
			throw new RuntimeException("Cannot get parameter [" + prefixedName + "] from " + this.toString());
		}
	}
	
	@Override
	public String getStringValue(String parameter, String defaultValue) {
		String prefixedName = getPrefixedParameter(parameter);
		try {
			GetParameterResponse getParameterResponse = getSSMClient().getParameter(((GetParameterRequest)  GetParameterRequest.builder()
																															 .name(prefixedName)
																															 .withDecryption(true)																								 .build()));
			return getParameterResponse.parameter().value();
		}
		catch(Throwable th) {
						//th.printStackTrace();
			//Means certainly parameter not found
			LOGGER.warn("Exception occured during the getParameter[" + prefixedName, th);
			
		}
		LOGGER.warn("Using defaultValue getParameter[" + prefixedName + ":" + defaultValue + "]");

		return defaultValue;
	}

	@Override
	public boolean setStringValue(String parameter, String value) {
		String prefixedName = getPrefixedParameter(parameter);
		try {
			PutParameterResponse putParameterResponse = getSSMClient().putParameter((PutParameterRequest)  PutParameterRequest.builder()
																															 .name(prefixedName)
																															 .type(ParameterType.STRING)
																															 .overwrite(true)
																															 .value(value)
																															 .build());
			return putParameterResponse.sdkHttpResponse().isSuccessful();
		}
		catch(Throwable th) {
			//th.printStackTrace();
			//Means certainly parameter not found
			LOGGER.warn("Exception occured during the putParameter[" + prefixedName + ":" + value, th);
		}
		return false;
	}
	
	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public AttributesProvider withPrefix(String prefix) {
		return new ParameterStoreProviderPath(prefix);
	}

	@Override
	public String getPrefixedParameter(String parameter) {
		if ((prefix == null) || (prefix.length() == 0)) {
			return parameter;
		}
		String prefixedName = prefix + "/" + parameter;
		return prefixedName;
	}

	@Override
	public String toString() {
		return "ParameterStoreProviderPath [prefix=" + prefix + "]";
	}

	@Override
	public AttributesProvider withPrefix(String prefix, boolean preload) {
		return withPrefix(prefix);
	}

	
}
