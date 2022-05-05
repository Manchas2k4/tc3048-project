package lexer;

public class Tag {
	public final static int
		EOF = 65535, ERROR = 65534,

		/* OPERATORS */
		GEQ = 258, LEQ = 259, NEQ = 260,

		/* REGULAR EXPRESSIONS */
		ID = 357, NUMBER = 358, STRING = 359, TRUE = 360, FALSE = 361,

		/* RESERVED WORDS */
		MAKE = 457;
}
