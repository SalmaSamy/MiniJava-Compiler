package SentaxAnalyzer;

public class ArgumentParameters {
	/*
	 * ArgumentParameters = Expression More | e
	 * More = "," Expression More | e
	 */
	
	public static Node valid () {
		Node arguments = new Node("ArgumentParameters");

		while(true){
			if (arguments.children.size() > 0) {
				Node commaNode = Analyzer.addTerminalNode(TokenType.COMMA);
				if (commaNode == null){
					Analyzer.index--;
					break;
				}
				arguments.addChild(commaNode);
			}
			
			Node expression = Expression.valid();
			if (expression == null)
			{
				if(arguments.children.size() > 0)
					return null;
				
				expression = new Node("Expression");
				expression.addChild(new Node("e"));
			}
			arguments.addChild(expression);
			
		}
		
		return arguments;
	}

}
