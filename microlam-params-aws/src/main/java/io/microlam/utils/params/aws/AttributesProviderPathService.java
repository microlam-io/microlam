package io.microlam.utils.params.aws;

import io.microlam.utils.Utils;
import io.microlam.utils.params.AttributesProvider;
import io.microlam.utils.params.cache.InMemoryCachingAttributesProvider;
import software.amazon.awssdk.services.ssm.SsmClient;

public class AttributesProviderPathService {

	public static AttributesProvider createAtributesProvider(String defaultPrefix) {
		return createAtributesProvider(defaultPrefix, 10*60*1000l /* 10 min */);
	}

	public static AttributesProvider createAtributesProvider(String defaultPrefix, long expiryInMs) {
		return createAtributesProvider(defaultPrefix, expiryInMs, null);
	}
	
	public static AttributesProvider createAtributesProvider(String defaultPrefix, long expiryInMs, SsmClient ssmClient) {
		String prefix = Utils.getEnv("AWS_SSM_PREFIX", defaultPrefix);
		ParameterStoreProviderPath parameterStoreProvider;
		parameterStoreProvider = new ParameterStoreProviderPath(ssmClient, prefix);
		long defaultExpiryInMs = Utils.getEnv("AWS_SSM_IN_MEMORY_EXPIRY", expiryInMs);
		InMemoryCachingAttributesProvider attributesProvider = new InMemoryCachingAttributesProvider(defaultExpiryInMs, parameterStoreProvider);
		attributesProvider.preloadParameters();
		return attributesProvider;
	}


}
