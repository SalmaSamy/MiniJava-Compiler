package SentaxAnalyzer;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class Tokenizer {

	private static ArrayList<Token> tokens;

	Tokenizer() {
		tokens = new ArrayList<Token>();
		initialize_tokens();
	}

	public static ArrayList<Token> execute(String code) {
		int index = 0;
		int len = code.length();

		ArrayList<Token> outTokens = new ArrayList<>();

		while (index < len) {

			int startMatch = len;
			int endMatch = len;

			TokenType patternName = null;

			for (Token token : tokens) {
				Matcher matcher = token.pattern.matcher(code);

				if (matcher.find(index)) {
					int matcherStart = matcher.start();
					if (matcherStart < startMatch) {
						patternName = token.type;
						startMatch = matcherStart;
						endMatch = matcher.end();
					}
				}
			}
			while (index < len && (code.charAt(index) == ' ' || code.charAt(index) == '\t'))
				++index;

			if (startMatch > index) {
				String unknown = code.substring(index, startMatch);
				System.out.println("Error unknown token " + unknown);
			}
			if (startMatch == len)
				break;

			String token = code.substring(startMatch, endMatch);
			// System.out.println("<" + patternName.name() + ">: " + token);

			Token t = new Token();
			t.type = patternName;
			t.terminal = token;
			if (!(t.type == TokenType.EOL || t.type == TokenType.S_COMMENTS || t.type == TokenType.M_COMMENTS))
				outTokens.add(t);

			index = endMatch;
		}

		return outTokens;
	}

	private static void initialize_tokens() {

		// words
		tokens.add(new Token(TokenType.TRY, "\\btry\\b"));
		tokens.add(new Token(TokenType.NULL, "\\bnull\\b"));
		tokens.add(new Token(TokenType.THROW, "\\bthrow\\b"));
		tokens.add(new Token(TokenType.SWITCH, "\\bswitch\\b"));
		tokens.add(new Token(TokenType.SUPER, "\\bsuper\\b"));
		tokens.add(new Token(TokenType.INTERFACE, "\\binterface\\b"));
		tokens.add(new Token(TokenType.INSTANCEOF, "\\binstanceof\\b"));
		tokens.add(new Token(TokenType.GOTO, "\\bgoto\\b"));
		tokens.add(new Token(TokenType.FOR, "\\bfor\\b"));
		tokens.add(new Token(TokenType.FINAL, "\\bfinal\\b"));
		tokens.add(new Token(TokenType.FINALLY, "\\bfinally\\b"));
		tokens.add(new Token(TokenType.IMPLEMENTS, "\\bimplements\\b"));
		tokens.add(new Token(TokenType.DO, "\\bdo\\b"));
		tokens.add(new Token(TokenType.DEFAULT, "\\bdefault\\b"));
		tokens.add(new Token(TokenType.PACKAGE, "\\bpackage\\b"));
		tokens.add(new Token(TokenType.CONST, "\\bconst\\b"));
		tokens.add(new Token(TokenType.CATCH, "\\bcatch\\b"));
		tokens.add(new Token(TokenType.CASE, "\\bcase\\b"));
		tokens.add(new Token(TokenType.BYTE, "\\bbyte\\b"));
		tokens.add(new Token(TokenType.CONTINUE, "\\bcontinue\\b"));
		tokens.add(new Token(TokenType.BREAK, "\\bbreak\\b"));
		tokens.add(new Token(TokenType.ASSERT, "\\bassert\\b"));
		tokens.add(new Token(TokenType.ABSTRACT, "\\babstract\\b"));
		tokens.add(new Token(TokenType.EXTENDS, "\\bextends\\b"));
		tokens.add(new Token(TokenType.NEW, "\\bnew\\b"));
		tokens.add(new Token(TokenType.STATIC, "\\bstatic\\b"));
		tokens.add(new Token(TokenType.RETURN, "\\breturn\\b"));
		tokens.add(new Token(TokenType.PROTECTED, "\\bprotected\\b"));
		tokens.add(new Token(TokenType.PRIVATE, "\\bprivate\\b"));
		tokens.add(new Token(TokenType.PUBLIC, "\\bpublic\\b"));
		tokens.add(new Token(TokenType.LENGTH, "\\blength\\b"));
		tokens.add(new Token(TokenType.WHILE, "\\bwhile\\b"));
		tokens.add(new Token(TokenType.FALSE, "\\bfalse\\b"));
		tokens.add(new Token(TokenType.VOID, "\\bvoid\\b"));
		tokens.add(new Token(TokenType.TRUE, "\\btrue\\b"));
		tokens.add(new Token(TokenType.THIS, "\\bthis\\b"));
		tokens.add(new Token(TokenType.MAIN, "\\bmain\\b"));
		tokens.add(new Token(TokenType.ELSE, "\\belse\\b"));
		tokens.add(new Token(TokenType.IF, "\\bif\\b"));
		tokens.add(new Token(TokenType.SYSTEMOUTPRINTLN, "\\bSystem.out.println\\b"));

		// data types
		tokens.add(new Token(TokenType.FLOAT, "\\bfloat\\b"));
		tokens.add(new Token(TokenType.STRING, "\\bString\\b"));
		tokens.add(new Token(TokenType.CHARACTER, "\\bchar\\b"));
		tokens.add(new Token(TokenType.BOOLEAN, "\\bboolean\\b"));
		tokens.add(new Token(TokenType.DOUBLE, "\\bdouble\\b"));
		tokens.add(new Token(TokenType.SHORT, "\\bshort\\b"));
		tokens.add(new Token(TokenType.ENUM, "\\bENUM\\b"));
		tokens.add(new Token(TokenType.CLASS, "\\bclass\\b"));
		tokens.add(new Token(TokenType.INT, "\\bint\\b"));

		// brackets
		tokens.add(new Token(TokenType.LEFT_CURLY_B, "\\{"));
		tokens.add(new Token(TokenType.RIGHT_CURLY_B, "\\}"));
		tokens.add(new Token(TokenType.LEFT_SQUARE_B, "\\["));
		tokens.add(new Token(TokenType.RIGHT_SQUARE_B, "\\]"));
		tokens.add(new Token(TokenType.LEFT_ROUND_B, "\\("));
		tokens.add(new Token(TokenType.RIGHT_ROUND_B, "\\)"));

		// values
		tokens.add(new Token(TokenType.FLOAT_LITERAL, "[0-9]*[\\.]{1}[0-9]+|[0-9]+[\\.]{1}[0-9]*"));
		tokens.add(new Token(TokenType.INTEGRAL_LITERAL, "\\b[0-9]+\\b"));
		tokens.add(new Token(TokenType.STRING_LITERAL, "\".*\""));
		tokens.add(new Token(TokenType.A_CHAR, "['].[']"));

		// comment
		tokens.add(new Token(TokenType.S_COMMENTS, "//.*"));
		tokens.add(new Token(TokenType.M_COMMENTS, "\\/\\*(.|\\n)+?\\*\\/"));

		// operators
		tokens.add(new Token(TokenType.PLUS, "\\+"));
		tokens.add(new Token(TokenType.MINUS, "\\-"));
		tokens.add(new Token(TokenType.MULTIPLY, "\\*"));
		tokens.add(new Token(TokenType.DIV, "/"));
		tokens.add(new Token(TokenType.EQUAL, "\\=="));
		tokens.add(new Token(TokenType.NOT_EQUAL, "\\!="));
		tokens.add(new Token(TokenType.ASSIGNMENT, "\\="));
		tokens.add(new Token(TokenType.AND, "\\&&"));
		tokens.add(new Token(TokenType.OR, "\\|\\|"));
		tokens.add(new Token(TokenType.GREATERTHAN_EQ, "\\>="));
		tokens.add(new Token(TokenType.LESSTHAN_EQ, "\\<="));
		tokens.add(new Token(TokenType.LESSTHAN, "\\<"));
		tokens.add(new Token(TokenType.GREATERTHAN, "\\>"));

		// punctuation
		tokens.add(new Token(TokenType.COMMA, "\\,"));
		tokens.add(new Token(TokenType.DOT, "\\."));
		tokens.add(new Token(TokenType.NOT, "\\!"));
		tokens.add(new Token(TokenType.SEMICOLON, "\\;"));

		// EOL
		tokens.add(new Token(TokenType.EOL, "\\n"));

		// ID
		tokens.add(new Token(TokenType.ID, "\\b[a-zA-Z_][a-zA-Z_$0-9]*"));

	}
}