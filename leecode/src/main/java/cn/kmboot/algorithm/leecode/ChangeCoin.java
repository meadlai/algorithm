package cn.kmboot.algorithm.leecode;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChangeCoin {
	private static int[] coins = new int[] { 1, 2, 5 };
//	private static int[] coins = new int[] { 4, 5, 10 };
	private static int amount = 11;
	//
	private static int min = amount + 1;
	private static int[] numberCoinsMax = new int[coins.length];
	// numTemp save the com
	private static int[] numTemp = new int[coins.length];
	private static int[][] array = new int[coins.length][];

	public static void main(String[] args) {

		calculateMax(coins, amount);
		for (int index = 0; index < array.length; index++) {
			array[index] = new int[numberCoinsMax[index]];
			for (int i = 0; i < numberCoinsMax[index]; i++) {
				array[index][i] = i;
			}
		}

		//
		printOut(0);
		if (min < amount + 1) {
			log.info("The min count is: {}", min);
		} else {
			log.warn("Here is no result for the coin: {} and amount: {}", Arrays.toString(coins), amount);
		}

	}

	/**
	 * 历遍二维数组
	 * 
	 * @param currentIndex
	 */
	private static void printOut(int currentIndex) {
		if (currentIndex == numTemp.length) {
			StringBuilder sb = new StringBuilder("");
			for (int count : numTemp) {
				sb.append(count);
			}
//			log.info("The Combine Result is: {}", sb.toString())
			min();
			return;
		}

		for (int i = 0; i < array[currentIndex].length; i++) {
			numTemp[currentIndex] = array[currentIndex][i];
			printOut(currentIndex + 1);
		}
	}

	/**
	 * 求每个硬币的最大值
	 * @param coins
	 * @param amount
	 */
	private static void calculateMax(int[] coins, int amount) {
		for (int i = 0; i < numberCoinsMax.length; i++) {
			numberCoinsMax[i] = amount / coins[i] + 1;
			log.info("Max number of coin {} is: {}", coins[i], numberCoinsMax[i]);
		}
	}

	/**
	 * 求排列组合正确值，硬币数相加
	 * @return
	 */
	private static int min() {
		int sum = 0;
		// num[0] = 0 ~ 12
		// num[1] = 0 ~ 4
		// num[2] = 0 ~ 3
		for (int i = 0; i < coins.length; i++) {
			sum = numTemp[i] * coins[i] + sum;
		}

		if (sum == amount) {
			log.warn("Here is the result for the coin: {} and amount: {}", Arrays.toString(numTemp), amount);
			int sumCoin = 0;
			for (int n : numTemp) {
				sumCoin = sumCoin + n;
			}
			min = Math.min(min, sumCoin);
		}
		return min;
	}

}
