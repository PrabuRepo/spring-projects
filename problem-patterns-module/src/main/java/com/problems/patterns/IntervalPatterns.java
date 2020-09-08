package com.problems.patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

import com.common.model.Interval;

public class IntervalPatterns {
	/********************* Interval Patterns - Selection Problems **************************/
	/*
	 * All the below problems are mostly identifying the overlap in the points.
	 * Overlapping points = No of meeting rooms required/No of platforms required/No of activites done
	 */
	/* Activity Selection Problem:
	 *  Select the maximum number of activities that can be performed by a single person, assuming that a person can only work
	 *  on a single activity at a time.
	 *  
	 *  Time Complexity: O(nlogn)
	 */
	public int maxActivities(int[] start, int[] end) {
		int n = start.length;
		Interval[] intervals = new Interval[n];
		for (int i = 0; i < n; i++)
			intervals[i] = new Interval(start[i], end[i]);
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

	/*
	 * N meetings in one room:
	 *   There are n meetings in the form of (S [ i ], F [ i ]) where S [ i ] is start time of meeting i and F [ i ] is finish time 
	 *  of meeting i.What is the maximum number of meetings that can be accommodated in the meeting room?
	 *  
	 */
	// Similar to Activity Selection Problem -2
	public void findMeetingsInOneRoom(int[] start, int[] end) {
		int n = start.length;
		Interval[] intervals = new Interval[n];
		for (int i = 0; i < n; i++)
			intervals[i] = new Interval(start[i], end[i], i + 1);
		Arrays.sort(intervals, (a, b) -> (a.end - b.end));
		int l = 0, r = 1;
		System.out.print(intervals[l].order + " ");
		while (r < n) { // Starting Index
			if (intervals[l].end <= intervals[r].start) {
				System.out.print(intervals[r].order + " ");
				l = r;
			}
			r++;
		}
	}

	/* Meeting Rooms I: 
	 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...]
	 * (si<ei), determine if a person could attend all meetings. For example, Given [[0, 30],[5, 10],[15, 20]], return
	 * false.
	 */
	public boolean canAttendAllMeetings(Interval[] intervals) {
		if (intervals.length <= 1) return true;

		Arrays.sort(intervals, (ob1, ob2) -> ob1.start - ob2.start);

		for (int i = 0; i < intervals.length - 1; i++) {
			if (intervals[i].end > intervals[i + 1].start) return false;
		}
		return true;
	}

	/* Meeting Rooms II:
	 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] find the minimum
	 * number of conference rooms required.
	 */
	// Approach1: Greedy Algorithm:
	public int minMeetingRooms1(Interval[] intervals) {
		int len = intervals.length;
		int[] startTime = new int[len];
		int[] endTime = new int[len];

		for (int i = 0; i < len; i++) {
			Interval curr = intervals[i];
			startTime[i] = curr.start;
			endTime[i] = curr.end;
		}
		Arrays.sort(startTime);
		Arrays.sort(endTime);
		int activeMeetings = 0, numMeetingRooms = 0, i = 0, j = 0;
		while (i < len && j < len) {
			if (startTime[i] < endTime[j]) {
				activeMeetings++;
				numMeetingRooms = Math.max(numMeetingRooms, activeMeetings);
				i++;
			} else {
				activeMeetings--;
				j++;
			}
		}
		return numMeetingRooms;
	}

	// Approach2: Using Heap
	public int minMeetingRooms2(Interval[] intervals) {
		Arrays.sort(intervals, (ob1, ob2) -> ob1.start - ob2.start);
		PriorityQueue<Integer> queue = new PriorityQueue<>();
		queue.add(intervals[0].end);
		int count = 1;
		for (int i = 1; i < intervals.length - 1; i++) {
			if (queue.peek() > intervals[i].start) {
				count++;
			} else {
				queue.poll();
			}
			queue.add(intervals[i].end);
		}
		return count;
	}

	// Approach3: Using TreeMap:
	public int minMeetingRooms3(Interval[] intervals) {
		// Here treeMap sorts the time in asc order
		TreeMap<Integer, Integer> timesMap = new TreeMap<>();
		for (Interval i : intervals) {
			timesMap.put(i.start, timesMap.getOrDefault(i.start, 0) + 1);
			timesMap.put(i.end, timesMap.getOrDefault(i.end, 0) - 1);
		}
		int count = 0, res = 0;
		for (int c : timesMap.values()) {
			count += c;
			res = Math.max(res, count);
		}
		return res;
	}

	/* Minimum Platforms:
	 * Given arrival and departure times of all trains that reach a railway station. Your task is to find the minimum
	 * number of platforms required for the railway station so that no train waits. Note: Consider that all the trains
	 * arrive on the same day and leave on the same day. Also, arrival and departure times must not be same for a train.
	 */
	// Using Simple Greedy Alg:
	public int minPlatformRequired1(int[][] arr, int[][] dep) {
		// arr, dep has 2 dim array, 0th index has time,
		// 1st index has order/seq of the train
		Arrays.sort(arr, (a, b) -> a[0] - b[0]);
		Arrays.sort(dep, (a, b) -> a[0] - b[0]);
		int platform = 1, i = 1, j = 0, result = 1;
		while (i < arr.length && j < dep.length) {
			// Additional 'or' condition to handle the train arrives & departure at the same time
			if (arr[i][0] < dep[j][0] || (arr[i][0] == dep[j][0] && arr[i][1] == dep[j][1])) {
				platform++;
				i++;
				result = Math.max(result, platform);
			} else {
				platform--;
				j++;
			}
		}
		return result;
	}

	// Approach2: Using TreeMap
	public static int minPlatformRequired2(int[] arrv, int[] dep) {
		TreeMap<Integer, Integer> map = new TreeMap<>();
		for (int i = 0; i < arrv.length; i++) {
			map.put(arrv[i], map.getOrDefault(arrv[i], 0) + 1);
			map.put(dep[i], map.getOrDefault(dep[i], 0) - 1);
		}
		int noOfPlatform = 0, count = 0;
		for (int key : map.keySet()) {
			count += map.get(key);
			noOfPlatform = Math.max(noOfPlatform, count);
		}
		return noOfPlatform;
	}

	//TODO: Conflict Appointments

	/******************** Interval Patterns - Interval Manipulations ***********************/

	/* Insert Interval:
	 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary). You may
	 * assume that the intervals were initially sorted according to their start times.
	 * Example: Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
	 *          Output: [[1,2],[3,10],[12,16]] Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
	 */
	// Approach1:
	public List<Interval> insert1(List<Interval> intervals, Interval newInterval) {
		List<Interval> result = new ArrayList<>();
		for (Interval interval : intervals) {
			if (interval.end < newInterval.start) {
				result.add(interval);
			} else if (interval.start > newInterval.end) {
				result.add(newInterval);
				newInterval = interval;
			} else if (interval.start <= newInterval.end || interval.end >= newInterval.start) {
				newInterval = new Interval(Math.min(interval.start, newInterval.start),
						Math.max(interval.end, newInterval.end));
			}
		}
		result.add(newInterval);
		return result;
	}

	// Approach2: Simplified Code:In place solution
	public List<Interval> insert2(List<Interval> intervals, Interval newInterval) {
		int i = 0;
		// Linear Search to find the starting pos
		while (i < intervals.size() && intervals.get(i).end < newInterval.start) i++;
		// or
		// Binary Search is used to find the starting position to insert the interval
		int startingIndex = binarySearch(intervals, newInterval);
		i = startingIndex;

		while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
			newInterval = new Interval(Math.min(intervals.get(i).start, newInterval.start),
					Math.max(intervals.get(i).end, newInterval.end));
			intervals.remove(i);
		}
		intervals.add(i, newInterval);
		return intervals;
	}

