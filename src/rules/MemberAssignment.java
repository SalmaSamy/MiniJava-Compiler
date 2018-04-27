package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class MemberAssignment {
	
	// Identifier SquareEx "=" Expression ";"
	// SquareEx = “[“ Expression “]” | e


	public static Node valid() {
		Node assignment = new Node("MemberAssignment");

		Node idNode = Parser.addTerminalNode(TokenType.ID);
		if (idNode == null)
			return null;
		assignment.addChild(idNode);

		// SquareEx = “[“ Expression “]” | e
		Node LSQUARE = Parser.addTerminalNode(TokenType.LEFT_SQUARE_B);
		if (LSQUARE != null) {
			assignment.addChild(LSQUARE);

			Node expression = Expression.valid();
			if (expression == null)
				return null;
			assignment.addChild(expression);

			Node RSQUARE = Parser.addTerminalNode(TokenType.RIGHT_SQUARE_B);
			if (RSQUARE == null)
				return null;
			assignment.addChild(RSQUARE);
		} else
		Parser.index--;

		Node equal = Parser.addTerminalNode(TokenType.ASSIGNMENT);
		if (equal == null)
			return null;
		assignment.addChild(equal);

		Node expression = Expression.valid();
		if (expression == null)
			return null;
		assignment.addChild(expression);

		Node semicolon = Parser.addTerminalNode(TokenType.SEMICOLON);
		if (semicolon == null)
			return null;
		assignment.addChild(semicolon);

		return assignment;
	}

}
