package rules;

import java.util.ArrayList;
import java.util.Arrays;

import lexical_Analyzer.Token;
import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class Type {
	
	//Type = "boolean" Pren |"int" Pren | "float" Pren | "String" Pren | "char" Pren

	private static ArrayList<TokenType> types = new ArrayList<>(
			Arrays.asList(TokenType.BOOLEAN, TokenType.STRING, TokenType.INT, TokenType.FLOAT, TokenType.CHARACTER));

	public static Node valid(boolean pren) {
		Token token = Parser.getCurToken();
		Node node = new Node("Type");

		for (TokenType tt : types) {
			if (tt == token.type) {
				Node typeNode = new Node(token.value);
				node.addChild(typeNode);
				
				if(pren){
					// Pren = “[]” | e
					Node LSquare = Parser.addTerminalNode(TokenType.LEFT_SQUARE_B, true);
					if (LSquare != null){
						node.addChild(LSquare);
						Node RSquare = Parser.addTerminalNode(TokenType.RIGHT_SQUARE_B, false);
						if(RSquare == null)
							return null;
						node.addChild(RSquare);
					}
					else
						Parser.index--;
				}
				return node;
			}
		}
		
		return null;
	}
}
