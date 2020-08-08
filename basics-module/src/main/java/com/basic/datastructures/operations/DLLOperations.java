package com.basic.datastructures.operations;

import com.common.model.ListNode;

public interface DLLOperations extends DSOperations {
	//Add: Insert element in the given index/pos(from index 1) 
	public void insert(int index, int data);

	//Add: Insert element before the given element
	public void insertBefore(int data, int newData);

	//Add: Insert element after the given element
	public void insertAfter(int data, int newData);

	//Find the given element and return that node
	public ListNode get(int data);

	//Find the given element based on position/index(from index 1) and return that node
	public ListNode get2(int index);

	//Print all the elements in the list
	public void print();

	//Print all the elements in the list in reverse.
	//This is understand that backward traverse is possible in DLL
	public void printReverse();

}
