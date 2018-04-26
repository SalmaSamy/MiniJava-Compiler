package SentaxAnalyzer;

public enum TokenType {
	
	// data types
	VOID, String, INT, BOOLEAN, CLASS, STRING, CHARACTER, DOUBLE, SHORT, ENUM, FLOAT,
	
	TRY, NULL, THROW, SWITCH, SUPER, INTERFACE, INSTANCEOF, GOTO, FOR, FINAL, FINALLY, IMPLEMENTS, DO, DEFAULT, PACKAGE, CONST, CATCH, CASE, BYTE, CONTINUE, BREAK, ASSERT, ABSTRACT, EXTENDS, NEW, STATIC, RETURN, PROTECTED, PRIVATE, PUBLIC, LENGTH, FALSE, TRUE, THIS, MAIN, IF, ELSE, WHILE,

	// brackets
	LEFT_CURLY_B, RIGHT_CURLY_B, RIGHT_SQUARE_B, LEFT_ROUND_B, RIGHT_ROUND_B,

	// values
	FLOAT_LITERAL, INTEGRAL_LITERAL, STRING_LITERAL, A_CHAR,

	// comment
	S_COMMENTS, M_COMMENTS,

	// operators
	PLUS, MINUS, MULTIPLY, DIV, EQUAL, NOT_EQUAL, ASSIGNMENT, AND, OR, GREATERTHAN_EQ, LESSTHAN_EQ, GREATERTHAN, LESSTHAN,

	//
	COMMA, DOT, NOT, SEMICOLON,

	//
	EOL,
	//
	ID, SYSTEMOUTPRINTLN, LEFT_SQUARE_B;

}
