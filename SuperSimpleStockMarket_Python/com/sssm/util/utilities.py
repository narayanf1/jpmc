class MathUtil(object):
    """ Utility method for mathematical calculations that are not available in the standard math library.
        @author Lucky (LakshmiNarayana Ambarkar)
    """
    
    @staticmethod
    def n_root_of_x(n, x):
        """ Calculates the Nth root of a given number.
            Logic obtained from https://stackoverflow.com/questions/26839890/calculating-nth-root-of-a-positive-integer-in-java
        """
        if n==0:
            return 1
        
        return 1 if n==0 else x**(1.0/n)
