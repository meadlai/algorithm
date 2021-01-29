package cn.kmboot.algorithm.mead.astar;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author meadlai
 *
 */
@Slf4j
public class Maze {
	private List<List<Node>> data = new ArrayList<>();
	private Node start;
	private Node goal;
	AtomicInteger rows = new AtomicInteger(0);
	AtomicInteger cols = new AtomicInteger(0);

	public int getRows() {
		return this.rows.get();
	}

	public int getColumns() {
		return this.cols.get();
	}

	public void setStart(Node node) {
		if (start != null) {
			throw new UnsupportedOperationException("Already set the start node: " + start.toString());
		}
		this.checkValidateNode(node);
		start = new Node(node);
		this.getNode(node.getRow(), node.getCol()).setType(NodeType.START);
	}

	public Node getStart() {
		return this.start;
	}

	public Node getGoal() {
		return this.goal;
	}

	public void setGoal(Node node) {
		if (goal != null) {
			throw new UnsupportedOperationException("Already set the goal node: " + start.toString());
		}
		this.checkValidateNode(node);
		goal = new Node(node);
		this.getNode(node.getRow(), node.getCol()).setType(NodeType.GOAL);
	}

	private void checkValidateNode(Node node) {
		// no rows
		if (data == null || data.isEmpty() || data.get(0) == null || data.get(0).isEmpty()) {
			throw new IndexOutOfBoundsException("There is no data in the Maze: " + this.toString());
		}
		if (node.getRow() > data.size()) {
			throw new IndexOutOfBoundsException(
					"The Maze only has " + data.size() + " rows, but you set a node in the row: " + node.getRow());
		}
		if (node.getCol() > data.get(0).size()) {
			throw new IndexOutOfBoundsException("The Maze only has " + data.get(0).size()
					+ " columns, but you set a node in the column: " + node.getCol());
		}

	}

	/**
	 * return the reference object of the node
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Node getNode(int row, int column) {
		this.checkValidateNode(new Node(row, column));
		List<Node> rowData = data.get(row);
		return rowData.get(column);
	}

	public Maze(String filePath) throws URISyntaxException {
		loadFile(filePath);
	}

	private void loadFile(String filePath) throws URISyntaxException {
		Path path = Paths.get(getClass().getClassLoader().getResource(filePath).toURI());

		try (Stream<String> fileLines = Files.lines(path);) {
			fileLines.forEach(fline -> {
				cols.getAndSet(0);
				// read a line from file, and split it with comma <code>,</code>
				List<Node> line = Stream.of(fline.split(",")).map(point -> {
					Node node = new Node(rows.intValue(), cols.intValue());
					cols.getAndIncrement();
					switch (point) {
					case "0":
						node.setType(NodeType.BLANK);
						break;
					case "1":
						node.setType(NodeType.OBSTACLE);
						break;
					case "S":
						node.setType(NodeType.START);
						start = new Node(node);
						break;
					case "E":
						node.setType(NodeType.GOAL);
						goal = new Node(node);
						break;
					default:
						node.setType(NodeType.OBSTACLE);
					}
					return node;
				}).collect(Collectors.toList());
				//
				rows.getAndIncrement();
				data.add(line);
			});
		} catch (IOException e) {
			log.error("Unable to load file: " + filePath, e);
		}
	}

	public static void main(String[] args) throws Exception {
		String filePath = "map.txt";
		Maze maze = new Maze(filePath);
//		maze.setStart(new Node(0, 0))
//		maze.setGoal(new Node(1, 5))
		maze.printOut();
		log.info("Maze rows: {}", maze.getRows());
		log.info("Maze columns: {}", maze.getColumns());
	}

	/**
	 * 
	 * <code>
	S # . . . . . . .
	. # . # # E . . .
	. # . . # # . # .
	. # . . . . . . .
	. . . # # . # . .
	. # . . . . . . .
	</code>
	 */
	public void printOut() {
		this.data.forEach(line -> {
			line.forEach(node -> {
				if (node.getStep() > 0 && node.getStep() < 10) {
					System.out.print(" " + node.getStep());
					return;
				}
				if (node.getStep() >= 10) {
					System.out.print("" + node.getStep());
					return;
				}
				switch (node.getType()) {
				case START:
					System.out.print(" S");
					break;
				case GOAL:
					System.out.print(" E");
					break;
				case OBSTACLE:
					System.out.print(" #");
					break;
				case BLANK:
					System.out.print(" .");
					break;
				default:
				}
			});
			System.out.println();
		});
	}

}
