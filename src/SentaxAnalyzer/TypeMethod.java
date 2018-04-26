package SentaxAnalyzer;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;

public class TypeMethod {

	// TypeMethodDeclaration = ("public" | "private" | "protected")
	// Static Type Identifier "(" MethodsParameters ")"
	// "{" Variables Statements "return" Expression ";" "}"

	public static Node valid() {

		int oldIndex = Analyzer.index;
		ArrayList<TokenType> accessType = new ArrayList<>(
				Arrays.asList(TokenType.PUBLIC, TokenType.PRIVATE, TokenType.PROTECTED));

		Node method = new Node("TypeMethodDeclaration");
		Token token = Analyzer.getCurToken();
		boolean check = false;
		for (TokenType tt : accessType) {
			if (token.type == tt) {
				check = true;
				method.addChild(new Node(tt.name()));
			}
		}

		// if it doesn't exist consider it public
		if (!check)
			Analyzer.index--;

		token = Analyzer.getCurToken();
		if (token.type == TokenType.STATIC) {
			method.addChild(new Node(TokenType.STATIC.name()));
		} else
			Analyzer.index--;

		Node typeNode = Type.valid();
		if (typeNode == null)
			return null;
		method.addChild(typeNode);

		Node idNode = Analyzer.addTerminalNode(TokenType.ID);
		if (idNode == null)
			return null;

		method.addChild(idNode);

		Node Lpran = Analyzer.addTerminalNode(TokenType.LEFT_ROUND_B);
		if (Lpran == null)
			return null;
		method.addChild(Lpran);

		oldIndex = Analyzer.index;
		Node parameters = Parameters.valid();
		if (parameters == null) {
			parameters = new Node("Paramters");
			parameters.addChild(new Node("e"));
			Analyzer.index = oldIndex;
		}
		method.addChild(parameters);

		Node RPran = Analyzer.addTerminalNode(TokenType.RIGHT_ROUND_B);
		if (RPran == null)
			return null;
		method.addChild(RPran);

		Node LCurly = Analyzer.addTerminalNode(TokenType.LEFT_CURLY_B);
		if (LCurly == null)
			return null;
		method.addChild(LCurly);

		// Variables
		oldIndex = Analyzer.index;
		Node variables = new Node("Variables");
		while (true) {
			Node varDecliration = VariableDecliration.valid();
			if (varDecliration == null) {
				if (variables.children.isEmpty()) {
					variables.addChild(new Node("e"));
				}
				Analyzer.index = oldIndex;
				break;
			}
			variables.addChild(varDecliration);
			oldIndex = Analyzer.index;
		}
		method.addChild(variables);

		// Statements
		oldIndex = Analyzer.index;
		Node statements = new Node("Statements");
		while (true) {
			Node singleStatement = Statement.valid();
			if (singleStatement == null) {
				if (statements.children.isEmpty()) {
					statements.addChild(new Node("e"));
				}
				Analyzer.index = oldIndex;
				break;
			}
			statements.addChild(singleStatement);
			oldIndex = Analyzer.index;
		}
		method.addChild(statements);

		Node returnNode = Analyzer.addTerminalNode(TokenType.RETURN);
		if (returnNode == null) {
			Analyzer.index = oldIndex;
			return null;
		}
		method.addChild(returnNode);

		Node expression = Expression.valid();
		if (expression == null) {
			Analyzer.index = oldIndex;
			return null;
		}
		method.addChild(expression);

		Node semicolonNode = Analyzer.addTerminalNode(TokenType.SEMICOLON);
		if (semicolonNode == null) {
			Analyzer.index = oldIndex;
			return null;
		}
		method.addChild(semicolonNode);

		Node RCurly = Analyzer.addTerminalNode(TokenType.RIGHT_CURLY_B);
		if (RCurly == null) {
			Analyzer.index = oldIndex;
			return null;
		}
		method.addChild(RCurly);

		return method;

	}
}