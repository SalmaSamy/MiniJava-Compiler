package sentax_Analyzer;

import java.util.ArrayList;

import lexical_Analyzer.Token;
import lexical_Analyzer.TokenType;
import rules.Goal;

public class Parser {
	private static ArrayList<Token> tokens;
	public static int index = 0;

	public Parser(ArrayList<Token> t) {
		tokens = new ArrayList<Token>();

		// remove \n and comments.
		for (Token token : t) {
			if (token.type == TokenType.EOL || token.type == TokenType.S_COMMENTS || token.type == TokenType.M_COMMENTS)
				continue;

			tokens.add(token);
		}
	}

	public static Token getCurToken() {
		if (index == tokens.size()) {
			System.out.println("No more tokens");
			return null;
		}

		Token token = Parser.tokens.get(index);
		index++;
		return token;
	}

	public void parse() {
		Node root = Goal.valid();
		print(root);
	}

	// print leaf nodes only.
	public static void print(Node cur) {

		if (cur.isLeaf() && !cur.isEpsilon())
			System.out.print(cur.getName() + " ");

		for (Node node : cur.getChildren()) {
			print(node);
		}
	}

	// just for testing ..
	public static void test() {
		System.out.println(Parser.tokens.get(index).type.name());
	}

	public static Node addTerminalNode(TokenType expected) {
		Token token = Parser.getCurToken();
		// TODO Analyzer.intdex--

		if (token == null || token.type != expected)
			return null;

		Node idNode = new Node(expected.name());
		Node terminalNode = new Node(token.value);
		idNode.addChild(terminalNode);

		return idNode;
	}
}