	// Approach3: Similar to Appraoch1; Binary Search:The best time is O(log(n)) and worst case time is O(n).
	public List<Interval> insert3(List<Interval> intervals, Interval newInterval) {
		List<Interval> result = new ArrayList<>();

		if (intervals.size() == 0) {
			result.add(newInterval);
			return result;
		}
		// Binary Search is used to find the starting position to insert the interval
		int startingIndex = binarySearch(intervals, newInterval);
		// Add all the intervals before the startinIndex in the result list
		result.addAll(intervals.subList(0, startingIndex));

		for (int i = startingIndex; i < intervals.size(); i++) {
			Interval interval = intervals.get(i);
			if (interval.end < newInterval.start) {
				result.add(interval);
			} else if (interval.start > newInterval.end) {
				result.add(newInterval);
				newInterval = interval;
			} else if (interval.end >= newInterval.start || interval.start <= newInterval.end) {
				newInterval = new Interval(Math.min(interval.start, newInterval.start),
						Math.max(newInterval.end, interval.end));
			}
		}

		result.add(newInterval);

		return result;
	}

	public int binarySearch(List<Interval> intervals, Interval newInterval) {
		int low = 0;
		int high = intervals.size() - 1;

		while (low < high) {
			int mid = low + (high - low) / 2;

			if (newInterval.start <= intervals.get(mid).start) {
				high = mid;
			} else {
				low = mid + 1;
			}
		}

		return high == 0 ? 0 : high - 1;
	}

	/*
	 * Merge Intervals:
	 * Given a collection of intervals, merge all overlapping intervals.
	 * Example 1: Input: [[1,3],[2,6],[8,10],[15,18]]; Output: [[1,6],[8,10],[15,18]]
	 */
	public List<Interval> merge(List<Interval> intervals) {
		if (intervals.size() <= 1) return intervals;
		Collections.sort(intervals, Comparator.comparing(i -> i.start));
		// Merge the overlapping intervals
		List<Interval> result = new ArrayList<>();
		Interval prevInterval = intervals.get(0);
		for (int i = 1; i < intervals.size(); i++) {
			Interval currInterval = intervals.get(i);
			if (prevInterval.end >= currInterval.start) {
				prevInterval.end = Math.max(currInterval.end, prevInterval.end);
			} else {
				result.add(prevInterval);
				prevInterval = currInterval;
			}
		}
		result.add(prevInterval);
		return result;
	}

	/*
	 * Non-overlapping Intervals
	 * Given a collection of intervals, find the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.
	 * Input: [ [1,2], [2,3], [3,4], [1,3] ]	Output: 1
	 * Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
	 */
	public int eraseOverlapIntervals(Interval[] intervals) {
		Arrays.sort(intervals, Comparator.comparingInt(i -> i.end));
		int max = 0, prevEnd = Integer.MIN_VALUE;
		for (Interval in : intervals) {
			if (prevEnd <= in.start) {
				prevEnd = in.end;
				max++;
			}
		}
		return intervals.length - max;
	}

}