package translator;

import errors.SemanticError;
import symbols.Environment;

public class ArithmeticOperator extends ArithmeticNode {
	private int operator;
	private Node left, right;
	
	public ArithmeticOperator(int operator, Node left, Node right) {
		this.operator = operator;
		this.left = left;
		this.right = right;
	}
	
	@Override
	public double eval(Environment env) throws SemanticError {
		double le, ri;
		
		if (!(left instanceof ArithmeticNode)) {
			throw new SemanticError();
		}
		
		if (!(right instanceof ArithmeticNode)) {
			throw new SemanticError();
		}
		
		le = ((ArithmeticNode) left).eval(env);
		ri = ((ArithmeticNode) right).eval(env);
		
		if (ri == 0) {
			throw new java.lang.ArithmeticException();
		}
		
		if (operator == ((int) '+')) {
			return le + ri;
		} else if (operator == ((int) '-')) {
			return le - ri;
		} else if (operator == ((int) '*')) {
			return le * ri;
		} else if (operator == ((int) '/')) {
			return le / ri;
		} else { // if (operator == Tag.MOD) {
			return le % ri;
		}
	}

}
