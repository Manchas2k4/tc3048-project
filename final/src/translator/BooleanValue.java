package translator;

import errors.SemanticError;
import symbols.Environment;

public class BooleanValue extends BooleanNode {
	private boolean value;
	
	public BooleanValue(boolean value) {
		this.value = value;
	}

	@Override
	public boolean eval(Environment env) throws SemanticError {
		return value;
	}

}
