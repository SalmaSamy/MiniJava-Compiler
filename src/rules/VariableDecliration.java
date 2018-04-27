package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class VariableDecliration {

	public static Node valid() {
		Node variableNode = new Node("Variable Declaration");

		Node typeVariable = Type.valid();
		if (typeVariable == null)
			return null;
		variableNode.addChild(typeVariable);

		while (true) {
			if (variableNode.getChildren().size() > 1) {
				Node commaNode = Parser.addTerminalNode(TokenType.COMMA);
				if (commaNode == null) {
					Parser.index--;
					break;
				}
				variableNode.addChild(commaNode);
			}

			Node idNode = Parser.addTerminalNode(TokenType.ID);
			if (idNode == null) {
				return null;
			}

			variableNode.addChild(idNode);

			Node assignment = Parser.addTerminalNode(TokenType.ASSIGNMENT);
			if (assignment == null) {

				--Parser.index;
				continue;
			}

			variableNode.addChild(assignment);

			Node expression = Expression.valid();
			if (expression == null)
				return null;

			variableNode.addChild(expression);
		}

		Node semicolon = Parser.addTerminalNode(TokenType.SEMICOLON);
		if (semicolon == null)
			return null;
		variableNode.addChild(semicolon);

		return variableNode;
	}
}