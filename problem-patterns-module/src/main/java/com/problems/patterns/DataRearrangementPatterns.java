package com.problems.patterns;

import java.util.Arrays;

import com.common.utilities.Utils;

public class DataRearrangementPatterns {

	/************************* Rearrangement Problems Category ****************/

	/*Sort Transformed Array - Parabola Prob - 2 ptr  approach
	 * Given a sorted array of integers nums and integer values a, b and c. Apply a function of the form f(x) = ax2 +bx + c 
	 * to each element x in the array. The returned array must be in sorted order.
	 */
	public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
		int len = nums.length;
		int[] result = new int[len];

		int l = 0, h = len - 1;
		int index = a >= 0 ? len - 1 : 0;
		while (l <= h) {
			int fl = function(nums[l], a, b, c);
			int fh = function(nums[h], a, b, c);
			if (a >= 0) {
				if (fl >= fh) {
					result[index--] = fl;
					l++;
				} else {
					result[index--] = fh;
					h--;
				}
			} else {
				if (fl <= fh) {
					result[index++] = fl;
					l++;
				} else {
					result[index++] = fh;
					h--;
				}
			}
		}
		return result;
	}

	public int function(int x, int a, int b, int c) {
		return a * x * x + b * x + c;
	}

	/*Wiggle Sort I/Convert array into Zig-Zag fashion: 
	 * Given an array of distinct elements, rearrange the elements of array in zig-zag fashion in O(n) time. 
	 * The converted array should be in form a < b > c < d > e < f.
	 */
	// Approach1:
	public void wiggleSort11(int[] arr) {
		int n = arr.length;
		for (int i = 0; i < n - 1; i++) {
			if (i % 2 == 0) { // odd element should be less than next element;
				if (arr[i] > arr[i + 1]) // swap only if greater than next element
					Utils.swap(arr, i, i + 1);
			} else {// even element should be less than next element
				if (arr[i] < arr[i + 1])// swap only if lesser than next element
					Utils.swap(arr, i, i + 1);
			}
		}

		// Print the result
		for (int i = 0; i < n; i++)
			System.out.print(arr[i] + " ");
	}

	/* Sort an array in wave form/Peaks and Valleys: (Vice versa of previous one)
	 * This function sorts arr[0..n-1] in wave form, i.e. arr[0] >= arr[1] <= arr[2] >= arr[3] <= arr[4]....
	 */
	void sortInWave(int arr[], int n) {
		for (int i = 0; i < n; i += 2) {
			// If current even element is smaller than previous
			if (i > 0 && arr[i - 1] > arr[i]) Utils.swap(arr, i - 1, i);

			// If current even element is smaller than next
			if (i < n - 1 && arr[i] < arr[i + 1]) Utils.swap(arr, i, i + 1);
		}
	}

	// Wiggle Sort II: (Vice versa of previous one & allowed duplicates)
	//Sorting and Arrangement: O(nlogn) time and O(n)
	public void wiggleSort1(int[] arr) {
		if (arr.length == 0) return;
		int n = arr.length, m = (n + 1) / 2;
		int[] copy = Arrays.copyOf(arr, n);
		Arrays.sort(copy);

		//Using for loop
		for (int i = m - 1, j = 0; i >= 0; i--, j += 2)
			arr[j] = copy[i];
		for (int i = n - 1, j = 1; i >= m; i--, j += 2)
			arr[j] = copy[i];

		//or

		//Using while loop
		//1. i or sorted copy index moves from m-1 to 0. It stores in j=0,2,4...
		/*  int i = m-1, j =0;
		while(i>=0){
		    arr[j] = copy[i];
		    i--; 
		    j += 2;
		}
		
		//2.i or sorted copy index moves from n-1 to m. It stores in j=1,3,5..
		i = n-1; j =1;
		while(i>=m){
		    arr[j] = copy[i];
		    i--; 
		    j += 2;
		}*/
	}

	// Approach: Combination of Using 3-Way Partitioning & Kth Smallest element Algorithm
	public void wiggleSort2(int[] nums) {
		int n = nums.length;
		// Find Median using kth smallest element Quick Select Algorithm
		int median = findMedian(nums, (n + 1) / 2);

		// Using 3-Way Partitioning or Sort 012 algorithm to order in waveform
		int left = 0, i = 0, right = n - 1;
		while (i <= right) {
			if (nums[newIndex(i, n)] > median) {
				Utils.swap(nums, newIndex(left++, n), newIndex(i++, n));
			} else if (nums[newIndex(i, n)] < median) {
				Utils.swap(nums, newIndex(right--, n), newIndex(i, n));
			} else {
				i++;
			}
		}
	}

	private int newIndex(int index, int n) {
		return (1 + 2 * index) % (n | 1);
	}

	// Find the Median using kth smallest element Quick Select Algorithm
	public int findMedian(int[] nums, int k) {
		if (nums.length == 0 || k == 0) return 0;

		int l = 0, r = nums.length - 1;
		while (l <= r) {
			int index = partition(nums, l, r);
			if (index == k - 1) return nums[index];
			else if (index < k - 1) l = index + 1;
			else r = index - 1;
		}
		return -1;
	}

	public int partition(int[] a, int left, int right) {
		int i = left, j = left, pivot = a[right];
		while (j < right) {
			if (a[j] < pivot) {
				Utils.swap(a, i, j);
				i++;
			}
			j++;
		}
		Utils.swap(a, i, right);
		return i;
	}

}