package com.mss.utils;

public class MathHelpers {
	public static double Round(double value, int decimals) {
		double factor = Math.pow(10, decimals);
		
		return Math.round(value*factor)/factor;
	}
}
