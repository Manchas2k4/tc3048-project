package translator;

import errors.SemanticError;
import symbols.Environment;

public class MoreElements extends StringNode {
	private Node element;
	private Node moreElements;
	
	public MoreElements(Node element, Node moreElements) {
		this.element = element;
		this.moreElements = moreElements;
	}

	@Override
	public String eval(Environment env) throws SemanticError {
		String result = "";
		
		if (!(element instanceof StringNode)) {
			throw new SemanticError();
		}
		
		if (!(moreElements instanceof StringNode)) {
			throw new SemanticError();
		}
		
		result = ((StringNode) element).eval(env);
		if (moreElements != null) {
			result =  " " + ((StringNode) moreElements).eval(env);
		}
		return result;
	}

}
