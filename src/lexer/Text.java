package lexer;

public class Text extends Token {
	private String string;
	
	public Text(String string) {
		super(Tag.STRING);
		this.string = string;
	}
	
	public String getString() {
		return string;
	}
	
	public String toString() {
		return super.toString() + " <-> string = " + string;
	}
}
