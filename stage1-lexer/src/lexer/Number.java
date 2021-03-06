package lexer;

public class Number extends Token {
	private double value;

	public Number(int value) {
		super(Tag.NUMBER);
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public String toString() {
		return "NUMBER value: "  + value;
	}
}
