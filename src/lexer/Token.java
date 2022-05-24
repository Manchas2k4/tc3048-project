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
		case Tag.FORWARD	: return "value: FORWARD";
		case Tag.BACKWARD	: return "value: BACKWARD";
		case Tag.LEFT	: return "value: LEFT";
		case Tag.RIGHT	: return "value: RIGHT";
		case Tag.SETX	: return "value: SETX";
		case Tag.SETY	: return "value: SETY";
		case Tag.SETXY	: return "value: SETXY";
		case Tag.CLEAR	: return "value: CLEAR";
		case Tag.CIRCLE	: return "value: CIRCLE";
		case Tag.ARC	: return "value: ARC";
		case Tag.PENUP	: return "value: PENUP";
		case Tag.PENDOWN	: return "value: PENDOWN";
		case Tag.COLOR	: return "value: COLOR";
		case Tag.PENWIDTH	: return "value: PENWIDTH";
		case Tag.PRINT	: return "value: PRINT";
		case Tag.REPEAT	: return "value: REPEAT";
		case Tag.IF		: return "value: IF";
		case Tag.IFELSE	: return "value: ELSE";
		case Tag.NOT	: return "value: NOT";
		case Tag.MOD	: return "value: MOD";
		case Tag.AND	: return "value: AND";
		case Tag.OR	: return "value: OR";
		case Tag.EOF	: return "value: EOF";
		default			: return "value: " + (char) tag;
		}
	}
}
