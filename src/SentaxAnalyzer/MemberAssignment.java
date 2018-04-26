package SentaxAnalyzer;

public class MemberAssignment {
	
	// Identifier SquareEx "=" Expression ";"
	// SquareEx = “[“ Expression “]” | e


	public static Node valid() {
		Node assignment = new Node("MemberAssignemtn");

		Node idNode = Analyzer.addTerminalNode("ID");
		if (idNode == null)
			return null;
		assignment.addChild(idNode);

		// SquareEx = “[“ Expression “]” | e
		Node LSQUARE = Analyzer.addTerminalNode("LEFT_SQUARE_B");
		if (LSQUARE != null) {
			assignment.addChild(LSQUARE);

			Node expression = Expression.valid();
			if (expression == null)
				return null;
			assignment.addChild(expression);

			Node RSQUARE = Analyzer.addTerminalNode("RIGHT_SQUARE_B");
			if (RSQUARE == null)
				return null;
			assignment.addChild(RSQUARE);
		} else
		Analyzer.index--;

		Node equal = Analyzer.addTerminalNode("ASSIGNMENT");
		if (equal == null)
			return null;
		assignment.addChild(equal);

		Node expression = Expression.valid();
		if (expression == null)
			return null;
		assignment.addChild(expression);

		Node semicolon = Analyzer.addTerminalNode("SEMICOLON");
		if (semicolon == null)
			return null;
		assignment.addChild(semicolon);

		return assignment;
	}

}
