package sentax_Analyzer;

import java.util.ArrayList;

public class Node {
	private String name;
	private ArrayList<Node> children = new ArrayList<>();
	private boolean isLeaf;
	private boolean isEpsilon;
	

	public Node(String n) {
		this.name = n;
		
		setLeaf(true);
		setEpsilon(false);
	}

	public String getName() {
		return name;
	}
	
	public void addChild(Node node) {
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
}