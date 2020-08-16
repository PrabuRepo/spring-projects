package com.problems.patterns;

public class DivideAndConquerPatterns {
	// Median of two sorted arrays(Same Size) - Brute Force:Time Complexity- o(n)
	/* Divide & conquer Alg(Recursive Approach)-> Time Complexity: O(logn)
	 * 1) Calculate the medians m1 and m2 of the input arrays ar1[]	and ar2[] respectively.
	2) If m1 and m2 both are equal then we are done. 
		return m1 (or m2)
	3) If m1 is greater than m2, then median is present in one of the below two subarrays.
		a)  From first element of ar1 to m1 (ar1[0...|_n/2_|])
		b)  From m2 to last element of ar2  (ar2[|_n/2_|...n-1])
	4) If m2 is greater than m1, then median is present in one of the below two subarrays.
		a)  From m1 to last element of ar1  (ar1[|_n/2_|...n-1])
		b)  From first element of ar2 to m2 (ar2[0...|_n/2_|])
	5) Repeat the above process until size of both the subarrays becomes 2.
	6) If size of the two arrays is 2 then use below formula to get the median.
		Median = (max(ar1[0], ar2[0]) + min(ar1[1], ar2[1]))/2
	 */
	public double findMedian2(int[] a1, int[] a2) {
		return findMedian2(a1, a2, 0, a1.length - 1, 0, a2.length - 1);
	}

	public double findMedian2(int[] a1, int[] a2, int l1, int h1, int l2, int h2) {
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

		return findMedian2(a1, a2, l1, h1, l2, h2);
	}

	// Divide & conquer Alg(Iterative Approach)-> Time Complexity: O(logn)
	public double findMedian3(int[] a1, int[] a2) {
		int l1 = 0, h1 = a1.length - 1, l2 = 0, h2 = a2.length - 1, m1 = 0, m2 = 0, len = 0;

		while (l1 <= h1 && l2 <= h2) {
			len = h1 - l1 + 1; // or h2-l2+1; Because every call, both arrays have equal no of values
			if (l1 == h1 && l2 == h2) return (double) (a1[l1] + a2[l2]) / 2;
			if (h1 - l1 == 1 && h2 - l2 == 1) return (double) (Math.max(a1[l1], a2[l2]) + Math.min(a1[h1], a2[h2])) / 2;

			m1 = (l1 + h1) / 2;
			m2 = (l2 + h2) / 2;

			if (a1[m1] == a2[m2]) {
				return a1[m1];
			} else if (a1[m1] < a2[m2]) {
				l1 = l1 + len / 2; // or l1 = m1;
				h2 = h2 - len / 2; // or h2 = m2;
			} else {
				h1 = h1 - len / 2; // or h1 = m1;
				l2 = l2 + len / 2; // or l2 = m2;
			}
		}

		return -1;
	}

	// Median of two sorted arrays(different Size) - Brute Force:Time Complexity- O(n)
	public double findMedianDiffSizeArray1(int[] X, int[] Y) {
		return 0;
	}

	//Note: This solution is not similar to same size problem. This is not elimination logic, its partition concept.
	// Median of two sorted arrays(different Size) - Divide & Conquer Alg: Time Complexity- O(log(min(a,b)))
	public double findMedianDiffSizeArray2(int[] X, int[] Y) {
		int len1 = X.length, len2 = Y.length;

		if (len1 > len2) return findMedianDiffSizeArray2(Y, X);

		int maxLeftX, maxLeftY, minRightX, minRightY, low = 0, high = len1;
		int totLength = len1 + len2;
		while (low <= high) {
			int partitionX = (low + high) / 2;
			//totLength+1 is used to handle the odd no elements. This make sure left side partition has additional one element in the left side.
			int partitionY = ((totLength + 1) / 2) - partitionX;

			maxLeftX = getLeft(X, partitionX);
			minRightX = getRight(X, partitionX);

			maxLeftY = getLeft(Y, partitionY);
			minRightY = getRight(Y, partitionY);

			if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
				if (totLength % 2 == 0) { // For even no of data
					return (double) (Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY)) / 2;
				} else { // For odd no of data
					return (double) (Math.max(maxLeftX, maxLeftY));
				}
			} else if (maxLeftX > minRightY) { // we are too far on right side for partitionX. Go on left side(Reduce
												// high value).
				high = partitionX - 1;
			} else {// we are too far on left side for partitionX. Go on right side(Increase low value).
				low = partitionX + 1;
			}
		}
		return 0.0;
	}

	// if partition is length of input then there is nothing on right side. Use +INF for minRightX
	private int getRight(int[] arr, int partIndex) {
		return partIndex == arr.length ? Integer.MAX_VALUE : arr[partIndex];
	}

	// if partition is 0 it means nothing is there on left side. Use -INF for maxLeftX
	private int getLeft(int[] arr, int partIndex) {
		return partIndex == 0 ? Integer.MIN_VALUE : arr[partIndex - 1];
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
