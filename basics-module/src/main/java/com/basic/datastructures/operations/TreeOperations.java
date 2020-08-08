package com.basic.datastructures.operations;

import com.common.model.TreeNode;

public interface TreeOperations extends DSOperations {

	public void addIterative(int data);

	public int findMin(TreeNode root);

	public int findMax(TreeNode root);

	/* BT-DFS Traversals - Recursive & Iterative
	 * 1.Recursive Approach - Preorder/Inorder/Postorder 
	 * 2.Iterative Approach:
	 * 	 - PreOrder - Stack 
	 * 	 - InOrder - Stack 
	 *   - PostOrder - 2 Stack or single stack
	 */
	public void preorder(TreeNode root);

	public void inorder(TreeNode root);

	public void postorder(TreeNode root);

	/*
	 * 2.DFS Traversals Iterative approach overview: 
	 * 		PreOrder  - Stack
	 * 		InOrder - Stack
	 * 		PostOrder - 2 Stack or single stack
	 */

	public void preorderIterative(TreeNode root);

	public void inorderIterative(TreeNode root);

	public void postorderIterative(TreeNode root);

	/* BT-BFS Traversals - Recursive & Iterative
	 * 1.Recursive Approach - Level Order
	 * 2.Iterative Approach - Level Order
	 */
	public void levelorder(TreeNode root);

	public void levelorderIterative(TreeNode root);
}
