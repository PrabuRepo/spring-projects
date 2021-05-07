package com.basic.datastructures;

import java.util.Scanner;

import com.basic.datastructures.operations.HashingOperations;

/*
 * Hashing: Hashing is a technique that is used to uniquely identify a specific object from a group of similar objects. 
 * The idea of hashing is to distribute entries (key/value pairs) uniformly across an array. Each element is assigned a key (Key calculates 
 * using the hash function). Using the key, the algorithm (hash function) computes an index that suggests where an entry can be found
 * or inserted.
 *  
 * Hash Function: In simple terms, a hash function maps a big number or string to a small integer that can be used as index in hash table.
 * A good hash function should have following properties
 *    1) Easy to compute: It should be easy to compute and must not become an algorithm in itself.
 *    2) Uniform distribution: It should provide a uniform distribution across the hash table and should not result in clustering.
 *    3) Less Collisions: Collisions occur when pairs of elements are mapped to the same hash value. These should be avoided.
 * Purpose of Hashing:
  - Hashing is used to index data  
  - In cryptographic application 
  - Sharding the Keys 
  - Finding duplicate records 
 *    
 * Hash Table: An array that stores pointers to records corresponding to a given phone number. An entry in hash table is NIL if no 
 * existing phone number has hash function value equal to the index for the entry.
 * 
 * Collision Handling: Since a hash function gets us a small number for a big key, there is possibility that two keys result in same 
 * value. The situation where a newly inserted key maps to an already occupied slot in hash table is called collision and must be
 * handled using some collision handling technique. Following are the ways to handle collisions:
 * 
 * Separate Chaining: The idea is to make each cell of hash table point to a linked list of records that have same hash function value. Chaining 
 * is simple, but requires additional memory outside the table. 
 * 
 * Open Addressing: In open addressing, all elements are stored in the hash table itself. Each table entry contains either a record or NIL. 
 * When searching for an element, we one by one examine table slots until the desired element is found or it is clear that the element is
 * not in the table.
 *
 */
public class Hashing {
	public static void main(String[] args) {

	}
}

/*
 * Open Addressing: Like separate chaining, open addressing is a method for handling collisions. In Open Addressing, all elements are 
 * stored in the hash table itself. So at any point, size of the table must be greater than or equal to the total number of keys.
 * 
 * Open Addressing is done in the following ways: - 
 *    1. Linear Probing  - 
 *    		Linear probing is when the interval between successive probes is fixed (usually to 1). Let�s assume that the hashed index for
 *       a particular entry is index. The probing sequence for linear probing will be:
 *        index = index % hashTableSize 
 *        index = (index + 1) % hashTableSize
 *        index = (index + 2) % hashTableSize  and so on�
 *        
 *    2. Quadratic Probing - Quadratic probing is similar to linear probing and the only difference is the interval between successive
 *    probes or entry slots. The interval between slots is computed by adding the successive value of an arbitrary polynomial in the 
 *    original hashed index. The probing sequence will be:
 *        index = index % hashTableSize 
 *        index = (index + 1^2) % hashTableSize
 *        index = (index + 2^2) % hashTableSize  and so on�
 *    
 *    3. Double Hashing -Double hashing is similar to linear probing and the only difference is the interval between successive probes.
 *    	 Here, the interval between probes is computed by using two hash functions. The probing sequence will be:
 *    			index = (index + 1 * indexH) % hashTableSize;
 *    			index = (index + 2 * indexH) % hashTableSize;  and so on�
 *    	Here index -> First HashFunction; indexH = second hash function
 *    
 */
class HashOpenAddressing implements HashingOperations {

	Integer[] array;
	int currSize;
	int maxSize;

	// Initialize the array size in the constructor
	public HashOpenAddressing(int size) {
		// Choose max size as nearest prime number to avoid clustering
		this.maxSize = nextPrime(size);
		System.out.println("Max size: " + maxSize);
		this.array = new Integer[maxSize];
		// Arrays.fill(array, -1);
	}

	@Override
	public void add(int data) {
		if (!isHashFull()) {
			// int key = findKey1(input); // Linear Probing
			int key = findKey2(data); // Quadratic Probing
			// int key = findKey3(input); // Double Hashing
			array[key] = data;
			currSize++;
		} else {
			System.out.println("Hash full, increase the size.");
		}
	}

