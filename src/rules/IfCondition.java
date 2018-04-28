package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class IfCondition {
	// IfCondition = "if" "("Expression")" Statment elseOption
	// ElseOption = “else” Statement | e

	public static Node valid() {
		Node ifCondition = new Node("IfCondition", true);

		Node ifNode = Parser.addTerminalNode(TokenType.IF, true);
		if (ifNode == null)
			return null;
		ifCondition.addChild(ifNode);

		Node LRound = Parser.addTerminalNode(TokenType.LEFT_ROUND_B, false);
		ifCondition.addChild(LRound);

		if (LRound.isException())
			return ifCondition;

		// expression
		Node expression = Expression.valid();
		ifCondition.addChild(expression);

		if (expression.isException())
			return ifCondition;

		Node RRound = Parser.addTerminalNode(TokenType.RIGHT_ROUND_B, false);
		ifCondition.addChild(RRound);

		if (RRound.isException())
			return ifCondition;

		// Statement
		Node statement = Statement.valid();
		if (statement == null) {
			statement = new Node("Syntax Error: No valid statement for if");
			statement.setException(true);
			ifCondition.addChild(statement);
			
			return ifCondition;
		}

		ifCondition.addChild(statement);

		// ElseOption = “else” Statement | e
		Node elseNode = Parser.addTerminalNode(TokenType.ELSE, true);
		if (elseNode != null) {
			ifCondition.addChild(elseNode);

			Node elseStatement = Statement.valid();
			if (elseStatement == null) {
				Node Exception = new Node("Syntax Error: No statement for else");
				Exception.setException(true);
				ifCondition.addChild(Exception);

				return ifCondition;
			}

			ifCondition.addChild(elseStatement);
		} else
			--Parser.index;

		return ifCondition;
	}
}