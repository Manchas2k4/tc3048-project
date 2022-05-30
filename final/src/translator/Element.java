package translator;

import errors.SemanticError;
import symbols.Environment;

public class Element extends StringNode {
	private Node content;
	
	public Element(Node content) {
		this.content = content;
	}

	@Override
	public String eval(Environment env) throws SemanticError {
		if (content instanceof StringNode) {
			return ((StringValue) content).eval(env);
		} else if (content instanceof ArithmeticNode) {
			return "" + ((ArithmeticNode) content).eval(env);
		} else if (content instanceof BooleanNode) {
			return "" + ((BooleanNode) content).eval(env);
		} else {
			throw new SemanticError();
		}
	}

}
