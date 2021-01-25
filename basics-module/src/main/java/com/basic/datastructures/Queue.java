package com.basic.datastructures;

import java.util.Arrays;

import com.basic.datastructures.operations.QueueOperations;
import com.common.model.ListNode;

/**
 * Queue Implementation using Array & Linked List
 */

public class Queue {
	public static void main(String[] args) {
		System.out.println("Queue Operations: ");
		Queue ob = new Queue();

		System.out.println("Queue - Array Impl: ");
		ob.testQueueArrayImpl();

		System.out.println("Queue - LinkedList Impl: ");
		ob.testQueueLinkedListImpl();
	}

	public void testQueueArrayImpl() {
		QueueArrayImpl queue = new QueueArrayImpl(5);
		System.out.println("Stack Operations:");
		System.out.println("EnQueue");
		queue.add(1);
		queue.add(2);
		queue.add(3);
		System.out.println("Display:");
		queue.print();
		System.out.println("\nDequeue: " + queue.poll());
		System.out.println("Front: " + queue.peek());
		System.out.println("Rear: " + queue.getRear());
	}

	public void testQueueLinkedListImpl() {
		QueueSLinkedListImpl queue = new QueueSLinkedListImpl();
		System.out.println("Stack Operations:");
		System.out.println("EnQueue");
		queue.add(1);
		queue.add(2);
		queue.add(3);
		System.out.println("Display:");
		queue.print();
		System.out.println("\nDequeue: " + queue.poll());
		System.out.println("Front: " + queue.peek());
		System.out.println("Rear: " + queue.getRear());
	}

}

class QueueArrayImpl implements QueueOperations {
	int[] queue;
	int front, rear;
	int maxSize;

	public QueueArrayImpl(int size) {
		this.maxSize = size;
		this.front = this.rear = -1;
		this.queue = new int[maxSize];
		Arrays.fill(queue, -1);
	}

	//Add the element in the rear end
	@Override
	public void add(int data) {
		if (isFull()) return;
		if (isEmpty()) // When Queue is empty; both front & rear will be increased zero.
			front++;
		queue[++rear] = data; // Normal case: Simply insert the data in the rear end
	}

	@Override
	public int poll() {
		if (isEmpty()) return -1;
		int element = queue[front];
		if (front == rear) front = rear = -1;
		else front++;
		return element;
	}

	@Override
	public boolean isEmpty() {
		return front == -1;
	}

	private boolean isFull() {
		return rear == maxSize - 1;
	}

	@Override
	public int peek() {
		return isEmpty() ? -1 : queue[front];
	}

	public int getRear() {
		return isEmpty() ? -1 : queue[rear];
	}

	@Override
	public void print() {
		if (isEmpty()) return;

		for (int i = front; i <= rear; i++)
			System.out.print(queue[i] + " ");
	}

	@Override
	public void set(int index, int data) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(int data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(int data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		return 0;
	}

}

class QueueSLinkedListImpl implements QueueOperations {

	ListNode front, rear;

	@Override
	public void add(int data) {
		ListNode newNode = new ListNode(data);
		if (isEmpty()) {
			front = rear = newNode;
		} else {
			rear.next = newNode;
			rear = rear.next; //or rear = newNode
		}
	}

	@Override
	public int poll() {
		if (isEmpty()) return -1;
		int data = front.data;
		if (front == rear) {
			front = rear = null;
		} else {
			front = front.next;
		}
		return data;
	}

	@Override
	public int peek() {
		return !isEmpty() ? front.data : -1;
	}

	public int getRear() {
		return !isEmpty() ? rear.data : -1;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return (front == null && rear == null);
	}

	@Override
	public void set(int index, int data) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(int data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(int data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void print() {
		if (isEmpty()) return;
		ListNode curr = front;
		while (curr != null) {
			System.out.print(curr.data + " ");
			curr = curr.next;
		}
	}
}

class QueueDLinkedListImpl {
	/*
	 * Queue can be implemented using DLL, but it doesnt give much improvement when comparing to SLL.
	 * Because Queue implementation using SLL gives O(1) time for all the operations.
	 */
}