package com.basic.datastructures.operations;

import com.common.model.ListNode;

public interface DLLOperations extends DSOperations {
	//Add: Insert element in the given index
	public void insert(int index, int data);

	//Add: Insert element before the given element
	public void insertBefore(int data, int newData);

	//Add: Insert element after the given element
	public void insertAfter(int data, int newData);

	//Find the given element and return the same node
	public ListNode get(int data);

	//Print all the elements in the list
	public void print();

}
