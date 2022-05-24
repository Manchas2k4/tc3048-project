package translator;

import symbols.Environment;
import errors.SemanticError;

public abstract class VoidNode extends Node {
	public abstract void eval(Environment env) throws SemanticError;
}
