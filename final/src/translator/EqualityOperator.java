package translator;

import errors.SemanticError;
import symbols.Environment;

public class EqualityOperator extends BooleanNode {
	private int operator;
	private Node left, right;
	
	public EqualityOperator(int operator, Node left, Node right) {
		this.operator = operator;
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean eval(Environment env) throws SemanticError {
		if (left instanceof ArithmeticNode && right instanceof ArithmeticNode) {
			double le, ri;
			le = ((ArithmeticNode) left).eval(env);
			ri = ((ArithmeticNode) right).eval(env);
			
			if (operator == ((int) '=')) {
				return le == ri;
			} else { // if (operator == Tag.NEQ) {
				return le != ri;
			} 
		} else if (left instanceof BooleanNode && right instanceof BooleanNode) {
			boolean le, ri;
			le = ((BooleanNode) left).eval(env);
			ri = ((BooleanNode) right).eval(env);
			
			if (operator == ((int) '=')) {
				return le == ri;
			} else { // if (operator == Tag.NEQ) {
				return le != ri;
			} 
			
		} else {
			throw new SemanticError();
		}
	}

}
