package translator;

import errors.SemanticError;
import symbols.Environment;

public class IfStatement extends VoidNode {
	private BooleanNode expression;
	private StatementSequence statementSequence;
	
	public IfStatement(BooleanNode expression, StatementSequence statementSequence) {
		this.expression = expression;
		this.statementSequence = statementSequence;
	}

	@Override
	public void eval(Environment env) throws SemanticError {
		if (expression.eval(env)) {
			statementSequence.eval(env);
		}
	}

}
