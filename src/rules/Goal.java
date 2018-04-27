package rules;

import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class Goal {
	public static Node valid() {
		Node goal = new Node("Goal");

		Node mainClass = MainClass.valid();
		if (mainClass == null)
			return null;
		
		goal.addChild(mainClass);

		Node classes = new Node("Classes");
		int oldIndex = Parser.index;
		while (true) {
			Node classNode = Class.valid();
			if (classNode == null) {
				Parser.index = oldIndex;
				break;
			}
			
			classes.addChild(classNode);
			oldIndex = Parser.index;
		}
		
		goal.addChild(classes);
		return goal;
	}
}
