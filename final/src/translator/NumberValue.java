package translator;

import symbols.Environment;
import errors.SemanticError;

public class NumberValue extends ArithmeticNode {
	private double value;
	
	public NumberValue(double value) {
		this.value = value;
	}

	@Override
	public double eval(Environment env) throws SemanticError {
		return value;
	}

}
