package symbols;

import errors.SemanticError;
import java.util.Hashtable;

public class Environment {
	private Hashtable<String, Double> table;
	private Environment previous;
	
	public Environment() {
		table = new Hashtable<String, Double>();
		previous = null;
	}
	
	public Environment(Environment prev) {
		table = new Hashtable<String, Double>();
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
	
	public Double lookup(String varName) {
		Double found;
		for (Environment env = this; env.previous != null; env = env.previous) {
			found = env.table.get(varName);
			if (found != null) {
				return found;
			}
		}
		return null;
	}
	
	public void set(String varName, double value) throws SemanticError{
		Double found = null;
		for (Environment env = this; env.previous != null; env = env.previous) {
			found = env.table.get(varName);
			if (found != null) {
				env.table.put(varName, Double.valueOf(value));
				break;
			}
		}
		if (found == null) {
			throw new SemanticError("The variable has not been defined");
		}
	}
}
