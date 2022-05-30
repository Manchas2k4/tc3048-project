package translator;

import errors.SemanticError;
import symbols.Environment;

public class IfElseStatement extends VoidNode {
	private Node expression;
	private Node ifTrue, ifFalse;
	
	public IfElseStatement(Node expression, Node ifTrue, Node ifFalse) {
		this.expression = expression;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
	}

	@Override
	public void eval(Environment env) throws SemanticError {
		if (!(expression instanceof BooleanNode)) {
			throw new SemanticError();
		}
		
		if (!(ifTrue instanceof VoidNode)) {
			throw new SemanticError();
		}
		
		if (!(ifTrue instanceof VoidNode)) {
			throw new SemanticError();
		}
		
		
		if (((BooleanNode) expression).eval(env)) {
			((VoidNode) ifTrue).eval(env);
		} else {
			((VoidNode) ifFalse).eval(env);
		}
	}
}
