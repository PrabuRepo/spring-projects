package com.problems.patterns.crossdomains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Stack;

import com.common.model.Cell;
import com.common.model.TreeNode;
import com.common.utilities.Utils;

/*Kth element Pattern - Heap/BS/QuickSelect*/
public class KthElementPatterns {
	//	Kth Smallest Element in a Sorted Matrix
	//	Kth Smallest Number in Multiplication Table
	//	Kth Smallest Number in M Sorted Lists

	/********************* Search Kth element *************************/
	//	Kth Smallest/Largest Element in an Array
	// K’th Smallest Element in Unsorted Array
	/* Find the kth Smallest element in an unsorted array. Note that it is the kth largest element in the sorted order,
	 * not the kth distinct element.
	 * Example 1: Input: [3,2,1,5,6,4] and k = 2; Output: 2
	 */
	// Approach1:Sort the given array using a sorting algorithm and return the element at index k-1 in the sorted array.
	// Time Complexity: O(nLogn)
	public int kthSmallestElementInArray1(int[] a, int k) {
		Arrays.sort(a);
		return a[k - 1];
	}

	// Approach2: Using Max Binary Heap: Time Complexity-O(nlogk)
	// Same solution used in the Kth Largest Element in the Stream
	public int kthSmallestElementInArray21(int[] arr, int k) {
		PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
		for (int i = 0; i < arr.length; i++) {// O(nlogk) times
			if (queue.isEmpty() || queue.size() < k) {
				queue.add(arr[i]);
			} else if (arr[i] < queue.peek()) {
				queue.remove();
				queue.add(arr[i]);
			}
		}
		return queue.peek();
	}

	// Approach3: Using Min Binary Heap: Time Complexity-O(n+klogn)
	public int kthSmallestElementInArray22(int[] arr, int k) {
		PriorityQueue<Integer> queue = new PriorityQueue<>();
		for (int i = 0; i < arr.length; i++) // O(n) Time to build the heap
			queue.add(arr[i]);

		for (int i = 0; i < k - 1; i++) // O(k.logn) time
			queue.remove();

		return queue.peek();
	}

	/*
	 * Using Quick sort Partitioning or Quick Select: Expect Linear Time complexity: O(n) 
	 * K’th Smallest/Largest Element in Unsorted Array -  
	 *   Partition or Quick Select: The partition subroutine of quicksort can also be used to solve this problem. 
	 *  In partition, we divide the array into elements>=pivot pivot elements<=pivot. Then, according to the index of pivot,
	 *  we will know whther the kth largest element is to the left or right of pivot or just itself. In average, this
	 *  algorithm reduces the size of the problem by approximately one half after each partition, giving the 
	 *  recurrence T(n) = T(n/2) + O(n) with O(n) being the time for partition. The solution is T(n) = O(n), which means 
	 *  we have found an average linear-time solution. However, in the worst case, the recurrence will become 
	 *  T(n) = T(n - 1) + O(n) and T(n) = O(n^2).
	 */
	// This is simpler than kthSmallestElementInArray32
	public int kthSmallestElementInArray31(int[] nums, int k) {
		if (nums.length == 0 || k == 0) return 0;

		int l = 0, r = nums.length - 1;

		while (l <= r) {
			int index = partition(nums, l, r); // Here partition being invoked all the condition

			if (index == k - 1) return nums[index];
			else if (index < k - 1) l = index + 1;
			else r = index - 1;
		}

		return -1;
	}

	/* Partition:
	 * Left side elements are less than pivotIndex(i) and right side elements are greater than pivotIndex(i)
	 * Use Partition to find the kth Smallest Element; 
	 */
	public int partition(int[] a, int left, int right) {
		int i = left, j = left, pivot = a[right];
		while (j < right) {
			if (a[j] < pivot) {
				Utils.swap(a, i, j);
				i++;
			}
			j++;
		}
		Utils.swap(a, i, right);
		return i;
	}

	/*
	 * Kth Largest Element in a Stream:
	 * 	Design a class to find the kth largest element in a stream. Note that it is the kth largest element in the sorted order, 
	 * not the kth distinct element. Your KthLargest class will have a constructor which accepts an integer k and an integer 
	 * array nums, which contains initial elements from the stream. For each call to the method KthLargest.add, return the 
	 * element representing the kth largest element in the stream.
	 * int k = 3; int[] arr = [4,5,8,2]; 
	 * KthLargest kthLargest = new KthLargest(3, arr);
	 * 	kthLargest.add(3);   // returns 4
	 * 	kthLargest.add(5);   // returns 5
	 */
	PriorityQueue<Integer> queue;
	int k;

