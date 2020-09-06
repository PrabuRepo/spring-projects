package com.basic.datastructures.adv;

import java.util.Arrays;

import com.basic.datastructures.operations.BITOperations;

/*
 * Implement fenwick or binary indexed tree:
 * A Fenwick tree or binary indexed tree is a data structure providing efficient methods
 * for calculation and manipulation of the prefix sums of a table of values.
 * 
 * Space complexity for fenwick tree is O(n)
 * Time complexity to create fenwick tree is O(nlogn)
 * Time complexity to update value is O(logn)
 * Time complexity to get sum of range is O(logn)
 */
public class BinaryIndexedTree implements BITOperations {
	public static void main(String[] args) {
		// int[] arr = { 2, 1, 7, 3, 2, 3, 5, 1 };
		int[] arr = { 2, 1, 1, 3, 2, 3, 4, 5, 6, 7, 8, 9, 6, 7, 8, 9 };
		BinaryIndexedTree ob = new BinaryIndexedTree(arr);

		System.out.println("Binary Indexed Tree: " + Arrays.toString(ob.biTree));
		System.out.println("Range sum: " + ob.sumRange(7, 11));

		System.out.println("Update: ");
		ob.update(2, 5);
		System.out.println("Binary Indexed Tree: " + Arrays.toString(ob.biTree));
		System.out.println("Range sum: " + ob.sumRange(1, 4));
	}

	int[] input;
	int[] biTree;

	public BinaryIndexedTree(int[] nums) {
		int n = nums.length;
		input = nums;
		biTree = new int[n + 1];
	}

	//Cascading sum approach: Time: O(n)
	@Override
	public void buildBIT(int[] nums) {
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
	public void update(int i, int val) {
		int oldVal = input[i];
		input[i] = val;
		val -= oldVal;
		i += 1;

		while (i < biTree.length) {
			biTree[i] += val;
			// System.out.print(i + ", ");
			//System.out.print(Integer.toBinaryString(i & -i) + ", ");
			i += lsb(i);
		}

	}

	@Override
	public int sumRange(int i, int j) {
		int left = prefixSum(i);
		int right = prefixSum(j + 1);
		return right - left;
	}

	private int lsb(int i) {
		return (i & -i);
	}

	private int prefixSum(int i) {
		int sum = 0;
		while (i > 0) {
			sum += biTree[i];
			i -= lsb(i); // This formula removes the last set bit from i. Eg: i=12, parent(i) = 8
		}
		return sum;
	}
}

//Implementation using sligtly different approach for build BIT
class BinaryIndexedTree2 implements BITOperations {
	int[] input;
	int[] biTree;

	public BinaryIndexedTree2(int[] nums) {
		input = nums;
		biTree = new int[nums.length + 1];
	}

	//Time  Complexity: O(nlogn) 
	@Override
	public void buildBIT(int[] nums) {
		for (int i = 0; i < nums.length; i++)
			insert(i, nums[i]);
	}

	/*
	 * Keep adding the value in "all the same level and next parent" index; 
	 * Eg: for 5 -> 5,6,8,16.. Here 5 & 6 are same level parent, 8,16 are next parent
	 * Find the child node of the index: Bitwise 'and' with 2's complement of index and finally add with given index  
	 * Note: 0 is Parent/Root Node
	 */
	private void insert(int i, int val) {
		i += 1;
		while (i < biTree.length) {
			biTree[i] += val;
			i += (i & -i);
		}
	}

	@Override
	public void update(int i, int val) {
		int oldVal = input[i];
		input[i] = val;
		insert(i, val - oldVal);
	}

	@Override
	public int sumRange(int i, int j) {
		int left = sum(i);
		int right = sum(j + 1);
		return right - left;
	}

	/*
	* Keep adding the value in from given index to parent index  
	* Eg: for 7 -> 7,6,4;  Here index flow will be 7 -> 6 -> 4
	*  
	*  Find the parent node of index: Bitwise 'and' with 2's complement of index and finally subtract with given index  
	*  Note: 0 is Parent/Root Node
	*/
	public int sum(int i) {
		int sum = 0;
		while (i > 0) {
			sum += biTree[i];
			i -= (i & -i); // This formula removes the last set bit from i. Eg: i=12, parent(i) = 8
		}
		return sum;
	}
}
