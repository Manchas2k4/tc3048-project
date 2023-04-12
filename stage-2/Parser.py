from Lexer import *

class Parser:
    __lex = None
    __token = None

    def __init__(self, filepath):
        self.__lex = Lexer(filepath)
        self.__token = None

        self.__firstPrimaryExpression = set((Tag.ID, Tag.NUMBER, Tag.TRUE, Tag.FALSE, ord('(')))

        self.__firstUnaryExpression = self.__firstPrimaryExpression.union( set((ord('-'), ord('!'))) )
        
        self.__firstExtendedMultiplicativeExpression = set((ord('*'), ord('/'), Tag.MOD))

        self.__firstMultiplicativeExpression = self.__firstUnaryExpression

    def __check(self, tag):
        if self.__token == tag:
            self.__token = self.__lex.scan()
        else:
            raise Exception('Syntax Error')
    
    def analize(self):
        self.__token = self.__lex.scan()
        ## self.__program()

    def __primaryExpression(self):
        if self.__token.getTag() in self.__firstPrimaryExpression:
            if self.__token.getTag() == Tag.ID:
                self.__check(Tag.ID)
            elif self.__token.getTag() == Tag.NUMBER:
                self.__check(Tag.NUMBER)
            elif self.__token.getTag() == Tag.TRUE:
                self.__check(Tag.TRUE)
            elif self.__token.getTag() == Tag.FALSE:
                self.__check(Tag.FALSE)
            elif self.__token.getTag() == ord('('):
                self.__check('(')
                # self.__expression()
                self.__check(')')
        else:
            raise Exception('Syntax Error')
        
    def __unaryExpression(self):
        if self.__token.getTag() in self.__firstPrimaryExpression:
            if self.__token.getTag() == ord('-'):
                self.__check(ord('-'))
            elif self.__token.getTag() == ord('!'):
                self.__check(ord('!'))
            else:
                self.__primaryExpression()
        else: 
            raise Exception('Syntax Error')
        
    def __extendedMultiplicativeExpression(self):
        if self.__token.getTag() in self.__firstExtendedMultiplicativeExpression:
            if self.__token.getTag() == ord('*'):
                self.__check(ord('*'))
                self.__check(ord('*'))
                self.__unaryExpression()
                self.__extendedMultiplicativeExpression()
            elif self.__token.getTag() == ord('/'):
                self.__check(ord('/'))
                self.__unaryExpression()
                self.__extendedMultiplicativeExpression()
            elif self.__token.getTag() == Tag.MOD:
                self.__check(Tag.MOD)
                self.__unaryExpression()
                self.__extendedMultiplicativeExpression()