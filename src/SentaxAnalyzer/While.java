package SentaxAnalyzer;

public class While {
	//While = “while"  "(" Expression ")" Statement
	
	public static Node valid(){
		Node whileStatement = new Node("WhileStatment");
		
		Node whileNode = Analyzer.addTerminalNode(TokenType.WHILE);
		if(whileNode == null)
			return null;
		whileStatement.addChild(whileNode);
		
		Node LRound = Analyzer.addTerminalNode(TokenType.LEFT_ROUND_B);
		if(LRound == null)
			return null;
		whileStatement.addChild(LRound);
		
		//expression
		Node expression = Expression.valid();
		if(expression == null)
			return null;
		whileStatement.addChild(expression);
		
		Node RRound = Analyzer.addTerminalNode(TokenType.RIGHT_ROUND_B);
		if(RRound == null)
			return null;
		whileStatement.addChild(RRound);
		
		//statement
		Node statement = Statement.valid();
		if(statement == null)
			return null;
		whileStatement.addChild(statement);
				
		return whileStatement;
		
	}

}
