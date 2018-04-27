package sentax_Analyzer;

import java.util.ArrayList;

public class Node {
	private String name;
	private ArrayList<Node> children = new ArrayList<>();
	private boolean isLeaf;
	private boolean isEpsilon;
	private boolean isException;
	
	public Node(String n) {
		this.name = n;
		setException(false);
		setLeaf(true);
		setEpsilon(false);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addChild(Node node) {
		if (node.isException())
			this.setException(true);
		
		children.add(node);
		setLeaf(false);
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	public boolean isEpsilon() {
		return isEpsilon;
	}
	
	public void setEpsilon(boolean isEpsilon) {
		this.isEpsilon = isEpsilon;
	}

	public boolean isException() {
		return isException;
	}
	
	public void setException(boolean isException) {
		this.isException = isException;
	}
	public static boolean valid(Node node) {
		return !(node == null || node.isException());
	}
}