package io.microlam.utils.params.cache;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.microlam.utils.params.AttributesProvider;


public class InMemoryCachingAttributesProvider implements AttributesProvider {

	private static Logger LOGGER = LoggerFactory.getLogger(InMemoryCachingAttributesProvider.class);	

	protected long expiryInMs;
	protected AttributesProvider attributesProvider;
	protected ConcurrentHashMap<String,ExpiringVariable<String>> memoryCache;

	public InMemoryCachingAttributesProvider(long expiryInMs, AttributesProvider attributesProvider) {
		this.memoryCache = new ConcurrentHashMap<>();
		this.expiryInMs = expiryInMs;
		this.attributesProvider = attributesProvider;
	}
		
	@Override
	public String getPrefix() {
		return attributesProvider.getPrefix();
	}

	@Override
	public String getPrefixedParameter(String parameter) {
		return attributesProvider.getPrefixedParameter(parameter);
	}

	@Override
	public String getStringValue(String parameter, String defaultValue) {
		ExpiringVariable<String> expiringVariable = memoryCache.get(parameter);
		if (expiringVariable == null) {
			LOGGER.debug("ExpiringVariable not found in cache: [{}]", parameter);
			expiringVariable = new ExpiringVariable<>(expiryInMs);
			memoryCache.put(parameter, expiringVariable);
		}
		else {
			SnapshotValue<String> snapshotValue = expiringVariable.getSnapshotValue();
			LOGGER.debug("ExpiringVariable found in cache: [{}]", parameter);

			if (! snapshotValue.isExpired()) {
				LOGGER.info("ExpiringVariable used from cache: [{} = {}]", parameter, snapshotValue.getValue());
				return snapshotValue.getValue();
			}
			LOGGER.debug("ExpiringVariable expired: [{}]", parameter);
		}
		String value = attributesProvider.getStringValue(parameter, defaultValue);
		expiringVariable.setValue(value);
		LOGGER.info("Set expiringVariable in cache: [{} = {}]", parameter, value);
		return value;
	}

	@Override
	public String getStringValueOrThrowException(String parameter) {
		ExpiringVariable<String> expiringVariable = memoryCache.get(parameter);
		if (expiringVariable == null) {
			expiringVariable = new ExpiringVariable<>(expiryInMs);
			memoryCache.put(parameter, expiringVariable);
		}
		else {
			SnapshotValue<String> snapshotValue = expiringVariable.getSnapshotValue();
			if (! snapshotValue.isExpired()) {
				return snapshotValue.getValue();
			}
		}
		String value = attributesProvider.getStringValueOrThrowException(parameter);
		expiringVariable.setValue(value);
		return value;
	}
	
	@Override
	public AttributesProvider withPrefix(String prefix) {
		return new InMemoryCachingAttributesProvider(expiryInMs, attributesProvider.withPrefix(prefix));
	}

	@Override
	public boolean setStringValue(String parameter, String value) {
		setMemoryStringValue(parameter, value);
		return attributesProvider.setStringValue(parameter, value);
	}

	public void setMemoryStringValue(String parameter, String value) {
		ExpiringVariable<String> expiringVariable = memoryCache.get(parameter);
		if (expiringVariable == null) {
			expiringVariable = new ExpiringVariable<>(expiryInMs);
			expiringVariable.value = value;
			memoryCache.put(parameter, expiringVariable);
		}
		else {
			expiringVariable.setValue(value);
		}
	}

	@Override
	public Map<String, String> preloadParameters() {
		Map<String, String> map = attributesProvider.preloadParameters();
		for(Entry<String,String> entry : map.entrySet()) {
			setMemoryStringValue(entry.getKey(), entry.getValue());
		}
		return map;
	}

	@Override
	public AttributesProvider withPrefix(String prefix, boolean preload) {
		AttributesProvider attributesProviderPrefix = attributesProvider.withPrefix(prefix);
		attributesProviderPrefix.preloadParameters();
		return attributesProviderPrefix;
	}


}
