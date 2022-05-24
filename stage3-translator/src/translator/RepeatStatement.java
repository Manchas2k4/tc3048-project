package translator;

import errors.SemanticError;
import symbols.Environment;

public class RepeatStatement extends VoidNode{
	private ArithmeticNode expression;
	private StatementSequence statementSequence;
	
	public RepeatStatement(ArithmeticNode expression, StatementSequence statementSequence) {
		this.expression = expression;
		this.statementSequence = statementSequence;
	}

	@Override
	public void eval(Environment env) throws SemanticError {
		int limit = (int) expression.eval(env);
		
		for (int i = 0; i < limit; i++) {
			statementSequence.eval(env);
		}
		
	}

}
