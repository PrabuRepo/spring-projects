package com.consolidated.problems.datastructures;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class QueueProblems {

	/********************** Type1: Queue usage problems *******************/

	public long[] riddle(long[] arr) {
		int n = arr.length;
		long max = 0;
		long[] result = new long[n];
		Deque<Integer> deque;

		for (int window = 1; window <= n; window++) {// Iterate for window size from 0-n
			max = 0;
			deque = new LinkedList<>();
			for (int i = 0; i < n; i++) {
				// Remove the element in the front end
				if (!deque.isEmpty() && i == deque.getFirst() + window)
					deque.removeFirst();

				// Remove the element in the rear end. This condition always keeps min element in the front side
				while (!deque.isEmpty() && arr[deque.getLast()] > arr[i])
					deque.removeLast();

				// Add element at the rear end
				deque.addLast(i);

				// index is greater than window size find max element from min elements
				if (i >= window - 1)
					max = Math.max(max, arr[deque.getFirst()]);
			}
			result[window - 1] = max;
		}
		return result;
	}

	public int maxMin(int k, int[] nums) {
		if (nums.length == 0 || k > nums.length)
			return 0;
		Arrays.sort(nums);

		int n = nums.length, maxDiff = Integer.MAX_VALUE;
		// Queue to store the index of the elements; Max element index should be retained in the deque front.
		Deque<Integer> minDeque = new LinkedList<>();
		Deque<Integer> maxDeque = new LinkedList<>();

		for (int i = 0; i < n; i++) {
			// If 'i' reaches the size k, then Remove the top element
			if (!minDeque.isEmpty() && i - minDeque.peek() == k)
				minDeque.poll();
			if (!maxDeque.isEmpty() && i - maxDeque.peek() == k)
				maxDeque.poll();

			// Keep removing the smaller element from the last in deque
			while (!minDeque.isEmpty() && nums[i] < nums[minDeque.peekLast()])
				minDeque.removeLast();
			while (!maxDeque.isEmpty() && nums[i] > nums[maxDeque.peekLast()])
				maxDeque.removeLast();

			minDeque.addLast(i);
			maxDeque.addLast(i);

			if (i + 1 >= k)
				maxDiff = Math.min(maxDiff, nums[maxDeque.peek()] - nums[minDeque.peek()]);

		}

		return maxDiff;
	}
}

class MinQueue {
	PrintWriter out;
	Queue<Integer> queue = new LinkedList<>();
	LinkedList<Integer> mins = new LinkedList<>();

	public int remove() {
		int front = queue.poll();
		// Update the min value
		if (mins.peek() == front)
			mins.poll();
		return front;
	}

	public int min() {
		return mins.peek();
	}

	public void add(int data) {
		queue.add(data); // Add the data in the queue

		// Update the min value
		if (mins.isEmpty() || mins.peekLast() < data) {
			mins.add(data);
		} else {
			while (!mins.isEmpty() && mins.peekLast().compareTo(data) > 0)
				mins.removeLast();

			mins.add(data);
		}
	}
}