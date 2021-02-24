package com.problems.patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

/*
 * SweepLine:
 * A sweep line is an imaginary vertical line which is swept across the plane rightwards. That's why, the algorithms based on this concept are 
 * sometimes also called plane sweep algorithms. We sweep the line based on some events, in order to discretize the sweep.
 * In computational geometry, a sweep line algorithm or plane sweep algorithm is an algorithmic paradigm that uses a conceptual sweep line or 
 * sweep surface to solve various problems in Euclidean space. It is one of the key techniques in computational geometry. The idea behind algorithms 
 * of this type is to imagine that a line (often a vertical line) is swept or moved across the plane, stopping at some points.
 */
public class SweepLinePatterns {

	//TODO: Closest Pair of Points:
	//Ref: https://www.geeksforgeeks.org/closest-pair-of-points-onlogn-implementation/

	//TODO: Line Segment Intersection Problem:
	//Ref: https://www.geeksforgeeks.org/given-a-set-of-line-segments-find-if-any-two-segments-intersect/

	//TODO: Remove Covered Intervals - Leet code 
	public int removeCoveredIntervals(int[][] A) {
		int res = 0, right = 0;
		Arrays.sort(A, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
		for (int[] v : A) {
			if (v[1] > right) {
				++res;
				right = v[1];
			}
		}
		return res;
	}

	/* The Skyline Problem: This problem basically removing the overlapping building from the input
	 *  or merging the 2D intervals.
	 * 
	 *  Approaches:
	 * Skyline problem without any additional object 
	 * SkyLine Problem using Priority Queue: TimeComplexity:  O(n^2) (Here n - no of building points)
	 * SkyLine Problem using TreeMap: TimeComplexity: O(nlogn);
	 */
	// Solution using Priority Queue - O(n^2) operation
	public List<int[]> getSkyline1(int[][] buildings) {
		List<int[]> points = new ArrayList<>();
		List<int[]> result = new ArrayList<>();
		//1.Split the input into two points (0,2) and (1,-ve of 2) and add both into list of array
		for (int[] building : buildings) {
			points.add(new int[] { building[0], building[2] }); //Start point
			points.add(new int[] { building[1], -building[2] }); //End point
		}
		//2.Sort the points array as per below logic.
		/* Here 0th index denotes x, 1st index denotes y or height
		 * 1. a[0]!=b[0] => ascending order based on x 
		 * 2. a[0]==b[0] => desc order based on height if both are +ve or one +ve and one -ve.
		 * 					asc order based on height if both are -ve.
		 */
		Collections.sort(points, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);

		//3.Add the heights in max priority queue one by one and find the max for each iteration
		PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
		queue.add(0);
		int prevHeight = 0, maxHeight = 0;
		for (int[] point : points) {
			if (point[1] < 0) { //If it is end point(or negative), remove from queue
				queue.remove(-point[1]);
			} else { //If point is start point add it into the queue
				queue.add(point[1]);
			}
			maxHeight = queue.peek();
			//If prevHeight != maxHeight, then add it into the result, otherwise ignore it(because it means overlapping building).
			if (prevHeight != maxHeight) {
				result.add(new int[] { point[0], maxHeight });
				prevHeight = maxHeight;
			}
		}
		return result;
	}

	// Solution using Tree Map - O(nlogn) operation
	public List<int[]> getSkyline2(int[][] buildings) {
		List<int[]> points = new ArrayList<>();
		List<int[]> result = new ArrayList<>();
		for (int[] building : buildings) {
			points.add(new int[] { building[0], building[2] });
			points.add(new int[] { building[1], -building[2] });
		}
		/* Here 0th index denotes x, 1st index denotes y or height
		 * 1. a[0]!=b[0] => ascending order based on x 
		 * 2. a[0]==b[0] => desc order based on height if both are +ve or one +ve and one -ve
		 * 					asc order based on height if both are -ve,
		 */
		Collections.sort(points, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);

		//Key - Height, Val - Count
		TreeMap<Integer, Integer> map = new TreeMap<>();
		map.put(0, 1);
		int prevHeight = 0, maxHeight = 0;
		for (int[] point : points) {
			if (point[1] < 0) {
				int value = map.get(-point[1]);
				if (value > 1) map.put(-point[1], value - 1);
				else map.remove(-point[1]);
			} else {
				map.put(point[1], map.getOrDefault(point[1], 0) + 1);
			}
			//Get the max value from tree map
			maxHeight = map.lastKey();
			if (prevHeight != maxHeight) {
				result.add(new int[] { point[0], maxHeight });
				prevHeight = maxHeight;
			}
		}
		return result;
	}

	//	Rectangle Area II
	//	Perfect Rectangle
	//	Time Intersection
	//	Number of Airplanes in the Sky

}
