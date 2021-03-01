package com.problems.patterns.dp;

import java.util.Arrays;

import com.common.utilities.Utils;

public class DPUncategorizedProblems {
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

	/***************************** Pattern Name?? *************************/

	/*
	 * Jump Game I:Given an array of non-negative integers, you are initially positioned at the first index of the array. 
	 * Each element in the array represents your maximum jump length at that position. Determine if you are able to reach 
	 * the last index.
	 */
	public boolean canJump(int[] nums) {
		int max = 0;
		for (int i = 0; i < nums.length; i++) {
			if (i > max) return false;
			max = Math.max(max, i + nums[i]);
		}

		return true;
	}

	/* Jump Game II: Minimum number of jumps to reach end. It can be solved using 
	 *   1. Recursive Approach
	 *   2. Linear Algorithm - Greedy Approach 
	 *   3. DP Approach
	 * 	 4. BFS Approach 
	 */
	// Recursive Solution
	public int minJumps(int arr[]) {
		return minJumps(arr, 0, arr.length - 1);
	}

	// Returns minimum number of jumps to reach arr[h] from arr[l]
	public int minJumps(int arr[], int index, int n) {
		if (index >= n) return 0;
		if (arr[index] == 0) return Integer.MAX_VALUE;
		int minJumps = Integer.MAX_VALUE;
		for (int i = index + 1; i <= n && i <= index + arr[index]; i++) {
			int currJump = minJumps(arr, i, n);
			if (currJump != Integer.MAX_VALUE) minJumps = Math.min(minJumps, currJump + 1);

		}
		return minJumps;
	}

	// Approach3: DP - Bottom up Approach; Time: O(n^2); Space: O(n)
	public int minJumps3(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n];
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[0] = 0;
		for (int i = 0; i < n - 1; i++)
			for (int j = 1; j <= nums[i] && i + j < n; j++)
				dp[i + j] = Math.min(dp[i + j], 1 + dp[i]);
		return dp[n - 1];
	}

	// Efficient Approach: Greedy Algorithm- Linear Approach
	public int minJumps4(int[] nums) {
		int currMax = 0, currEnd = 0, jumps = 0;
		int n = nums.length;

		//Note: iteration from 0 to n-2; Because there is a chance of getting 0 in last index, that may fail the logic 
		for (int i = 0; i < n - 1; i++) {
			currMax = Math.max(currMax, i + nums[i]);
			if (i == currEnd) {
				//Edge case: if jumps are not reachable to last position
				if (i >= currMax) return Integer.MAX_VALUE;
				jumps++;
				currEnd = currMax;
			}
		}
		return jumps;
	}

	// Efficient Approach: BFS- Linear Approach. It looks like 2 iterations, but it takes O(n) time
	public int minJumps5(int[] nums) {
		// Add this condition to handle single element in the array
		if (nums.length <= 1) return 0;

		int i = 0, max = 0, level = 0, n = nums.length;
		while (i <= max) {
			level++;
			int maxLevel = max;
			//Each level iterate from i to maxLevel
			while (i <= maxLevel) {
				max = Math.max(max, i + nums[i]);
				if (max >= n - 1) return level;
				i++;
			}
		}
		return -1; //or Integer.MAX_VALUE;
	}
}
