package SentaxAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;

public class Type {
	private static ArrayList<TokenType> types = new ArrayList<>(
			Arrays.asList(TokenType.BOOLEAN, TokenType.STRING, TokenType.INT, TokenType.FLOAT, TokenType.CHARACTER));

	public static Node valid() {
		Token token = Analyzer.getCurToken();
		Node node = new Node("Type");

		for (TokenType tt : types) {
			if (tt == token.type) {
				Node terminalNode = new Node(token.terminal);
				node.addChild(terminalNode);
				return node;
			}
		}
		return null;
	}
}
