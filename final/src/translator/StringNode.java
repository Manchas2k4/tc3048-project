package translator;

import errors.SemanticError;
import symbols.Environment;

public abstract class StringNode extends Node {
	public abstract String eval(Environment env) throws SemanticError;
}
