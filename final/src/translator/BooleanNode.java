package translator;

import errors.SemanticError;
import symbols.Environment;

public abstract class BooleanNode extends Node {
	public abstract boolean eval(Environment env) throws SemanticError;
}
