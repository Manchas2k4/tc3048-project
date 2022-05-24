package translator;

import errors.SemanticError;
import lexer.Tag;
import symbols.Environment;

public class BooleanOperator extends BooleanNode {
	private int operator;
	private BooleanNode left, right;
	
	public BooleanOperator(int operator, BooleanNode left, BooleanNode right) {
		this.operator = operator;
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean eval(Environment env) throws SemanticError {
		boolean le = left.eval(env);
		boolean ri = right.eval(env);
		
		if (operator == Tag.OR) {
			return le || ri;
		} else { // if (operator == Tag.AND) 
			return le && ri;
		}
	}
}
