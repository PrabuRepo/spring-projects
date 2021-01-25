package com.basic.datastructures.operations;

public interface HeapOperations extends DSOperations, QueueOperations {

	// to get index of parent node at index i 
	int parent(int i);

	// to get index of left child of node at index i 
	int left(int i);

	// to get index of right child of node at index i 
	int right(int i);

	//Verify heap property for each index and move down to rearrange the data to make sure to follow the heap property
	//ShiftDown and Heapify are same
	void heapify(int i);

	//Verify heap property for each index and move down to rearrange the data to make sure to follow the heap property
	void shiftDown(int i);

	//Verify heap property for each index and move up to rearrange the data to make sure to follow the heap property
	void shiftUp(int i);
}
