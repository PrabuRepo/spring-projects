package com.basic.generic.datastructures;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//Simple implementation: Allocate max possible number as size of map to avoid the collisions.
public class MyHashSet {

	private int[] map;
	private int size;

	public MyHashSet() {
		//Constraint: 0 <= key <= 10^6; So allocate max possible key as a size
		size = 1000001;
		map = new int[size];
		Arrays.fill(map, -1);
	}

	public void add(int key) {
		if (contains(key)) return;
		map[getIndex(key)] = key;
	}

	public void remove(int key) {
		if (!contains(key)) return;
		map[getIndex(key)] = -1;
	}

	/** Returns true if this set contains the specified element */
	public boolean contains(int key) {
		return map[getIndex(key)] == -1 ? false : true;
	}

	//Hashing
	private int getIndex(int key) {
		return key % size;
	}

	public static void main(String[] args) {
		MyHashSet set = new MyHashSet();
		set.add(1);
		set.add(2);
		System.out.println("Contains: " + set.contains(1));
		System.out.println("Contains: " + set.contains(2));
		set.remove(2);
		set.remove(3);
		System.out.println("Contains: " + set.contains(1));
		System.out.println("Contains: " + set.contains(2));
	}
}

//Implementation similar to Java Built-in Hashset implementations: Separate Chaining
class MyHashSet2 {
	List<Integer>[] container = null;
	int cap;
	double loadFactor;
	int count;

	/** Initialize your data structure here. */
	public MyHashSet2() {
		cap = 1000;
		loadFactor = 0.75;
		count = 0;
		container = new java.util.LinkedList[cap];
	}

	public void add(int key) {
		if (contains(key)) return;

		//Number of elements(count) reaches 75%, then double the cap size and rehash all the elements in the container 
		if (loadFactor * cap == count) {
			rehashing();
		}

		int hash = key % cap;
		if (container[hash] == null) container[hash] = new java.util.LinkedList<>();
		container[hash].add(key);
		++count;
	}

	public void remove(int key) {
		int hash = key % cap;
		List<Integer> list = container[hash];
		if (list == null) return;

		Iterator<Integer> itr = list.iterator();
		while (itr.hasNext()) {
			if (itr.next() == key) {
				itr.remove();
				--count;
			}
		}
	}

	/** Returns true if this set contains the specified element */
	public boolean contains(int key) {
		int hash = key % cap;
		List<Integer> list = container[hash];
		if (list != null) {
			Iterator<Integer> itr = list.iterator();
			while (itr.hasNext()) {
				if (itr.next() == key) return true;
			}
		}
		return false;
	}

	private void rehashing() {
		count = 0;
		cap *= 2; // Double the capacity
		List<Integer>[] oldContainer = container;
		container = new java.util.LinkedList[cap];

		//Copy the elements from the oldContainer to new contianer
		for (int i = 0; i < oldContainer.length; i++) {
			List<Integer> list = oldContainer[i];
			if (list == null || list.isEmpty()) continue;

			for (int entry : list)
				this.add(entry);
		}
	}

	public static void main(String[] args) {
		MyHashSet2 set = new MyHashSet2();
		set.add(1);
		set.add(2);
		System.out.println("Contains: " + set.contains(1));
		System.out.println("Contains: " + set.contains(2));
		set.remove(2);
		set.remove(3);
		System.out.println("Contains: " + set.contains(1));
		System.out.println("Contains: " + set.contains(2));
	}
}