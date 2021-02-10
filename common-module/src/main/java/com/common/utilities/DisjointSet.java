package com.common.utilities;

/*
 * Simple Disjoint set/Union find implementation using array, it will be useful in competitive programming.
 */
/* DisJointSet using Array Implementation:
 * 	In array,
 * 		index denotes node
 * 		value of index denotes Parent of node
 * 
 * Example:
 * 	Set1 = {1,2,3}; Here 1, 2, 3 are nodes and comes under one set
 *  Set2 = {4,5,6}; Here 4, 5, 6 are nodes and comes under one set
 */
public class DisjointSet implements DisJointOperations {
	public int[] nodes;

	public DisjointSet(int size) {
		nodes = new int[size];
	}

	@Override
	public void initialize(int size) {
		for (int i = 0; i < size; i++) {
			set(i, i);
		}
	}

	@Override
	public void set(int node, int parentNode) {
		nodes[node] = parentNode;
	}

	@Override
	public int get(int node) {
		return nodes[node];
	}

	@Override
	public boolean union(int node1, int node2) {
		int parent1 = find(node1);
		int parent2 = find(node2);
		// Means already pointed to same parent, no need to combine or union the nodes
		if (parent1 == parent2) return true;

		// If it doesn't have same nodes
		nodes[parent2] = parent1;
		return false; // Means pointed to same parent or union the two sets
	}

	@Override
	public int find(int i) {
		while (nodes[i] != i) {
			nodes[i] = nodes[nodes[i]]; // Path Compression
			i = nodes[i];
		}

		return i;
	}

	@Override
	public int find2(int node) {
		if (nodes[node] == node) return node;

		nodes[node] = find2(nodes[node]);
		return nodes[node];
	}
}

interface DisJointOperations {
	void initialize(int size);

	void set(int node, int parentNode);

	//Returns the parent of any given node
	int get(int node);

	//Find the parent of the node. Search the array/nodes till node and its parent is same.
	int find(int node);

	//Recursive solution
	int find2(int node);

	/* Combine 2 nodes and make it as set. 
	 * If both nodes are in same set then return true, otherwise combine those nodes and make it as single set.
	 */
	boolean union(int node1, int node2);
}
