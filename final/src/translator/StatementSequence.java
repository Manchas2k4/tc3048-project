package translator;

import errors.SemanticError;
import symbols.Environment;

public class StatementSequence extends VoidNode{
	private Node statement, statementSequence;
	
	public StatementSequence(VoidNode statement, VoidNode statementSequence) {
		this.statement = statement;
		this.statementSequence = statementSequence;
	}

	@Override
	public void eval(Environment env) throws SemanticError {
		if (!(statement instanceof VoidNode)) {
			throw new SemanticError();
		}
		
		if (!(statementSequence instanceof VoidNode)) {
			throw new SemanticError();
		}
		
		
		((VoidNode) statement).eval(env);
		if (statementSequence != null) {
			((VoidNode) statementSequence).eval(env);
		}
	}
}
