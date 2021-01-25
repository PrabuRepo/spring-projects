package com.common.utilities;

import java.util.HashMap;
import java.util.Map;

/*
 * Simple Disjoint set/Union find implementation using array, it will be useful in competitive programming.
 */
public class DisjointSet {
	public int[] parent;

	public DisjointSet(int capacity) {
		parent = new int[capacity];
	}

	public boolean union(int set1, int set2) {
		int root1 = find(set1);
		int root2 = find(set2);
		if (root1 != root2) { // If it doesn't have same parent
			parent[root2] = root1;
			return false; // Means pointed to same parent or union the two sets
		}
		return true; // Means already pointed to same parent, no need to combine or union the sets
	}

	public int find(int i) {
		while (parent[i] != i) {
			parent[i] = parent[parent[i]]; // Is that mandatory??
			i = parent[i];
		}

		return i;
	}

	public int find1(int node) {
		if (parent[node] == node) return node;

		parent[node] = find1(parent[node]);
		return parent[node];
	}
}

class DisjointSet2 {

	public Map<Integer, Integer> root;

	public DisjointSet2() {
	}

	public void initialize(int[] arr) {
		root = new HashMap<>();
		for (int val : arr) {
			root.put(val, val);
		}
	}

	public boolean union(int set1, int set2) {
		int root1 = find(set1);
		int root2 = find(set2);
		if (root1 != root2) { // If it doesn't have same parent
			root.put(root2, root1);
			return false;
		}
		return true; // Means already pointed to same parent, no need to combine or union the sets
	}

	public int find(int node) {
		while (root.get(node) != node) {
			int parentNode = root.get(node);
			root.put(node, root.get(parentNode)); //Path Compression

			node = root.get(node);
		}
		return node;
	}

	public int find2(int node) {
		if (root.get(node) == node) return node;

		root.put(node, find2(root.get(node))); //Path Compression
		return root.get(node);
	}

}