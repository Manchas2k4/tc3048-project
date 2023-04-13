class Environment:
    __table = {}
    __previous = None

    def __init__(self, env = None):
        self.__previous = env
        self.__table = {}

    def getPrevious(self):
        return self.__previous
    
    def lookup(self, variable):
        if variable in self.__table:
            return self.__table[variable]
        elif self.__previous != None:
            return self.__previous.lookup(variable)
        else: 
            return None
    
    def insert(self, variable):
        if not variable in self.__table:
            self.__table[variable] = 0.0
            return True
        else:
            return False
        
    def set(self, variable, value):
        env = self
        found = False
        while env != None:
            if variable in env.__table:
                found = True
                break
            env = env.__previous
        
        if not found:
            env.__table[variable] = value
            return True
        else: 
            # return False
            raise Exception('Semantic Error - The variable has not been defined')
