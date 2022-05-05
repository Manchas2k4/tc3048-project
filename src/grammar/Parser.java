package grammar;

import java.io.FileInputStream;
import java.io.IOException;

import errors.SyntaxError;
import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

public class Parser {
	private Lexer lexer;
	private Token token;

	public Parser (FileInputStream input) {
		lexer = new Lexer(input);
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
		statementSequence();
		if (token.getTag() == Tag.EOF) {
			System.out.println("ACCEPTED");
		} else {
			throw new SyntaxError();
		}
	}

	// ADD CODE TO ALL PRODUCTIONS HERE
}
