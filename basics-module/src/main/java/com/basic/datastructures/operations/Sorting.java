package com.basic.datastructures.operations;

import com.common.model.ListNode;

public interface Sorting {
	public void bubbleSort(int[] a);

	public void bubbleSortRecursive(int[] a);

	public void selectionSort(int[] a);

	public void pancakeSort(int[] a);

	public void insertionSort(int[] a);

	public void insertionSortRecursive(int[] a);

	public void insertionSort(ListNode head);

	public void mergeSort(int[] a);

	public void mergeSort(ListNode head);

	public void quickSort(int[] a);

	public void heapSort(int[] a);

	public void countingSort(int[] a);

	public void radixSort(int[] a);

	public void bucketSort(int[] a);

}
