package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class FunctionCall {

	// Identifier "(" ArgumentParameter ")"

	public static Node valid() {
		Node functionCall = new Node("Function Call");

		Node idNode = Parser.addTerminalNode(TokenType.ID);
		if (idNode != null) {
			functionCall.addChild(idNode);
			
			Node LROUND = Parser.addTerminalNode(TokenType.LEFT_ROUND_B);
			if (LROUND != null) {
				functionCall.addChild(LROUND);

				Node arguments = ArgumentParameters.valid();
				if (arguments == null)
					return null;
				functionCall.addChild(arguments);

				Node RROUND = Parser.addTerminalNode(TokenType.RIGHT_ROUND_B);
				if (RROUND == null)
					return null;
				functionCall.addChild(RROUND);
				return functionCall;
			}
		}
		return null;
	}
}
