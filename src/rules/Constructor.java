package rules;

import java.util.ArrayList;
import java.util.Arrays;

import lexical_Analyzer.Token;
import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class Constructor {

	public static Node valid() {
		int oldIndex = Parser.index;
		Node constructor = new Node("ConstructorDeclaration");

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
		Node idNode = Parser.addTerminalNode(TokenType.ID);
		if (idNode == null)
			return null;
		constructor.addChild(idNode);

		Node LRoundNode = Parser.addTerminalNode(TokenType.LEFT_ROUND_B);
		if (LRoundNode == null)
			return null;
		constructor.addChild(LRoundNode);

		oldIndex = Parser.index;
		Node parameter = Parameters.valid();
		if (parameter == null) {
			parameter = new Node("Parameters");
			parameter.setEpsilon(true);
			Parser.index = oldIndex;
		}
		constructor.addChild(parameter);

		Node RRoundNode = Parser.addTerminalNode(TokenType.RIGHT_ROUND_B);
		if (RRoundNode == null)
			return null;
		constructor.addChild(RRoundNode);

		Node LCurlyNode = Parser.addTerminalNode(TokenType.LEFT_CURLY_B);
		if (LCurlyNode == null)
			return null;
		constructor.addChild(LCurlyNode);

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
			oldIndex = Parser.index;
		}
		constructor.addChild(statements);

		Node RCurlyNode = Parser.addTerminalNode(TokenType.RIGHT_CURLY_B);
		if (RCurlyNode == null)
			return null;
		constructor.addChild(RCurlyNode);

		return constructor;
	}

}