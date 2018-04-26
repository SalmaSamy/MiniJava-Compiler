package SentaxAnalyzer;

public class MainClass {

	/*
	 * MainClass = "class" Identifier "{" "public" "static" "void" "main" "("
	 * "String" "[" "]" Identifier ")" "{" Statements "}""}"
	 */

	public static Node valid() {

		int oldIndex = Analyzer.index;
		Node mainClass = new Node("MainClass");

		TokenType[] mainTerminals = { TokenType.CLASS, TokenType.ID, TokenType.LEFT_CURLY_B, TokenType.PUBLIC,
				TokenType.STATIC, TokenType.VOID, TokenType.MAIN, TokenType.LEFT_ROUND_B, TokenType.STRING,
				TokenType.LEFT_SQUARE_B, TokenType.RIGHT_SQUARE_B, TokenType.ID, TokenType.RIGHT_ROUND_B,
				TokenType.LEFT_CURLY_B };

		for (TokenType tt : mainTerminals) {
			// System.out.println(tt.name());
			Node current = Analyzer.addTerminalNode(tt);
			if (current == null)
				return null;

			mainClass.addChild(current);
			oldIndex = Analyzer.index;
		}
		Node statements = new Node("Statements");
		while (true) {
			Node singleStatement = Statement.valid();
			if (singleStatement == null) {
				Analyzer.index = oldIndex;
				break;
			}
			statements.addChild(singleStatement);
		}
		mainClass.addChild(statements);

		TokenType[] mainTerminals2 = { TokenType.RIGHT_CURLY_B, TokenType.RIGHT_CURLY_B };
		for (TokenType tt : mainTerminals2) {
			Node current = Analyzer.addTerminalNode(tt);
			if (current == null)
				return null;

			mainClass.addChild(current);
			oldIndex = Analyzer.index;
		}
		return mainClass;

	}

}
