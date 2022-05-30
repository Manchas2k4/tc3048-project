package translator;

import errors.SemanticError;
import lexer.Tag;
import symbols.Environment;

public class RelationalOperator extends BooleanNode {
	private int operator;
	private Node left, right;
	
	public RelationalOperator(int operator, Node left, Node right) {
		this.operator = operator;
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean eval(Environment env) throws SemanticError {
		double le, ri;
		
		if (!(left instanceof ArithmeticNode)) {
			throw new SemanticError();
		}
		
		if (!(right instanceof ArithmeticNode)) {
			throw new SemanticError();
		}
		
		le = ((ArithmeticNode) left).eval(env);
		ri = ((ArithmeticNode) right).eval(env);
		
		if (operator == ((int) '<')) {
			return le < ri;
		} else if (operator == Tag.LEQ) {
			return le <= ri;
		} else if (operator == ((int) '>')) {
			return le > ri;
		} else if (operator == Tag.GEQ) {
			return le >= ri;
		} else
		
		return false;
	}

}
