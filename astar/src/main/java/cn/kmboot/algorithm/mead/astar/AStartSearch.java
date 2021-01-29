package cn.kmboot.algorithm.mead.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author meadlai
 *
 */
@Slf4j
public class AStartSearch {
	private List<Node> result = new ArrayList<>();
	private List<Node> listOpen = new ArrayList<>();
	private List<Node> listClose = new ArrayList<>();
	private Maze maze;
	private int step = 0;

	public List<Node> search(Maze imaze, Node start, Node goal) {
		if (imaze == null) {
			throw new UnsupportedOperationException("Unable to process the null Maze");
		}
		if (imaze.getStart() == null) {
			imaze.setStart(start);
		}
		if (imaze.getGoal() == null) {
			imaze.setGoal(goal);
		}
		this.maze = imaze;
		//
		Node istart = this.maze.getNode(this.maze.getStart().getRow(), this.maze.getStart().getCol());
		istart.setValueG(0);
		this.maze.getStart().setValueG(0);
		this.listClose.add(this.maze.getStart());
		this.expand(this.maze.getStart());
		//
		return result;
	}

	private void expand(Node node) {
		List<Node> neighbors = this.getNeighbors(node);
		neighbors.forEach(inode -> {
			if (listClose.contains(inode)) {
				log.info("inode: {} is in the close list", inode);
				return;
			}
//			if (listOpen.contains(inode)) {
//				log.info("inode: {} is in the open list", inode);
//				return;
//			}
//			inode.setValueH(this.calculateHeuristic(inode));
			int gValue = node.getValueG() + 1;
			if (gValue < inode.getValueG()) {
				inode.setValueG(gValue);
				inode.setParent(node);
			}
			this.caculateFValue(inode);
			log.info("inode is: {}", inode);
			this.listOpen.add(inode);
		});
		if (this.listOpen.isEmpty()) {
			log.error("Finished, no items in the open list.");
		}
		if (this.listOpen.contains(this.maze.getGoal())) {
			log.info("Get the path!!!");
			return;
		}

		Collections.sort(this.listOpen);

		Node current = this.listOpen.get(0);
		current.setStep(++this.step);
		log.warn("::{}:: Selecting the current node: {}", this.step, current);
		this.listClose.add(current);
		this.listOpen.remove(current);
		// Recursive expand it
		this.expand(current);

	}

	private List<Node> getNeighbors(Node node) {
		List<Node> neighbors = new ArrayList<>();
		// up
		if (node.getRow() > 0) {
			Node up = this.maze.getNode(node.getRow() - 1, node.getCol());
			if (up != null && up.getType() != NodeType.OBSTACLE) {
				neighbors.add(up);
			}
		}
		// down
		if (node.getRow() < this.maze.getRows() - 1) {
			Node down = this.maze.getNode(node.getRow() + 1, node.getCol());
			if (down != null && down.getType() != NodeType.OBSTACLE) {
				neighbors.add(down);
			}
		}
		// left
		if (node.getCol() > 0) {
			Node left = this.maze.getNode(node.getRow(), node.getCol() - 1);
			if (left != null && left.getType() != NodeType.OBSTACLE) {
				neighbors.add(left);
			}
		}
		// right
		if (node.getCol() < this.maze.getColumns() - 1) {
			Node right = this.maze.getNode(node.getRow(), node.getCol() + 1);
			if (right != null && right.getType() != NodeType.OBSTACLE) {
				neighbors.add(right);
			}
		}
		return neighbors;
	}

	private int calculateHeuristic(Node node) {
		int h = Math.abs(node.getRow() - this.maze.getGoal().getRow())
				+ Math.abs(node.getCol() - this.maze.getGoal().getCol());
		return h;
	}

	private int caculateFValue(Node node) {
		node.setValueH(this.calculateHeuristic(node));
		int f = node.getValueH() + node.getValueG();
		node.setValueF(f);
		return f;
	}
}
