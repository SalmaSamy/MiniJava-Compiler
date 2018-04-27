package rules;

import sentax_Analyzer.Node;

public class Goal {
	public static Node valid() {
		Node goal = new Node("Goal");

		Node mainClass = MainClass.valid();
		goal.addChild(mainClass);
		
		if (mainClass.isException())
			return goal;
			
		Node classes = new Node("Classes");
		classes.setEpsilon(true);
				
		while (true) {
			Node classNode = Class.valid();
			
			if (classNode == null)
				break;
			
			classes.addChild(classNode);
			
			if (classNode.isException()) 
				break;
		}
		
		goal.addChild(classes);
		return goal;
	}
}