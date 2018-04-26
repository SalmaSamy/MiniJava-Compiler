package SentaxAnalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Analyzer {
	public static ArrayList<Token> tokens;
	public static int index = 0;
	
	public Analyzer(ArrayList<Token> t) {
		tokens = t;
	}
	
	public static Token getCurToken(){
		if(index == tokens.size()){
			System.out.println("No more tokens");
			return null;
		}
		Token token = Analyzer.tokens.get(index);
		index++;
		return token;
	}
	
	public static void parse() {
		Node root = Goal.valid();
		print(root);
	}
	
	public static void print(Node cur){
		
		if(cur.children.size() == 0 && !cur.name.equals("e"))
			System.out.print(cur.name+" ");
		for(Node node : cur.children){
			print(node);
		}
	}
	
	public static void test () {
		System.out.println(Analyzer.tokens.get(index).name);
	}
	
	public static void cout(int num) {
		System.out.println("Seif and Salma " + num);
	}
	
	public static Node addTerminalNode(String terminal){
		Token token = Analyzer.getCurToken();
		//TODO Analyzer.intdex--
		if (token == null || !token.name.equals(terminal))
			return null;
	
		Node idNode = new Node(terminal);
		Node terminalNode = new Node(token.terminal);
		idNode.addChild(terminalNode);
		
		return idNode;
	}
	
}
