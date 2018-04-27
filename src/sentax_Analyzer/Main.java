package sentax_Analyzer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import lexical_Analyzer.Token;
import lexical_Analyzer.Tokenizer;

public class Main {

	static private final String INPUT = "input.txt";
	static private final String OUTPUT = "output.txt";

	
	public static void main(String args[]) {
		FileInputStream instream = null;
		PrintStream outstream = null;

		try {
			instream = new FileInputStream(INPUT);
			outstream = new PrintStream(new FileOutputStream(OUTPUT));

			System.setIn(instream);
			System.setOut(outstream);
		} catch (Exception e) {
			System.err.println("No input file");
		}

		Scanner in = new Scanner(System.in);

		String code = "";
		for (; in.hasNextLine();) {
			if (!code.isEmpty())
				code += "\n";
			String s = in.nextLine();
			code += s;
		}
		
		Tokenizer tokenizer = new Tokenizer();
		ArrayList<Token> tokens = tokenizer.execute(code);
		
		Parser parser = new Parser(tokens);
		parser.parse();
		in.close();
	}
}