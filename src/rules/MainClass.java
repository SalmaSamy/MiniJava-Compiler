package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class MainClass {

	/*
	 * MainClass = "class" Identifier "{" "public" "static" "void" "main" "("
	 * "String" "[" "]" Identifier ")" "{" Statements "}""}"
	 */

	public static Node valid() {

		int oldIndex = Parser.index;
		Node mainClass = new Node("MainClass");

		TokenType[] mainTerminals = { TokenType.CLASS, TokenType.ID, TokenType.LEFT_CURLY_B, TokenType.PUBLIC,
				TokenType.STATIC, TokenType.VOID, TokenType.MAIN, TokenType.LEFT_ROUND_B, TokenType.STRING,
				TokenType.LEFT_SQUARE_B, TokenType.RIGHT_SQUARE_B, TokenType.ID, TokenType.RIGHT_ROUND_B,
				TokenType.LEFT_CURLY_B };

		for (TokenType tt : mainTerminals) {
			Node current = Parser.addTerminalNode(tt, false);
			mainClass.addChild(current);
			
			if (current.isException())
				return mainClass;
			
			oldIndex = Parser.index;
		}
		
		Node statements = new Node("Statements");
		statements.setEpsilon(true);
		oldIndex = Parser.index;
		
		while (true) {
			Node singleStatement = Statement.valid();
			
			if (singleStatement == null) {
				Parser.index = oldIndex;
				break;
			}
			
			statements.addChild(singleStatement);
			
			if (singleStatement.isException()) {
				mainClass.addChild(statements);
				return mainClass;
			}
			oldIndex = Parser.index;
		}
		mainClass.addChild(statements);
		

		TokenType[] mainTerminals2 = { TokenType.RIGHT_CURLY_B, TokenType.RIGHT_CURLY_B };
		for (TokenType tt : mainTerminals2) {
			Node current = Parser.addTerminalNode(tt, false);
			mainClass.addChild(current);
			
			if (current.isException())
				return mainClass;
		}
		return mainClass;

	}
}
