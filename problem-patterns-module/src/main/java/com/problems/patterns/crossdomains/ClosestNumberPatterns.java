package com.problems.patterns.crossdomains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

import com.common.model.TreeNode;

/*
 * The 'closest' is defined as absolute difference minimized between two integers.
 */
public class ClosestNumberPatterns {

	// Find K Closest Elements
	//Brute force approach: Time: O(n), Space: O(k)
	public List<Integer> findClosestElements1(int[] arr, int k, int x) {
		int lo = 0;
		int hi = arr.length - 1;
		while (hi - lo >= k) {
			if (Math.abs(arr[lo] - x) > Math.abs(arr[hi] - x)) {
				lo++;
			} else {
				hi--;
			}
		}

		return Arrays.stream(arr, lo, hi + 1).boxed().collect(Collectors.toList());
	}

	//Binary Search: Here find the index position for the given element x. Then find the range 
	//Time: O(log(n + k)), Space: O(k)
	public List<Integer> findClosestElements2(int[] arr, int k, int x) {
		//Find the index of the element
		int index = binarySearch(arr, x);

		int l = index - 1, h = index;
		while (k-- > 0) {
			if (h >= arr.length || (l >= 0 && x - arr[l] <= arr[h] - x)) {
				l--;
			} else {
				h++;
			}
		}
		return Arrays.stream(arr, l + 1, h).boxed().collect(Collectors.toList());
	}

	private int binarySearch(int[] nums, int target) {
		int l = 0, h = nums.length - 1, m = 0;
		while (l <= h) {
			m = l + (h - l) / 2;
			if (nums[m] == target) return m;
			else if (nums[m] < target) l = m + 1;
			else h = m - 1;
		}
		return l;
	}

	//Binary Search(Better than above): In this approach trying to find the starting index of the range
	//Time: O(log(n-k)), Space: O(k)
	//Refer this: https://leetcode.com/problems/find-k-closest-elements/discuss/106426/JavaC%2B%2BPython-Binary-Search-O(log(N-K)-%2B-K)
	public List<Integer> findClosestElements3(int[] arr, int k, int x) {
		int l = 0, h = arr.length - k, m = 0;

		while (l < h) {
			m = l + (h - l) / 2;
			//Here trying to find range instead of single value 
			// Normal BS condition: x <= arr[m]m but below one is range search
			if (x - arr[m] <= arr[m + k] - x) {
				h = m;
			} else {
				l = m + 1;
			}
		}

		/*  List<Integer> result = new ArrayList<>();
		for(int i=0; i<k;i++)
		    result.add(arr[i+l]); */

		return Arrays.stream(arr, l, l + k).boxed().collect(Collectors.toList());
	}

