package com.basic.datastructures;

import java.util.Arrays;
import java.util.Scanner;

import com.basic.datastructures.operations.HeapOperations;

/*
 * A Heap is a special Tree-based data structure in which the tree is a complete binary tree. A Binary Heap is a Binary Tree with following properties.
 *   1) It’s a complete tree (All levels are completely filled except possibly the last level and the last level has all keys as left as possible). 
 *      This property of Binary Heap makes them suitable to be stored in an array.
 *   2) A Binary Heap is either Min Heap or Max Heap. In a Min Binary Heap, the key at root must be minimum among all keys present in Binary Heap. 
 *      The same property must be recursively true for all nodes in Binary Tree. Max Binary Heap is similar to MinHeap.
 * 
 * Applications of Heaps:
 *   1) Heap Sort: Heap Sort uses Binary Heap to sort an array in O(nLogn) time.
 *   2) Priority Queue: Priority queues can be efficiently implemented using Binary Heap because it supports insert(), delete() and extractmax(), decreaseKey() 
 *      operations in O(logn) time. Binomoial Heap and Fibonacci Heap are variations of Binary Heap. These variations perform union also efficiently.
 *   3) Graph Algorithms: The priority queues are especially used in Graph Algorithms like Dijkstra’s Shortest Path and Prim’s Minimum Spanning Tree.
 *   4) Many problems can be efficiently solved using Heaps. See following for example.
 *   		a) K’th Largest Element in an array.
 *   		b) Sort an almost sorted array/
 *   		c) Merge K Sorted Arrays.
 * 
 * Priority Queue Impl using Heap:
 * - Max Binary Heap
 * - Min Binary Heap 
 */
public class Heap {

}

class MaxBinaryHeap implements HeapOperations {

	int heapSize;
	int capacity;
	Integer[] elements;

	public MaxBinaryHeap(int capacity) {
		this.heapSize = 0;
		this.capacity = capacity;
		this.elements = new Integer[capacity];
	}

	// Time Complexity: O(logn)
	@Override
	public void add(int data) {
		if (isHeapFull()) {
			System.out.println("Heap Full!");
		} else if (heapSize == 0) {
			elements[heapSize++] = data;
		} else {
			elements[heapSize] = data;
			//Shift up logic:
			shiftUp(heapSize);
			heapSize++;

			/*for (int i = heapSize; i > 0; i = parent(i)) {
				int parentIndex = parent(i);
				if (elements[parentIndex] != null && elements[parentIndex] < data) {
					swap(i, parentIndex);
				} else {
					break;
				}
			}*/
		}
	}

	//Update the value: Time Complexity: O(logn)
	@Override
	public void set(int index, int newValue) {
		if (index >= heapSize || index < 0) {
			System.out.println("Index is not a valid!");
			return;
		}

		if (newValue > elements[index]) {
			elements[index] = newValue;
			shiftUp(index);
		} else {
			elements[index] = newValue;
			shiftDown(index);
		}
	}

	//Get the max value
	// Time Complexity: O(logn)
	@Override
	public int poll() {
		Integer value = null;
		if (!isHeapEmpty()) {
			value = elements[0];
			if (heapSize == 1) {
				heapSize--;
			} else {
				elements[0] = elements[heapSize - 1]; // Last element assigned to root
				elements[heapSize--] = null; // Last element set it as null & reduce the heap size
				shiftDown(0);
			}
		}
		return value;
	}

	// Time Complexity:O(1)
	@Override
	public int peek() {
		return isHeapEmpty() ? null : elements[0];
	}

