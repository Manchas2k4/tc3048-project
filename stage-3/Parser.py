from Lexer import *
from Environment import *

class Parser:
    __lex = None
    __token = None
    __env = None
    __output = ""

    def __init__(self, filepath):
        self.__lex = Lexer(filepath)
        self.__token = None
        self.__env = Environment()
        self.__output = ""

        self.__firstPrimaryExpression = set((Tag.ID, Tag.NUMBER, Tag.TRUE, Tag.FALSE, ord('(')))

        self.__firstUnaryExpression = self.__firstPrimaryExpression.union( set((ord('-'), ord('!'))) )

        self.__firstExtendedMultiplicativeExpression = set((ord('*'), ord('/'), Tag.MOD))

        self.__firstMultiplicativeExpression = self.__firstUnaryExpression

        self.__firstExtendedAdditiveExpression = set((ord('+'), ord('-')))

        self.__firstAdditiveExpression = self.__firstMultiplicativeExpression

        self.__firstExtendedRelationExpresion = set((ord('<'), Tag.LEQ, ord('>'), Tag.GEQ))

        self.__firstRelationalExpression = self.__firstAdditiveExpression

        self.__firstExtendedEqualityExpression = set((ord('='), Tag.NEQ))

        self.__firstEqualityExpression = self.__firstRelationalExpression

        self.__firstConditionalTerm = self.__firstEqualityExpression

        self.__firstConditionalExpression = self.__firstConditionalTerm

        self.__firstExpression = self.__firstConditionalExpression

        self.__firstConditionalStatement = set((Tag.IF, Tag.IFELSE))

        self.__firstStructuredStatement = self.__firstConditionalStatement.union({Tag.REPEAT})

        self.__firstElement = self.__firstExpression.union({Tag.STRING})

        self.__firstDrawingStatement = set((Tag.CLEAR, Tag.CIRCLE, Tag.ARC, Tag.PENUP, 
                                            Tag.PENDOWN, Tag.COLOR, Tag.PENWIDTH))

        self.__firstMovementStatement = set((Tag.FORWARD, Tag.BACKWARD, Tag.RIGHT, Tag.LEFT, 
                                             Tag.SETX, Tag.SETY, Tag.SETXY, Tag.HOME))
        
        self.__firstSimpleStatement = (self.__firstMovementStatement.union(self.__firstDrawingStatement)).union(
            set((Tag.VAR, Tag.ID, Tag.PRINT)))
        
        self.__firstStatement = self.__firstSimpleStatement.union(self.__firstStructuredStatement)

        self.__firstStatementSequence = self.__firstStatement

        self.__firstProgram = self.__firstStatementSequence

    def __check(self, tag, extra = None):
        if self.__token.getTag() == tag:
            self.__token = self.__lex.scan()
        else:
            text = 'Line ' + str(self.__lex.getLine())
            if extra == None:
                text = text + "."
            else:
                text = text + extra
            raise Exception(text)
    
    def analize(self):
        self.__token = self.__lex.scan()
        self.__program()

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
                self.__check(ord('('))
                self.__expression()
                self.__check(ord(')'))
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
        #else:

    def __multiplicativeExpression(self):
        if self.__token.getTag() in self.__firstMultiplicativeExpression:
            self.__unaryExpression()
            self.__extendedMultiplicativeExpression()
        else: 
            raise Exception('Syntax Error')
        
    def __extendedAdditiveExpression(self):
        if self.__token.getTag() in self.__firstExtendedAdditiveExpression:
            if self.__token.getTag() == ord('+'):
                self.__check(ord('+'))
                self.__multiplicativeExpression()
                self.__extendedAdditiveExpression()
            elif self.__token.getTag() == ord('-'):
                self.__check(ord('-'))
                self.__multiplicativeExpression()
                self.__extendedAdditiveExpression()

    def __additiveExpression(self):
        if self.__token.getTag() in self.__firstAdditiveExpression:
            self.__multiplicativeExpression()
            self.__extendedAdditiveExpression()
        else: 
            raise Exception('Syntax Error')
        
    def __extendedRelationalExpression(self):
        if self.__token.getTag() in self.__firstExtendedRelationExpresion:
            if self.__token.getTag() == ord('<'):
                self.__check(ord('<'))
                self.__additiveExpression()
                self.__extendedRelationalExpression()
            elif self.__token.getTag() == Tag.LEQ:
                self.__check(Tag.LEQ)
                self.__additiveExpression()
                self.__extendedRelationalExpression()
            elif self.__token.getTag() == ord('>'):
                self.__check(ord('>'))
                self.__additiveExpression()
                self.__extendedRelationalExpression()
            elif self.__token.getTag() == Tag.GEQ:
                self.__check(Tag.GEQ)
                self.__additiveExpression()
                self.__extendedRelationalExpression()

    def __relationalExpression(self):
        if self.__token.getTag() in self.__firstRelationalExpression:
            self.__additiveExpression()
            self.__extendedRelationalExpression()
        else: 
            raise Exception('Syntax Error')
        
    def __extendedEqualityExpression(self):
        if self.__token.getTag() in self.__firstExtendedEqualityExpression:
            if self.__token.getTag() == ord('='):
                self.__check(ord('='))
                self.__relationalExpression()
                self.__extendedEqualityExpression()
            elif self.__token.getTag() == Tag.NEQ:
                self.__check(Tag.NEQ)
                self.__relationalExpression()
                self.__extendedEqualityExpression()

    def __equalityExpression(self):
        if self.__token.getTag() in self.__firstEqualityExpression:
            self.__relationalExpression()
            self.__extendedEqualityExpression()
        else:
            raise Exception('Syntax Error')
        
    def __extendedConditionalTerm(self):
        if self.__token.getTag() == Tag.AND:
            self.__check(Tag.AND)
            self.__equalityExpression()
            self.__extendedConditionalTerm()

    def __conditionalTerm(self):
        if self.__token.getTag() in self.__firstEqualityExpression:
            self.__equalityExpression()
            self.__extendedConditionalTerm()
        else:
            raise Exception('Syntax Error')
        
    def __extendedConditionalExpression(self):
        if self.__token.getTag() == Tag.OR:
            self.__check(Tag.OR)
            self.__conditionalTerm()
            self.__extendedConditionalExpression()

    def __conditionalExpression(self):
        if self.__token.getTag() in self.__firstConditionalExpression:
            self.__conditionalTerm()
            self.__extendedConditionalExpression()
        else:
            raise Exception('Syntax Error')
        
    def __expression(self):
        if self.__token.getTag() in self.__firstExpression:
            self.__conditionalExpression()
        else:
            raise Exception('Syntax Error')
        
    def __ifElseStatement(self):
        if self.__token.getTag() == Tag.IFELSE:
            self.__check(Tag.IFELSE)
            self.__expression()
            self.__check(ord('['))
            self.__statementSequence()
            self.__check(ord(']'))
            self.__check(ord('['))
            self.__statementSequence()
            self.__check(ord(']'))
        else:
            raise Exception('Syntax Error')
        
    def __ifStatement(self):
        if self.__token.getTag() == Tag.IF:
            self.__check(Tag.IF)
            self.__expression()
            self.__check(ord('['))
            self.__statementSequence()
            self.__check(ord(']'))
        else:
            raise Exception('Syntax Error')

    def __conditionalStatement(self):
        if self.__token.getTag() in self.__firstConditionalStatement:
            if self.__token.getTag() == Tag.IF:
                self.__ifStatement()
            elif self.__token.getTag() == Tag.IFELSE:
                self.__ifElseStatement()
        else:
            raise Exception('Syntax Error')

    def __repetitiveStatement(self):
        if self.__token.getTag() == Tag.REPEAT:
            self.__check(Tag.REPEAT)
            self.__expression()
            self.__check(ord('['))
            self.__statementSequence()
            self.__check(ord(']'))
        else:
            raise Exception('Syntax Error')
        
    def __structuredStatement(self):
        if self.__token.getTag() in self.__firstStructuredStatement:
            if self.__token.getTag() in self.__firstConditionalStatement:
                self.__conditionalStatement()
            elif self.__token.getTag() == Tag.REPEAT:
                self.__repetitiveStatement()
        else:
            raise Exception('Syntax Error')
        
    def __element(self):
        if self.__token.getTag() in self.__firstElement:
            if self.__token.getTag() == Tag.STRING:
                self.__check(Tag.STRING)
            elif self.__token.getTag() in self.__firstExpression:
                self.__expression()
        else:
            raise Exception('Syntax Error')
        
    def __elementList(self):
        if self.__token.getTag() == ord(','):
            self.__check(ord(','))
            self.__element()
            self.__elementList()
        
    def __textStatement(self):
        if self.__token.getTag() == Tag.PRINT:
            self.__check(Tag.PRINT)
            self.__check(ord('['))
            self.__element()
            self.__elementList()
            self.__check(ord(']'))

    def __penWidthStatement(self):
        if self.__token.getTag() == Tag.PENWIDTH:
            self.__check(Tag.PENWIDTH)
            self.__expression()
        else:
            raise Exception('Syntax Error')
        
    def __colorStatement(self):
        if self.__token.getTag() == Tag.COLOR:
            self.__check(Tag.COLOR)
            self.__expression()
            self.__check(ord(','))
            self.__expression()
            self.__check(ord(','))
            self.__expression()
        else:
            raise Exception('Syntax Error')
        
    def __penDownStatement(self):
        if self.__token.getTag() == Tag.PENDOWN:
            self.__check(Tag.PENDOWN)
        else:
            raise Exception('Syntax Error')
        
    def __penUpStatement(self):
        if self.__token.getTag() == Tag.PENUP:
            self.__check(Tag.PENUP)
        else:
            raise Exception('Syntax Error')
        
    def __arcStatement(self):
        if self.__token.getTag() == Tag.ARC:
            self.__check(Tag.ARC)
            self.__expression()
        else:
            raise Exception('Syntax Error')
        
    def __circleStatement(self):
        if self.__token.getTag() == Tag.CIRCLE:
            self.__check(Tag.CIRCLE)
            self.__expression()
        else:
            raise Exception('Syntax Error')
        
    def __clearStatement(self):
        if self.__token.getTag() == Tag.CLEAR:
            self.__check(Tag.CLEAR)
        else:
            raise Exception('Syntax Error')

    def __drawingStatement(self):
        if self.__token.getTag() in self.__firstDrawingStatement:
            if self.__token.getTag() == Tag.CLEAR:
                self.__clearStatement()
            elif self.__token.getTag() == Tag.CIRCLE:
                self.__circleStatement()
            elif self.__token.getTag() == Tag.ARC:
                self.__arcStatement()
            elif self.__token.getTag() == Tag.PENUP:
                self.__penUpStatement()
            elif self.__token.getTag() == Tag.PENDOWN:
                self.__penDownStatement()
            elif self.__token.getTag() == Tag.COLOR:
                self.__colorStatement()
            elif self.__token.getTag() == Tag.PENWIDTH:
                self.__penWidthStatement()

    def __setXYStatement(self):
        if self.__token.getTag() == Tag.SETXY:
            self.__check(Tag.SETXY)
            self.__expression()
            self.__check(ord(','))
            self.__expression()
        else:
            raise Exception('Syntax Error')
        
    def __setXStatement(self):
        if self.__token.getTag() == Tag.SETX:
            self.__check(Tag.SETX)
            self.__expression()
        else:
            raise Exception('Syntax Error')
        
    def __setYStatement(self):
        if self.__token.getTag() == Tag.SETY:
            self.__check(Tag.SETY)
            self.__expression()
        else:
            raise Exception('Syntax Error')
        
    def __leftStatement(self):
        if self.__token.getTag() == Tag.LEFT:
            self.__check(Tag.LEFT)
            self.__expression()
        else:
            raise Exception('Syntax Error')
        
    def __rightStatement(self):
        if self.__token.getTag() == Tag.RIGHT:
            self.__check(Tag.RIGHT)
            self.__expression()
        else:
            raise Exception('Syntax Error')
        
    def __backwardStatement(self):
        if self.__token.getTag() == Tag.BACKWARD:
            self.__check(Tag.BACKWARD)
            self.__expression()
        else:
            raise Exception('Syntax Error')
        
    def __forwardStatement(self):
        if self.__token.getTag() == Tag.FORWARD:
            self.__check(Tag.FORWARD)
            self.__expression()
        else:
            raise Exception('Syntax Error')
        
    def __movementStatement(self):
        if self.__token.getTag() in self.__firstMovementStatement:
            if self.__token.getTag() == Tag.FORWARD:
                self.__forwardStatement()
            elif self.__token.getTag() == Tag.BACKWARD:
                self.__backwardStatement()
            elif self.__token.getTag() == Tag.RIGHT:
                self.__rightStatement()
            elif self.__token.getTag() == Tag.LEFT:
                self.__leftStatement()
            elif self.__token.getTag() == Tag.SETX:
                self.__setXStatement()
            elif self.__token.getTag() == Tag.SETY:
                self.__setYStatement()
            elif self.__token.getTag() == Tag.SETXY:
                self.__setXYStatement()
            elif self.__token.getTag() == Tag.HOME:
                self.__check(Tag.HOME)
        else:
            raise Exception('Syntax Error')
        
    def __assigmentStatement(self):
        if self.__token.getTag() == Tag.ID:
            self.__check(Tag.ID)
            self.__check(Tag.ASSIGN)
            self.__expression()
        else:
            raise Exception('Syntax Error')
        
    def __identifierList(self):
        if self.__token.getTag() == ord(','):
            self.__check(ord(','))
            self.__check(Tag.ID)
            self.__identifierList()

    def __declarationStatement(self):
        if self.__token.getTag() == Tag.VAR:
            self.__check(Tag.VAR)
            self.__check(Tag.ID)
            self.__identifierList()
        else:
            raise Exception('Syntax error in declaration statement')
        
    def __simpleStatement(self):
        if self.__token.getTag() in self.__firstSimpleStatement:
            if self.__token.getTag() == Tag.VAR:
                self.__declarationStatement()
            elif self.__token.getTag() == Tag.ID:
                self.__assigmentStatement()
            elif self.__token.getTag() in self.__firstMovementStatement:
                self.__movementStatement()
            elif self.__token.getTag() in self.__firstDrawingStatement:
                self.__drawingStatement()
            elif self.__token.getTag() == Tag.PRINT:
                self.__textStatement()
        else:
            raise Exception('Syntax Error')
        
    def __statement(self):
        if self.__token.getTag() in self.__firstStatement:
            if self.__token.getTag() in self.__firstSimpleStatement:
                self.__simpleStatement()
            elif self.__token.getTag() in self.__firstStructuredStatement:
                self.__structuredStatement()
        else:
            raise Exception('Syntax Error')
        
    def __statementSequence(self):
        if self.__token.getTag() in self.__firstStatementSequence:
            self.__statement()
            self.__statementSequence()

    def __program(self):
        if self.__token.getTag() in self.__firstProgram:
            self.__statementSequence()
