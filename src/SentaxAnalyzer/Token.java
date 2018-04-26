package SentaxAnalyzer;

import java.util.regex.Pattern;

public class Token {
	public String name;
	public Pattern pattern;
	public String terminal;
	
	Token() {
		
	}
	Token(String n, String r) {
		name = n;
		pattern = Pattern.compile(r);
	}
}