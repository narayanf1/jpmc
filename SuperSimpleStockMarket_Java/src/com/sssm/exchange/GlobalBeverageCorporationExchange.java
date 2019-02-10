package com.sssm.exchange;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sssm.exchange.exception.TradeException;
import com.sssm.stocks.Stock;
import com.sssm.util.Utilities;

/**
 * @author Lucky (LakshmiNarayana Ambarkar)
 * 
 * Represents the Global Beverage Corporation Exchange Board.
 * 
 * The exchange board maintains a private set of stocks and a list of trades registered.
 * Trades are registered through a synchronized registerTrade method.
 * 
 * Only one instance of the exchange board can be active, hence using singleton.
 * Ideally, registerTrade is called by several threads simultaneously, hence synchronized.
 * 
 */
public class GlobalBeverageCorporationExchange {
	// Using Java 8's generic diamond syntax, hence use JDK8+ to compile.
	private Set<Stock> registeredStocks = new HashSet<>();
	private List<Trade> tradeList = new ArrayList<>();
	
	private static final int OBSERVED_PERIOD_MINUTES = 10; 
	
	// singleton - need only one exchange
	private static GlobalBeverageCorporationExchange exchange = new GlobalBeverageCorporationExchange();
	public static GlobalBeverageCorporationExchange getInstance() {
		return exchange;
	}
	
	public void registerStock(Stock stock) {
		registeredStocks.add(stock);
	}
	
	/**
	 * @return Iterator<Stock>
	 * 
	 * Return Iterator instead of the set itself to avoid accidental updates outside the class's control. 
	 */
	public Iterator<Stock> getRegisteredStocksIterator() {
		return this.registeredStocks.iterator();
	}
	
	/**
	 * @param trade to be registered in the exchange.
	 * 
	 * This call is synchronized to avoid inconsistent state in a multithreaded environment.
	 */
	public synchronized void registerTrade(Trade trade) {
		// trade timestamp is already supplied in trade object for simplicity 
		tradeList.add(trade);
	}
	
	/**
	 * @param stock to calculate Volume Weighted Price upon.
	 * @return float
	 * @throws TradeException
	 * 
	 * Calculates Volume Weighted Price for the given stock
	 * Only trades registered within a defined time period from current time are considered.
	 */
	public float calculateVWSP(Stock stock) throws TradeException {
		float numerator = 0;	// sum of (price X quantity) from each trade of the stock 
		float denominator = 0;	// sum of quantity from each trade of the stock
		
		Date currentTimestamp = new Date();
		
		for (Trade trade: this.tradeList) {
			float timeDiffInMinutes = (float)(currentTimestamp.getTime()-trade.getTimestamp().getTime())/(1000*60);
			if (timeDiffInMinutes <= OBSERVED_PERIOD_MINUTES) {
				if (trade.getStock().equals(stock)) {
					numerator += trade.getPrice() * trade.getQuantity();
					denominator += trade.getQuantity();
				}
			}
		}
		
		if (numerator == 0 || denominator == 0) {
			throw new TradeException("No trade registered for " + stock);
		}
		
		return numerator/denominator;
	}
	
	/**
	 * @return double - The Geometric Mean of all the stocks who have a valid Volume Weighted Price
	 * @throws TradeException
	 * 
	 * Step 1: For each stock, get the VWSP and calculate their cumulative produce.
	 * Step 2: calculate Nth root of the product, where N is the number of stocks for which VWSP was obtained.
	 */
	public double calculateGeometricMean() throws TradeException {
		double vwspProduct = -1;	// initialized with -1 - just in case there is no VWSP for any stocks
		int stockCount = 0;
		for (Stock stock : this.registeredStocks) {
			try {
				float vwsp = this.calculateVWSP(stock);
				stockCount++;
				vwspProduct = vwspProduct == -1 ? vwsp : vwspProduct*vwsp;
			} catch (TradeException e) {
				// log the exception & skip the stock
			}
		}
		if (vwspProduct != -1) {
			return Utilities.nThRootOf(stockCount, vwspProduct);
		} else {
			throw new TradeException("VWSP not available for any stock");
		}
	}
}
