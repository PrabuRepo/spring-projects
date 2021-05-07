package com.basic.algorithms;

import java.util.Arrays;

/* What is Dynamic Programming?
 * Dynamic Programming (DP) is an algorithmic technique for solving an optimization problem by breaking it down into simpler subproblems 
 * and utilizing the fact that the optimal solution to the overall problem depends upon the optimal solution to its subproblems.
 * 
 * Dynamic Programming is a method for solving a complex problem by breaking it down into a collection of simpler subproblems, solving 
 * each of those subproblems just once, and storing their solutions using a memory-based data structure (array, map,etc). Each of the 
 * subproblem solutions is indexed in some way, typically based on the values of its input parameters, so as to facilitate its lookup. 
 * So the next time the same subproblem occurs, instead of recomputing its solution, one simply looks up the previously computed solution, 
 * thereby saving computation time. This technique of storing solutions to subproblems instead of recomputing them is called memoization.
 * 
 * DP Quote: "Those who cannot remember the past are condemned to repeat it" 
 * 
 * Characteristics of Dynamic Programming:
 * 	1.Optimal Substructure Property - Use sub problems: Any problem has optimal substructure property if its overall optimal solution can 
 * 	  be constructed from the optimal solutions of its subproblems.   
 * 	2.Overlapping Subproblems - Reuse sub problems: Subproblems are smaller versions of the original problem. Any problem has overlapping 
 *    sub-problems if finding its solution involves solving the same subproblem multiple times. 
 *      Eg: Fibonacci numbers, as we know, Fib(n) = Fib(n-1) + Fib(n-2). This clearly shows that a problem of size �n� has been reduced
 *      to subproblems of size �n-1� and �n-2�. Therefore, Fibonacci numbers have optimal substructure property.
 *   
 * DP offers two methods to solve a problem:
 * 1.Top-down with Memoization: 
 * 	 In this approach, we try to solve the bigger problem by recursively finding the solution to smaller 
 * 	 sub-problems. Whenever we solve a sub-problem, we cache its result so that we don�t end up solving it repeatedly if it�s called 
 *   multiple times. Instead, we can just return the saved result. This technique of storing the results of already solved subproblems
 *   is called Memoization.
 * 2.Bottom-up with Tabulation:
 * 	 Tabulation is the opposite of the top-down approach and avoids recursion. In this approach, we solve the problem �bottom-up� 
 *   (i.e. by solving all the related sub-problems first). This is typically done by filling up an n-dimensional table. Based on the 
 *   results in the table, the solution to the top/original problem is then computed. 
 *   Tabulation is the opposite of Memoization, as in Memoization we solve the problem and maintain a map of already solved sub-problems.
 *   In other words, in memoization, we do it top-down in the sense that we solve the top problem first (which typically recurses down to 
 *   solve the sub-problems).    
 *
 * All the DP problems are solved in following 3 approaches such as,
 *    1.Recursion -  Time: exponential time O(2^n), space complexity is O(n) which is used to store the recursion stack.
 *    2.DP: Top Down Approach or Memoization - Time and space complexity is equal to memoization array size. Mostly this approach takes 
 *    										   same Time & Space complexity as Bottom up approach. 
 *    3.DP: Bottom Up Approach or Tabulation - Time & space complexity is O(n^2) or O(n)
 *    
 * Imp Note: All the recursion problems needs below two points
 * 		1. Base case -> Its common for Recursion solution, memoization and bottom up approach
 * 		2. Recursive function(Sub problems + Relationship between sub problems)
 */
public class DynamicProgramming {

	// Fibonacci Number calculation:
	// 1.using recursive function
	public int fibonacci1(int n) {
		if (n <= 1) return n;
		return fibonacci1(n - 1) + fibonacci1(n - 2);
	}

	// 2.DP: Top Down or Memoization
	public int fibonacci2(int n) {
		int[] dp = new int[n + 1];
		Arrays.fill(dp, -1);
		return fibonacci2(n, dp);
	}

	public int fibonacci2(int n, int[] dp) {
		if (dp[n] != -1) return dp[n];
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

	// 4.DP-Bottom Up: Two variable approach:
	// Fibonacci series: 0,1,1,2,3,5,8,13,21,34...
	public int fibonacci4(int n) {
		if (n == 0) return 0;
		int prev = 0, curr = 1;
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
		if (n < 0) return 0;
		else if (n == 0) return 1;
		return tripleSteps1(n - 1) + tripleSteps1(n - 2) + tripleSteps1(n - 3);
	}

	// 2. DP- Top down: Memoization
	public int tripleSteps2(int n) {
		if (n <= 0) return 0;
		int[] dp = new int[n + 1];
		return tripleStepsUtil(n, dp);
	}

	public int tripleStepsUtil(int n, int[] dp) {
		if (n < 0) return 0;
		else if (n == 0) return 1;
		if (dp[n] == 0) {
			dp[n] = tripleStepsUtil(n - 1, dp) + tripleStepsUtil(n - 2, dp) + tripleStepsUtil(n - 3, dp);
		}
		return dp[n];
	}

	// 3. DP- Bottom up: Tabulation;
	public int tripleSteps3(int n) {
		if (n <= 0) return 0;
		else if (n == 1) return 1;
		else if (n == 2) return 2;
		else if (n == 3) return 4;
		int[] dp = new int[n + 1];
		dp[0] = 1;
		dp[1] = 1;
		dp[2] = 2;
		for (int i = 3; i <= n; i++) {
			dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
		}

		//or this alternative Approach:
		dp[0] = 1;
		for (int i = 1; i <= n; i++) {
			if (i - 1 >= 0) dp[i] += dp[i - 1];
			if (i - 2 >= 0) dp[i] += dp[i - 2];
			if (i - 3 >= 0) dp[i] += dp[i - 3];
			//dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
		}
		return dp[n];
	}

	//This is similar to above approach
	public int tripleSteps32(int n) {
		if (n <= 0) return 0;

		int[] dp = new int[n + 1];
		dp[0] = 1;

		for (int i = 0; i < n; i++) {
			if (i + 1 <= n) dp[i + 1] += dp[i];
			if (i + 2 <= n) dp[i + 2] += dp[i];
			if (i + 3 <= n) dp[i + 3] += dp[i];
		}
		return dp[n];
	}

	// 4. DP- Bottom up: Two variable approach
	public int tripleSteps4(int n) {
		if (n <= 0) return 0;
		else if (n == 1) return 1;
		else if (n == 2) return 2;

		int prev1 = 1, prev2 = 1, curr = 2;
		for (int i = 3; i <= n; i++) {
			int sum = prev1 + prev2 + curr;
			prev1 = prev2;
			prev2 = curr;
			curr = sum;
		}
		return curr;
	}

	public static void main(String[] args) {
		DynamicProgramming ob = new DynamicProgramming();
		int n = 34;
		System.out.println("Triple Steps: " + ob.tripleSteps3(n));
		System.out.println("Triple Steps: " + ob.tripleSteps32(n));
		System.out.println("Triple Steps: " + ob.tripleSteps4(n));
	}
}
