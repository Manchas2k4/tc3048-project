package translator;

import symbols.Environment;
import errors.SemanticError;

public class Number extends ArithmeticNode {
	private double value;
	
	public Number(double value) {
		this.value = value;
	}

	@Override
	public double eval(Environment env) throws SemanticError {
		return value;
	}

}
