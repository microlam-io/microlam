package io.microlam.utils.params.cache;

public class SnapshotValue<T> {

	private final T value;
	private final boolean expired;
	
	public SnapshotValue(T value, boolean expired) {
		this.value = value;
		this.expired = expired;
	}

	public T getValue() {
		return value;
	}

	public boolean isExpired() {
		return expired;
	}
	
}
