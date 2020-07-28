package com.problems.patterns.crossdomains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.common.model.TreeNode;

/*
 * These problems can be solved by 4 ways
 * 	1.Brute Force Approach - Time: O(n^2), Space:O(1)
 *  2.Merge Sort - Time: O(nlogn), Space: O(n)
 *  3.Binary Search Tree - Time: O(n^2), Space:O(n); 
 *     But Balanced BST like AVL - Time: O(nlogn), Space: O(n)
 *  4.Binary Indexed Tree - Time: O(nlogn), Space: O(n)
 */
public class NumberInversions {

	public static void main(String[] args) {

	}

	/* Count Inversions in an array:
	 * Inversion Count for an array indicates – how far (or close) the array is from being sorted. If array is already sorted 
	 * then inversion count is 0. If array is sorted in reverse order that inversion count is the maximum.
	 * Formally speaking, two elements a[i] and a[j] form an inversion if a[i] > a[j] and i < j
	 * Example:	The sequence 2, 4, 1, 3, 5 has three inversions (2, 1), (4, 1), (4, 3)
	 */
	// Brute Force Approach: Time: O(n^2)
	public int countInversions1(int[] arr) {
		int count = 0, n = arr.length;
		for (int i = 0; i < n - 1; i++)
			for (int j = i + 1; j < n; j++)
				if (arr[i] > arr[j])
					count++;

		return count;
	}

	// Using Merge Sort Alg: Time: O(nlogn); Space: O(n)
	public int countInversions(int arr[]) {
		int n = arr.length;
		int helper[] = new int[n];
		return mergeSort1(arr, helper, 0, n - 1);
	}

	private int mergeSort1(int[] arr, int[] helper, int l, int r) {
		if (l >= r)
			return 0;

		int m = (l + r) / 2;
		int count = mergeSort1(arr, helper, l, m);
		count += mergeSort1(arr, helper, m + 1, r);
		count += merge1(arr, helper, l, m, r);

		return count;
	}

	private int merge1(int[] arr, int[] helper, int l, int m, int r) {
		for (int i = l; i <= r; i++)
			helper[i] = arr[i];

		int i = l, j = m + 1, curr = l, count = 0;
		while (i <= m && j <= r) {
			if (helper[i] <= helper[j]) {
				arr[curr++] = helper[i++];
			} else {
				/* Note: If a[i] is greater than a[j], then there are (mid – i) inversions. because left and right subarrays are sorted, 
				 * so all the remaining elements in left-subarray (a[i+1], a[i+2] … a[mid]) will be greater than a[j].
				 */
				count += m - i + 1;
				arr[curr++] = helper[j++];
			}
		}

		while (i <= m)
			arr[curr++] = helper[i++];

		return count;
	}

	// Using BIT DS: Time: O(nlog(maximumelement)).
	// Space: O(maximumelement)
	// Returns sum of arr[0..index].
	// This function assumes that the
	// array is preprocessed and partial
	// sums of array elements are stored
	// in BITree[].
	public int getSum(int[] BITree, int index) {
		int sum = 0; // Initialize result

		// Traverse ancestors of BITree[index]
		while (index > 0) {
			// Add current element of BITree to sum
			sum += BITree[index];

			// Move index to parent node in getSum View
			index -= index & (-index);
		}
		return sum;
	}

	// Updates a node in Binary Index
	// Tree (BITree) at given index
	// in BITree. The given value 'val'
	// is added to BITree[i] and all
	// of its ancestors in tree.
	public void updateBIT(int[] BITree, int n, int index, int val) {
		// Traverse all ancestors and add 'val'
		while (index <= n) {
			// Add 'val' to current node of BI Tree
			BITree[index] += val;

			// Update index to that of parent in update View
			index += index & (-index);
		}
	}

