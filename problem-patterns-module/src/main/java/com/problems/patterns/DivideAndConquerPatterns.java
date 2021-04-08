package com.problems.patterns;

public class DivideAndConquerPatterns {
	// Median of two sorted arrays(Same Size) - Brute Force:Time Complexity- o(n)

	/* Divide & conquer Alg-> Time Complexity: O(logn)
		1) Calculate the medians m1 and m2 of the input arrays ar1[]	and ar2[] respectively.
		2) If m1 and m2 both are equal then we are done. 
			return m1 (or m2)
		3) If arr[m1] is less than arr[m2], then median is present in one of the below two subarrays.
			Eliminate first half in arr1 and second half in arr2
			Eg: arr1={1,2,3,4}, arr2={5,6,7,8} -> Here arr1[m1] or 2 < arr2[m2] or 6
				After elimination arr1 and arr2 will be, {3,4} and {5,6}
		4) If arr[m1] is greater than arr[m2], then median is present in one of the below two subarrays.
			Eliminate second half in arr1 and first half in arr2
			Eg: arr1={5,6,7,8}, arr1={1,2,3,4} -> Here arr1[m1] or 6 > arr2[m2] or 2
				After elimination arr1 and arr2 will be, {5,6} and {3,4}
		5) Repeat the above process until size of both the subarrays becomes 2.
		6) If size of the two arrays is 2 then use below formula to get the median.
			Median = (max(ar1[l1], ar2[l2]) + min(ar1[h1], ar2[h2]))/2
	 */

	// Divide & conquer Alg(Iterative Approach)-> Time Complexity: O(logn)
	public double findMedian2(int[] a1, int[] a2) {
		int l1 = 0, h1 = a1.length - 1, l2 = 0, h2 = a2.length - 1, len = 0;

		while (l1 <= h1 && l2 <= h2) {
			len = h1 - l1 + 1; // or h2-l2+1; Because every call, both arrays have equal no of values
			if (l1 == h1 && l2 == h2) return (double) (a1[l1] + a2[l2]) / 2;
			if (h1 - l1 == 1 && h2 - l2 == 1) return (double) (Math.max(a1[l1], a2[l2]) + Math.min(a1[h1], a2[h2])) / 2;

			int m1 = (l1 + h1) / 2;
			int m2 = (l2 + h2) / 2;

			if (a1[m1] == a2[m2]) {
				return a1[m1];
			} else if (a1[m1] < a2[m2]) {
				l1 = l1 + len / 2;
				h2 = h2 - len / 2;
			} else {
				h1 = h1 - len / 2;
				l2 = l2 + len / 2;
			}
		}

		return -1;
	}

	// Divide & conquer Alg: Recursive Approach
	public double findMedian3(int[] a1, int[] a2) {
		return findMedian3(a1, a2, 0, a1.length - 1, 0, a2.length - 1);
	}

	public double findMedian3(int[] a1, int[] a2, int l1, int h1, int l2, int h2) {
		int len = h1 - l1 + 1; // or h2-l2+1; Because every call, both arrays have equal no of values

		if (l1 > h1 || l2 > h2) return -1;

		if (l1 == h1 && l2 == h2) return (double) (a1[l1] + a2[l2]) / 2;

		if ((h1 - l1 == 1) && (h2 - l2 == 1)) return (double) (Math.max(a1[l1], a2[l2]) + Math.min(a1[h1], a2[h2])) / 2;

		int m1 = (h1 + l1) / 2;
		int m2 = (h2 + l2) / 2;

		if (a1[m1] == a2[m2]) {
			return a1[m1];
		} else if (a1[m1] < a2[m2]) { // Remove 1st half in a1 & 2nd half in a2
			l1 = l1 + len / 2;
			h2 = h2 - len / 2;
		} else {// Remove 2nd half in a1 & 1st half in a2
			h1 = h1 - len / 2;
			l2 = l2 + len / 2;
		}

		return findMedian3(a1, a2, l1, h1, l2, h2);
	}

	// Median of two sorted arrays(different Size) - Brute Force:Time Complexity- O(n)
	public double findMedianDiffSizeArray1(int[] X, int[] Y) {
		return 0;
	}

