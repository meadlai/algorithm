package cn.kmboot.algorithm.mead.astar;

import java.net.URISyntaxException;

public class Main {

	public static void main(String[] args) throws URISyntaxException {
		test();
	}
	
	/**
	 * test basic Maze works.
	 * @throws URISyntaxException
	 */
	public static void test() throws URISyntaxException {
		String filePath = "map.txt";
		Maze maze = new Maze(filePath);
		maze.printOut();
		AStartSearch search = new AStartSearch();
		search.search(maze, null, null);
		maze.printOut();
	}

}
