package com.problems.patterns.crossdomains;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.common.model.UndirectedGraphNode;

public class CloneProblems {

	// Clone an Undirected Graph - DFS/BFS
	// Using BFS traversal to clone the graph
	public static UndirectedGraphNode cloneGraph1(UndirectedGraphNode root) {
		if (root == null)
			return null;
		Queue<UndirectedGraphNode> queue = new LinkedList<>();
		// cloneMap: node, cloneNode
		HashMap<UndirectedGraphNode, UndirectedGraphNode> cloneMap = new HashMap<>();
		cloneMap.put(root, new UndirectedGraphNode(root.label));
		queue.add(root);
		while (!queue.isEmpty()) {
			UndirectedGraphNode currCloneNode, neighborClone, currNode;
			currNode = queue.poll();
			currCloneNode = cloneMap.get(currNode);
			for (UndirectedGraphNode neighbor : currNode.neighbors) {
				neighborClone = cloneMap.get(neighbor);
				if (neighborClone == null) {
					neighborClone = new UndirectedGraphNode(neighbor.label);
					cloneMap.put(neighbor, neighborClone);
					queue.add(neighbor);
				}
				currCloneNode.neighbors.add(neighborClone);
			}
		}
		return cloneMap.get(root);
	}

	// DFS traversal using stack & iterative
	public static UndirectedGraphNode cloneGraph2(UndirectedGraphNode node) {
		if (node == null)
			return null;

		HashMap<UndirectedGraphNode, UndirectedGraphNode> hm = new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
		LinkedList<UndirectedGraphNode> stack = new LinkedList<UndirectedGraphNode>();
		UndirectedGraphNode head = new UndirectedGraphNode(node.label);
		hm.put(node, head);
		stack.push(node);

		while (!stack.isEmpty()) {
			UndirectedGraphNode curnode = stack.pop();
			for (UndirectedGraphNode aneighbor : curnode.neighbors) {// check each neighbor
				if (!hm.containsKey(aneighbor)) {// if not visited,then push to stack
					stack.push(aneighbor);
					UndirectedGraphNode newneighbor = new UndirectedGraphNode(aneighbor.label);
					hm.put(aneighbor, newneighbor);
				}

				hm.get(curnode).neighbors.add(hm.get(aneighbor));
			}
		}

		return head;
	}

	// Using DFS traversal recursive
	public static UndirectedGraphNode cloneGraph3(UndirectedGraphNode root) {
		if (root == null)
			return root;
		// CloneMap: node label, Clone node
		HashMap<Integer, UndirectedGraphNode> cloneMap = new HashMap<>();
		return cloneGraph(root, cloneMap);
	}

	private static UndirectedGraphNode cloneGraph(UndirectedGraphNode root,
			HashMap<Integer, UndirectedGraphNode> cloneMap) {
		UndirectedGraphNode clone = new UndirectedGraphNode(root.label);
		cloneMap.put(root.label, clone);
		for (UndirectedGraphNode neigbhor : root.neighbors) {
			UndirectedGraphNode neighborClone = cloneMap.get(neigbhor.label);
			if (neighborClone != null) {
				clone.neighbors.add(neighborClone);
			} else {
				clone.neighbors.add(cloneGraph(neigbhor, cloneMap));
			}
		}
		return clone;
	}
}