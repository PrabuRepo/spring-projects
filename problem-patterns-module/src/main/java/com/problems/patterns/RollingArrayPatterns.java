package com.problems.patterns;

import java.util.Arrays;

import com.common.model.TreeNode;

/*
 * This class covers DP: Pattern 1: Rolling Array or Fibonacci Patterns
 * Rolling Array or Fibonacci Patterns: 
 * 	Fibonacci numbers are a series of numbers in which each number is the sum of the two preceding numbers.
 *  All these problems follow the same rule. Every iteration result is based on sum of last two or three 
 *  preceeding numbers. Sometime preceeding values calculated with additional logic like min or max of last
 *  two values etc.
 * 
 * Two State Sequence Pattern/Two Variable Approach:
 *   - This approach work for sequence problems 
 *     with last values decide the next result
 *   - Use last 2 variables as p1(prev), p2(curr) 
 *   - assign p2 to tmp 
 *   - calculate p2 using p1, based on problem 
 *   - assign tmp to p1 
 *   - Final result will be p2.
 *
 */
public class RollingArrayPatterns {

	// 1.Fibonacci numbers: Two variable approach:
	//Refer DynamicProgramming.java in Basic Module approach
	public int fibonacci4(int n) {
		if (n == 0)
			return 0;
		int prev = 1, curr = 1;
		for (int i = 2; i < n; i++) {
			int tmp = curr;
			curr += prev;
			prev = tmp;
		}
		return curr;
	}

	//Staircase/Climbing Stairs/Triple Step/ Davis' Staircase/Count number of ways to cover a distance
	//Refer DynamicProgramming.java in Basic Module approach
	public int tripleSteps4(int n) {
		if (n <= 0)
			return 0;
		else if (n == 1)
			return 1;
		else if (n == 2)
			return 2;

		int prev1 = 1, prev2 = 1, curr = 2;
		for (int i = 3; i <= n; i++) {
			int sum = prev1 + prev2 + curr;
			prev1 = prev2;
			prev2 = curr;
			curr = sum;
		}
		return curr;
	}

	/* Maximum Sum Subsequence Non-Adjacent:
	 * Given an array of integers, find the subset of non-adjacent
	 * elements with the maximum sum. Calculate the sum of that subset.
	 */
	// Recursive slow solution.
	public int maxSubsetSum1(int arr[], int index) {
		if (index == 0) {
			return arr[0];
		} else if (index == 1) {
			return Math.max(arr[0], arr[1]);
		}
		return Math.max(maxSubsetSum1(arr, index - 2) + arr[index], maxSubsetSum1(arr, index - 1));
	}

	public int maxSubsetSum(int[] arr) {
		if (arr.length == 0)
			return 0;
		int incl = 0, excl = 0, temp = 0;
		for (int a : arr) {
			temp = incl;
			incl = Math.max(incl, excl + a);
			excl = temp;
		}
		return incl;
	}

	/*
	 * Maximum sum in a 2 x n grid such that no two elements are adjacent
	 * Sample:
	 * 	Input : 1 2 3 4 5
	 *          6 7 8 9 10
	 *  Output : 24
	 */
	public int maxSubsetSum2D(int[][] A) {
		int prev = 0, curr = 0, temp = 0;

		for (int i = 0; i < A[0].length; i++) {
			temp = Math.max(prev, curr);
			curr = prev + Math.max(A[0][i], A[1][i]);
			prev = temp;
		}

		return Math.max(prev, curr);
	}

	/* House Robber: 
	 * You are a professional robber planning to rob houses along a street. Each house has a certain
	 * amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have
	 * security system connected and it will automatically contact the police if two adjacent houses were broken into on
	 * the same night. Given a list of non-negative integers representing the amount of money of each house, determine
	 * the maximum amount of money you can rob tonight without alerting the police.
	 * 
	 * Solution: 
	 *   5 Different Approaches: 
	 *     1.Recursion
	 *     2.DP-Top Down Approach
	 *     3.DP-Bottom Up Approach
	 *     4.Bottom Up - 2 variable approach
	 *     5.Bottom up - odd/even approach
	 */
	// 1.Recursion:
	public int houseRob11(int[] nums) {
		return rob1(nums, nums.length - 1);
	}

	private int rob1(int[] nums, int i) {
		if (i < 0)
			return 0;
		return Math.max(rob1(nums, i - 2) + nums[i], rob1(nums, i - 1));
	}

	// 2.DP-Top Down Approach
	int[] memo;

