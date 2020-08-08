package com.basic.datastructures.operations;

public interface DequeOperations extends DSOperations {
	//Add: Add element in the first position
	public void addFirst(int data);

	//Add: Add element in the last position
	public void addLast(int data);

	//Remove: Remove first element in the deque
	public int removeFirst();

	//Remove: Remove last element in the deque
	public int removeLast();

	//Remove: Get first element in the deque
	public int getFirst();

	//Get: Get last element in the deque
	public int getLast();

	//Print all the elements in the list
	public void print();

}
