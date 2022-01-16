package io.microlam.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class Utils {

	public static long valueOf(String value, long defaultValue) {
		return (value != null)?Long.parseLong(value):defaultValue;
	}

	public static String valueOf(String value, String defaultValue) {
		return (value != null)?value:defaultValue;
	}

	public static String getEnv(String name, String defaultValue) {
		return valueOf(System.getenv(name), defaultValue);
	}

	public static long getEnv(String name, long defaultValue) {
		return valueOf(System.getenv(name), defaultValue);
	}
	
	public static boolean areEqual(double v1, double v2, int decimalPlaces) {
		double diff = Math.abs((v1-v2)*Math.pow(10,decimalPlaces));
		return diff < 1;
	}

	public static boolean areEqual(BigDecimal v1, BigDecimal v2, int decimalPlaces) {
		BigDecimal diff = (v1.subtract(v2)).multiply(BigDecimal.TEN.pow(decimalPlaces)).abs();
		return diff.compareTo(BigDecimal.ONE) < 0;
	}
	
	public static double round(double value, int scale, boolean max) {
		double result;
		if (max) {
			result = Math.ceil(value*Math.pow(10,scale))*Math.pow(10,-scale);
		}
		else {
			result = Math.floor(value*Math.pow(10,scale))*Math.pow(10,-scale);
		}
		return result;
	}
	
	public static BigDecimal convert(double value, int scale, boolean max) {
		long result;
		if (max) {
			result = (long) Math.ceil(value*Math.pow(10,scale));
		}
		else {
			result = (long) Math.floor(value*Math.pow(10,scale));
		}
		BigDecimal resultB = new BigDecimal(BigInteger.valueOf(result), scale);
		return resultB;
	}
	
	
}
