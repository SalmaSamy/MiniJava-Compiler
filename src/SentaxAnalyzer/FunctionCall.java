package SentaxAnalyzer;

public class FunctionCall {

	// Identifier "(" ArgumentParameter ")"

	public static Node valid() {
		Node functionCall = new Node("Function Call");

		Node idNode = Analyzer.addTerminalNode(TokenType.ID);
		if (idNode != null) {
			functionCall.addChild(idNode);
			
			Node LROUND = Analyzer.addTerminalNode(TokenType.LEFT_ROUND_B);
			if (LROUND != null) {
				functionCall.addChild(LROUND);

				Node arguments = ArgumentParameters.valid();
				if (arguments == null)
					return null;
				functionCall.addChild(arguments);

				Node RROUND = Analyzer.addTerminalNode(TokenType.RIGHT_ROUND_B);
				if (RROUND == null)
					return null;
				functionCall.addChild(RROUND);
				return functionCall;
			}
		}
		return null;
	}
}
