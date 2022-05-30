package errors;

public class SemanticError extends Exception {
	private static final long serialVersionUID = 1L;
	
	public SemanticError() {
		super();
	}
	
	public SemanticError(String s) {
		super(s);
	}
}
