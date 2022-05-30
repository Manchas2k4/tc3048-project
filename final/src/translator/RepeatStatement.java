package translator;

import errors.SemanticError;
import symbols.Environment;

public class RepeatStatement extends VoidNode{
	private Node expression;
	private Node statementSequence;
	
	public RepeatStatement(Node expression, Node statementSequence) {
		this.expression = expression;
		this.statementSequence = statementSequence;
	}

	@Override
	public void eval(Environment env) throws SemanticError {
		if (!(expression instanceof ArithmeticNode)) {
			throw new SemanticError();
		}
		
		if (!(statementSequence instanceof VoidNode)) {
			throw new SemanticError();
		}
		
		int limit = (int) ((ArithmeticNode) expression).eval(env);
		
		for (int i = 0; i < limit; i++) {
			((VoidNode) statementSequence).eval(env);
		}
		
	}

}
