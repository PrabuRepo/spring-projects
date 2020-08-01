package com.basic.algorithms;

import java.util.Arrays;

/* Dynamic Programming problems can be solved by three approaches,
 *    - Recursion
 *    - DP: Top Down Approach or Memoization 
 *    - DP: Bottom Up Approach or Tabulation
 */
public class DynamicProgramming {
	// Fibonacci Number calculation:
	// 1.using recursive function
	public int fibonacci1(int n) {
		if (n <= 1)
			return n;
		return fibonacci1(n - 1) + fibonacci1(n - 2);
	}

	// 2.DP: Top Down or Memoization
	public int fibonacci2(int n) {
		int[] dp = new int[n + 1];
		Arrays.fill(dp, -1);
		return fibonacci2(n, dp);
	}

	public int fibonacci2(int n, int[] dp) {
		if (dp[n] != -1)
			return dp[n];
		if (n <= 1) {
			dp[n] = n;
		} else {
			dp[n] = fibonacci2(n - 1) + fibonacci2(n - 2);
		}
		return dp[n];
	}

	// 3.DP:Bottom Up or Tabulation
	public static long fibonacci3(int n) {
		long[] fib = new long[n + 1];
		fib[0] = 0;
		fib[1] = 1;
		for (int i = 2; i <= n; i++)
			fib[i] = fib[i - 1] + fib[i - 2];
		return fib[n];
	}

	// 1.Two variable approach:
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

	// Staircase/Climbing Stairs/Triple Step/ Davis' Staircase/Count number of ways to cover a distance
	// 1.Recursive Solution
	public int tripleSteps1(int n) {
		if (n < 0)
			return 0;
		else if (n == 0)
			return 1;
		return tripleSteps1(n - 1) + tripleSteps1(n - 2) + tripleSteps1(n - 3);
	}

	// 2. DP- Top down: Memoization
	public int tripleSteps2(int n) {
		if (n <= 0)
			return 0;
		int[] dp = new int[n + 1];
		return tripleStepsUtil(n, dp);
	}

	public int tripleStepsUtil(int n, int[] dp) {
		if (n < 0)
			return 0;
		else if (n == 0)
			return 1;
		if (dp[n] == 0) {
			dp[n] = tripleStepsUtil(n - 1, dp) + tripleStepsUtil(n - 2, dp) + tripleStepsUtil(n - 3, dp);
		}
		return dp[n];
	}

	// 3. DP- Bottom up: Tabulation
	public int tripleSteps3(int n) {
		if (n <= 0)
			return 0;
		else if (n == 1)
			return 1;
		else if (n == 2)
			return 2;
		else if (n == 3)
			return 4;
		int[] dp = new int[n + 1];
		dp[1] = 1;
		dp[2] = 2;
		dp[3] = 4;
		for (int i = 4; i <= n; i++)
			dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
		return dp[n];
	}
}
