package com.basic.datastructures.operations;

public interface StackOperations extends DSOperations {
	//Add: Add element in the top of the stack
	public void push(int data);

	//Remove: Remove element from top of the stack
	public int pop();

	//Get: Get top of the element in the stack
	public int peek();

	//Print all the elements in the list
	public void print();

}
