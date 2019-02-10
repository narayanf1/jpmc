from .com.sssm.exchange.exchange import GlobalBeverageCorporationExchange
from .com.sssm.exchange.trade import Trade, TradeType, TradeException
from .com.sssm.stocks.stock import Stock, StockType, StockException
from datetime import datetime, timedelta

def run_metrics(exchange):
    """ Calculate Dividend Yield, P/E Ratio and VWSP for each stock.
    """
    for stock in exchange.get_stocks():
        # let's first calculate Dividend Yield for the stocks.
        try:
            print " {:>15s} for {:>20s}: {:>.2f}%".format("Dividend Yield", str(stock), stock.calculate_dividend_yield(100.0)*100)
        except StockException as e:
            print " Calculation of Dividend Yield failed: " + str(e)
        
        # now, let's calculate P/E ratio for the stocks.
        try:
            print " {:>15s} for {:>20s}: {:>.2f}".format("P/E Ratio", str(stock), stock.calculate_PE_ratio(100.0))
        except StockException as e:
            print " Calculation of P/E Ratio failed: " + str(e)

        # finally, time to calculate Volume Weighted Stock Price.
        try:
            print " {:>15s} for {:>20s}: {:>.2f}".format("VWSP", str(stock), exchange.calculate_VWSP(stock))
        except TradeException as e:
            print " Calculation of VWSP failed: " + str(e)
        
        print ""

def run_geometric_mean(exchange):        
    """ Calculate Geometric Mean for all the stocks.
    """
    try:
        print " {:>15s} for {:>20s}: {:>.2f}".format("Geometric Mean", "for all stocks in GBCE", exchange.calculate_geometric_mean())
    except TradeException as e:
        print " Calculation of Geometric Mean failed: " + str(e)

def main():
    exchange = GlobalBeverageCorporationExchange()
    
    # registering stocks & trades in static initialization block for test purpose.
    # Ideally, should be registered in a more controlled manner.
    TEA = Stock("TEA", StockType.COMMON,    0.0,    0.0,    100.0)
    POP = Stock("POP", StockType.COMMON,    8.0,    0.0,    100.0)
    ALE = Stock("ALE", StockType.COMMON,    23.0,   0.0,    60.0)
    GIN = Stock("GIN", StockType.PREFERRED, 8.0,    2.0,    100.0)
    JOE = Stock("JOE", StockType.COMMON,    13.0,   0.0,    250.0)
    
    exchange.register_stock(TEA)
    exchange.register_stock(POP)
    exchange.register_stock(ALE)
    exchange.register_stock(GIN)
    exchange.register_stock(JOE)
    
    # a stock that's not traded ever! (SNT)
    SNT = Stock("SNT", StockType.PREFERRED, 0.0,    0.0,    10.0);
    exchange.register_stock(SNT)

    current_timestamp = datetime.now()
    exchange.register_trade(Trade(TEA, 96.53,   328, TradeType.BUY,     current_timestamp - timedelta(seconds=14*60)))
    exchange.register_trade(Trade(POP, 92.27,   343, TradeType.BUY,     current_timestamp - timedelta(seconds=14*60)))
    exchange.register_trade(Trade(TEA, 97.26,   234, TradeType.SELL,    current_timestamp - timedelta(seconds=13*60)))
    exchange.register_trade(Trade(ALE, 67.54,   464, TradeType.BUY,     current_timestamp - timedelta(seconds=13*60)))
    exchange.register_trade(Trade(GIN, 96.53,   298, TradeType.SELL,    current_timestamp - timedelta(seconds=13*60)))
    exchange.register_trade(Trade(TEA, 97.74,   345, TradeType.BUY,     current_timestamp - timedelta(seconds=11*60)))
    exchange.register_trade(Trade(ALE, 67.48,   289, TradeType.BUY,     current_timestamp - timedelta(seconds=11*60)))
    exchange.register_trade(Trade(JOE, 244.53,  728, TradeType.SELL,    current_timestamp - timedelta(seconds=11*60)))
    exchange.register_trade(Trade(POP, 92.75,   390, TradeType.SELL,    current_timestamp - timedelta(seconds=10*60)))
    exchange.register_trade(Trade(ALE, 67.52,   278, TradeType.SELL,    current_timestamp - timedelta(seconds=10*60)))
    exchange.register_trade(Trade(JOE, 244.25,  287, TradeType.BUY,     current_timestamp - timedelta(seconds=10*60)))
    exchange.register_trade(Trade(TEA, 99.20,   903, TradeType.BUY,     current_timestamp - timedelta(seconds=10*60)))
    exchange.register_trade(Trade(POP, 93.03,   299, TradeType.BUY,     current_timestamp - timedelta(seconds=9*60)))
    exchange.register_trade(Trade(ALE, 67.04,   289, TradeType.SELL,    current_timestamp - timedelta(seconds=9*60)))
    exchange.register_trade(Trade(ALE, 66.50,   934, TradeType.BUY,     current_timestamp - timedelta(seconds=8*60)))
    exchange.register_trade(Trade(JOE, 242.53,  289, TradeType.SELL,    current_timestamp - timedelta(seconds=7*60)))
    exchange.register_trade(Trade(GIN, 98.22,   489, TradeType.SELL,    current_timestamp - timedelta(seconds=7*60)))
    exchange.register_trade(Trade(GIN, 98.57,   893, TradeType.BUY,     current_timestamp - timedelta(seconds=7*60)))
    exchange.register_trade(Trade(TEA, 98.44,   901, TradeType.SELL,    current_timestamp - timedelta(seconds=5*60)))
    exchange.register_trade(Trade(ALE, 67.36,   349, TradeType.BUY,     current_timestamp - timedelta(seconds=5*60)))
    exchange.register_trade(Trade(JOE, 240.32,  278, TradeType.BUY,     current_timestamp - timedelta(seconds=5*60)))
    exchange.register_trade(Trade(POP, 92.94,   290, TradeType.SELL,    current_timestamp - timedelta(seconds=4*60)))
    exchange.register_trade(Trade(TEA, 97.68,   348, TradeType.BUY,     current_timestamp - timedelta(seconds=4*60)))
    exchange.register_trade(Trade(TEA, 97.17,   903, TradeType.SELL,    current_timestamp - timedelta(seconds=4*60)))
    exchange.register_trade(Trade(ALE, 68.00,   893, TradeType.SELL,    current_timestamp - timedelta(seconds=3*60)))
    exchange.register_trade(Trade(JOE, 239.13,  788, TradeType.BUY,     current_timestamp - timedelta(seconds=2*60)))
    exchange.register_trade(Trade(POP, 94.11,   278, TradeType.BUY,     current_timestamp - timedelta(seconds=1*60)))
    exchange.register_trade(Trade(ALE, 68.34,   389, TradeType.SELL,    current_timestamp - timedelta(seconds=1*60)))
    exchange.register_trade(Trade(JOE, 241.05,  238, TradeType.BUY,     current_timestamp - timedelta(seconds=1*60)))

    run_metrics(exchange)
    run_geometric_mean(exchange)
    
if __name__ == '__main__':
    """ Run the main method
    """
    main()
