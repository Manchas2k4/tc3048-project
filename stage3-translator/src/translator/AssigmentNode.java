package translator;

import errors.SemanticError;
import symbols.Environment;

public class AssigmentNode extends VoidNode {
	private String name;
	private ArithmeticNode expression;
	
	public AssigmentNode(String name, ArithmeticNode expression) {
		this.name = name;
		this.expression = expression;
	}
	
	@Override
	public void eval(Environment env) throws SemanticError {
		Double result = env.lookup(name);
		if (result == null) {
			env.insert(name);
		}
		env.set(name, expression.eval(env));
	}

}
