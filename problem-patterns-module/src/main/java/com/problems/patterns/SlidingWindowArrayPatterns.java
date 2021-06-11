package com.problems.patterns;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;

import com.problems.patterns.ds.HeapPatterns;
import com.problems.patterns.ds.QueuePatterns;

/*
 * Sliding Window Array Patterns solves subarray problems.
 * Window Sliding Technique: 
 *    This technique shows how a nested for loop in few problems can be converted to single for loop and hence reducing the time complexity.
 */
public class SlidingWindowArrayPatterns {

	// This method just consolidates all the subarray problems
	public void subarrayConsolidation() {
		int[] arr = { 2, 5, 6, -1, -3, 6, 8, -4, 7 };
		int k = 12;

		//Subarray calculation for given window:
		int window = 4;
		subarraySumInWindowK(arr, window);
		subarrayAvgMaxInWindowK(arr, window);
		countDistinctInWindowK(arr, window);

		//Subarray Sum: 
		subarraySumMax(arr);
		subarraySumMaxRange(arr);

		subarrayProductMax(arr);

		subarraySumK(arr, k);
		subarraySumKCount(arr, k);
		subarraySumKMaxLen(arr, k);
		subarraySumKMinLen1(arr, k);
		subarraySumKMinLen2(arr, k);

		subarraySumLenCloseToK(arr, k);
		subarraySumCloseToK21(arr, k);
		subarraySumCloseToK22(arr, k);

		subarraySumZero1(arr);
		subarraySumZero2(arr);
		subarraySumZeroCount(arr);

		//Subarray - Count consecutive ones
		subarrayMaxConsecutiveOnes(arr);
		subarrayMaxConsecutiveOnesII1(arr);
		subarrayMaxConsecutiveOnesII2(arr);
	}

	/***************** 1.Fixed Window: Calculate value for each fixed window ***********************/
	/*
	 * This pattern solves problems for the given input array and fixed window size. We have to perform action 
	 * on this given window size depends on problem such as add, distinct no, avg etc.
	 * Note:
	 * 	To remove or access k index before, use this "i-k+1"
	 */

	/* Sliding Window Motivation Problem: To understand the Sliding Window Concepts
	 * Find the sum of subarrays of size k in a given array
	 * Solution:
	 *   BruteForce Algorithm: O(nk)
	 *   Sliding Window Algorithm: O(n)
	 */
	public int[] subarraySumInWindowK(int[] arr, int k) {
		int sum = 0, n = arr.length;
		int[] result = new int[n - k + 1];
		for (int i = 0; i < n; i++) {
			sum += arr[i];

			if (i >= k - 1) {
				result[i - k + 1] = sum; // or initialize index=0 and increment->result[index++]
				sum -= arr[i - k + 1];
			}
		}
		return result;
	}

	/* Maximum Average Subarray I, II/Maximum average subarray of size k:
	 * Given an array consisting of n integers, find the contiguous subarray of given length k that has the maximum
	 * average value. And you need to output the maximum average value. 
	 * Example 1: Input: [1,12,-5,-6,50,3], k = 4 Output: 12.75 Explanation: Maximum average is (12-5-6+50)/4 = 51/4 = 12.75
	 */
	public double subarrayAvgMaxInWindowK(int[] arr, int k) {
		if (arr.length == 0 || arr.length < k) return 0;

		int sum = 0;
		double maxAvg = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];

