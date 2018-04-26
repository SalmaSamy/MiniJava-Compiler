package SentaxAnalyzer;

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
		
		--Analyzer.index;
		Node dot = Analyzer.addTerminalNode("DOT");
		if (dot != null) {
			// .length
			operatorSide.addChild(dot);
			
			Node len = Analyzer.addTerminalNode("LENGTH");
			if (len != null) {
				operatorSide.addChild(len);
				return operatorSide;
			}
			--Analyzer.index;
			// function call
			Node functionCall = FunctionCall.valid();
			if (functionCall != null) {
				operatorSide.addChild(functionCall);
				return operatorSide;
			}
			return null;
		}
		--Analyzer.index;

		// [ expression]
		Node LSquare = Analyzer.addTerminalNode("LEFT_SQUARE_B");
		if (LSquare != null) {
			operatorSide.addChild(LSquare);

			Node expression = Expression.valid();
			if (expression == null)
				return null;

			operatorSide.addChild(expression);

			Node RSquare = Analyzer.addTerminalNode("RIGHT_SQUARE_B");
			if (RSquare == null)
				return null;

			operatorSide.addChild(RSquare);
			return operatorSide;
		}
		
		--Analyzer.index;
		return operatorSide;
	}

}