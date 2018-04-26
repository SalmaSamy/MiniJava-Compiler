package SentaxAnalyzer;

public class IfCondition {
	//IfCondition = "if" "("Expression")" Statment elseOption
	//ElseOption =  “else” Statement | e
	
	public static Node valid(){
		Node ifCondition = new Node("IfCondition");

		Node ifNode = Analyzer.addTerminalNode("IF");
		if(ifNode == null)
			return null;
		ifCondition.addChild(ifNode);
		
		Node LRound = Analyzer.addTerminalNode("LEFT_ROUND_B");
		if(LRound == null)
			return null;
		ifCondition.addChild(LRound);
		
		//expression
		Node expression = Expression.valid();
		if(expression == null)
			return null;
		ifCondition.addChild(expression);
		
		Node RRound = Analyzer.addTerminalNode("RIGHT_ROUND_B");
		if(RRound == null)
			return null;
		ifCondition.addChild(RRound);
		
		
		//Statement
		Node statement = Statement.valid();
		if(statement == null)
			return null;
		ifCondition.addChild(statement);
		
		
		//ElseOption =  “else” Statement | e
		Node elseNode = Analyzer.addTerminalNode("ELSE");
		if(elseNode != null){
			ifCondition.addChild(elseNode);
			
			Node elseStatement = Statement.valid();
			if(elseStatement == null)
				return null;
			ifCondition.addChild(elseStatement);
		}
		else
			--Analyzer.index;
		
		return ifCondition;
	}

}