			if (i >= k - 1) {
				maxAvg = Math.max(maxAvg, (double) sum / k);
				sum -= arr[i - k + 1];
			}
		}

		return maxAvg;
	}

	/*Count distinct elements in every window:
	 * Given an array A[] of size N and an integer K. Your task is to complete the function countDistinct() which prints
	 * the count of distinct numbers in all windows of size k in the array A[].
	 */
	public int[] countDistinctInWindowK(int A[], int k) {
		Map<Integer, Integer> map = new HashMap<>(); // Number, count
		int n = A.length;
		int[] result = new int[n - k + 1];
		for (int i = 0; i < n; i++) {
			map.put(A[i], map.getOrDefault(A[i], 0) + 1);

			if (i >= k - 1) {
				result[i - k + 1] = map.size();
				int count = map.get(A[i - k + 1]);
				if (count == 1) map.remove(A[i - k + 1]);
				else map.put(A[i - k + 1], --count);
			}
		}
		return result;
	}

	// Few more sliding window pattern problems
	QueuePatterns queuePatterns = new QueuePatterns();
	HeapPatterns heapPatterns = new HeapPatterns();

	public void slidingWindow(int[] nums, int k) {
		queuePatterns.subarrayAvgMaxInStream1();
		queuePatterns.subarrayAvgMaxInStream2();
		queuePatterns.maxSlidingWindow(nums, k);
		queuePatterns.maxMin(k, nums);
		heapPatterns.medianSlidingWindow1(nums, k);
		heapPatterns.medianSlidingWindow2(nums, k);
	}

	/************* 2.Varying Window Size: Move from left to right till reach the target *************/
	/* Pattern Understanding:
	 * This pattern problems do not have fixed window/range size. Left and Right pointers varies based on the
	 * given problem.
	 */

	/* Maximum Subarray - Kadane�s Algorithm:
	 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest
	 * sum and return its sum. 
	 * Example: Input: [-2,1,-3,4,-1,2,1,-5,4], Output: 6 Explanation: [4,-1,2,1] has the largest sum = 6.
	 */
	public int subarraySumMax(int[] nums) {
		if (nums.length == 0) return 0;

		int sum = 0, maxSum = Integer.MIN_VALUE;
		for (int num : nums) {
			/*sum += num;
			maxSum = Math.max(sum, maxSum);
			if (sum < 0) sum = 0;*/
			//or
			sum = Math.max(sum + num, num);
			maxSum = Math.max(sum, maxSum);
		}
		return maxSum;
	}

	//Use Kadane's Algorithm to find max sum subarray
	public int[] subarraySumMaxRange(int[] a) {
		int max = Integer.MIN_VALUE, sum = 0, startIndex = 0, start = 0, end = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
			if (sum > max) {
				max = sum;
				start = startIndex;
				end = i;
			}

			if (sum < 0) {
				sum = 0;
				startIndex = i + 1;
			}
		}
		System.out.println("Starting Index: " + start + "; Ending Index: " + end);
		return Arrays.copyOfRange(a, start, end + 1);
	}

	/* Maximum Product Subarray - Kadane�s Algorithm:
	 * Given an integer array nums, find the contiguous subarray within an array (containing at least one number) which
	 * has the largest product. Example 1: Input: [2,3,-2,4] Output: 6 Explanation: [2,3] has the largest product 6.
	 */
	public int subarrayProductMax(int[] nums) {
		if (nums.length == 0) return 0;
		int maxProd = 1, minProd = 1, result = Integer.MIN_VALUE;
		for (int num : nums) {
			int tempMax = maxProd;
			maxProd = max(maxProd * num, minProd * num, num);
			minProd = min(tempMax * num, minProd * num, num);
			result = Math.max(result, maxProd);
		}
		return result;
	}

	private int max(int d1, int d2, int d3) {
		return Math.max(Math.max(d1, d2), d3);
	}

	private int min(int d1, int d2, int d3) {
		return Math.min(Math.min(d1, d2), d3);
	}

	/* Subarray with given sum:Given an unsorted array A of size N of non-negative integers, find a continuous sub-array 
	 * which adds to a given number.
	 * Note: It works only for "Positive" numbers
	 */
	public boolean subarraySumK(int[] arr, int k) {
		int sum = 0, l = 0, r = 0;
		while (r < arr.length) {
			sum += arr[r];
			while (sum >= k && l <= r) {
				if (sum == k) {
					System.out.println("Subarray Range: " + l + " to " + r);
					return true;
				}
				sum -= arr[l];
				l++;
			}
			r++;
		}
		return false;
	}

	/* Minimum Size Subarray Sum:
	 * Given an array of n positive integers and a positive integer k, find the minimal length of a contiguous subarray
	 * of which the sum >= k. If there isn't one, return 0 instead. Example: Input: k = 7, nums = [2,3,1,2,4,3] Output:
	 * 2 Explanation: the subarray [4,3] has the minimal length under the problem constraint.
	 */
	// Approach1: Using Sliding Window -> This works only array has positive elements
	public int subarraySumKMinLen1(int[] nums, int k) {
		if (nums.length == 0) return 0;

		int l = 0, r = 0, sum = 0, minLen = Integer.MAX_VALUE;
		while (r < nums.length) {
			sum += nums[r];
			while (sum >= k && l <= r) {
				if (sum == k) {
					minLen = Math.min(minLen, r - l + 1);
				}
				sum -= nums[l];
				l++;
			}
			r++;
		}
		return minLen == Integer.MAX_VALUE ? 0 : minLen;
	}

	/*
	 * Longest Subarray having sum of elements atmost �k�:
	 * Given an array, find the maximum sum of subarray close to k but not larger than k
	 */
	//TODO: Test this solution & move to closest pattterns 
	// Approach1: Using Sliding Window -> This works only array has positive elements
	public int subarraySumLenCloseToK(int[] nums, int k) {
		if (nums.length == 0) return 0;

		int l = 0, r = 0, sum = 0;
		int maxLen = Integer.MIN_VALUE, prevSum = -1;
		while (r < nums.length) {
			sum += nums[r];
			while (sum > k) {
				sum -= nums[l];
				l++;
			}
			//Record and Move on: To find atmost 'k'
			if (prevSum == -1 || sum - k < prevSum - k) {
				prevSum = sum;
				maxLen = Math.max(maxLen, r - l + 1);
			}
			r++;
		}
		return maxLen == Integer.MIN_VALUE ? 0 : maxLen;
	}

	/* 
	 * Max Consecutive one I:
	 * Given a binary array, find the maximum number of consecutive 1s in this array. Example 1: Input: [1,1,0,1,1,1]
	 * Output: 3
	 */
	public int subarrayMaxConsecutiveOnes(int[] nums) {
		int count = 0, maxCount = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == 1) {
				count++;
			} else {
				maxCount = Math.max(maxCount, count);
				count = 0;
			}
		}

		return Math.max(maxCount, count);
	}

	/* Max Consecutive one II & III:
	 * Given a binary array, find the maximum number of consecutive 1s in this array if you can flip at most one 0.
	 * Example 1: Input: [1,0,1,1,0] Output: 4 Explanation: Flip the first zero will get the the maximum number of
	 * consecutive 1s. After flipping, the maximum number of consecutive 1s is 4.
	 */
	public int subarrayMaxConsecutiveOnesII1(int[] nums) {
		int max = 0, zero = 0, k = 1; // flip at most k zero
		int l = 0, r = 0;
		while (r < nums.length) {
			if (nums[r] == 0) zero++;

			while (zero > k) {
				if (nums[l] == 0) zero--;
				l++;
			}

			max = Math.max(max, r - l + 1);
			r++;
		}
		return max;
	}

	/* Follow up: What if the input numbers come in one by one as an infinite stream? In other words, you can't store
	 * all numbers coming from the stream as it's too large to hold in memory. Could you solve it efficiently?
	 */
	public int subarrayMaxConsecutiveOnesII2(int[] nums) {
		int max = 0, k = 1; // flip at most k zero
		Queue<Integer> zeroIndex = new LinkedList<>();
		for (int l = 0, r = 0; r < nums.length; r++) {
			if (nums[r] == 0) zeroIndex.offer(r);
			if (zeroIndex.size() > k) {
				l = zeroIndex.poll() + 1;
			}
			max = Math.max(max, r - l + 1);
		}
		return max;
	}

	/*************** 2.Varying Window Size: Use HashMap+PrefixSum to solve problems ************/

	/* Pattern Understanding:
	 * This pattern problems do not have fixed window/range size. Left and Right pointers varies based on the
	 * given problem. This pattern mostly used to solve the problem which has both "+ve and -ve" numbers in 
	 * the input.This pattern problem uses Hashmap where Key stores sum and Value stores Index or 
	 * frequency(depends on the problem)
	 * 
	 * Prefix Sum Patterns Logic: If we consider all prefix sums, we can notice that there is a subarray 
	 * with 0 sum when : 1) Either a prefix sum repeats or 2) prefix sum becomes 0.
	 * 	 * Example:
	 * 	Arr: {-1,-1,2,1,-4,2,3,-1,2} 
	 * 	Prefix Sum: {-1,-2,0,1,-3,-1,2,1,3}; 
	 * 	Here zero sum subarray is
	 * 		1.when sum=0; (Sum from 0th Index to currIndex)
	 * 		2.same number repeats (Sum from lastIndex+1 to currIndex)
	 */

	/*
	 * Zero Sum Subarray: Input array has both +ve and -ve numbers
	 */
	public int[] subarraySumZero1(int[] arr) {
		//Hashmap: Key: sum; Val: index
		Map<Integer, Integer> map = new HashMap<>();
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
			if (sum == 0) {
				// Sum range from next index of sum to curr index
				return Arrays.copyOfRange(arr, 0, i + 1);
			} else if (map.containsKey(sum)) {
				int oldIndex = map.get(sum);
				// Sum range from next index of sum to curr index
				return Arrays.copyOfRange(arr, oldIndex + 1, i + 1);
			}

			map.put(sum, i);
		}
		return null;
	}

	//Both are same with small changes; Here Map initializes with (0,-1)
	public int[] subarraySumZero2(int[] arr) {
		Map<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
			if (map.containsKey(sum)) {
				int oldIndex = map.get(sum);
				// Sum range from next index of sum to curr index
				return Arrays.copyOfRange(arr, oldIndex + 1, i + 1);
			}

			map.put(sum, i);
		}
		return null;
	}

	/*
	 * Zero Sum Subarrays: - Count no zero sum sub arrays
	 * You are given an array A of size N. You need to print the total count of sub-arrays having their sum equal to 0
	 */
	public int subarraySumZeroCount(int[] a) {
		HashMap<Integer, Integer> map = new HashMap<>();
		//the initial entry map.put(0, 1) can be exchanged with statement: if (sum == k) count++; inside for loop
		map.put(0, 1);
		int count = 0, sum = 0, n = a.length;
		for (int i = 0; i < n; i++) {
			sum += a[i];
			if (map.containsKey(sum)) {
				count += map.get(sum);
			}

			map.put(sum, map.getOrDefault(sum, 0) + 1);
		}
		return count;
	}

	/* Subarray Sum Equals K:
	 * Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum
	 * equals to k. Example 1: Input:nums = [1,1,1], k = 2 Output: 2
	 */
	/*
	 * Why this solution works?
	 The idea behind this approach is as follows: If the cumulative sum(represented by sum[i] for sum up to ith index) up to two 
	 indices is the same, the sum of the elements lying in between those indices is zero. 
	 Extending the same thought further, if the cumulative sum up to two indices, say i and j is at a difference of k
	 i.e. if sum[i] - sum[j] = k the sum of elements lying between indices i and j is k.
	 Explanation:
	1. Hashmap<sum[0,i - 1], frequency>
	2. sum[i, j] = sum[0, j] - sum[0, i - 1]    --> sum[0, i - 1] = sum[0, j] - sum[i, j]
	     k           sum      hashmap-key     -->  hashmap-key  =  sum - k
	3. now, we have k and sum.  
	    As long as we can find a sum[0, i - 1], we then get a valid subarray
	   which is as long as we have the hashmap-key,  we then get a valid subarray
	4. Why don't map.put(sum[0, i - 1], 1) every time ?
	    if all numbers are positive, this is fine
	    if there exists negative number, there could be preSum frequency > 1
	*/
	public int subarraySumKCount(int[] nums, int k) {
		int n = nums.length, count = 0, sum = 0;
		//Hashmap: Key: sum[0,i - 1]; Val: frequency
		Map<Integer, Integer> map = new HashMap<>();
		//the initial entry map.put(0, 1) can be exchanged with statement: if (sum == k) count++; inside for loop
		map.put(0, 1);
		for (int i = 0; i < n; i++) {
			sum += nums[i];
			if (map.containsKey(sum - k)) {
				count += map.get(sum - k);
			}

			map.put(sum, map.getOrDefault(sum, 0) + 1);

			//or
			/*
			 map.put(k, 1); //Initalize 'k' as 1, instead of 0. 
			 if (map.containsKey(sum)) {
				count += map.get(sum);
			}
			map.put(sum + k, map.getOrDefault(sum + k, 0) + 1);*/
		}
		return count;
	}

	/*
	 * Maximum Size Subarray Sum Equals k/Largest subarray with 0 sum:
	 * Given an array nums and a target value k, find the maximum length of a subarray that sums to k. If there isn't one, return 0 instead.
	 */
	public int subarraySumKMaxLen(int[] nums, int k) {
		int maxLen = 0, sum = 0;
		//Hashmap: Key: sum[0,i - 1]; Val: index
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, -1); //Use this one or below commented -> if (sum == k) 
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
			//if (sum == k) maxLen = Math.maxLen(maxLen, i + 1);
			if (map.containsKey(sum - k)) {
				maxLen = Math.max(maxLen, i - map.get(sum - k));
			}
			//This condition is not required for normal scenario, but here it needs to find the maxLen size subarray
			if (!map.containsKey(sum)) {
				map.put(sum, i);
			}
		}
		return maxLen;
	}

	// Find Minimum Length Sub Array With Sum K
	public int subarraySumKMinLen2(int[] nums, int k) {
		int minLen = Integer.MAX_VALUE, sum = 0;
		//Hashmap: Key: sum[0,i - 1]; Val: index
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, -1); //Use this one or below commented -> if (sum == k) 
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
			//if (sum == k) minLen = Math.minLen(minLen, i + 1);
			if (map.containsKey(sum - k)) {
				minLen = Math.min(minLen, i - map.get(sum - k));
			}
			map.put(sum, i);
		}
		return minLen;
	}

	//TODO: Move this to closest number patterns
	/* Maximum Sum of Subarray Close to K:
	 * Given an array, find the maximum sum of subarray close to k but not larger than k
	 */
	// Approach2: Using Prefix Sum approach - Works for both +ve and -ve numbers
	//Time Complexity: O(nlogn); Tree Datastructure takes o(logn) time for add() and ceiling() methods
	public int subarraySumCloseToK21(int[] arr, int k) {
		int sum = 0;
		TreeSet<Integer> set = new TreeSet<Integer>();
		int closestSum = Integer.MIN_VALUE;
		set.add(0);

		for (int i = 0; i < arr.length; i++) {
			sum = sum + arr[i];

			Integer ceiling = set.ceiling(sum - k);
			if (ceiling != null) {
				closestSum = Math.max(closestSum, sum - ceiling);
			}

			set.add(sum);
		}

		return closestSum;
	}

	/* Maximum Sum of Subarray Close to K:
	 * Given an array, find the maximum sum of subarray close to k but not larger than k
	 */
	public int subarraySumCloseToK22(int[] arr, int k) {
		int sum = 0, closestSum = Integer.MIN_VALUE;
		//Here TreeSet datastructure used instead of Map to find the nearest sum k
		TreeSet<Integer> set = new TreeSet<Integer>();
		set.add(0);

		for (int i = 0; i < arr.length; i++) {
			sum = sum + arr[i];

			Integer floor = set.floor(sum - k);
			if (floor != null) {
				closestSum = Math.max(closestSum, sum - floor);
			}
			set.add(sum);
		}

		return closestSum;
	}

	public static void main(String[] args) {
		SlidingWindowArrayPatterns ob = new SlidingWindowArrayPatterns();
		int[] arr = { 3, 9, 1, 7, 8, 2 };
		System.out.println(ob.subarraySumK(arr, 1));

		int[] nums1 = { 1, 3, 2, 4, 2, 1 };
		System.out.println("Longest subarray: " + ob.subarraySumLenCloseToK(nums1, 7));
		int[] nums = { 4, -1, 5 };
		System.out.println("Closer to K, but Not larger than K: " + ob.subarraySumCloseToK21(nums, 7));
		System.out.println("Closer to K, but larger than K: " + ob.subarraySumCloseToK22(nums, 7));

		System.out.println(Arrays.toString(ob.subarraySumMaxRange(nums)));
	}
}