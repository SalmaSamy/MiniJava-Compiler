package lexical_Analyzer;

import java.util.regex.Pattern;

public class Token {
	public TokenType type;
	public Pattern pattern;
	public String value;
	
	Token() {
		
	}
	Token(TokenType type, String regex) {
		this.type = type;
		pattern = Pattern.compile(regex);
	}
}