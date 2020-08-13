package com.problems.patterns.recursion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.common.utilities.Utils;

/* All the below problems are solved in 4 approaches such as,
 *    1.Recursion -  Time: exponential time O(2^n), space complexity is O(n) which is used to store the recursion stack.
 *    2.DP: Top Down Approach or Memoization - Time and space complexity is same as memoization array size.
 *    3.DP: Bottom Up Approach or Tabulation - Time & space complexity is O(n^2) or O(n)
 *    4.Memory Optimization - This will be same time as Bottom up approach, but space efficient. Eg: Two variable approach
 */
public class DynamicProgrammingPatterns {

	/***************************** Pattern 2: Pattern Name?? *************************/
	/* Min cost to Paint House: 
	 * There are a row of n houses, each house can be painted with one of the three colors: red, blue or green. The cost of painting 
	 * each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.
	 *  
	 * The cost of painting each house with a certain color is represented by a n x 3 cost matrix. For example, costs[0][0] is 
	 * the cost of painting house 0 with color red; costs[1][2] is the cost of painting house 1 with color green, and so on... 
	 * Find the minimum cost to paint all houses.
	 */

	public int minCostToPaintHouse1(int[][] costs) {
		if (costs == null || costs.length == 0) return 0;
		int n = costs.length;
		for (int i = 1; i < n; i++) {
			costs[i][0] += Math.min(costs[i - 1][1], costs[i - 1][2]);
			costs[i][1] += Math.min(costs[i - 1][0], costs[i - 1][2]);
			costs[i][2] += Math.min(costs[i - 1][0], costs[i - 1][1]);
		}
		return Utils.min(costs[n - 1][0], costs[n - 1][1], costs[n - 1][2]);
	}

