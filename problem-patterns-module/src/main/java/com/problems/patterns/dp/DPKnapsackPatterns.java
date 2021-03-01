package com.problems.patterns.dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * Types of Knapsack Problems:
 * 	1. Fractional Knapsack - Can be solved using Greedy Algorithms
 *  2. 0/1 Knapsack
 *  3. Unbounded Knapsack
 */
public class DPKnapsackPatterns {

	/******************************** Pattern 3: 0/1 Knapsack *************************/
	/*Note:
	 * 	0/1 Knapsack: Combinations without repeating the same data 
	 *  Unbounded Knapsack: Combination with repeating the same data
	 *  
	 *Common formula for 0/1 knapsack and unbounded knapsack pattern problems:
	 *	 dp[i][j] = Math.max(dp[i - 1][j], (currVal + dp[i - 1][j - currWt]));
	 *	 dp[j] = Math.max(dp[j], val[i] + dp[j - wt[i]]);
	 *	 dp[i][j] = dp[i - 1][j] || dp[i - 1][j - arr[i - 1]];
	 *	 dp[j] = dp[j] || dp[j-arr[i]];
	 *
	 *   dp[j] = Math.min(dp[j], dp[j - 1 + coins[i]] + 1);
	 *   dp[j] = dp[j] || dp[j - arr[i]];
	 *   dp[j] = dp[j] + dp[j - coins[i]];
	 */
	//Time Complexity: O(2^n); Additional Space: O(1); recursion space: O(n)
	public int knapsack1(int val[], int wt[], int W) {
		return knapsack1(W, wt, val, wt.length - 1);
	}

	public int knapsack1(int W, int wt[], int val[], int i) {
		if (i < 0 || W == 0) return 0;
		if (wt[i] > W) return knapsack1(W, wt, val, i - 1);
		return Math.max(val[i] + knapsack1(W - wt[i], wt, val, i - 1), knapsack1(W, wt, val, i - 1));
	}

	// Approach2: DP - Top Up Approach
	// Time Complexity: O(n*Weight), As redundant calculations of states are avoided. 
	// Space: O(n*Weight), The use of 2D array data structure for storing intermediate states
	public int knapsack2(int[] val, int[] wt, int weight) {
		int n = wt.length;
		int[][] memo = new int[n + 1][weight + 1];

		//Intialize memo array to -1.
		for (int i = 0; i <= n; i++) {
			Arrays.fill(memo[i], -1);
		}

		return knapsack2(weight, wt, val, n - 1, memo);
	}

	public int knapsack2(int W, int wt[], int val[], int i, int[][] memo) {
		if (i < 0 || W == 0) return 0;
		if (memo[i][W] != -1) return memo[i][W];

		if (wt[i] > W) {
			return memo[i][W] = knapsack2(W, wt, val, i - 1, memo);
		}

		return memo[i][W] = Math.max(val[i] + knapsack2(W - wt[i], wt, val, i - 1, memo),
				knapsack2(W, wt, val, i - 1, memo));
	}

