import java.util.Date;
import java.util.Iterator;

import com.sssm.exchange.GlobalBeverageCorporationExchange;
import com.sssm.exchange.Trade;
import com.sssm.exchange.Trade.TradeType;
import com.sssm.exchange.exception.TradeException;
import com.sssm.stocks.Stock;
import com.sssm.stocks.Stock.StockType;
import com.sssm.stocks.exception.StockException;

/**
 * @author Lucky (LakshmiNarayana Ambarkar)
 * 
 * This class is used to test the 'Super Simple (really?) Stock Market' application.
 * 
 * For testing purpose...
 * 		stocks are created and trades are registered in a static initialization block.
 * 		trades are assigned timestamp while creation (rather than in the registerTrade method).
 * 
 * Sample output mentioned at the end.
 * 		
 */
public class TestApplication {
	static GlobalBeverageCorporationExchange exchange = GlobalBeverageCorporationExchange.getInstance();
	static {
		// registering stocks & trades in static initialization block for test purpose.
		// Ideally, should be registered in a more controlled manner.
		
		Stock TEA = new Stock("TEA", StockType.COMMON, 0f, 0f, 100f);
		Stock POP = new Stock("POP", StockType.COMMON, 8f, 0f, 100f);
		Stock ALE = new Stock("ALE", StockType.COMMON, 23f, 0f, 60f);
		Stock GIN = new Stock("GIN", StockType.PREFERRED, 8f, 2f, 100f);
		Stock JOE = new Stock("JOE", StockType.COMMON, 13f, 0f, 250f);
		exchange.registerStock(TEA);
		exchange.registerStock(POP);
		exchange.registerStock(ALE);
		exchange.registerStock(GIN);
		exchange.registerStock(JOE);
		
		// a stock that's not traded ever! (SNT)
		Stock SNT = new Stock("SNT", StockType.PREFERRED, 0f, 0f, 10f);
		exchange.registerStock(SNT);
		
		long currentTimestampMillis = new Date().getTime();
		exchange.registerTrade(new Trade(TEA, 96.53f, 328, TradeType.BUY, new Date(currentTimestampMillis-(14*60*1000))));
		exchange.registerTrade(new Trade(POP, 92.27f, 343, TradeType.BUY, new Date(currentTimestampMillis-(14*60*1000))));
		exchange.registerTrade(new Trade(TEA, 97.26f, 234, TradeType.SELL, new Date(currentTimestampMillis-(13*60*1000))));
		exchange.registerTrade(new Trade(ALE, 67.54f, 464, TradeType.BUY, new Date(currentTimestampMillis-(13*60*1000))));
		exchange.registerTrade(new Trade(GIN, 96.53f, 298, TradeType.SELL, new Date(currentTimestampMillis-(13*60*1000))));
		exchange.registerTrade(new Trade(TEA, 97.74f, 345, TradeType.BUY, new Date(currentTimestampMillis-(11*60*1000))));
		exchange.registerTrade(new Trade(ALE, 67.48f, 289, TradeType.BUY, new Date(currentTimestampMillis-(11*60*1000))));
		exchange.registerTrade(new Trade(JOE, 244.53f, 728, TradeType.SELL, new Date(currentTimestampMillis-(11*60*1000))));
		exchange.registerTrade(new Trade(POP, 92.75f, 390, TradeType.SELL, new Date(currentTimestampMillis-(10*60*1000))));
		exchange.registerTrade(new Trade(ALE, 67.52f, 278, TradeType.SELL, new Date(currentTimestampMillis-(10*60*1000))));
		exchange.registerTrade(new Trade(JOE, 244.25f, 287, TradeType.BUY, new Date(currentTimestampMillis-(10*60*1000))));
		exchange.registerTrade(new Trade(TEA, 99.20f, 903, TradeType.BUY, new Date(currentTimestampMillis-(10*60*1000))));
		exchange.registerTrade(new Trade(POP, 93.03f, 299, TradeType.BUY, new Date(currentTimestampMillis-(9*60*1000))));
		exchange.registerTrade(new Trade(ALE, 67.04f, 289, TradeType.SELL, new Date(currentTimestampMillis-(9*60*1000))));
		exchange.registerTrade(new Trade(ALE, 66.50f, 934, TradeType.BUY, new Date(currentTimestampMillis-(8*60*1000))));
		exchange.registerTrade(new Trade(JOE, 242.53f, 289, TradeType.SELL, new Date(currentTimestampMillis-(7*60*1000))));
		exchange.registerTrade(new Trade(GIN, 98.22f, 489, TradeType.SELL, new Date(currentTimestampMillis-(7*60*1000))));
		exchange.registerTrade(new Trade(GIN, 98.57f, 893, TradeType.BUY, new Date(currentTimestampMillis-(7*60*1000))));
		exchange.registerTrade(new Trade(TEA, 98.44f, 901, TradeType.SELL, new Date(currentTimestampMillis-(5*60*1000))));
		exchange.registerTrade(new Trade(ALE, 67.36f, 349, TradeType.BUY, new Date(currentTimestampMillis-(5*60*1000))));
		exchange.registerTrade(new Trade(JOE, 240.32f, 278, TradeType.BUY, new Date(currentTimestampMillis-(5*60*1000))));
		exchange.registerTrade(new Trade(POP, 92.94f, 290, TradeType.SELL, new Date(currentTimestampMillis-(4*60*1000))));
		exchange.registerTrade(new Trade(TEA, 97.68f, 348, TradeType.BUY, new Date(currentTimestampMillis-(4*60*1000))));
		exchange.registerTrade(new Trade(TEA, 97.17f, 903, TradeType.SELL, new Date(currentTimestampMillis-(4*60*1000))));
		exchange.registerTrade(new Trade(ALE, 68.00f, 893, TradeType.SELL, new Date(currentTimestampMillis-(3*60*1000))));
		exchange.registerTrade(new Trade(JOE, 239.13f, 788, TradeType.BUY, new Date(currentTimestampMillis-(2*60*1000))));
		exchange.registerTrade(new Trade(POP, 94.11f, 278, TradeType.BUY, new Date(currentTimestampMillis-(1*60*1000))));
		exchange.registerTrade(new Trade(ALE, 68.34f, 389, TradeType.SELL, new Date(currentTimestampMillis-(1*60*1000))));
		exchange.registerTrade(new Trade(JOE, 241.05f, 238, TradeType.BUY, new Date(currentTimestampMillis-(1*60*1000))));
	}
	
