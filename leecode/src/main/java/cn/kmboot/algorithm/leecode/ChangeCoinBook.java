package cn.kmboot.algorithm.leecode;

import java.util.Arrays;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChangeCoinBook {

	public static void main(String[] args) {
		int[] coins = new int[] { 1, 2, 5 };
//		int[] coins = new int[] { 4, 5, 10 };
		int amount = 11;
		int minCoin = coinChange(coins, amount);
		log.info("min Coin is: {}", minCoin);
	}

	/**
	 * https://blog.csdn.net/nettee/article/details/106935507
	 * 
	 * @param coins
	 * @param amount
	 * @return
	 */
	public static int coinChange(int[] coins, int amount) {
		// 子问题：
		// f(k) = 凑出金额 k 的最小硬币数
		// f(k) = min{ 1 + f(k - c) }
		// f(0) = 0
		int[] dp = new int[amount + 1];
		Arrays.fill(dp, amount + 1); // DP 数组初始化为正无穷大
		Stream.of(dp).forEach(item -> log.info("init dp value: {}", item));
		dp[0] = 0;
		for (int k = 1; k <= amount; k++) {
			log.info("k: {}, amount: {}", k, amount);
			for (int c : coins) {
				log.info("--## coins.c: {}", c);
				if (k >= c) {
					log.info("----[k >= c]$$ dp[k]: {}, 1 + dp[k - c]: {}", dp[k], 1 + dp[k - c]);
					dp[k] = Math.min(dp[k], 1 + dp[k - c]);
					log.info("----[k >= c]## dp[k]: {}, 1 + dp[k - c]: {}", dp[k], 1 + dp[k - c]);
				}
			}
		}
		// 如果 dp[amount] > amount，认为是无效元素。

		Stream.of(dp).forEach(item -> log.info("end dp value: {}", item));

		if (dp[amount] > amount) {
			return -1;
		} else {
			log.info("--## dp[amount]: {}", dp[amount]);
			return dp[amount];
		}
	}
}
