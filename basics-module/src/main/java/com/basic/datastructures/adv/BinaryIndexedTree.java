package com.basic.datastructures.adv;

import java.util.Arrays;

import com.basic.datastructures.adv.operations.BITOperations;

/*
 * Implement fenwick or binary indexed tree:
 * A Fenwick tree or binary indexed tree is a data structure providing efficient methods
 * for calculation and manipulation of the prefix sums of a table of values.
 * 
 * Fenwick Tree Motivation:
 * 	 - Fenwick Tree datastructure is used to solve rangesum problem.
 * 	 - Given an array of integer values compute the range sum between index (i,j)
 * 			1. Brute force approach takes O(n) time for finding the range sum and O(1) time to update any index
 * 			2. Prefix Sum Approach take O(1) time for finding the range sum, but O(n) time to update any index
 * 			3. Fenwick tree provides the following,	
 * 				- Space complexity for fenwick tree is O(n)
 * 				- Time complexity to build fenwick tree is O(n)/O(nlogn)
 * 				- Time complexity to update value is O(logn)
 *  			- Time complexity to get sum of range is O(logn)
 *  
 *  Note: Understand Immutable Range sum problem using Prefix Sum approach before studying this. PrefixSumPatterns.java
 *  Refer: https://www.youtube.com/watch?v=RgITNht_f4Q&list=PLDV1Zeh2NRsB6SWUrDFW2RmDotAfPbeHu&index=38
 */
public class BinaryIndexedTree implements BITOperations {
	public static void main(String[] args) {
		int[] arr = { 5, -3, 6, 1, 0, -4, 11, 6, 2, 7 };
		//int[] arr = { 2, 1, 1, 3, 2, 3, 4, 5, 6, 7, 8, 9, 6, 7, 8, 9 };
		BinaryIndexedTree ob = new BinaryIndexedTree(arr);

		System.out.println("Binary Indexed Tree: " + Arrays.toString(ob.biTree));
		System.out.println("Range sum: " + ob.sumRange(2, 5));

		System.out.println("Update: ");
		ob.update(2, 5);
		System.out.println("Binary Indexed Tree: " + Arrays.toString(ob.biTree));
		System.out.println("Range sum: " + ob.sumRange(8, 9));
	}

	int[] input;
	int[] biTree;

	public BinaryIndexedTree(int[] nums) {
		int n = nums.length;
		input = nums;
		biTree = new int[n + 1];

		//Using point update method: Time: O(nlogn)
		//buildFenwickTree1(nums);

		//Cascading sum approach: Time: O(n)
		buildFenwickTree2(nums);

		System.out.println("Print Constructed BIT : ");
		Arrays.stream(biTree).forEach(val -> System.out.print(val + " "));
		System.out.println();
	}

	@Override
	public void buildFenwickTree1(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			// "i+1" Reason: -> BIT uses index from 1 to n
			pointUpdate(i + 1, nums[i]);
		}
	}

	@Override
	public void buildFenwickTree2(int[] nums) {
		//Copy all the elements from nums to biTree array
		for (int i = 0; i < nums.length; i++) {
			biTree[i + 1] = nums[i];
		}

		for (int i = 1; i < biTree.length; i++) {
			int parent = i + lsb(i);
			if (parent < biTree.length) biTree[parent] += biTree[i];
		}
	}

	@Override
	public void update(int i, int newVal) {
		int oldVal = input[i];
		input[i] = newVal;
		// "i+1" Reason: -> BIT uses index from 1 to n
		pointUpdate(i + 1, newVal - oldVal);
	}

	//Move from low to high index range
	private void pointUpdate(int index, int val) {
		while (index < biTree.length) {
			biTree[index] += val;
			index += lsb(index);
		}
	}

	@Override
	public int sumRange(int i, int j) {
		int left = prefixSum(i); //Exclusive Sum: Meaning gives sum from 0 to i-1
		int right = prefixSum(j + 1); //Inclusive Sum: Meaning gives sum from 0 to j
		return right - left;
	}

	//This method returns sum of values of index from i to 0 in nums; but bitree iterate from i to 1;
	private int prefixSum(int i) {
		int sum = 0;
		while (i > 0) {
			sum += biTree[i];
			i -= lsb(i);
		}
		return sum;
	}

	private int lsb(int i) {
		return (i & -i);
	}
}