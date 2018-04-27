package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class OperatorSide {

	/*
	 * OperatorSide = Operators Expression | "." AfterDot | "[" Expression "]" |
	 * e
	 * 
	 * AfterDot = "length” | Identifier "(" ArgumentParameter ")"
	 * 
	 */

	public static Node valid() {
		Node operatorSide = new Node("OperatorSide");

		// operator expression
		Node operator = Operators.valid();
				
		if (operator != null) {
			operatorSide.addChild(operator);

			Node expression = Expression.valid();
			operatorSide.addChild(expression);

			return operatorSide;
		}

		--Parser.index;
		Node dot = Parser.addTerminalNode(TokenType.DOT, true);
		if (dot != null) {
			
			operatorSide.addChild(dot);

			// function call
			Node functionCall = FunctionCall.valid();
			if (functionCall != null) {
				operatorSide.addChild(functionCall);
				return operatorSide;
			}
			--Parser.index;
			
			// .length
			Node len = Parser.addTerminalNode(TokenType.LENGTH, false);
			operatorSide.addChild(len);
		
			return operatorSide;
		}
		--Parser.index;

		// [ expression]
		Node LSquare = Parser.addTerminalNode(TokenType.LEFT_SQUARE_B, true);
		if (LSquare != null) {
			operatorSide.addChild(LSquare);

			Node expression = Expression.valid();
			operatorSide.addChild(expression);
			
			if (expression.isException())
				return operatorSide;

			

			Node RSquare = Parser.addTerminalNode(TokenType.RIGHT_SQUARE_B, false);
			operatorSide.addChild(RSquare);
			
			return operatorSide;
		}

		--Parser.index;

		operatorSide.setEpsilon(true);
		return operatorSide;
	}
}