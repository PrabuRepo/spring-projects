package com.consolidated.problems.operations;

import java.util.List;

import com.common.model.TreeNode;

public interface TreePaths {

	//Find path/distance from root to leaf
	public void pathFromRootToLeaf1(TreeNode root);

	//Find path/distance from root to leaf - Concatenate the root to leaf path
	public List<String> pathFromRootToLeaf2(TreeNode root);

	//Find the path/distance between two nodes
	public void pathFromRootToAnyNode(TreeNode root, int n);

	//Find the path/distance between any two nodes
	public int findDistanceBwNodes(TreeNode root, int n1, int n2);

	//The diameter of a binary tree is the length of the longest path between any two nodes in a tree. 
	public int diameter(TreeNode root);

	public void lowestCommonAncestor(TreeNode root, int n1, int n2);

	//Path Sum Problems:
	//Path Sum I: Find path/distance from root to leaf
	public boolean hasPathSumFromRootToLeaf1(TreeNode root, int sum);

	//Check the root to leaf path for the given Sum and print the result:
	public List<Integer> hasPathSumFromRootToLeaf2(TreeNode root, int sum);

	//Path SumII: Find all root-to-leaf paths where each path's sum equals the given sum
	public List<List<Integer>> findRootToLeafPathSum(TreeNode root, int sum);

	// Path Sum III: Find the number of paths that sum to a given value. The path does not need to start or end at the root or a leaf, but it must go downwards
	public int findSumBwNodes1(TreeNode root, int sum);

	public int findSumBwNodes2(TreeNode root, int sum);

	//Binary Tree Maximum Path Sum: Path can be nodes from some starting node to any node in the tree
	public void maxPathSum(TreeNode root);

	/* Most Frequent Subtree Sum:
	 * Given the root of a tree, you are asked to find the most frequent subtree sum. The subtree sum of a node is defined as the sum of all the
	 * node values formed by the subtree rooted at that node (including the node itself). So what is the most frequent subtree sum value? If there 
	 * is a tie, return all the values with the highest frequency in any order.
	 */
	public int[] findFrequentTreeSum(TreeNode root);
}
