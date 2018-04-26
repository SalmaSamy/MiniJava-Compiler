package SentaxAnalyzer;

public class SysOut {
	
	//"System.out.println" "(" Expression ")" ";"

	public static Node valid() {

		Node sysout = new Node("Sysout");

		Node system = Analyzer.addTerminalNode("SYSTEM.OUT.PRINTLN");
		if (system == null)
			return null;
		sysout.addChild(system);

		Node LRound = Analyzer.addTerminalNode("LEFT_ROUND_B");
		if (LRound == null)
			return null;
		sysout.addChild(LRound);

		Node expression = Expression.valid();
		if (expression == null)
			return null;
		sysout.addChild(expression);

		Node RRound = Analyzer.addTerminalNode("RIGHT_ROUND_B");
		if (RRound == null)
			return null;
		sysout.addChild(RRound);

		Node semicolon = Analyzer.addTerminalNode("SEMICOLON");
		if (semicolon == null)
			return null;
		sysout.addChild(semicolon);

		return sysout;
	}

}
