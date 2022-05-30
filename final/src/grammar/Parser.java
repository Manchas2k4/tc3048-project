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
	private static LinkedHashSet<Integer> primaryExpressionFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> unaryExpressionFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> multiplicativeExpressionFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> additiveExpressionFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> relationalExpressionFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> equalityExpressionFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> conditionalTermFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> conditionalExpressionFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> expressionFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> drawingStatementFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> movementStatementFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> simpleStatementFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> structuredStatementFirst = new LinkedHashSet<Integer>();
	private static LinkedHashSet<Integer> statementFirst = new LinkedHashSet<Integer>();

	public Parser (FileInputStream input) {
		lexer = new Lexer(input);
		
		primaryExpressionFirst.add(Tag.ID); primaryExpressionFirst.add(Tag.NUMBER);
		primaryExpressionFirst.add(Tag.TRUE); primaryExpressionFirst.add(Tag.FALSE);
		primaryExpressionFirst.add((int) '('); 
		
		unaryExpressionFirst.add((int) '-'); unaryExpressionFirst.add(Tag.NOT);
		unaryExpressionFirst.addAll(primaryExpressionFirst);
		
		multiplicativeExpressionFirst.addAll(unaryExpressionFirst);
		
		additiveExpressionFirst.addAll(multiplicativeExpressionFirst);
		
		relationalExpressionFirst.addAll(additiveExpressionFirst);
		
		equalityExpressionFirst.addAll(relationalExpressionFirst);
		
		conditionalTermFirst.addAll(equalityExpressionFirst);
		
		conditionalExpressionFirst.addAll(conditionalTermFirst);
		
		expressionFirst.addAll(conditionalExpressionFirst);
		
		drawingStatementFirst.add(Tag.CLEAR); drawingStatementFirst.add(Tag.CIRCLE);
		drawingStatementFirst.add(Tag.ARC); drawingStatementFirst.add(Tag.PENUP);
		drawingStatementFirst.add(Tag.PENDOWN); drawingStatementFirst.add(Tag.COLOR);
		drawingStatementFirst.add(Tag.PENWIDTH);
		
		movementStatementFirst.add(Tag.FORWARD); movementStatementFirst.add(Tag.BACKWARD);
		movementStatementFirst.add(Tag.LEFT); movementStatementFirst.add(Tag.RIGHT);
		movementStatementFirst.add(Tag.SETX); movementStatementFirst.add(Tag.SETY);
		movementStatementFirst.add(Tag.SETXY); movementStatementFirst.add(Tag.HOME);
		
		simpleStatementFirst.add(Tag.MAKE); simpleStatementFirst.addAll(movementStatementFirst);
		simpleStatementFirst.addAll(drawingStatementFirst); simpleStatementFirst.add(Tag.PRINT);
		
		structuredStatementFirst.add(Tag.IF); structuredStatementFirst.add(Tag.IFELSE);
		structuredStatementFirst.add(Tag.REPEAT);
		
		statementFirst.addAll(simpleStatementFirst); statementFirst.addAll(structuredStatementFirst);
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
		program();
		if (token.getTag() == Tag.EOF) {
			System.out.println("ACCEPTED");
		} else {
			throw new SyntaxError();
		}
	}
	
	private void primaryExpression() throws SyntaxError, IOException {
		if (token.getTag() == Tag.ID) {
			check(Tag.ID);
		} else if (token.getTag() == Tag.NUMBER) {
			check(Tag.NUMBER);
		} else if (token.getTag() == Tag.TRUE) {
			check(Tag.TRUE);
		} else if (token.getTag() == Tag.FALSE) {
			check(Tag.FALSE);
		} else if (token.getTag() == ((int) '(')) {
			check((int) '(');
			expression();
			check((int) ')');
		} else {
			throw new SyntaxError();
		}
	}
	
	private void unaryExpression() throws SyntaxError, IOException {
		if (token.getTag() == ((int) '-')) {
			check((int) '-');
			unaryExpression();
		} else if (token.getTag() == Tag.NOT) {
			check(Tag.NOT);
			unaryExpression();
		} else if (primaryExpressionFirst.contains(token.getTag())) {
			primaryExpression();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void extendedMultiplicativeExpression() throws SyntaxError, IOException {
		if (token.getTag() == ((int) '*')) {
			check((int) '*');
			unaryExpression();
			extendedMultiplicativeExpression();
		} else if (token.getTag() == ((int) '/')) {
			check((int) '/');
			unaryExpression();
			extendedMultiplicativeExpression();
		} else if (token.getTag() == Tag.MOD) {
			check(Tag.MOD);
			unaryExpression();
			extendedMultiplicativeExpression();
		} else {
			// do nothing
		}
	}
	
	private void multiplicativeExpression() throws SyntaxError, IOException {
		if (unaryExpressionFirst.contains(token.getTag())) {
			unaryExpression();
			extendedMultiplicativeExpression();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void extendedAdditiveExpression() throws SyntaxError, IOException {
		if (token.getTag() == ((int) '+')) {
			check((int) '+');
			multiplicativeExpression();
			extendedAdditiveExpression();
		} else if (token.getTag() == ((int) '-')) {
			check((int) '-');
			multiplicativeExpression();
			extendedAdditiveExpression();
		} else {
			// do nothing
		}
	}
	
	private void additiveExpression() throws SyntaxError, IOException {
		if (multiplicativeExpressionFirst.contains(token.getTag())) {
			multiplicativeExpression();
			extendedAdditiveExpression();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void extendedRelationalExpression() throws SyntaxError, IOException {
		if (token.getTag() == ((int) '<')) {
			check((int) '<');
			additiveExpression();
			extendedRelationalExpression();
		} else if (token.getTag() == Tag.LEQ) {
			check(Tag.LEQ);
			additiveExpression();
			extendedRelationalExpression();
		} else if (token.getTag() == ((int) '>')) {
			check((int) '>');
			additiveExpression();
			extendedRelationalExpression();
		} else if (token.getTag() == Tag.GEQ) {
			check(Tag.GEQ);
			additiveExpression();
			extendedRelationalExpression();
		} else {
			// do nothing
		}
	}
	
	private void relationalExpression() throws SyntaxError, IOException {
		if (additiveExpressionFirst.contains(token.getTag())) {
			additiveExpression();
			extendedRelationalExpression();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void extendedEqualityExpression() throws SyntaxError, IOException {
		if (token.getTag() == ((int) '=')) {
			check((int) '=');
			relationalExpression();
			extendedEqualityExpression();
		} else if (token.getTag() == Tag.NEQ) {
			check(Tag.NEQ);
			relationalExpression();
			extendedEqualityExpression();
		} else {
			// do nothing
		}
	}
	
	private void equalityExpression() throws SyntaxError, IOException {
		if (relationalExpressionFirst.contains(token.getTag())) {
			relationalExpression();
			extendedEqualityExpression();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void extendedConditionalTerm() throws SyntaxError, IOException {
		if (token.getTag() == Tag.AND) {
			check(Tag.AND);
			equalityExpression();
			extendedConditionalTerm();
		} else {
			// do nothing
		}
	}
	
	private void conditionalTerm() throws SyntaxError, IOException {
		if (equalityExpressionFirst.contains(token.getTag())) {
			equalityExpression();
			extendedConditionalTerm();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void extendedConditionalExpression() throws SyntaxError, IOException {
		if (token.getTag() == Tag.OR) {
			check(Tag.OR);
			conditionalTerm();
			extendedConditionalExpression();
		} else {
			// do nothing
		}
	}
	
	private void conditionalExpression() throws SyntaxError, IOException {
		if (conditionalTermFirst.contains(token.getTag())) {
			conditionalTerm();
			extendedConditionalExpression();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void expression() throws SyntaxError, IOException {
		if (conditionalExpressionFirst.contains(token.getTag())) {
			conditionalExpression(); 
		} else {
			throw new SyntaxError();
		}
	}
	
	private void ifElseStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.IFELSE) {
			check(Tag.IFELSE);
			expression();
			check((int) '[');
			statementSequence();
			check((int) ']');
			check((int) '[');
			statementSequence();
			check((int) ']');
		} else {
			throw new SyntaxError();
		}
	}
	
	private void ifStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.IF) {
			check(Tag.IF);
			expression();
			check((int) '[');
			statementSequence();
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
			expression();
			check((int) '[');
			statementSequence();
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
			check((int) ',');
			element();
			moreElements();
		} else {
			//do nothing
		}
	}
	
	private void element() throws SyntaxError, IOException {
		if (token.getTag() == Tag.STRING) {
			check(Tag.STRING);
		} else if (expressionFirst.contains(token.getTag())) {
			expression();
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
			expression();
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void colorStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.COLOR) {
			check(Tag.COLOR);
			expression();
			expression();
			expression();
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
			expression();
			expression();
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void circleStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.CIRCLE) {
			check(Tag.CIRCLE);
			expression();
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
	
	private void homeStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.HOME) {
			check(Tag.HOME);
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void setXYStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.SETXY) {
			check(Tag.SETXY);
			expression();
			expression();
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void setYStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.SETY) {
			check(Tag.SETY);
			expression();
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void setXStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.SETX) {
			check(Tag.SETX);
			expression();
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void rightStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.RIGHT) {
			check(Tag.RIGHT);
			expression();
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void leftStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.LEFT) {
			check(Tag.LEFT);
			expression();
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void backwardStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.BACKWARD) {
			check(Tag.BACKWARD);
			expression();
		} else {
			throw new SyntaxError();
		} 
	}
	
	private void forwardStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.FORWARD) {
			check(Tag.FORWARD);
			expression();
		} else {
			throw new SyntaxError();
		}		
	}
	
	private void movementStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.FORWARD) {
			forwardStatement();
		} else if (token.getTag() == Tag.BACKWARD) {
			backwardStatement();
		} else if (token.getTag() == Tag.LEFT) {
			leftStatement();
		} else if (token.getTag() == Tag.RIGHT) {
			rightStatement();
		} else if (token.getTag() == Tag.SETX) {
			setXStatement();
		} else if (token.getTag() == Tag.SETY) {
			setYStatement();
		} else if (token.getTag() == Tag.SETXY) {
			setXYStatement();
		} else if (token.getTag() == Tag.HOME) {
			homeStatement();
		} else {
			throw new SyntaxError();
		}		
	}
	
	private void assigmentStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.MAKE) {
			check(Tag.MAKE);
			check(Tag.ID);
			expression();
		} else {
			throw new SyntaxError();
		}		
	}
	
	private void simpleStatement() throws SyntaxError, IOException {
		if (token.getTag() == Tag.MAKE) {
			assigmentStatement();
		} else if (movementStatementFirst.contains(token.getTag())) {
			movementStatement();
		} else if (drawingStatementFirst.contains(token.getTag())) {
			drawingStatement();
		} else if (token.getTag() == Tag.PRINT) {
			textStatement();
		} else {
			throw new SyntaxError();
		}
	}
	
	
	private void statement() throws SyntaxError, IOException {
		if (simpleStatementFirst.contains(token.getTag())) {
			simpleStatement();
		} else if (structuredStatementFirst.contains(token.getTag())) {
			structuredStatement();
		} else {
			throw new SyntaxError();
		}
	}
	
	private void statementSequence() throws SyntaxError, IOException {
		if (statementFirst.contains(token.getTag())) {
			statement();
			statementSequence();
		} else {
			// do nothing
		}
	}
	
	private void program() throws SyntaxError, IOException {
		if (statementFirst.contains(token.getTag())) {
			statementSequence();
		} else {
			throw new SyntaxError();
		}
	}
}
