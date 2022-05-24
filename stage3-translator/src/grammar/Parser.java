package grammar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashSet;

import errors.SyntaxError;
import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

public class Parser {
	private Lexer lexer;
	private Token token;
	private static LinkedHashSet<Integer> arithmeticFactorFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> arithmeticTermFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> arithmeticExpressionFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> booleanFactorFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> booleanTermFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> simpleBooleanExpressionFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> booleanExpressionFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> drawingStatementFirst = new LinkedHashSet<Integer>();

	public Parser (FileInputStream input) {
		lexer = new Lexer(input);
		
		arithmeticFactorFirst.add(Tag.ID); arithmeticFactorFirst.add(Tag.NUMBER);
		arithmeticFactorFirst.add((int) '(');
		
		arithmeticTermFirst.addAll(arithmeticFactorFirst);
		
		arithmeticExpressionFirst.addAll(arithmeticTermFirst);
		arithmeticExpressionFirst.add((int) '-'); arithmeticExpressionFirst.add((int) '+');
		
		booleanFactorFirst.add(Tag.TRUE); booleanFactorFirst.add(Tag.FALSE);
		booleanFactorFirst.add((int) '('); booleanFactorFirst.add(Tag.NOT);
		
		booleanTermFirst.addAll(booleanFactorFirst);
		
		simpleBooleanExpressionFirst.addAll(booleanTermFirst);
		
		booleanExpressionFirst.addAll(simpleBooleanExpressionFirst);
		
		drawingStatementFirst.add(Tag.CLEAR); drawingStatementFirst.add(Tag.CIRCLE);
		drawingStatementFirst.add(Tag.ARC); drawingStatementFirst.add(Tag.PENUP);
		drawingStatementFirst.add(Tag.PENDOWN); drawingStatementFirst.add(Tag.COLOR);
		drawingStatementFirst.add(Tag.PENWIDTH);
	}

	private void check(int tag) throws SyntaxError, IOException {
		if (token.getTag() == tag) {
			token = lexer.scan();
		} else {
			throw new SyntaxError();
		}
	}

	public void analyze() throws SyntaxError, IOException {
		token = lexer.scan();
		if (token.getTag() == Tag.EOF) {
			System.out.println("ACCEPTED");
		} else {
			throw new SyntaxError();
		}
	}
	
	private void arithmeticFactor() throws SyntaxError, IOException {
		if (token.getTag() == Tag.ID) {
			check(Tag.ID);
		} else if (token.getTag() == Tag.NUMBER) {
			check(Tag.NUMBER);
		} else if (token.getTag() == ((int) '(')) {
			check((int) '(');
			arithmeticExpression();
			check((int) ')');
		} else {
			throw new SyntaxError();
		}
	}
	
	private void extendedArithmeticTerm() throws SyntaxError, IOException {
		if (token.getTag() == ((int) '*')) {
			check((int) '*');
			arithmeticFactor();
			extendedArithmeticTerm();
		} else if (token.getTag() == ((int) '/')) {
			check((int)'/');
			arithmeticFactor();
			extendedArithmeticTerm();
		} else if (token.getTag() == Tag.MOD) {
			check(Tag.MOD);
			arithmeticFactor();
			extendedArithmeticTerm();
		} else {
			// do nothing
		}
	}
	
