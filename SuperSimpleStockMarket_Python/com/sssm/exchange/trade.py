from enum import Enum
from copy import deepcopy
from ..stocks.stock import Stock

class TradeException(Exception):
    pass

class TradeType(Enum):
    BUY     = "Buy"
    SELL    = "Sell"
    
class Trade(object):
    """ Represents a trade to be registered on the exchange board.
        All elements except timestamp are final - once set cannot be changed.  Ideally, timestamp is set while being registered.
        
        @author Lucky (LakshmiNarayana Ambarkar)
    """
    
    def __init__(self, stock, price, quantity, trade_type, timestamp):
        # for simplicity, setting timestamp through initializer.
        self.__stock        = stock
        self.__price        = price
        self.__quantity     = quantity
        self.__trade_type   = trade_type
        self.__timestamp    = timestamp

    def get_stock(self):
        return Stock(self.__stock.get_stock_symbol(), self.__stock.get_stock_type(), self.__stock.get_last_dividend(), self.__stock.get_fixed_dividend_percent(), self.__stock.get_par_value())

    def get_price(self):
        return self.__price

    def get_quantity(self):
        return self.__quantity;
    
    def get_timestamp(self):
        return self.__timestamp
