package com.sssm.stocks.exception;

/**
 * @author Lucky (LakshmiNarayana Ambarkar)
 *
 * A custom exception indicating a stock-related error in processing a request
 */
public class StockException extends Exception { 
	public StockException(String message) {
		super(message);
	}
}
