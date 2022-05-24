package translator;

import errors.SemanticError;
import symbols.Environment;

public class Identifier extends ArithmeticNode {
	private String name;
	
	public Identifier(String name) {
		this.name = name;
	}

	@Override
	public double eval(Environment env) throws SemanticError {
		Double result = env.lookup(name);
		
		if (result != null) {
			return result.doubleValue();
		} else {
			throw new SemanticError("The variable has not been defined");
		}
	}

}
