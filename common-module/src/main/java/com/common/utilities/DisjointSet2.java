package com.common.utilities;

import java.util.HashMap;
import java.util.Map;

public class DisjointSet2 implements DisJointOperations {

	public Map<Integer, Integer> nodeMap;

	public DisjointSet2() {
		nodeMap = new HashMap<>();
	}

	@Override
	public void initialize(int size) {
		for (int i = 0; i < size; i++)
			set(i, i);
	}

	@Override
	public void set(int node, int parentNode) {
		nodeMap.put(node, parentNode);
	}

	@Override
	public int get(int node) {
		return nodeMap.get(node);
	}

	@Override
	public boolean union(int set1, int set2) {
		int root1 = find(set1);
		int root2 = find(set2);
		if (root1 != root2) {
			nodeMap.put(root2, root1);
			return false;
		}
		return true;
	}

	@Override
	public int find(int node) {
		while (nodeMap.get(node) != node) {
			int parentNode = nodeMap.get(node);
			nodeMap.put(node, nodeMap.get(parentNode)); //Path Compression

			node = nodeMap.get(node);
		}
		return node;
	}

	@Override
	public int find2(int node) {
		if (nodeMap.get(node) == node) return node;

		nodeMap.put(node, find2(nodeMap.get(node))); //Path Compression
		return nodeMap.get(node);
	}

}
