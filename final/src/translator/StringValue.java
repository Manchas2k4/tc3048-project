package translator;

import errors.SemanticError;
import symbols.Environment;

public class StringValue extends StringNode {
	private String text;
	
	public StringValue(String text) {
		this.text = text;
	}

	public String eval(Environment env) throws SemanticError {
		return text;
	}

}
