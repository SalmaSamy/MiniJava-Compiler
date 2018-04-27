package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class MemberAssignment {
	
	// Identifier SquareEx "=" Expression ";"
	// SquareEx = “[“ Expression “]” | e


	public static Node valid() {
		Node assignment = new Node("MemberAssignment");

		Node idNode = Parser.addTerminalNode(TokenType.ID, true);
		if (idNode == null)
			return null;
		assignment.addChild(idNode);

		// SquareEx = “[“ Expression “]” | e
		
		Node LSQUARE = Parser.addTerminalNode(TokenType.LEFT_SQUARE_B, true);
		
		if (LSQUARE != null) {
			assignment.addChild(LSQUARE);

			Node expression = Expression.valid();
			assignment.addChild(expression);
			
			if (expression.isException())
				return assignment;
			

			Node RSQUARE = Parser.addTerminalNode(TokenType.RIGHT_SQUARE_B, false);
			assignment.addChild(RSQUARE);
			
			if (RSQUARE.isException())
				return assignment;
		} else
		Parser.index--;


		Node equal = Parser.addTerminalNode(TokenType.ASSIGNMENT, false);
		assignment.addChild(equal);
		
		if (equal.isException())
			return null;
	
		Node expression = Expression.valid();
		assignment.addChild(expression);
		
		if (expression.isException())
			return assignment;
		
		


		Node semicolon = Parser.addTerminalNode(TokenType.SEMICOLON, false);
		assignment.addChild(semicolon);

		return assignment;
	}

}
