package translator;

import errors.SemanticError;
import symbols.Environment;

public abstract class ArithmeticNode extends Node {
	public abstract double eval(Environment env) throws SemanticError;
}