	// Returns inversion count arr[0..n-1]
	public int getInvCount(int[] arr, int n) {
		int invcount = 0; // Initialize result

		// Find maximum element in arr[]
		int maxElement = 0;
		for (int i = 0; i < n; i++)
			if (maxElement < arr[i])
				maxElement = arr[i];

		// Create a BIT with size equal to
		// maxElement+1 (Extra one is used so
		// that elements can be directly be
		// used as index)
		int[] BIT = new int[maxElement + 1];
		for (int i = 1; i <= maxElement; i++)
			BIT[i] = 0;

		// Traverse all elements from right.
		for (int i = n - 1; i >= 0; i--) {
			// Get count of elements smaller than arr[i]
			invcount += getSum(BIT, arr[i] - 1);

			// Add current element to BIT
			updateBIT(BIT, maxElement, arr[i], 1);
		}

		return invcount;
	}

	/*
	 * Count of Smaller Numbers After Self:
	 * You are given an integer array nums and you have to return a new counts array. The counts array has the property 
	 * where counts[i] is the number of smaller elements to the right of nums[i].
	 * Example:
	 * 	Input: [5,2,6,1]; Output: [2,1,1,0] 
	 */
	// Approach1: Using BST
	public List<Integer> countSmaller1(int[] nums) {
		int n = nums.length;
		Integer[] result = new Integer[n];
		TreeNode root = null;
		for (int i = n - 1; i >= 0; i--) {
			root = insert(root, nums[i], result, i, 0);
		}

		return Arrays.asList(result);
	}

	public TreeNode insert(TreeNode root, int data, Integer[] result, int i, int currCount) {
		if (root == null) {
			root = new TreeNode(data, 0);
			result[i] = currCount;
		} else if (data < root.data) {
			root.count++;
			root.left = insert(root.left, data, result, i, currCount);
		} else {
			root.right = insert(root.right, data, result, i, currCount + root.count + (data > root.data ? 1 : 0));
		}
		return root;
	}

	// Approach2: Using Merge Sort: Time: O(nlogn)
	public List<Integer> countSmaller(int[] nums) {
		List<Integer> res = new ArrayList<>();
		int[] count = new int[nums.length];
		int[] index = new int[nums.length];
		for (int i = 0; i < index.length; i++)
			index[i] = i;

		mergeSort(nums, index, count, 0, nums.length - 1);

		for (int i : count)
			res.add(i);
		return res;
	}

	private void mergeSort(int[] nums, int[] index, int[] count, int low, int high) {
		if (low >= high)
			return;
		int mid = low + (high - low) / 2;
		mergeSort(nums, index, count, low, mid);
		mergeSort(nums, index, count, mid + 1, high);
		int rightCount = 0, i = low;
		for (int j = mid + 1; i <= mid && j <= high;) {
			if (nums[index[j]] < nums[index[i]]) {
				rightCount++;
				j++;
			} else
				count[index[i++]] += rightCount;
		}

		while (i <= mid)
			count[index[i++]] += rightCount;

		merge(nums, index, low, high);
	}

	private void merge(int[] nums, int[] index, int low, int high) {
		int mid = low + (high - low) / 2;
		int[] arr = new int[high - low + 1];
		int i = low, j = mid + 1, k = 0;

		while (k < arr.length) {
			int num1 = i <= mid ? nums[index[i]] : Integer.MAX_VALUE;
			int num2 = j <= high ? nums[index[j]] : Integer.MAX_VALUE;

			arr[k++] = num1 <= num2 ? index[i++] : index[j++];
		}

		for (int p = 0; p < arr.length; p++)
			index[p + low] = arr[p];
	}

	/*
	 * Count of Range Sum:
	 * Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
	 * Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i <= j), inclusive.
	 * Input: nums = [-2,5,-1], lower = -2, upper = 2;  Output: 3 
	 */
	// Approach1: Brute Force Approach: Time:O(n^2)
	public int countRangeSum1(int[] nums, int lower, int upper) {
		int n = nums.length;
		long[] sums = new long[n + 1];
		for (int i = 0; i < n; ++i)
			sums[i + 1] = sums[i] + nums[i];
		int ans = 0;
		for (int i = 0; i < n; ++i)
			for (int j = i + 1; j <= n; ++j)
				if (sums[j] - sums[i] >= lower && sums[j] - sums[i] <= upper)
					ans++;
		return ans;
	}

