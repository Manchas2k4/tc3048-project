package translator;

import errors.SemanticError;
import symbols.Environment;

public class IfStatement extends VoidNode {
	private Node expression;
	private Node ifTrue;
	
	public IfStatement(Node expression, Node ifTrue) {
		this.expression = expression;
		this.ifTrue = ifTrue;
	}

	@Override
	public void eval(Environment env) throws SemanticError {
		if (!(expression instanceof BooleanNode)) {
			throw new SemanticError();
		}
		
		if (!(ifTrue instanceof VoidNode)) {
			throw new SemanticError();
		}
		
		if (((BooleanNode) expression).eval(env)) {
			((VoidNode) ifTrue).eval(env);
		} 
	}

}