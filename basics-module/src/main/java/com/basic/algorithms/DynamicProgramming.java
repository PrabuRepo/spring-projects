package com.basic.algorithms;

import java.util.Arrays;

/* What is Dynamic Programming?
 * Dynamic Programming (DP) is an algorithmic technique for solving an optimization problem by breaking it down into simpler subproblems 
 * and utilizing the fact that the optimal solution to the overall problem depends upon the optimal solution to its subproblems.
 * 
 * Characteristics of Dynamic Programming:
 * 	  1.Overlapping Subproblems: Subproblems are smaller versions of the original problem. Any problem has overlapping sub-problems if 
 *      finding its solution involves solving the same subproblem multiple times. 
 *    2.Optimal Substructure Property: Any problem has optimal substructure property if its overall optimal solution can be constructed 
 *      from the optimal solutions of its subproblems. 
 *      Eg: Fibonacci numbers, as we know, Fib(n) = Fib(n-1) + Fib(n-2). This clearly shows that a problem of size ‘n’ has been reduced
 *      to subproblems of size ‘n-1’ and ‘n-2’. Therefore, Fibonacci numbers have optimal substructure property.
 *   
 * DP offers two methods to solve a problem:
 * 1.Top-down with Memoization: 
 * 	 In this approach, we try to solve the bigger problem by recursively finding the solution to smaller 
 * 	 sub-problems. Whenever we solve a sub-problem, we cache its result so that we don’t end up solving it repeatedly if it’s called 
 *   multiple times. Instead, we can just return the saved result. This technique of storing the results of already solved subproblems
 *   is called Memoization.
 * 2.Bottom-up with Tabulation:
 * 	 Tabulation is the opposite of the top-down approach and avoids recursion. In this approach, we solve the problem “bottom-up” 
 *   (i.e. by solving all the related sub-problems first). This is typically done by filling up an n-dimensional table. Based on the 
 *   results in the table, the solution to the top/original problem is then computed. 
 *   Tabulation is the opposite of Memoization, as in Memoization we solve the problem and maintain a map of already solved sub-problems.
 *   In other words, in memoization, we do it top-down in the sense that we solve the top problem first (which typically recurses down to 
 *   solve the sub-problems).    
 *
 * All the below problems are solved in 4 approaches such as,
 *    1.Recursion -  Time: exponential time O(2^n), space complexity is O(n) which is used to store the recursion stack.
 *    2.DP: Top Down Approach or Memoization - Time and space complexity is same as memoization array size.
 *    3.DP: Bottom Up Approach or Tabulation - Time & space complexity is O(n^2) or O(n)
 *    4.Memory Optimization - This will be same time as Bottom up approach, but space efficient. Eg: Two variable approach
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

	// 4.Two variable approach:
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
		dp[0] = 1;
		dp[1] = 1;
		dp[2] = 2;
		for (int i = 3; i <= n; i++)
			dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
		return dp[n];
	}

	// 4. DP- Bottom up: Tabulation
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
}
