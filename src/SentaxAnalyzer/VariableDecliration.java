package SentaxAnalyzer;

public class VariableDecliration {

	public static Node valid() {
		Node variableNode = new Node("Variable Declaration");

		Node typeVariable = Type.valid();
		if (typeVariable == null)
			return null;
		variableNode.addChild(typeVariable);

		while (true) {
			if (variableNode.children.size() > 1) {
				Node commaNode = Analyzer.addTerminalNode("COMMA");
				if (commaNode == null) {
					Analyzer.index--;
					break;
				}
				variableNode.addChild(commaNode);
			}

			Node idNode = Analyzer.addTerminalNode("ID");
			if (idNode == null)
				return null;
			variableNode.addChild(idNode);

			// TODO check assignment
			Node assignment = Analyzer.addTerminalNode("ASSIGNMENT");
			if (assignment == null) {
				--Analyzer.index;
				continue;
			}
			
			variableNode.addChild(assignment);
			
			Node expression = Expression.valid();
			if (expression == null)
				return null;
			
			variableNode.addChild(expression);
		}
		
		Node semicolon = Analyzer.addTerminalNode("SEMICOLON");
		if (semicolon == null)
			return null;
		variableNode.addChild(semicolon);

		return variableNode;
	}
}