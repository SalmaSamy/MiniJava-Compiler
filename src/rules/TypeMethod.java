package rules;

import java.util.ArrayList;
import java.util.Arrays;

import lexical_Analyzer.Token;
import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class TypeMethod {

	// TypeMethodDeclaration = ("public" | "private" | "protected")
	// Static Type Identifier "(" MethodsParameters ")"
	// "{" Variables Statements "return" Expression ";" "}"

	public static Node valid() {

		int oldIndex = Parser.index;
		ArrayList<TokenType> accessType = new ArrayList<>(
				Arrays.asList(TokenType.PUBLIC, TokenType.PRIVATE, TokenType.PROTECTED));

		Node method = new Node("TypeMethodDeclaration");
		Token token = Parser.getCurToken();
		boolean check = false;
		for (TokenType tt : accessType) {
			if (token.type == tt) {
				check = true;
				method.addChild(new Node(tt.name()));
			}
		}

		// if it doesn't exist consider it public
		if (!check)
			Parser.index--;

		token = Parser.getCurToken();
		if (token.type == TokenType.STATIC) {
			method.addChild(new Node(TokenType.STATIC.name()));
		} else
			Parser.index--;

		Node typeNode = Type.valid();
		if (typeNode == null)
			return null;
		method.addChild(typeNode);

		Node idNode = Parser.addTerminalNode(TokenType.ID);
		if (idNode == null)
			return null;

		method.addChild(idNode);

		Node Lpran = Parser.addTerminalNode(TokenType.LEFT_ROUND_B);
		if (Lpran == null)
			return null;
		method.addChild(Lpran);

		oldIndex = Parser.index;
		Node parameters = Parameters.valid();
		if (parameters == null) {
			parameters = new Node("Paramters");
			
			parameters.setEpsilon(true);
			Parser.index = oldIndex;
		}
		method.addChild(parameters);

		Node RPran = Parser.addTerminalNode(TokenType.RIGHT_ROUND_B);
		if (RPran == null)
			return null;
		method.addChild(RPran);

		Node LCurly = Parser.addTerminalNode(TokenType.LEFT_CURLY_B);
		if (LCurly == null)
			return null;
		method.addChild(LCurly);

		// Variables
		oldIndex = Parser.index;
		Node variables = new Node("Variables");
		while (true) {
			Node varDecliration = VariableDecliration.valid();
			if (varDecliration == null) {
				if (variables.isLeaf()) {
					variables.addChild(new Node("e"));
				}
				Parser.index = oldIndex;
				break;
			}
			variables.addChild(varDecliration);
			oldIndex = Parser.index;
		}
		method.addChild(variables);

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
		method.addChild(statements);

		Node returnNode = Parser.addTerminalNode(TokenType.RETURN);
		if (returnNode == null) {
			Parser.index = oldIndex;
			return null;
		}
		method.addChild(returnNode);

		Node expression = Expression.valid();
		if (expression == null) {
			Parser.index = oldIndex;
			return null;
		}
		method.addChild(expression);

		Node semicolonNode = Parser.addTerminalNode(TokenType.SEMICOLON);
		if (semicolonNode == null) {
			Parser.index = oldIndex;
			return null;
		}
		method.addChild(semicolonNode);

		Node RCurly = Parser.addTerminalNode(TokenType.RIGHT_CURLY_B);
		if (RCurly == null) {
			Parser.index = oldIndex;
			return null;
		}
		method.addChild(RCurly);

		return method;

	}
}