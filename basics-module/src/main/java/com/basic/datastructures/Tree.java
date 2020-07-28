package com.basic.datastructures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import com.common.model.TreeNode;

public class Tree {
	public static void main(String[] arge) {
		Scanner in = new Scanner(System.in);
		BinarySearchTree bst = new BinarySearchTree();
		char ch;
		int input;
		do {
			System.out.println("Binary Search Tree Operations:");
			System.out.println("1.Insert");
			System.out.println("2.Delete");
			System.out.println("3.Find");
			System.out.println("4.FindMin");
			System.out.println("5.FindMax");
			System.out.print("Enter option:");
			input = in.nextInt();
			// BTNode root = null;
			switch (input) {
			case 1:
				System.out.println("Enter no of elements to be inserted:");
				int t = in.nextInt();
				while (t-- > 0)
					bst.insert(in.nextInt());
				break;
			case 2:
				System.out.println("Element needs to be removed in tree:");
				bst.delete(in.nextInt());
				break;
			case 3:
				System.out.println("Enter elements to search:");
				System.out.println("Element present in BST? " + bst.search(in.nextInt()));
				break;
			case 4:
				System.out.println("Minmum element in BST:" + bst.findMin(bst.root));
				break;
			case 5:
				System.out.println("Maximum element in BST:" + bst.findMax(bst.root));
				break;
			default:
				System.out.println("Please enter the valid option");
				break;
			}
			System.out.println("\nDisplay:");
			System.out.println("In Order Traversal:");
			bst.inOrderTraversal();
			System.out.println("\nPre Order Traversal:");
			bst.preOrderTraversal();
			System.out.println("\nPost Order Traversal:");
			bst.postOrderTraversal();
			System.out.println("\nLevel Order Traaversal:");
			bst.levelOrderTraversal();

			System.out.println("\nDo you want to continue(y/n):");
			ch = in.next().charAt(0);
		} while (ch == 'y' || ch == 'Y');
		System.out.println("****Thank You******");
		in.close();
	}
}

class BinarySearchTree {
	TreeNode root;

	/******************* Binary Search Tree Operations ************************/
	public void insert(int n) {
		// Recursive
		root = insertNode1(root, n);

		// Iterative
		// root = insertNode2(n);
	}

	private TreeNode insertNode1(TreeNode root, int n) {
		if (root == null) {
			root = new TreeNode(n);
		} else if (n <= root.data) {
			root.left = insertNode1(root.left, n);
		} else {
			root.right = insertNode1(root.right, n);
		}
		return root;
	}

	public TreeNode insertNode2(int n) {
		TreeNode newNode = new TreeNode(n);
		if (root == null)
			root = newNode;

		else {
			TreeNode curr = root;
			while (curr != null) {
				if (n <= curr.data) {
					if (curr.left == null) {
						curr.left = newNode;
						break;
					} else {
						curr = curr.left;
					}
				} else {
					if (curr.right == null) {
						curr.right = newNode;
						break;
					} else {
						curr = curr.right;
					}
				}
			}
		}
		return root;
	}

	public void delete(int n) {
		root = deleteNode(root, n);
	}

	private TreeNode deleteNode(TreeNode root, int n) {
		if (root == null) {
			System.out.println("Data not found");
		} else if (n < root.data) {
			root.left = deleteNode(root.left, n);
		} else if (n > root.data) {
			root.right = deleteNode(root.right, n);
		} else if (root.left != null & root.right != null) {
			int minElement = findMin(root.right);
			root.data = minElement;
			root.right = deleteNode(root.right, minElement);
		} else {
			root = root.left != null ? root.left : root.right;
		}
		return root;
	}

	public boolean search(int data) {
		return search(root, data) != null ? true : false;
	}

	private TreeNode search(TreeNode node, int x) {
		if (node == null)
			return null;

		if (x == node.data)
			return node;
		else if (x < node.data)
			return search(node.left, x);
		else
			return search(node.right, x);
	}

	public int findMin(TreeNode node) {
		if (node == null)
			return -1;

		while (node.left != null)
			node = node.left;

		return node.data;
	}

	public int findMax(TreeNode node) {
		if (node == null)
			return -1;

		while (node.right != null)
			node = node.right;

		return node.data;
	}

	/******************* Binary Tree Operations ************************/
	//TODO: Insert, Search, Update, Delete operations

