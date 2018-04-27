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
				TokenType.INTEGRAL_LITERAL, TokenType.FLOAT_LITERAL, TokenType.TRUE, TokenType.FALSE, TokenType.THIS));

		Token token = Parser.getCurToken();
		for (TokenType tt : terminals) {
			if (tt == token.type) {
				term.addChild(new Node(token.value));

				return term;
			}
		}

		// "(" Expression ")"
		Parser.index--;
		Node LRound = Parser.addTerminalNode(TokenType.LEFT_ROUND_B, true);
		if (LRound != null) {
			term.addChild(LRound);

			Node expression = Expression.valid();
			term.addChild(expression);

			if (expression.isException())
				return term;

			Node RRound = Parser.addTerminalNode(TokenType.RIGHT_ROUND_B, false);
			term.addChild(RRound);

			return term;
		}

		// new
		Parser.index--;
		Node newNode = Parser.addTerminalNode(TokenType.NEW, false);
		term.addChild(newNode);

		if (newNode.isException()) {
			newNode.setName("Syntax Error: expecting expression");
			Parser.index--;
			return term;
		}

		// AfterNew

		// function call
		Node functionCall = FunctionCall.valid();
		if (Node.valid(functionCall)) {
			term.addChild(functionCall);
			return term;
		}
		Parser.index--;

		Node type = Type.valid();

		if (type == null) {
			term.setName("Syntax error");
			term.setException(true);
			return term;
		}
		term.addChild(type);

		// Type "[" Expression "]"

		Node LSQUARE = Parser.addTerminalNode(TokenType.LEFT_SQUARE_B, false);
		term.addChild(LSQUARE);
		
		if (!LSQUARE.isException()) {
		
			Node expression = Expression.valid();
			term.addChild(expression);
			
			if (expression.isException())
				return term;
			
			Node RSQUARE = Parser.addTerminalNode(TokenType.RIGHT_SQUARE_B, false);
			term.addChild(RSQUARE);
			
			return term;
		}

		Parser.index--;
		return term;
	}
}