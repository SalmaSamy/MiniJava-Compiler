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

		// remove \n
		for (Token token : t) {
			if (token.type == TokenType.EOL)
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

		if (cur.isLeaf() && !cur.isEpsilon()) {
			if (cur.isException())
				System.out.println("\nException: " + cur.getName());
			else
				System.out.print(cur.getName() + " ");
			if (cur.getName().equals(";") || cur.getName().equals("}") || cur.getName().equals("{"))
				System.out.println();
		}

		for (Node node : cur.getChildren()) {
			print(node);
		}
	}

	// just for testing ..
	public static void test() {
		System.out.println(Parser.tokens.get(index).type.name());
	}

	public static Node addTerminalNode(TokenType expected, boolean optional) {
		Node idNode = new Node(expected.name());
		Token token = Parser.getCurToken();
		int cnt = 0;

		while (token != null && (token.type == TokenType.S_COMMENTS || token.type == TokenType.M_COMMENTS)) {
			idNode.addChild(new Node(token.value));
			token = Parser.getCurToken();
			++cnt;
		}

		if (token == null || token.type != expected) {
			if (optional) {
				index -= cnt;
				return null;
			}

			if (token == null) {
				Node ret = new Node("Expected More tokens");
				ret.setException(true);
				return ret;
			}

			Node ret = new Node("Expected: " + expected.name() + " found: " + token.value);
			ret.setException(true);
			return ret;
		}

		Node terminalNode = new Node(token.value);
		idNode.addChild(terminalNode);

		return idNode;
	}
}