	public void init(int k, int[] nums) {
		this.queue = new PriorityQueue<>();
		this.k = k;
		for (int i = 0; i < nums.length; i++) {
			add(nums[i]);
		}
	}

	public int add(int val) {
		queue.add(val);
		if (queue.size() > k) queue.poll();

		return queue.size() < k ? -1 : queue.peek();
	}

	/* Kth Smallest Element in a Sorted Matrix: 
	 * Given a n x n matrix where each of the rows and columns are sorted in ascending order, find the kth smallest element in the matrix.
	 * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
	 * 	Example:
	 * 	matrix = [[ 1,  5,  9],	[10, 11, 13],[12, 13, 15]], k = 8, return 13.
	 */
	public int kthSmallestInMatrix1(int[][] matrix, int k) {
		if (matrix == null || matrix.length == 0) return 0;
		int r = matrix.length, c = matrix[0].length;
		if (k > r * c) return 0;

		// Priority Queue arranged based on val
		PriorityQueue<Cell> queue = new PriorityQueue<>((ob1, ob2) -> ob1.data - ob2.data);

		// Add 1st row in the matrix: TC: O(n)( Build a min heap which takes O(n) time)
		for (int j = 0; j < c; j++)
			queue.add(new Cell(0, j, matrix[0][j]));

		// Remove one by one and next row element corresponding to val; TC:O(klogn)(Heapify k times which takes O(kLogn)
		// time.)
		for (int i = 1; i < k; i++) {
			Cell cell = queue.poll();
			if (cell.i < r - 1) {
				queue.add(new Cell(cell.i + 1, cell.j, matrix[cell.i + 1][cell.j]));
			}
		}

		return queue.peek().data;
	}

	//Using Binary Search:
	/* 1.Since we are given 1 <= k <= n^2, the kth number must exist in [lo, hi] range.
	 * 2.We use binary search to find the minimum number A, such that the count of ( numbers satisfying num <= A ) is >= k.
	 */
	public int kthSmallestInMatrix2(int[][] matrix, int k) {
		if (matrix == null || matrix.length == 0) return 0;
		int r = matrix.length, c = matrix[0].length;
		if (k > r * c) return 0;

		int l = matrix[0][0], h = matrix[r - 1][c - 1];
		while (l < h) {
			int m = (l + h) / 2;
			int count = count1(matrix, m);
			// int count = count2(matrix, m); 
			System.out.println(m + " - " + count);
			if (count < k) l = m + 1;
			else h = m;
		}
		return l;
	}

	//Count no elements less equal to target
	public int count1(int[][] matrix, int target) {
		int r = matrix.length, c = matrix[0].length;
		int i = r - 1, j = 0, count = 0;
		while (i >= 0 && j < c) {
			if (matrix[i][j] <= target) {
				count += i + 1;
				j++;
			} else {
				i--;
			}
		}
		return count;
	}

	//binary search to find the count.
	private int count2(int[][] matrix, int target) {
		int m = matrix.length;
		int n = matrix[0].length;
		int count = 0;
		for (int i = 0; i < m; i++) {
			// binary search, speed up a little bit.
			int left = 0;
			int right = n - 1;
			int mid = (left + right) / 2; // may overflow
			while (left < right) {
				mid = (left + right + 1) / 2;
				if (matrix[i][mid] > target) {
					right = mid - 1;
				} else {
					left = mid;
				}
			}
			count += (left + 1);
			if (matrix[i][left] > target) {
				count--;
			}
		}
		return count;
	}

	//	Kth Smallest/Largest Element in a BST 
	/*
	 * Kth Smallest Element in a BST:
	 * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
	 * Note: You may assume k is always valid, 1 <= k <= BST's total elements.
	 * Example 1: Input: root = [3,1,4,null,2], k = 1
	 * 
	 * Solution: 
	 * 	Approach1: 
	 * 		i. Inorder traversal - Recursive
	 * 		ii.Inorder traversal Modification - Recursive
	 * 		iii.Inorder traversal - Iterative
	 * 	Approach2: 
	 * 		Using Priority Queue
	 */
	// Approach1: i.Using Inorder Traversal
	public int kthSmallest11(TreeNode root, int k) {
		if (root == null || k == 0) return 0;
		ArrayList<Integer> list = new ArrayList<>();
		kthSmallest(root, list);
		return list.get(k - 1);
	}

	public void kthSmallest(TreeNode root, ArrayList<Integer> list) {
		if (root == null) return;
		kthSmallest(root.left, list);
		list.add(root.val);
		kthSmallest(root.right, list);
	}

	// ii.Inorder traversal Modification
	int count = 0;
	int result = Integer.MIN_VALUE;

