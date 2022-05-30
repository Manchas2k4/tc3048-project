package translator;

import errors.SemanticError;
import lexer.Tag;
import symbols.Environment;
import view.BugPanel;

public class BugNode extends VoidNode {
	private BugPanel bugPanel;
	private int operation;
	private Node arg1, arg2, arg3;
	
	public BugNode(BugPanel bugPanel, int operation) {
		this.bugPanel = bugPanel;
		this.operation = operation;
		this.arg1 = null;
		this.arg2 = null;
		this.arg3 = null;
	}
	
	public BugNode(BugPanel bugPanel, int operation, Node arg1) {
		this.bugPanel = bugPanel;
		this.operation = operation;
		this.arg1 = arg1;
		this.arg2 = null;
		this.arg3 = null;
	}
	
	public BugNode(BugPanel bugPanel, int operation, Node arg1, Node arg2) {
		this.bugPanel = bugPanel;
		this.operation = operation;
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = null;
	}
	
	public BugNode(BugPanel bugPanel, int operation, Node arg1, Node arg2, Node arg3) {
		this.bugPanel = bugPanel;
		this.operation = operation;
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.arg3 = arg3;
	}
	
	@Override
	public void eval(Environment env) throws SemanticError {
		if (arg1 != null && !(arg1 instanceof ArithmeticNode)) {
			throw new SemanticError();
		}
		
		if (arg2 != null && !(arg2 instanceof ArithmeticNode)) {
			throw new SemanticError();
		}
		
		if (arg3 != null && !(arg3 instanceof ArithmeticNode)) {
			throw new SemanticError();
		}
		
		if (operation == Tag.FORWARD) {
			bugPanel.move((int) ((ArithmeticNode) arg1).eval(env));
		} else if (operation == Tag.BACKWARD) {
			bugPanel.move((int) (-1 * ((ArithmeticNode) arg1).eval(env)));
		} else if (operation == Tag.LEFT) {
			bugPanel.left((int) ((ArithmeticNode) arg1).eval(env));
		} else if (operation == Tag.RIGHT) {
			bugPanel.right((int) ((ArithmeticNode) arg1).eval(env));
		} else if (operation == Tag.SETX) {
			bugPanel.set((int) ((ArithmeticNode) arg1).eval(env), 0);
		} else if (operation == Tag.SETY) {
			bugPanel.set(0, (int) ((ArithmeticNode) arg1).eval(env));
		} else if (operation == Tag.SETXY) {
			bugPanel.set((int) ((ArithmeticNode) arg1).eval(env), (int) ((ArithmeticNode) arg2).eval(env));
		} else if (operation == Tag.HOME) {
			bugPanel.init();
		} else if (operation == Tag.CLEAR) {
			bugPanel.clear();
		} else if (operation == Tag.CIRCLE) {
			bugPanel.circle((int) ((ArithmeticNode) arg1).eval(env));
		} else if (operation == Tag.ARC) {
			bugPanel.arc((int) ((ArithmeticNode) arg1).eval(env), (int) ((ArithmeticNode) arg2).eval(env));
		} else if (operation == Tag.PENUP) {
			bugPanel.penUp();
		} else if (operation == Tag.PENDOWN) {
			bugPanel.penDown();
		} else if (operation == Tag.COLOR) {
			bugPanel.changeColor((int) ((ArithmeticNode) arg1).eval(env), (int) ((ArithmeticNode) arg2).eval(env), (int) ((ArithmeticNode) arg3).eval(env));
		} else if (operation == Tag.PENWIDTH) {
			bugPanel.penDown();
		}
	}

}
