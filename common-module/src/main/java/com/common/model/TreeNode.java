package com.common.model;

/*
 * Binary Tree Node
 */
public class TreeNode {
	public int val;
	public char ch;
	public TreeNode left, right, next;
	public int count;

	public TreeNode(int val) {
		this.val = val;
		this.left = this.right = null;
		this.next = null;
	}

	public TreeNode(int val, int count) {
		this.val = val;
		this.count = count;
	}

	public TreeNode(char ch) {
		this.ch = ch;
		left = right = null;
	}
}