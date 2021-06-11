package com.problems.patterns.ds;

import com.common.model.ListNode;

/* Pattern: In-place Reversal of a LinkedList
 * 		Introduction Reverse a LinkedList (easy) 
 * 		Reverse a Sub-list (medium) 
 * 		Reverse every K-element Sub-list (medium) 
 * 		Reverse alternating K-element Sub-list (medium) 
 * 		Rotate a LinkedList (medium)
 * 
 *  Note: Consider below three positions for all the LL operations: 
 *  	     1.First; 2.Last 3.Middle(Anywhere between first & last);
 *  
 *  Tips for index based traverse from first node to given node(K)
 *  	Case 1: Move to prev to the Kth node
 *  			i. Using Incrementer: count = 1; while(++count < K)
 *  			ii.Using Decrementer: while(--k > 1)
 *  	Case 2: Move to the Kth node
 *    			i. Using Incrementer: count = 1; while(count++ < K)
 *  			ii.Using Decrementer: while(k-- > 1)
 *  	Case 3: Move to next to the Kth node
 *	 			i. Using Incrementer: count = 1; while(count++ <= K)
 *  			ii.Using Decrementer: while(k-- >= 1)
 */
public class InPlaceReversalLLPatterns {
	// Reverse a LinkedList (easy)
	// Iterative Approach
	public ListNode reverseList1(ListNode head) {
		ListNode curr = head, next, prev = null;
		while (curr != null) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		return prev;
	}

	// Recursive Approach
	public ListNode reverseList2(ListNode head) {
		return helper(head, null);
	}

	public ListNode helper(ListNode curr, ListNode prev) {
		if (curr == null) return prev;
		ListNode next = curr.next;
		curr.next = prev;
		return helper(next, curr);
	}

	// Reverse a Sub-list (medium)
	// Reverse a linked list II: Reverse a linked list from position m to n. Do it in one-pass.
	public ListNode reverseBetween(ListNode head, int m, int n) {
		if (head == null) return head;

		ListNode curr = head, prev = null;
		int i = 1;
		// Move to mth node:
		while (i++ < m) {
			prev = curr;
			curr = curr.next;
		}

		ListNode mthNode = curr, prevMthNode = prev, next;
		// Reverse the node from 'm' to node 'n'
		// Here prev node moving to nthNode and curr node moving to next to curr node
		while (i++ <= n) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}

		// If m=1, then preMthNode is null and prev node is pointing to head
		if (prevMthNode != null) prevMthNode.next = prev;
		else head = prev;

		// Here curr is next to nth Node
		mthNode.next = curr;

		return head;
	}

	// Reverse every K-element Sub-list (medium)
	/* Reverse Nodes in k-Group:
	 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
	 * Eg: 1->2->3->4->5 and k=3; Result: 3->2->1->4->5
	 */
	//Head Recursive Call approach
	public ListNode reverseKGroup(ListNode head, int k) {
		if (head == null) return head;
		int count = 1;
		ListNode curr = head;
		// Validation and find the k+1th node
		while (count++ <= k && curr != null) {
			curr = curr.next;
		}

		//If count is less than equal to k, then do not reverse the list
		if (count <= k) return head;

		// reverse list with k+1th node as head and this call returns "head of reversed list" 
		ListNode prev = reverseKGroup(curr, k);

		// LL Reversal Alg: reverse current k-group from head ptr
		while (count-- > 1) {
			ListNode next = head.next;
			head.next = prev;
			prev = head;
			head = next;
		}
		return prev;
	}

	// Reverse K group including no of elements less than k. 
	// Eg: 1->2->3->4->5 and k=3; Result: 3->2->1->5->4
	//Tail Recursive Call approach
	public ListNode reverseKGroup2(ListNode head, int k) {
		if (head == null || head.next == null) return head;
		ListNode prev = null, curr = head, next = null;
		int count = 1;
		while (count++ <= k && curr != null) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		//reverse list with k+1th node as head and this call returns "head of reversed list" 
		head.next = reverseKGroup2(curr, k);
		return prev;
	}

	// Reverse alternating K-element Sub-list (medium)
	/* Reverses alternate k nodes and returns the pointer to the new head node */
	ListNode kAltReverse(ListNode head, int k) {
		ListNode curr = head, next = null, prev = null;
		int count = 1;
		/*1) LL Revese Alg: reverse first k nodes of the linked list */
		while (curr != null && count++ <= k) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		/* 2) Now head points to the kth node. So change next of head to (k+1)th node*/
		if (head != null) head.next = curr;

		/* 3) We do not want to reverse next k nodes. So move the curr pointer to skip next k nodes.
		 *    Move to previous of kth node. 
		 */
		count = 1;
		while (count++ < k && curr != null) {
			curr = curr.next;
		}
		/* 4) Recursively call for the list starting from curr->next. 
		And make rest of the list as next of first node */
		if (curr != null) {
			curr.next = kAltReverse(curr.next, k);
		}
		/* 5) prev is new head of the input list */
		return prev;
	}

	// Rotate a LinkedList (medium)
	/* 1. Rotate Left or Clockwise rotation:
	 *  Eg:Input: 1->2->3->4->5->NULL, k = 2;
	 * Output: 3->4->5->1->2->NULL
	 */
	public ListNode rotateLeft(ListNode head, int k) {
		if (k <= 0) return head;
		ListNode curr = head;
		int count = 1;
		while (count++ < k && curr != null) {
			curr = curr.next;
		}
		if (curr == null) return head;

		ListNode kthNode = curr;
		while (curr.next != null) {
			curr = curr.next;
		}
		curr.next = head;
		head = kthNode.next;
		kthNode.next = null;
		return head;
	}

	/* Rotate Right or Anti Clockwise Rotation:
	 * Eg:Input: 1->2->3->4->5->NULL, k = 2;
	 * Output: 4->5->1->2->3->NULL
	 */
	public ListNode rotateRight(ListNode head, int k) {
		//Find size of list
		int size = listSize(head);

		if (head == null || k <= 0 || k == size) return head;
		if (k > size) k %= size;

		return rotateLeft(head, size - k);
	}

	public int listSize(ListNode head) {
		int count = 0;
		while (head != null) {
			count++;
			head = head.next;
		}
		return count;
	}
}