	//Approach4: Using minheap priority queue
	//Time: O(n*logk), Space: O(k)
	public List<Integer> findClosestElements4(int[] arr, int k, int x) {
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			if (pq.size() >= k) {
				if (Math.abs(x - arr[i]) < Math.abs(x - pq.peek())) {
					pq.poll();
					pq.add(arr[i]);
				}
			} else {
				pq.add(arr[i]);
			}
		}
		int s = pq.size();
		for (int i = 0; i < s; i++) {
			result.add(pq.remove());
		}
		return result;
	}

	//TODO: Learn how sorting works using functional programming/comparator implementation and understand the below 2 approaches
	//Using maxheap priority queue
	public List<Integer> findClosestElements5(int[] arr, int k, int x) {
		// maxHeap, sort descendingly according to diff to x
		PriorityQueue<Integer> maxHeap = new PriorityQueue<>(
				(a, b) -> Math.abs(x - b) == Math.abs(x - a) ? b - a : Math.abs(x - b) - Math.abs(x - a));
		// each time, if we have better option, delete num with max diff from x, and insert the new num
		for (int num : arr) {
			maxHeap.offer(num);
			if (maxHeap.size() >= k) {
				maxHeap.poll();
			}
		}
		// convert heap back to List<Integer> and sort them to get the original order
		List<Integer> res = new ArrayList(maxHeap);
		res.forEach(i -> System.out.println(i));
		Collections.sort(res);
		return res;
	}

	// Simple collection approach
	public List<Integer> findClosestElements6(int[] arr, int k, int x) {
		// convert int[] to List<Integer> for better implementation simplicity
		List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
		Collections.sort(list, (a, b) -> a == b ? a - b : Math.abs(a - x) - Math.abs(b - x));
		list = list.subList(0, k);
		Collections.sort(list);
		return list;
	}

	/* 3Sum Closest:
	 * Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest
	 * to target. Return the sum of the three integers. You may assume that each input would have exactly one 
	 */
	public int threeSumClosest(int[] nums, int target) {
		int minDiff = Integer.MAX_VALUE;
		int result = 0;
		Arrays.sort(nums);
		for (int i = 0; i < nums.length; i++) {
			int l = i + 1;
			int h = nums.length - 1;
			while (l < h) {
				int sum = nums[i] + nums[l] + nums[h];
				int diff = Math.abs(sum - target);

				if (diff == 0) return sum;

				if (diff < minDiff) {
					minDiff = diff;
					result = sum;
				}
				if (sum <= target) {
					l++;
				} else {
					h--;
				}
			}
		}
		return result;
	}

	/*
	 * Closest Binary Search Tree Value: Given a non-empty binary search tree and a target value, find the value in the BST that is closest 
	 * to the target.
	 * Example:
	 * 	Input: root = {5,4,9,2,#,8,10} and target = 6.124780
	 * 	Output: 5
	 */
	int closestValue = 0;

	public int closestValue(TreeNode root, double target) {
		closestValue(root, target, Double.MAX_VALUE);
		return closestValue;
	}

	private void closestValue(TreeNode node, double target, double minDiff) {
		if (node == null) return;

		if (Math.abs(target - (double) node.val) <= minDiff) {
			minDiff = Math.abs(target - (double) node.val);
			closestValue = node.val;
		}

		if (target <= node.val) closestValue(node.left, target, minDiff);
		else closestValue(node.right, target, minDiff);
	}

	//Time: O(n), Space: O(k)
	public List<Integer> closestKValues1(TreeNode root, double target, int k) {
		Queue<Integer> list = new LinkedList<>();
		inorder(root, target, k, list);
		return new ArrayList<Integer>(list);
	}

	private void inorder(TreeNode node, double target, int k, Queue<Integer> list) {
		if (node == null) return;

		inorder(node.left, target, k, list);

		if (list.size() < k) {
			list.add(node.val);
		} else if (Math.abs(target - (double) node.val) < Math.abs(target - (double) list.peek())) {
			list.poll();
			list.add(node.val);
		}

		inorder(node.right, target, k, list);
	}

	//TODO: Try to understand the solution
	public List<Integer> closestKValues2(TreeNode root, double target, int k) {
		List<Integer> result = new ArrayList<>();
		if (root == null) return result;

		Stack<Integer> precedessor = new Stack<>();
		Stack<Integer> successor = new Stack<>();

		getPredecessor(root, target, precedessor);
		getSuccessor(root, target, successor);

		for (int i = 0; i < k; i++) {
			if (precedessor.isEmpty()) {
				result.add(successor.pop());
			} else if (successor.isEmpty()) {
				result.add(precedessor.pop());
			} else if (Math.abs((double) precedessor.peek() - target) < Math.abs((double) successor.peek() - target)) {
				result.add(precedessor.pop());
			} else {
				result.add(successor.pop());
			}
		}

		return result;
	}

	private void getPredecessor(TreeNode root, double target, Stack<Integer> precedessor) {
		if (root == null) return;
		getPredecessor(root.left, target, precedessor);
		if (root.val > target) return;
		precedessor.push(root.val);
		getPredecessor(root.right, target, precedessor);
	}

	private void getSuccessor(TreeNode root, double target, Stack<Integer> successor) {
		if (root == null) return;
		getSuccessor(root.right, target, successor);
		if (root.val <= target) return;
		successor.push(root.val);
		getSuccessor(root.left, target, successor);
	}

}