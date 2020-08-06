package com.consolidated.problems.design.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.common.model.NestedInteger;

/*
 * Flatten Nested List Iterator:
 * Given a nested list of integers, implement an iterator to flatten it. Each element is either 
 * an integer, or a list -- whose elements may also be integers or other lists.
 * Example 1: Input: [[1,1],2,[1,1]]; Output: [1,1,2,1,1]
 * Example 2: Input: [1,[4,[6]]]; Output: [1,4,6]
 */
public class FlattenNestedIterator implements Iterator<Integer> {
	Stack<NestedInteger> stack;

	public FlattenNestedIterator(List<NestedInteger> nestedList) {
		stack = new Stack<>();
		flattenList(nestedList);
	}

	@Override
	public Integer next() {
		if (!hasNext())
			return null;

		return stack.pop().getInteger();
	}

	@Override
	public boolean hasNext() {
		while (!stack.isEmpty()) {
			// If top NestedInteger is integer return true 
			if (stack.peek().isInteger())
				return true;

			// If top NestedInteger is list, then pop the top element and flatten the list
			flattenList(stack.pop().getList());
		}

		return false;
	}

	public void flattenList(List<NestedInteger> nestedList) {
		for (int i = nestedList.size() - 1; i >= 0; i--)
			stack.push(nestedList.get(i));
	}
}
