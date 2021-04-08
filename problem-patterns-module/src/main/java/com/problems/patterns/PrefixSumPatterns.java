package com.problems.patterns;

public class PrefixSumPatterns {

	/*
	 * Prefix Sum Array: Given an array arr[] of size n, its prefix sum array is
	 * another array prefixSum[] of same size such that the value of
	 * prefixSum[i] is arr[0] + arr[1] + arr[2] … arr[i].
	 */
	public int[] fillPrefixSum(int arr[]) {
		int n = arr.length;
		int[] prefixSum = new int[n];
		prefixSum[0] = arr[0];
		// Adding present element with previous element 
		for (int i = 1; i < n; ++i)
			prefixSum[i] = prefixSum[i - 1] + arr[i];

		return prefixSum;
	}

	/*
	 * Range Sum Query - Immutable -> Use Prefix Sum Given an integer array
	 * nums, find the sum of the elements between indices i and j (i <= j),
	 * inclusive. Example: Given nums = [-2, 0, 3, -5, 2, -1] sumRange(0, 2) ->
	 * 1 sumRange(2, 5) -> -1 sumRange(0, 5) -> -3
	 */
	private int[] sum;

	public void init2(int[] nums) {
		for (int i = 1; i < nums.length; i++)
			nums[i] += nums[i - 1];
		this.sum = nums;
	}

	//Time: O(n), Space: O(1)
	public int sumRange1(int[] nums, int i, int j) {
		int sum = 0;
		for (int k = i; k < j; k++)
			sum += nums[k];
		return sum;
	}

	//Time: O(1), Space: O(n)
	public int sumRange(int i, int j) {
		if (i == 0) return sum[j];

		return sum[j] - sum[i - 1];
	}

	/*
	 * Range Sum Query 2D - Immutable Given a 2D matrix, find the sum of
	 * the elements inside the rectangle defined by its upper left corner (row1,
	 * col1) and lower right corner (row2, col2).
	 */
	private int[][] lookup, matrix;

	public void init3(int[][] matrix) {
		populateLookup(matrix);
	}

	public void populateLookup(int[][] matrix) {
		if (matrix == null || matrix.length == 0) return;
		int m = matrix.length, n = matrix[0].length;
		lookup = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i == 0 && j == 0) {
					lookup[i][j] = matrix[i][j];
				} else if (i == 0 || j == 0) {
					int val = i == 0 ? lookup[i][j - 1] : lookup[i - 1][j];
					lookup[i][j] = matrix[i][j] + val;
				} else {
					lookup[i][j] = matrix[i][j] + lookup[i - 1][j] + lookup[i][j - 1] - lookup[i - 1][j - 1];
				}
			}
		}
	}

	// Approach1: Brute force approach: Time: O(m*n), Space: O(1)
	public int sumRegion1(int row1, int col1, int row2, int col2) {
		int sum = 0;
		for (int i = row1; i <= row2; i++) {
			for (int j = col1; j <= col2; j++) {
				sum += matrix[i][j];
			}
		}
		return sum;
	}

	//Approach2: Using Prefix Sum: Time: O(1), Space: O(m*n)
	public int sumRegion2(int r1, int c1, int r2, int c2) {
		if (r1 > r2 || c1 > c2) return 0;
		if (lookup.length == 0) return 0;

		int result = 0;
		if (r1 == 0 && c1 == 0) {
			result = lookup[r2][c2];
		} else if (r1 == 0 || c1 == 0) {
			int val = r1 == 0 ? lookup[r2][c1 - 1] : lookup[r1 - 1][c2];
			result = lookup[r2][c2] - val;
		} else {
			result = lookup[r2][c2] - lookup[r2][c1 - 1] - lookup[r1 - 1][c2] + lookup[r1 - 1][c1 - 1];
		}

		return result;
	}

	//Below logic has additional one index in rows and cols.
	public void populateLookup1(int[][] matrix) {
		if (matrix == null || matrix.length == 0) return;
		int m = matrix.length, n = matrix[0].length;
		lookup = new int[m + 1][n + 1];
		for (int i = 1; i <= m; i++)
			for (int j = 1; j <= n; j++)
				lookup[i][j] = lookup[i][j - 1] + lookup[i - 1][j] + matrix[i - 1][j - 1] - lookup[i - 1][j - 1];
	}

	public int sumRegion3(int row1, int col1, int row2, int col2) {
		if (lookup.length == 0) return 0;
		return lookup[row2 + 1][col2 + 1] - lookup[row2 + 1][col1] - lookup[row1][col2 + 1] + lookup[row1][col1];
	}

	/* Equilibrium point/Find Pivot Index
	 * Equilibrium position in an array is a position such that the sum 
	 * of elements before it is equal to the sum of elements after it.
	 */
	//Brute Force Approach:
	public int equilibriumPoint1(int[] a) {
		int leftSum, rightSum;
		if (a.length == 1) return 1;
		for (int i = 1; i < a.length; i++) {
			leftSum = 0;
			// Left Sum:
			for (int j = 0; j < i; j++)
				leftSum += a[j];
			// Right sum
			rightSum = 0;
			for (int k = i + 1; k < a.length; k++)
				rightSum += a[k];
			if (leftSum == rightSum) return (i + 1); // Problem expects to return position(not index)
		}
		return -1;
	}

	//Using Prefix Sum
	public int equilibriumPoint2(int[] a) {
		int leftSum = 0, sum = 0;
		if (a.length == 1) return 1;
		for (int i = 0; i < a.length; i++)
			sum += a[i];

		for (int i = 0; i < a.length; i++) {
			sum -= a[i];
			if (leftSum == sum) return i + 1;
			leftSum += a[i];
		}
		return -1;
	}

	/*
	 * Product of Array Except Self: Given an array nums of n integers where n >
	 * 1, return an array output such that output[i] is equal to the product of
	 * all the elements of nums except nums[i]. Example: Input: [1,2,3,4]
	 * Output: [24,12,8,6]
	 */
	/*
	 * Solution without using Divison Time Complexity: O(n) Space Complexity:
	 * O(n) Auxiliary Space: O(n)
	 */
	public int[] productExceptSelf1(int[] nums) {
		int n = nums.length;
		int[] left = new int[n];
		int[] right = new int[n];
		int[] prod = new int[n];

		// Multiply from left side
		left[0] = 1;
		for (int i = 1; i < n; i++)
			left[i] = left[i - 1] * nums[i - 1];

		// Multiply from right side
		right[n - 1] = 1;
		for (int i = n - 2; i >= 0; i--)
			right[i] = right[i + 1] * nums[i + 1];

		// Combine both
		for (int i = 0; i < n; i++)
			prod[i] = left[i] * right[i];

		return prod;
	}

	// Improved version
	public int[] productExceptSelf(int[] nums) {
		int n = nums.length;
		int[] prod = new int[n];

		// Multiply from left side
		int temp = 1;
		for (int i = 0; i < n; i++) {
			prod[i] = temp;
			temp *= nums[i];
		}

		// Multiply from right side
		temp = 1;
		for (int i = n - 1; i >= 0; i--) {
			prod[i] *= temp;
			temp *= nums[i];
		}

		return prod;
	}

}