	// Search/Access element: Time Complexity: O(n)
	@Override
	public boolean contains(int data) {
		boolean flag = false;
		for (int i = 0; i < heapSize; i++) {
			if (elements[i] == data) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	@Override
	public void print() {
		for (int i = 0; i < heapSize; i++)
			System.out.print(elements[i] + " ");
	}

	@Override
	public boolean remove(int data) {
		/*Steps: 
			1. Find the index of the data.
			2. Set max value in index and set() method, that will move the record to top.
			3. Invoke poll() method, that will delete the root value and rearrange the data.*/

		int index = findIndex(data);
		if (index != -1) {
			set(index, Integer.MAX_VALUE);
			poll();
		}

		return true;
	}

	private int findIndex(int data) {
		for (int i = 0; i < heapSize; i++) {
			if (elements[i] == data) return i;
		}
		return -1;
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
	public int parent(int i) {
		return (i - 1) / 2;
	}

	@Override
	public int left(int i) {
		return 2 * i + 1;
	}

	@Override
	public int right(int i) {
		return 2 * i + 2;
	}

	//Shift Down Logic:
	@Override
	public void heapify(int i) {
		maxHeapify(i);

		maxHeapify2(i);
	}

	// Time Complexity: O(logn)
	private void maxHeapify(int i) {
		int left, right, maxIndex;
		while (i < heapSize) {
			left = left(i);
			right = right(i);
			maxIndex = i;

			if (left < heapSize && elements[left] > elements[maxIndex]) maxIndex = left;

			if (right < heapSize && elements[right] > elements[maxIndex]) maxIndex = right;

			if (maxIndex == i) break;
			swap(i, maxIndex);
			i = maxIndex;
		}
	}

	// Time Complexity: O(logn)
	// Recursive:
	public void maxHeapify2(int i) {
		int left = left(i);
		int right = right(i);
		int maxIndex = i;

		if (left < heapSize && elements[left] > elements[maxIndex]) maxIndex = left;

		if (right < heapSize && elements[right] > elements[maxIndex]) maxIndex = right;

		if (i != maxIndex) {
			swap(i, maxIndex);
			maxHeapify2(maxIndex);
		}
	}

	@Override
	public void shiftDown(int i) {
		maxHeapify(i);
	}

	@Override
	public void shiftUp(int i) {
		while (i > 0 && elements[parent(i)] < elements[i]) {
			swap(i, parent(i));
			i = parent(i);
		}
	}

	private boolean isHeapFull() {
		return (heapSize == capacity);
	}

	private boolean isHeapEmpty() {
		return (heapSize == 0);
	}

	private void swap(int pos1, int pos2) {
		int temp = elements[pos1];
		elements[pos1] = elements[pos2];
		elements[pos2] = temp;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char ch;
		int input;
		MaxBinaryHeap binaryHeap = new MaxBinaryHeap(20);
		do {
			System.out.println("Maximum Binary Heap Operations:");
			System.out.println("1.Insert");
			System.out.println("2.Delete");
			System.out.println("3.getMaximum");
			System.out.println("4.Increase Key");
			System.out.println("5.Find/Search");
			System.out.print("Enter option:");
			input = in.nextInt();
			switch (input) {
			case 1:
				System.out.println("Enter no of data to be inserted:");
				int t = in.nextInt();
				while (t-- > 0) binaryHeap.add(in.nextInt());

				System.out.println("Elements are inserted!");
				break;
			case 2:
				System.out.println("Max Element: " + binaryHeap.poll());
				break;
			case 3:
				System.out.println("Max element present in Heap: " + binaryHeap.peek());
				break;
			case 4:
				System.out.println("Enter the index & increased value:");
				binaryHeap.set(in.nextInt(), in.nextInt());
				break;
			case 6:
				System.out.println("Enter element needs to find: ");
				System.out.println("Element present in the heap? " + binaryHeap.contains(in.nextInt()));
				break;
			default:
				System.out.println("Please enter the valid option!!!");
				break;

			}
			System.out.println("\nDisplay:");
			binaryHeap.print();

			System.out.println("\nDo you want to continue(y/n):");
			ch = in.next().charAt(0);
		} while (ch == 'y' || ch == 'Y');
		System.out.println("****Thank You******");
		in.close();
	}

}

class MinBinaryHeap implements HeapOperations {
	Integer[] elements;
	int capacity;
	int currSize;

	public MinBinaryHeap(int max) {
		this.elements = new Integer[max];
		this.capacity = max;
		this.currSize = 0;
	}

	@Override
	public void add(int data) {
		if (!isFull()) {
			elements[currSize] = data;
			//Shift up logic:
			shiftUp(currSize);
			currSize++;
			/*for (int i = currSize; (i > 0 && elements[parent(i)] > elements[i]); i = parent(i))
				swap(parent(i), i);*/

		} else {
			System.out.println("Overflow: heap full!");
		}
	}

	// Time Complexity: O(logn)
	@Override
	public void set(int index, int newVal) {
		if (index < 0 || index >= currSize) {
			System.out.println("Index is invalid!");
		}

		if (newVal < elements[index]) {
			elements[index] = newVal;
			shiftUp(index);
		} else {
			elements[index] = newVal;
			shiftDown(index);
		}
	}

	// Search/Access element: Time Complexity: O(n)
	@Override
	public boolean contains(int data) {
		for (int i = 0; i < currSize; i++) {
			if (elements[i] == data) return true;
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return currSize <= 0;
	}

	// Time Complexity: O(logn)
	@Override
	public int poll() {
		if (isEmpty()) return -1;
		// Get the min element from 0th position
		Integer element = elements[0];
		if (currSize == 1) {
			elements[0] = null;
			currSize--;
		} else {
			elements[0] = elements[currSize - 1]; // assign last element in the heap
			elements[currSize - 1] = null;
			currSize--;
			shiftDown(0);
		}
		return element;
	}

	// Time Complexity:O(1)
	@Override
	public int peek() {
		return currSize > 0 ? elements[0] : null;
	}

	@Override
	public void print() {
		if (currSize > 0) {
			for (int i = 0; i < currSize; i++)
				System.out.print(elements[i] + " ");
		} else {
			System.out.println("Heap is Empty!");
		}
	}

	@Override
	public boolean remove(int data) {
		/*Steps: 
		1. Find the index of the data.
		2. Set max value in index and set() method, that will move the record to top.
		3. Invoke poll() method, that will delete the root value and rearrange the data.*/

		int index = findIndex(data);
		if (index != -1) {
			set(index, Integer.MIN_VALUE);
			poll();
		}
		return true;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int parent(int i) {
		return (i - 1) / 2;
	}

	@Override
	public int left(int i) {
		return (2 * i) + 1;
	}

	@Override
	public int right(int i) {
		return (2 * i) + 2;
	}

	@Override
	public void heapify(int i) {
		minHeapify(i);

		minHeapifyIterative(i);
	}

	private void minHeapifyIterative(int i) {
		int left, right, minIndex;
		while (i < currSize) {
			left = left(i);
			right = right(i);
			minIndex = i;

			if (left < currSize && elements[left] < elements[minIndex]) {
				minIndex = left;
			}
			if (right < currSize && elements[right] < elements[minIndex]) {
				minIndex = right;
			}
			if (i != minIndex) {
				swap(minIndex, i);
				i = minIndex;
			} else {
				break;
			}
		}
	}

	// Time Complexity: O(logn)
	private void minHeapify(int index) {
		int left = left(index);
		int right = right(index);
		int smallest = index;

		if (left < currSize && elements[left] < elements[smallest]) smallest = left;

		if (right < currSize && elements[right] < elements[smallest]) smallest = right;

		if (smallest != index) {
			swap(index, smallest);
			minHeapify(smallest);
		}
	}

	@Override
	public void shiftUp(int i) {
		while (i > 0 && elements[parent(i)] > elements[i]) {
			swap(i, parent(i));
			i = parent(i);
		}
	}

	@Override
	public void shiftDown(int i) {
		minHeapify(i);
	}

	private boolean isFull() {
		return (currSize == capacity) ? true : false;
	}

	private void swap(int pos1, int pos2) {
		int temp = elements[pos1];
		elements[pos1] = elements[pos2];
		elements[pos2] = temp;
	}

	//TODO: Check what this method does?
	// Time Complexity: O(logn)
	public void removeKey(int index) {
		if (index < currSize) {
			elements[index] = elements[currSize - 1];
			elements[currSize - 1] = null;
			currSize--;
			minHeapify(index);
		}
	}

	private int findIndex(int data) {
		for (int i = 0; i < currSize; i++) {
			if (elements[i] == data) return i;
		}
		return -1;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char ch;
		int input;
		MinBinaryHeap binaryHeap = new MinBinaryHeap(20);
		do {
			System.out.println("Minimum Binary Heap Operations:");
			System.out.println("1.Insert");
			System.out.println("2.Delete/extractMin");
			System.out.println("3.getMinimum");
			System.out.println("4.Decrease Key");
			System.out.println("5.Find/Search");
			System.out.print("Enter option:");
			input = in.nextInt();
			switch (input) {

			case 1:
				System.out.println("Enter no of elements to be inserted:");
				int t = in.nextInt();
				while (t-- > 0) binaryHeap.add(in.nextInt());

				System.out.println("Elements are inserted!");
				break;
			case 2:
				System.out.println("Deleted min Element in the heap: " + binaryHeap.poll());
				break;
			case 3:
				System.out.println("Min element present in Heap: " + binaryHeap.peek());
				break;
			case 4:
				System.out.println("Enter the index & decreased value:");
				binaryHeap.set(in.nextInt(), in.nextInt());
				break;
			case 5:
				System.out.println("Enter element needs to find: ");
				System.out.println("Element present in the heap? " + binaryHeap.contains(in.nextInt()));
				break;
			default:
				System.out.println("Please enter the valid option!!!");
				break;

			}
			System.out.println("\nDisplay:");
			binaryHeap.print();

			System.out.println("\nDo you want to continue(y/n):");
			ch = in.next().charAt(0);
		} while (ch == 'y' || ch == 'Y');
		System.out.println("****Thank You******");
		in.close();
	}

}

class PriorityQueue {

	int[] elements;
	int currSize;
	int maxSize;

	public PriorityQueue(int max) {
		this.maxSize = max;
		this.currSize = 0;
		elements = new int[max];
		Arrays.fill(elements, -1);
	}

	// Add the elements
	public void enQueue(int data) {
		if (currSize == maxSize) {
			System.out.println("Overflow: Queue is full.");
		} else {
			elements[currSize] = data;
			for (int i = currSize; i >= 0 && elements[i] < elements[parent(i)]; i = parent(i))
				swap(elements, i, parent(i));
		}
		currSize++;
	}

	// Remove the smallest element(Highest priority) in the queue
	public int deQueue() {
		int minElement = -1;
		if (!isEmpty()) {
			minElement = elements[0];
			if (currSize == 1) {
				elements[0] = -1;
				currSize--;
			} else {
				elements[0] = elements[currSize - 1];
				elements[currSize - 1] = -1;
				currSize--; // Current size should be reduced before invoking the heapify
				// Rebuild the heap data structure
				heapify(elements, 0);
			}
		}
		return minElement;
	}

	private boolean isEmpty() {
		return currSize == 0 ? true : false;
	}

	public int peek() {
		return isEmpty() ? -1 : elements[0];
	}

	public void display() {
		if (!isEmpty()) {
			for (int i = 0; i < currSize; i++)
				System.out.print(elements[i] + " ");
		}
	}

	/* Heapify - It is used to build the heap property array. It does from any element to leaf*/
	private void heapify(int[] a, int startIndex) {
		if (startIndex < currSize) {
			int leftChild = leftChild(startIndex);
			int rightChild = rightChild(startIndex);
			int minIndex = startIndex;

			if (leftChild < currSize && a[leftChild] < a[minIndex]) minIndex = leftChild;

			if (rightChild < currSize && a[rightChild] < a[minIndex]) minIndex = rightChild;

			if (startIndex != minIndex) {
				swap(elements, startIndex, minIndex);
				heapify(a, minIndex);
			}
		}
	}

	private int parent(int i) {
		return (i - 1) / 2;
	}

	private int leftChild(int i) {
		return (2 * i) + 1;
	}

	private int rightChild(int i) {
		return (2 * i) + 2;
	}

	private void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		char ch;
		int input;
		PriorityQueue queue = new PriorityQueue(10);
		do {
			System.out.println("Queue Operations:");
			System.out.println("1.EnQueue");
			System.out.println("2.Dequeue(highest Priority)");
			System.out.println("3.Peek");
			System.out.print("Enter option:");
			input = in.nextInt();
			switch (input) {
			case 1:
				System.out.println("Enter no of elements to be inserted:");
				int t = in.nextInt();
				while (t-- > 0) {
					queue.enQueue(in.nextInt());
				}
				System.out.println("Elements are inserted!");
				break;
			case 2:
				System.out.println("Dequeue operation: " + queue.deQueue());
				break;
			case 3:
				System.out.println("Top element in the Queue: " + queue.peek());
				break;
			default:
				System.out.println("Please enter the valid option!!!");
				break;
			}

			System.out.println("\nDisplay:");
			queue.display();

			System.out.println("\nDo you want to continue(y/n):");
			ch = in.next().charAt(0);
		} while (ch == 'y' || ch == 'Y');
		System.out.println("****Thank You******");
		in.close();
	}
}
