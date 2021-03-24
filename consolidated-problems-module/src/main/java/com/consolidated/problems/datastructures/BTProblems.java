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
import com.consolidated.problems.operations.TreePaths;
import com.consolidated.problems.operations.TreeProperties;
import com.problems.patterns.ds.BTPatterns;

/*
 * All the tree problems can be solved by 
 * 	1.DFS - Using Recursion or Stack
 *  2.BFS - Using Queue
 */
public class BTProblems implements TreeProperties, TreePaths {

	BTPatterns bTPatterns = new BTPatterns();

	/************************ Type1: Basics & Properties ************************/

	//Time: O(n), Space:(1)
	@Override
	public int sizeOfTree1(TreeNode root) {
		if (root == null) return 0;
		return 1 + sizeOfTree1(root.left) + sizeOfTree1(root.right);
	}

	//Time: O(n), Space:(n)
	@Override
	public int sizeOfTree2(TreeNode root) {
		int count = 0;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			count++;
			TreeNode curr = queue.poll();
			if (curr.left != null) queue.add(curr.left);
			if (curr.right != null) queue.add(curr.right);
		}
		return count;
	}

	/* Count Complete Tree Nodes/Check Completeness of a BT
	 * Count Complete Tree Nodes: Given a complete binary tree, count the number of nodes. Definition of a complete binary tree: 
	 * In a complete binary tree every level, except possibly the last, is completely filled, and all nodes in the last level are 
	 * as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.
	 */
	/*
	 * Approach1: A fully completed tree has nodes atmost 2 ^ h - 1. h is height of the tree or level of the tree 
	 * Time Complexity-O(log(n)^2) or O(h^2); 
	 *  Recursion -> O(h)+O(h-1)+O(h-2)+...O(1) = O(h^2)
	 */
	@Override
	public int countNodes1(TreeNode root) {
		int lh = leftSize(root); //no of nodes in left 
		int rh = rightSize(root); //no of nodes in right

		//if no of nodes are equals in both side, then total nodes in tree = 2^h-1
		if (lh == rh) return (1 << lh) - 1; // or return (int)Math.pow(2, lh) -1;

		return 1 + countNodes1(root.left) + countNodes1(root.right);
	}

	/*
	 * Approach2: Time Complexity-O(log(n)^2) The height of a tree can be found by just going left. Let a single node tree have height 0.
	 * Find the height h of the whole tree. If the whole tree is empty, i.e., has height -1, there are 0 nodes.
	 * Finding a height costs O(log(n)). So overall O(log(n)^2).
	 */
	@Override
	public int countNodes2(TreeNode root) {
		int count = 0, h;
		h = leftSize(root);

		while (root != null) {
			int rh = leftSize(root.right);
			if (rh == h - 1) {
				count += 1 << h - 1; //2^h-1
				root = root.right;
			} else {
				count += 1 << (h - 2);
				root = root.left;
			}
			h--;
		}

		return count;
	}

	private int leftSize(TreeNode root) {
		if (root == null) return 0;
		return leftSize(root.left) + 1;
	}

	private int rightSize(TreeNode root) {
		if (root == null) return 0;
		return rightSize(root.right) + 1;
	}

	/* Height of Binary Tree == Depth of  Binary Tree == Level of Binary Tree
	 * Height of the tree: Height from root to longest leaf
	 * Maximum Depth of Binary Tree or Depth of the Tree: Path/no of nodes from root to longest leaf Depth of the tree
	 */
	@Override
	public int heightOfTree1(TreeNode root) {
		if (root == null) return 0; // 0 means count the nodes, -1 means count the edges

		return 1 + Math.max(heightOfTree1(root.left), heightOfTree1(root.right));
	}

	@Override
	public int heightOfTree2(TreeNode root) {
		int height = 0; // 0 means count the nodes, -1 means count the edges
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			int levelSize = queue.size();
			height++;
			while (levelSize > 0) {
				TreeNode curr = queue.poll();
				if (curr.left != null) queue.add(curr.left);
				if (curr.right != null) queue.add(curr.right);
				levelSize--;
			}
		}
		return height;
	}

	// Minimum Depth of a Binary Tree - Minimum Depth of Binary Tree is equal to the nearest leaf from root.
	/* Solution 1: DFS 
	 * if a node only has one child -> MUST return the depth of the side with child, i.e. MAX(left, right) + 1
	 * if a node has two children on both side -> return min depth of two sides, i.e. MIN(left, right) + 1
	 */
	@Override
	public int minDepthOfTree1(TreeNode root) {
		if (root == null) return 0;

		int left = minDepthOfTree1(root.left);
		int right = minDepthOfTree1(root.right);
		return (left == 0 || right == 0) ? Math.max(left, right) + 1 : Math.min(left, right) + 1;
	}

	// Using BFS version 1
	public int minDepthOfTree2(TreeNode root) {
		if (root == null) return 0;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		int level = 0;
		while (!queue.isEmpty()) {
			int len = queue.size();
			level++;
			while (len-- > 0) {
				TreeNode node = queue.poll();
				if (node.left == null && node.right == null) return level;
				if (node.left != null) queue.add(node.left);
				if (node.right != null) queue.add(node.right);
			}
		}
		return 0;
	}

	// Using BFS version 2
	public int minDepthOfTree22(TreeNode root) {
		if (root == null) return 0;
		Queue<QueuePack1> queue = new LinkedList<>();
		queue.add(new QueuePack1(root, 1));
		while (!queue.isEmpty()) {
			QueuePack1 queuePack = queue.poll();
			TreeNode curr = queuePack.node;
			if (curr.left == null && curr.right == null) return queuePack.depth;
			if (curr.left != null) queue.add(new QueuePack1(curr.left, queuePack.depth + 1));
			if (curr.right != null) queue.add(new QueuePack1(curr.right, queuePack.depth + 1));
		}
		return 0;
	}

	//The height of a node is the number of edges from the node to the deepest leaf.
	@Override
	public int heightOfNode(TreeNode root, int data) {
		return 0;
	}

	//The depth of a node is the number of edges from the root to the node.
	@Override
	public int depthOfNode1(TreeNode root, int data) {
		return depthOfNode1(root, data, 1);
	}

	private int depthOfNode1(TreeNode root, int data, int level) {
		if (root == null) return -1;
		if (root.val == data) return level;
		int depth = depthOfNode1(root.left, data, level + 1);
		if (depth == -1) depth = depthOfNode1(root.right, data, level + 1);
		return depth;
	}

	@Override
	public int depthOfNode2(TreeNode root, int element) {
		if (root == null) return 0;
		int level = 0;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			int size = queue.size();
			level++;
			while (size-- > 0) {
				TreeNode curr = queue.remove();
				if (curr.val == element) return level;

				if (curr.left != null) queue.add(curr.left);
				if (curr.right != null) queue.add(curr.right);
			}
		}
		return level;
	}

	@Override
	public int levelOfNode1(TreeNode root, int data) {
		return depthOfNode1(root, data);
	}

	@Override
	public int levelOfNode2(TreeNode root, int element) {
		return depthOfNode2(root, element);
	}

	@Override
	public int countLeafNodes1(TreeNode root) {
		if (root == null) return 0;
		if (root.left == null && root.right == null) return 1;
		return countLeafNodes1(root.left) + countLeafNodes1(root.right);
	}

	@Override
	public int countLeafNodes2(TreeNode root) {
		if (root == null) return 0;

		int count = 0;
		TreeNode curr;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			curr = queue.poll();
			if (curr.left == null && curr.right == null) count++;
			if (curr.left != null) queue.add(curr.left);
			if (curr.right != null) queue.add(curr.right);
		}
		return count;
	}

	@Override
	public void printLeafNodes(TreeNode root) {
		if (root == null) return;

		if (root.left == null && root.right == null) {
			System.out.print(root.val + " ");
			return;
		}

		printLeafNodes(root.left);
		printLeafNodes(root.right);
	}

	@Override
	public int sumOfLeftLeaf(TreeNode root) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * Given a binary tree, collect a tree's nodes as if you were doing this: Collect and remove all leaves, repeat until the tree is empty.
	 * Example1: 
	 * 	Input: {1,2,3,4,5} 
	 *  Output: [[4, 5, 3], [2], [1]].
	 */
	@Override
	public List<List<Integer>> findLeaves(TreeNode root) {
		List<List<Integer>> result = new ArrayList<>();
		findLeaves(root, result);

		printList(result);
		return result;
	}

	private int findLeaves(TreeNode root, List<List<Integer>> result) {
		if (root == null) return -1;

		int left = findLeaves(root.left, result);
		int right = findLeaves(root.right, result);
		int currLevel = Math.max(left, right) + 1;
		if (result.size() <= currLevel) result.add(new ArrayList<>());
		result.get(currLevel).add(root.val);
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

	@Override
	public List<Double> averageOfLevels1(TreeNode root) {
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
		if (curr == null) return;
		if (level < sum.size()) {
			sum.set(level, sum.get(level) + curr.val);
			count.set(level, count.get(level) + 1);
		} else {
			sum.add(curr.val * 1.0);
			count.add(1);
		}
		average(curr.left, level + 1, sum, count);
		average(curr.right, level + 1, sum, count);
	}

	@Override
	public List<Double> averageOfLevels2(TreeNode root) {
		List<Double> result = new ArrayList<>();
		if (root == null) return result;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			int n = queue.size();
			double sum = 0;
			for (int i = 0; i < n; i++) {
				TreeNode curr = queue.poll();
				sum += curr.val;
				if (curr.left != null) queue.add(curr.left);
				if (curr.right != null) queue.add(curr.right);
			}
			result.add(sum / n);
		}
		return result;
	}

	@Override
	public TreeNode levelOrderSuccessor(TreeNode root, TreeNode key) {
		if (root == null) return root;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			TreeNode curr = queue.poll();
			if (curr.left != null) queue.add(curr.left);
			if (curr.right != null) queue.add(curr.right);
			if (curr.val == key.val) break;
		}
		return queue.peek();
	}

	@Override
	public TreeNode levelOrderPredecessor(TreeNode root, TreeNode key) {
		if (root == null) return root;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		TreeNode prev = null;
		while (!queue.isEmpty()) {
			TreeNode curr = queue.poll();
			if (curr.val == key.val) break;
			else prev = curr;
			if (curr.left != null) queue.add(curr.left);
			if (curr.right != null) queue.add(curr.right);
		}
		return prev;
	}

	@Override
	public void swapNodes(TreeNode root) {
		// TODO Auto-generated method stub

	}

	/******************************** Type2: BT Traversals **************************/

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

		if (root == null) return result;

		queue.offer(root);
		while (!queue.isEmpty()) {
			int levelSize = queue.size();
			List<Integer> subList = new LinkedList<Integer>();
			while (levelSize-- > 0) {
				TreeNode curr = queue.poll();
				subList.add(curr.val);
				if (curr.left != null) queue.offer(curr.left);
				if (curr.right != null) queue.offer(curr.right);
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
					System.out.print(root.val + " ");
					if (root.left != null) q2.add(root.left);
					if (root.right != null) q2.add(root.right);
				}
				System.out.println();
				while (!q2.isEmpty()) {
					root = q2.poll();
					System.out.print(root.val + " ");
					if (root.left != null) q1.add(root.left);
					if (root.right != null) q1.add(root.right);
				}
				System.out.println();
			}
		}
	}

	//Using recursive function
	public void levelorder3(TreeNode root) {
		List<List<Integer>> result = new java.util.LinkedList<>();
		levelOrder(root, result, 0);
		result.forEach(k -> System.out.println(k));
	}

	public void levelOrder(TreeNode root, List<List<Integer>> result, int level) {
		if (root == null) return;

		if (result.size() <= level) result.add(new ArrayList<>());
		result.get(level).add(root.val);

		levelOrder(root.left, result, level + 1);
		levelOrder(root.right, result, level + 1);
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
				if (root.right != null) queue.add(root.right);
				if (root.left != null) queue.add(root.left);
			}

			while (!stack.isEmpty()) System.out.print(stack.pop().val + " ");

		}
	}

	// Bottom-up LevelByLevel Traversal: Iterative Approach; Single Iteration &
	// levelSize iteration
	public List<List<Integer>> levelOrderBottom1(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		List<List<Integer>> result = new LinkedList<List<Integer>>();

		if (root == null) return result;

		queue.offer(root);
		while (!queue.isEmpty()) {
			int levelSize = queue.size();
			List<Integer> subList = new LinkedList<Integer>();
			while (levelSize-- > 0) {
				if (queue.peek().left != null) queue.offer(queue.peek().left);
				if (queue.peek().right != null) queue.offer(queue.peek().right);
				subList.add(queue.poll().val);
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
		if (root == null) return;
		if (result.size() <= level) result.add(0, new ArrayList<>()); // Create new List in the zeroth position

		levelOrderBottom(root.left, result, level + 1);
		levelOrderBottom(root.right, result, level + 1);

		result.get(result.size() - level - 1).add(root.val); // Add the list into resultSize-level-1
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
					System.out.print(root.val + " ");
					if (root.left != null) s2.push(root.left);
					if (root.right != null) s2.push(root.right);
				}
				while (!s2.isEmpty()) {
					root = s2.pop();
					System.out.print(root.val + " ");
					if (root.right != null) s1.push(root.right);
					if (root.left != null) s1.push(root.left);
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
			//TODO: Change this to Hashmap; Becaue Treemap takes logn time to insert/update the data
			Map<Integer, ArrayList<Integer>> map = new TreeMap<>();
			verticalOrder(root, map, 0);

			if (map != null) {
				for (Entry<Integer, ArrayList<Integer>> entry : map.entrySet())
					System.out.println(entry.getKey() + "-" + entry.getValue());
			}
		}
	}

	private void verticalOrder(TreeNode root, Map<Integer, ArrayList<Integer>> map, int hd) {
		if (root == null) return;
		// hd - horizontal distance

		/*ArrayList<Integer> list = map.get(hd);
		if (list == null) list = new ArrayList<>();
		list.add(root.val);
		map.put(hd, list);*/

		map.putIfAbsent(hd, new ArrayList<>());
		map.get(hd).add(root.val);

		verticalOrder(root.left, map, hd - 1);
		verticalOrder(root.right, map, hd + 1);
	}

	// Vertical View1: BFS Approach
	public void verticalViewTraversal2(TreeNode root) {
		//TODO: Change this to Hashmap; Becaue Treemap takes logn time to insert/update the data
		TreeMap<Integer, ArrayList<Integer>> map = new TreeMap<>();
		Queue<QueuePack> queue = new LinkedList<>();
		QueuePack queuePack;
		//ArrayList<Integer> list = new ArrayList<>();
		TreeNode curr;
		int hd;
		queue.add(new QueuePack(0, root));
		while (!queue.isEmpty()) {
			queuePack = queue.poll();
			hd = queuePack.hd;
			curr = queuePack.node;

			/*list = map.get(hd);
			if (list == null) list = new ArrayList<>();
			list.add(curr.val);
			map.put(hd, list);*/

			map.putIfAbsent(hd, new ArrayList<>());
			map.get(hd).add(curr.val);

			if (curr.left != null) queue.add(new QueuePack(hd - 1, curr.left));
			if (curr.right != null) queue.add(new QueuePack(hd + 1, curr.right));
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
			 * if (!set.contains(hd)) { set.add(hd); System.out.print(curr.val + " "); }
			 */
			if (!map.containsKey(hd)) map.put(hd, curr.val);

			if (curr.left != null) queue.add(new QueuePack(hd - 1, curr.left));

			if (curr.right != null) queue.add(new QueuePack(hd + 1, curr.right));
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

			map.put(hd, curr.val);

			if (curr.left != null) queue.add(new QueuePack(hd - 1, curr.left));

			if (curr.right != null) queue.add(new QueuePack(hd + 1, curr.right));
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
		if (root == null) return;

		/*ArrayList<Integer> list = map.get(dist);
		if (list == null) list = new ArrayList<>();
		list.add(root.val);
		map.put(dist, list);*/

		map.putIfAbsent(dist, new ArrayList<>());
		map.get(dist).add(root.val);

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
		if (root == null) return;

		if (maxLeftLevel < level) {
			System.out.print(root.val + " ");
			maxLeftLevel = level;
		}

		leftViewOfTree2(root.left, level + 1);
		leftViewOfTree2(root.right, level + 1);
	}

	public List<Integer> leftViewOfTree3(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		leftViewOfTree3(root, result, 0);
		return result;
	}

	public void leftViewOfTree3(TreeNode root, List<Integer> result, int level) {
		if (root == null) return;
		if (level == result.size()) // Add one element per level
			result.add(root.val);

		leftViewOfTree3(root.left, result, level + 1);
		leftViewOfTree3(root.right, result, level + 1);
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
		if (root == null) return;
		if (maxRightLevel < level) {
			System.out.print(root.val + " ");
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
		if (root == null) return;
		if (level == result.size()) // Add one element per level
			result.add(root.val);

		rightSideView(root.right, result, level + 1);
		rightSideView(root.left, result, level + 1);
	}

	//	TODO: Print Left View of Binary Tree

	//	TODO: How to print all diagonal's sums for a given binary tree

	public List<Integer> boundaryOfBinaryTree(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		if (root == null) return result;

		//1.Add root
		if (!isLeaf(root)) result.add(root.val);

		//2.Add left boundary
		leftBoundary(root.left, result);

		//3.Add leaf nodes(Bottom boundary)
		leafNodes(root, result);

		//4.Add right boundary
		List<Integer> rightView = new ArrayList<>();
		rightBoundary(root.right, rightView);
		//Print result in reverse 
		for (int i = rightView.size() - 1; i >= 0; i--)
			result.add(rightView.get(i));

		return result;
	}

	private boolean isLeaf(TreeNode root) {
		return root.left == null && root.right == null;
	}

	private void leftBoundary(TreeNode curr, List<Integer> result) {
		while (curr != null) {
			if (!isLeaf(curr)) result.add(curr.val);

			if (curr.left != null) curr = curr.left;
			else curr = curr.right;
		}
	}

	private void rightBoundary(TreeNode curr, List<Integer> rightView) {
		while (curr != null) {
			if (!isLeaf(curr)) rightView.add(curr.val);

			if (curr.right != null) curr = curr.right;
			else curr = curr.left;
		}
	}

	private void leafNodes(TreeNode curr, List<Integer> result) {
		if (curr == null) return;

		if (curr.left == null && curr.right == null) result.add(curr.val);

		leafNodes(curr.left, result);
		leafNodes(curr.right, result);
	}

	/************************ Type4: BT Checking ************************/
	//	Write Code to Determine if Two Trees are Identical/Same Tree
	public boolean isSameTree(TreeNode p, TreeNode q) {
		if (p == null && q == null) return true;
		if (p == null || q == null) return false;
		return (p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right));
	}

	//	Check whether it is a mirror of itself/Symmetric Tree 
	// Recursive Approach
	public boolean isSymmetric(TreeNode root) {
		if (root == null) return true;
		return isMirror(root, root);
	}

	public boolean isMirror(TreeNode node1, TreeNode node2) {
		if (node1 == null && node2 == null) return true;
		if (node1 == null || node2 == null) return false;
		return node1.val == node2.val && isMirror(node1.left, node2.right) && isMirror(node1.right, node2.left);
	}

	// Iterative Approach
	public boolean isMirror2(TreeNode node1, TreeNode node2) {
		return false;
	}

	// Isomorphism: Combination of same tree & mirror/symmetric tree logic
	public boolean isomorphism(TreeNode node1, TreeNode node2) {
		if (node1 == null && node2 == null) return true;
		if (node1 == null || node2 == null) return false;
		// There are two possible cases for node1 and node2 to be isomorphic
		// Case 1: The subtrees rooted at these nodes have NOT been "Flipped". (Similar
		// to Normal Tree comparison)
		// Case 2: The subtrees rooted at these nodes have been "Flipped" (Similar to
		// Mirror Tree comparison)
		// Both of these subtrees have to be isomorphic, hence the &&
		return node1.val == node2.val && ((isomorphism(node1.left, node2.left) && isomorphism(node1.right, node2.right))
				|| (isomorphism(node1.left, node2.right) && isomorphism(node1.right, node2.left)));
	}

	//	Invert or Flip Binary Tree
	// Approach1:
	public TreeNode invertTree(TreeNode root) {
		if (root == null || (root.left == null && root.right == null)) return root;
		invertTree(root.left);
		invertTree(root.right);
		TreeNode temp = root.right;
		root.right = root.left;
		root.left = temp;
		return root;
	}

	// Approach2:
	public TreeNode invertTree2(TreeNode root) {
		if (root == null) return null;
		TreeNode left = root.left;
		root.left = invertTree(root.right);
		root.right = invertTree(left);
		return root;
	}

	//	Subtree of Another Tree/Check Subtree
	// Approach1: Time Complexity: O(n^2)
	public boolean isSubtree1(TreeNode s, TreeNode t) {
		if (t == null) return true;
		if (s == null) return false;

		if (isSameTree(s, t)) return true;

		return isSubtree1(s.left, t) || isSubtree1(s.right, t);
	}

	// Approach2: Time Complexity: O(n) -> Using inorder & preorder of both tree
	public boolean isSubtree2(TreeNode s, TreeNode t) {
		return false;
	}

	// Approach3: Time Complexity: O(m+n): Use Preorder traversal and build the
	// string for the both, finally check the substring
	public boolean isSubtree3(TreeNode T1, TreeNode T2) {
		if (T1 == null) return true;
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

		sb.append(root.val);
		buildTree(root.left, sb);
		buildTree(root.right, sb);
	}

	/************************ Type5: BT Print, Path, Sum & LCA Problems ************************/
	@Override
	public void pathFromRootToLeaf1(TreeNode root) {
		List<List<Integer>> result = new ArrayList<>();
		pathFromRootToLeaf1(root, new ArrayList<>(), result);
		result.forEach(k -> System.out.println(k));
	}

	private void pathFromRootToLeaf1(TreeNode root, List<Integer> path, List<List<Integer>> result) {
		if (root == null) return;

		path.add(root.val);
		if (root.left == null && root.right == null) result.add(new ArrayList<>(path));

		pathFromRootToLeaf1(root.left, path, result);
		pathFromRootToLeaf1(root.right, path, result);

		// Remove the visited path from list, if node is not present in the path
		if (!path.isEmpty()) path.remove(path.size() - 1);
	}

	@Override
	public List<String> pathFromRootToLeaf2(TreeNode root) {
		List<String> result = new ArrayList<>();
		if (root == null) return result;
		pathFromRootToLeaf2(root, result, "");
		result.forEach(k -> System.out.print(k + ", "));
		return result;
	}

	private void pathFromRootToLeaf2(TreeNode root, List<String> result, String path) {
		if (root == null) return;
		path += root.val;
		if (root.left == null && root.right == null) result.add(path);
		pathFromRootToLeaf2(root.left, result, path + "->");
		pathFromRootToLeaf2(root.right, result, path + "->");
	}

	@Override
	public void pathFromRootToAnyNode(TreeNode root, int n) {
		ArrayList<Integer> path = new ArrayList<>();
		//Approach1:
		if (pathFromRootToAnyNode1(root, n, path)) {
			path.forEach(k -> System.out.print(k + "-"));
		}

		//Approach2:
		if (pathFromRootToAnyNode2(root, n, path)) {
			path.forEach(k -> System.out.print(k + "-"));
		}
	}

	// Approach1:
	private boolean pathFromRootToAnyNode1(TreeNode root, int n, ArrayList<Integer> path) {
		if (root == null) return false;

		// To find the path, first add the element in the list and check the data
		path.add(root.val);
		if (root.val == n) return true;

		boolean flag = pathFromRootToAnyNode1(root.left, n, path);
		// This flag should check separately, otherwise when root.right returns,
		// required element will be removed
		if (!flag) flag = pathFromRootToAnyNode1(root.right, n, path);

		// Remove the visited path from list, if node is not present in the path;
		if (!flag) path.remove(path.size() - 1);
		return flag;
	}

	// Approach-2:
	private boolean pathFromRootToAnyNode2(TreeNode root, int n, ArrayList<Integer> path) {
		if (root == null) return false;

		if (root.val == n) return true;

		if (pathFromRootToAnyNode2(root.left, n, path) || pathFromRootToAnyNode2(root.right, n, path)) {
			path.add(root.val);
			return true;
		}
		return false;
	}

	//TODO:Solve this
	@Override
	public int findDistanceBwNodes(TreeNode root, int n1, int n2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int diameter(TreeNode root) {
		bTPatterns.diameterOfTree1(root);
		bTPatterns.diameterOfTree2(root);
		return 0;
	}

	@Override
	public void lowestCommonAncestor(TreeNode root, int n1, int n2) {
		bTPatterns.lowestCommonAncestor1(root, n1, n2);
		bTPatterns.lowestCommonAncestor2(root, n1, n2);
		bTPatterns.lowestCommonAncestor3(root, n1, n2);
	}

	/* Path Sum I:
	 *  Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values
	 *  along the path equals the given sum.
	 *  Note: A leaf is a node with no children.
	 *  Example: Given the below binary tree and sum = 22,
	 */
	@Override
	public boolean hasPathSumFromRootToLeaf1(TreeNode root, int sum) {
		if (root == null) return false;
		if (sum == root.val && root.left == null && root.right == null) return true;
		return hasPathSumFromRootToLeaf1(root.left, sum - root.val)
				|| hasPathSumFromRootToLeaf1(root.right, sum - root.val);

	}

	// Small Modification of prev one: Check the path sum and print the same 
	@Override
	public List<Integer> hasPathSumFromRootToLeaf2(TreeNode root, int sum) {
		List<Integer> path = new ArrayList<>();

		//Approach1:
		if (sumPathFromRootToLeaf1(root, sum, path)) {
			path.forEach(k -> System.out.print(k + " "));
			return path;
		}

		//Approach2:
		if (sumPathFromRootToLeaf2(root, sum, path)) {
			path.forEach(k -> System.out.print(k + " "));
			return path;
		}
		return null;
	}

	private boolean sumPathFromRootToLeaf1(TreeNode root, int sum, List<Integer> path) {
		if (root == null) return false;

		if (sum == root.val && root.left == null && root.right == null) {
			path.add(root.val);
			return true;
		}
		if (sumPathFromRootToLeaf1(root.left, sum - root.val, path)) {
			path.add(root.val);
			return true;
		}
		if (sumPathFromRootToLeaf1(root.right, sum - root.val, path)) {
			path.add(root.val);
			return true;
		}
		return false;
	}

	//TODO: Test this
	private boolean sumPathFromRootToLeaf2(TreeNode root, int sum, List<Integer> path) {
		if (root == null) return false;

		path.add(root.val);
		if (root.left == null && root.right == null) {
			if (sum == 0) return true;
			else return false;
		}
		boolean flag = sumPathFromRootToLeaf2(root.left, sum - root.val, path);
		if (!flag) flag = sumPathFromRootToLeaf2(root.right, sum - root.val, path);
		if (!flag) path.remove(path.size() - 1);
		return flag;
	}

	/* Path Sum II: Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
	 * Note: A leaf is a node with no children.
	 * Example: Given the below binary tree and sum = 22,
	 */
	@Override
	public List<List<Integer>> findRootToLeafPathSum(TreeNode root, int sum) {
		List<List<Integer>> result = new ArrayList<>();
		List<Integer> eachList = new ArrayList<>();
		pathSum(root, result, eachList, sum);
		return result;
	}

	public void pathSum(TreeNode root, List<List<Integer>> result, List<Integer> eachList, int sum) {
		if (root == null) return;
		eachList.add(root.val);
		if (root.val == sum && root.left == null && root.right == null) result.add(new ArrayList<>(eachList));
		pathSum(root.left, result, eachList, sum - root.val);
		pathSum(root.right, result, eachList, sum - root.val);
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
	@Override
	public int findSumBwNodes1(TreeNode root, int sum) {
		if (root == null) return 0;

		return pathSumFrom(root, sum) + findSumBwNodes1(root.left, sum) + findSumBwNodes1(root.right, sum);
	}

	private int pathSumFrom(TreeNode root, int sum) {
		if (root == null) return 0;

		return (sum == root.val ? 1 : 0) + pathSumFrom(root.left, sum - root.val)
				+ pathSumFrom(root.right, sum - root.val);
	}

	// Prefix Sum Method: Time: O(n), Space:O(n)
	@Override
	public int findSumBwNodes2(TreeNode root, int sum) {
		HashMap<Integer, Integer> preSum = new HashMap<>();
		preSum.put(0, 1);
		return helper(root, 0, sum, preSum);
	}

	public int helper(TreeNode root, int currSum, int target, HashMap<Integer, Integer> preSum) {
		if (root == null) return 0;

		currSum += root.val;
		int res = preSum.getOrDefault(currSum - target, 0);
		preSum.put(currSum, preSum.getOrDefault(currSum, 0) + 1);

		res += helper(root.left, currSum, target, preSum) + helper(root.right, currSum, target, preSum);
		preSum.put(currSum, preSum.get(currSum) - 1);
		return res;
	}

	@Override
	public void maxPathSum(TreeNode root) {
		bTPatterns.maxPathSum1(root);
		bTPatterns.maxPathSum2(root);
		bTPatterns.maxPathSum3(root);
	}

	@Override
	public int[] findFrequentTreeSum(TreeNode root) {
		Map<Integer, Integer> map = new HashMap<>();
		List<Integer> list = new ArrayList<>();
		int[] max = new int[1];

		subTreeSum(root, map, max);

		for (Integer key : map.keySet())
			if (map.get(key) == max[0]) list.add(key);

		int[] result = new int[list.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = list.get(i);

		return result;
	}

	public int subTreeSum(TreeNode root, Map<Integer, Integer> map, int[] max) {
		if (root == null) return 0;
		int sum = root.val;

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
		if (inOrderStart > inOrderEnd) return null;

		TreeNode node = new TreeNode(preOrder[preOrderIndex.index++]);
		if (inOrderStart == inOrderEnd) return node;

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
		if (inOrderStart > inOrderEnd) return null;

		TreeNode node = new TreeNode(postOrder[postOrderIndex.index--]);
		if (inOrderStart == inOrderEnd) return node;

		int rootNodeIndex = search(inOrder, inOrderStart, inOrderEnd, node.ch);
		node.right = buildTreeFromInAndPostOrder(inOrder, postOrder, rootNodeIndex + 1, inOrderEnd, postOrderIndex);
		node.left = buildTreeFromInAndPostOrder(inOrder, postOrder, inOrderStart, rootNodeIndex - 1, postOrderIndex);
		return node;
	}

	private int search(char[] charArray, int start, int end, char x) {
		for (int i = start; i <= end; i++) {
			if (charArray[i] == x) return i;
		}
		return -1;
	}

	//	Verify Preorder Serialization of a Binary Tree    

	//	Flatten Binary Tree to Linked List
	// Approach11: Inorder Traversal Modification
	public void flatten(TreeNode root) {
		if (root == null) return;

		flatten(root.left);

		if (root.left != null) {
			TreeNode tempRight = root.right;
			root.right = root.left;
			root.left = null;
			while (root.right != null) root = root.right;
			root.right = tempRight;
		}

		flatten(root.right);
	}

	//Solution using Stack
	public void flatten2(TreeNode root) {
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode curr = root;

		while (curr != null || !stack.empty()) {

			if (curr.right != null) stack.push(curr.right);

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