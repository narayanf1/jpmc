package com.sssm.exchange;

import java.util.Date;

import com.sssm.stocks.Stock;

/**
 * @author Lucky (LakshmiNarayana Ambarkar)
 * 
 * Represents a trade to be registered on the exchange board.
 * All elements except timestamp are final.  Ideally, timestamp is set while being registered.
 */
public class Trade {
	public static enum TradeType {
		BUY,
		SELL
	}
	
	private final Stock stock;
	private final float price;
	private final int quantity;
	private final TradeType tradeType; 
	private Date timestamp;
	
	public Trade(Stock stock, float price, int quantity, TradeType tradeType, Date timestamp) {
		// for simplicity, setting timestamp through constructor.
		this.stock = stock;
		this.price = price;
		this.quantity = quantity;
		this.tradeType = tradeType;
		this.timestamp = timestamp;
	}

	public Stock getStock() {
		return stock;
	}

	public float getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public TradeType getTradeType() {
		return tradeType;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}
