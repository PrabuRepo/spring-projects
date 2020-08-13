package com.consolidated.problems.operations;

import java.util.List;

import com.common.model.TreeNode;

/*
 * All the tree problems can be solved by 
 * 	1.DFS - Using Recursion or Stack  
 *  2.BFS - Using Queue
 *  
 *  Note: In all the problem 1 denotes DFS and 2 denotes BFS.
 */
public interface TreeProperties {
	// Count no of nodes in the BT
	public int sizeOfTree1(TreeNode root);

	public int sizeOfTree2(TreeNode root);

	//Count Complete Tree Nodes
	public int countNodes1(TreeNode root);

	public int countNodes2(TreeNode root);

	/* Height of Binary Tree == Depth of  Binary Tree == Level of Binary Tree
	 * Height of the tree: Height from root to longest leaf
	 * Maximum Depth of Binary Tree or Depth of the Tree: Path/no of nodes from root to longest leaf Depth of the tree
	 */
	public int heightOfTree1(TreeNode root);

	public int heightOfTree2(TreeNode root);

	// Minimum Depth of a Binary Tree - Minimum Depth of Binary Tree is equal to the nearest leaf from root.
	public int minDepthOfTree1(TreeNode root);

	public int minDepthOfTree2(TreeNode root);

	public int heightOfNode(TreeNode root, int data);

	public int depthOfNode(TreeNode root, int data);

	public int levelOfNode1(TreeNode root, int data);

	public int levelOfNode2(TreeNode root, int data);

	//	Number of leaf nodes
	public int countLeafNodes1(TreeNode root);

	public int countLeafNodes2(TreeNode root);

	public void printLeafNodes(TreeNode root);

	public int sumOfLeftLeaf(TreeNode root);

	/* Find Leaves of Binary Tree: The key to solve this problem is converting the problem to be finding the index of the element
	 * in the result list. Then this is a typical DFS problem on trees.
	 */
	public List<List<Integer>> findLeaves(TreeNode root);

	public List<Double> averageOfLevels1(TreeNode root);

	public List<Double> averageOfLevels2(TreeNode root);

	public TreeNode levelOrderSuccessor(TreeNode root, TreeNode key);

	public TreeNode levelOrderPredecessor(TreeNode root, TreeNode key);

	public void swapNodes(TreeNode root);

}