package translator;

import errors.SemanticError;
import lexer.Tag;
import symbols.Environment;

public class ArithmeticOperator extends ArithmeticNode {
	private int operator;
	private ArithmeticNode left, right;
	
	public ArithmeticOperator(int operator, ArithmeticNode left, ArithmeticNode right) {
		this.operator = operator;
		this.left = left;
		this.right = right;
	}
	
	@Override
	public double eval(Environment env) throws SemanticError {
		double le = left.eval(env);
		double ri = right.eval(env);
		
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
