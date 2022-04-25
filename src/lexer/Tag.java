package lexer;

public class Tag {
	public final static int 
		EOF = 65535, ERROR = 65534,
		
		EQ = 257, GEQ = 258, LEQ = 259, NEQ = 260,
		
		ID = 357, NUMBER = 358, STRING = 359, TRUE = 360, FALSE = 361,
		
		MAKE = 457, PRINT = 458, WHILE = 459, IF = 460, ELSE = 461;
}
