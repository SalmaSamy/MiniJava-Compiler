package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class SysOut {
	
	//"System.out.println" "(" Expression ")" ";"

	public static Node valid() {

		Node sysout = new Node("Sysout");

		Node system = Parser.addTerminalNode(TokenType.SYSTEMOUTPRINTLN, true);
		if (system == null)
			return null;
		sysout.addChild(system);

		Node LRound = Parser.addTerminalNode(TokenType.LEFT_ROUND_B, false);
		sysout.addChild(LRound);
		
		if (LRound.isException())
			return sysout;

		Node expression = Expression.valid();
		sysout.addChild(expression);
		
		if (expression.isException())
			return sysout;
		

		Node RRound = Parser.addTerminalNode(TokenType.RIGHT_ROUND_B, false);
		sysout.addChild(RRound);
		
		if (RRound.isException())
			return sysout;
		
		Node semicolon = Parser.addTerminalNode(TokenType.SEMICOLON, false);
		sysout.addChild(semicolon);

		return sysout;
	}

}
