package cn.kmboot.algorithm.leecode;

import java.io.Console;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Program {

	static char[][] item = new char[][] { new char[] { '1', '2', '3' }, new char[] { '1', '2' },
			new char[] { '1', '2' } };
	static char[] selectedItem;

	public static void main(String[] args) {
		selectedItem = new char[item.length];
		printOut(0);
	}

	static void printOut(int currentIndex) {
		if (currentIndex == selectedItem.length) {
			log.info(new String(selectedItem));
			return;
		}

		for (int i = 0; i < item[currentIndex].length; i++) {
			selectedItem[currentIndex] = item[currentIndex][i];
			printOut(currentIndex + 1);
		}
	}
}