	private void arithmeticTerm() throws SyntaxError, IOException {
		if (arithmeticFactorFirst.contains(token.getTag())) {
			arithmeticFactor();
			extendedArithmeticTerm();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void extendedArithmeticExpression() throws SyntaxError, IOException {
		if (token.getTag() == ((int) '+')) {
			check((int) '+');
			arithmeticTerm();
			extendedArithmeticExpression();
		} else if (token.getTag() == ((int) '-')) {
			check((int)'-');
			arithmeticTerm();
			extendedArithmeticExpression();
		} else {
			// do nothing
		}
	}
	
	private void arithmeticExpression() throws SyntaxError, IOException {
		if (token.getTag() == ((int) '-')) {
			check((int) '-');
			arithmeticTerm();
			extendedArithmeticExpression();
		} else if (token.getTag() == ((int) '+')) {
			check((int) '+');
			arithmeticTerm();
			extendedArithmeticExpression();
		} else if (arithmeticTermFirst.contains(token.getTag())) {
			arithmeticTerm();
			extendedArithmeticExpression();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void booleanFactor() throws SyntaxError, IOException {
		if (token.getTag() == Tag.TRUE) {
			check(Tag.TRUE);
		} else if (token.getTag() == Tag.FALSE) {
			check(Tag.FALSE);
		} else if (token.getTag() == ((int) '(')) {
			check((int) '(');
			booleanExpression();
			check((int) ')');
		} else if (token.getTag() == Tag.NOT) {
			check(Tag.NOT);
			booleanFactor();
		} else{
			throw new SyntaxError();
		}
	}
	
	private void extendedBooleanTerm() throws SyntaxError, IOException {
		if (token.getTag() == Tag.AND) {
			check(Tag.AND);
			booleanFactor();
			extendedBooleanTerm();
		} else {
			// do nothing
		}
	}
	
	private void booleanTerm() throws SyntaxError, IOException {
		if (booleanFactorFirst.contains(token.getTag())) {
			booleanFactor();
			extendedBooleanTerm();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void extendedSimpleBooleanExpression() throws SyntaxError, IOException {
		if (token.getTag() == Tag.OR) {
			check(Tag.OR);
			booleanTerm();
			extendedSimpleBooleanExpression();
		} else {
			// do nothing
		}
	}
	
	private void simpleBooleanExpression() throws SyntaxError, IOException {
		if (booleanTermFirst.contains(token.getTag())) {
			booleanTerm();
			extendedSimpleBooleanExpression();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void extendedBooleanExpression() throws SyntaxError, IOException {
		if (token.getTag() == ((int) '=')) {
			check((int) '=');
			simpleBooleanExpression();
			extendedBooleanExpression();
		} else if (token.getTag() == Tag.NEQ) {
			check(Tag.NEQ);
			simpleBooleanExpression();
			extendedBooleanExpression();
		} else if (token.getTag() == ((int) '<')) {
			check((int) '<');
			simpleBooleanExpression();
			extendedBooleanExpression();
		} else if (token.getTag() == Tag.LEQ) {
			check(Tag.LEQ);
			simpleBooleanExpression();
			extendedBooleanExpression();
		} else if (token.getTag() == ((int) '>')) {
			check((int) '>');
			simpleBooleanExpression();
			extendedBooleanExpression();
		} else if (token.getTag() == Tag.GEQ) {
			check(Tag.GEQ);
			simpleBooleanExpression();
			extendedBooleanExpression();
		} else {
			// do nothing
		}
	}
	
	private void booleanExpression() throws SyntaxError, IOException {
		if (simpleBooleanExpressionFirst.contains(token.getTag())) {
			simpleBooleanExpression();
			extendedBooleanExpression();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void ifElseStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.IFELSE) {
			check(Tag.IFELSE);
			booleanExpression();
			check((int) '[');
			//statementSequence();
			check((int) ']');
			check((int) '[');
			//statementSequence();
			check((int) ']');
		} else {
			throw new SyntaxError();
		}
	}
	
	private void ifStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.IF) {
			check(Tag.IF);
			booleanExpression();
			check((int) '[');
			//statementSequence();
			check((int) ']');
		} else {
			throw new SyntaxError();
		}
	}
	
	private void conditionalStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.IF) {
			ifStatement();
		} else if (token.getTag() == Tag.IFELSE) {
			ifElseStatement();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void repeatStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.REPEAT) {
			check(Tag.REPEAT);
			arithmeticExpression();
			check((int) '[');
			//statementSequence();
			check((int) ']');
		} else {
			throw new SyntaxError();
		}
	}
	
	private void structuredStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.IF || token.getTag() == Tag.IFELSE) {
			conditionalStatement();
		} else if (token.getTag() == Tag.REPEAT) {
			repeatStatement();
		} else {
			throw new SyntaxError();
		}
	}
	
	
	private void moreElements() throws SyntaxError, IOException {
		if (token.getTag() == ((int) ',')) {
			element();
			moreElements();
		} else {
			//do nothing
		}
	}
	
	private void element() throws SyntaxError, IOException {
		if (token.getTag() == Tag.STRING) {
			check(Tag.STRING);
		} else if (booleanExpressionFirst.contains(token.getTag())) {
			booleanExpression();
		} else if (arithmeticExpressionFirst.contains(token.getTag())) {
			arithmeticExpression();
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void textStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.PRINT) {
			check(Tag.PRINT);
			check((int) '[');
			element();
			moreElements();
			check((int) ']');
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void penWidthStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.PENWIDTH) {
			check(Tag.PENWIDTH);
			arithmeticExpression();
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void colorStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.COLOR) {
			check(Tag.COLOR);
			arithmeticExpression();
			arithmeticExpression();
			arithmeticExpression();
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void penDownStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.PENDOWN) {
			check(Tag.PENDOWN);
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void penUpStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.PENUP) {
			check(Tag.PENUP);
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void arcStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.ARC) {
			check(Tag.ARC);
			arithmeticExpression();
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void circleStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.CIRCLE) {
			check(Tag.CIRCLE);
			arithmeticExpression();
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void clearStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.CLEAR) {
			check(Tag.CLEAR);
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void drawingStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.CLEAR) {
			clearStatement();
		} else if (token.getTag() == Tag.CIRCLE) {
			circleStatement();
		} else if (token.getTag() == Tag.ARC) {
			arcStatement();
		} else if (token.getTag() == Tag.PENUP) {
			penUpStatement();
		} else if (token.getTag() == Tag.PENDOWN) {
			penDownStatement();
		} else if (token.getTag() == Tag.COLOR) {
			colorStatement();
		} else if (token.getTag() == Tag.PENWIDTH) {
			penWidthStatement();
		} else {
			throw new SyntaxError();
		} 
	}
}
