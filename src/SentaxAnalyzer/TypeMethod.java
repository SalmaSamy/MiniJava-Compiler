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
		ArrayList<String> access = new ArrayList<>(Arrays.asList("public", "private", "protected"));

		Node method = new Node("TypeMethodDeclaration");
		Token token = Analyzer.getCurToken();
		boolean check = false;
		for (String s : access) {
			if (token.name.equals(s.toUpperCase())) {
				check = true;
				method.addChild(new Node(s));
			}
		}

		// if it doesn't exist consider it public
		if (!check)
			Analyzer.index--;

		token = Analyzer.getCurToken();
		if (token.name.equals("STATIC")) {
			method.addChild(new Node("static"));
		} else
			Analyzer.index--;

		Node typeNode = Type.valid();
		if (typeNode == null)
			return null;
		method.addChild(typeNode);

		Node idNode = Analyzer.addTerminalNode("ID");
		if (idNode == null)
			return null;

		method.addChild(idNode);

		Node Lpran = Analyzer.addTerminalNode("LEFT_ROUND_B");
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

		Node RPran = Analyzer.addTerminalNode("RIGHT_ROUND_B");
		if (RPran == null)
			return null;
		method.addChild(RPran);

		Node LCurly = Analyzer.addTerminalNode("LEFT_CURLY_B");
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

		Node returnNode = Analyzer.addTerminalNode("RETURN");
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

		Node semicolonNode = Analyzer.addTerminalNode("SEMICOLON");
		if (semicolonNode == null) {
			Analyzer.index = oldIndex;
			return null;
		}
		method.addChild(semicolonNode);

		Node RCurly = Analyzer.addTerminalNode("RIGHT_CURLY_B");
		if (RCurly == null) {
			Analyzer.index = oldIndex;
			return null;
		}
		method.addChild(RCurly);

		return method;

	}
}