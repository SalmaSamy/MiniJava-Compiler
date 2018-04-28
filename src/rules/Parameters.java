package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class Parameters {
	
	/*
	 * Parameters = Type Identifier MoreParameters | e
	 *	MoreParameters = "," Type Identifier MoreParameters | e
	 */

	public static Node valid() {
		Node parameters = new Node("Parameters");
		parameters.setEpsilon(true);

		while (true) {
			if (!parameters.isLeaf()) {
				Node commaNode = Parser.addTerminalNode(TokenType.COMMA, true);
				if (commaNode == null) {
					Parser.index--;
					break;
				}
				parameters.addChild(commaNode);
			}

			Node typeParameter = Type.valid(true);
			
			if (typeParameter == null) {
				--Parser.index;
				break;
			}
				
			parameters.addChild(typeParameter);

			Node idNode = Parser.addTerminalNode(TokenType.ID, false);
			parameters.addChild(idNode);
			
			if (idNode.isException())
				break;
				
			parameters.setEpsilon(false);
		}
		
		return parameters;
	}

}
