package SentaxAnalyzer;

public class MainClass {
	
	/*
	 MainClass = "class" Identifier "{"
	"public" "static" "void" "main" "(" "String" "[" "]" Identifier ")"
	"{" Statements "}""}" 
	 */
	
	public static Node valid(){
		
		int oldIndex = Analyzer.index;
		Node mainClass = new Node("MainClass");
		
		String[] mainTerminals = { "class", "ID", "LEFT_CURLY_B", "public", "static",
				"void", "main", "LEFT_ROUND_B", "String", "LEFT_SQUARE_B",
				"RIGHT_SQUARE_B", "ID", "RIGHT_ROUND_B","LEFT_CURLY_B", "Statements",
				"RIGHT_CURLY_B", "RIGHT_CURLY_B"}; 
		
		for(String s : mainTerminals){
			
			if(s.equals("Statements")){
				Node statements = new Node("Statements");
				while(true){
					Node singleStatement = Statement.valid();
					if(singleStatement == null){
						Analyzer.index = oldIndex;
						break;
					}
					statements.addChild(singleStatement);
				}
				mainClass.addChild(statements);
				continue;
			}
			
			Node current = Analyzer.addTerminalNode(s.toUpperCase());
			if(current == null) {
				return null;
			}
			mainClass.addChild(current);
			oldIndex = Analyzer.index;
		}
		
		return mainClass;
		
	}

}
