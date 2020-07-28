package com.consolidated.problems.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

import com.common.model.OrderIndex;
import com.common.model.QueuePack;
import com.common.model.TreeNode;

public class BTProblems {

	/************************ Type1: Basics & Properties ************************/
	/* Height of Binary Tree:
	 * Recursive Approach: - Height - Path/no of nodes from any node to leaf -
	 * Height of the tree - Height from root to longest leaf - Depth of the tree ==
	 * Height of the tree == No of levels in the tree == Depth of the Tree 
	 * Maximum Depth/Depth/Level/Height of a Tree - Recursive & Iterative:
	 */

	//Height of the tree - Recursive approach
	public int heightOfTree1(TreeNode root) {
		if (root == null)
			return 0; // 0 means count the nodes, -1 means count the edges
		return 1 + Math.max(heightOfTree1(root.left), heightOfTree1(root.right));
	}

	// Height of the tree - Iterative Approach
	public int heightOfTree2(TreeNode root) {
		int nodeCount = 0, height = 0; // 0 means count the nodes, -1 means count the edges
		if (root != null) {
			TreeNode temp;
			Queue<TreeNode> queue = new LinkedList<>();
			queue.add(root);
			while (!queue.isEmpty()) {
				nodeCount = queue.size();
				height++;
				while (nodeCount > 0) {
					temp = queue.poll();
					if (temp.left != null)
						queue.add(temp.left);
					if (temp.right != null)
						queue.add(temp.right);
					nodeCount--;
				}
			}
		}
		return height;
	}

	/* Maximum Depth of Binary Tree:
	 * Depth of the Tree/Max Depth: Path/no of nodes from root to longest leaf Depth
	 * of the tree == Height of the tree == No of levels in the tree
	 */
	public int depthOfTree(TreeNode root) {
		if (root == null)
			return 0; // 0 means count the nodes, -1 means count the edges

		return 1 + Integer.max(depthOfTree(root.left), depthOfTree(root.right));
	}

	public int depthOfNode(TreeNode root, int data) {
		return depthOfNode(root, data, 1);
	}

	public int depthOfNode(TreeNode root, int data, int level) {
		if (root == null)
			return -1;

		if (root.data == data)
			return level;

		int depth = depthOfNode(root.left, data, level + 1);

		if (depth == -1)
			depth = depthOfNode(root.right, data, level + 1);

		return depth;
	}

	/*
	 * Overview of level, path, ancestor: - It follows same recursive approach to
	 * find the level, path or ancestor - Level: Pass level & element in recursive
	 * method - Path to leaf or any node: Pass list & element in recursive method -
	 * Ancestors for any node: Pass list & element in recursive method
	 */
	// Level of the tree
	public int levelOfTree(TreeNode root) {
		if (root == null)
			return 0; // 0 means count the nodes, -1 means count the edges

		return 1 + Integer.max(depthOfTree(root.left), depthOfTree(root.right));
	}

	// Level of the given Node - Recursive approach
	public int levelOfNode1(TreeNode root, int data) {
		return levelOfNode1(root, data, 1);
	}

	private int levelOfNode1(TreeNode root, int data, int level) {
		if (root == null)
			return 0;

		if (root.data == data)
			return level;

		int currentLevel = levelOfNode1(root.left, data, level + 1);
		if (currentLevel == 0)
			currentLevel = levelOfNode1(root.right, data, level + 1);

		return currentLevel;
	}

	// Level of the given Node - Iterative approach
	public int levelOfNode2(TreeNode root, int element) {
		int level = 0, count = 0;
		TreeNode curr = null;
		if (root != null) {
			Queue<TreeNode> queue = new LinkedList<>();
			queue.add(root);
			level++;
			if (root.data == element)
				return level;
			while (!queue.isEmpty()) {
				count = queue.size();
				level++;
				while (count-- > 0) {
					curr = queue.remove();
					if (curr.left != null) {
						if (curr.left.data == element)
							return level;
						queue.add(curr.left);
					}
					if (curr.right != null) {
						if (curr.right.data == element)
							return level;
						queue.add(curr.right);
					}
				}
			}
		}
		return level;
	}

	// Minimum Depth of a Binary Tree - Minimum Depth of Binary Tree is equal to the nearest leaf from root.
	public int minDepth1(TreeNode root) {
		if (root == null)
			return 0;
		int left = minDepth(root.left);
		int right = minDepth(root.right);
		return (left == 0 || right == 0) ? left + right + 1 : Math.min(left, right) + 1;
	}

	// Using BFS version 1
	public int minDepth2(TreeNode root) {
		if (root == null)
			return 0;
		Queue<QueuePack1> queue = new LinkedList<>();
		queue.add(new QueuePack1(root, 1));
		while (!queue.isEmpty()) {
			QueuePack1 queuePack = queue.poll();
			TreeNode curr = queuePack.node;
			if (curr.left == null && curr.right == null)
				return queuePack.depth;
			if (curr.left != null)
				queue.add(new QueuePack1(curr.left, queuePack.depth + 1));
			if (curr.right != null)
				queue.add(new QueuePack1(curr.right, queuePack.depth + 1));
		}
		return 0;
	}

	// Using BFS version 2
	public int minDepth(TreeNode root) {
		if (root == null)
			return 0;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		int level = 1;
		while (!queue.isEmpty()) {
			int len = queue.size();
			while (len-- > 0) {
				TreeNode node = queue.poll();
				if (node.left == null && node.right == null)
					return level;
				if (node.left != null)
					queue.add(node.left);
				if (node.right != null)
					queue.add(node.right);
			}
			level++;
		}
		return 0;
	}

