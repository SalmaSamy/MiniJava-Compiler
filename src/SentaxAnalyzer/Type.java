package SentaxAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;

public class Type {
	private static ArrayList<String> types =
			new ArrayList<String>(Arrays.asList( "Boolean", "String", "int", "float", "char" ));
	
	public static Node valid() {
		Token token = Analyzer.getCurToken();
		Node node = new Node("Type");
		
		for (String s: types) {
			s = s.toLowerCase();
			if (s.equals(token.name.toLowerCase())) {
				Node terminalNode = new Node(token.terminal);
				node.addChild(terminalNode);
				return node;
			}
		}
		return null;
	}
}
