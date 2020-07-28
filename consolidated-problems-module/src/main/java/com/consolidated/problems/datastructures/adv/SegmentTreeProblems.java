package com.consolidated.problems.datastructures.adv;

import com.common.model.Query;

public class SegmentTreeProblems {
	public void sumRange(int[] arr, Query[] queries) {
		int n = arr.length;
		int[] segmentTree = new int[findSegmentTreeSize(n)];

		buildSumSegmentTree(arr, segmentTree, 0, n - 1, 0);
		// Find the min in given range
		for (int i = 0; i < queries.length; i++) {
			int l = queries[i].left;
			int r = queries[i].right;
			System.out.println("Sum Range: " + l + " to " + r + " : " + querySumRange(segmentTree, l, r, 0, n - 1, 0));
		}
	}

	public void buildSumSegmentTree(int[] arr, int[] segmentTree, int l, int h, int pos) {
		if (l == h) {
			segmentTree[pos] = arr[l];
			return;
		}

		int m = l + (h - l) / 2;

		buildSumSegmentTree(arr, segmentTree, l, m, 2 * pos + 1);
		buildSumSegmentTree(arr, segmentTree, m + 1, h, 2 * pos + 2);
		segmentTree[pos] = segmentTree[2 * pos + 1] + segmentTree[2 * pos + 2];
	}

	public int querySumRange(int[] segmentTree, int qLow, int qHigh, int arrLow, int arrHigh, int pos) {
		if (arrLow >= qLow && arrHigh <= qHigh) // Total overlap
			return segmentTree[pos];

		if (arrLow > qHigh || arrHigh < qLow) // No overlap
			return 0;

		int m = (arrLow + arrHigh) / 2;

		return querySumRange(segmentTree, qLow, qHigh, arrLow, m, 2 * pos + 1)
				+ querySumRange(segmentTree, qLow, qHigh, m + 1, arrHigh, 2 * pos + 2);
	}

	/* A recursive function to update the nodes which have the given  
	index in their range. The following are parameters 
	 st, si, ss and se are same as getSumUtil() 
	 i    --> index of the element to be updated. This index is in 
	          input array. 
	diff --> Value to be added to all nodes which have i in range */
	void updateValueUtil(int[] seqmentTree, int l, int h, int index, int diff, int pos) {
		// Base Case: If the input index lies outside the range of this segment
		if (l > index || h < index)
			return;

		// If the input index is in range of this node, then update the
		// value of the node and its children
		seqmentTree[pos] = seqmentTree[pos] + diff;
		if (l != h) {
			int mid = l + (h - l) / 2;
			updateValueUtil(seqmentTree, l, mid, index, diff, 2 * pos + 1);
			updateValueUtil(seqmentTree, mid + 1, h, index, diff, 2 * pos + 2);
		}
	}

	// The function to update a value in input array and segment tree.
	// It uses updateValueUtil() to update the value in segment tree
	void updateValue(int arr[], int[] segmentTree, int n, int i, int new_val) {
		// Check for erroneous input index
		if (i < 0 || i > n - 1) {
			System.out.println("Invalid Input");
			return;
		}

		// Get the difference between new value and old value
		int diff = new_val - arr[i];

		// Update the value in array
		arr[i] = new_val;

		// Update the values of nodes in segment tree
		updateValueUtil(segmentTree, 0, n - 1, i, diff, 0);
	}

	/**************** Common Method *******************/
	/* Find the segment size or no of nodes in the tree:
	 * Find the nth next 2 power value(Which is equal to no of leafs in the tree); 
	 *     Eg: for 9, it will be 16. No of nodes in the tree = 2*16 -1 (2*no of leafs - 1);
	 *  Steps:
	 *    - Find 2th power from input n(which is equal to max no of leafs in the tree), because all the inputs will be leaf node in segment tree
	 *    - max tree size = 2* no of leafs - 1;  
	 */
	private int findSegmentTreeSize(int n) {
		int h = (int) (Math.ceil(Math.log(n) / Math.log(2))); // Find 2th power from input n, which is equal to max no
																// of leafs in the tree
																// Maximum size of segment tree
		int noOfLeafs = (int) Math.pow(2, h);
		int treeSize = 2 * noOfLeafs - 1; // No of nodes in tree: 2*(No of leafs =2^h) - 1
		return treeSize;
	}
}

class TreeNode {
	TreeNode left, right;
	int val;
	int count; // No of nodes which is greater than left elements

	public TreeNode(int data, int c) {
		this.val = data;
		this.count = c;
	}
}