package errors;

public class SyntaxError extends Exception {
	private static final long serialVersionUID = 1L;

	public SyntaxError() {
		super();
	}
	
	public SyntaxError(String s) {
		super(s);
	}
}