	// Approach3: DP - Bottom Up Approach - 2D Array
	public int knapsack31(int[] val, int[] wt, int weight) {
		int n = wt.length;
		int[][] dp = new int[n + 1][weight + 1];
		for (int i = 1; i <= n; i++) {
			//Current value and weight
			int currVal = val[i - 1], currWt = wt[i - 1];
			//Note: variable 'j' act as weight. Since it is bottom up approach, so values are populated weight one by one.
			// and this similar to recursive fun. In the recursive fun, each weight is subract from given W. (W-wt[i] => j-wt[i-1])
			for (int j = 1; j <= weight; j++) {
				if (j < currWt) {
					//Previous result is assigned for j which less than equal to currWt. Which is eliminated or not rquired in 1D array approach
					dp[i][j] = dp[i - 1][j];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], (currVal + dp[i - 1][j - currWt]));
				}
			}
		}
		return dp[n][weight];
	}

	/* Approach3: DP - Bottom Up Approach - 1D Array
	 *  Iteration should be decremented from max weight(W) to wt[i]. Because here we need previous value to populate 
	 *  the table. So if we start last value(W), then we will get a chance to get prev value from current indices.
	 *  Suppose if we increment iteration from wt[i] to W, then lower indices will be populated first and when we move 
	 *  further there is no way to get prev value for higher indices. 
	 *  In 2D approach, value is incremented from 1 to max weight, because there we have additional space to store the 
	 *  previous values. So here prev result getting from previous row in the 2D array.
	 */
	public int knapsack32(int[] val, int[] wt, int W) {
		int n = wt.length;
		int[] dp = new int[W + 1];
		for (int i = 0; i < n; i++)
			for (int j = W; j >= wt[i]; j--)
				dp[j] = Math.max(dp[j], (val[i] + dp[j - wt[i]]));

		return dp[W];
	}

	/* Subset Sum: 
	 * Given an array of non negative numbers and a total, is there subset of numbers in this array which adds up
	 * to given total. Another variation is given an array is it possible to split it up into 2 equal
	 * sum partitions. Partition need not be equal sized. Just equal sum.
	 */
	// Approach1: Using Recursive Function; Time: O(2^n)
	boolean isSubsetSum1(int arr[], int sum) {
		return isSubsetSum1(arr, arr.length - 1, sum);
	}

	boolean isSubsetSum1(int arr[], int i, int sum) {
		if (sum == 0) return true;
		if (i < 0 || sum < 0) return false;

		if (arr[i] > sum) return isSubsetSum1(arr, i - 1, sum);
		return isSubsetSum1(arr, i - 1, sum) || isSubsetSum1(arr, i - 1, sum - arr[i]);
	}

	//Approach2: Top Down or Memoization Approach:
	boolean isSubsetSum2(int arr[], int sum) {
		int n = arr.length;
		int[][] memo = new int[n + 1][sum + 1];
		for (int i = 0; i <= n; i++)
			Arrays.fill(memo[i], -1);
		return isSubsetSum2(arr, n - 1, sum, memo);
	}

	boolean isSubsetSum2(int arr[], int i, int sum, int[][] memo) {
		if (sum == 0) return true;
		if (i < 0 || sum < 0) return false;
		if (memo[i][sum] != -1) return memo[i][sum] == 1;

		boolean result = false;
		if (arr[i] > sum) {
			result = isSubsetSum1(arr, i - 1, sum);
			memo[i][sum] = result == true ? 1 : 0;
			return result;
		}
		result = isSubsetSum1(arr, i - 1, sum) || isSubsetSum1(arr, i - 1, sum - arr[i]);
		memo[i][sum] = result == true ? 1 : 0;
		return result;
	}

	// Approach3: Bottom Up DP - Time: O(n*sum); Space: O(n*sum)
	public boolean isSubsetSum31(int[] arr, int sum) {
		boolean dp[][] = new boolean[arr.length + 1][sum + 1];
		for (int i = 0; i <= arr.length; i++)
			dp[i][0] = true;
		for (int i = 1; i <= arr.length; i++) {
			for (int j = 1; j <= sum; j++) {
				if (j < arr[i - 1]) {
					dp[i][j] = dp[i - 1][j];
				} else {
					dp[i][j] = dp[i - 1][j] || dp[i - 1][j - arr[i - 1]];
				}
			}
		}
		return dp[arr.length][sum];
	}

	// Approach3: Bottom Up DP - Time: O(n*sum); Space: O(sum)
	public boolean isSubsetSum32(int[] arr, int sum) {
		boolean dp[] = new boolean[sum + 1];
		dp[0] = true;
		for (int i = 0; i < arr.length; i++)
			for (int j = sum; j >= arr[i]; j--)
				dp[j] = dp[j] || dp[j - arr[i]];
		return dp[sum];
	}

	/*
	 * Count of Subset Sum/Perfect Sum Problem:
	 * 
	 */
	// Approach1: Using Recursive Function; Time Complexity: O(2^n)
	public int countSubsetSum1(int arr[], int sum) {
		return countSubsetSum1(arr, arr.length - 1, sum);
	}

	private int countSubsetSum1(int arr[], int i, int sum) {
		if (sum == 0) return 1;
		if (i < 0) return 0;
		if (arr[i] > sum) return countSubsetSum1(arr, i - 1, sum);
		return countSubsetSum1(arr, i - 1, sum) + countSubsetSum1(arr, i - 1, sum - arr[i]);
	}

	// Approach3: Bottom Up DP - Time: O(n*sum); Space: O(n*sum)
	public int countSubsetSum3(int[] nums, int sum) {
		int n = nums.length;
		int[] dp = new int[sum + 1];
		dp[0] = 1;
		for (int i = 0; i < n; i++)
			for (int j = sum; j >= nums[i]; j--)
				dp[j] = dp[j] + dp[j - nums[i]];

		return dp[sum];
	}

	/* Target Sum: 
	 * You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you have 2 symbols 
	 * + and -. For each integer, you should choose one from + and - as its new symbol.
	 * Find out how many ways to assign symbols to make sum of integers equal to target S.
	 */
	// Approach1: Recursive Algorithm; Time Complexity: O(2^n)
	public int findTargetSumWays1(int[] num, int target) {
		if (num == null || num.length == 0) return 0;
		return noOfWays(num, target, 0, 0);
	}

	public int noOfWays(int[] nums, int target, int sum, int index) {
		if (nums.length == index) return target == sum ? 1 : 0;
		return noOfWays(nums, target, sum + nums[index], index + 1)
				+ noOfWays(nums, target, sum - nums[index], index + 1);
	}

	int result = 0;

	// Aproach2: Top Down DP or Memoization
	// Time: O(n*target), Space:(n*target)
	public int findTargetSumWays2(int[] num, int target) {
		if (num == null || num.length == 0) return 0;
		Map<String, Integer> memo = new HashMap<>();
		//or we can use 2D array to store the results
		//int[][] memo = new int[num.length + 1][target + 1];
		return noOfWays(num, memo, target, 0, 0);
	}

	public int noOfWays(int[] nums, Map<String, Integer> memo, int target, int sum, int index) {
		String serializedKey = index + "-" + sum;
		if (memo.containsKey(serializedKey)) memo.get(serializedKey);
		if (nums.length == index) return target == sum ? 1 : 0;
		int add = noOfWays(nums, target, sum + nums[index], index + 1);
		int sub = noOfWays(nums, target, sum - nums[index], index + 1);
		memo.put(serializedKey, add + sub);
		return add + sub;
	}

	// Approach3: DP: Bottom Up Approach
	/*Using subset sum algorithm using DP Bottom Up Approach,Let's see how this can be converted to a subset sum problem: 
	 * sum(P) - sum(N) = target; // where P & N set of elements
	 * sum(P) + sum(N) + sum(P) - sum(N) = target + sum(P) + sum(N); //Add sum(P) + sum(N) on both sides
	 *  2 * sum(P) =  target + sum(P) + sum(N);
	 *  2* sum(P) = target + sum(nums); // where sum(nums) = sum(P) + sum(N); 
	 * So the original problem has been converted to a subset sum problem as follows:
	 *  Find a subset P of nums such that sum(P) = (target + sum(nums)) / 2
	 */
	public int findTargetSumWays3(int[] nums, int target) {
		int sum = 0;
		for (int n : nums)
			sum += n;
		return sum < target || (target + sum) % 2 != 0 ? 0 : countSubsetSum(nums, (sum + target) / 2);
	}

	// Solution for subset sum problem
	public int countSubsetSum(int[] nums, int sum) {
		int[] dp = new int[sum + 1];
		dp[0] = 1;
		for (int i = 0; i < nums.length; i++)
			for (int j = sum; j >= nums[i]; j--)
				dp[j] = dp[j] + dp[j - nums[i]];

		return dp[sum];
	}

	/***************************** Pattern 4: Unbounded Knapsack *************************/
	// Unbounded Knapsack: 3 Approaches
	// 1.Recursive Approach - Time Complexity: O(2^n); Additional Space: O(1); recursion space: O(n)
	public int unboundedKnapsack1(int val[], int wt[], int W) {
		return unboundedKnapsack1(W, wt, val, wt.length - 1);
	}

	public int unboundedKnapsack1(int cap, int wt[], int val[], int i) {
		if (i < 0 || cap == 0) return 0;
		if (wt[i] > cap) return unboundedKnapsack1(cap, wt, val, i - 1);
		return Math.max(val[i] + unboundedKnapsack1(cap - wt[i], wt, val, i), unboundedKnapsack1(cap, wt, val, i - 1));
	}

	// Approach2: DP - Top Up Approach
	// Time Complexity: O(n*Weight), As redundant calculations of states are avoided. 
	// Space: O(n*Weight), The use of 2D array data structure for storing intermediate states
	public int unboundedKnapsack2(int[] val, int[] wt, int cap) {
		int n = wt.length;
		int[][] memo = new int[n + 1][cap + 1];

		//Intialize memo array to -1.
		for (int i = 0; i <= n; i++) {
			Arrays.fill(memo[i], -1);
		}

		return unboundedKnapsack2(cap, wt, val, n - 1, memo);
	}

	public int unboundedKnapsack2(int W, int wt[], int val[], int i, int[][] memo) {
		if (i < 0 || W == 0) return 0;
		if (memo[i][W] != -1) return memo[i][W];

		if (wt[i] > W) return memo[i][W] = unboundedKnapsack2(W, wt, val, i - 1, memo);

		return memo[i][W] = Math.max(val[i] + unboundedKnapsack2(W - wt[i], wt, val, i, memo),
				unboundedKnapsack2(W, wt, val, i - 1, memo));
	}

	// Using DP - Bottom Up Approach
	// Note: For unbounded knapsack prob, iteration should be from wt[i] to cap
	public int unboundedKnapsack3(int val[], int wt[], int cap) {
		int n = wt.length;
		int dp[] = new int[cap + 1];
		for (int i = 0; i < n; i++)
			for (int j = wt[i]; j <= cap; j++)
				dp[j] = Math.max(dp[j], val[i] + dp[j - wt[i]]);
		return dp[cap];
	}

	/*Coin Change 2 /Coins - No of ways to get amount
	 * Given an infinite number of quarters (25 cents), dimes (10 cents), nickels (5 cents), and pennies (1 cent), 
	 * write code to calculate the number of ways of representing n cents.
	 */
	public int coinChanges(int[] coins, int amount) {
		return coinChange1(coins, coins.length - 1, amount);
	}

	public int coinChange1(int[] coins, int i, int amt) {
		if (amt == 0) return 1;
		if (i < 0 || amt < 0) return 0;
		if (coins[i] > amt) return coinChange1(coins, i - 1, amt);

		return coinChange1(coins, i - 1, amt) + coinChange1(coins, i, amt - coins[i]);
	}

	// Approach: DP Bottom up Approach
	public int coinChange3(int amt, int[] coins) {
		int[] dp = new int[amt + 1];
		dp[0] = 1;
		for (int i = 0; i < coins.length; i++)
			for (int j = coins[i]; j <= amt; j++)
				dp[j] = dp[j] + dp[j - coins[i]];

		return dp[amt];
	}

	//Coin Change: Min Coins-
	//Simple Recursive approach: Time: O(2^n)
	public int minCoins11(int[] coins, int amt) {
		int result = minCoins11(amt, coins, coins.length - 1, 0);
		return result == Integer.MAX_VALUE ? -1 : result;
	}

	public int minCoins11(int amt, int[] coins, int i, int count) {
		if (amt == 0) return count;
		if (i < 0 || amt < 0) return Integer.MAX_VALUE;

		if (amt < coins[i]) return minCoins11(amt, coins, i - 1, count);

		return Math.min(minCoins11(amt, coins, i - 1, count), minCoins11(amt - coins[i], coins, i, count + 1));
	}

	//Top Down approach/Memoization: O(n*amt), Space:O(n*amt)
	//TODO: Its not working. Revisit this
	public int minCoins2(int[] coins, int amt) {
		int n = coins.length;
		int[][] memo = new int[n][amt + 1];

		for (int[] arr : memo)
			Arrays.fill(arr, -1);

		int result = minCoins2(amt, coins, n - 1, 0, memo);
		return result == Integer.MAX_VALUE ? -1 : result;
	}

	public int minCoins2(int amt, int[] coins, int i, int count, int[][] memo) {
		if (amt == 0) return count;
		if (i < 0 || amt < 0) return Integer.MAX_VALUE;

		if (memo[i][amt] != -1) return memo[i][amt];

		if (amt < coins[i]) memo[i][amt] = minCoins2(amt, coins, i - 1, count, memo);

		return memo[i][amt] = Math.min(minCoins2(amt, coins, i - 1, count, memo),
				minCoins2(amt - coins[i], coins, i, count + 1, memo));
	}

	//DP(Bottom up): Time Complexity: O(S*n)
	public int minCoins31(int[] coins, int amount) {
		int max = amount + 1;
		int[] dp = new int[max];
		Arrays.fill(dp, max);
		dp[0] = 0;

		for (int i = 0; i < coins.length; i++)
			for (int j = coins[i]; j <= amount; j++)
				dp[j] = Math.min(dp[j], 1 + dp[j - coins[i]]);

		return dp[amount] > amount ? -1 : dp[amount];
	}

	//Another approach for mincoins problem:
	//TODO: Compare below 3 approaches with combination sum IV problem
	//Recursive another approach - Time: Exponential O(S^n), where S = Total amount
	public int minCoins12(int[] coins, int amt) {
		int result = minCoins(coins, amt);
		return result == Integer.MAX_VALUE ? -1 : result;
	}

	public int minCoins(int coins[], int amt) {
		if (amt < 0) return -1;
		if (amt == 0) return 0;

		int min = Integer.MAX_VALUE;
		for (int i = 0; i < coins.length; i++) {
			int currMin = minCoins(coins, amt - coins[i]);
			if (currMin >= 0 && currMin < min) min = currMin + 1;
		}
		return min == Integer.MAX_VALUE ? -1 : min;
	}

	//DP(Memoization): Time Complexity: O(S*n)
	public int minCoins22(int[] coins, int amt) {
		return minCoins2(coins, amt, new int[amt]);
	}

	public int minCoins2(int coins[], int amt, int[] dp) {
		if (amt < 0) return -1;
		if (amt == 0) return 0;
		if (dp[amt - 1] != 0) return dp[amt - 1];

		int min = Integer.MAX_VALUE;
		for (int coin : coins) {
			int currMin = minCoins2(coins, amt - coin, dp);
			if (currMin >= 0 && currMin < min) min = currMin + 1;
		}
		dp[amt - 1] = min == Integer.MAX_VALUE ? -1 : min;
		return dp[amt - 1];
	}

	// DP(Bottom up): Time Complexity: O(S*n) - Other approach
	public int coinChange32(int[] coins, int amount) {
		int max = amount + 1;
		int[] dp = new int[max];
		Arrays.fill(dp, max);
		dp[0] = 0;
		for (int i = 1; i <= amount; i++) {
			for (int coin : coins) {
				if (i >= coin) dp[i] = Math.min(dp[i], dp[i - coin] + 1);
			}
		}
		return dp[amount] > amount ? -1 : dp[amount];
	}

	//TODO: Check this

	// Combination Sum IV  - Permutation Problem
	/* Eg: nums = [1, 2, 3],  target = 4
	 * The possible combination ways are:
	 * 	(1, 1, 1, 1)
	 *  (1, 1, 2)
	 *  (1, 2, 1)
	 *  (1, 3)
	 *  (2, 1, 1)
	 *  (2, 2)
	 *  (3, 1)
	 *  Ans: 7
	 *  
	 *  Note: For combination sum w/o dup: 4
	 */
	// Approach1: Recursion
	public int combinationSum41(int[] nums, int target) {
		if (target == 0) return 1;
		int count = 0;
		for (int i = 0; i < nums.length; i++) {
			if (target >= nums[i]) count += combinationSum41(nums, target - nums[i]);
		}
		return count;
	}

	// Approach2: DP Top down or Memoization
	public int combinationSum42(int[] nums, int target) {
		int[] dp = new int[target + 1];
		Arrays.fill(dp, -1);
		dp[0] = 1;
		return helper(nums, target, dp);
	}

	public int helper(int[] nums, int target, int[] dp) {
		if (dp[target] != -1) return dp[target];
		int count = 0;
		for (int i = 0; i < nums.length; i++) {
			if (target >= nums[i]) count += combinationSum42(nums, target - nums[i]);
		}
		dp[target] = count;
		return count;
	}

	// Approach3: DP Bottom up approach
	public int combinationSum43(int[] nums, int target) {
		int[] dp = new int[target + 1];
		dp[0] = 1;
		for (int i = 1; i <= target; i++) {
			for (int num : nums) {
				if (i >= num) dp[i] += dp[i - num];
			}
		}
		return dp[target];
	}
}