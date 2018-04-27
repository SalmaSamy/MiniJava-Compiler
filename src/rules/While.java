package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class While {
	//While = “while"  "(" Expression ")" Statement
	
	public static Node valid(){
		Node whileStatement = new Node("WhileStatment");
		
		Node whileNode = Parser.addTerminalNode(TokenType.WHILE, true);
		if(whileNode == null)
			return null;
		whileStatement.addChild(whileNode);
		
		Node LRound = Parser.addTerminalNode(TokenType.LEFT_ROUND_B,false);
		whileStatement.addChild(LRound);
		
		if(LRound.isException())
			return whileNode;
		
		
		//expression
		Node expression = Expression.valid();
		whileStatement.addChild(expression);
		
		if(expression.isException())
			return whileStatement;
		
		
		Node RRound = Parser.addTerminalNode(TokenType.RIGHT_ROUND_B, false);
		whileStatement.addChild(RRound);
		
		if(RRound == null)
			return whileNode;
		
		
		//statement
		Node statement = Statement.valid();
		if(statement == null) {
			statement = new Node("Syntax Error: expecting statement after while");
			statement.setException(true);
		}
		
		whileStatement.addChild(statement);
		return whileStatement;
		
	}

}
