package com.basic.datastructures.adv.operations;

public interface BITOperations {

	//Using point update method: Time: O(nlogn)
	void buildFenwickTree1(int[] nums);

	//Cascading sum approach: Time: O(n)
	void buildFenwickTree2(int[] nums);

	public void update(int index, int val);

	public int sumRange(int i, int j);

}
