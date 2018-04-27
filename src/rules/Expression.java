package rules;

import sentax_Analyzer.Node;

public class Expression {
	
	// Expression = Term OperatorSide

	public static Node valid() {
		Node expression = new Node ("Expression");
		
		Node term = Term.valid();
		expression.addChild(term);
		
		if(term.isException()) {
			return expression;
		}		

		Node operatorSide = OperatorSide.valid();
		
		if (operatorSide != null)
			expression.addChild(operatorSide);
		
		return expression;
	}

}
