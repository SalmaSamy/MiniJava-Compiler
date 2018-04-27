package rules;

import java.util.ArrayList;
import java.util.Arrays;

import lexical_Analyzer.Token;
import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class Term {

	/*
	 * Term = "(" Expression ")" | "new" AfterNew Identifier | <INTEGER_LITERAL>
	 * | <FLOAT_LITERAL> | "true" | "false" | "this"
	 * 
	 * AfterNew = Type "[" Expression "]" | Identifier "(" ArgumentParameter ")"
	 */

	public static Node valid() {
		Node term = new Node("Term");

		// Terminals
		ArrayList<TokenType> terminals = new ArrayList<TokenType>(Arrays.asList(TokenType.ID,
				TokenType.INTEGRAL_LITERAL, TokenType.FLOAT, TokenType.TRUE, TokenType.FALSE, TokenType.THIS));
		
		Token token = Parser.getCurToken();
		for (TokenType tt : terminals) {
			if (tt == token.type) {
				term.addChild(new Node(token.value));
				return term;
			}
		}

		// "(" Expression ")"
		Parser.index--;
		Node LRound = Parser.addTerminalNode(TokenType.LEFT_ROUND_B);
		if (LRound != null) {
			term.addChild(LRound);

			Node expression = Expression.valid();
			if (expression == null)
				return null;

			term.addChild(expression);

			Node RRound = Parser.addTerminalNode(TokenType.RIGHT_ROUND_B);
			if (RRound == null)
				return null;
			term.addChild(RRound);
			return term;
		}

		// new
		Parser.index--;
		Node newNode = Parser.addTerminalNode(TokenType.NEW);
		if (newNode == null) {
			Parser.index--;
			return null;
		}
		term.addChild(newNode);

		// AfterNew
		// Type "[" Expression "]"
		Node type = Type.valid();
		if (type != null) {
			term.addChild(type);
			Node LSQUARE = Parser.addTerminalNode(TokenType.LEFT_SQUARE_B);
			if (LSQUARE != null) {
				term.addChild(LSQUARE);

				Node expression = Expression.valid();
				if (expression == null)
					return null;
				term.addChild(expression);

				Node RSQUARE = Parser.addTerminalNode(TokenType.RIGHT_SQUARE_B);
				if (RSQUARE == null)
					return null;
				term.addChild(RSQUARE);
				return term;
			}
		}

		Parser.index--;
		Node functionCall = FunctionCall.valid();
		if (functionCall != null) {
			term.addChild(functionCall);
			return term;
		}

		Parser.index--;
		return null;
	}
}