package com.sssm.stocks;

import com.sssm.stocks.exception.StockException;

/**
 * @author Lucky (LakshmiNarayana Ambarkar)
 * 
 * Represents a stock on the market
 * stockSymbol identifies a stock and is used for equality check
 */
public class Stock {
	public static enum StockType {
		COMMON,
		PREFERRED
	}
	
	private final String stockSymbol;
	private StockType stockType;
	private float lastDividend;
	private float fixedDividendPercent;
	private float parValue;
	
	public Stock(String symbol, StockType type, float lastDividend, float fixedDividendPercent, float parValue) {
		// for simplicity, setting all instance variables through constructor.
		this.stockSymbol = symbol;
		this.stockType = type;
		this.lastDividend = lastDividend;
		this.fixedDividendPercent = fixedDividendPercent;
		this.parValue = parValue;
		
		if (type == StockType.PREFERRED) {
			this.lastDividend = (fixedDividendPercent/100) * parValue;
		}
	}
	
	public String getStockSymbol() {
		return stockSymbol;
	}

	public StockType getStockType() {
		return stockType;
	}

	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	public float getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(float lastDividend) throws StockException {
		if (parValue <= 0f) {
			throw new StockException("Invalid dividend for " + this);
		}
		this.lastDividend = lastDividend;
	}

	public float getFixedDividendPercent() {
		return fixedDividendPercent;
	}

	public void setFixedDividendPercent(float fixedDividendPercent) throws StockException{
		if (this.stockType == StockType.PREFERRED && fixedDividendPercent <= 0f) {
			throw new StockException("Invalid fixed dividend for " + this);
		}
		this.fixedDividendPercent = fixedDividendPercent;
	}

	public float getParValue() {
		return parValue;
	}

	public void setParValue(float parValue) throws StockException {
		if (parValue <= 0f) {
			throw new StockException("Invalid par value for " + this);
		}
		this.parValue = parValue;
	}

	@Override
	public String toString() {
		// nicely represent the stock on console.
		return this.stockType.name() + " stock " + this.stockSymbol;
	}
	
	@Override
	public boolean equals(Object otherStock) {
		if (otherStock == this) { return true; }
		if (otherStock == null || this.getClass() != otherStock.getClass()) { return false; }
		
		return this.stockSymbol.equals(((Stock)otherStock).stockSymbol);
	}
	
	@Override
	public int hashCode() {
		return this.stockSymbol.hashCode();
	}
	
	public float calculateDividendYield(float stockPrice) throws StockException {
		if (stockPrice <= 0) {
			throw new StockException("Invalid price for " + this);
		}
		
		// for PREFERRED stocks, lastDividend = parValue X fixedDividend/100.
		return this.lastDividend / stockPrice;
	}
	
	public float calculatePERatio(float stockPrice) throws StockException {
		if (stockPrice <= 0) {
			throw new StockException("Invalid price for " + this);
		}
		
		if (this.lastDividend == 0) {
			throw new StockException("Dividend never issued for " + this);
		}
		
		return stockPrice / this.lastDividend;
	}
}
