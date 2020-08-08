package com.basic.datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.basic.datastructures.operations.TreeOperations;
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
					bst.add(in.nextInt());
				break;
			case 2:
				System.out.println("Element needs to be removed in tree:");
				bst.remove(in.nextInt());
				break;
			case 3:
				System.out.println("Enter elements to search:");
				System.out.println("Element present in BST? " + bst.contains(in.nextInt()));
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
			bst.inorder(bst.root);
			System.out.println("\nPre Order Traversal:");
			bst.preorder(bst.root);
			System.out.println("\nPost Order Traversal:");
			bst.postorder(bst.root);
			System.out.println("\nLevel Order Traaversal:");
			bst.levelorder(bst.root);

			System.out.println("\nDo you want to continue(y/n):");
			ch = in.next().charAt(0);
		} while (ch == 'y' || ch == 'Y');
		System.out.println("****Thank You******");
		in.close();
	}
}

class BinarySearchTree implements TreeOperations {
	TreeNode root;

	// Recursive
	@Override
	public void add(int data) {
		root = add(root, data);
	}

	private TreeNode add(TreeNode root, int n) {
		if (root == null) {
			root = new TreeNode(n);
		} else if (n <= root.val) {
			root.left = add(root.left, n);
		} else {
			root.right = add(root.right, n);
		}
		return root;
	}

	@Override
	public void addIterative(int data) {
		TreeNode newNode = new TreeNode(data);
		if (root == null)
			root = newNode;

		else {
			TreeNode curr = root;
			while (curr != null) {
				if (data <= curr.val) {
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
	}

	@Override
	public boolean remove(int data) {
		root = deleteNode(root, data);
		return true;
	}

	private TreeNode deleteNode(TreeNode root, int n) {
		if (root == null) {
			System.out.println("Data not found");
		} else if (n < root.val) {
			root.left = deleteNode(root.left, n);
		} else if (n > root.val) {
			root.right = deleteNode(root.right, n);
		} else if (root.left != null & root.right != null) {
			int minElement = findMin(root.right);
			root.val = minElement;
			root.right = deleteNode(root.right, minElement);
		} else {
			root = root.left != null ? root.left : root.right;
		}
		return root;
	}

	@Override
	public boolean contains(int data) {
		return search(root, data) != null ? true : false;
	}

	private TreeNode search(TreeNode node, int x) {
		if (node == null)
			return null;

		if (x == node.val)
			return node;
		else if (x < node.val)
			return search(node.left, x);
		else
			return search(node.right, x);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int findMin(TreeNode node) {
		if (node == null)
			return -1;

		while (node.left != null)
			node = node.left;

		return node.val;
	}

	@Override
	public int findMax(TreeNode node) {
		if (node == null)
			return -1;

		while (node.right != null)
			node = node.right;

		return node.val;
	}

	@Override
	public void preorder(TreeNode root) {
		if (root == null)
			return;

		System.out.print(root.val + " ");
		preorder(root.left);
		preorder(root.right);
	}

	@Override
	public void inorder(TreeNode root) {
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(root.val + " ");
		inorder(root.right);
	}

	@Override
	public void postorder(TreeNode root) {
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(root.val + " ");
	}

	@Override
	public void preorderIterative(TreeNode root) {
		if (root == null)
			return;
		java.util.Stack<TreeNode> stack = new java.util.Stack<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			root = stack.pop();
			System.out.print(root.val + " ");
			if (root.right != null)
				stack.push(root.right);
			if (root.left != null)
				stack.push(root.left);
		}
	}

	@Override
	public void inorderIterative(TreeNode root) {
		if (root == null)
			return;
		java.util.Stack<TreeNode> stack = new java.util.Stack<>();
		while (true) {
			if (root != null) {
				stack.push(root);
				root = root.left;
			} else {
				if (stack.isEmpty())
					break;
				root = stack.pop();
				System.out.print(root.val + " ");
				root = root.right;
			}
		}
	}

	@Override
	public void postorderIterative(TreeNode root) {
		postOrderUsing2Stack(root);

		postOrderUsingStack(root);
	}

	// Reverse logic of Preorder iterative approach
	private void postOrderUsing2Stack(TreeNode root) {
		if (root == null)
			return;
		java.util.Stack<TreeNode> s1 = new java.util.Stack<>();
		java.util.Stack<Integer> s2 = new java.util.Stack<>();
		s1.push(root);
		while (!s1.isEmpty()) {
			root = s1.pop();
			s2.push(root.val);
			if (root.left != null)
				s1.push(root.left);
			if (root.right != null)
				s1.push(root.right);
		}

		while (!s2.isEmpty())
			System.out.print(s2.pop() + " ");
	}

	private void postOrderUsingStack(TreeNode root) {
		if (root == null)
			return;
		TreeNode current = root, rightNode, topNode;
		java.util.Stack<TreeNode> stack = new java.util.Stack<>();
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
					System.out.print(topNode.val + " ");
					while (!stack.isEmpty() && topNode == stack.peek().right) {
						topNode = stack.pop();
						System.out.print(topNode.val + " ");
					}
				}
			}
		}
	}

	//Recursive
	@Override
	public void levelorder(TreeNode root) {
		List<List<Integer>> result = new java.util.LinkedList<>();
		levelOrder(root, result, 0);
		result.forEach(k -> System.out.println(k));
	}

	public void levelOrder(TreeNode root, List<List<Integer>> result, int level) {
		if (root == null)
			return;

		if (result.size() <= level)
			result.add(new ArrayList<>());
		result.get(level).add(root.val);

		levelOrder(root.left, result, level + 1);
		levelOrder(root.right, result, level + 1);
	}

	@Override
	public void levelorderIterative(TreeNode root) {
		if (root == null)
			return;
		java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			root = queue.remove();
			System.out.print(root.val + " ");
			if (root.left != null)
				queue.add(root.left);
			if (root.right != null)
				queue.add(root.right);
		}

	}

	@Override
	public void set(int index, int data) {
		// TODO Auto-generated method stub

	}

}