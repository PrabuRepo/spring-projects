package com.basic.generic.datastructures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/*
 * Ref: https://dzone.com/articles/how-hashmap-works-in-java
 *      https://howtodoinjava.com/java/collections/hashmap/how-hashmap-works-in-java/
 * 		https://www.geeksforgeeks.org/internal-working-of-hashmap-java/
 */
public class MyHashMap<K, V> {

	/*
	get(K key) : returns the value corresponding to the key if the key is present in HT (Hast Table)
	getSize() : return the size of the HT
	add() : adds new valid key, value pair to the HT, if already present updates the value
	remove() : removes the key, value pair
	isEmpty() : returns true if size is zero
	*/
	ArrayList<HashNode<K, V>> bucketArray;
	int capacity;
	int size;

	public MyHashMap() {
		bucketArray = new ArrayList<>();
		capacity = 10;
		size = 0;

		for (int i = 0; i < 10; i++)
			bucketArray.add(null);
	}

	public void add(K key, V value) {
		// Calculate the index using hashing method
		int index = getIndex(key);
		// Get the value from the array
		HashNode<K, V> hashNode = bucketArray.get(index);
		// If that index is empty, create the new node and add in the array
		if (hashNode == null) {
			hashNode = new HashNode<K, V>(key, value);
		} else {// If index already has some element link the new element with existing one
			HashNode<K, V> temp = hashNode;
			while (temp.next != null) temp = temp.next;
			temp.next = new HashNode<K, V>(key, value);
		}
		// Finally set the updated elements in the array
		bucketArray.set(index, hashNode);
		// Increase the current size
		size++;
	}

	public V remove(K key) {
		// Calculate the index using hashing method
		int index = getIndex(key);
		V value = null;
		// Get the value from the array
		HashNode<K, V> hashNode = bucketArray.get(index);
		// If that index has element, then delete it
		if (hashNode != null) {
			HashNode<K, V> temp = hashNode;
			if (hashNode.key.equals(key)) {// If first element matches with the given key
				value = hashNode.value;
				hashNode = hashNode.next;
				size--;
			} else {// Iterate the linked list and delete the key
				while (temp != null) {
					if (temp.next != null && temp.next.key.equals(key)) {
						value = temp.next.value;
						temp.next = temp.next.next;
						size--;
					} else {
						temp = temp.next;
					}
				}
			}
		} else {
			System.out.println("Element is not present!");
		}
		return value;
	}

	public void display() {
		for (int i = 0; i < capacity; i++) {
			HashNode<K, V> hashNode = bucketArray.get(i);
			if (hashNode != null) {
				while (hashNode != null) {
					System.out.print(hashNode.key + "-" + hashNode.value + "; ");
					hashNode = hashNode.next;
				}
			}
		}
	}

	int getIndex(K key) {
		int hashCode = key.hashCode();
		return hashCode % capacity;
	}

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		char ch;
		int input;
		MyHashMap<Integer, Integer> map = new MyHashMap<>();
		do {
			System.out.println("Map Operations:");
			System.out.println("1.Add");
			System.out.println("2.Remove");
			System.out.println("3.Find");
			System.out.print("Enter option:");
			input = in.nextInt();
			switch (input) {

			case 1:
				System.out.println("Enter no of Key-Value pair to be inserted:");
				int t = in.nextInt();
				while (t-- > 0) {
					map.add(in.nextInt(), in.nextInt());
				}
				System.out.println("Elements are inserted!");
				break;
			case 2:
				System.out.println("Enter key needs to be deleted:");
				System.out.println("Element has deleted? " + map.remove(in.nextInt()));
				break;
			case 3:
				System.out.println("Enter elements needs to be find:");
				// System.out.println("Element is present in the list? " +
				// list.find(in.nextInt()));
				break;
			default:
				System.out.println("Please enter the valid option!!!");
				break;

			}
			System.out.println("\nDisplay:");
			map.display();
			System.out.println("\nDo you want to continue(y/n):");
			ch = in.next().charAt(0);
		} while (ch == 'y' || ch == 'Y');
		System.out.println("****Thank You******");
		in.close();

	}

}

//Simple implementation: Allocate max possible number as size of map to avoid the collisions.
class MyHashMap1 {

	int size;
	HashMapNode[] map;

	/** Initialize your data structure here. */
	public MyHashMap1() {
		this.size = 1000001;
		this.map = new HashMapNode[size];
	}

	/** value will always be non-negative. */
	public void put(int key, int value) {
		int index = getIndex(key);
		map[index] = new HashMapNode(key, value);
	}

	/**
	 * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for
	 * the key
	 */
	public int get(int key) {
		int index = getIndex(key);

		return map[index] != null ? map[index].value : -1;
	}

	/** Removes the mapping of the specified value key if this map contains a mapping for the key */
	public void remove(int key) {
		int index = getIndex(key);
		map[index] = null;
	}

	private int getIndex(int key) {
		return key % size;
	}
}

//Implementation similar to Java Built-in Hashset implementations: Separate Chaining
class MyHashMap2 {
	List<HashMapNode>[] container = null;
	int cap;
	double loadFactor;
	int count;

	/** Initialize your data structure here. */
	public MyHashMap2() {
		cap = 1000;
		loadFactor = 0.75;
		count = 0;
		container = new java.util.LinkedList[cap];
	}

	/** value will always be non-negative. */
	public void put(int key, int value) {
		//If key already presents, remove first
		if (get(key) != -1) remove(key);

		//Number of elements(count) reaches 75%, then double the cap size and rehash all the elements in the container 
		if (loadFactor * cap == count) {
			//TODO: Do this later
			//rehashing();
		}

		int hash = key % cap;
		if (container[hash] == null) container[hash] = new java.util.LinkedList<>();
		container[hash].add(new HashMapNode(key, value));
		++count;
	}

	/** Removes the mapping of the specified value key if this map contains a mapping for the key */
	public void remove(int key) {
		int hash = key % cap;
		List<HashMapNode> list = container[hash];
		if (list == null) return;

		Iterator<HashMapNode> itr = list.iterator();
		while (itr.hasNext()) {
			if (itr.next().key == key) {
				itr.remove();
				--count;
			}
		}
	}

	/**
	 * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for
	 * the key
	 */
	public int get(int key) {
		int hash = key % cap;
		List<HashMapNode> list = container[hash];
		if (list != null) {
			for (HashMapNode curr : list) {
				if (curr.key == key) {
					return curr.value;
				}
			}
		}
		return -1;
	}

	//TODO: Revist this logic
	private void rehashing() {
		count = 0;
		cap *= 2; // Double the capacity
		List<HashMapNode>[] oldContainer = container;
		container = new java.util.LinkedList[cap];

		//Copy the elements from the oldContainer to new contianer
		for (int i = 0; i < oldContainer.length; i++) {
			List<HashMapNode> list = oldContainer[i];
			if (list == null || list.isEmpty()) continue;

			for (HashMapNode curr : list)
				this.put(curr.key, curr.value);
		}
	}

}

class HashMapNode {
	public int key;
	public int value;

	public HashMapNode(int k, int v) {
		this.key = k;
		this.value = v;
	}
}

class HashNode<K, V> {
	K key;
	V value;
	HashNode<K, V> next;

	public HashNode(K key, V value) {
		this.key = key;
		this.value = value;
		this.next = null;
	}
}