	public static void main(String[] args) {
		Iterator<Stock> stockIterator = exchange.getRegisteredStocksIterator();
		while (stockIterator.hasNext()) {
			Stock stock = stockIterator.next();
			
			// let's first calculate Dividend Yield for the stocks.
			try {
				System.out.printf("\n %15s for %20s: %.2f%%", "Dividend Yield", stock, stock.calculateDividendYield(100f)*100);
			} catch (StockException e) {
				System.err.print("\n Calculation of Dividend Yield failed: " + e.getMessage());
			}
			
			// now, let's calculate P/E ratio for the stocks.
			try {
				System.out.printf("\n %15s for %20s: %.2f", "P/E Ratio", stock, stock.calculatePERatio(100f));
			} catch (StockException e) {
				System.err.print("\n Calculation of P/E Ratio failed: " + e.getMessage());
			}

			// finally, time to calculate Volume Weighted Stock Price.
			try {
				System.out.printf("\n %15s for %20s: %.2f", "VWSP", stock, exchange.calculateVWSP(stock));
			} catch (TradeException e) {
				System.err.print("\n Calculation of VWSP failed: " + e.getMessage());
			}
			
			System.out.print("\n");
		}
		
		try {
			System.out.printf("\n %15s for %20s: %.2f", "Geometric Mean", "all stocks in GBCE", exchange.calculateGeometricMean());
		} catch (TradeException e) {
			System.err.print("\n Calculation of Geometric Mean failed: " + e.getMessage());
		}
	}

}

/** Sample output...

  Dividend Yield for     COMMON stock POP: 8.00%
       P/E Ratio for     COMMON stock POP: 12.50
            VWSP for     COMMON stock POP: 93.35

  Dividend Yield for     COMMON stock TEA: 0.00%
 Calculation of P/E Ratio failed: Dividend never issued for COMMON stock TEA
            VWSP for     COMMON stock TEA: 97.78

  Dividend Yield for     COMMON stock JOE: 13.00%
       P/E Ratio for     COMMON stock JOE: 7.69
            VWSP for     COMMON stock JOE: 240.24

  Dividend Yield for  PREFERRED stock SNT: 0.00%
 Calculation of P/E Ratio failed: Dividend never issued for PREFERRED stock SNT
 Calculation of VWSP failed: No trade registered for PREFERRED stock SNT

  Dividend Yield for     COMMON stock ALE: 23.00%
       P/E Ratio for     COMMON stock ALE: 4.35
            VWSP for     COMMON stock ALE: 67.38

  Dividend Yield for  PREFERRED stock GIN: 2.00%
       P/E Ratio for  PREFERRED stock GIN: 50.00
            VWSP for  PREFERRED stock GIN: 98.45

  Geometric Mean for   all stocks in GBCE: 107.78
              
 */
