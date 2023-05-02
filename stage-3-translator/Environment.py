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
            # The variable is a local to this scope.
            return self.__table[variable]
        elif self.__previous != None:
            # The variable maybe is declared in an upper scope.
            return self.__previous.lookup(variable)
        else: 
            return None
    
    def insert(self, variable):
        if not variable in self.__table:
            # the variable has not been declared.
            self.__table[variable] = (None, None)
            return True
        else:
            # the variable has already been declared.
            return False
        
    def set(self, variable, type = None, value = None):
        env = self
        found = False
        while env != None:
            if variable in env.__table:
                found = True
                break
            env = env.__previous
        
        if found:
            # the variable is declared.
            env.__table[variable] = (type, value)
            return True
        else: 
            # the variable is not declared.
            return False