	/************************ Type2: BT Traversals ************************/
	/* BT-DFS Traversals - Recursive & Iterative
	 * 1.Recursive Approach - Preorder/Inorder/Postorder 
	 * 2.Iterative Approach:
	 * 	 - PreOrder - Stack 
	 * 	 - InOrder - Stack 
	 *   - PostOrder - 2 Stack or single stack
	 */
	//1.DFS Traversals - Recursive
	public void preOrder(TreeNode root) {
		if (root == null)
			return;

		System.out.print(root.data + " ");
		preOrder(root.left);
		preOrder(root.right);
	}

	public void inOrder(TreeNode root) {
		if (root == null)
			return;

		inOrder(root.left);
		System.out.print(root.data + " ");
		inOrder(root.right);
	}

	public void postOrder(TreeNode root) {
		if (root == null)
			return;

		postOrder(root.left);
		postOrder(root.right);
		System.out.print(root.data + " ");
	}

	/*
	 * 2.DFS Traversals Iterative approach overview: 
	 * 		PreOrder  - Stack
	 * 		InOrder - Stack
	 * 		PostOrder - 2 Stack or single stack
	 */
	public void preOrderIterative(TreeNode root) {
		if (root != null) {
			Stack<TreeNode> stack = new Stack<>();
			stack.push(root);
			while (!stack.isEmpty()) {
				root = stack.pop();
				System.out.print(root.data + " ");
				if (root.right != null)
					stack.push(root.right);
				if (root.left != null)
					stack.push(root.left);
			}
		}
	}

	public void inOrderIterative(TreeNode root) {
		if (root != null) {
			Stack<TreeNode> stack = new Stack<>();
			while (true) {
				if (root != null) {
					stack.push(root);
					root = root.left;
				} else {
					if (stack.isEmpty())
						break;
					root = stack.pop();
					System.out.print(root.data + " ");
					root = root.right;
				}
			}
		}
	}

	// Reverse logic of Preorder iterative approach
	public void postOrderUsing2Stack(TreeNode root) {
		if (root != null) {
			Stack<TreeNode> s1 = new Stack<>();
			Stack<Integer> s2 = new Stack<>();
			s1.push(root);
			while (!s1.isEmpty()) {
				root = s1.pop();
				s2.push(root.data);
				if (root.left != null)
					s1.push(root.left);
				if (root.right != null)
					s1.push(root.right);
			}

			while (!s2.isEmpty())
				System.out.print(s2.pop() + " ");
		}
	}

	public void postOrderUsingStack(TreeNode root) {
		if (root != null) {
			TreeNode current = root, rightNode, topNode;
			Stack<TreeNode> stack = new Stack<>();
			while (current != null || !stack.isEmpty()) {
				if (current != null) {
					stack.push(current);
					current = current.left;
				} else {
					rightNode = stack.peek().right;
					if (rightNode != null) {
						current = rightNode;
					} else {
						topNode = stack.pop();
						System.out.print(topNode.data + " ");
						while (!stack.isEmpty() && topNode == stack.peek().right) {
							topNode = stack.pop();
							System.out.print(topNode.data + " ");
						}
					}
				}
			}
		}
	}

	/* BT-BFS Traversals - Recursive & Iterative
	 * 1.Recursive Approach - Level Order
	 * 2.Iterative Approach - Level Order
	 */
	// 1.Recursive Approach:(LevelOrder)
	public List<List<Integer>> levelByLevelOrderRecursive(TreeNode root) {
		List<List<Integer>> result = new LinkedList<>();
		levelOrder(root, result, 0);
		return result;
	}

	public void levelOrder(TreeNode root, List<List<Integer>> result, int level) {
		if (root == null)
			return;

		if (result.size() <= level)
			result.add(new ArrayList<>());
		result.get(level).add(root.data);

		levelOrder(root.left, result, level + 1);
		levelOrder(root.right, result, level + 1);
	}

	// 2.Iterative Approach using Queue:
	public void levelOrder(TreeNode root) {
		if (root == null)
			return;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			root = queue.remove();
			System.out.print(root.data + " ");
			if (root.left != null)
				queue.add(root.left);
			if (root.right != null)
				queue.add(root.right);
		}
	}

	// Tree Traversals:
	public void inOrderTraversal() {
		inOrder(root);
	}

	public void preOrderTraversal() {
		preOrder(root);
	}

	public void postOrderTraversal() {
		postOrder(root);
	}

	public void levelOrderTraversal() {
		levelOrder(root);
	}

}