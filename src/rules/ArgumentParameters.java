package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class ArgumentParameters {
	/*
	 * ArgumentParameters = Expression More | e More = "," Expression More | e
	 */

	public static Node valid() {
		Node arguments = new Node("ArgumentParameters");

		while (true) {
			if (!arguments.isLeaf()) {
				Node commaNode = Parser.addTerminalNode(TokenType.COMMA);
				if (commaNode == null) {
					Parser.index--;
					break;
				}
				arguments.addChild(commaNode);
			}

			Node expression = Expression.valid();
			if (expression == null) {
				if (!arguments.isLeaf())
					return null;

				expression = new Node("Expression");
				expression.setEpsilon(true);
			}
			arguments.addChild(expression);

		}

		return arguments;
	}

}
