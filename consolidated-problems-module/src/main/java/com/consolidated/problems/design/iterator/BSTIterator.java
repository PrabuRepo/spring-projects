package com.consolidated.problems.design.iterator;

import java.util.Stack;

import com.common.model.TreeNode;

public class BSTIterator {
	Stack<TreeNode> stack;

	public BSTIterator(TreeNode root) {
		stack = new Stack<>();
		//Push all the left node in the stack
		push(root);
	}

	/** @return the next smallest number */
	public int next() {
		TreeNode node = stack.pop();
		int val = node.data;
		//Push the right side nodes
		if (node.right != null)
			push(node.right);
		return val;
	}

	/** @return whether we have a next smallest number */
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	public void push(TreeNode node) {
		while (node != null) {
			stack.push(node);
			node = node.left;
		}
	}
}
