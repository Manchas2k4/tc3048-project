import java.io.FileInputStream;
import java.io.IOException;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

public class MainLexer {
	public static void main(String args[]) throws IOException {
		if (args.length != 1) {
			System.out.println("usage: java MainLexer file");
			System.exit(0);
		}
		
		FileInputStream input = new FileInputStream(args[0]); 
		Lexer lex = new Lexer(input);
		Token token = lex.scan();
		while (token.getTag() != Tag.EOF) {
			System.out.println("" + token.toString());
			token = lex.scan();
		}
		
		System.out.println("END");
	}
}
