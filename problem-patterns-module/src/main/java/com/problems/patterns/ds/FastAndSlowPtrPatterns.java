package com.problems.patterns.ds;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.common.model.ListNode;

/*
 * Floyd's cycle-finding algorithm is a pointer algorithm that uses only two pointers, which move through the sequence at different speeds. 
 * The idea is to move fast pointer twice as quickly as the slow pointer and the distance between them increases by 1 at each step. If at
 * some point both meet, we found a cycle in the list, else we will reach the end of the list and no cycle is present. It is also 
 * called as "Tortoise and Hare" algorithm.
 */
public class FastAndSlowPtrPatterns {

	InPlaceReversalLLPatterns reversalLLPatterns = new InPlaceReversalLLPatterns();

	// LinkedList Cycle: Floyd's Algorithm or Tortoise & Hare Algorithm
	public boolean hasCycle(ListNode head) {
		ListNode slowPtr = head, fastPtr = head;
		while (fastPtr != null && fastPtr.next != null) {
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
			if (slowPtr == fastPtr) return true;
		}
		return false;
	}

	/* Start of LinkedList Cycle/Linked List Cycle II: Linked List Cycle II: Given a linked list, return the node
	 *  where the cycle begins. If there is no cycle, return null. */
	public ListNode detectCycle(ListNode head) {
		if (head == null || head.next == null) return null;
		ListNode slow = head, fast = head, start = head;
		while (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				while (slow != start) {
					slow = slow.next;
					start = start.next;
				}
				return start;
			}
		}
		return null;
	}

	// Happy Number (medium)
	/* Write an algorithm to determine if a number is "happy". A happy number is a number defined by the following process:
	 * Starting with any positive integer, replace the number by the sum of the squares of its digits, and repeat the
	 * process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
	 * Those numbers for which this process ends in 1 are happy numbers.
	 */
	/* Solution: Using floyd'd algorithm, we can find whether square number equals 1 (where it will stay), or it loops
	 * endlessly in a cycle which does not include 1.
	 */
	public boolean isHappy1(int n) {
		if (n == 0) return false;

		int slow = n, fast = n;
		do {
			slow = squareSum(slow);
			fast = squareSum(squareSum(fast));
		} while (slow != fast);

		return (slow == 1);
	}

	private int squareSum(int n) {
		int sum = 0, digit = 0;
		while (n > 0) {
			digit = n % 10;
			sum += (digit * digit); // Square
			n = n / 10;
		}
		return sum;
	}

	/* Using HashSet: The idea is to use one hash set to record sum of every digit square of every number occurred.
	 * Once the current sum cannot be added to set, return false; once the current sum equals 1, return true;
	 */
	public boolean isHappy2(int n) {
		Set<Integer> set = new HashSet<Integer>();
		while (set.add(n)) {
			int squareSum = squareSum(n);
			if (squareSum == 1) return true;
			n = squareSum;
		}
		return false;
	}

	// Find the Duplicate Number (easy)
	// Floyd's Tortoise and Hare; Time Complexity:O(n); Space Complexity: O(1)
	public int findDuplicate8(int[] nums) {
		int tortoise = 0;
		int hare = 0;
		// Find the intersection point of the two runners.
		do {
			tortoise = nums[tortoise]; // slowPtr
			hare = nums[nums[hare]]; // fastPtr
		} while (tortoise != hare);

		// Find the "entrance" to the cycle.
		int start = 0;
		while (tortoise != start) {
			start = nums[start];
			tortoise = nums[tortoise];
		}
		return tortoise;
	}

	// Middle of the LinkedList (easy)
	//Approach1: For getting first mid element in the even size of LL; i.e. fastPtr.next != null
	public ListNode middleNode1(ListNode head) {
		ListNode slowPtr = head, fastPtr = head;
		while (fastPtr != null && fastPtr.next != null) {
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
		}
		return slowPtr;
	}

	//Approach2: For getting second mid element in the even size of LLl i.e. fastPtr.next.next != null
	public ListNode middleNode2(ListNode head) {
		if (head == null) return null;
		ListNode slowPtr = head, fastPtr = head;
		while (fastPtr.next != null && fastPtr.next.next != null) {
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
		}
		return slowPtr;
	}

	// Palindrome Linked List:
	public boolean isPalindrome1(ListNode head) {
		Stack<ListNode> stack = new Stack<>();
		ListNode curr = head;
		while (curr != null) {
			stack.push(curr);
			curr = curr.next;
		}
		curr = head;
		while (curr != null) {
			if (curr.data != stack.pop().data) return false;
			curr = curr.next;
		}
		return true;
	}

	public boolean isPalindrome2(ListNode head) {
		if (head == null || head.next == null) return true;
		ListNode slowPtr = head, fastPtr = head, prevNode = null;
		while (fastPtr != null && fastPtr.next != null) {
			prevNode = slowPtr;
			slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
		}
		ListNode midNode = null;
		if (fastPtr != null) {
			midNode = slowPtr;
			slowPtr = slowPtr.next;
		}
		prevNode.next = null;
		ListNode secondHalf = slowPtr;
		secondHalf = reversalLLPatterns.reverseList1(secondHalf);
		boolean flag = compare(head, secondHalf);
		secondHalf = reversalLLPatterns.reverseList1(secondHalf);
		if (midNode != null) {
			prevNode.next = midNode;
			midNode.next = slowPtr;
		} else {
			prevNode.next = slowPtr;
		}
		return flag;
	}

	public boolean compare(ListNode node1, ListNode node2) {
		if (node1 == null && node2 == null) return true;
		if (node1 == null || node2 == null) return false;
		return ((node1.data == node2.data) && compare(node1.next, node2.next));
	}

	//TODO: Cycle in a Circular Array (hard) -> Refer "Circular Array Loop" problems in leetcode
}
