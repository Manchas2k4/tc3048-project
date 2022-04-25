package symbols;

import lexer.*;
import java.util.Hashtable;

public class Environment {
	private Hashtable<String, Token> table;
	private Environment prev;
	
	public Environment(Environment prev) {
		this.table = new Hashtable<String, Token>();
		this.prev = prev;
	}
	
	public void put(String s, Token t) {
		table.put(s,  t);
	}
	
	public Token get(String s) {
		for (Environment e = this; e != null; e = e.prev) {
			Token found = e.table.get(s);
			if (found != null) {
				return found;
			}
		}
		return null;
	}
}
