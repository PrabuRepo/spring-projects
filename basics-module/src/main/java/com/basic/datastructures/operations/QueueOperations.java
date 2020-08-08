package com.basic.datastructures.operations;

public interface QueueOperations extends DSOperations {
	//Remove: Remove first element in the queue
	public int poll();

	//Get: Get the first element in the Queue
	public int peek();

	//Print all the elements in the list
	public void print();

}
