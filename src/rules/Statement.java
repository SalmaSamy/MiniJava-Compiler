package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class Statement {

	/*
	 * Statement = "{" Statments "}" | IfCondition | While | SysOut |
	 * memberAssignment Statments = Statement Statments | e
	 */

	public static Node valid() {
		Node statement = new Node("Statment", true);

		// ifCondition
		Node ifCondition = IfCondition.valid();
		if (ifCondition != null) {
			statement.addChild(ifCondition);
			return statement;
		}
		Parser.index--;

		// while
		Node whileNode = While.valid();
		if (whileNode != null) {
			statement.addChild(whileNode);
			return statement;
		}
		Parser.index--;

		// Sysout
		Node sysout = SysOut.valid();
		if (sysout != null) {
			statement.addChild(sysout);
			return statement;
		}
		Parser.index--;

		// MemberAssignment
		Node memberAssignment = MemberAssignment.valid();
		if (memberAssignment != null) {
			statement.addChild(memberAssignment);
			return statement;
		}
		Parser.index--;

		// "{" Statments "}"
		Node LCurl = Parser.addTerminalNode(TokenType.LEFT_CURLY_B, true);

		if (LCurl != null) {
			statement.addChild(LCurl);

			// Statments = Statement Statments | e
			int oldIndex = Parser.index;
			Node statements = new Node("Statements", true);
			
			while (true) {
				Node singleStatement = Statement.valid();
				if (singleStatement == null) {
					Parser.index = oldIndex;
					break;
				}

				statements.addChild(singleStatement);
				if (singleStatement.isException()) {
					statement.addChild(statements);
					return statement;
				}

				oldIndex = Parser.index;
			}
			statement.addChild(statements);

			Node RCurl = Parser.addTerminalNode(TokenType.RIGHT_CURLY_B, false);
			statement.addChild(RCurl);

			return statement;
		}
		Parser.index--;
		
		return null;
	}
}