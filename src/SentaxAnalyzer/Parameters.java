package SentaxAnalyzer;

public class Parameters {
	
	public static Node valid(){
		Node parameters = new Node("Parameters");
		
		while(true){
			if (!parameters.children.isEmpty()) {
				Node commaNode = Analyzer.addTerminalNode("COMMA");
				if (commaNode == null)
				{
					Analyzer.index--;
					break;
				}
				parameters.addChild(commaNode);
			}
	
			Node typeParameter = Type.valid();
			if (typeParameter == null)
				return null;
			parameters.addChild(typeParameter);
			
			Node idNode = Analyzer.addTerminalNode("ID");
			if (idNode == null)
				return null;
			parameters.addChild(idNode);
		}
		
		return parameters;
	}
	

}
