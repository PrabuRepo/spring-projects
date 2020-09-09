package com.consolidated.problems.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.common.model.TreeNode;
import com.problems.patterns.ds.BSTPatterns;

public class BSTProblems {

	BSTPatterns bstPatterns = new BSTPatterns();

	/**************************** Type2: Traversal Modification **************************/
	// Minimum Absolute Difference in BST:
	int min = Integer.MAX_VALUE;
	Integer prevVal = null;

	// Approach1: Inorder Traversal Modification
	public int getMinimumDifference1(TreeNode root) {
		if (root == null) return min;
		inorder(root);
		return min;
	}

	public void inorder(TreeNode root) {
		if (root == null) return;

		inorder(root.left);
		if (prevVal != null) min = Math.min(min, root.val - prevVal);
		prevVal = root.val;
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
			if (map.get(key) == max[0]) list.add(key);

		// Copy element from list to array
		int[] result = new int[list.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = list.get(i);

		return result;
	}

	public void inorder(TreeNode root, Map<Integer, Integer> map, int[] max) {
		if (root == null) return;

		map.put(root.val, map.getOrDefault(root.val, 0) + 1);
		max[0] = Math.max(max[0], map.get(root.val));

		inorder(root.left, map, max);
		inorder(root.right, map, max);
	}

	// Approach2: Without Map
	int curCount = 1;
	int maxCount = 0;

	public int[] findMode(TreeNode root) {
		if (root == null) return new int[0];
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
		if (root == null) return;
		inorder(root.left, list);

		if (prev != null) {
			if (root.val == prev.val) curCount++;
			else curCount = 1;
		}
		prev = new TreeNode(root.val);
		if (curCount > maxCount) {
			maxCount = curCount;
			list.clear();
			list.add(root.val);
		} else if (curCount == maxCount) list.add(root.val);

		inorder(root.right, list);
	}

	public int findDistanceBwNodes(TreeNode root, int n1, int n2) {

		//Find LCA
		TreeNode lca = bstPatterns.lowestCommonAncestor(root, n1, n2);

		//Find distance
		return distance1(lca, n1) + distance1(lca, n2);
	}

	private int distance1(TreeNode node, int n) {
		int count = 0;
		while (node != null) {
			if (n == node.val) {
				return count;
			} else if (n < node.val) {
				count++;
				node = node.left;
			} else {
				count++;
				node = node.right;
			}
		}
		return count;
	}

	private int distance2(TreeNode node, int n) {
		if (node.val == n) return 0;
		if (n < node.val) return 1 + distance2(node.left, n);
		return 1 + distance2(node.right, n);
	}
}
