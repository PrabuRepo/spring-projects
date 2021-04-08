package com.problems.patterns.crossdomains;

import java.util.Random;

import com.common.model.ListNode;

public class RandomNumberPatterns {

	public static void main(String[] args) {
		RandomNumberPatterns ob = new RandomNumberPatterns();
		System.out.println("Get Random Node: ");
		int[] array2 = { 1, 0, 6, 2, 3, 9, 4, 5, 8, 7 };
		for (int x : array2)
			ob.insertInOrder(x);
		System.out.println("Random Node: " + ob.getRandomNode().data);
		System.out.println("Random Node: " + ob.getRandomNode().data);
	}

	//Random Number in Array: Simple Random.nextInt(n) API

	//Random Number in Stream: https://www.geeksforgeeks.org/select-a-random-number-from-stream-with-o1-space/?ref=rp

	/* Linked List Random Node: https://www.geeksforgeeks.org/select-a-random-node-from-a-singly-linked-list/
	 *  Given a singly linked list, return a random node's value from the linked list. Each node must have the same probability
	 *  of being chosen.
	 *  Follow up: What if the linked list is extremely large and its length is unknown to you? Could you solve this efficiently
	 *  without using extra space?
	 */
	ListNode node;
	Random rand;

	/**
	 * The linked list's head. Note that the head is guaranteed to be not null, so it contains at least
	 * one node.
	 */
	public void init(ListNode head) {
		node = head;
		rand = new Random();
	}

	/** Returns a random node's value. */
	public int getRandom() {
		ListNode curr = node;
		int count = 1, randomVal = 0;
		while (curr != null) {
			if (rand.nextInt(count++) == 0) randomVal = curr.data;
			curr = curr.next;
		}
		return randomVal;
	}

	/*
	 * Linked List Random Node:
	 * Given a singly linked list, return a random node's value from the linked list. Each node must have the same
	 * probability of being chosen.
	 */
	public int getRandom2(ListNode node) {
		ListNode curr = node;
		Random random = new Random();
		int count = 1, randomVal = 0;
		while (curr != null) {
			if (random.nextInt(count++) == 0) randomVal = curr.data;
			curr = curr.next;
		}
		return randomVal;
	}

	/* 
	 * 11.Random Node: You are implementing a binary tree class from scratch which, in addition to insert, find, and delete, has a 
	 * method getRandomNode() which returns a random node from the tree. All nodes should be equally likely to be chosen. Design
	 * and implement an algorithm for getRandomNode, and explain how you would implement the rest of the methods.
	 */
	TreeNode1 root = null;

	public void insertInOrder(int value) {
		if (root == null) root = new TreeNode1(value);
		else root.insertInOrder(value);
	}

	public int size() {
		return root == null ? 0 : root.size();
	}

	public TreeNode1 getRandomNode() {
		if (root == null) return null;

		Random random = new Random();
		int i = random.nextInt(size());
		return root.getIthNode(i);
	}

	/* Random Node in BST: https://www.geeksforgeeks.org/select-random-node-tree-equal-probability/
	 * Approach1:
	 * A simple solution is to store Inorder traversal of tree in an array. Let the count of nodes be n. To get a random 
	 * node, we generate a random number from 0 to n-1, use this number as index in array and return the value at index.
	 * Approach2:
	 * An alternate solution is to modify tree structure. We store count of children in every node. Consider the above
	 * tree. We use inorder traversal here also. We generate a number smaller than or equal count of nodes. We traverse
	 * tree and go to the node at that index. We use counts to quickly reach the desired node. With counts, we reach in
	 * O(h) time where h is height of tree.
	 * - Try this approach 
	 */
}

class TreeNode1 {
	public int data;
	public TreeNode1 left;
	public TreeNode1 right;
	private int size = 0;

	public TreeNode1(int d) {
		data = d;
		size = 1;
	}

	public void insertInOrder(int d) {
		if (d <= data) {
			if (left == null) left = new TreeNode1(d);
			else left.insertInOrder(d);
		} else {
			if (right == null) right = new TreeNode1(d);
			else right.insertInOrder(d);
		}
		size++;
	}

	public int size() {
		return size;
	}

	public TreeNode1 find(int d) {
		if (d == data) return this;
		else if (d <= data) return left != null ? left.find(d) : null;
		else if (d > data) return right != null ? right.find(d) : null;
		return null;
	}

	public TreeNode1 getRandomNode() {
		int leftSize = left == null ? 0 : left.size();
		Random random = new Random();
		int index = random.nextInt(size);

		if (index < leftSize) return left.getRandomNode();
		else if (index == leftSize) return this;
		else return right.getRandomNode();
	}

	public TreeNode1 getIthNode(int i) {
		int leftSize = left == null ? 0 : left.size();

		if (i < leftSize) return left.getIthNode(i);
		else if (i == leftSize) return this;
		else return right.getIthNode(i - (leftSize + 1));
	}

	// Move this consolidated Module:
	// 528. Random Pick with Weight
	int[] prefixSum;
	Random random;

	public void init(int[] w) {
		random = new Random();
		prefixSum = w;
		for (int i = 1; i < w.length; i++)
			prefixSum[i] += prefixSum[i - 1];
	}

	public int pickIndex() {
		// generate random number in [1,sum of elements]
		int val = random.nextInt(prefixSum[prefixSum.length - 1]) + 1;
		//this returns the index of the random number
		return insertPosition(prefixSum, val);
	}

	//Search Insert Position
	private int insertPosition(int[] prefixSum, int target) {
		int l = 0, r = prefixSum.length - 1;
		while (l <= r) {
			int m = l + (r - l) / 2;
			if (target == prefixSum[m]) return m;
			else if (target < prefixSum[m]) r = m - 1;
			else l = m + 1;
		}
		return l;
	}

}