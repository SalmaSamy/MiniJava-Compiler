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
					
			String patternName = "";
			
			for (Token token : tokens) {
				Matcher matcher = token.pattern.matcher(code);
	
				if (matcher.find(index)) {
					int matcherStart = matcher.start();
					if (matcherStart < startMatch) {
						patternName = token.name;
						startMatch = matcherStart;
						endMatch = matcher.end();
					}
				}
			}
			while (index < len && (code.charAt(index) == ' '||code.charAt(index) == '\t'))
				++index;
			
			if (startMatch > index) {
				String unknown = code.substring(index, startMatch);
				System.out.println("Error unknown token " + unknown);
			}
			if (startMatch == len)
				break;
			
			String token = code.substring(startMatch, endMatch);
			//System.out.println("<" + patternName + ">: " + token);
			
			Token t = new Token();
			t.name = patternName;
			t.terminal = token;
			if(!t.name.equals("EOL"))
				outTokens.add(t);
			
			index = endMatch;
		}
		
		return outTokens;
	}

	private static void initialize_tokens() {
		
		// words
		tokens.add(new Token("TRY", "\\btry\\b"));
		tokens.add(new Token("NULL", "\\bnull\\b"));
		tokens.add(new Token("THROW", "\\bthrow\\b"));
		tokens.add(new Token("SWITCH", "\\bswitch\\b"));
		tokens.add(new Token("SUPER", "\\bsuper\\b"));
		tokens.add(new Token("INTERFACE", "\\binterface\\b"));
		tokens.add(new Token("INSTANCEOF", "\\binstanceof\\b"));
		tokens.add(new Token("GOTO", "\\bgoto\\b"));
		tokens.add(new Token("FOR", "\\bfor\\b"));
		tokens.add(new Token("FINAL", "\\bfinal\\b"));
		tokens.add(new Token("FINALLY", "\\bfinally\\b"));
		tokens.add(new Token("IMPLEMENTS", "\\bimplements\\b"));
		tokens.add(new Token("DO", "\\bdo\\b"));
		tokens.add(new Token("DEFAULT", "\\bdefault\\b"));
		tokens.add(new Token("PACKAGE", "\\bpackage\\b"));
		tokens.add(new Token("CONST", "\\bconst\\b"));
		tokens.add(new Token("CATCH", "\\bcatch\\b"));
		tokens.add(new Token("CASE", "\\bcase\\b"));
		tokens.add(new Token("BYTE", "\\bbyte\\b"));
		tokens.add(new Token("CONTINUE", "\\bcontinue\\b"));
		tokens.add(new Token("BREAK", "\\bbreak\\b"));
		tokens.add(new Token("ASSERT", "\\bassert\\b"));
		tokens.add(new Token("ABSTRACT", "\\babstract\\b"));
		tokens.add(new Token("EXTENDS", "\\bextends\\b"));
		tokens.add(new Token("NEW", "\\bnew\\b"));
		tokens.add(new Token("STATIC", "\\bstatic\\b"));
		tokens.add(new Token("RETURN", "\\breturn\\b"));
		tokens.add(new Token("PROTECTED", "\\bprotected\\b"));
		tokens.add(new Token("PRIVATE", "\\bprivate\\b"));
		tokens.add(new Token("PUBLIC", "\\bpublic\\b"));
		tokens.add(new Token("LENGTH", "\\blength\\b"));
		tokens.add(new Token("WHILE", "\\bwhile\\b"));
		tokens.add(new Token("FALSE", "\\bfalse\\b"));
		tokens.add(new Token("VOID", "\\bvoid\\b"));
		tokens.add(new Token("TRUE", "\\btrue\\b"));
		tokens.add(new Token("THIS", "\\bthis\\b"));
		tokens.add(new Token("MAIN", "\\bmain\\b"));
		tokens.add(new Token("ELSE", "\\belse\\b"));
		tokens.add(new Token("IF", "\\bif\\b"));
		tokens.add(new Token("SYSTEM.OUT.PRINTLN", "\\bSystem.out.println\\b"));

		// data types
		tokens.add(new Token("FLOAT", "\\bfloat\\b"));
		tokens.add(new Token("STRING", "\\bString\\b"));
		tokens.add(new Token("CHARACTER", "\\bchar\\b"));
		tokens.add(new Token("BOOLEAN", "\\bboolean\\b"));
		tokens.add(new Token("DOUBLE", "\\bdouble\\b"));
		tokens.add(new Token("SHORT", "\\bshort\\b"));
		tokens.add(new Token("ENUM", "\\bENUM\\b"));
		tokens.add(new Token("CLASS", "\\bclass\\b"));
		tokens.add(new Token("INT", "\\bint\\b"));

		// brackets
		tokens.add(new Token("LEFT_CURLY_B", "\\{"));
		tokens.add(new Token("RIGHT_CURLY_B", "\\}"));
		tokens.add(new Token("LEFT_SQUARE_B", "\\["));
		tokens.add(new Token("RIGHT_SQUARE_B", "\\]"));
		tokens.add(new Token("LEFT_ROUND_B", "\\("));
		tokens.add(new Token("RIGHT_ROUND_B", "\\)"));
		
		
		// values
		tokens.add(new Token("FLOAT_LITERAL", "[0-9]*[\\.]{1}[0-9]+|[0-9]+[\\.]{1}[0-9]*"));
		tokens.add(new Token("INTEGRAL_LITERAL", "\\b[0-9]+\\b"));
		tokens.add(new Token("STRING_LITERAL", "\".*\""));
		tokens.add(new Token("A_CHAR", "['].[']"));
		
		// comment
		tokens.add(new Token("S_COMMENTS", "//.*"));
		tokens.add(new Token("M_COMMENTS", "\\/\\*(.|\\n)+?\\*\\/"));
		
		// operators
		tokens.add(new Token("PLUS", "\\+"));
		tokens.add(new Token("MINUS", "\\-"));
		tokens.add(new Token("MULTIPLY", "\\*"));
		tokens.add(new Token("DIV", "/"));
		tokens.add(new Token("EQUAL", "\\=="));
		tokens.add(new Token("NOT_EQUAL", "\\!="));
		tokens.add(new Token("ASSIGNMENT", "\\="));
		tokens.add(new Token("AND", "\\&&"));
		tokens.add(new Token("OR", "\\|\\|"));
		tokens.add(new Token("GREATERTHAN_EQ", "\\>="));
		tokens.add(new Token("LESSTHAN_EQ", "\\<="));
		tokens.add(new Token("LESSTHAN", "\\<"));
		tokens.add(new Token("GREATERTHAN", "\\>"));
		
		// punctuation
		tokens.add(new Token("COMMA", "\\,"));
		tokens.add(new Token("DOT", "\\."));
		tokens.add(new Token("NOT", "\\!"));
		tokens.add(new Token("SEMICOLON", "\\;"));
		
		// EOL
		tokens.add(new Token("EOL", "\\n"));
		
		// ID
		tokens.add(new Token("ID", "\\b[a-zA-Z_][a-zA-Z_$0-9]*"));
	
	}
}