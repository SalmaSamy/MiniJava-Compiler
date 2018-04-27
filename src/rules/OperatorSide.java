package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class OperatorSide {

	/*
	 * OperatorSide = Operators Expression | "." AfterDot | "[" Expression "]" | e
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
			if (expression == null)
				return null;
			operatorSide.addChild(expression);

			return operatorSide;
		}
		
		--Parser.index;
		Node dot = Parser.addTerminalNode(TokenType.DOT);
		if (dot != null) {
			// .length
			operatorSide.addChild(dot);
			
			Node len = Parser.addTerminalNode(TokenType.LENGTH);
			if (len != null) {
				operatorSide.addChild(len);
				return operatorSide;
			}
			--Parser.index;
			// function call
			Node functionCall = FunctionCall.valid();
			if (functionCall != null) {
				operatorSide.addChild(functionCall);
				return operatorSide;
			}
			return null;
		}
		--Parser.index;

		// [ expression]
		Node LSquare = Parser.addTerminalNode(TokenType.LEFT_SQUARE_B);
		if (LSquare != null) {
			operatorSide.addChild(LSquare);

			Node expression = Expression.valid();
			if (expression == null)
				return null;

			operatorSide.addChild(expression);

			Node RSquare = Parser.addTerminalNode(TokenType.RIGHT_SQUARE_B);
			if (RSquare == null)
				return null;

			operatorSide.addChild(RSquare);
			return operatorSide;
		}
		
		--Parser.index;
		
		operatorSide.setEpsilon(true);
		return operatorSide;
	}

}