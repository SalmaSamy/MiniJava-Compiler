package SentaxAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;

public class Constructor{

	public static Node valid() {
		int oldIndex = Analyzer.index;
		Node constructor = new Node("ConstructorDeclaration");

		ArrayList<String> access = new ArrayList<>(Arrays.asList("public", "private", "protected"));
		Token token = Analyzer.getCurToken();
		boolean check = false;
		for (String s : access) {
			if (token.name.equals(s.toUpperCase())) {
				check = true;
				constructor.addChild(new Node(s));
				break;
			}
		}

		if (!check)
			Analyzer.index--;

		// check if ID = class name!
		Node idNode = Analyzer.addTerminalNode("ID");
		if (idNode == null)
			return null;
		constructor.addChild(idNode);

		Node LRoundNode = Analyzer.addTerminalNode("LEFT_ROUND_B");
		if (LRoundNode == null)
			return null;
		constructor.addChild(LRoundNode);
		
		oldIndex = Analyzer.index;
		Node parameter = Parameters.valid();
		if(parameter == null){
			parameter = new Node("Parameters");
			parameter.addChild(new Node("e"));
			Analyzer.index = oldIndex;
		}
		constructor.addChild(parameter);
				
		Node RRoundNode = Analyzer.addTerminalNode("RIGHT_ROUND_B");
		if (RRoundNode == null)
			return null;
		constructor.addChild(RRoundNode);

		Node LCurlyNode = Analyzer.addTerminalNode("LEFT_CURLY_B");
		if (LCurlyNode == null)
			return null;
		constructor.addChild(LCurlyNode);
		
		// Variables
		oldIndex = Analyzer.index;
		Node variables = new Node("Variables");
		while (true) {
			Node varDecliration = VariableDecliration.valid();
			if (varDecliration == null) {
				if (variables.children.isEmpty()) {
					variables.addChild(new Node("e"));
				}
				Analyzer.index = oldIndex;
				break;
			}
			variables.addChild(varDecliration);
			oldIndex = Analyzer.index;
		}
		constructor.addChild(variables);
		
		// TODO statements
		

		Node RCurlyNode = Analyzer.addTerminalNode("RIGHT_CURLY_B");
		if (RCurlyNode == null)
			return null;
		constructor.addChild(RCurlyNode);

		return constructor;
	}

}