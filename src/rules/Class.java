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
		Node classDecliration = new Node("Class");

		Node classNode = Parser.addTerminalNode(TokenType.CLASS, true);
		if (classNode == null) {
			return null;
		}

		classDecliration.addChild(classNode);

		Node idNode = Parser.addTerminalNode(TokenType.ID, false);

		classDecliration.addChild(idNode);

		if (idNode.isException())
			return classDecliration;

		Node inheritanceNode = Parser.addTerminalNode(TokenType.EXTENDS, true);
		if (inheritanceNode == null)
			Parser.index--;
		else {
			classDecliration.addChild(inheritanceNode);

			Node inheritedClassNode = Parser.addTerminalNode(TokenType.ID, false);

			classDecliration.addChild(inheritedClassNode);
			if (inheritedClassNode.isException())
				return classDecliration;
		}

		Node LCurlyNode = Parser.addTerminalNode(TokenType.LEFT_CURLY_B, false);
		classDecliration.addChild(LCurlyNode);

		if (LCurlyNode.isException())
			return classDecliration;

		// Variables
		Node classVars = new Node("Variables");
		classVars.setEpsilon(true);

		oldIndex = Parser.index;
		while (true) {
			Node varDecliration = VariableDecliration.valid();

			if (varDecliration == null) {

				Parser.index = oldIndex;
				break;
			}

			classVars.addChild(varDecliration);

			if (varDecliration.isException()) {
				classDecliration.addChild(classVars);
				return classDecliration;
			}

			classVars.setEpsilon(false);
			oldIndex = Parser.index;
		}

		classDecliration.addChild(classVars);

		// Constructors
		oldIndex = Parser.index;
		Node constructors = new Node("Constructors");
		constructors.setEpsilon(true);

		while (true) {
			Node constructorDecliration = Constructor.valid();
			if (constructorDecliration == null) {
				Parser.index = oldIndex;
				break;
			}
			constructors.addChild(constructorDecliration);

			if (constructorDecliration.isEpsilon()) {
				classDecliration.addChild(constructorDecliration);
				return classDecliration;
			}
			oldIndex = Parser.index;
		}
		classDecliration.addChild(constructors);

		// Methods

		Node methods = new Node("Methods");
		methods.setEpsilon(true);

		oldIndex = Parser.index;
		while (true) {
			Node methodDecliration = TypeMethod.valid();
			if (methodDecliration == null) {
				Parser.index = oldIndex;
				break;
			}

			methods.addChild(methodDecliration);

			if (methodDecliration.isException()) {
				classDecliration.addChild(methods);
				return classDecliration;
			}

			oldIndex = Parser.index;
		}

		classDecliration.addChild(methods);

		Node RCurlyNode = Parser.addTerminalNode(TokenType.RIGHT_CURLY_B, false);
		classDecliration.addChild(RCurlyNode);

		return classDecliration;
	}
}