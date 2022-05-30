package translator;

import errors.SemanticError;
import symbols.Environment;

public class AssigmentNode extends VoidNode {
	private String name;
	private Node expression;
	
	public AssigmentNode(String name, Node expression) {
		this.name = name;
		this.expression = expression;
	}
	
	@Override
	public void eval(Environment env) throws SemanticError {
		Object result = env.lookup(name);
		if (result == null) {
			env.insert(name);
		}
		
		if (expression instanceof ArithmeticNode) {
			env.set(name, ((ArithmeticNode) expression).eval(env));
		} else if (expression instanceof ArithmeticNode) {
			env.set(name, ((BooleanNode) expression).eval(env));
		} else {
			throw new SemanticError();
		}
	}

}
