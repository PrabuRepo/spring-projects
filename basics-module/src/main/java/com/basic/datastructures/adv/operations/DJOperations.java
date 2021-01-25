package com.basic.datastructures.adv.operations;

public interface DJOperations {
	/*
	 * Union(x,y) uses Find to determine the roots of the trees x and y belong to. If the roots are distinct, the trees are combined
	 * by attaching the root of one to the root of the other.  union by rank or union by size is uses to implement this.
	 * Note: Union operation return type should be void. But boolean will be useful to apply in problems
	 */
	public boolean union(int n1, int n2);

	// Recursive Approach
	public int find(int node);

	// Recursive Approach
	public int find2(int node);
}
