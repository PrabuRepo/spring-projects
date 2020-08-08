package com.basic.datastructures.operations;

import com.common.model.ListNode;

public interface SLLOperations extends DSOperations {
	//Add: Insert element in the given index
	public void insert(int index, int data);

	//Find the given element and return the same node
	public ListNode get(int data);

	//Print all the elements in the list
	public void print();

}
