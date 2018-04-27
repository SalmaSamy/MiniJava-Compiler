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

		ArrayList<TokenType> accessType = new ArrayList<>(
				Arrays.asList(TokenType.PUBLIC, TokenType.PRIVATE, TokenType.PROTECTED));

		Node method = new Node("TypeMethodDeclaration");
		Token token = Parser.getCurToken();

		boolean check = false;
		for (TokenType tt : accessType) {
			if (token.type == tt) {
				check = true;
				method.addChild(new Node(tt.name().toLowerCase()));
			}
		}

		// if it doesn't exist consider it public
		if (!check)
			Parser.index--;

		token = Parser.getCurToken();
		if (token.type == TokenType.STATIC) {
			method.addChild(new Node(TokenType.STATIC.name().toLowerCase()));
		} else
			Parser.index--;

		Node typeNode = Type.valid();
		if (typeNode == null)
			return null;

		method.addChild(typeNode);

		Node idNode = Parser.addTerminalNode(TokenType.ID, false);
		method.addChild(idNode);

		if (idNode.isException())
			return method;

		Node Lpran = Parser.addTerminalNode(TokenType.LEFT_ROUND_B, false);
		method.addChild(Lpran);

		if (Lpran.isException())
			return method;

		Node parameters = Parameters.valid();
		method.addChild(parameters);

		if (parameters.isException()) {
			System.out.println("Y");

			return method;

		}

		Node RPran = Parser.addTerminalNode(TokenType.RIGHT_ROUND_B, false);
		method.addChild(RPran);

		if (RPran.isException())
			return method;

		Node LCurly = Parser.addTerminalNode(TokenType.LEFT_CURLY_B, false);
		method.addChild(LCurly);

		if (LCurly.isException())
			return method;

		// Variables
		int oldIndex = Parser.index;
		Node variables = new Node("Variables");
		variables.setEpsilon(true);

		while (true) {
			Node varDecliration = VariableDecliration.valid();
			if (varDecliration == null) {
				Parser.index = oldIndex;
				break;
			}
			variables.addChild(varDecliration);

			if (varDecliration.isException()) {
				method.addChild(variables);
				return method;
			}

			oldIndex = Parser.index;
		}
		method.addChild(variables);

		// Statements
		oldIndex = Parser.index;
		Node statements = new Node("Statements");
		statements.setEpsilon(true);

		while (true) {
			Node singleStatement = Statement.valid();
			if (singleStatement == null) {
				Parser.index = oldIndex;
				break;
			}

			statements.addChild(singleStatement);

			if (singleStatement.isException()) {
				method.addChild(statements);
				return method;
			}

			oldIndex = Parser.index;
		}
		method.addChild(statements);

		Node returnNode = Parser.addTerminalNode(TokenType.RETURN, false);
		method.addChild(returnNode);

		if (returnNode.isException()) {
			Parser.index = oldIndex;
			return method;
		}

		Node expression = Expression.valid();
		method.addChild(expression);

		if (expression.isException()) {
			Parser.index = oldIndex;
			return method;
		}

		Node semicolonNode = Parser.addTerminalNode(TokenType.SEMICOLON, false);
		method.addChild(semicolonNode);

		if (semicolonNode.isException()) {
			Parser.index = oldIndex;
			return method;
		}

		Node RCurly = Parser.addTerminalNode(TokenType.RIGHT_CURLY_B, false);
		method.addChild(RCurly);

		if (RCurly.isException())
			Parser.index = oldIndex;

		return method;

	}
}