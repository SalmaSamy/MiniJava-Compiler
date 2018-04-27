package rules;

import java.util.ArrayList;
import java.util.Arrays;

import lexical_Analyzer.Token;
import sentax_Analyzer.Node;
import sentax_Analyzer.Parser;

public class Operators {

	public static Node valid() {
		ArrayList<String> operators = new ArrayList<String>(
				Arrays.asList("&&", "||", "!", "==", "!=", ">", "<", "<=", ">=", "+", "-", "*", "/"));

		Node operator = new Node("Operator");
		Token token = Parser.getCurToken();
		for (String op : operators) {
			if (op.equals(token.value)) {
				operator.addChild(new Node(op));
				return operator;
			}
		}
		
		return null;
	}

}
