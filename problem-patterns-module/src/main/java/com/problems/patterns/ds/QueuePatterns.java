package com.problems.patterns.ds;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class QueuePatterns {

	/********************* Type1: Queue Simple Problems ****************/

	//Circular tour/Gas Station - Circular Queue & Greed Alg
	/* There are N gas stations along a circular route, where the amount of gas at station i is gas[i].
	 * You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1). 
	 * You begin the journey with an empty tank at one of the gas stations.
	 * Return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1.
	 * 
	 * Note: gas[i] -> Amount of gas in the gas station;
	 *       cost[i] -> Amount of gas required to travel from ith station to i+1th station
	 */
	public int canCompleteCircuit1(int[] gas, int[] cost) {
		int n = gas.length, tank = 0;
		for (int i = 0; i < n; i++)
			tank += gas[i] - cost[i];
		if (tank < 0) return -1;

		int rem = 0, start = 0;
		for (int i = 0; i < n; i++) {
			rem += gas[i] - cost[i];
			if (rem < 0) {
				start = i + 1;
				rem = 0;
			}
		}
		return start;
	}

	//Using Circular Array/Queue
	public int canCompleteCircuit(int[] gas, int[] cost) {
		int n = gas.length, front = 0, rear = 1;
		int rem = gas[front] - cost[front];
		if (n == 1) return rem < 0 ? -1 : 0;
		while (front != rear || rem < 0) {
			//Move front ptr
			while (front != rear && rem < 0) {
				rem -= gas[front] - cost[front];
				front = (front + 1) % n;
				if (front == 0) return -1;
			}
			rem += gas[rear] - cost[rear];
			rear = (rear + 1) % n;
		}
		return front;
	}

	//First non-repeating character in a stream
	public void firstNonRepeatingChar(char[] ch) {
		if (ch.length == 0) return;
		Queue<Character> queue = new LinkedList<>();
		int[] count = new int[26];
		for (int i = 0; i < ch.length; i++) {
			// Add first non repeating char in queue
			queue.add(ch[i]);
			// Increase the count array corresponding to the char
			count[ch[i] - 'a']++;

			while (!queue.isEmpty()) {
				if (count[queue.peek() - 'a'] > 1) {
					queue.poll();
				} else {
					System.out.print(queue.peek() + " ");
					break;
				}
			}
			if (queue.isEmpty()) System.out.print("-1" + " ");
		}
	}

	//LRU Cache

	//LFU Cache 

	/********************* Type1: Monotonic Queue Problems ****************/
	/*
	 * Monotonic Queue: A monotonic Queue is a data structure the elements from the front to the end is strictly either increasing or decreasing.
	 * Monotonic increasing queue: to push an element e, starts from the rear element, we pop out element s>=e(violation).
	 * Monotonic decreasing queue: we pop out element s<=e (violation).
	 */

	//monotonous increasing stack: elements in the monotonous increase queue keeps an increasing order.
	public void monotonicIncreasingQueue(int[] arr) {
		Deque<Integer> queue = new ArrayDeque<>(); //or new LinkedList<>();
		for (int i = 0; i < arr.length; i++) {
			while (!queue.isEmpty() && queue.getLast() > arr[i]) {
				queue.removeLast();
			}
			queue.add(arr[i]);
		}
	}

	//monotonous decreasing stack: elements in the monotonous decrease queue keeps an decreasing order.
	public void monotonicDecreasingQueue(int[] arr) {
		Deque<Integer> queue = new LinkedList<>();
		for (int i = 0; i < arr.length; i++) {
			while (!queue.isEmpty() && queue.getLast() < arr[i]) {
				queue.removeLast();
			}
			queue.add(arr[i]);
		}
	}

	/* Sliding Window Maximum/Maximum of all subarrays of size k:
	 * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the
	 * very right. You can only see the k numbers in the window. Each time the sliding window moves right by one
	 * position. Return the max sliding window.
	 * Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3; Output: [3,3,5,5,6,7] 
	 * 
	 * Solution:
	 * 	1. BruteForce: O(nk)
	 *  2. Balanced BST(TreeMap): O(nlogk)
	 *  3. Sliding Window: O(n)
	 */
	public int[] maxSlidingWindow(int[] nums, int k) {
		if (nums.length == 0 || k > nums.length) return new int[0];

		int n = nums.length;
		int[] result = new int[n - k + 1];
		//Queue to store the index of the elements; Max element index should be retained in the deque front.
		Deque<Integer> deque = new LinkedList<>();

		for (int i = 0; i < n; i++) {
			//If 'i' reaches the size k, then Remove the top element 
			if (!deque.isEmpty() && i - deque.peekFirst() == k) deque.removeFirst();

			//Keep removing the smaller element from the last in deque
			while (!deque.isEmpty() && nums[i] > nums[deque.peekLast()]) deque.removeLast();

			deque.addLast(i);

			if (i + 1 >= k) result[i - k + 1] = nums[deque.peek()];
		}

		return result;
	}

	/*
	 * Min Max Riddle:
	 * You will be given a list of integers arr and a single integer k. You must create an array of length k
	 *  from elements of arr such that its unfairness is minimized. Call that array subArr. 
	 *  Unfairness of an array is calculated as
	 *  	max(subArr) - min(subArr);
	 *  	Where:
	 *  		- max denotes the largest integer in subArr
	 *   		- min denotes the smallest integer in subArr*/
	int maxMin(int k, int[] nums) {
		if (nums.length == 0 || k > nums.length) return 0;
		Arrays.sort(nums);

		int n = nums.length, maxDiff = Integer.MAX_VALUE;
		// Queue to store the index of the elements; Max element index should be
		// retained in the deque front.
		Deque<Integer> minDeque = new LinkedList<>();
		Deque<Integer> maxDeque = new LinkedList<>();

		for (int i = 0; i < n; i++) {
			// If 'i' reaches the size k, then Remove the top element
			if (!minDeque.isEmpty() && i - minDeque.peek() == k) minDeque.poll();
			if (!maxDeque.isEmpty() && i - maxDeque.peek() == k) maxDeque.poll();

			// Keep removing the smaller element from the last in deque
			while (!minDeque.isEmpty() && nums[i] < nums[minDeque.peekLast()]) minDeque.removeLast();
			while (!maxDeque.isEmpty() && nums[i] > nums[maxDeque.peekLast()]) maxDeque.removeLast();
			minDeque.addLast(i);
			maxDeque.addLast(i);

			if (i + 1 >= k) maxDiff = Math.min(maxDiff, nums[maxDeque.peek()] - nums[minDeque.peek()]);
		}
		return maxDiff;
	}

	//Shortest Subarray with Sum at Least K
	public int shortestSubarray(int[] A, int K) {
		int N = A.length, res = N + 1;
		int[] B = new int[N + 1];
		for (int i = 0; i < N; i++)
			B[i + 1] = B[i] + A[i];
		Deque<Integer> d = new ArrayDeque<>();
		for (int i = 0; i < N + 1; i++) {
			while (d.size() > 0 && B[i] - B[d.getFirst()] >= K) {
				res = Math.min(res, i - d.pollFirst());
			}
			while (d.size() > 0 && B[i] <= B[d.getLast()]) d.pollLast();
			d.addLast(i);
		}
		return res <= N ? res : -1;
	}
}