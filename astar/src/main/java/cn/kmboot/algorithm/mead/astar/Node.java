package cn.kmboot.algorithm.mead.astar;

import lombok.Data;

@Data
public class Node implements Comparable<Node> {
	private int row;
	private int col;
	private NodeType type;
	private Node parent;
	private int step;
	private int valueG = Integer.MAX_VALUE;
	private int valueH = Integer.MAX_VALUE;
	private int valueF = Integer.MAX_VALUE;

	public Node() {
		//
	}

	public Node(int r, int c) {
		this.row = r;
		this.col = c;
	}

	public Node(Node node) {
		this.row = node.getRow();
		this.col = node.getCol();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	@Override
	public int compareTo(Node n2) {
		if (this.getValueF() == n2.getValueF()) {
			return 0;
		}
		return this.getValueF() > n2.getValueF() ? 1 : -1;
	}

	@Override
	public String toString() {
		String p = null;
		if (parent != null) {
			p = "" + parent.getRow() + ", " + parent.getCol();
		}
		return "Node [row=" + row + ", col=" + col + ", type=" + type + ", parent=[" + p + "], step=" + step
				+ ", valueG=" + valueG + ", valueH=" + valueH + ", valueF=" + valueF + "]";
	}

}
