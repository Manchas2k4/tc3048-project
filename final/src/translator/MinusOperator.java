package translator;

import errors.SemanticError;
import symbols.Environment;

public class MinusOperator extends ArithmeticNode {
	private Node right;
	
	public MinusOperator(Node right) {
		this.right = right;
	}

	@Override
	public double eval(Environment env) throws SemanticError {
		if (!(right instanceof ArithmeticNode)) {
			throw new SemanticError();
		}
		
		return -1 * ((ArithmeticNode) right).eval(env);
	}

}
