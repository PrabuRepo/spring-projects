package com.consolidated.problems.operations;

import java.util.List;

import com.common.model.TreeNode;

public interface TreeViews {
	// Vertical View: DFS Approach
	public void verticalViewTraversal1(TreeNode root);

	// Vertical View: BFS Approach
	public void verticalViewTraversal2(TreeNode root);

	// Top View - DFS Approach
	public void topViewTraversal1(TreeNode root);

	// Top View - BFS Approach
	public void topViewTraversal2(TreeNode root);

	// Bottom View - DFS Approach
	public void bottomViewTraversal1(TreeNode root);

	// Bottom View - BFS Approach
	public void bottomViewTraversal2(TreeNode root);

	// Diagonal View - DFS Approach
	public void diagonalTraversal(TreeNode root);

	/*
	 * Left View of Tree - BFS Approach: The left view contains all nodes that are
	 * first nodes in their levels. A simple solution is to do level order traversal
	 * and print the first node in every level.
	 */
	public void leftViewOfTree1(TreeNode root);

	// Left View - DFS Approach
	public void leftViewOfTree2(TreeNode root);

	/*
	 * Right View of Tree - BFS Approach: The Right view contains all nodes that are
	 * last nodes in their levels. A simple solution is to do level order traversal
	 * and print the last node in every level.
	 */
	public void rightViewOfTree1(TreeNode root);

	//DFS Approach
	public void rightViewOfTree2(TreeNode root);

	/*
	 * Right Side View - DFS Approach: The core idea of this algorithm: 1.Each
	 * depth/level of the tree only select one node. 2. View depth/level is current
	 * size of result list.
	 */
	public List<Integer> rightSideView(TreeNode root);

	//How to print all diagonal's sums for a given binary tree
	public List<Integer> diagonalSum(TreeNode root);

	public List<Integer> boundaryOfBinaryTree(TreeNode root);
}
