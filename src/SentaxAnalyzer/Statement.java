package SentaxAnalyzer;

public class Statement {

	/*
	 * Statement = "{" Statments "}" | IfCondition | While |
	 * "System.out.println" "(" Expression ")" ";" | Identifier SquareEx "="
	 * Expression ";" SquareEx = “[“ Expression “]” | e Statments = Statement
	 * Statments | e
	 */

	public static Node valid() {
		Node statement = new Node("Statment");

		// ifCondition
		Node ifCondition = IfCondition.valid();
		if (ifCondition != null) {
			statement.addChild(ifCondition);
			return statement;
		}
		Analyzer.index--;

		// while
		Node whileNode = While.valid();
		if (whileNode != null) {
			statement.addChild(whileNode);
			return statement;
		}
		Analyzer.index--;

		// "System.out.println" "(" Expression ")" ";"
		Node sysout = Analyzer.addTerminalNode("SYSTEM.OUT.PRINTLN");
		if (sysout != null) {
			statement.addChild(sysout);

			Node LRound = Analyzer.addTerminalNode("LEFT_ROUND_B");
			if (LRound == null)
				return null;
			statement.addChild(LRound);

			Node expression = Expression.valid();
			if (expression == null)
				return null;
			statement.addChild(expression);

			Node RRound = Analyzer.addTerminalNode("RIGHT_ROUND_B");
			if (RRound == null)
				return null;
			statement.addChild(RRound);

			Node semicolon = Analyzer.addTerminalNode("SEMICOLON");
			if (semicolon == null)
				return null;
			statement.addChild(semicolon);

			return statement;
		}
		Analyzer.index--;
		
		// Identifier SquareEx "=" Expression ";"
		Node idNode = Analyzer.addTerminalNode("ID");
		if (idNode != null) {
			statement.addChild(idNode);

			// SquareEx = “[“ Expression “]” | e
			Node LSQUARE = Analyzer.addTerminalNode("LEFT_SQUARE_B");
			if (LSQUARE != null) {
				statement.addChild(LSQUARE);

				Node expression = Expression.valid();
				if (expression == null)
					return null;
				statement.addChild(expression);

				Node RSQUARE = Analyzer.addTerminalNode("RIGHT_SQUARE_B");
				if (RSQUARE == null)
					return null;
				statement.addChild(RSQUARE);
			} else
				Analyzer.index--;

			Node equal = Analyzer.addTerminalNode("ASSIGNMENT");
			if (equal == null)
				return null;
			statement.addChild(equal);

			Node expression = Expression.valid();
			if (expression == null)
				return null;
			statement.addChild(expression);

			Node semicolon = Analyzer.addTerminalNode("SEMICOLON");
			if (semicolon == null)
				return null;
			statement.addChild(semicolon);
			return statement;

		}
		Analyzer.index--;

		// "{" Statments "}"
		Node LCurl = Analyzer.addTerminalNode("LEFT_CURL_B");
		if (LCurl != null) {
			statement.addChild(LCurl);

			// Statments = Statement Statments | e
			Node statements = new Node("Statements");
			while (true) {
				Node singleStatement = Statement.valid();
				if (singleStatement == null)
					break;
				statements.addChild(singleStatement);
			}
			statement.addChild(statements);

			Node RCurl = Analyzer.addTerminalNode("RIGHT_CURL_B");
			if (RCurl != null)
				return null;

			statement.addChild(RCurl);
			return statement;
		}
		Analyzer.index--;

		return null;
	}
}