package lexer;

public class Number extends Token {
	private int value;
	
	public Number(int value) {
		super(Tag.NUMBER);
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		return super.toString() + " <-> value = "  + value;
	}
}
