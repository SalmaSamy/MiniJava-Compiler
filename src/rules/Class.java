package rules;

import lexical_Analyzer.TokenType;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class Class {

	/*
	 * ClassDeclaration = "class" Identifier Inheritance "{" Variables
	 * Constructors Methods "}"
	 * 
	 * Inheritance = "extends" Identifier | e
	 * 
	 * Constructors = ConstructorDeclaration Constructors | e Methods =
	 * MethodDeclaration Methods | e Variables = VarDeclaration Variables | e
	 * 
	 */

	public static Node valid() {

		int oldIndex = Parser.index;
		Node classDecliration = new Node("ClassDecliration");

		Node classNode = Parser.addTerminalNode(TokenType.CLASS);
		if (classNode == null)
			return null;
		classDecliration.addChild(classNode);

		Node idNode = Parser.addTerminalNode(TokenType.ID);
		if (idNode == null)
			return null;
		classDecliration.addChild(idNode);

		Node inheritanceNode = Parser.addTerminalNode(TokenType.EXTENDS);
		if (inheritanceNode == null)
			Parser.index--;
		else {
			classDecliration.addChild(inheritanceNode);

			Node inheritedClassNode = Parser.addTerminalNode(TokenType.ID);
			if (inheritedClassNode == null)
				return null;
			classDecliration.addChild(inheritedClassNode);
		}

		Node LCurlyNode = Parser.addTerminalNode(TokenType.LEFT_CURLY_B);
		if (LCurlyNode == null)
			return null;
		classDecliration.addChild(LCurlyNode);

		// Variables
		oldIndex = Parser.index;
		Node classVars = new Node("Variables");
		while (true) {
			Node varDecliration = VariableDecliration.valid();

			if (varDecliration == null) {

				if (classVars.isLeaf())
					classVars.setEpsilon(true);

				Parser.index = oldIndex;
				break;
			}

			classVars.addChild(varDecliration);
			oldIndex = Parser.index;
		}
		classDecliration.addChild(classVars);

		// Constructors
		oldIndex = Parser.index;
		Node constructors = new Node("Constructors");
		while (true) {
			Node constructorDecliration = Constructor.valid();
			if (constructorDecliration == null) {

				if (constructors.isLeaf())
					constructors.setEpsilon(true);

				Parser.index = oldIndex;
				break;
			}
			constructors.addChild(constructorDecliration);
			oldIndex = Parser.index;
		}
		classDecliration.addChild(constructors);

		// Methods
		oldIndex = Parser.index;
		Node methods = new Node("Methods");
		while (true) {
			Node methodDecliration = TypeMethod.valid();
			if (methodDecliration == null) {

				if (methods.isLeaf())
					methods.setEpsilon(true);

				Parser.index = oldIndex;
				break;
			}
			methods.addChild(methodDecliration);
			oldIndex = Parser.index;
		}
		classDecliration.addChild(methods);

		Node RCurlyNode = Parser.addTerminalNode(TokenType.RIGHT_CURLY_B);
		if (RCurlyNode == null)
			return null;
		classDecliration.addChild(RCurlyNode);

		return classDecliration;

	}
}