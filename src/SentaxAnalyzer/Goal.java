package SentaxAnalyzer;

public class Goal {
	public static Node valid() {
		Node goal = new Node("Goal");

		Node mainClass = MainClass.valid();
		if (mainClass == null)
			return null;
		
		goal.addChild(mainClass);

		Node classes = new Node("Classes");
		int oldIndex = Analyzer.index;
		while (true) {
			Node classNode = Class.valid();
			if (classNode == null) {
				Analyzer.index = oldIndex;
				break;
			}
			
			classes.addChild(classNode);
			oldIndex = Analyzer.index;
		}
		
		goal.addChild(classes);
		return goal;
	}
}