	int count = 0;
	int lower;
	int upper;

	// Approach2: Using Merge Sort; Time: O(nlogn)
	/*
	 * Despite the nested loops, the time complexity of the "merge & count" stage is still linear. Because the indices
	 * i, j, k will only increase but not decrease, each of them will only traversal the right half once at most. The
	 * total time complexity of this divide and conquer solution is then O(n log n).
	 */
	public int countRangeSum2(int[] nums, int lower, int upper) {
		if (nums == null || nums.length == 0)
			return 0;

		long[] sums = new long[nums.length + 1];
		for (int i = 1; i < sums.length; i++) {
			sums[i] = nums[i - 1] + sums[i - 1];
		}

		return mergeSort(sums, lower, upper, 0, sums.length - 1);
	}

	private int mergeSort(long[] sums, int lower, int upper, int low, int high) {
		if (low >= high)
			return 0;

		int mid = low + (high - low) / 2;
		int count = mergeSort(sums, lower, upper, low, mid) + mergeSort(sums, lower, upper, mid + 1, high);
		int i = mid + 1, j = mid + 1;

		// Time complexity: for i or j, it could only be moved from mid+1 to high,
		// so this is a two pointer problem, not 2 loops
		for (int k = low; k <= mid; k++) {
			while (i <= high && sums[i] - sums[k] < lower)
				i++;
			while (j <= high && sums[j] - sums[k] <= upper)
				j++;
			count += j - i;
		}

		merge(sums, low, high);
		return count;
	}

	private void merge(long[] sums, int low, int high) {
		int mid = low + (high - low) / 2;
		int i = low, j = mid + 1, k = 0;
		long[] arr = new long[high - low + 1];

		while (k < arr.length) {
			long num1 = i <= mid ? sums[i] : Long.MAX_VALUE;
			long num2 = j <= high ? sums[j] : Long.MAX_VALUE;

			arr[k++] = num1 <= num2 ? sums[i++] : sums[j++];
		}

		for (int p = 0; p < arr.length; p++)
			sums[p + low] = arr[p];
	}

	/*
	 * Reverse Pairs:
	 * Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].
	 * You need to return the number of important reverse pairs in the given array.
	 * Example1:
	 * 	Input: [1,3,2,3,1]
	 *  Output: 2
	 */
	//Approach2: Merge Sort
	public int reversePairs(int[] nums) {
		if (nums == null || nums.length < 2)
			return 0;
		int[] helper = new int[nums.length];
		return mergeSort(nums, helper, 0, nums.length - 1);
	}

	private int mergeSort(int[] arr, int[] helper, int l, int r) {
		if (l >= r)
			return 0;

		int m = (l + r) / 2;
		int count = mergeSort(arr, helper, l, m);
		count += mergeSort(arr, helper, m + 1, r);

		//Count Reverse Pairs
		int i = l, j = m + 1;
		while (i <= m && j <= r) {
			if (arr[i] > (long) arr[j] * 2) {
				count += m - i + 1;
				j++;
			} else {
				i++;
			}
		}

		merge(arr, helper, l, m, r);
		return count;
	}

	private void merge(int[] arr, int[] helper, int l, int m, int r) {
		for (int i = l; i <= r; i++)
			helper[i] = arr[i];

		int i = l, j = m + 1, curr = l;
		while (i <= m && j <= r) {
			if (helper[i] <= helper[j]) {
				arr[curr++] = helper[i++];
			} else {
				arr[curr++] = helper[j++];
			}
		}

		while (i <= m)
			arr[curr++] = helper[i++];
	}

}