package SentaxAnalyzer;

import java.util.ArrayList;

public class Node {
	public String name;
	public ArrayList<Node> children = new ArrayList<>();
	
	Node (String n) {
		name = n;
	}
	public static void print() {
		
	}
	public void addChild(Node node) {
		children.add(node);
	}
}
