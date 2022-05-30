package translator;

import errors.SemanticError;
import symbols.Environment;

public class BooleanIdentifier extends BooleanNode {
	private String name;
	
	public BooleanIdentifier(String name) {
		this.name = name;
	}

	@Override
	public boolean eval(Environment env) throws SemanticError {
		Object result = env.lookup(name);
		
		if (result != null) {
			return ((Boolean) result).booleanValue();
		} else {
			throw new SemanticError("The variable has not been defined");
		}
	}

}
