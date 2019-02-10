package com.sssm.util;

/**
 * @author Lucky (LakshmiNarayana Ambarkar)
 * 
 * Utility class containing static methods
 */
public class Utilities {
	/**
	 * Logic obtained from https://stackoverflow.com/questions/26839890/calculating-nth-root-of-a-positive-integer-in-java
	 * 
	 * @param n		=> nth root 
	 * @param value => value on which the root has to be calculated
	 * @return		=> nth root of value
	 */
	public static double nThRootOf(int n, double value) {
		if (n==0) {
			return 1;
		}
		
		return Math.pow(value, (1.0/n));
	}
}
