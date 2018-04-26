package SentaxAnalyzer;

public class Class {
	
	/*
	 ClassDeclaration = "class" Identifier Inheritance "{" Variables Constructors Methods "}"

	Inheritance = "extends" Identifier | e
	
	Constructors = ConstructorDeclaration Constructors | e
	Methods = MethodDeclaration Methods | e
	Variables = VarDeclaration Variables | e

	 */
	
	public static Node valid(){
		
		int oldIndex = Analyzer.index;
		Node classDecliration = new Node("ClassDecliration");
		
		Node classNode = Analyzer.addTerminalNode("CLASS");
		if(classNode == null)
			return null;
		classDecliration.addChild(classNode);
		
		Node idNode = Analyzer.addTerminalNode("ID");
		if(idNode == null)
			return null;
		classDecliration.addChild(idNode);
		
		Node inheritanceNode = Analyzer.addTerminalNode("EXTENDS");
		if(inheritanceNode == null)
			Analyzer.index--;
		else{
			classDecliration.addChild(inheritanceNode);
			
			Node inheritedClassNode = Analyzer.addTerminalNode("ID");
			if(inheritedClassNode == null)
				return null;
			classDecliration.addChild(inheritedClassNode);
		}
		
		Node LCurlyNode = Analyzer.addTerminalNode("LEFT_CURLY_B");
		if(LCurlyNode == null)
			return null;
		classDecliration.addChild(LCurlyNode);
		
		//Variables
		oldIndex = Analyzer.index;
		Node classVars = new Node("Variables");
		while(true){
			Node varDecliration = VariableDecliration.valid();
			if(varDecliration == null){
				if(classVars.children.isEmpty()){
					classVars.addChild(new Node("e"));
				}
				Analyzer.index = oldIndex;
				break;
			}
			classVars.addChild(varDecliration);
			oldIndex = Analyzer.index;
		}
		classDecliration.addChild(classVars);
		
		//Constructors
		oldIndex = Analyzer.index;
		Node constructors = new Node("Constructors");
		while(true){
			Node constructorDecliration = Constructor.valid();
			if(constructorDecliration == null){
				if(constructors.children.isEmpty()){
					constructors.addChild(new Node("e"));
				}
				Analyzer.index = oldIndex;
				break;
			}
			constructors.addChild(constructorDecliration);
			oldIndex = Analyzer.index;
		}
		classDecliration.addChild(constructors);
				
		//Methods
		oldIndex = Analyzer.index;
		Node methods = new Node("Methods");
		while(true){
			Node methodDecliration = TypeMethod.valid();
			if(methodDecliration == null){
				if(methods.children.isEmpty()){
					methods.addChild(new Node("e"));
				}
				Analyzer.index = oldIndex;
				break;
			}
			methods.addChild(methodDecliration);
			oldIndex = Analyzer.index;
		}
		classDecliration.addChild(methods);
						
		Node RCurlyNode = Analyzer.addTerminalNode("RIGHT_CURLY_B");
		if(RCurlyNode == null)
			return null;
		classDecliration.addChild(RCurlyNode);
		
		return classDecliration;
		
	}
}