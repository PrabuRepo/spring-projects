package com.problems.patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

import com.common.model.Interval;

/*
 * Reason for sorting finishing time:
 * https://www.freecodecamp.org/news/what-is-a-greedy-algorithm/
 * 
 * Sort based on start time:
 * 	- To find the overlapping intervals, insert the intervals, merge overlapping intervals
 * 
 * Sort based on end time:
 * 	- To find the maximum number of non-overlapping intervals. Eg: max activities, no of meeting rooms required,  
 */
public class IntervalPatterns {
	/********************* Interval Patterns - Pattern I **************************/

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
		int prev = 0, curr = 1;
		System.out.print(intervals[prev].order + " ");
		while (curr < n) { // Starting Index
			if (intervals[prev].end <= intervals[curr].start) {
				System.out.print(intervals[curr].order + " ");
				prev = curr;
			}
			curr++;
		}
	}

	/* Meeting Rooms I: 
	 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...]
	 * (si<ei), determine if a person could attend all meetings. For example, Given [[0, 30],[5, 10],[15, 20]], return
	 * false.
	 */
	public boolean canAttendAllMeetings(Interval[] intervals) {
		if (intervals.length <= 1) return true;

		//This problem is basically to find the overlapping b/w the interval
		//So it can be sorted based on both start & finish time
		//Arrays.sort(intervals, (ob1, ob2) -> ob1.start - ob2.start);
		//or 
		Arrays.sort(intervals, (ob1, ob2) -> ob1.end - ob2.end);

		for (int i = 0; i < intervals.length - 1; i++) {
			if (intervals[i].end > intervals[i + 1].start) return false;
		}
		return true;
	}

	/*
	 * Non-overlapping Intervals
	 * Given a collection of intervals, find the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.
	 * Input: [ [1,2], [2,3], [3,4], [1,3] ]	Output: 1
	 * Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
	 */
	//NoteL This solution is opposite of "Activity Selection Problem"
	public int eraseOverlapIntervals(int[][] intervals) {
		if (intervals.length <= 1) return 0;

		//0th Index-Starting point, 1st index-Ending point
		//Sort the input intervals based on ending point 
		Arrays.sort(intervals, (ob1, ob2) -> ob1[1] - ob2[1]);

		//Count the overlapping intervals
		int prev = 0, curr = 1, overlapCount = 0;

		while (curr < intervals.length) {
			if (intervals[prev][1] <= intervals[curr][0]) {
				prev = curr;
			} else {
				overlapCount++;
			}
			curr++;
		}

		return overlapCount;
	}

	/* Insert Interval:
	 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary). You may
	 * assume that the intervals were initially sorted according to their start times.
	 * Example: Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
	 *          Output: [[1,2],[3,10],[12,16]] Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
	 */
	//Using List
	public void insertIntervals1(List<Interval> intervals, Interval newInterval) {
		insert1(intervals, newInterval);
		insert2(intervals, newInterval);
		insert3(intervals, newInterval);
	}

	//Using 2D Array
	public void insertIntervals2(int[][] intervals, int[] newInterval) {
		insert(intervals, newInterval);
	}

	/*
	 * Merge Intervals:
	 * Given a collection of intervals, merge all overlapping intervals.
	 * Example 1: Input: [[1,3],[2,6],[8,10],[15,18]]; Output: [[1,6],[8,10],[15,18]]
	 */
	// intverals list
	public List<Interval> merge(List<Interval> intervals) {
		if (intervals.size() <= 1) return intervals;
		Collections.sort(intervals, Comparator.comparing(i -> i.start));
		//Collections.sort(intervals, (a, b) -> (a.start - b.start));

		// Merge the overlapping intervals
		List<Interval> result = new ArrayList<>();
		Interval prev = intervals.get(0);
		for (int i = 1; i < intervals.size(); i++) {
			Interval curr = intervals.get(i);
			if (prev.end >= curr.start) {
				prev.end = Math.max(curr.end, prev.end);
			} else {
				result.add(prev);
				prev = curr;
			}
		}
		result.add(prev);
		return result;
	}

	//intervals array
	public int[][] merge(int[][] intervals) {
		if (intervals.length <= 1) return intervals;

		Arrays.sort(intervals, (a, b) -> (a[0] - b[0]));

		int[] prev = intervals[0];
		List<int[]> result = new ArrayList<>();
		for (int i = 1; i < intervals.length; i++) {
			if (prev[1] >= intervals[i][0]) {
				prev[0] = Math.min(prev[0], intervals[i][0]);
				prev[1] = Math.max(prev[1], intervals[i][1]);
			} else {
				result.add(prev);
				prev = intervals[i];
			}
		}

		result.add(prev);

		return result.toArray(new int[result.size()][]);
		//return convertToArray(result);
	}

	/******************** Interval Patterns - Pattern II ***********************/
	/*
	 * All the below problems are mostly identifying the overlap in the points.
	 * Overlapping points = No of meeting rooms required/No of platforms required
	 */

	/* Meeting Rooms II:
	 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] find the minimum
	 * number of conference rooms required.
	 * Time: O(nlogn) for the below solutions 
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
		for (int i = 1; i < intervals.length; i++) {
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

	//======================Util or Common Methods==========================

	//Approach1:
	public List<Interval> insert1(List<Interval> intervals, Interval newInterval) {
		//Note: Here consider intervals as prev interval and newInterval as curr; 
		List<Interval> result = new LinkedList<>();
		int i = 0, n = intervals.size();

		//Sort based on start time, if input is not sorted
		Collections.sort(intervals, (a, b) -> (a.start - b.start));

		//1.Add all intervals before newInterval start point; prev.end<curr.start
		while (i < n && intervals.get(i).end < newInterval.start) {
			result.add(intervals.get(i++));
		}

		//2.merge overlapping intervals; prev.start<=curr.end
		while (i < n && intervals.get(i).start <= newInterval.end) {
			newInterval = new Interval( // we could mutate newInterval here also
					Math.min(newInterval.start, intervals.get(i).start),
					Math.max(newInterval.end, intervals.get(i).end));
			i++;
		}
		result.add(newInterval);

		//3.Add remaining intervals, after newInterval;
		while (i < n) {
			result.add(intervals.get(i++));
		}
		return result;
	}

	// Approach2:
	//Here curr Interval is new Interval going add in the list, intervals list act as prev Interval  
	public List<Interval> insert2(List<Interval> intervals, Interval curr) {
		List<Interval> result = new ArrayList<>();
		//Sort based on start time, if input is not sorted
		Collections.sort(intervals, (a, b) -> (a.start - b.start));

		for (Interval prev : intervals) {
			if (prev.end < curr.start) {
				result.add(prev);
			} else if (prev.start > curr.end) {
				result.add(curr);
				curr = prev;
			} else if (prev.start <= curr.end || prev.end >= curr.start) { //or just else
				curr = new Interval(Math.min(prev.start, curr.start), Math.max(prev.end, curr.end));
			}
		}
		result.add(curr);
		return result;
	}

	// Approach2: Simplified Code:In place solution
	// Time is O(n^2), if list is ArrayList. Time is O(n), if list is LinkedList  
	public List<Interval> insert3(List<Interval> intervals, Interval newInterval) {
		//Sort based on start time, if input is not sorted
		Collections.sort(intervals, (a, b) -> (a.start - b.start));

		int i = 0, n = intervals.size();
		// Linear Search to find the starting pos
		while (i < n && intervals.get(i).end < newInterval.start) i++;
		// or
		// Binary Search is used to find the starting position to insert the interval
		i = binarySearch(intervals, newInterval);

		//Time is O(n^2), if list is ArrayList. Time is O(n), if list is LinkedList  
		while (i < n && intervals.get(i).start <= newInterval.end) {
			newInterval = new Interval(Math.min(intervals.get(i).start, newInterval.start),
					Math.max(intervals.get(i).end, newInterval.end));
			intervals.remove(i);
		}

		//Time is O(n^2), if list is ArrayList. Time is O(n), if list is LinkedList  
		intervals.add(i, newInterval);
		return intervals;
	}

	public int[][] insert(int[][] intervals, int[] newInterval) {
		//Note: Here consider intervals as prev interval and newInterval as curr; 
		List<int[]> result = new ArrayList<>();
		int i = 0, n = intervals.length;
		//Sort based on start time, if input is not sorted
		Arrays.sort(intervals, (a, b) -> (a[0] - b[0]));

		//1.Add all intervals before newInterval start point; prev.end<curr.start
		while (i < n && intervals[i][1] < newInterval[0]) {
			result.add(intervals[i++]);
		}

		//2.merge overlapping intervals; prev.start<=curr.end
		while (i < n && intervals[i][0] <= newInterval[1]) {
			newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
			newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
			i++;
		}
		result.add(newInterval);

		//3.Add remaining intervals, after newInterval; 
		while (i < n) {
			result.add(intervals[i++]);
		}

		return result.toArray(new int[result.size()][]);
	}

	//Applied "Search Insert Position" problem's solution. 
	// Here target is newInterval's end time and consider start time of intervals as input 
	public int binarySearch(List<Interval> intervals, Interval newInterval) {
		int l = 0, h = intervals.size() - 1, m = 0;

		int target = newInterval.end;
		while (l <= h) {
			m = l + (h - l) / 2;
			if (target == intervals.get(m).start) return m;
			else if (target < intervals.get(m).start) h = m - 1;
			else l = m + 1;
		}

		return l;
	}

	//TODO: Test previous solution and delete this
	public int binarySearch2(List<Interval> intervals, Interval newInterval) {
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

}