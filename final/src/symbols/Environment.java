package symbols;

import errors.SemanticError;
import java.util.Hashtable;

public class Environment {
	private Hashtable<String, Object> table;
	private Environment previous;
	
	public Environment() {
		table = new Hashtable<String, Object>();
		previous = null;
	}
	
	public Environment(Environment prev) {
		table = new Hashtable<String, Object>();
		previous = prev;
	}
	
	public Environment getPrevious() {
		return previous;
	}
	
	public void setPrevious(Environment previous) {
		this.previous = previous;
	}
	
	public void insert(String varName) throws SemanticError{
		if (table.get(varName) == null) {
			table.put(varName, Double.valueOf(0.0));
		} else {
			throw new SemanticError("The variable was already defined");
		}
	}
	
	public Object lookup(String varName) {
		Object found;
		for (Environment env = this; env.previous != null; env = env.previous) {
			found = env.table.get(varName);
			if (found != null) {
				return found;
			}
		}
		return null;
	}
	
	public void set(String varName, Object value) throws SemanticError{
		Object found = null;
		for (Environment env = this; env.previous != null; env = env.previous) {
			found = env.table.get(varName);
			if (found != null) {
				env.table.put(varName, value);
				break;
			}
		}
		if (found == null) {
			throw new SemanticError("The variable has not been defined");
		}
	}
}
