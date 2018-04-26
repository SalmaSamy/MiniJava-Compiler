package SentaxAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;

public class Operators {
	
	public static Node valid(){
		ArrayList<String> operators = new ArrayList<String>(
				Arrays.asList("&&","||","!","==","!=",">","<","<=",">=","+","-","*","/"));
		
		Node operator = new Node("Operator");
		Token token = Analyzer.getCurToken();
		for(String op : operators){
			if(op.equals(token.terminal)){
				operator.addChild(new Node(op));
				return operator;
			}
		}
		return null;
	}

}
