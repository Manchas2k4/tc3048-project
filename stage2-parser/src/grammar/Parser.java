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
	private LinkedHashSet<Tag> firstPrimaryExpression = new LinkedHashSet<Tag>();
	private LinkedHashSet<Tag> firstUnaryExpression = new LinkedHashSet<Tag>();
	
	public Parser (FileInputStream input) {
		lexer = new Lexer(input);
		
		firstPrimaryExpression.add(Tag.ID); firstPrimaryExpression.add(Tag.NUMBER);
		firstPrimaryExpression.add(Tag.TRUE); firstPrimaryExpression.add(Tag.FALSE);
		firstPrimaryExpression.add((int) '(');
		
		firstUnaryExpression.add((int) '-'); firstUnaryExpression.add((int) '!');
		firstUnaryExpression.addAll(firstPrimaryExpression);
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
	}
	
	private void program() {
		statementSequence();
	}
	
	private void primaryExpression() {
		if (firstPrimaryExpression.contains(token.getTag())) {
			switch(token.getTag()) {
			case Tag.ID : check(Tag.ID) break;
			case Tag.NUMBER : check(Tag.NUMBER) break;
			case Tag.TRUE : check(Tag.TRUE) break;
			case Tag.FALSE : check(Tag.FALSE) break;
			case ((int) '(') :
				check((int) '(');
				expression();
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
}
