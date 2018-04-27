package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class FunctionCall {

	// Identifier "(" ArgumentParameter ")"

	public static Node valid() {
		Node functionCall = new Node("Function Call");

		Node idNode = Parser.addTerminalNode(TokenType.ID, true);
		if (idNode != null) {
			functionCall.addChild(idNode);

			Node LROUND = Parser.addTerminalNode(TokenType.LEFT_ROUND_B, false);
			functionCall.addChild(LROUND);

			if (!LROUND.isException()) {
				Node arguments = ArgumentParameters.valid();
				functionCall.addChild(arguments);
				
				if (arguments.isException())
					return functionCall;
				

				Node RROUND = Parser.addTerminalNode(TokenType.RIGHT_ROUND_B, false);
				functionCall.addChild(RROUND);
			}
			return functionCall;
			
		}
		return null;
	}
}
