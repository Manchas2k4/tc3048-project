package translator;

import errors.SemanticError;
import symbols.Environment;

public class PrintStatement extends VoidNode {
	private Node element, moreElements;
	
	public PrintStatement(Node element, Node moreElements) {
		this.element = element;
		this.moreElements = moreElements;
	}

	@Override
	public void eval(Environment env) throws SemanticError {
		if (!(element instanceof StringNode)) {
			throw new SemanticError();
		}
		
		if (!(moreElements instanceof StringNode)) {
			throw new SemanticError();
		}
		
		// DISPLAY IN TEXTAREA
	}

}
