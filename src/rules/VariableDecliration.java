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
				Node commaNode = Parser.addTerminalNode(TokenType.COMMA, true);
				if (commaNode == null) {
					Parser.index--;
					break;
				}
				variableNode.addChild(commaNode);
			}

			Node idNode = Parser.addTerminalNode(TokenType.ID, false);
			variableNode.addChild(idNode);

			if (idNode.isException()) {
				return variableNode;
			}

			Node assignment = Parser.addTerminalNode(TokenType.ASSIGNMENT, true);
			if (assignment == null) {
				--Parser.index;
				continue;
			}

			variableNode.addChild(assignment);

			Node expression = Expression.valid();
			variableNode.addChild(expression);

			if (expression.isException())
				return variableNode;
		}

		Node semicolon = Parser.addTerminalNode(TokenType.SEMICOLON, false);
		variableNode.addChild(semicolon);

		return variableNode;
	}
}