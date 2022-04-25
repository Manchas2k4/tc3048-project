package lexer;

public class Word extends Token {
	private String lexeme;
	
	public Word(String lexeme, int tag) {
		super(tag);
		this.lexeme = lexeme;
	}
	
	public String getLexeme() {
		return lexeme;
	}
	
	public String toString() {
		return super.toString() + " <-> lexeme = " + lexeme;
	}
	
	public static final Word 
		Eq = new Word("==", Tag.EQ), Neq = new Word("!=", Tag.NEQ),
		Leq = new Word( "<=", Tag.LEQ), Geq = new Word(">=", Tag.GEQ),
		True = new Word("#t",  Tag.TRUE), False = new Word("#f", Tag.FALSE);
}

