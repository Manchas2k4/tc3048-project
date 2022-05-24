package lexer;

import errors.SyntaxError;
import java.io.*;
import java.util.Hashtable;

public class Lexer {
	private char peek;
	private Hashtable<String, Token> words = new Hashtable<String, Token>();
	private InputStream input;
	public static int line = 1;

	private void addReserveWord(Word w) {
		words.put(w.getLexeme(), w);
	}

	public Lexer(InputStream input) {
		this.peek = ' ';

		this.input = input;
		addReserveWord(new Word("MAKE", Tag.MAKE));
		addReserveWord(new Word("FORWARD", Tag.FORWARD));
		addReserveWord(new Word("FD", Tag.FORWARD));
		addReserveWord(new Word("BACKWARD", Tag.BACKWARD));
		addReserveWord(new Word("BK", Tag.BACKWARD));
		addReserveWord(new Word("LEFT", Tag.LEFT));
		addReserveWord(new Word("LT", Tag.LEFT));
		addReserveWord(new Word("RIGHT", Tag.RIGHT));
		addReserveWord(new Word("RT", Tag.RIGHT));
		addReserveWord(new Word("SETX", Tag.SETX));
		addReserveWord(new Word("SETY", Tag.SETY));
		addReserveWord(new Word("SETXY", Tag.SETXY));
		addReserveWord(new Word("HOME", Tag.HOME));
		addReserveWord(new Word("CLEAR", Tag.CLEAR));
		addReserveWord(new Word("CLS", Tag.CLEAR));
		addReserveWord(new Word("CIRCLE", Tag.CIRCLE));
		addReserveWord(new Word("ARC", Tag.ARC));
		addReserveWord(new Word("PENUP", Tag.PENUP));
		addReserveWord(new Word("PU", Tag.PENUP));
		addReserveWord(new Word("PENDOWN", Tag.PENDOWN));
		addReserveWord(new Word("PD", Tag.PENDOWN));
		addReserveWord(new Word("COLOR", Tag.COLOR));
		addReserveWord(new Word("PENWIDTH", Tag.PENWIDTH));
		addReserveWord(new Word("PRINT", Tag.PRINT));
		addReserveWord(new Word("REPEAT", Tag.REPEAT));
		addReserveWord(new Word("IF", Tag.IF));
		addReserveWord(new Word("IFELSE", Tag.IFELSE));
		addReserveWord(new Word("NOT", Tag.NOT));
		addReserveWord(new Word("MOD", Tag.MOD));
		addReserveWord(new Word("AND", Tag.AND));
		addReserveWord(new Word("OR", Tag.OR));
	}

	private void readch() throws IOException {
		peek = (char) input.read();
	}

	private boolean readch(char c) throws IOException {
		readch();
		if (peek != c)
			return false;

		peek = ' ';
		return true;
	}

	private void skipSpaces() throws IOException {
		for ( ; ; readch() ) {
			if (peek == ' ' || peek == '\t' || peek == '\r') {
				continue;
			} else if (peek == '\n') {
				line = line + 1;
			} else {
				break;
			}
		}
	}

	public Token scan() throws IOException, SyntaxError {
		skipSpaces();

		if (peek == '%') {
			for ( ; ; readch() ) {
				if (peek == '\n') {
					line = line + 1;
					break;
				}
			}
			skipSpaces();
		}

		switch(peek) {
			case '<':
				if (readch('=')) return Word.Leq;
				else if (readch('>')) return Word.Neq;
				else return new Token('<');
			case '>':
				if (readch('=')) return Word.Geq;
				else return new Token('>');
			case '#':
				if (readch('t')) return Word.True;
				else if (readch('f')) return Word.False;
				else return new Token('#');
		}

		if (peek == '"') {
			String val = "";
			do {
				val = val + peek;
				readch();
			} while ( peek != '"' );
			val = val + peek;
			readch();
			return new Text(val);
		}

		if (Character.isDigit(peek)) {
			double number = 0;
			do {
				number = (10 * number) + Character.digit(peek, 10);
				readch();
			} while ( Character.isDigit(peek) );

			if (peek == '.') {
				readch();
				if (!Character.isDigit(peek)) {
					throw new SyntaxError("NUMERIC");
				}

				double divisor = 10.0;
				do {
					number = number + (Character.digit(peek, 10) / divisor);
					divisor = divisor * 10.0;
					readch();
				} while ( Character.isDigit(peek) );
			}
			return new Number(number);
		}

		if (Character.isLetter(peek)) {
			StringBuffer b = new StringBuffer();
			do {
				b.append(Character.toUpperCase(peek));
				readch();
			} while ( Character.isLetterOrDigit(peek) ) ;
			String s = b.toString();
			Word w = (Word) words.get(s);
			if (w != null)
				return w;

			w = new Word(s, Tag.ID);
			words.put(s, w);
			return w;
		}

		Token tok = new Token(peek); peek = ' ';
		return tok;
	}
}
