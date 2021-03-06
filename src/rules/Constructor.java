package rules;

import java.util.ArrayList;
import java.util.Arrays;

import lexical_Analyzer.Token;
import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class Constructor {
	
	/*
	 * ConstructorDeclaration = ("public" | "private" | "protected") Identifier
	 * "(" MethodParameters ")" "{" Variables Stms "}" 
	 */

	public static Node valid() {
		int oldIndex = Parser.index;
		Node constructor = new Node("Constructor");

		ArrayList<TokenType> accessType = new ArrayList<>(
				Arrays.asList(TokenType.PUBLIC, TokenType.PRIVATE, TokenType.PROTECTED));

		Token token = Parser.getCurToken();
		boolean check = false;
		for (TokenType tt : accessType) {
			if (token.type == tt) {
				check = true;
				constructor.addChild(new Node(tt.name()));
				break;
			}
		}

		if (!check)
			Parser.index--;

		// check if ID = class name?
		Node idNode = Parser.addTerminalNode(TokenType.ID, true);
		if (idNode == null)
			return null;
			
		constructor.addChild(idNode);

		Node LRoundNode = Parser.addTerminalNode(TokenType.LEFT_ROUND_B, false);
		constructor.addChild(LRoundNode);
		
		if (LRoundNode.isException())
			return constructor;

		oldIndex = Parser.index;
		Node parameter = Parameters.valid();
		if (parameter == null) {
			parameter = new Node("Parameters");
			parameter.setEpsilon(true);
			Parser.index = oldIndex;
		}
		constructor.addChild(parameter);
		
		if (parameter.isException())
			return constructor;

		Node RRoundNode = Parser.addTerminalNode(TokenType.RIGHT_ROUND_B, false);
		constructor.addChild(RRoundNode);
		
		if (RRoundNode.isException())
			return constructor;
		

		Node LCurlyNode = Parser.addTerminalNode(TokenType.LEFT_CURLY_B, false);
		constructor.addChild(LCurlyNode);
		
		if (LCurlyNode.isException())
			return constructor;

		// Variables
		oldIndex = Parser.index;
		Node variables = new Node("Variables");
		while (true) {
			Node varDecliration = VariableDecliration.valid();
			if (varDecliration == null) {

				if (variables.isLeaf())
					variables.setEpsilon(true);

				Parser.index = oldIndex;
				break;
			}
			variables.addChild(varDecliration);
			
			if (varDecliration.isException()) {
				constructor.addChild(variables);
				return constructor;
			}
			
			oldIndex = Parser.index;
		}
		constructor.addChild(variables);

		// Statements
		oldIndex = Parser.index;
		Node statements = new Node("Statements");
		while (true) {
			Node singleStatement = Statement.valid();
			if (singleStatement == null) {

				if (statements.isLeaf())
					statements.setEpsilon(true);

				Parser.index = oldIndex;
				break;
			}
			statements.addChild(singleStatement);
			
			if (singleStatement.isException()) {
				constructor.addChild(statements);
				return constructor;
			}
			
			oldIndex = Parser.index;
		}
		constructor.addChild(statements);

		Node RCurlyNode = Parser.addTerminalNode(TokenType.RIGHT_CURLY_B, false);
		constructor.addChild(RCurlyNode);
		
		return constructor;
	}
}