	public int houseRob12(int[] nums) {
		memo = new int[nums.length + 1];
		Arrays.fill(memo, -1);
		return rob2(nums, nums.length - 1);
	}

	private int rob2(int[] nums, int i) {
		if (i < 0)
			return 0;
		if (memo[i] >= 0)
			return memo[i];
		memo[i] = Math.max(rob2(nums, i - 2) + nums[i], rob2(nums, i - 1));
		return memo[i];
	}

	/* Java Solution 3- Dynamic Programming The key is to find the relation dp[i]=Math.max(dp[i-1],dp[i-2]+nums[i]).
	 */
	public int houseRob13(int[] nums) {
		if (nums == null || nums.length == 0)
			return 0;
		int n = nums.length;
		if (n == 1)
			return nums[0];
		int[] dp = new int[n];
		dp[0] = nums[0];
		dp[1] = Math.max(nums[0], nums[1]);
		for (int i = 2; i < n; i++) {
			dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
		}
		return dp[n - 1];
	}

	// 4:Bottom Up: Two variable approach:
	public int houseRob14(int[] nums) {
		if (nums.length == 0)
			return 0;
		int prev = 0, curr = 0;
		for (int i = 0; i < nums.length; i++) {
			int tmp = curr;
			curr = Math.max(curr, nums[i] + prev);
			prev = tmp;
		}
		return curr;
	}

	/* Java Solution 5: We can use two variables, even and odd, to track the maximum value so far as iterating the
	 * array.You can use the following example to walk through the code.
	 */
	public int houseRob15(int[] num) {
		if (num == null || num.length == 0)
			return 0;
		int even = 0;
		int odd = 0;
		for (int i = 0; i < num.length; i++) {
			if (i % 2 == 0) {
				even += num[i];
				even = even > odd ? even : odd;
			} else {
				odd += num[i];
				odd = even > odd ? even : odd;
			}
		}
		return even > odd ? even : odd;
	}

	/* House Robber II: 
	 * After robbing those houses on that street, the thief has found himself a new place for his
	 * thievery so that he will not get too much attention. This time, all houses at this place are arranged in a
	 * circle. That means the first house is the neighbor of the last one.Meanwhile, the security system for these
	 * houses remain the same as for those in the previous street. Given a list of non- negative integers representing
	 * the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the
	 * police.
	 */

	/* Analysis:
	 * This is an extension of House Robber.There are two cases here 1)1 st element is included and last is not included
	 * 2)1 st is not included and last is included.Therefore, we can use the similar dynamic programming approach to
	 * scan the array twice and get the larger value.
	 */

	public int houseRob2(int[] nums) {
		if (nums == null || nums.length == 0)
			return 0;
		if (nums.length == 1)
			return nums[0];
		int max1 = findMax1(nums, 0, nums.length - 2);
		int max2 = findMax1(nums, 1, nums.length - 1);
		return Math.max(max1, max2);
	}

	//House Robber Solution: DP Solution: Time Complexity: O(n); Space Complexity: O(1)
	public int findMax1(int[] nums, int l, int h) {
		int prev = 0, curr = 0;
		for (int i = l; i <= h; i++) {
			int tmp = curr;
			curr = Math.max(curr, prev + nums[i]);
			prev = tmp;
		}
		return curr;
	}

	//Time Complexity: O(n); Space Complexity: O(n)
	public int findMax2(int[] nums, int l, int h) {
		if (l == h)
			return nums[l];
		int[] dp = new int[nums.length];
		dp[l] = nums[l];
		dp[l + 1] = Math.max(nums[l], nums[l + 1]);
		for (int i = l + 2; i <= h; i++) {
			dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
		}
		return dp[h];
	}

	/* House Robber III:
	 * The houses form a binary tree. If the root is robbed, its left and right can not be robbed. 
	 * 
	 * Analysis: Traverse down the tree recursively. We can use an array to keep 2 values: the maximum money when a root is selected and
	 * the maximum value when a root if NOT selected.
	 */
	public int houseRob3(TreeNode root) {
		if (root == null)
			return 0;
		int[] result = helper(root);
		return Math.max(result[0], result[1]);
	}

