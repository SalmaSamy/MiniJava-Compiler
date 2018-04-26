package SentaxAnalyzer;

public class Statement {
	/*
	 * Statement = "{" Statments "}"
	 * | IfCondition
	 * | While
	 * | SysOut
	 * | memberAssignment
	
	 * Statments = Statement Statments | e
	 */

	public static Node valid() {
		Node statement = new Node("Statment");

		// ifCondition
		Node ifCondition = IfCondition.valid();
		if (ifCondition != null) {
			statement.addChild(ifCondition);
			return statement;
		}
		Analyzer.index--;

		// while
		Node whileNode = While.valid();
		if (whileNode != null) {
			statement.addChild(whileNode);
			return statement;
		}
		Analyzer.index--;
		
		//Sysout
		Node sysout = SysOut.valid();
		if(sysout != null){
			statement.addChild(sysout);			
			return statement;
		}
		Analyzer.index--;
		
		//MemberAssignment
		Node memberAssignment = MemberAssignment.valid();
		if (memberAssignment != null) {
			statement.addChild(memberAssignment);
			return statement;
		}
		Analyzer.index--;
		
		//"{" Statments "}"
		Node LCurl = Analyzer.addTerminalNode("LEFT_CURL_B");
		if (LCurl != null){
			statement.addChild(LCurl);
			
			//Statments = Statement Statments | e
			Node statements = new Node("Statements");
			while(true){
				Node singleStatement = Statement.valid();
				if(singleStatement == null)
					break;
				statements.addChild(singleStatement);
			}
			statement.addChild(statements);
			
			Node RCurl = Analyzer.addTerminalNode("RIGHT_CURL_B");
			if (RCurl != null)
				return null;
			
			statement.addChild(RCurl);
			return statement;
		}
		Analyzer.index--;

		return null;
	}
}