	//	Number of leaf nodes
	public int countLeafNodes1(TreeNode root) {
		if (root == null)
			return 0;
		else if (root.left == null && root.right == null)
			return 1;
		return countLeafNodes1(root.left) + countLeafNodes1(root.right);
	}

	public int countLeafNodes2(TreeNode root) {
		if (root == null)
			return 0;

		int count = 0;
		TreeNode curr;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			curr = queue.poll();
			if (curr.left == null && curr.right == null)
				count++;
			if (curr.left != null)
				queue.add(curr.left);
			if (curr.right != null)
				queue.add(curr.right);
		}
		return count;
	}

	public void printLeafNodes(TreeNode root) {
		if (root == null)
			return;

		if (root.left == null && root.right == null) {
			System.out.print(root.data + " ");
			return;
		}

		printLeafNodes(root.left);
		printLeafNodes(root.right);
	}

	// TODO: Sum of Left Leaves

	/* Count Complete Tree Nodes/Check Completeness of a BT
	 * Count Complete Tree Nodes: Given a complete binary tree, count the number of
	 * nodes. Definition of a complete binary tree: In a complete binary tree every
	 * level, except possibly the last, is completely filled, and all nodes in the
	 * last level are as far left as possible. It can have between 1 and 2h nodes
	 * inclusive at the last level h.
	 */
	/*
	 * Approach1: Time Complexity-O(log(n)^2) - A fully completed tree has node
	 * number: count = 2 ^ h - 1 - Compare left height and right height, if equal,
	 * use the formular, otherwise recurvisely search left and right at next level -
	 * The search pattern is very similar to binary search, the difference of
	 * heights ethier exsits in left side, or right side - Due to the reason stated
	 * in point 3, the time complexity is h ^ 2, there is h times for each level,
	 * and h times for calculating height at each level
	 */
	public int countNodes1(TreeNode root) {
		int leftHeight = findLeft(root);
		int rightHeight = findRight(root);

		if (leftHeight == rightHeight)
			return (1 << leftHeight) - 1; // or return (int)Math.pow(2, leftHeight) -1;

		return 1 + countNodes1(root.left) + countNodes1(root.right);
	}

	/*
	 * Approach2: Time Complexity-O(log(n)^2) The height of a tree can be found by
	 * just going left. Let a single node tree have height 0. Find the height h of
	 * the whole tree. If the whole tree is empty, i.e., has height -1, there are 0
	 * nodes.
	 * 
	 * Otherwise check whether the height of the right subtree is just one less than
	 * that of the whole tree, meaning left and right subtree have the same height.
	 * - If yes, then the last node on the last tree row is in the right subtree and
	 * the left subtree is a full tree of height h-1. So we take the 2^h-1 nodes of
	 * the left subtree plus the 1 root node plus recursively the number of nodes in
	 * the right subtree. - If no, then the last node on the last tree row is in the
	 * left subtree and the right subtree is a full tree of height h-2. So we take
	 * the 2^(h-1)-1 nodes of the right subtree plus the 1 root node plus
	 * recursively the number of nodes in the left subtree. Since I halve the tree
	 * in every recursive step, I have O(log(n)) steps. Finding a height costs
	 * O(log(n)). So overall O(log(n)^2).
	 */
	public int countNodes2(TreeNode root) {
		int count = 0, h;
		h = height(root); // Overall Height

		while (root != null) {
			int rh = height(root.right); // Right Subtree height
			if (rh == h - 1) {
				count += 1 << h;
				root = root.right;
			} else {
				count += 1 << (h - 1);
				root = root.left;
			}
			h--;
		}

		return count;
	}

	// Required Methods for countNodes - start
	public int findLeft(TreeNode root) {
		if (root == null)
			return 0;
		return findLeft(root.left) + 1;
	}

	public int findRight(TreeNode root) {
		if (root == null)
			return 0;
		return findRight(root.right) + 1;
	}

	public int height(TreeNode root) {
		if (root == null)
			return -1;
		return height(root.left) + 1;
	}

	//	TODO: Swap Nodes 

	/*
	 * Find Leaves of Binary Tree: The key to solve this problem is converting the
	 * problem to be finding the index of the element in the result list. Then this
	 * is a typical DFS problem on trees.
	 */
	public List<List<Integer>> findLeaves(TreeNode root) {
		List<List<Integer>> result = new ArrayList<>();
		findLeaves(root, result);

		printList(result);
		return result;
	}

	public int findLeaves(TreeNode root, List<List<Integer>> result) {
		if (root == null)
			return -1;

		int left = findLeaves(root.left, result);
		int right = findLeaves(root.right, result);

		int currLevel = Math.max(left, right) + 1;

		if (result.size() <= currLevel)
			result.add(new ArrayList<>());
		result.get(currLevel).add(root.data);

		return currLevel;
	}

	private void printList(List<List<Integer>> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.print(i + ": ");
			for (Integer data : list.get(i)) {
				System.out.print(data + " ");
			}
			System.out.println();
		}
	}

	// Level Averages in a Binary Tree - Average of Levels in Binary Tree - LeetCode -???
	// Breadth First Search
	public List<Double> averageOfLevels1(TreeNode root) {
		List<Double> result = new ArrayList<>();
		if (root == null)
			return result;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			int n = queue.size();
			double sum = 0;
			for (int i = 0; i < n; i++) {
				TreeNode curr = queue.poll();
				sum += curr.data;
				if (curr.left != null)
					queue.add(curr.left);
				if (curr.right != null)
					queue.add(curr.right);
			}
			result.add(sum / n);
		}
		return result;
	}

	// Depth First Search
	public List<Double> averageOfLevels2(TreeNode root) {
		List<Double> sum = new ArrayList<>();
		List<Integer> count = new ArrayList<>();
		// Calculate the sum in each level
		average(root, 0, sum, count);
		// Calculate the average
		for (int i = 0; i < sum.size(); i++) {
			sum.set(i, sum.get(i) / count.get(i));
		}
		return sum;
	}

	public void average(TreeNode curr, int level, List<Double> sum, List<Integer> count) {
		if (curr == null)
			return;
		if (level < sum.size()) {
			sum.set(level, sum.get(level) + curr.data);
			count.set(level, count.get(level) + 1);
		} else {
			sum.add(curr.data * 1.0);
			count.add(1);
		}
		average(curr.left, level + 1, sum, count);
		average(curr.right, level + 1, sum, count);
	}

	// Level Order Successor of a node in Binary Tree
	public TreeNode levelOrderSuccessor(TreeNode root, TreeNode key) {
		if (root == null)
			return root;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			TreeNode curr = queue.poll();
			if (curr.left != null)
				queue.add(curr.left);
			if (curr.right != null)
				queue.add(curr.right);
			if (curr.data == key.data)
				break;
		}
		return queue.peek();
	}

	// Level Order Predecessor of a node in Binary Tree
	public TreeNode levelOrderPredecessor(TreeNode root, TreeNode key) {
		if (root == null)
			return root;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		TreeNode prev = null;
		while (!queue.isEmpty()) {
			TreeNode curr = queue.poll();
			if (curr.data == key.data)
				break;
			else
				prev = curr;
			if (curr.left != null)
				queue.add(curr.left);
			if (curr.right != null)
				queue.add(curr.right);
		}
		return prev;
	}

	//	Binary Tree Level Order Traversal I, II
	/*
	 * BFS/Level Order Traversal: 
	 * 1.Recursive Approach - Refer Basic Module
	 * 2.Iterative Approach using Queue - Refer Basic Module
	 * 3.Level by Level1: using single queue & level size iteration 
	 * 4.Level by Level2 - using 2 queues 
	 * 5.Bottom-up/Reverse level order traversal: Traverse level by level using queue and store the result in Stack
	 * 6.Bottom-up LevelByLevel Traversal: Iterative Approach; Single Iteration & levelSize iteration 
	 * 7.Bottom-up LevelByLevel Traversal: Recursive Approach 
	 * 8.Spiral Order Traversal:
	 */

	// 3. Level by Level1: using single queue & levelsize iteration
	public List<List<Integer>> levelByLevelOrder1(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<>();
		List<List<Integer>> result = new LinkedList<>();

		if (root == null)
			return result;

		queue.offer(root);
		while (!queue.isEmpty()) {
			int levelSize = queue.size();
			List<Integer> subList = new LinkedList<Integer>();
			while (levelSize-- > 0) {
				if (queue.peek().left != null)
					queue.offer(queue.peek().left);
				if (queue.peek().right != null)
					queue.offer(queue.peek().right);
				subList.add(queue.poll().data);
			}
			result.add(subList);
		}
		return result;
	}

	// Level by Level2 - using 2 queues
	public void levelByLevelOrder2(TreeNode root) {
		if (root != null) {
			Queue<TreeNode> q1 = new LinkedList<>();
			Queue<TreeNode> q2 = new LinkedList<>();
			q1.add(root);
			while (!q1.isEmpty() || !q2.isEmpty()) {
				while (!q1.isEmpty()) {
					root = q1.remove();
					System.out.print(root.data + " ");
					if (root.left != null)
						q2.add(root.left);
					if (root.right != null)
						q2.add(root.right);
				}
				System.out.println();
				while (!q2.isEmpty()) {
					root = q2.poll();
					System.out.print(root.data + " ");
					if (root.left != null)
						q1.add(root.left);
					if (root.right != null)
						q1.add(root.right);
				}
				System.out.println();
			}
		}
	}

	// Bottom-up/Reverse level order traversal: Traverse level by level using queue
	// and store the result in Stack
	public void reverseLevelOrder(TreeNode root) {
		if (root != null) {
			Stack<TreeNode> stack = new Stack<>();
			Queue<TreeNode> queue = new LinkedList<>();
			queue.add(root);
			while (!queue.isEmpty()) {
				root = queue.poll();
				stack.push(root);
				if (root.right != null)
					queue.add(root.right);
				if (root.left != null)
					queue.add(root.left);
			}

			while (!stack.isEmpty())
				System.out.print(stack.pop().data + " ");

		}
	}

	// Bottom-up LevelByLevel Traversal: Iterative Approach; Single Iteration &
	// levelSize iteration
	public List<List<Integer>> levelOrderBottom1(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		List<List<Integer>> result = new LinkedList<List<Integer>>();

		if (root == null)
			return result;

		queue.offer(root);
		while (!queue.isEmpty()) {
			int levelSize = queue.size();
			List<Integer> subList = new LinkedList<Integer>();
			while (levelSize-- > 0) {
				if (queue.peek().left != null)
					queue.offer(queue.peek().left);
				if (queue.peek().right != null)
					queue.offer(queue.peek().right);
				subList.add(queue.poll().data);
			}
			result.add(0, subList);// Add the list in the Zeroth Position
		}
		return result;
	}

	// Bottom-up LevelByLevel Traversal: Recursive Approach(LevelOrder - DFS
	// Traversals)
	public List<List<Integer>> levelOrderBottom2(TreeNode root) {
		List<List<Integer>> result = new LinkedList<List<Integer>>();
		levelOrderBottom(root, result, 0);
		return result;
	}

	public void levelOrderBottom(TreeNode root, List<List<Integer>> result, int level) {
		if (root == null)
			return;
		if (result.size() <= level)
			result.add(0, new ArrayList<>()); // Create new List in the zeroth position

		levelOrderBottom(root.left, result, level + 1);
		levelOrderBottom(root.right, result, level + 1);

		result.get(result.size() - level - 1).add(root.data); // Add the list into resultSize-level-1
	}

	/* Binary Tree Zigzag /Spiral Level Order Traversal
	 * Spiral Order Traversal: 
	 * 	1.Using 2 stack 
	 * 	2.Using Deque
	 */
	// 1.Using 2 stack
	public void levelOrderSpiralForm(TreeNode root) {
		if (root != null) {
			Stack<TreeNode> s1 = new Stack<>();
			Stack<TreeNode> s2 = new Stack<>();
			s1.push(root);
			while (!s1.isEmpty() || !s2.isEmpty()) {
				while (!s1.isEmpty()) {
					root = s1.pop();
					System.out.print(root.data + " ");
					if (root.left != null)
						s2.push(root.left);
					if (root.right != null)
						s2.push(root.right);
				}
				while (!s2.isEmpty()) {
					root = s2.pop();
					System.out.print(root.data + " ");
					if (root.right != null)
						s1.push(root.right);
					if (root.left != null)
						s1.push(root.left);
				}
			}
		}
	}

	//	TODO: List of Depths / Level by Level Traversal

	//	TODO: Find Largest Value in Each Tree Row - Level Order

	/************************ Type3: BT Views ************************/
	//	Print a Binary Tree in Vertical Order
	/*
	 * 2.Tree View Problems: 
	 * 	- Vertical View - DFS/BFS 
	 * 	- Top View - DFS/BFS 
	 *  - Bottom View - DFS 
	 *  - Left View - DFS 
	 *  - Right View - DFS 
	 *  - Diagonal View - DFS
	 */
	// Vertical View1: DFS Approach
	public void verticalViewTraversal1(TreeNode root) {
		if (root != null) {
			Map<Integer, ArrayList<Integer>> map = new TreeMap<>();
			verticalOrder(root, map, 0);

			if (map != null) {
				for (Entry<Integer, ArrayList<Integer>> entry : map.entrySet())
					System.out.println(entry.getKey() + "-" + entry.getValue());
			}
		}
	}

	private void verticalOrder(TreeNode root, Map<Integer, ArrayList<Integer>> map, int hd) {
		if (root == null)
			return;
		// hd - horizontal distance
		ArrayList<Integer> list = map.get(hd);
		if (list == null)
			list = new ArrayList<>();

		list.add(root.data);
		map.put(hd, list);

		verticalOrder(root.left, map, hd - 1);
		verticalOrder(root.right, map, hd + 1);
	}

	// Vertical View1: BFS Approach
	public void verticalViewTraversal2(TreeNode root) {
		TreeMap<Integer, ArrayList<Integer>> map = new TreeMap<>();
		Queue<QueuePack> queue = new LinkedList<>();
		QueuePack queuePack;
		ArrayList<Integer> list = new ArrayList<>();
		TreeNode curr;
		int hd;
		queue.add(new QueuePack(0, root));
		while (!queue.isEmpty()) {
			queuePack = queue.poll();
			hd = queuePack.hd;
			curr = queuePack.node;

			list = map.get(hd);
			if (list == null)
				list = new ArrayList<>();

			list.add(curr.data);
			map.put(hd, list);

			if (curr.left != null)
				queue.add(new QueuePack(hd - 1, curr.left));

			if (curr.right != null)
				queue.add(new QueuePack(hd + 1, curr.right));
		}

		for (Entry<Integer, ArrayList<Integer>> entry : map.entrySet())
			System.out.println(entry.getKey() + "-" + entry.getValue());
	}

	// Top View - BFS Approach
	public void topViewTraversal(TreeNode root) {
		TreeMap<Integer, Integer> map = new TreeMap<>(); // Using Map Api
		// Set<Integer> set = new HashSet<>(); // Using Set Api
		Queue<QueuePack> queue = new LinkedList<>();
		QueuePack queuePack;
		TreeNode curr;
		int hd;
		queue.add(new QueuePack(0, root));
		while (!queue.isEmpty()) {
			queuePack = queue.poll();
			hd = queuePack.hd;
			curr = queuePack.node;

			/*
			 * if (!set.contains(hd)) { set.add(hd); System.out.print(curr.data + " "); }
			 */
			if (!map.containsKey(hd))
				map.put(hd, curr.data);

			if (curr.left != null)
				queue.add(new QueuePack(hd - 1, curr.left));

			if (curr.right != null)
				queue.add(new QueuePack(hd + 1, curr.right));
		}

		for (Integer key : map.keySet())
			System.out.print(map.get(key) + "-");
	}

	//	Print Bottom View of Binary Tree
	// Bottom View - BFS Approach
	public void bottomViewTraversal(TreeNode root) {
		TreeMap<Integer, Integer> map = new TreeMap<>();
		Queue<QueuePack> queue = new LinkedList<>();
		QueuePack queuePack;
		TreeNode curr;
		int hd;
		queue.add(new QueuePack(0, root));
		while (!queue.isEmpty()) {
			queuePack = queue.poll();
			hd = queuePack.hd;
			curr = queuePack.node;

			map.put(hd, curr.data);

			if (curr.left != null)
				queue.add(new QueuePack(hd - 1, curr.left));

			if (curr.right != null)
				queue.add(new QueuePack(hd + 1, curr.right));
		}

		for (Integer key : map.keySet())
			System.out.print(map.get(key) + "-");

	}

	// Diagonal View - DFS Approach
	public void diagonalTraversal(TreeNode root) {
		Map<Integer, ArrayList<Integer>> map = new HashMap<>();

		diagonalTraversal(root, 0, map);

		for (Entry<Integer, ArrayList<Integer>> entry : map.entrySet())
			System.out.println(entry.getKey() + " - " + entry.getValue());

	}

	public void diagonalTraversal(TreeNode root, int dist, Map<Integer, ArrayList<Integer>> map) {
		if (root == null)
			return;

		ArrayList<Integer> list = map.get(dist);
		if (list == null)
			list = new ArrayList<>();
		list.add(root.data);
		map.put(dist, list);

		diagonalTraversal(root.left, dist + 1, map);
		diagonalTraversal(root.right, dist, map);
	}

	/*
	 * Left View of Tree - BFS Approach: The left view contains all nodes that are
	 * first nodes in their levels. A simple solution is to do level order traversal
	 * and print the first node in every level.
	 */
	public void leftViewOfTree1(TreeNode root) {

	}

	//	Binary Tree Left/Right Side View
	// Left View - DFS Approach
	static int maxLeftLevel = 0;

	public void leftViewOfTree2(TreeNode root) {
		leftViewOfTree2(root, 1);
	}

	public void leftViewOfTree2(TreeNode root, int level) {
		if (root == null)
			return;

		if (maxLeftLevel < level) {
			System.out.print(root.data + " ");
			maxLeftLevel = level;
		}

		leftViewOfTree2(root.left, level + 1);
		leftViewOfTree2(root.right, level + 1);
	}

	/*
	 * Right View of Tree - BFS Approach: The Right view contains all nodes that are
	 * last nodes in their levels. A simple solution is to do level order traversal
	 * and print the last node in every level.
	 */
	public void rightViewOfTree1(TreeNode root) {

	}

	// Right View - DFS Approach
	static int maxRightLevel = 0;

	public void rightViewOfTree2(TreeNode root) {
		rightViewOfTree2(root, 1);
	}

	public void rightViewOfTree2(TreeNode root, int level) {
		if (root == null)
			return;
		if (maxRightLevel < level) {
			System.out.print(root.data + " ");
			maxRightLevel = level;
		}

		rightViewOfTree2(root.right, level + 1);
		rightViewOfTree2(root.left, level + 1);
	}

	/*
	 * Right Side View - DFS Approach: The core idea of this algorithm: 1.Each
	 * depth/level of the tree only select one node. 2. View depth/level is current
	 * size of result list.
	 */
	public List<Integer> rightSideView(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		rightSideView(root, result, 0);
		return result;
	}

	public void rightSideView(TreeNode root, List<Integer> result, int level) {
		if (root == null)
			return;
		if (level == result.size()) // Add one element per level
			result.add(root.data);

		rightSideView(root.right, result, level + 1);
		rightSideView(root.left, result, level + 1);
	}

	//	TODO: Print Left View of Binary Tree

	//	TODO: How to print all diagonal's sums for a given binary tree

	/************************ Type4: BT Checking ************************/
	//	Write Code to Determine if Two Trees are Identical/Same Tree
	public boolean isSameTree(TreeNode p, TreeNode q) {
		if (p == null && q == null)
			return true;
		if (p == null || q == null)
			return false;
		return (p.data == q.data && isSameTree(p.left, q.left) && isSameTree(p.right, q.right));
	}

	//	Check whether it is a mirror of itself/Symmetric Tree 
	// Recursive Approach
	public boolean isSymmetric(TreeNode root) {
		if (root == null)
			return true;
		return isMirror(root, root);
	}

	public boolean isMirror(TreeNode node1, TreeNode node2) {
		if (node1 == null && node2 == null)
			return true;
		if (node1 == null || node2 == null)
			return false;
		return node1.data == node2.data && isMirror(node1.left, node2.right) && isMirror(node1.right, node2.left);
	}

	// Iterative Approach
	public boolean isMirror2(TreeNode node1, TreeNode node2) {
		return false;
	}

	// Isomorphism: Combination of same tree & mirror/symmetric tree logic
	public boolean isomorphism(TreeNode node1, TreeNode node2) {
		if (node1 == null && node2 == null)
			return true;
		if (node1 == null || node2 == null)
			return false;
		// There are two possible cases for node1 and node2 to be isomorphic
		// Case 1: The subtrees rooted at these nodes have NOT been "Flipped". (Similar
		// to Normal Tree comparison)
		// Case 2: The subtrees rooted at these nodes have been "Flipped" (Similar to
		// Mirror Tree comparison)
		// Both of these subtrees have to be isomorphic, hence the &&
		return node1.data == node2.data
				&& ((isomorphism(node1.left, node2.left) && isomorphism(node1.right, node2.right))
						|| (isomorphism(node1.left, node2.right) && isomorphism(node1.right, node2.left)));
	}

	//	Invert or Flip Binary Tree
	// Approach1:
	public TreeNode invertTree(TreeNode root) {
		if (root == null || (root.left == null && root.right == null))
			return root;
		invertTree(root.left);
		invertTree(root.right);
		TreeNode temp = root.right;
		root.right = root.left;
		root.left = temp;
		return root;
	}

	// Approach2:
	public TreeNode invertTree2(TreeNode root) {
		if (root == null)
			return null;
		TreeNode left = root.left;
		root.left = invertTree(root.right);
		root.right = invertTree(left);
		return root;
	}

	//	Subtree of Another Tree/Check Subtree
	// Approach1: Time Complexity: O(n^2)
	public boolean isSubtree1(TreeNode s, TreeNode t) {
		if (t == null)
			return true;
		if (s == null)
			return false;

		if (isSameTree(s, t))
			return true;

		return isSubtree1(s.left, t) || isSubtree1(s.right, t);
	}

	// Approach2: Time Complexity: O(n) -> Using inorder & preorder of both tree
	public boolean isSubtree2(TreeNode s, TreeNode t) {
		return false;
	}

	// Approach3: Time Complexity: O(m+n): Use Preorder traversal and build the
	// string for the both, finally check the
	// substring
	public boolean isSubtree3(TreeNode T1, TreeNode T2) {
		if (T1 == null)
			return true;
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		buildTree(T1, sb1);
		buildTree(T2, sb2);
		System.out.println("S1: " + sb1.toString());
		System.out.println("S2: " + sb2.toString());
		return (sb1.indexOf(sb2.toString()) != -1);
	}

	public void buildTree(TreeNode root, StringBuilder sb) {
		if (root == null) {
			sb.append("X");
			return;
		}

		sb.append(root.data);
		buildTree(root.left, sb);
		buildTree(root.right, sb);
	}

	/************************ Type5: BT Print, Path, Sum & LCA Problems ************************/
	//	Binary Tree Paths
	public void printPathFromRootToLeaf1(TreeNode root) {
		ArrayList<Integer> list = new ArrayList<>();
		printPathFromRootToLeaf1(root, list);
	}

	private void printPathFromRootToLeaf1(TreeNode root, ArrayList<Integer> path) {
		if (root == null)
			return;

		path.add(root.data);
		if (root.left == null && root.right == null) {
			path.stream().forEach(k -> System.out.print(k + "-"));
			System.out.println();
		}

		printPathFromRootToLeaf1(root.left, path);
		printPathFromRootToLeaf1(root.right, path);

		// Remove the visited path from list, if node is not present in the path
		if (!path.isEmpty())
			path.remove(path.size() - 1);
	}

	// Binary Tree Paths - String
	public List<String> printPathFromRootToLeaf2(TreeNode root) {
		List<String> result = new ArrayList<>();
		if (root == null)
			return result;
		printPathFromRootToLeaf2(root, result, "");
		result.stream().forEach(k -> System.out.print(k + ", "));
		return result;
	}

	public void printPathFromRootToLeaf2(TreeNode root, List<String> result, String path) {
		if (root == null)
			return;
		path += root.data;
		if (root.left == null && root.right == null)
			result.add(path);
		printPathFromRootToLeaf2(root.left, result, path + "->");
		printPathFromRootToLeaf2(root.right, result, path + "->");
	}

	// Find the path from root to any node
	public void findPathFromRootToAnyNode1(TreeNode root, int n) {
		ArrayList<Integer> path = new ArrayList<>();

		if (findPathFromRootToAnyNode1(root, n, path))
			path.stream().forEach(k -> System.out.print(k + "-"));
		else
			System.out.println("Element is not present in the tree");
	}

	// Approach1:
	private boolean findPathFromRootToAnyNode1(TreeNode root, int n, ArrayList<Integer> path) {
		if (root == null)
			return false;

		// To find the path, first add the element in the list and check the data
		path.add(root.data);
		if (root.data == n)
			return true;

		boolean flag = findPathFromRootToAnyNode1(root.left, n, path);
		// This flag should check separately, otherwise when root.right returns,
		// required element will be removed
		if (!flag)
			flag = findPathFromRootToAnyNode1(root.right, n, path);

		// Remove the visited path from list, if node is not present in the path;
		if (!flag)
			path.remove(path.size() - 1);
		return false;
	}

	// Approach-2:
	public boolean findPathFromRootToAnyNode2(TreeNode root, int element) {
		if (root == null)
			return false;

		if (root.data == element)
			return true;

		if (findPathFromRootToAnyNode2(root.left, element) || findPathFromRootToAnyNode2(root.right, element)) {
			System.out.print(root.data + " ");
			return true;
		}
		return false;
	}

	//	TODO: Path Sum I, II, III, IV -> Need to clean up
	// Root to leaf path sum:
	// Binary Tree Path Sum
	/* Path Sum I:
	 *  Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values
	 *  along the path equals the given sum.
	 *  Note: A leaf is a node with no children.
	 *  Example: Given the below binary tree and sum = 22,
	 */
	public boolean hasPathSum(TreeNode root, int sum) {
		if (root == null)
			return false;

		if (sum == root.data && root.left == null && root.right == null)
			return true;

		return hasPathSum(root.left, sum - root.data) || hasPathSum(root.right, sum - root.data);
	}

	/*
	 * Path Sum II: Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
	 * Note: A leaf is a node with no children.
	 * Example: Given the below binary tree and sum = 22,
	 */
	public List<List<Integer>> pathSumII(TreeNode root, int sum) {
		List<List<Integer>> result = new ArrayList<>();
		List<Integer> eachList = new ArrayList<>();
		pathSum(root, result, eachList, sum);
		return result;
	}

	public void pathSum(TreeNode root, List<List<Integer>> result, List<Integer> eachList, int sum) {
		if (root == null)
			return;
		eachList.add(root.data);
		if (root.data == sum && root.left == null && root.right == null)
			result.add(new ArrayList<>(eachList));
		pathSum(root.left, result, eachList, sum - root.data);
		pathSum(root.right, result, eachList, sum - root.data);
		eachList.remove(eachList.size() - 1);
	}

	/*
	 * Path Sum III: You are given a binary tree in which each node contains an integer value. Find the number of paths
	 * that sum to a given value. The path does not need to start or end at the root or a leaf, but it must go downwards
	 * (traveling only from parent nodes to child nodes). The tree has no more than 1,000 nodes and the values are in
	 * the range -1,000,000 to 1,000,000. 
	 * Example: root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
	 */
	// Brute Force DFS Approach: Time - O(n^2)
	public int pathSumIII(TreeNode root, int sum) {
		if (root == null)
			return 0;

		return pathSumFrom(root, sum) + pathSumIII(root.left, sum) + pathSumIII(root.right, sum);
	}

	public int pathSumFrom(TreeNode root, int sum) {
		if (root == null)
			return 0;

		return (sum == root.data ? 1 : 0) + pathSumFrom(root.left, sum - root.data)
				+ pathSumFrom(root.right, sum - root.data);
	}

	// Prefix Sum Method: Time: O(n), Space:O(n)
	public int pathSumIII2(TreeNode root, int sum) {
		HashMap<Integer, Integer> preSum = new HashMap<>();
		preSum.put(0, 1);
		return helper(root, 0, sum, preSum);
	}

	public int helper(TreeNode root, int currSum, int target, HashMap<Integer, Integer> preSum) {
		if (root == null) {
			return 0;
		}

		currSum += root.data;
		int res = preSum.getOrDefault(currSum - target, 0);
		preSum.put(currSum, preSum.getOrDefault(currSum, 0) + 1);

		res += helper(root.left, currSum, target, preSum) + helper(root.right, currSum, target, preSum);
		preSum.put(currSum, preSum.get(currSum) - 1);
		return res;
	}

	// All Paths for a Sum - Check all the root to leaf path for the given Sum
	public void sumOfNodesFromRootToLeaf(TreeNode root, int sum) {
		ArrayList<Integer> path = new ArrayList<>();
		if (rootToLeafUtil1(root, sum, path)) {
			System.out.print("Sum Path for " + sum + " is:");
			path.stream().forEach(k -> System.out.print(k + " "));
		} else {
			System.out.println("Sum is not present from root to leaf!");
		}
	}

	private boolean rootToLeafUtil1(TreeNode root, int sum, ArrayList<Integer> path) {
		if (root == null)
			return false;

		if (root.left == null && root.right == null) {
			if (sum == root.data) {
				path.add(root.data);
				return true;
			} else {
				return false;
			}
		}

		if (rootToLeafUtil1(root.left, sum - root.data, path)) {
			path.add(root.data);
			return true;
		}

		if (rootToLeafUtil1(root.right, sum - root.data, path)) {
			path.add(root.data);
			return true;
		}
		return false;
	}

	private boolean rootToLeafUtil2(TreeNode root, int currSum, int sum, ArrayList<Integer> path) {
		if (root == null)
			return false;

		currSum += root.data;
		if (root.left == null && root.right == null) {
			if (currSum == sum)
				return true;
			else
				return false;
		}

		boolean flag = rootToLeafUtil2(root.left, currSum, sum, path);

		if (!flag)
			flag = rootToLeafUtil2(root.right, currSum, sum, path);

		if (!flag)
			path.remove(path.size() - 1);

		return false;
	}

	// Path Sum1: Check the sum presents in the path:
	public boolean hasPathSumFromRootToLeaf(TreeNode root, int sum) {
		if (root == null)
			return false;
		if (sum == root.data && root.left == null && root.right == null)
			return true;
		return hasPathSumFromRootToLeaf(root.left, sum - root.data)
				|| hasPathSumFromRootToLeaf(root.right, sum - root.data);
	}

	// Path Sum II: find all root-to-leaf paths where each path's sum equals the given sum
	public List<List<Integer>> sumPathFromRootToLeafAll(TreeNode root, int sum) {
		List<List<Integer>> result = new ArrayList<>();
		List<Integer> eachList = new ArrayList<>();
		sumPathFromRootToLeafAll(root, result, eachList, sum);
		return result;
	}

	public void sumPathFromRootToLeafAll(TreeNode root, List<List<Integer>> result, List<Integer> eachList, int sum) {
		if (root == null)
			return;

		eachList.add(root.data);

		if (root.data == sum && root.left == null && root.right == null)
			result.add(new ArrayList<>(eachList));

		sumPathFromRootToLeafAll(root.left, result, eachList, sum - root.data);
		sumPathFromRootToLeafAll(root.right, result, eachList, sum - root.data);
		eachList.remove(eachList.size() - 1);
	}

	//	TODO: Sum Root to Leaf Numbers
	// Check the root to leaf path for the given Sum and print the result:
	public void sumPathFromRootToLeaf(TreeNode root, int sum) {
		ArrayList<Integer> path = new ArrayList<>();
		if (sumPathFromRootToLeaf1(root, sum, path)) {
			// if (sumPathFromRootToLeaf2(root, sum, path)) {
			System.out.print("Sum Path for " + sum + " is:");
			path.stream().forEach(k -> System.out.print(k + " "));
		} else {
			System.out.println("Sum is not present from root to leaf!");
		}
	}

	private boolean sumPathFromRootToLeaf1(TreeNode root, int sum, ArrayList<Integer> path) {
		if (root == null)
			return false;

		if (sum == root.data && root.left == null && root.right == null) {
			path.add(root.data);
			return true;
		}
		if (sumPathFromRootToLeaf1(root.left, sum - root.data, path)) {
			path.add(root.data);
			return true;
		}
		if (sumPathFromRootToLeaf1(root.right, sum - root.data, path)) {
			path.add(root.data);
			return true;
		}
		return false;
	}

	public boolean sumPathFromRootToLeaf2(TreeNode root, int currSum, int sum, ArrayList<Integer> path) {
		if (root == null)
			return false;

		currSum += root.data;
		path.add(root.data);
		if (root.left == null && root.right == null) {
			if (currSum == sum)
				return true;
			else
				return false;
		}
		boolean flag = sumPathFromRootToLeaf2(root.left, currSum, sum, path);
		if (!flag)
			flag = sumPathFromRootToLeaf2(root.right, currSum, sum, path);
		if (!flag)
			path.remove(path.size() - 1);
		return false;
	}

	// Most Frequent Subtree Sum:
	public int[] findFrequentTreeSum(TreeNode root) {
		Map<Integer, Integer> map = new HashMap<>();
		List<Integer> list = new ArrayList<>();
		int[] max = new int[1];

		subTreeSum(root, map, max);

		for (Integer key : map.keySet())
			if (map.get(key) == max[0])
				list.add(key);

		int[] result = new int[list.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = list.get(i);

		return result;
	}

	public int subTreeSum(TreeNode root, Map<Integer, Integer> map, int[] max) {
		if (root == null)
			return 0;
		int sum = root.data;

		sum += subTreeSum(root.left, map, max);
		sum += subTreeSum(root.right, map, max);

		map.put(sum, map.getOrDefault(sum, 0) + 1);
		max[0] = Math.max(max[0], map.get(sum));
		return sum;
	}

	//	TODO: Sum of Path Numbers

	//	TODO: Path With Given Sequence

	//	TODO: Count Paths for a Sum

	/************************ Type6: BT Construction, Conversion ************************/
	//	Construct Binary Tree from Inorder and Postorder Traversal
	/*
	 * If you are given two traversal sequences, can you construct the binary tree?
	 * It depends on what traversals are given. If one of the traversal methods is
	 * Inorder then the tree can be constructed, otherwise not. Therefore, following
	 * combination can uniquely identify a tree. - Inorder and Preorder - Inorder
	 * and Postorder - Inorder and Level-order.
	 * 
	 * And following do not. - Postorder and Preorder - Preorder and Level-order -
	 * Postorder and Level-order.
	 * 
	 * So, even if three of them (Pre, Post and Level) are given, the tree can not
	 * be constructed.
	 */
	public TreeNode constructTreeFromInAndPreOrder(char[] inOrder, char[] preOrder) {
		OrderIndex preOrderIndex = new OrderIndex();
		preOrderIndex.index = 0;
		return buildTreeFromInAndPreOrder(inOrder, preOrder, 0, inOrder.length - 1, preOrderIndex);
	}

	private TreeNode buildTreeFromInAndPreOrder(char[] inOrder, char[] preOrder, int inOrderStart, int inOrderEnd,
			OrderIndex preOrderIndex) {
		if (inOrderStart > inOrderEnd)
			return null;

		TreeNode node = new TreeNode(preOrder[preOrderIndex.index++]);
		if (inOrderStart == inOrderEnd)
			return node;

		int rootNodeIndex = search(inOrder, inOrderStart, inOrderEnd, node.ch);
		node.left = buildTreeFromInAndPreOrder(inOrder, preOrder, inOrderStart, rootNodeIndex - 1, preOrderIndex);
		node.right = buildTreeFromInAndPreOrder(inOrder, preOrder, rootNodeIndex + 1, inOrderEnd, preOrderIndex);
		return node;
	}

	public TreeNode constructTreeFromInAndPostOrder(char[] inOrder, char[] postOrder) {
		OrderIndex postOrderIndex = new OrderIndex();
		postOrderIndex.index = postOrder.length - 1;
		return buildTreeFromInAndPostOrder(inOrder, postOrder, 0, inOrder.length - 1, postOrderIndex);
	}

	public TreeNode buildTreeFromInAndPostOrder(char[] inOrder, char[] postOrder, int inOrderStart, int inOrderEnd,
			OrderIndex postOrderIndex) {
		if (inOrderStart > inOrderEnd)
			return null;

		TreeNode node = new TreeNode(postOrder[postOrderIndex.index--]);
		if (inOrderStart == inOrderEnd)
			return node;

		int rootNodeIndex = search(inOrder, inOrderStart, inOrderEnd, node.ch);
		node.right = buildTreeFromInAndPostOrder(inOrder, postOrder, rootNodeIndex + 1, inOrderEnd, postOrderIndex);
		node.left = buildTreeFromInAndPostOrder(inOrder, postOrder, inOrderStart, rootNodeIndex - 1, postOrderIndex);
		return node;
	}

	private int search(char[] charArray, int start, int end, char x) {
		for (int i = start; i <= end; i++) {
			if (charArray[i] == x)
				return i;
		}
		return -1;
	}

	//	Verify Preorder Serialization of a Binary Tree    

	//	Flatten Binary Tree to Linked List
	// Approach11: Inorder Traversal Modification
	public void flatten(TreeNode root) {
		if (root == null)
			return;

		flatten(root.left);

		if (root.left != null) {
			TreeNode tempRight = root.right;
			root.right = root.left;
			root.left = null;
			while (root.right != null)
				root = root.right;
			root.right = tempRight;
		}

		flatten(root.right);
	}

	//Solution using Stack
	public void flatten2(TreeNode root) {
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode curr = root;

		while (curr != null || !stack.empty()) {

			if (curr.right != null)
				stack.push(curr.right);

			if (curr.left != null) {
				curr.right = curr.left;
				curr.left = null;
			} else if (!stack.empty()) {
				curr.right = stack.pop();
			}

			curr = curr.right;
		}
	}
	/************************ Type7: BT Traversal Modification Probs, Misc Probs ****************/
	//	Balanced Forest - Revisit this
	//	Connect All Level Order Siblings
	//	Tree Boundary

}

class QueuePack1 {
	public int depth;
	public TreeNode node;

	public QueuePack1(TreeNode node, int depth) {
		this.depth = depth;
		this.node = node;
	}
}
