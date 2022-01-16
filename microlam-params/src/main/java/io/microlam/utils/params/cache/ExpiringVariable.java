package io.microlam.utils.params.cache;

public class ExpiringVariable<T> {

	public final SnapshotValue<T> EXPIRED_VALUE = new SnapshotValue<>(null, true);
	
	protected long expiryInMs;
	protected T value = null;
	protected long setTimestamp = 0;
	
	public ExpiringVariable(long expiryInMs) {
		this.expiryInMs = expiryInMs;
	}

	public void setExpiryInMs(long expiryInMs) {
		this.expiryInMs = expiryInMs;
	}
	
	public void waitForExpiration() {
		try {
			long ttl = getTTL();
			if (ttl > 0) {
				Thread.sleep(ttl);
			}
		}
		catch (InterruptedException e) {
			//e.printStackTrace();
		}
	}
	
	public long getTTL() {
		if (setTimestamp == 0) {
			return  0;
		}
		long currentTimestamp = System.currentTimeMillis();
		long elapsed = currentTimestamp-setTimestamp;
		if (elapsed >= expiryInMs) {
			return 0;
		}
		return expiryInMs-elapsed;
	}
	
	public void setValue(T value) {
		this.value = value;
		this.setTimestamp = System.currentTimeMillis();
	}
	
	public SnapshotValue<T> getSnapshotValue() {
		if (setTimestamp == 0) {
			//not set like expired
			return EXPIRED_VALUE;
		}
		long currentTimestamp = System.currentTimeMillis();
		if (currentTimestamp-setTimestamp >= expiryInMs) {
			//Expired
			this.value = null;
			return EXPIRED_VALUE;
		}
		return new SnapshotValue<>(value, false);
	}

	
	public T getValue() {
		if ((setTimestamp == 0) || (this.value == null)) {
			return null;
		}
		long currentTimestamp = System.currentTimeMillis();
		if (currentTimestamp-setTimestamp >= expiryInMs) {
			this.value = null;
		}
		return this.value;
	}
	
	public void expired() {
		setTimestamp = 0;
	}
	
	public boolean isExpired() {
		return getTTL() == 0;
	}
}
