package com.problems.patterns.crossdomains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

import com.common.model.TreeNode;

/*
 * The 'closest' is defined as absolute difference minimized between two integers. Here we have to 
 * find closest of any given target. abs(target-a[i])
 * 
 * Approaches used:
 * 	  - "Record and Move on" to find the closest values
 */
public class ClosestNumberPatterns {

	/* 3Sum Closest:
	 * Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest
	 * to target. Return the sum of the three integers. You may assume that each input would have exactly one 
	 */
	public int threeSumClosest(int[] nums, int target) {
		int minDiff = Integer.MAX_VALUE;
		int result = Integer.MAX_VALUE, n = nums.length;
		Arrays.sort(nums);
		for (int i = 0; i < n - 2; i++) {
			int l = i + 1;
			int h = n - 1;
			while (l < h) {
				int sum = nums[i] + nums[l] + nums[h];
				if (sum == target) return sum;

				int diff = Math.abs(sum - target);
				//Record the result and move on
				if (diff < minDiff) {
					minDiff = diff;
					result = sum;
				}
				//or
				/*if (result == Integer.MAX_VALUE || Math.abs(sum - target) < Math.abs(result - target)) {
					result = sum;
				}*/

				if (sum < target) {
					l++;
				} else {
					h--;
				}
			}
		}
		return result;
	}

	/*
	 * Search for closest the element – BS & record-and-move-on approach
	 * Given the element is sorted
	 */
	public int searchClosestElement(int[] arr, int target) {
		int l = 0, h = arr.length - 1, m = 0, index = -1;
		while (l <= h) {
			m = l + (h - l) / 2;
			if (target == arr[m]) return m;
			//Record and move on
			if (index == -1 || (Math.abs(arr[m] - target) < Math.abs(arr[index] - target))) {
				index = m;
			}

			if (target < arr[m]) h = m - 1;
			else l = m + 1;
		}
		return index;
	}

	// Find K Closest Elements
	//Brute force approach: Time: O(n), Space: O(k)
	public List<Integer> findKClosestElements1(int[] arr, int k, int x) {
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

	//Binary Search(Better than above): In this approach trying to find the starting index of the range
	//Time: O(log(n-k)), Space: O(k)
	//Refer this: https://leetcode.com/problems/find-k-closest-elements/discuss/106426/JavaC%2B%2BPython-Binary-Search-O(log(N-K)-%2B-K)
	public List<Integer> findKClosestElements2(int[] arr, int k, int x) {
		int l = 0, h = arr.length - 1 - k, m = 0;

		while (l <= h) {
			m = l + (h - l) / 2;
			//Here trying to find range instead of single value 
			// Normal BS condition: x <= arr[m] but below one is find the starting index of range 'k'
			if (x - arr[m] <= arr[m + k] - x) {
				h = m - 1;
			} else {
				l = m + 1;
			}
		}

		/*  List<Integer> result = new ArrayList<>();
		for(int i=0; i<k;i++)
		result.add(arr[i+l]); */

		return Arrays.stream(arr, l, l + k).boxed().collect(Collectors.toList());

	}

	//Approach4: Using Queue: This works because input is sorted
	//Time: O(n), Space: O(k)
	public List<Integer> findKClosestElements3(int[] arr, int k, int x) {
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < arr.length; i++) {
			if (queue.size() < k) {
				queue.add(arr[i]);
			} else if (Math.abs(x - arr[i]) < Math.abs(x - queue.peek())) {
				queue.poll();
				queue.add(arr[i]);
			}
		}
		return new ArrayList<>(queue);
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
		return closestValue(root, target, Integer.MAX_VALUE);
	}

	private int closestValue(TreeNode node, double target, int closest) {
		if (node == null) return closest;

		//Record and move on
		if (closest == Integer.MAX_VALUE
				|| Math.abs(target - (double) node.val) < Math.abs(target - (double) closest)) {
			closest = node.val;
		}

		if (target <= node.val) return closestValue(node.left, target, closest);
		else return closestValue(node.right, target, closest);
	}

	//Approach 2: Using global variable
	private void closestValue(TreeNode node, double target, double minDiff) {
		if (node == null) return;

		if (Math.abs(target - (double) node.val) <= minDiff) {
			minDiff = Math.abs(target - (double) node.val);
			closestValue = node.val;
		}

		if (target <= node.val) closestValue(node.left, target, minDiff);
		else closestValue(node.right, target, minDiff);
	}

	/*
	 * Closest Binary Search Tree Value II/Find K closest values in BST:
	 */
	//Time: O(n), Space: O(k)
	public List<Integer> closestKValues1(TreeNode root, double target, int k) {
		Queue<Integer> queue = new LinkedList<>();
		inorder(root, target, k, queue);
		return new ArrayList<Integer>(queue);
	}

	private void inorder(TreeNode node, double target, int k, Queue<Integer> queue) {
		if (node == null) return;

		inorder(node.left, target, k, queue);

		if (queue.size() < k) {
			queue.add(node.val);
		} else if (Math.abs(target - (double) node.val) < Math.abs(target - (double) queue.peek())) {
			queue.poll();
			queue.add(node.val);
		}

		inorder(node.right, target, k, queue);
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

	public static void main(String[] args) {
		ClosestNumberPatterns ob = new ClosestNumberPatterns();
		int[] arr = { 1, 1, 2, 2, 2, 2, 2, 3, 3 };
		ob.findKClosestElements2(arr, 3, 2).forEach(k -> System.out.print(k + " "));

		int[] arr2 = { 3, 2, 1, 2, 1, 2, 3, 2, 2 };
		ob.findKClosestElements2(arr2, 3, 2).forEach(k -> System.out.print(k + " "));
	}
}