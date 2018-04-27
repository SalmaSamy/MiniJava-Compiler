package rules;

import java.util.ArrayList;
import java.util.Arrays;

import lexical_Analyzer.Token;
import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class Type {
	private static ArrayList<TokenType> types = new ArrayList<>(
			Arrays.asList(TokenType.BOOLEAN, TokenType.STRING, TokenType.INT, TokenType.FLOAT, TokenType.CHARACTER));

	public static Node valid() {
		Token token = Parser.getCurToken();
		Node node = new Node("Type");

		for (TokenType tt : types) {
			if (tt == token.type) {
				Node terminalNode = new Node(token.value);
				node.addChild(terminalNode);
				return node;
			}
		}
		return null;
	}
}
