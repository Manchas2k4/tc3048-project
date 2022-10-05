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
	private LinkedHashSet<Integer> firstPrimaryExpression = new LinkedHashSet<Integer>();
	private LinkedHashSet<Integer> firstUnaryExpression = new LinkedHashSet<Integer>();
	private LinkedHashSet<Integer> firstExtendedMultiplicativeExpression = new LinkedHashSet<Integer>();
	private LinkedHashSet<Integer> firstMultiplicativeExpression = new LinkedHashSet<Integer>();
	private LinkedHashSet<Integer> firstExtendedAdditiveExpression = new LinkedHashSet<Integer>();
	private LinkedHashSet<Integer> firstAdditiveExpression = new LinkedHashSet<Integer>();
	
	public Parser (FileInputStream input) {
		lexer = new Lexer(input);
		
		firstPrimaryExpression.add(Tag.ID); firstPrimaryExpression.add(Tag.NUMBER);
		firstPrimaryExpression.add(Tag.TRUE); firstPrimaryExpression.add(Tag.FALSE);
		firstPrimaryExpression.add((int) '(');
		
		firstUnaryExpression.add((int) '-'); firstUnaryExpression.add((int) '!');
		firstUnaryExpression.addAll(firstPrimaryExpression);
		
		firstExtendedMultiplicativeExpression.add((int) '*'); firstExtendedMultiplicativeExpression.add((int) '/');
		firstExtendedMultiplicativeExpression.add(Tag.MOD);
		
		firstMultiplicativeExpression.containsAll(firstUnaryExpression);
		
		firstExtendedAdditiveExpression.add((int) '+'); firstExtendedAdditiveExpression.add((int) '-');
		
		firstAdditiveExpression.addAll(firstMultiplicativeExpression);
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
		//program();
	}
	
	private void primaryExpression() throws SyntaxError, IOException {
		if (firstPrimaryExpression.contains(token.getTag())) {
			switch(token.getTag()) {
			case Tag.ID : check(Tag.ID); break;
			case Tag.NUMBER : check(Tag.NUMBER); break;
			case Tag.TRUE : check(Tag.TRUE); break;
			case Tag.FALSE : check(Tag.FALSE); break;
			case ((int) '(') :
				check((int) '(');
				//expression();
				check((int) ')');
				break;
			}
		} else {
			throw new SyntaxError();
		}
	}
	
	private void unaryExpression() throws SyntaxError, IOException {
		if (firstUnaryExpression.contains(token.getTag())) {
			switch(token.getTag()) {
			case ((int) '-') : check((int) '-'); unaryExpression(); break;
			case ((int) '!') : check((int) '!'); unaryExpression(); break;
			default			 : primaryExpression(); break;
			}
		} else {
			throw new SyntaxError();
		}
	}
	
	private void extendedMultiplicativeExpression() throws SyntaxError, IOException {
		if (firstExtendedMultiplicativeExpression.contains(token.getTag())) {
			switch(token.getTag()) {
			case ((int) '*') : 
				check((int) '*');
				unaryExpression();
				extendedMultiplicativeExpression();
				break;
			case ((int) '/') : 
				check((int) '/');
				unaryExpression();
				extendedMultiplicativeExpression();
				break;
			case Tag.MOD : 
				check(Tag.MOD);
				unaryExpression();
				extendedMultiplicativeExpression();
				break;
			}
		} else {
			// do nothing
		}
	}
	
	protected void multiplicativeExpression() throws SyntaxError, IOException {
		if (firstMultiplicativeExpression.contains(token.getTag())) {
			unaryExpression();
			extendedMultiplicativeExpression();
		}
	}
	
	protected void extendedAdditiveExpression() throws SyntaxError, IOException {
		if (firstExtendedAdditiveExpression.contains(token.getTag())) {
			switch(token.getTag()) {
			case ((int) '+') : 
				check((int) '+');
				multiplicativeExpression();
				extendedAdditiveExpression();
				break;
			case ((int) '-') : 
				check((int) '-');
				multiplicativeExpression();
				extendedAdditiveExpression();
				break;
			}
		} else {
			// do nothing
		}
	}
	
	protected void additiveExpression() throws SyntaxError, IOException {
		if (firstAdditiveExpression.contains(token.getTag())) {
			multiplicativeExpression();
			extendedAdditiveExpression();
		}
	}
}
