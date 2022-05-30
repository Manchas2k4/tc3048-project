package lexer;

public class Tag {
	public final static int
		EOF = 65535, ERROR = 65534,

		/* OPERATORS */
		GEQ = 257, LEQ = 258, NEQ = 259,

		/* REGULAR EXPRESSIONS */
		ID = 357, NUMBER = 358, STRING = 359, TRUE = 360, FALSE = 361,

		/* RESERVED WORDS */
		MAKE = 457, FORWARD = 458, BACKWARD = 459, LEFT = 460, RIGHT = 461,
		SETX = 462, SETY = 463, SETXY = 464, CLEAR = 465, CIRCLE = 466,
		ARC = 467, PENUP = 468, PENDOWN = 469, COLOR = 470, PENWIDTH = 471,
		PRINT = 472, REPEAT = 473, IF = 474, IFELSE = 475, HOME = 476,
		OR = 477, MOD = 478, AND = 479, NOT = 480;

	/*
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof Tag)) {
			return false;
		}

		Tag obj = (Tag) other;
		return (this.getTag() == obj.getTag());
	}
	*/
}
