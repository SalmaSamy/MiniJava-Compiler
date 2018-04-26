package SentaxAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;

public class Term {

	/*
	 * Term = "(" Expression ")" | "new" AfterNew
	 * Identifier | <INTEGER_LITERAL> | <FLOAT_LITERAL> | "true" | "false" | "this"
	 * 
	 * AfterNew = Type "[" Expression "]" | Identifier "(" ArgumentParameter ")"
	 */

	public static Node valid() {
		Node term = new Node("Term");
		
		//Terminals
		ArrayList<String> terminals = new ArrayList<String>(
				Arrays.asList("ID", "INTEGRAL_LITERAL", "FLOAT", "TRUE", "FALSE", "THIS"));

		Token token = Analyzer.getCurToken();
		for (String s : terminals) {
			if (s.equals(token.name)) {
				term.addChild(new Node(token.terminal));
				return term;
			}
		}

		// "(" Expression ")"
		Analyzer.index--;
		Node LRound = Analyzer.addTerminalNode("LEFT_ROUND_B");
		if (LRound != null) {
			term.addChild(LRound);

			Node expression = Expression.valid();
			if (expression == null) 
				return null;
			
			term.addChild(expression);

			Node RRound = Analyzer.addTerminalNode("RIGHT_ROUND_B");
			if (RRound == null)
				return null;
			term.addChild(RRound);
			return term;
		}
		
		//new
		Analyzer.index--;
		Node newNode = Analyzer.addTerminalNode("NEW");
		if (newNode == null){
			Analyzer.index--;
			return null;
		}
		term.addChild(newNode);

		// AfterNew
		// Type "[" Expression "]"
		Node type = Type.valid();
		if (type != null) {
			term.addChild(type);
			Node LSQUARE = Analyzer.addTerminalNode("LEFT_SQUARE_B");
			if (LSQUARE != null) {
				term.addChild(LSQUARE);

				Node expression = Expression.valid();
				if (expression == null)
					return null;
				term.addChild(expression);

				Node RSQUARE = Analyzer.addTerminalNode("RIGHT_SQUARE_B");
				if (RSQUARE == null)
					return null;
				term.addChild(RSQUARE);
				return term;
			}
		}

		Analyzer.index--;
		Node functionCall = FunctionCall.valid();
		if (functionCall != null) {
			term.addChild(functionCall);
			return term;
		}
		
		Analyzer.index--;
		return null;
	}
}