
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
	 *  
	 * The greedy choice is to always pick the next activity whose finish time is least among the remaining activities and the 
	 * start time is more than or equal to the finish time of previously selected activity. We can sort the activities according
	 * to their finishing time so that we always consider the next activity as minimum finishing time activity.
	 * 
	 * Sort based on start time: To find the overlapping intervals, insert the intervals, merge overlapping intervals
	 * Sort based on end time: To find the maximum number of non-overlapping intervals. Eg: max activities, no of meeting rooms required,  
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

		int activityCount = 1, prev = 0, curr = 1;
		while (curr < n) {
			if (intervals[prev].end <= intervals[curr].start) {
				activityCount++;
				prev = curr;
			}
			curr++;
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
