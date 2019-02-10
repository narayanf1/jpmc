package com.sssm.exchange.exception;

/**
 * @author Lucky (LakshmiNarayana Ambarkar)
 *
 * A custom exception indicating a trade-related error in processing a request
 */
public class TradeException extends Exception {
	public TradeException(String message) {
		super(message);
	}
}
