package translator;

import errors.SemanticError;
import lexer.Tag;
import symbols.Environment;

public class BooleanOperator extends BooleanNode {
	private int operator;
	private Node left, right;
	
	public BooleanOperator(int operator, Node left, Node right) {
		this.operator = operator;
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean eval(Environment env) throws SemanticError {
		boolean le, ri;
		
		if (!(left instanceof BooleanNode)) {
			throw new SemanticError();
		}
		
		if (!(right instanceof BooleanNode)) {
			throw new SemanticError();
		}
		
		le = ((BooleanNode) left).eval(env);
		ri = ((BooleanNode) right).eval(env);
		
		if (operator == Tag.OR) {
			return le || ri;
		} else { // if (operator == Tag.AND) 
			return le && ri;
		}
	}
}
