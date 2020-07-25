package com.consolidated.problems.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.common.model.TreeNode;

public class BSTProblems {
	/**************************** Type2: Traversal Modification **************************/
	// Minimum Absolute Difference in BST:
	int min = Integer.MAX_VALUE;
	Integer prevVal = null;

	// Approach1: Inorder Traversal Modification
	public int getMinimumDifference1(TreeNode root) {
		if (root == null)
			return min;
		inorder(root);
		return min;
	}

	public void inorder(TreeNode root) {
		if (root == null)
			return;

		inorder(root.left);
		if (prevVal != null)
			min = Math.min(min, root.data - prevVal);
		prevVal = root.data;
		inorder(root.right);
	}

	// Find Mode in Binary Search Tree:
	// Approach1: Brute Force Approach using Map
	public int[] findMode1(TreeNode root) {
		Map<Integer, Integer> map = new HashMap<>();
		int[] max = new int[1];
		max[0] = 0;

		inorder(root, map, max);

		// Populate most frequent(max) elements from map into list
		List<Integer> list = new LinkedList<>();
		for (int key : map.keySet())
			if (map.get(key) == max[0])
				list.add(key);

		// Copy element from list to array
		int[] result = new int[list.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = list.get(i);

		return result;
	}

	public void inorder(TreeNode root, Map<Integer, Integer> map, int[] max) {
		if (root == null)
			return;

		map.put(root.data, map.getOrDefault(root.data, 0) + 1);
		max[0] = Math.max(max[0], map.get(root.data));

		inorder(root.left, map, max);
		inorder(root.right, map, max);
	}

	// Approach2: Without Map
	int curCount = 1;
	int maxCount = 0;

	public int[] findMode(TreeNode root) {
		if (root == null)
			return new int[0];
		List<Integer> list = new ArrayList<>();
		inorder(root, list);
		int[] res = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
			res[i] = list.get(i);
		return res;
	}

	// Simple Modification of inorder traversal
	TreeNode prev = null;

	private void inorder(TreeNode root, List<Integer> list) {
		if (root == null)
			return;
		inorder(root.left, list);

		if (prev != null) {
			if (root.data == prev.data)
				curCount++;
			else
				curCount = 1;
		}
		prev = new TreeNode(root.data);
		if (curCount > maxCount) {
			maxCount = curCount;
			list.clear();
			list.add(root.data);
		} else if (curCount == maxCount)
			list.add(root.data);

		inorder(root.right, list);
	}
}
