package rules;

import sentax_Analyzer.Node;

public class Expression {
	
	// Expression = Term OperatorSide

	public static Node valid() {
		Node expression = new Node ("Expression");
		
		Node term = Term.valid();
		if(term == null)
			return null;

		expression.addChild(term);
		
		Node operatorSide = OperatorSide.valid();
		if(operatorSide == null)
			return null;
		expression.addChild(operatorSide);
		
		return expression;
	}

}
