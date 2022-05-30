package translator;

import errors.SemanticError;
import symbols.Environment;

public class NumericIdentifier extends ArithmeticNode {
	private String name;
	
	public NumericIdentifier(String name) {
		this.name = name;
	}
	
	@Override
	public double eval(Environment env) throws SemanticError {
		Object result = env.lookup(name);
		
		if (result != null) {
			return ((Double) result).doubleValue();
		} else {
			throw new SemanticError("The variable has not been defined");
		}
	}
}
