package com.problems.patterns.ds;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.common.model.OrderIndex;
import com.common.model.TreeNode;

//Binary Tree Patterns - DFS/BFS
public class BTPatterns {

	/*********************** 4.BT Checking ***********************/
	// Check if given BT is Height Balanced/Balanced Binary Tree:
	// Approach 1: Time Complexity: O(n^2)
	public boolean isBinaryTreeBalanced1(TreeNode root) {
		if (root == null) return true;

		return Math.abs(heightOfTree1(root.left) - heightOfTree1(root.right)) <= 1 && isBinaryTreeBalanced1(root.left)
				&& isBinaryTreeBalanced1(root.right);
	}

	public int heightOfTree1(TreeNode root) {
		// 0 means count the nodes, -1 means count the edges
		if (root == null) return 0;
		int left = heightOfTree1(root.left);
		int right = heightOfTree1(root.right);
		return 1 + Math.max(left, right);
	}

	/* Approach 2: This approach avoids two recursive calls & check the tree 
	 * is balanced or not while calculating the height. 
	 * Time Complexity: O(n)
	 */
	public boolean isBinaryTreeBalanced2(TreeNode root) {
		return heightOfTree2(root) != Integer.MIN_VALUE;
	}

	//Modification of height of tree logic
	public int heightOfTree2(TreeNode root) {
		if (root == null) return 0;

		int left = heightOfTree2(root.left);
		if (left == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		int right = heightOfTree2(root.right);
		if (right == Integer.MIN_VALUE) return Integer.MIN_VALUE;
		return Math.abs(left - right) <= 1 ? 1 + Math.max(left, right) : Integer.MIN_VALUE;
	}

	/*********************** 5.BT Print, Path, Sum & LCA Problems ***************/
	// Lowest Common Ancestor of a BT
	/*
	 * LCA - Approach -1 : This method assumes that keys are present in Binary Tree.
	 * If one key is present and other is absent, then it returns the present key as
	 * LCA (Ideally should have returned NULL).
	 */
	public TreeNode lowestCommonAncestor1(TreeNode root, int n1, int n2) {
		if (root == null) return null;

		TreeNode left = lowestCommonAncestor1(root.left, n1, n2);
		TreeNode right = lowestCommonAncestor1(root.right, n1, n2);

		if (root.val == n1 || root.val == n2) return root;

		if (left != null && right != null) return root;

		return left != null ? left : right;
	}

	/*
	 * LCA - Approach -2; This method to handle all cases by passing boolean array to make sure that 
	 * both n1 and n2 present in the tree.
	 */
	public TreeNode lowestCommonAncestor2(TreeNode root, int n1, int n2) {
		if (root == null) return null;

		boolean[] flag = { false, false };
		TreeNode result = lca2(root, n1, n2, flag);
		return flag[0] && flag[1] ? result : null;
	}

	public TreeNode lca2(TreeNode root, int n1, int n2, boolean[] flag) {
		if (root == null) return null;

		TreeNode left = lca2(root.left, n1, n2, flag);
		TreeNode right = lca2(root.right, n1, n2, flag);

		if (root.val == n1) flag[0] = true;
		if (root.val == n2) flag[1] = true;
		if (root.val == n1 || root.val == n2) return root;

		if (left != null && right != null) return root;
		return left != null ? left : right;
	}

	// LCA- Approach-3: Find the path from root to n1 & n2. Then common node in
	// these two path is lowest common ancestor
	public Integer lowestCommonAncestor3(TreeNode root, int n1, int n2) {
		if (root == null) return null;

		ArrayList<Integer> path1 = new ArrayList<>();
		ArrayList<Integer> path2 = new ArrayList<>();

		if (!findPathFromRootToAnyNode1(root, n1, path1) || !findPathFromRootToAnyNode1(root, n2, path2)) {
			System.out.println("Element is not present in the Tree");
			return null;
		}

		int i = 0;
		for (i = 0; i < path1.size() && i < path2.size(); i++) {
			if (!(path1.get(i) == path2.get(i))) break;
		}

		return i == 0 ? path1.get(0) : path1.get(i - 1);
	}

	private boolean findPathFromRootToAnyNode1(TreeNode root, int n, ArrayList<Integer> path) {
		if (root == null) return false;

		// To find the path, first add the element in the list and check the data
		path.add(root.val);
		if (root.val == n) return true;

		boolean flag = findPathFromRootToAnyNode1(root.left, n, path);
		// This flag should check separately, otherwise when root.right returns,
		// required element will be removed
		if (!flag) flag = findPathFromRootToAnyNode1(root.right, n, path);

		// Remove the visited path from list, if node is not present in the path;
		if (!flag) path.remove(path.size() - 1);
		return flag;
	}

	// Diameter of Binary Tree:
	/*  Approach1:
	 *  Time Complexity: n+(n-1)+(n-2)+...1 => O(n^2)
	 */
	public int diameterOfTree1(TreeNode root) {
		if (root == null) return 0;
		// Get the height of left and right sub trees
		int leftHeight = heightOfTree1(root.left);
		int rightHeight = heightOfTree1(root.right);

		/* Return max of following three 1) Height of left subtree + Height of right
		 * subtree + 1; 2) Diameter of left subtree; 3) Diameter of right subtree
		 */
		return Math.max(leftHeight + rightHeight + 1,
				Math.max(diameterOfTree1(root.left), diameterOfTree1(root.right)));
	}

	int maxDiameter = 0;

	// Efficient Approach: Modification of Height calculation; Time Complexity-O(n)
	public int diameterOfTree2(TreeNode root) {
		if (root == null) return 0;
		heightOfTree3(root);
		return maxDiameter;
	}

	// Modification of height of tree logic
	public int heightOfTree3(TreeNode root) {
		if (root == null) return 0;
		int left = heightOfTree3(root.left);
		int right = heightOfTree3(root.right);
		//Record the result and move on
		maxDiameter = Math.max(maxDiameter, left + right + 1);

		return 1 + Math.max(left, right);
	}

	// Modification of previous approach using array variable:s
	public int diameterOfTree3(TreeNode root) {
		if (root == null) return 0;
		int[] max = new int[1];
		heightOfTree4(root, max);
		return max[0];
	}

	// Modification of height of tree logic
	public int heightOfTree4(TreeNode root, int[] max) {
		if (root == null) return 0;
		int left = heightOfTree4(root.left, max);
		int right = heightOfTree4(root.right, max);
		//Record the result and move on
		max[0] = Math.max(max[0], left + right + 1);

		return 1 + Math.max(left, right);
	}

	/* Binary Tree Maximum Path Sum:
	 * A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node 
	 * can only appear in the sequence at most once. Note that the path does not need to pass through the root. The path sum of a path is 
	 * the sum of the node's values in the path. 
	 * Given the root of a binary tree, return the maximum path sum of any path.
	 * Eg: [2,-1], Output: 2
	 *     [-3], Output: -3
	 */
	// Brute Force Approach:
	public int maxPathSum1(TreeNode root) {
		if (root == null) return Integer.MIN_VALUE; // Here return MIN_VALUE to handle -ve val

		int left = maxPathSumUtil1(root.left);
		int right = maxPathSumUtil1(root.right);
		return Math.max(left + right + root.val, Math.max(maxPathSum1(root.left), maxPathSum1(root.right)));
	}

	//Find max path sum
	private int maxPathSumUtil1(TreeNode root) {
		if (root == null) return 0;
		int left = maxPathSumUtil1(root.left);
		int right = maxPathSumUtil1(root.right);

		// Compare with zero to eliminate the -ve values
		return Math.max(0, root.val + Math.max(left, right));
	}

	// Efficient Approach using class variable:
	int maxValue;

	public int maxPathSum2(TreeNode root) {
		maxValue = Integer.MIN_VALUE;
		maxPathSumUtil2(root);
		return maxValue;
	}

	//Modification of max path sum
	private int maxPathSumUtil2(TreeNode node) {
		if (node == null) return 0;
		int left = maxPathSumUtil2(node.left);
		int right = maxPathSumUtil2(node.right);
		//Record the result and move on
		maxValue = Math.max(maxValue, left + right + node.val);
		// Compare with zero to eliminate the -ve values
		return Math.max(0, node.val + Math.max(left, right));
	}

	// Modification of previous approach using array variable:
	public int maxPathSum3(TreeNode root) {
		int[] max = new int[1];
		max[0] = Integer.MIN_VALUE;
		maxPathSumUtil3(root, max);
		return max[0];
	}

	//Modification of max path sum
	private int maxPathSumUtil3(TreeNode node, int[] max) {
		if (node == null) return 0;
		int left = maxPathSumUtil3(node.left, max);
		int right = maxPathSumUtil3(node.right, max);
		//Record the result and move on
		max[0] = Math.max(max[0], left + right + node.val);
		// Compare with zero to eliminate the -ve values
		return Math.max(0, node.val + Math.max(left, right));
	}

	/****************************** 6.BT Construction, Conversion **********************************/

	//Construct Binary Tree from Preorder and Inorder Traversal
	public TreeNode constructTreeFromInAndPreOrder(char[] inOrder, char[] preOrder) {
		OrderIndex preOrderIndex = new OrderIndex();
		preOrderIndex.index = 0;
		//Or Instead of OrderIndex class, array can be used to store the preorder index 
		return buildTreeFromInAndPreOrder(inOrder, preOrder, 0, inOrder.length - 1, preOrderIndex);
	}

	private TreeNode buildTreeFromInAndPreOrder(char[] inOrder, char[] preOrder, int inOrderStart, int inOrderEnd,
			OrderIndex preOrderIndex) {
		if (inOrderStart > inOrderEnd) return null;

		TreeNode node = new TreeNode(preOrder[preOrderIndex.index++]);
		if (inOrderStart == inOrderEnd) return node;

		int rootNodeIndex = search(inOrder, inOrderStart, inOrderEnd, node.ch);
		node.left = buildTreeFromInAndPreOrder(inOrder, preOrder, inOrderStart, rootNodeIndex - 1, preOrderIndex);
		node.right = buildTreeFromInAndPreOrder(inOrder, preOrder, rootNodeIndex + 1, inOrderEnd, preOrderIndex);
		return node;
	}

	private int search(char[] charArray, int start, int end, char x) {
		for (int i = start; i <= end; i++) {
			if (charArray[i] == x) return i;
		}
		return -1;
	}

	//Serialize and Deserialize a BT
	// Approach1: Convert the tree to list; use PreOrder
	public ArrayList<Integer> serialize1(TreeNode root) {
		ArrayList<Integer> result = new ArrayList<>();
		serialize1(result, root);
		result.stream().forEach(k -> System.out.print(k + " "));
		return result;
	}

	private void serialize1(ArrayList<Integer> result, TreeNode root) {
		if (root == null) {
			result.add(-1); //or result.add(null);
			return;
		}
		result.add(root.val);
		serialize1(result, root.left);
		serialize1(result, root.right);
	}

	// Approach1: Convert the list to Tree;
	static int index = 0;

	public TreeNode deserialize1(ArrayList<Integer> list) {
		if (list.size() == index || list.get(index) == -1) return null;
		TreeNode node = new TreeNode(list.get(index));
		index++;
		node.left = deserialize1(list);
		index++;
		node.right = deserialize1(list);
		return node;
	}

	// Same approach, but used class variable to increment the serialized data index
	public TreeNode deSerialize1(ArrayList<Integer> list, OrderIndex in) {
		if (list.size() == in.index || list.get(in.index) == -1) return null;
		TreeNode root = new TreeNode(list.get(in.index));
		in.index++;
		root.left = deSerialize1(list, in);
		in.index++;
		root.right = deSerialize1(list, in);
		return root;
	}

	// Approach2: Convert the tree to string; use PreOrder
	public String serialize2(TreeNode root) {
		StringBuilder sb = new StringBuilder();
		serialize2(root, sb);
		System.out.println("Serialized data: " + sb.toString());
		return sb.toString();
	}

	public void serialize2(TreeNode root, StringBuilder sb) {
		if (root == null) {
			sb.append("#,");
			return;
		}
		sb.append(root.val + ",");
		serialize2(root.left, sb);
		serialize2(root.right, sb);
	}

	// Approach2: Convert the string to tree
	public TreeNode deSerialize2(String data) {
		int[] inorder = new int[1];
		String str[] = data.split(",");
		return deSerialize2(str, inorder);
	}

	public TreeNode deSerialize2(String[] str, int[] inOrder) {
		if (str.length == inOrder[0] || str[inOrder[0]].equals("#")) return null;
		TreeNode root = new TreeNode(Integer.valueOf(str[inOrder[0]]));
		inOrder[0]++;
		root.left = deSerialize2(str, inOrder);
		inOrder[0]++;
		root.right = deSerialize2(str, inOrder);
		return root;
	}

	//Convert a given Binary Tree to Doubly Linked List
	TreeNode head = null, prev = null;

	// Convert a given Binary Tree to Doubly Linked List:(using Inorder Traversal)
	/*
	 * Approach1: The idea is to do inorder traversal of the binary tree. While
	 * doing inorder traversal, keep track of the previously visited node in a
	 * variable say prev. For every visited node, make it next of prev and previous
	 * of this node as prev. Note: Here left -> prev, right -> next
	 */
	public TreeNode convertBinaryTreeToDLL1(TreeNode root) {
		head = null;
		return binaryTreeToDLLUtil1(root);
	}

	public TreeNode binaryTreeToDLLUtil1(TreeNode root) {
		if (root == null) return null;

		binaryTreeToDLLUtil1(root.left);

		if (prev == null) {
			head = root;
		} else {
			root.left = prev;
			prev.right = root;
		}
		prev = root;
		binaryTreeToDLLUtil1(root.right);
		return head;
	}

	/*
	 * Approach2: We add nodes at the beginning of current linked list and update
	 * head of the list using pointer to head pointer. Since we insert at the
	 * beginning, we need to process leaves in reverse order. For reverse order, we
	 * first traverse the right subtree before the left subtree. i.e. do a reverse
	 * inorder traversal.
	 */
	public TreeNode convertBinaryTreeToDLL2(TreeNode root) {
		head = null;
		return binaryTreeToDLLUtil2(root);
	}

	public TreeNode binaryTreeToDLLUtil2(TreeNode root) {
		if (root == null) return null;

		binaryTreeToDLLUtil2(root.right);

		root.right = head;

		if (head != null) head.left = root;

		head = root;
		binaryTreeToDLLUtil2(root.left);
		return head;
	}

	/************************* 7.BT Traversal Modification Probs, Misc Probs **************************/
	//Connect Nodes at Same Level/Populating Next Right Pointers in Each Node I, II
	// Approach1: Using extended level order traversal - Level order traversal with null markers;
	// Time Complexity: O(n) & Space Complexity: O(n)
	public void connectNodesAtSameLevel1(TreeNode root) {
		Queue<TreeNode> q = new LinkedList<>();
		q.add(root);
		q.add(null);
		TreeNode curr = null;
		while (!q.isEmpty()) {
			curr = q.poll();
			if (curr != null) {
				curr.next = q.peek();
				if (curr.left != null) q.add(curr.left);
				if (curr.right != null) q.add(curr.right);
			} else if (!q.isEmpty()) {
				q.add(null);
			}
		}
	}

	// Approach2: Using extended pre order traversal(Works only for complete binary tree or perfect binary tree)
	// Time Complexity:O(n); Space complexity: Only recursion stack space or O(1)
	public void connectNodesAtSameLevel2(TreeNode root) {
		if (root == null) return;
		if (root.left == null && root.right == null) return;
		if (root.left != null && root.right != null) root.left.next = root.right;
		if (root.next != null) root.right.next = root.next.left;
		connectNodesAtSameLevel2(root.left);
		connectNodesAtSameLevel2(root.right);
	}

	/* Approach3 : Connect nodes at same level using constant extra space
	 *    Traverse the nextRight ptr before the left & right ptr, then traverse getNextRight().
	 */
	// i.Approach30: Recursive approach
	public void connect(TreeNode root) {
		if (root == null) return;

		if (root.left != null) {
			root.left.next = root.right != null ? root.right : findNext(root);
		}

		if (root.right != null) {
			root.right.next = findNext(root);
		}

		connect(root.right);
		connect(root.left);
	}

	private TreeNode findNext(TreeNode node) {
		TreeNode curr = node.next;
		while (curr != null) {
			if (curr.left != null) return curr.left;
			if (curr.right != null) return curr.right;
			curr = curr.next;
		}
		return null;
	}

}