package com.basic.algorithms;

import com.common.model.ListNode;

/*
Divide and Conquer algorithm solves a problem using following three steps:
	1. Divide: Break the given problem into subproblems of same type.
	2. Conquer: Recursively solve these subproblems
	3. Combine: Appropriately combine the answers
	Eg: Binary Search, Quick Sort, Merge Sort, Strassen’s Algorithm
 */
public class DivideAndConquer {

	private SortingAlgorithms sort = new SortingAlgorithms();

	private MathProblems math = new MathProblems();

	public static int binarySearch(int[] a, int x) {
		return binarySearch(a, 0, a.length - 1, 610);
	}

	private static int binarySearch(int[] a, int l, int h, int x) {
		int index = -1;
		if (l > h) return index;

		int m = (l + h) / 2;
		if (a[m] == x) index = m;
		else if (x < a[m]) index = binarySearch(a, l, m - 1, x);
		else index = binarySearch(a, m + 1, h, x);

		return index;
	}

	public void mergeSort() {
		// MergeSort for array
		int[] arr = { 3, 7, 4, 8, 2, 1 };
		sort.mergeSort(arr);

		// MergeSort for linked list
		ListNode head = new ListNode(10);
		sort.mergeSort(head);
	}

	public void quickSort() {
		// QuickSort for array
		int[] arr = { 3, 7, 4, 8, 2, 1 };
		sort.quickSort(arr);
	}

	// Calculate pow(x, n)
	public void pow(double x, int n) {
		math.pow1(x, n);
		math.pow2(x, n);
		math.pow3(x, n);
	}

}