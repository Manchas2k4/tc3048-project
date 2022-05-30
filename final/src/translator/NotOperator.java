package translator;

import errors.SemanticError;
import symbols.Environment;

public class NotOperator extends BooleanNode {
	private Node right;
	
	public NotOperator(Node right) {
		this.right = right;
	}

	@Override
	public boolean eval(Environment env) throws SemanticError {
		if (!(right instanceof BooleanNode)) {
			throw new SemanticError();
		}
		
		return !(((BooleanNode) right).eval(env));
	}

}
