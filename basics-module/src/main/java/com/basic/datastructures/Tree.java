package com.basic.datastructures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.basic.datastructures.operations.TreeOperations;
import com.common.model.TreeNode;

/*
 * All the tree problems can be solved by 
 * 	1.DFS - Using Recursion or Stack
 *  2.BFS - Using Queue
 */
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
				while (t-- > 0) bst.add(in.nextInt());
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
		if (root == null) {
			root = newNode;
		} else {
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

	private TreeNode deleteNode(TreeNode curr, int n) {
		if (curr == null) {
			System.out.println("Data not found");
		} else if (n < curr.val) {
			curr.left = deleteNode(curr.left, n);
		} else if (n > curr.val) {
			curr.right = deleteNode(curr.right, n);
		} else if (curr.left != null & curr.right != null) {
			int minElement = findMin(curr.right);
			curr.val = minElement;
			curr.right = deleteNode(curr.right, minElement);
		} else {
			curr = curr.left != null ? curr.left : curr.right;
		}
		return curr;
	}

	@Override
	public boolean contains(int data) {
		return search(root, data) != null ? true : false;
	}

	private TreeNode search(TreeNode node, int x) {
		if (node == null) return null;

		if (x == node.val) return node;
		else if (x < node.val) return search(node.left, x);
		else return search(node.right, x);
	}

	@Override
	public int size() {
		return sizeOfTree(root);
	}

	private int sizeOfTree(TreeNode node) {
		if (node == null) return 0;

		return 1 + sizeOfTree(node.left) + sizeOfTree(node.right);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int findMin(TreeNode node) {
		if (node == null) return -1;

		while (node.left != null) node = node.left;

		return node.val;
	}

	@Override
	public int findMax(TreeNode node) {
		if (node == null) return -1;

		while (node.right != null) node = node.right;

		return node.val;
	}

	@Override
	public void preorder(TreeNode root) {
		if (root == null) return;

		System.out.print(root.val + " ");
		preorder(root.left);
		preorder(root.right);
	}

	@Override
	public void inorder(TreeNode root) {
		if (root == null) return;

		inorder(root.left);
		System.out.print(root.val + " ");
		inorder(root.right);
	}

	@Override
	public void postorder(TreeNode root) {
		if (root == null) return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(root.val + " ");
	}

	@Override
	public void preorderIterative(TreeNode root) {
		preorderTraversal(root);

		preorderTraversal2(root);
	}

	public List<Integer> preorderTraversal(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		if (root == null) return result;

		java.util.Stack<TreeNode> stack = new java.util.Stack<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			root = stack.pop();
			result.add(root.val);
			if (root.right != null) stack.push(root.right);
			if (root.left != null) stack.push(root.left);
		}

		return result;
	}

	public List<Integer> preorderTraversal2(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		if (root == null) return result;

		TreeNode curr = root;
		java.util.Deque<TreeNode> stack = new ArrayDeque<>();

		while (!stack.isEmpty() || curr != null) {
			if (curr != null) {
				result.add(curr.val); // Add before going to children
				stack.push(curr);
				curr = curr.left;
			} else {
				curr = stack.pop();
				curr = curr.right;
			}
		}
		return result;
	}

	@Override
	public void inorderIterative(TreeNode root) {
		inorderTraversal(root);
	}

	public List<Integer> inorderTraversal(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		if (root == null) return result;

		TreeNode curr = root;
		java.util.Deque<TreeNode> stack = new ArrayDeque<>();

		while (!stack.isEmpty() || curr != null) {
			if (curr != null) {
				stack.push(curr);
				curr = curr.left;
			} else {
				curr = stack.pop();
				result.add(curr.val); // Add after all left children
				curr = curr.right;
			}
		}

		return result;
	}

	@Override
	public void postorderIterative(TreeNode root) {
		postorderTraversal1(root);
		postorderTraversal2(root);
		postorderTraversal3(root);
	}

	public List<Integer> postorderTraversal1(TreeNode root) {
		java.util.LinkedList<Integer> result = new java.util.LinkedList<>();
		//List<Integer> result = new ArrayList<>();
		if (root == null) return result;

		java.util.Stack<TreeNode> stack = new java.util.Stack<>();
		stack.push(root);

		while (!stack.isEmpty()) {
			TreeNode curr = stack.pop();
			result.addFirst(curr.val);

			if (curr.left != null) stack.push(curr.left);
			if (curr.right != null) stack.push(curr.right);
		}

		return result;
	}

	public List<Integer> postorderTraversal2(TreeNode root) {
		java.util.LinkedList<Integer> result = new java.util.LinkedList<>();
		//List<Integer> result = new ArrayList<>();
		if (root == null) return result;

		TreeNode curr = root;
		java.util.Deque<TreeNode> stack = new ArrayDeque<>();

		while (!stack.isEmpty() || curr != null) {
			if (curr != null) {
				result.addFirst(curr.val); // Reverse the process of preorder
				// result.add(0, curr.val); //for ArrayList - Time: O(n)
				stack.push(curr);
				curr = curr.right; // Reverse the process of preorder
			} else {
				curr = stack.pop();
				curr = curr.left; // Reverse the process of preorder
			}
		}

		return result;
	}

	public List<Integer> postorderTraversal3(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		if (root == null) return result;
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
					result.add(topNode.val);
					//To handle continuous right subtree
					while (!stack.isEmpty() && topNode == stack.peek().right) {
						topNode = stack.pop();
						result.add(topNode.val);
					}
				}
			}
		}
		return result;
	}

	//DFS:
	@Override
	public void levelorder(TreeNode root) {
	}

	@Override
	public void levelorderIterative(TreeNode root) {
		if (root == null) return;
		java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			root = queue.remove();
			System.out.print(root.val + " ");
			if (root.left != null) queue.add(root.left);
			if (root.right != null) queue.add(root.right);
		}

	}

	@Override
	public void set(int index, int data) {
		// TODO Auto-generated method stub

	}

	//Build a Tree from array using level order traversal Eg: {1, -1, 1, -1, -1, -1, 1}
	public TreeNode buildTree(int[] arr) {
		int i = 0, n = arr.length;
		java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
		TreeNode root = new TreeNode(arr[i++]);
		queue.add(root);

		while (!queue.isEmpty() && i < n) {
			TreeNode top = queue.poll();
			if (i < n && arr[i] != -1 && top != null) {
				TreeNode left = new TreeNode(arr[i]);
				top.left = left;
				queue.add(left);
			} else {
				queue.add(null);
			}
			i++;

			if (i < n && arr[i] != -1 && top != null) {
				TreeNode right = new TreeNode(arr[i]);
				top.right = right;
				queue.add(right);
			} else {
				queue.add(null);
			}
			i++;
		}

		return root;
	}

}