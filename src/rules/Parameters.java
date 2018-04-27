package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class Parameters {

	public static Node valid() {
		Node parameters = new Node("Parameters");

		while (true) {
			if (!parameters.isLeaf()) {
				Node commaNode = Parser.addTerminalNode(TokenType.COMMA);
				if (commaNode == null) {
					Parser.index--;
					break;
				}
				parameters.addChild(commaNode);
			}

			Node typeParameter = Type.valid();
			if (typeParameter == null)
				return null;
			parameters.addChild(typeParameter);

			Node idNode = Parser.addTerminalNode(TokenType.ID);
			if (idNode == null)
				return null;
			parameters.addChild(idNode);
		}

		return parameters;
	}

}