	public int[] helper(TreeNode root) {
		if (root == null) {
			int[] result = { 0, 0 };
			return result;
		}
		int[] result = new int[2];
		int[] left = helper(root.left);
		int[] right = helper(root.right);
		result[0] = root.val + left[1] + right[1];
		result[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
		return result;
	}

	//Decode Ways/Count Number of Encodings of a digit string
	// Approach1: Using Recursion
	public int numDecodings1(String s) {
		if (s.length() == 0)
			return 0;
		return numDecodings(s, 0);
	}

	public int numDecodings(String s, int i) {
		int n = s.length();
		if (i == n)
			return 1;
		if (i > n || s.charAt(i) == '0')
			return 0;
		int sum = numDecodings(s, i + 1);
		if (i + 1 < n)
			if (s.charAt(i) == '1' || (s.charAt(i) == '2' && s.charAt(i + 1) <= '6'))
				sum += numDecodings(s, i + 2);
		return sum;
	}

	// Approach2: DP-Bottom up
	/*I used a dp array of size n + 1 to save subproblem solutions. dp[0] means an empty string will have one way to decode,
	 *  dp[1] means the way to decode a string of size 1. I then check one digit and two digit combination and save the results
	 *   along the way. In the end, dp[n] will be the end result.*/
	public int numDecodings3(String s) {
		int n = s.length();
		int[] dp = new int[n + 1];
		dp[0] = 1;
		dp[1] = s.charAt(0) != '0' ? 1 : 0;
		for (int i = 2; i <= n; i++) {
			int first = Integer.valueOf(s.substring(i - 1, i));
			int second = Integer.valueOf(s.substring(i - 2, i));
			if (first >= 1 && first <= 9) //if(first!=0)
				dp[i] += dp[i - 1];
			if (second >= 10 && second <= 26)
				dp[i] += dp[i - 2];
		}
		return dp[n];
	}

	// Two variable approach:
	/* I think we can use two variables to store the previous results.
	 * Since we only use dp[i-1] and dp[i-2] to compute dp[i], why not 
	 * just use two variable prev1, prev2 instead? This can reduce the 
	 * space to O(1) */
	public int numDecodings4(String s) {
		if (s.charAt(0) == '0')
			return 0;
		int prev = 1, curr = 1;
		for (int i = 1; i < s.length(); i++) {
			// if prev & curr are zero, we can jump out of the loop earlier
			if (prev == 0 && curr == 0)
				return 0;

			int tmp = curr;
			if (s.charAt(i) == '0')
				curr = 0;
			int num = Integer.valueOf(s.substring(i - 1, i + 1));
			if (num >= 10 && num <= 26)
				curr += prev;
			prev = tmp;
		}
		return curr;
	}

	/*
	 * Jump Game I:Given an array of non-negative integers, you are initially positioned at the first index of the array. 
	 * Each element in the array represents your maximum jump length at that position. Determine if you are able to reach 
	 * the last index.
	 * 
	 */
	public boolean canJump(int[] nums) {
		int max = 0;
		for (int i = 0; i < nums.length; i++) {
			if (i > max)
				return false;
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
	// TODO: BFS Solution: https://leetcode.com/problems/jump-game-ii/discuss/18028/O(n)-BFS-solution
	// Minimum number of jumps to reach end
	public int minJumps(int arr[]) {
		return minJumps(arr, 0, arr.length - 1);
	}

	// Returns minimum number of jumps to reach arr[h] from arr[l]
	public int minJumps(int arr[], int index, int n) {
		if (index >= n)
			return 0;
		if (arr[index] == 0)
			return Integer.MAX_VALUE;
		int minJumps = Integer.MAX_VALUE;
		for (int i = index + 1; i <= n && i <= index + arr[index]; i++) {
			int currJump = minJumps(arr, i, n);
			if (currJump != Integer.MAX_VALUE && currJump + 1 < minJumps) {
				minJumps = currJump + 1;
			}
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

		for (int i = 0; i < n - 1; i++) {
			currMax = Math.max(currMax, i + nums[i]);
			if (i == currEnd) {
				//Edge case: if jumps are not reachable to last position
				if (i >= currMax)
					return Integer.MAX_VALUE;
				jumps++;
				currEnd = currMax;
			}
		}
		return jumps;
	}

	//TODO: Check this solution later
	public int minJumps41(int[] A) {
		if (A == null || A.length == 0)
			return -1;
		int jumps = 0, currMax = 0, currEnd = 0;
		for (int i = 0; i < A.length && i <= currEnd; i++) {
			if (i > currMax) {
				jumps++;
				currMax = currEnd;
			}
			currEnd = Math.max(currEnd, i + A[i]);
		}
		if (currEnd < A.length - 1)
			return -1;
		return jumps;
	}

}