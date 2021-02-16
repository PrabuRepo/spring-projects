package com.problems.patterns.dp;

import java.util.Arrays;

import com.common.utilities.Utils;

/* All the below problems are solved in 4 approaches such as,
 *    1.Recursion -  Time: exponential time O(2^n), space complexity is O(n) which is used to store the recursion stack.
 *    2.DP: Top Down Approach or Memoization - Time and space complexity is same as memoization array size. Mostly same as Bottomup space and time.
 *    3.DP: Bottom Up Approach or Tabulation - Time & space complexity is O(n^2) or O(n)
 *    4.Memory Optimization - This will be same time as Bottom up approach, but space efficient. Eg: Two variable approach
 *    
 *  Imp Note: All the recursion problems needs below two points
 *  		1. Base case -> Its common for Recursion solution, memoization and bottom up approach
 *  		2. Recursive function(Sub problems solution)
 */
public class DPArraySeqPatterns {

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
				if (arr[j] < arr[i] && dp[i] < dp[j] + 1) {
					dp[i] = dp[j] + 1;
				}
			}
			max = Math.max(max, dp[i]);
		}
		return max;
	}

	// Binary Search Approach : O(nlogn)
	public int LIS4(int[] nums) {
		int[] dp = new int[nums.length];
		int size = 0;
		for (int num : nums) {
			int l = 0, h = size;
			while (l != h) {
				int m = (l + h) / 2;
				if (num > dp[m]) l = m + 1;
				else h = m;
			}
			dp[l] = num;
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