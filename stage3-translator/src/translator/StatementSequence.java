package translator;

import errors.SemanticError;
import symbols.Environment;

public class StatementSequence extends VoidNode{
	private VoidNode statement, statementSequence;
	
	public StatementSequence(VoidNode statement, VoidNode statementSequence) {
		this.statement = statement;
		this.statementSequence = statementSequence;
	}

	@Override
	public void eval(Environment env) throws SemanticError {
		statement.eval(env);
		if (statementSequence != null) {
			statementSequence.eval(env);
		}
	}
}