	/* Median of two sorted arrays(different Size): Partition + Binary Search
	 * Solution:
	 * 		1. Do the binary search in min size array to find the partition which satisfies "leftX <= rightY && leftY <= rightX"
	 * 		   or 
	 * 		   Do the binary search in min size array to till we reach the condition "leftX <= rightY && leftY <= rightX"
	 * 		2. Each and every iteration,
	 * 			i. no of elements in left partition =  no of elements in the right partition, if totalLength is even
	 * 			ii. no of elements in left partition + 1 = no of element in the right partition, if totalLenght is odd has one 
	 * 
	 * Note: This solution is not similar to same size problem. This is not elimination logic, its partition concept.
	 * Here we are doing binary search on minimum size array to find the median.
	 * Time Complexity- O(log(min(a,b)))
	 */
	public double findMedianDiffSizeArray2(int[] X, int[] Y) {
		int lenX = X.length, lenY = Y.length;

		if (lenX > lenY) return findMedianDiffSizeArray2(Y, X);

		int l = 0, h = lenX; //Note: h is size of X[]; not n-1
		int totLength = lenX + lenY;
		while (l <= h) {
			//patitionX is mid element in the array X; partitionX = m
			int partitionX = (l + h) / 2;
			//totLength+1 is used to handle the odd no elements. This make sure left side partition has additional one element in the left side.
			int partitionY = ((totLength + 1) / 2) - partitionX;

			int leftX = getLeft(X, partitionX);
			int rightX = getRight(X, partitionX);

			int leftY = getLeft(Y, partitionY);
			int rightY = getRight(Y, partitionY);

			if (leftX <= rightY && leftY <= rightX) {
				if (totLength % 2 == 0) { // For even no of data
					return (double) (Math.max(leftX, leftY) + Math.min(rightX, rightY)) / 2;
				} else { // For odd no of data
					return (double) (Math.max(leftX, leftY));
				}
			} else if (leftX > rightY) {
				// we are too far on right side for partitionX. Go on left side(Reduce h value).
				h = partitionX - 1;
			} else {// we are too far on left side for partitionX. Go on right side(Increase l value).
				l = partitionX + 1;
			}
		}
		return 0.0;
	}

	// if partition is 0 it means nothing is there on left side. Use -INF for leftX
	private int getLeft(int[] arr, int partIndex) {
		return partIndex == 0 ? Integer.MIN_VALUE : arr[partIndex - 1];
	}

	// if partition is length of input then there is nothing on right side. Use +INF for rightX
	private int getRight(int[] arr, int partIndex) {
		return partIndex == arr.length ? Integer.MAX_VALUE : arr[partIndex];
	}

	// K-th Element of Two Sorted Arrays
	// Brute force Approach: Time Complexity- O(m+n)
	public int findKthElement1() {
		return 0;
	}

	public int findKthElement2(int[] a1, int[] a2, int k) {
		int len1 = a1.length, len2 = a2.length;
		if (len1 > len2) return findKthElement2(a2, a1, k);

		return findKthElement2(a1, a2, 0, len1 - 1, 0, len2 - 1, k);
	}

	public int findKthElement2(int[] a1, int[] a2, int l1, int h1, int l2, int h2, int k) {
		int len1 = a1.length - 1, len2 = a2.length - 1;

		if ((len1 + len2) < k) return -1;

		if (l1 == h1 && k > 1) return a2[k - 1];

		if (k == 1) return Math.max(a1[l1], a2[l2]);

		int m1 = Math.min(len1, k / 2);
		int m2 = Math.min(len2, k / 2);

		if (a1[m1] < a2[m2]) return findKthElement2(a1, a2, l1 + m1, h1, l2, h2, k - m1);

		return findKthElement2(a1, a2, l1, h1, l2 + m2, h2, k - m2);
	}

	public static int kthElement(int[] a1, int[] a2, int k) {
		return kthElement(a1, a2, 0, a1.length - 1, 0, a2.length - 1, k);
	}

	public static int kthElement(int[] a1, int[] a2, int l1, int h1, int l2, int h2, int k) {
		int m = h1 - l1 + 1, n = h2 - l2 + 1; // Size of the array
		if (k > m + n || k < 0) return -1;

		// Keep always a1 > a2
		if (m < n) return kthElement(a2, a1, l2, h2, l1, h1, k);

		if (m == 0) return a1[l1 + k - 1]; // i.e k-1 th index in arr, adding l1 for base index

		if (k == 1) return Math.min(a1[l1], a2[l2]);

		int i = Math.min(m, k / 2), j = Math.min(n, k / 2);
		if (a1[l1 + i - 1] < a2[l2 + j - 1]) return kthElement(a1, a2, l1 + i, h1, l2, h2, k - i);

		return kthElement(a1, a2, l1, h1, l2 + j, h2, k - j);
	}

}
