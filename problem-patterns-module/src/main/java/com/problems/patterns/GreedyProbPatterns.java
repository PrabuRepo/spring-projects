package com.problems.patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GreedyProbPatterns {

	/************************* Type3: Optimal Solution Problem ***********************/
	/* Queue Reconstruction by Height - Sort & Insertion:
	 * Suppose you have a random list of people standing in a queue. Each person is described by a pair of integers (h, k), 
	 * where h is the height of the person and k is the number of people in front of this person who have a height greater 
	 * than or equal to h. Write an algorithm to reconstruct the queue.
	 */
	//Ref: https://leetcode.com/problems/queue-reconstruction-by-height/discuss/672958/Problem-Explanation-or-Detailed-Steps-Solution-or-Simple-or-Using-Sorting
	public int[][] reconstructQueue(int[][] people) {
		// sort array using Arrays.sort with custom comparator
		Arrays.sort(people, (p1, p2) -> p1[0] == p2[0] ? p1[1] - p2[1] : p2[0] - p1[0]);

		List<int[]> list = new ArrayList<>();
		for (int i = 0; i < people.length; i++) {
			list.add(people[i][1], people[i]); // placing people based on the K value
		}

		return list.toArray(people); // convert list to array
	}

	/****************************** Type4: Possibilities ****************************/

	/* Patching Array:
	 * Given a sorted positive integer array nums and an integer n, add/patch elements to the array such that any number
	 * in range [1, n] inclusive can be formed by the sum of some elements in the array. Return the minimum number of 
	 * patches required.
	 * Input: nums = [1,3], n = 6; Output: 1
	 * Input: nums = [1,5,10], n = 20; Output: 2  
	 */

	/* Solution:
	 * 	Sample: {1,3}, n=6 => When you add 2 to arr, will get all the numbers from 1 to 6 in arr {1,2,3}. like [1], [2], [3], [1,3], [2,3], [1,2,3].
	 *  Sample: {1,5,10}, n=20 => When you add 2, 4, will get all the numbers from 1 to 20 in arr {1,2,4,5,10}
	 *  Intuition here is, 
	 *  	1     -> covers from 1 to sum of all nums(1)
	 *      1,2   -> covers from 1 to sum of all nums(3)
	 *      1,2,4 -> covers from 1 to sum of all nums(7)
	 *      1,2,4,8 -> covers from 1 to sum of all nums(15)
	 */
	public int minPatches(int[] nums, int n) {
		int i = 0, count = 0;
		long sum = 1; //Assume initial missing num
		while (sum <= n) {
			//If sum of values from 0 to curr index i >= nums[i], then it covers all the numbers from 1 to sum
			if (i < nums.length && sum >= nums[i]) {
				sum += nums[i++];
			} else {
				sum <<= 1; // sum *- 2 or sum <<= 1;
				count++; //Number of values added or patched 
			}
		}
		return count;
	}

}