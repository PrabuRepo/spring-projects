package com.problems.patterns.crossdomains;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.common.model.GraphNode2;

public class CloneProblems {

	// Clone an Undirected Graph - DFS/BFS
	// Using BFS traversal to clone the graph
	public GraphNode2 cloneGraph1(GraphNode2 root) {
		if (root == null) return null;
		Queue<GraphNode2> queue = new LinkedList<>();
		// Here visited map used to store the visited nodes as well clone node for each node.
		// visited: node.label, cloneNode
		HashMap<Integer, GraphNode2> visited = new HashMap<>();
		visited.put(root.label, new GraphNode2(root.label));
		queue.add(root);
		while (!queue.isEmpty()) {
			GraphNode2 currNode = queue.poll();
			GraphNode2 cloneNode = visited.get(currNode.label);
			//Iterate all neighbors
			for (GraphNode2 neighbor : currNode.neighbors) {
				if (!visited.containsKey(neighbor.label)) {
					visited.put(neighbor.label, new GraphNode2(neighbor.label));
					queue.add(neighbor);
				}
				cloneNode.neighbors.add(visited.get(neighbor.label));
			}
		}
		return visited.get(root.label);
	}

	// DFS traversal using stack & iterative
	public GraphNode2 cloneGraph2(GraphNode2 root) {
		if (root == null) return null;
		LinkedList<GraphNode2> stack = new LinkedList<GraphNode2>();
		// visited: node.label, cloneNode
		HashMap<Integer, GraphNode2> visited = new HashMap<>();
		visited.put(root.label, new GraphNode2(root.label));
		stack.push(root);

		while (!stack.isEmpty()) {
			GraphNode2 currNode = stack.pop();
			GraphNode2 cloneNode = visited.get(currNode.label);
			//Iterate all neighbors
			for (GraphNode2 neighbor : currNode.neighbors) {
				if (!visited.containsKey(neighbor.label)) {
					visited.put(neighbor.label, new GraphNode2(neighbor.label));
					stack.push(neighbor);
				}
				cloneNode.neighbors.add(visited.get(neighbor.label));
			}
		}

		return visited.get(root.label);
	}

	// Using DFS traversal recursive
	public GraphNode2 cloneGraph3(GraphNode2 root) {
		if (root == null) return root;
		// visited: node label, Clone node
		HashMap<Integer, GraphNode2> visited = new HashMap<>();
		return cloneGraph(root, visited);
	}

	private GraphNode2 cloneGraph(GraphNode2 root, HashMap<Integer, GraphNode2> visited) {
		GraphNode2 cloneNode = new GraphNode2(root.label);
		visited.put(root.label, cloneNode);

		for (GraphNode2 neigbhor : root.neighbors) {
			if (!visited.containsKey(neigbhor.label)) {
				cloneNode.neighbors.add(cloneGraph(neigbhor, visited));
			} else {
				cloneNode.neighbors.add(visited.get(neigbhor.label));
			}
		}
		return cloneNode;
	}

	/* Copy List with Random Pointer:
	 * A linked list is given such that each node contains an additional random pointer which could point to any node
	 * in the list or null.	Return a deep copy of the list.
	 */
	// Approach1: Using HashMap: Time-O(n), Space:O(n)
	public Node copyRandomList1(Node head) {
		if (head == null) return head;
		Map<Integer, Node> map = new HashMap<>();
		Node curr = head;
		while (curr != null) {
			map.put(curr.val, new Node(curr.val, null, null));
			curr = curr.next;
		}
		curr = head;
		Node clone = map.get(head.val);
		while (curr != null) {
			clone.next = curr.next == null ? null : map.get(curr.next.val);
			clone.random = curr.random == null ? null : map.get(curr.random.val);

			curr = curr.next;
			clone = clone.next;
		}
		return map.get(head.val);
	}

	// Approach2: Efficient Linear Approach: Time-O(n), Space-O(1)
	public Node copyRandomList2(Node head) {
		if (head == null) return head;
		Node curr = head, clone;
		//1.Create clone node after each node 
		while (curr != null) {
			clone = new Node(curr.val, curr.next, null);
			curr.next = clone;
			curr = clone.next;
		}

		//2.Arrange random pointer for the clone node 
		curr = head;
		clone = null;
		while (curr != null) {
			clone = curr.next;
			clone.random = curr.random == null ? null : curr.random.next;
			curr = clone.next;
		}

		//3.Split the original and cloned nodes
		//Note: Cant merge step 2 and 3, because random pointer which points to behind the curr nodes does not work.
		curr = head;
		Node cloneHead = curr.next;
		clone = cloneHead;
		while (curr != null) {
			curr.next = clone.next;
			clone.next = clone.next == null ? null : clone.next.next;

			clone = clone.next;
			curr = curr.next;
		}
		return cloneHead;
	}
}

class Node {
	public int val;
	public Node next;
	public Node random;

	public Node(int _val, Node _next, Node _random) {
		val = _val;
		next = _next;
		random = _random;
	}
}