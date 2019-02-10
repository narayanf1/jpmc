from enum import Enum

class StockException(Exception):
    pass

class StockType(Enum):
    COMMON      = "Common Stock"
    PREFERRED   = "Preferred Stock"
    
class Stock(object):
    """ Represents a stock on the market
        stock_symbol identifies a stock and is used for equality check
        
        @author Lucky (LakshmiNarayana Ambarkar)
    """
    
    def __init__(self, stock_symbol, stock_type, last_dividend, fixed_dividend_percent, par_value):
        """ Create a stock object with symbol, type and par value.
            For simplicity, setting last_divident fixed_dividend_percent through the initializer method. 
        """
        self.__stock_symbol             = stock_symbol
        self.__stock_type               = stock_type
        self.__last_dividend            = last_dividend
        self.__fixed_dividend_percent   = fixed_dividend_percent
        self.__par_value                = par_value

        if stock_type == StockType.PREFERRED:
            self.__last_dividend = (fixed_dividend_percent/100) * par_value;
            
    def __str__(self):
        # nicely represent the stock on console.
        return self.__stock_type.value + " " + self.__stock_symbol
    
    def __eq__(self, other_stock):
        return self.__stock_symbol == other_stock.get_stock_symbol()

    def get_stock_symbol(self):
        return self.__stock_symbol
    
    def set_stock_type(self, stockType):
        self.__stock_type  = stockType
    
    def get_stock_type(self):
        return self.__stock_type
    
    def get_last_dividend(self):
        return self.__last_dividend
    
    def get_fixed_dividend_percent(self):
        return self.__fixed_dividend_percent
    
    def get_par_value(self):
        return self.__par_value
    
    def calculate_dividend_yield(self, stock_price):
        if (stock_price <= 0):
            raise StockException("Invalid price for " + str(self))
        
        # for PREFERRED stocks, lastDividend = parValue X fixedDividend/100.
        return self.get_last_dividend() / stock_price;
    
    def calculate_PE_ratio(self, stock_price):
        if (stock_price <= 0):
            raise StockException("Invalid price for " + str(self))
        
        if (self.__last_dividend == 0):
            raise StockException("Dividend never issued for " + str(self))
        
        return stock_price / self.__last_dividend
