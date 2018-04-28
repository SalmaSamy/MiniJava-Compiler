package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class Expression {

	/*
	 * Expression = Term LaLa OperatorSide
	 * LaLa = "." AfterDot | "[" Expression "]" | e
	 * OperatorSide = Operators Expression | e
	 * 
	 * AfterDot = "length” | Identifier "(" ArgumentParameter ")"
	 */

	public static Node valid() {
		Node expression = new Node("Expression");

		// Term
		Node term = Term.valid();
		expression.addChild(term);

		if (term.isException())
			return expression;

		// LaLa
		Node dot = Parser.addTerminalNode(TokenType.DOT, true);
		if (dot != null) {
			expression.addChild(dot);

			// function call
			Node functionCall = FunctionCall.valid();
			if (functionCall != null) {
				expression.addChild(functionCall);
			}
			--Parser.index;

			// .length
			Node len = Parser.addTerminalNode(TokenType.LENGTH, false);
			expression.addChild(len);

		}
		else
			--Parser.index;

		// [expression]
		Node LSquare = Parser.addTerminalNode(TokenType.LEFT_SQUARE_B, true);
		if (LSquare != null) {
			expression.addChild(LSquare);

			Node inExpression = Expression.valid();
			expression.addChild(inExpression);

			if (inExpression.isException())
				return expression;

			Node RSquare = Parser.addTerminalNode(TokenType.RIGHT_SQUARE_B, false);
			expression.addChild(RSquare);

		}
		else
			--Parser.index;

		// operator expression
		Node operator = Operators.valid();

		if (operator != null) {
			expression.addChild(operator);

			Node opExpression = Expression.valid();
			expression.addChild(opExpression);
		}
		else
			--Parser.index;

		return expression;
	}

}
