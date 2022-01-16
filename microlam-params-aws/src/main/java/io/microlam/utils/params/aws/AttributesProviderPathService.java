package io.microlam.utils.params.aws;

import io.microlam.utils.Utils;
import io.microlam.utils.params.AttributesProvider;
import io.microlam.utils.params.cache.InMemoryCachingAttributesProvider;

public class AttributesProviderPathService {

	public static AttributesProvider createAtributesProvider(String defaultPrefix) {
		return createAtributesProvider(defaultPrefix, 10*60*1000l /* 10 min */);
	}

	public static AttributesProvider createAtributesProvider(String defaultPrefix, long expiryInMs) {
		String prefix = Utils.getEnv("AWS_SSM_PREFIX", defaultPrefix);
		ParameterStoreProviderPath parameterStoreProvider;
		parameterStoreProvider = new ParameterStoreProviderPath(prefix);
		long defaultExpiryInMs = Utils.getEnv("AWS_SSM_IN_MEMORY_EXPIRY", expiryInMs);
		InMemoryCachingAttributesProvider attributesProvider = new InMemoryCachingAttributesProvider(defaultExpiryInMs, parameterStoreProvider);
		attributesProvider.preloadParameters();
		return attributesProvider;
	}

}
