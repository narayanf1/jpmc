import threading
from datetime import datetime
from ..stocks.stock import Stock
from ..exchange.trade import TradeException
from ..util.utilities import MathUtil

def synchronized(method):
    """ Wrapper method to synchronize a method using the threading module and lock.
        @author Lucky (LakshmiNarayana Ambarkar)
    """
    
    method.__lock = threading.Lock()
    
    def synchronized_method(*args, **kwargs):
        with method.__lock:
            return method(*args, **kwargs)
    return synchronized_method


class GlobalBeverageCorporationExchange(object):
    """ Represents the Global Beverage Corporation Exchange Board.
    
        Only one instance of the exchange board can be active, hence using singleton.
        This class acts as a proxy to support singleton model for the actual nested Exchange class.
        __new__ method is overriden to return the single object of __Exchange class.  
        
        @author Lucky (LakshmiNarayana Ambarkar)
    """
    
    OBSERVED_PERIOD_MINUTES = 10
    
    class __Exchange(object):
        """ The actual Exchange class.
        
            The exchange board maintains a private set of stocks and a list of trades registered.
            Trades are registered through a synchronized register_trade method.
        """
        
        def __init__(self):
            self.__registered_stocks    = set([])
            self.__trade_list           = []

        def register_stock(self, stock):
            self.__registered_stocks.add(stock)
            
        def get_stocks(self):
            for stock in self.__registered_stocks:
                yield Stock(stock.get_stock_symbol(), stock.get_stock_type(), stock.get_last_dividend(), stock.get_fixed_dividend_percent(), stock.get_par_value())

        @synchronized
        def register_trade(self, trade):
            """ Registers a trade on the exchange.
                This call is synchronized to avoid inconsistent state in a multi-threaded environment.
            """
            self.__trade_list.append(trade)
            
        def calculate_VWSP(self, stock):
            """ Calculates the Volume Weighted Stock Price for the given stock.
                Only trades registered within a defined time period from current time are considered.
            """
            numerator = 0.0     # sum of (price X quantity) from each trade of the stock 
            denominator = 0.0   # sum of quantity from each trade of the stock
            
            current_timestamp = datetime.now()
            
            for trade in self.__trade_list:
                time_diff_minutes = (current_timestamp - trade.get_timestamp()).total_seconds()/60.0
                if time_diff_minutes <= GlobalBeverageCorporationExchange.OBSERVED_PERIOD_MINUTES:
                    if trade.get_stock() == stock:
                        numerator   += (trade.get_price() * trade.get_quantity())
                        denominator += trade.get_quantity()
            
            if (numerator == 0 or denominator == 0):
                raise TradeException("No trade registered for " + str(stock))
            
            return numerator/denominator;
            
        def calculate_geometric_mean(self):
            """ Calculates the geometric mean for all those stocks for which VWSP is available.
            
                Step 1: For each stock, get the VWSP and calculate their cumulative produce.
                Step 2: calculate Nth root of the product, where N is the number of stocks for which VWSP was obtained.
            """
            vwsp_product    = -1;    # initialized with -1 - just in case there is no VWSP for any stocks
            stock_count     = 0;
            for stock in self.__registered_stocks:
                try:
                    vwsp = self.calculate_VWSP(stock)
                    stock_count += 1
                    vwsp_product = vwsp if vwsp_product == -1 else vwsp_product*vwsp
                except TradeException as e:
                    pass    # log the exception & skip the stock
                
            if vwsp_product != -1:
                return MathUtil.n_root_of_x(stock_count, vwsp_product)
            else:
                raise TradeException("VWSP not available for any stock")
            
            
    __exchange = __Exchange()
    
    def __new__(self):
        return GlobalBeverageCorporationExchange.__exchange
            
    def  get_stocks(self):
        return __exchange.get_stocks()