	public int kthSmallest12(TreeNode root, int k) {
		traverse(root, k);
		return result;
	}

	public void traverse(TreeNode root, int k) {
		if (root == null) return;
		traverse(root.left, k);
		count++;
		if (count == k) {
			result = root.val;
			return;
		}
		traverse(root.right, k);
	}

	// iii.Inorder traversal - Iterative
	public int kthSmallest13(TreeNode root, int k) {
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode p = root;
		int count = 0;
		while (!stack.isEmpty() || p != null) {
			if (p != null) {
				stack.push(p); // Just like recursion
				p = p.left;
			} else {
				TreeNode node = stack.pop();
				if (++count == k) return node.val;
				p = node.right;
			}
		}
		return Integer.MIN_VALUE;
	}

	// Approach2: Using Heap
	public int kthSmallest2(TreeNode root, int k) {
		if (root == null) return 0;
		PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
		kthSmallest(root, queue, k);
		return queue.peek();
	}

	public void kthSmallest(TreeNode root, PriorityQueue<Integer> queue, int k) {
		if (root == null) return;
		if (queue.isEmpty() || queue.size() < k || root.val < queue.peek()) {
			if (queue.size() == k) queue.remove();
			queue.add(root.val);
		}
		kthSmallest(root.left, queue, k);
		kthSmallest(root.right, queue, k);
	}

	/*
	 * 'K' Closest Points to the Origin: 
	 *  We have a list of points on the plane.  Find the K closest points to the origin (0, 0). (Here, the distance between two 
	 *  points on a plane is the Euclidean distance.)
	 *  You may return the answer in any order.  The answer is guaranteed to be unique (except for the order that it is in.)
	 *  
	 *  Example 1: Input: points = [[1,3],[-2,2]], K = 1; Output: [[-2,2]]
	 *  Explanation: The distance between (1, 3) and the origin is sqrt(10).
	 *  			 The distance between (-2, 2) and the origin is sqrt(8).
	 *  Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin. We only want the closest K = 1 points from the origin, so the
	 *  answer is just [[-2,2]].
	 */

	/* Note: Euclidean Distance Formula: Sqrt((x2-x1)^2 + (y2-y1)^2). But in this problem distance calculates from origin(0,0). 
	 * Simplified formula formula will be: sqrt((x2)^2 + (y2)^2)    
	 */

	//Sort:  O(NlogN),
	public int[][] kClosest1(int[][] points, int K) {
		//Sort in ascending order
		Arrays.sort(points, (p1, p2) -> (getDistance(p1) - getDistance(p2)));
		return Arrays.copyOfRange(points, 0, K);
	}

	//Using Heap: Time: O(nlogk), space: O(k)
	public int[][] kClosest2(int[][] points, int k) {
		//Sort in descending order
		PriorityQueue<int[]> queue = new PriorityQueue<>((p1, p2) -> (getDistance(p2)) - getDistance(p1));

		for (int[] point : points) {
			queue.add(point);
			if (queue.size() > k) queue.poll();
		}
		int[][] result = new int[k][2];

		for (int i = 0; i < k; i++)
			result[i] = queue.poll();
		return result;
	}

	/* Approach3: Using QuickSelect: Avg Time complexity: O(N) & Worst Case: O(N^2)
	 * Explanation: Ideally, in first iteration it will run n times, in the second iteration I will n/2 times, in the third iteration I will run n/4.... , therefore
	 * sum(n + n/2 + n/4 + ...) = O(n), here I have to do logN iterations.
	 * But in worst case, sum(n + n - 1 + n - 2 +.... ) = O(n^2), here I have to do N iterations
	 */
	public int[][] kClosest3(int[][] points, int K) {
		int len = points.length, l = 0, r = len - 1;
		while (l <= r) {
			int mid = helper(points, l, r);
			if (mid == K - 1) break;
			if (mid < K) {
				l = mid + 1;
			} else {
				r = mid - 1;
			}
		}
		return Arrays.copyOfRange(points, 0, K);
	}

	private int helper(int[][] A, int l, int r) {
		int[] pivot = A[r];
		int i = l, j = l;
		while (j < r) {
			if (compare(A[j], pivot) < 0) {
				swap(A, i, j);
				i++;
			}
			j++;
		}
		swap(A, i, r);

		return i;
	}

	private int compare(int[] p1, int[] p2) {
		return getDistance(p1) - getDistance(p2);
	}

	//Distance calculated from origin (0,0):
	//(x2)^2 + (y2)^2 calculation; sqrt has been ignored
	private int getDistance(int[] point) {
		return point[0] * point[0] + point[1] * point[1];
	}

	private void swap(int[][] A, int i, int j) {
		int[] temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}
}
