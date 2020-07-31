package com.problems.patterns.crossdomains;

import java.util.Arrays;
import java.util.HashSet;

import com.common.model.TreeNode;

public class ConsecutiveSeqPatterns {
	/*
	 * Longest Consecutive Sequence:
	 * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
	 * Your algorithm should run in O(n) complexity.
	 * Example: Input: [100, 4, 200, 1, 3, 2]; Output: 4
	 * Approaches:
	 * 1.Brute Force: Check elements one by one for the consective elements -O(n^3)
	 * 2.Sorting and linear search - O(nlogn)
	 * 3.Use Hash set & up/down sequence search - O(n)
	 * 4.Union Find: 
	 */

	// Approach1:
	public int longestConsecutive1(int[] nums) {
		int longestSeq = 0, count, curr;
		for (int i = 0; i < nums.length; i++) {
			count = 1;
			curr = nums[i];
			while (contains(nums, curr + 1)) {
				count++;
				curr++;
			}

			longestSeq = Math.max(longestSeq, count);
		}

		return longestSeq;
	}

	private boolean contains(int[] arr, int num) {
		for (int i = 0; i < arr.length; i++)
			if (arr[i] == num)
				return true;

		return false;
	}

	// Approach2:
	public int longestConsecutive2(int[] nums) {
		if (nums.length == 0)
			return 0;

		// Sort
		Arrays.sort(nums);

		// Linear Search:
		int longestSeq = 0, count = 1;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i - 1] == nums[i]) {
				continue;
			} else if (nums[i - 1] + 1 == nums[i]) {
				count++;
			} else {
				longestSeq = Math.max(longestSeq, count);
				count = 1;
			}
		}

		return Math.max(longestSeq, count);
	}

	// Approach3:
	public int longestConsecutive(int[] nums) {
		HashSet<Integer> set = new HashSet<>();
		// Add all the elements in set and also remove duplicates
		for (int num : nums)
			set.add(num);

		// Up/Down search
		int longestSeq = 0, up, down;
		for (int i = 0; i < nums.length; i++) {
			if (!set.contains(nums[i]))
				continue;

			set.remove(nums[i]);

			// Down Search: Decrement the element by one and search
			down = nums[i];
			while (set.contains(down - 1)) {
				down--;
				set.remove(down);
			}

			// Up Search: Decrement the element by one and search
			up = nums[i];
			while (set.contains(up + 1)) {
				up++;
				set.remove(up);
			}

			longestSeq = Math.max(longestSeq, up - down + 1);
		}

		return longestSeq;
	}

	/* Binary Tree Longest Consecutive Sequence:
	 * Given a binary tree, find the length of the longest consecutive sequence path. The path refers to any sequence of nodes
	 * from some starting node to any node in the tree along the parent-child connections. The longest consecutive path need 
	 * to be from parent to child (cannot be the reverse).
	 */
	public int longestConsecutive(TreeNode root) {
		if (root == null)
			return 0;
		return lcs(root, root.data, 0);
	}

	private int lcs(TreeNode node, int next, int count) {
		if (node == null)
			return count;

		count = (node.data == next) ? count + 1 : 1;
		return Math.max(count, Math.max(lcs(node.left, node.data + 1, count), lcs(node.right, node.data + 1, count)));
	}

	/*
	 * Binary Tree Longest Consecutive Sequence II:
	 * Given a binary tree, find the length(number of nodes) of the longest consecutive sequence(Monotonic and adjacent node values differ by 1) path.
	 * The path could be start and end at any node in the tree
	 * Example 1:
			Input: {1,2,0,3}
			Output: 4
			Explanation:
			    1
			   / \
			  2   0
			 /
			3
			0-1-2-3
	 */

	int maxVal = 0;

	public int longestConsecutive2(TreeNode root) {
		longestPath(root);
		return maxVal;
	}

	public int[] longestPath(TreeNode root) {
		if (root == null)
			return new int[] { 0, 0 };
		int inr = 1, dcr = 1;
		if (root.left != null) {
			int[] l = longestPath(root.left);
			if (root.left.data == root.data - 1)
				dcr = l[1] + 1;
			else if (root.left.data == root.data + 1)
				inr = l[0] + 1;
		}
		if (root.right != null) {
			int[] r = longestPath(root.right);
			if (root.right.data == root.data - 1)
				dcr = Math.max(dcr, r[1] + 1);
			else if (root.right.data == root.data + 1)
				inr = Math.max(inr, r[0] + 1);
		}
		maxVal = Math.max(maxVal, dcr + inr - 1);
		return new int[] { inr, dcr };
	}
}