import java.io.FileInputStream;

import grammar.Parser;

public class MainParser {
	public static void main(String args[]) throws Exception {
		if (args.length != 1) {
			System.out.println("usage: java ConsoleApp file");
			System.exit(0);
		}
		
		FileInputStream input = new FileInputStream(args[0]); 
		Parser parser = new Parser(input);
		parser.analyze();
	}
}
