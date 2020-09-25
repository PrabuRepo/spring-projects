
package com.basic.algorithms;

import java.util.Arrays;

import com.common.model.Interval;

/*
 * Greedy is an algorithmic paradigm that builds up a solution piece by piece, always choosing the next piece that offers the most
 * obvious and immediate benefit. So the problems where choosing locally optimal also leads to global solution are best fit for Greedy. 
 * An optimization problem can be solved using Greedy if the problem has the following property: At every step, we can make a choice 
 * that looks best at the moment, and we get the optimal solution of the complete problem.
 * 
 * Standard Greedy algorithms are,
 * 	Kruskal’s Minimum Spanning Tree
 *  Prim's Minimum Spannig Tree
 *  Dijikstra's Algorithm
 *  Huffman Encoding for compression technique
 */
public class GreedyAlgorithms {

	/* Activity Selection Problem:
	 *  Select the maximum number of activities that can be performed by a single person, assuming that a person can only work
	 *  on a single activity at a time.
	 *  
	 *  Time Complexity: O(nlogn)
	 */
	public int maxActivities(int[] start, int[] end) {
		if (start.length == 0 || end.length == 0) return 0;
		int n = start.length;

		// Construct Interval Object;
		Interval[] intervals = new Interval[n];
		for (int i = 0; i < n; i++)
			intervals[i] = new Interval(start[i], end[i]);

		// Sort based on end time
		Arrays.sort(intervals, (a, b) -> (a.end - b.end));

		int activityCount = 1, l = 0, r = 1;
		while (r < n) {
			if (intervals[l].end <= intervals[r].start) {
				activityCount++;
				l = r;
			}
			r++;
		}

		return activityCount;
	}

	public void kruskalsAlgorithm() {
		//Refer Graph.java
	}

	public void primsAlgorithm() {
		//Refer Graph.java
	}

	public void dijikstrasAlgorithm() {
		//Refer Graph.java
	}

}