	//Using additional space and not modifying the input:
	public int minCostToPaintHouse2(int[][] costs) {
		if (costs == null || costs.length == 0) return 0;

		int n = costs.length;
		int[][] dp = new int[n][3];

		for (int i = 0; i < n; i++) {
			if (i == 0) {
				dp[i][0] = costs[i][0];
				dp[i][1] = costs[i][1];
				dp[i][2] = costs[i][2];
			} else {
				dp[i][0] = Math.min(dp[i - 1][1], dp[i - 1][2]) + costs[i][0];
				dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][2]) + costs[i][1];
				dp[i][2] = Math.min(dp[i - 1][0], dp[i - 1][1]) + costs[i][2];
			}
		}

		return Math.min(Math.min(dp[n - 1][0], dp[n - 1][1]), dp[n - 1][2]);
	}

	/*
	 * Paint House II: 
	 * There are a row of n houses, each house can be painted with one of the k colors. The cost of painting each house with
	 * a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.
	 * 
	 * The cost of painting each house with a certain color is represented by a n x k cost matrix. For example, costs[0][0] 
	 * is the cost of painting house 0 with color 0; costs[1][2] is the cost of painting house 1 with color 2, and so on... 
	 * Find the minimum cost to paint all houses.
	 * 
	 * 	Ref: http://buttercola.blogspot.com/2015/09/leetcode-paint-house-ii.html
	 */
	//Approach1: Time: O(n*k^2); Space:O(nk)
	public int minCostToPaintHouseII1(int[][] costs) {
		if (costs == null || costs.length == 0) return 0;
		int n = costs.length;
		int k = costs[0].length;
		int[][] dp = new int[n][k];
		for (int i = 0; i < k; i++)
			dp[0][i] = costs[0][i];

		for (int i = 1; i < n; i++) {
			for (int j = 0; j < k; j++) {
				int min = Integer.MAX_VALUE;
				//Find Min value
				for (int m = 0; m < k; m++) {
					if (m == j) continue;
					min = Math.min(min, dp[i - 1][m]);
				}
				dp[i][j] = min + costs[i][j];
			}
		}

		int minCost = Integer.MAX_VALUE;
		for (int i = 0; i < k; i++)
			minCost = Math.min(minCost, dp[n - 1][i]);
		return minCost;
	}

	//Efficient Approach: TC: O(nk); Space:O(1)
	public int minCostToPaintHouseII2(int[][] costs) {
		if (costs == null || costs.length == 0) return 0;
		int n = costs.length, k = costs[0].length;
		if (n == 1 && k == 1) return costs[0][0];

		int prevMin1 = 0, prevMin2 = 0, preMinIndex = -1;
		for (int i = 0; i < n; i++) {
			int currMin1 = Integer.MAX_VALUE, currMin2 = Integer.MAX_VALUE, currMinIndex = -1;
			for (int j = 0; j < k; j++) {
				//Add prev min 1st and 2nd in the curr cost 
				if (j == preMinIndex) costs[i][j] += prevMin2;
				else costs[i][j] += prevMin1;

				//Find min 1st and 2nd in every stage:
				if (costs[i][j] < currMin1) {
					currMin2 = currMin1;
					currMin1 = costs[i][j];
					currMinIndex = j;
				} else if (costs[i][j] < currMin2) {
					currMin2 = costs[i][j];
				}
			}
			//Assign back the curr values
			prevMin1 = currMin1;
			preMinIndex = currMinIndex;
			prevMin2 = currMin2;
		}
		//preMin already have min value for the last row 
		return prevMin1;
	}

	/*Paint Fence: 
	 * 	There is a fence with n posts, each post can be painted with one of the k colors. You have to paint all the posts such that 
	 * no more than two adjacent fence posts have the same color. Return the total number of ways you can paint the fence.
	 */

	/* Solution1:
	 * The key to solve this problem is finding this relation.f(n)=(k-1)(f(n-1)+f(n-2)) Assuming there are 3 posts, if
	 * the first one and the second one has the same color, then the third one has k-1 options. The first and second
	 * together has k options. If the first and the second do not have same color, the total is k * (k-1), then the
	 * third one has k options. Therefore, f(3) = (k-1)*k + k*(k-1)*k = (k-1)(k+k*k)
	 */

	public int paintFence1(int n, int k) {
		int dp[] = { 0, k, k * k, 0 };
		if (n <= 2) return dp[n];
		for (int i = 2; i < n; i++) {
			dp[3] = (k - 1) * (dp[1] + dp[2]);
			dp[1] = dp[2];
			dp[2] = dp[3];
		}
		return dp[3];
	}

	public int paintFence2(int n, int k) {
		if (n <= 0 || k <= 0) return 0;
		if (n == 1) return k;
		int preSame = 0, preDiff = k;
		for (int i = 1; i < n; i++) {
			int same = preDiff;
			int diff = (k - 1) * (preSame + preDiff);
			preSame = same;
			preDiff = diff;
		}
		return preSame + preDiff;
	}

	/******************************** Pattern 3: 0/1 Knapsack *************************/
	/*Note:
	 * 	0/1 Knapsack: Combinations without repeating the same data 
	 *  Unbounded Knapsack: Combination with repeating the same data
	 */
	public int knapsack1(int val[], int wt[], int W) {
		return knapsack1(W, wt, val, wt.length - 1);
	}

	public int knapsack1(int capacity, int wt[], int val[], int i) {
		if (i < 0 || capacity == 0) return 0;
		if (wt[i] > capacity) return knapsack1(capacity, wt, val, i - 1);
		return Math.max(val[i] + knapsack1(capacity - wt[i], wt, val, i - 1), knapsack1(capacity, wt, val, i - 1));
	}

	// Approach2: DP - Top Up Approach
	public int knapsack2(int[] val, int[] wt, int weight) {
		return 0;
	}

	// Approach3: DP - Bottom Up Approach - 2D Array
	public int knapsack31(int[] val, int[] wt, int weight) {
		int n = wt.length;
		int[][] dp = new int[n + 1][weight + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= weight; j++) {
				if (j < wt[i - 1]) {
					dp[i][j] = dp[i - 1][j];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], (val[i - 1] + dp[i - 1][j - wt[i - 1]]));
				}
			}
		}
		return dp[n][weight];
	}

	// Approach3: DP - Bottom Up Approach - 1D Array
	//Note: For knapsack prob, iter should be cap to wt[i]
	public int knapsack32(int[] val, int[] wt, int cap) {
		int n = wt.length;
		int[] dp = new int[cap + 1];
		for (int i = 0; i < n; i++)
			for (int j = cap; j >= wt[i]; j--)
				dp[j] = Math.max(dp[j], (val[i] + dp[j - wt[i]]));

		return dp[cap];
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
		if (i < 0) return false;
		if (arr[i] > sum) return isSubsetSum1(arr, i - 1, sum);
		return isSubsetSum1(arr, i - 1, sum) || isSubsetSum1(arr, i - 1, sum - arr[i]);
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

	/* Target Sum: 
	 * You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you have 2 symbols 
	 * + and -. For each integer, you should choose one from + and - as its new symbol.
	 * Find out how many ways to assign symbols to make sum of integers equal to target S.
	 */
	// Aproach1: Recursive Algorithm; Time Complexity: O(2^n)
	public int findTargetSumWays1(int[] num, int s) {
		if (num == null || num.length == 0) return 0;
		return noOfWays(num, s, 0, 0);
	}

	public int noOfWays(int[] nums, int target, int sum, int index) {
		if (nums.length == index) return target == sum ? 1 : 0;
		return noOfWays(nums, target, sum + nums[index], index + 1)
				+ noOfWays(nums, target, sum - nums[index], index + 1);
	}

	int result = 0;

	// Aproach2: Top Down DP or Memoization
	public int findTargetSumWays2(int[] num, int s) {
		if (num == null || num.length == 0) return 0;
		Map<String, Integer> memo = new HashMap<>();
		return noOfWays(num, memo, s, 0, 0);
	}

	public int noOfWays(int[] nums, Map<String, Integer> memo, int target, int sum, int index) {
		String serializedKey = index + "-" + sum;
		if (memo.containsKey(serializedKey)) memo.get(serializedKey);
		if (nums.length == index) return target == sum ? 1 : 0;
		int add = noOfWays(nums, target, sum + nums[index], index + 1);
		int sub = noOfWays(nums, target, sum - nums[index], index + 1);
		memo.put(serializedKey, add + sum);
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
	public int findTargetSumWays3(int[] num, int s) {
		int sum = 0;
		for (int n : num)
			sum += n;
		return sum < s || (s + sum) % 2 != 0 ? 0 : noOfWays(num, (sum + s) / 2);
	}

	public int noOfWays(int[] num, int sum) {
		int[] dp = new int[sum + 1];
		dp[0] = 1;
		for (int i = 0; i < num.length; i++)
			for (int j = sum; j >= num[i]; j--)
				dp[j] += dp[j - num[i]];

		return dp[sum];
	}

	/***************************** Pattern 4: Unbounded Knapsack *************************/
	// Unbounded Knapsack: 3 Approaches
	// 1.Recursive Approach
	public int unboundedKnapsack1(int val[], int wt[], int W) {
		return unboundedKnapsack1(W, wt, val, wt.length - 1);
	}

	public int unboundedKnapsack1(int cap, int wt[], int val[], int i) {
		if (i == 0 || cap == 0) return 0;
		if (wt[i] > cap) return unboundedKnapsack1(cap, wt, val, i - 1);
		return Math.max(val[i] + unboundedKnapsack1(cap - wt[i], wt, val, i), unboundedKnapsack1(cap, wt, val, i - 1));
	}

	// Using DP - Bottom Up Approach
	// Note: For unbounded knapsack prob, iteration
	// should be from wt[i] to cap
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
		if (i < 0) return 0;
		if (coins[i] > amt) return coinChange1(coins, i - 1, amt);

		return coinChange1(coins, i - 1, amt) + coinChange1(coins, i, amt - coins[i]);
	}

	// Approach: DP Bottom up Approach
	public int coinChange3(int amt, int[] coins) {
		int[] dp = new int[amt + 1];
		dp[0] = 1;
		for (int i = 0; i < coins.length; i++)
			for (int j = coins[i]; j <= amt; j++)
				dp[j] += dp[j - coins[i]];

		return dp[amt];
	}

	//Min Coins:
	//Simple Recursive approach:
	public int minCoins11(int[] coins, int amt) {
		int result = minCoins11(amt, coins, coins.length - 1, 0);
		return result == Integer.MAX_VALUE ? -1 : result;
	}

	public int minCoins11(int amt, int[] coins, int i, int count) {
		if (amt == 0) return count;
		if (i < 0) return Integer.MAX_VALUE;

		if (amt < coins[i]) return minCoins11(amt, coins, i - 1, count);

		return Math.min(minCoins11(amt, coins, i - 1, count), minCoins11(amt - coins[i], coins, i, count + 1));
	}

	//Recursive another approach - Time: Exponential O(S^n), where S = Total amount
	public int minCoins12(int[] coins, int amt) {
		int result = minCoins(coins, amt);
		return result == Integer.MAX_VALUE ? -1 : result;
	}

	public int minCoins(int coins[], int amt) {
		if (amt < 0) return -1;
		if (amt == 0) return 0;

		int min = Integer.MAX_VALUE;
		for (int coin : coins) {
			int currMin = minCoins(coins, amt - coin);
			if (currMin >= 0 && currMin < min) min = currMin + 1;
		}
		return min == Integer.MAX_VALUE ? -1 : min;
	}

	//DP(Memoization): Time Complexity: O(S*n)
	public int minCoins2(int[] coins, int amt) {
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

	// DP(Bottom up): Time Complexity: O(S*n)
	public int coinChange(int[] coins, int amount) {
		int max = amount + 1;
		int[] dp = new int[max];
		Arrays.fill(dp, max);
		dp[0] = 0;
		for (int i = 1; i <= amount; i++) {
			for (int coin : coins) {
				if (coin <= i) dp[i] = Math.min(dp[i], dp[i - coin] + 1);
			}
		}
		return dp[amount] > amount ? -1 : dp[amount];
	}

	//TODO: Check this
	//DP(Bottom up): Time Complexity: O(S*n)
	public int minCoins3(int[] coins, int amt) {
		int max = amt + 1;
		int[] dp = new int[max];
		Arrays.fill(dp, max);
		dp[0] = 0;

		for (int i = 0; i < coins.length; i++)
			for (int j = coins[i]; j <= amt; j++)
				dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);

		return dp[amt] > amt ? -1 : dp[amt];
	}

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
			for (int j = 0; j < nums.length; j++) {
				if (i >= nums[j]) dp[i] += dp[i - nums[j]];
			}
		}
		return dp[target];
	}

	/********************* Pattern 5: String-Palindromic substring/subseq Probs ***********************/
	// Longest Palindromic Subsequence:
	// 1.Recursion Approach
	public int lps1(String str) {
		return lps1(str, 0, str.length() - 1);
	}

	public int lps1(String str, int i, int j) {
		if (i == j) return 1;
		if (str.charAt(i) == str.charAt(j) && i + 1 == j) return 2;
		if (str.charAt(i) == str.charAt(j)) return lps1(str, i + 1, j - 1) + 2;
		return Math.max(lps1(str, i, j - 1), lps1(str, i + 1, j));
	}

	// 3.DP-Bottom Up Approach
	public int lps3(String str) {
		int n = str.length();
		int[][] result = new int[n][n];
		for (int i = 1; i < n; i++)
			result[i][i] = 1;
		for (int len = 2; len <= n; len++) {
			for (int i = 0; i < (n - len + 1); i++) {
				int j = i + len - 1;
				if (str.charAt(i) == str.charAt(j) && len == 2) {
					result[i][j] = 2;
				} else if (str.charAt(i) == str.charAt(j)) {
					result[i][j] = result[i + 1][j - 1] + 2;
				} else {
					result[i][j] = Math.max(result[i][j - 1], result[i + 1][j]);
				}
			}
		}

		printLPS3(result, str);

		return result[0][n - 1];
	}

	private String printLPS3(int[][] result, String str) {
		int n = str.length();
		int row = 0, col = n - 1, start = 0, end = result[0][n - 1] - 1;
		char[] seq = new char[result[0][n - 1]];
		while (row <= col) {
			if (result[row][col] > result[row][col - 1] && result[row][col] > result[row + 1][col]) {
				seq[start++] = str.charAt(col);
				seq[end--] = str.charAt(col);
				row++;
				col--;
			} else if (result[row][col] == result[row][col - 1]) {
				col--;
			} else if (result[row][col] == result[row + 1][col]) {
				row++;
			} else {
				row++;
				col--;
			}
		}
		return String.valueOf(seq);
	}

	// Longest Palindromic Substring:
	/* Method 1(Brute	Force):
	 * The simple approach is to check each substring whether the substring is a palindrome or not. We can run three
	 * loops, the outer two loops pick all substrings one by one by fixing the corner characters, the inner loop checks
	 * whether the picked substring is palindrome or not.
	 * Time complexity: O ( n^3 )
	 */
	public String lpSubstr1(String str) {
		int max = -1, n = str.length();
		String maxString = null, subString;
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				subString = str.substring(i, j + 1);
				if (isPalindrome(subString) && max < (j - i + 1)) {
					max = j - i + 1;
					maxString = subString;
				}
			}
		}
		return maxString;
	}

	// 2. Using DP-Bottom Up Approach:
	public String lpSubstr3(String str) {
		int n = str.length(), max = 1, start = 0;
		boolean[][] table = new boolean[n][n];
		for (int i = 0; i < n; i++)
			table[i][i] = true;
		for (int len = 2; len <= n; len++) {
			for (int i = 0; i <= n - len; i++) {
				int j = i + len - 1;
				if (str.charAt(i) == str.charAt(j) && (len == 2 || table[i + 1][j - 1])) {
					table[i][j] = true;
					if (len > max) {
						start = i;
						max = len;
					}
				}
			}
		}
		int maxLen = max + start;
		return str.substring(start, maxLen);
	}

	public boolean isPalindrome(String str) {
		int l = 0, h = str.length() - 1;
		while (l < h) {
			if (str.charAt(l++) != str.charAt(h--)) return false;
		}
		return true;
	}

	/*
	 * Palindrome Partitioning II:
	 *   Given a string s, partition s such that every substring of the partition is a palindrome. Return the minimum cuts needed for 
	 *   a palindrome partitioning of s.*/
	public int palindromicPartioningII(String s) {
		int n = s.length();
		if (n <= 1) return 0;
		boolean[][] dp = new boolean[n][n];
		int[] cut = new int[n];

		for (int r = 0; r < n; r++) {
			cut[r] = r;
			for (int l = 0; l <= r; l++) {
				if (s.charAt(l) == s.charAt(r) && (r - l <= 1 || dp[l + 1][r - 1])) {
					dp[l][r] = true;
					cut[r] = l > 0 ? Math.min(cut[r], cut[l - 1] + 1) : 0;
				}
			}
		}
		return cut[n - 1];
	}

	/************************** Pattern 6: String-Substring/Subsequence Probs *******************/
	// Longest Common Substring:
	// 1.Recursion Approach:
	public int lcStr1(String s1, String s2) {
		return lcStr1(s1, s2, s1.length() - 1, s2.length() - 1, 0);
	}

	public int lcStr1(String s1, String s2, int i, int j, int count) {
		if (i < 0 || j < 0) return count;
		if (s1.charAt(i) == s2.charAt(j)) return lcStr1(s1, s2, i - 1, j - 1, count + 1);
		return Utils.max(count, lcStr1(s1, s2, i - 1, j, 0), lcStr1(s1, s2, i, j - 1, 0));
	}

	// 2.DP:Bottom Up Approach:Time Complexity-O(m.n)
	public int lcStr3(String s1, String s2) {
		int m = s1.length(), n = s2.length();
		int[][] dp = new int[m][n];
		int max = 0, row = 0, col = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (s1.charAt(i) == s2.charAt(j)) {
					if (i == 0 || j == 0) {
						dp[i][j] = 1;
					} else {
						dp[i][j] = dp[i - 1][j - 1] + 1;
					}
					if (max < dp[i][j]) {
						max = dp[i][j];
						row = i;
						col = j;
					}
				}
			}
		}
		//s1 - row or s2 - col
		printLCStr(dp, row, col, s1);
		return max;
	}

	public String printLCStr(int[][] dp, int row, int col, String s) {
		String subStr = "";
		while (row >= 0 && col >= 0 && dp[row][col] != 0) {
			subStr = s.charAt(row) + subStr;
			row--;
			col--;
		}
		return subStr;
	}

	// Longest Common subsequence:
	// 1.Recursive approach
	public int lcs1(String s1, String s2) {
		return lcs1(s1, s2, s1.length() - 1, s2.length() - 1);
	}

	private int lcs1(String s1, String s2, int i, int j) {
		if (i < 0 || j < 0) return 0;
		if (s1.charAt(i) == s2.charAt(j)) return 1 + lcs1(s1, s2, i - 1, j - 1);
		return Math.max(lcs1(s1, s2, i - 1, j), lcs1(s1, s2, i, j - 1));
	}

	// 2.DP:Top Down approach
	public int lcs2(String s1, String s2) {
		int[][] dp = new int[s1.length()][s2.length()];
		for (int[] row : dp)
			Arrays.fill(row, -1);
		return lcs2(s1, s2, s1.length() - 1, s2.length() - 1, dp);
	}

	private int lcs2(String s1, String s2, int i, int j, int[][] dp) {
		if (i < 0 || j < 0) return 0;
		if (dp[i][j] != -1) return dp[i][j];
		if (s1.charAt(i) == s2.charAt(j)) {
			return dp[i][j] = lcs2(s1, s2, i - 1, j - 1, dp) + 1;
		}
		return dp[i][j] = Math.max(lcs2(s1, s2, i - 1, j, dp), lcs2(s1, s2, i, j - 1, dp));
	}

	// 3.DP Bottom Up Approach
	public int lcs3(String s1, String s2) {
		int m = s1.length(), n = s2.length();
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0 || j == 0) {
					dp[i][j] = 0;
				} else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
					dp[i][j] = 1 + dp[i - 1][j - 1];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}
		printLCS(dp, s1, s2);
		return dp[m][n];
	}

	// Print the longest common sub sequence
	private void printLCS(int[][] dp, String s1, String s2) {
		int i = s1.length(), j = s2.length();
		int longSeqCount = dp[i][j];
		char[] result = new char[longSeqCount];
		int index = longSeqCount;
		while (i > 0 && j > 0) {
			if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
				result[--index] = s1.charAt(i - 1);
				i--;
				j--;
			} else if (dp[i - 1][j] > dp[i][j - 1]) {
				i--;
			} else {
				j--;
			}
		}
		System.out.print("SubSequence:");
		for (int k = 0; k < longSeqCount; k++) {
			System.out.print(result[k] + "-");
		}
	}

	// Edit Distance: Find minimum number of edits (operations) required to convert ‘str1’ into ‘str2’.
	// Recursion Approach
	public int minDistance1(String s1, String s2) {
		return minDistance(s1, s2, s1.length() - 1, s2.length() - 1);
	}

	public int minDistance(String s1, String s2, int i, int j) {
		if (i < 0) return j + 1;
		if (j < 0) return i + 1;
		if (s1.charAt(i) == s2.charAt(j)) return minDistance(s1, s2, i - 1, j - 1);
		return 1 + Utils.min(minDistance(s1, s2, i, j - 1), minDistance(s1, s2, i - 1, j),
				minDistance(s1, s2, i - 1, j - 1));
	}

	// DP-Bottom up Approach
	public int minDistance(String s1, String s2) {
		int m = s1.length(), n = s2.length();
		if (m == 0 && n == 0) return 0;
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0) dp[i][j] = j;
				else if (j == 0) dp[i][j] = i;
				else if (s1.charAt(i - 1) == s2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
				else dp[i][j] = 1 + Utils.min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]);
			}
		}
		return dp[m][n];
	}

	/*
	 * Interleaving String:
	 *  Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
	 *  Example: 
	 *  	Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac" Output: true
	 *  	Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc" Output: false
	 */
	// Recursive Approach
	public boolean isInterleave1(String s1, String s2, String s3) {
		if ((s1.length() + s2.length()) != s3.length()) return false;
		return isInterleave(s1, s2, s3, 0, 0);
	}

	public boolean isInterleave(String s1, String s2, String s3, int i, int j) {
		if (i == s1.length() && j == s2.length() && i + j == s3.length()) return true;
		if (i + j == s3.length()) return false;

		return ((i < s1.length() && s1.charAt(i) == s3.charAt(i + j) && isInterleave(s1, s2, s3, i + 1, j))
				|| (j < s2.length() && s2.charAt(j) == s3.charAt(i + j) && isInterleave(s1, s2, s3, i, j + 1)));
	}

	// DP-Bottom Up Approach
	public boolean isInterleave3(String s1, String s2, String s3) {
		int n1 = s1.length(), n2 = s2.length();
		if ((n1 + n2) != s3.length()) return false;

		boolean[][] dp = new boolean[n1 + 1][n2 + 1];
		for (int i = 0; i <= n1; i++) {
			for (int j = 0; j <= n2; j++) {
				if (i == 0 && j == 0) {
					dp[i][j] = true;
				} else if (i == 0) {
					dp[i][j] = dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1);
				} else if (j == 0) {
					dp[i][j] = dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1);
				} else {
					dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
							|| (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
				}
			}
		}
		return dp[n1][n2];
	}

	/***************************** Pattern 7: Array-Subsequence Probs *******************************/
	// Longest Increasing Sequence:
	// Approach1: Recursive APproach
	// stores the LIS

	/* To make use of recursive calls, this function must return two things: 
	1) Length of LIS ending with element arr[n-1]. We use max_ending_here for this purpose 
	2) Overall maximum as the LIS may end with an element before arr[n-1] max_ref is used this purpose. 
	The value of LIS of full array of size n is stored in max_ref which is our final result 
	*/
	//Recursive1:
	public int LIS1(int[] nums) {
		if (nums.length <= 1) return nums.length;
		return lengthOfLIS(nums, 0, Integer.MIN_VALUE);
	}

	public int lengthOfLIS(int[] nums, int i, int prevNum) {
		if (i >= nums.length) return 0;
		int taken = 0, notTaken = 0;
		if (prevNum < nums[i]) {
			taken = 1 + lengthOfLIS(nums, i + 1, nums[i]);
		}
		notTaken = lengthOfLIS(nums, i + 1, prevNum);
		return Math.max(taken, notTaken);
	}

	private int max_ref; // stores the LIS

	//Recursive2:
	public int LIS11(int arr[], int n) {
		max_ref = 1;
		lis(arr, n);
		return max_ref;
	}

	private int lis(int arr[], int n) {
		if (n == 1) return 1;
		int res, max_ending_here = 1; // 'max_ending_here' is length of LIS ending with arr[n-1]

		/* Recursively get all LIS ending with arr[0], arr[1] ... arr[n-2]. If   arr[i-1] is smaller than arr[n-1], and 
		   max ending with arr[n-1] needs to be updated, then update it */
		for (int i = 1; i < n; i++) {
			res = lis(arr, i);
			if (arr[i - 1] < arr[n - 1] && res + 1 > max_ending_here) {
				max_ending_here = res + 1;
			}
		}

		max_ref = Math.max(max_ref, max_ending_here);

		return max_ending_here;
	}

	// Approach2: DP Approach : O(n^2)
	public int LIS3(int[] arr) {
		int n = arr.length;
		if (n <= 1) return n;
		int[] dp = new int[n];
		Arrays.fill(dp, 1);
		int max = dp[0];
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < i; j++) {
				if (arr[j] < arr[i] && dp[i] < dp[j] + 1) dp[i] = dp[j] + 1;
			}
			max = Math.max(max, dp[i]);
		}
		return max;
	}

	// Binary Search Approach : O(nlogn)
	public int LIS4(int[] nums) {
		int[] dp = new int[nums.length];
		int size = 0;
		for (int x : nums) {
			int l = 0, h = size;
			while (l != h) {
				int m = (l + h) / 2;
				if (dp[m] < x) l = m + 1;
				else h = m;
			}
			dp[l] = x;
			if (l == size) ++size;
		}
		return size;
	}

	// Longest Bitonic Subsequence
	// Approach3: DP-Bottom Up Approach
	public int lbs3(int[] arr) {
		int n = arr.length;
		int[] lis = new int[n]; // Largest Increasing Sequence array
		int[] lds = new int[n];// Largest Decreasing Sequence array
		for (int i = 0; i < n; i++) {
			lis[i] = 1;
			lds[i] = 1;
		}

		// Largest Increasing Sequence logic
		for (int i = 1; i < n; i++)
			for (int j = 0; j < i; j++)
				if (arr[j] < arr[i] && lis[i] < lis[j] + 1) lis[i] = lis[j] + 1;

		// Largest Decreasing Sequence logic
		for (int i = n - 2; i >= 0; i--)
			for (int j = n - 1; j > i; j--)
				if (arr[j] < arr[i] && lds[i] < lds[j] + 1) lds[i] = lds[j] + 1;

		// Find the Bitonic value from LIS & LDS ( LIS+LDS-1)
		int max = Integer.MIN_VALUE, temp;
		for (int i = 0; i < n; i++) {
			temp = lds[i] + lis[i] - 1;
			if (temp > max) max = temp;
		}
		return max;
	}

	// Maximum Sum Increasing Subsequence:
	// Approach3: DP-Bottom Up Approach
	public int MSIS3(int[] a) {
		int n = a.length;
		if (n <= 1) return n;
		int[] dp = new int[n];
		int[] indexSeq = new int[n];
		for (int i = 0; i < n; i++) {
			dp[i] = a[i];
			indexSeq[i] = i;
		}
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < i; j++) {
				if (a[j] < a[i] && dp[i] < dp[j] + a[i]) {
					dp[i] = dp[j] + a[i];
					indexSeq[i] = j;
				}
			}
		}
		printMSIS(a, dp, indexSeq);
		return Utils.max(dp);
	}

	private void printMSIS(int[] a, int[] msis, int[] indexSeq) {
		int max = Integer.MIN_VALUE, index = 0;
		for (int i = 0; i < msis.length; i++) {
			if (msis[i] > max) {
				max = msis[i];
				index = i;
			}
		}
		int temp = max;
		while (temp > 0) {
			System.out.print(a[index] + " ");
			temp = temp - a[index];
			index = indexSeq[index];
		}
	}

}