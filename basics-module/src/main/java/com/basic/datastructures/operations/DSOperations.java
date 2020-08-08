package com.basic.datastructures.operations;

public interface DSOperations {

	//Add: Add given element in the data structure
	public void add(int data);

	//TODO: Revisit and remove if doesnt make sense
	//Update: Update element in the given index 
	public void set(int index, int data);

	//Get: check whether given element is present in the data structure
	public boolean contains(int data);

	//Remove: Remove the given element in the data structure
	public boolean remove(int data);

	//Returns no of elements present in the data structure
	public int size();

	//Check whether the data structure has any element or not
	public boolean isEmpty();
}