	@Override
	public int get(int data) {
		// return findValue1(data); //Linear Probing
		// return findValue2(data); // Quadratic Probing
		return findValue3(data); // Double Hashing
	}

	@Override
	public boolean contains(int data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(int data) {
		// int key = findValue1(data); //Linear Probing
		// int key = findValue2(data); // Quadratic Probing
		int key = findValue3(data); // Double Hashing
		if (key >= 0) {
			array[key] = null;
			currSize--;
			return true;
		}
		return false;
	}

	@Override
	public void set(int index, int data) {
		// TODO Auto-generated method stub

	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void print() {
		for (int i = 0; i < maxSize; i++) {
			System.out.print(i + " - " + array[i] + "; ");
		}
	}

	// Get the empty index/key to insert the data using Linear Probing
	public int findKey1(int data) {
		int index = hash1(data);
		int i = 1, hashValue = index;
		while (array[index] != null) {
			// 1.Linear Probing: If slot hash(x) % S is full, then we try (hash(x) + i) % S; where i=1,2,3...
			index = (hashValue + i) % maxSize;
			i++;
		}
		return index;
	}

	/*
	 * Quadratic Probing is not working for below test case
	 * Max size = 11;
	 * 25 25 25 25 25 25 25 25 25 25 25 
	 */
	// Get the empty index/key to insert the data using Quadratic Probing
	public int findKey2(int data) {
		int index = hash1(data);
		int i = 0, hashValue = index;
		while (array[index] != null) {
			// 2.Quadratic Probing: If slot hash(x) % S is full, then we try (hash(x) + 1*1) %S; where i=1,2,3..
			index = (hashValue + (i * i)) % maxSize;
			System.out.println("i: " + i + " index: " + index);
			i++;
		}
		return index;
	}

	// Get the empty index/key to insert the data using Double Hashing
	public int findKey3(int data) {
		int index = hash1(data); // First hash function is typically hash1(key) = key % TABLE_SIZE
		int i = 0, hash1 = index, indexH = hash2(data); // Second hash function is : hash2(key) = PRIME � (key % PRIME)
														// where PRIME is a prime smaller
														// than the TABLE_SIZE.
		while (array[index] != null) {
			// 3.Double Hashing: (hash1(key) + i * hash2(key)) % TABLE_SIZE) %S; where i=1,2,3..
			index = (hash1 + (i * indexH)) % maxSize;
			System.out.println("i: " + i + " index: " + index);
			i++;
		}
		return index;
	}

	// Find the data in the array (use Linear Probing)
	public int findValue1(int element) {
		int index = -1;
		index = hash1(element); // Find the hashing value
		int hashValue = index;
		for (int i = 1; i < maxSize; i++) {
			if (array[index] != null && array[index] == element) {
				return index;
			} else {
				index = (hashValue + i) % maxSize; // Linear Probing: (hash(x) + i) % S;
			}
		}
		return -1;
	}

	// Find the data in the array (use Linear Probing)
	public int findValue2(int element) {
		int index = -1;
		index = hash1(element); // Find the hashing value
		int hashValue = index;
		for (int i = 1; i < maxSize; i++) {
			if (array[index] != null && array[index] == element) {
				return index;
			} else {
				index = (hashValue + (i * i)) % maxSize; // Linear Probing: (hash(x) + i) % S;
			}
		}
		return -1;
	}

	// Find the data in the array (use Double Hashing)
	public int findValue3(int element) {
		int index = hash1(element); // First hash function is typically hash1(key) = key % TABLE_SIZE
		int hashValue1 = index, hashValue2 = hash2(element);
		for (int i = 1; i < maxSize; i++) {
			if (array[index] != null && array[index] == element) {
				return index;
			} else {
				// 3.Double Hashing: (hash1(key) + i * hash2(key)) % TABLE_SIZE) %S;
				index = (hashValue1 + (i * hashValue2)) % maxSize;
			}
		}
		return -1;
	}

	private boolean isHashFull() {
		return (currSize == maxSize);
	}

	private int hash1(int key) {
		return key % maxSize;
	}

	private int hash2(int val) {
		int PRIME_NO = 7; // hash2(key) = PRIME � (key % PRIME) where PRIME is a prime smaller than the TABLE_SIZE.
		return (PRIME_NO - val % PRIME_NO);
	}

	/************ Util Methods ******************/
	private int nextPrime(int n) {
		while (!isPrime(n)) {
			n++;
		}
		return n;
	}

	private boolean isPrime(int n) {
		int sqrt = (int) Math.sqrt((double) n);
		boolean flag = true;
		if (n == 1) {
			flag = false;
		} else if (n == 2 || n == 3) {
			flag = true;
		} else {
			for (int i = 2; i <= sqrt; i++) {
				if (n % i == 0) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char ch;
		int input;
		HashOpenAddressing hashTable = new HashOpenAddressing(10);
		do {
			System.out.println("HashTable Operations:");
			System.out.println("1.Insert");
			System.out.println("2.Delete");
			System.out.println("3.Find/Search");
			System.out.print("Enter option:");
			input = in.nextInt();
			switch (input) {

			case 1:
				System.out.println("Enter no of elements to be inserted:");
				int t = in.nextInt();
				while (t-- > 0) {
					hashTable.add(in.nextInt());
				}
				System.out.println("Elements are inserted!");
				break;
			case 2:
				System.out.println("Enter element needs to be deleted:");
				hashTable.remove(in.nextInt());
				break;
			case 3:
				System.out.println("Enter elements needs to be find:");
				System.out.println("Element present at index: " + hashTable.get(in.nextInt()));
				break;
			default:
				System.out.println("Please enter the valid option!!!");
				break;
			}
			System.out.println("\nDisplay:");
			hashTable.print();
			System.out.println("\nDo you want to continue(y/n):");
			ch = in.next().charAt(0);
		} while (ch == 'y' || ch == 'Y');
		System.out.println("****Thank You******");
		in.close();
	}

}

/* Separate Chaining:The idea is to make each cell of hash table point to a linked list of records that have same hash function value. Chaining
 * is simple, but requires additional memory outside the table.
 */
class HashSeperateChaining implements HashingOperations {

	java.util.LinkedList<Integer>[] table;
	int hashSize;
	int capacity;

	public HashSeperateChaining(int capacity) {
		this.capacity = capacity;
		this.table = new java.util.LinkedList[capacity];
	}

	@Override
	public void add(int data) {
		int pos = hash(data);
		if (table[pos] == null) table[pos] = new java.util.LinkedList();
		hashSize++;
		table[pos].add(data);
	}

	@Override
	public int get(int data) {
		int pos = hash(data);
		if (table[pos] != null && table[pos].contains(data)) {
			return data;
		}
		return -1;
	}

	@Override
	public boolean contains(int data) {
		return get(data) != -1 ? true : false;
	}

	@Override
	public boolean remove(int data) {
		int pos = hash(data);
		if (table[pos] != null) {
			if (table[pos].remove((Integer) data)) {
				hashSize--;
				return true;
			}
		}
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void set(int index, int data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void print() {
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null) {
				System.out.println("\ni=" + i + "->");
				table[i].forEach(k -> System.out.print(k + " "));
			}
		}
	}

	private int hash(int key) {
		return (Integer) key % capacity;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char ch;
		int input;
		HashSeperateChaining hashTable = new HashSeperateChaining(10);
		do {
			System.out.println("HashTable Operations:");
			System.out.println("1.Insert");
			System.out.println("2.Delete");
			System.out.println("3.Find/Search");
			System.out.print("Enter option:");
			input = in.nextInt();
			switch (input) {

			case 1:
				System.out.println("Enter no of elements to be inserted:");
				int t = in.nextInt();
				while (t-- > 0) {
					hashTable.add(in.nextInt());
				}
				System.out.println("Elements are inserted!");
				break;
			case 2:
				System.out.println("Enter element needs to be deleted:");
				hashTable.remove(in.nextInt());
				break;
			case 3:
				System.out.println("Enter elements needs to be find:");
				System.out.println("Element is present in the list? " + hashTable.get(in.nextInt()));
				break;
			default:
				System.out.println("Please enter the valid option!!!");
				break;
			}
			System.out.println("\nDisplay:");
			hashTable.print();
			System.out.println("\nDo you want to continue(y/n):");
			ch = in.next().charAt(0);
		} while (ch == 'y' || ch == 'Y');
		System.out.println("****Thank You******");
		in.close();

	}
}