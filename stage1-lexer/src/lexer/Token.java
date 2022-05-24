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
		case Tag.GEQ	: return "value: >=";
		case Tag.LEQ	: return "value: <=";
		case Tag.NEQ	: return "value: <>";
		case Tag.TRUE	: return "value: TRUE";
		case Tag.FALSE	: return "value: FALSE";
		case Tag.MAKE	: return "value: MAKE";
		case Tag.EOF	: return "value: EOF";
		default			: return "value: " + (char) tag;
		}
	}
}
