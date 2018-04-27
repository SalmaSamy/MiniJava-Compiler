package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class IfCondition {
	//IfCondition = "if" "("Expression")" Statment elseOption
	//ElseOption =  “else” Statement | e
	
	public static Node valid(){
		Node ifCondition = new Node("IfCondition");

		Node ifNode = Parser.addTerminalNode(TokenType.IF);
		if(ifNode == null)
			return null;
		ifCondition.addChild(ifNode);
		
		Node LRound = Parser.addTerminalNode(TokenType.LEFT_ROUND_B);
		if(LRound == null)
			return null;
		ifCondition.addChild(LRound);
		
		//expression
		Node expression = Expression.valid();
		if(expression == null)
			return null;
		ifCondition.addChild(expression);
		
		Node RRound = Parser.addTerminalNode(TokenType.RIGHT_ROUND_B);
		if(RRound == null)
			return null;
		ifCondition.addChild(RRound);
		
		
		//Statement
		Node statement = Statement.valid();
		if(statement == null)
			return null;
		ifCondition.addChild(statement);
		
		
		//ElseOption =  “else” Statement | e
		Node elseNode = Parser.addTerminalNode(TokenType.ELSE);
		if(elseNode != null){
			ifCondition.addChild(elseNode);
			
			Node elseStatement = Statement.valid();
			if(elseStatement == null)
				return null;
			ifCondition.addChild(elseStatement);
		}
		else
			--Parser.index;
		
		return ifCondition;
	}

}
