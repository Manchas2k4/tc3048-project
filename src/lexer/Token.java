package lexer;

public class Token {
	private final int tag;
	
	public Token(int tag) {
		this.tag = tag;
	}
	
	public int getTag() {
		return tag;
	}
	
	public String toString() {
		switch (tag) {
		case Tag.EQ		: return "==";
		case Tag.GEQ	: return ">=";
		case Tag.LEQ	: return "<=";
		case Tag.NEQ	: return "!="; 
		case Tag.ID		: return "ID";
		case Tag.NUMBER	: return "NUMBER";
		case Tag.STRING	: return "STRING";
		case Tag.TRUE	: return "TRUE"; 
		case Tag.FALSE	: return "FALSE";
		case Tag.VAR	: return "VAR";
		case Tag.PRINT	: return "PRINT";
		case Tag.WHILE	: return "WHILE";
		case Tag.IF		: return "IF";
		case Tag.ELSE	: return "ELSE";
		case Tag.EOF	: return "EOF";
		default			: return "TOKEN <-> " + (char) tag;
		}
	}
}
