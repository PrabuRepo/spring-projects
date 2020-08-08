package com.basic.datastructures;

import com.basic.datastructures.operations.DLLOperations;
import com.basic.datastructures.operations.SLLOperations;
import com.common.model.ListNode;

/*
 * Linked List Implementation: 
 *   1. Singly Linked List
 *   2. Doubly Linked List
 *   3. Circular Singly Linked List
 *   
 *  Note: Consider below for all the LL operations:
 *  	1.Front End
 *  	2.Tail End
 *  	3.Middle
 */
public class LinkedList {
	public static void main(String[] args) {
		System.out.println("Linked List Operations:");
		LinkedList ob = new LinkedList();

		System.out.println("1. Singly Linked List");
		ob.testSinglyLinkedList();

		System.out.println("\n2. Doubly Linked List");
		ob.testDoublyLinkedList();

		System.out.println("\n3. Circular Singly Linked List");
		ob.testCircularSLinkedList();
	}

	public void testSinglyLinkedList() {
		SinglyLinkedList list = new SinglyLinkedList();
		System.out.println("Insert:");
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(5);
		list.add(7);
		System.out.println("\nDisplay:");
		list.print();
		list.insert(4, 4);
		list.insert(7, 8);
		System.out.println("\nDisplay:");
		list.print();
		System.out.println("\nDelete: " + list.remove(1));
		System.out.println("Find: " + list.contains(3));
	}

	public void testDoublyLinkedList() {
		DoublyLinkedList list = new DoublyLinkedList();
		System.out.println("Insert");
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println("Display:");
		list.print();
		System.out.println("\nDelete: ");
		list.remove(1);
		System.out.println("Find: " + list.contains(3));
	}

	public void testCircularSLinkedList() {
		CircularSLinkedList list = new CircularSLinkedList();
		System.out.println("Insert");
		list.add(1);
		list.add(2);
		list.add(3);
		System.out.println("Display:");
		list.print();
		System.out.println("\nDelete: ");
		list.remove(1);
		System.out.println("Find: " + list.contains(3));
	}
}

class SinglyLinkedList implements SLLOperations {
	ListNode head;

	@Override
	public void add(int data) {
		ListNode newNode = new ListNode(data);
		if (head == null) {
			head = newNode;
		} else {
			ListNode curr = head;
			while (curr.next != null)
				curr = curr.next;
			curr.next = newNode;
		}
	}

	@Override
	public void set(int index, int data) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(int data) {
		return get(data) != null ? true : false;
	}

	@Override
	public boolean remove(int data) {
		if (isEmpty())
			return false;

		if (head.data == data) {
			head = head.next;
			return true;
		} else {
			ListNode curr = head;
			while (curr != null && curr.next != null) {
				if (curr.next.data == data) {
					curr.next = curr.next.next;
					return true;
				} else {
					curr = curr.next;
				}
			}
		}
		return false;
	}

	@Override
	public int size() {
		if (isEmpty())
			return 0;
		int count = 0;
		ListNode curr = head;
		while (curr != null) {
			count++;
			curr = curr.next;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return head == null ? true : false;
	}

	@Override
	public void insert(int index, int data) {
		ListNode curr = head;
		while (--index > 1)// Move to prev node
			curr = curr.next;

		ListNode newNode = new ListNode(data);
		if (curr != null) {
			newNode.next = curr.next;
			curr.next = newNode;
		}
	}

	@Override
	public ListNode get(int data) {
		if (isEmpty())
			return null;

		ListNode curr = head;
		while (curr != null) {
			if (curr.data == data)
				return curr;
			curr = curr.next;
		}
		return null;
	}

	@Override
	public void print() {
		ListNode curr = head;
		while (curr != null) {
			System.out.print(curr.data + " ");
			curr = curr.next;
		}
	}

}

class DoublyLinkedList implements DLLOperations {

	ListNode head;

	@Override
	public void add(int data) {
		ListNode newNode = new ListNode(data);
		if (head == null) {
			head = newNode;
		} else {
			ListNode curr = head;
			while (curr.next != null)
				curr = curr.next;
			curr.next = newNode;
			newNode.prev = curr;
		}
	}

	@Override
	public void set(int index, int data) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean contains(int data) {
		return get(data) != null ? true : false;
	}

	@Override
	public boolean remove(int data) {
		ListNode delNode = get(data);

		if (delNode == null)
			return false;

		if (head == delNode)
			head = head.next;

		if (delNode.prev != null)
			delNode.prev.next = delNode.next;

		if (delNode.next != null)
			delNode.next.prev = delNode.prev;

		return true;
	}

	@Override
	public int size() {
		if (isEmpty())
			return 0;
		int count = 0;
		ListNode curr = head;
		while (curr != null) {
			count++;
			curr = curr.next;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return head == null ? true : false;
	}

	@Override
	public void insert(int index, int data) {
		// TODO Auto-generated method stub
	}

	@Override
	public void insertBefore(int data, int newData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertAfter(int data, int newData) {
		// TODO Auto-generated method stub

	}

	@Override
	public ListNode get(int data) {
		if (isEmpty())
			return null;

		ListNode curr = head;
		while (curr != null) {
			if (curr.data == data)
				return curr;
			curr = curr.next;
		}
		return null;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}
}

/*
 * Circular Linked List can be implemented using,
 * 	1.SLL
 *  2.DLL
 */
//1.Circular (Singly) Linked List Implementation:  using only one pointer 'next'
class CircularSLinkedList implements SLLOperations {
	ListNode head;

	@Override
	public void add(int data) {
		ListNode newNode = new ListNode(data);
		if (head == null) {
			head = newNode;
			head.next = head;
		} else {
			ListNode curr = head;
			do {
				if (curr.next == head) {
					curr.next = newNode;
					newNode.next = head;
					break;
				} else {
					curr = curr.next;
				}
			} while (curr != head);
		}
	}

	@Override
	public void set(int index, int data) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(int data) {
		return get(data) != null ? true : false;
	}

	@Override
	public boolean remove(int data) {
		if (isEmpty())
			return false;

		ListNode curr = head;
		if (curr.data == data && curr.next == head) {
			head = null;
		} else if (curr.data == data) {
			curr.data = curr.next.data;
			curr.next = curr.next.next;
		} else {
			do {
				if (curr.next.data == data) {
					curr.next = curr.next.next;
					return true;
				} else {
					curr = curr.next;
				}
			} while (curr != head);
		}
		return false;
	}

	@Override
	public int size() {
		if (isEmpty())
			return 0;

		ListNode curr = head;
		int count = 0;
		if (curr != null) {
			do {
				count++;
				curr = curr.next;
			} while (curr != head);
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return head == null ? true : false;
	}

	@Override
	public void insert(int index, int data) {
		// TODO Auto-generated method stub

	}

	@Override
	public ListNode get(int data) {
		ListNode curr = head;
		if (curr != null) {
			do {
				if (curr.data == data)
					return curr;
				curr = curr.next;
			} while (curr != head);
		}
		return null;
	}

	@Override
	public void print() {
		if (isEmpty())
			return;

		ListNode curr = head;
		do {
			System.out.print(curr.data + " ");
			curr = curr.next;
		} while (curr != head);
	}
}

//2.Circular (Doubly) Linked List Implementation - using two pointers front, rear
class CircularDLinkedList implements DLLOperations {

	ListNode head;

	@Override
	public void add(int data) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void insert(int index, int data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertBefore(int data, int newData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertAfter(int data, int newData) {
		// TODO Auto-generated method stub

	}

	@Override
	public ListNode get(int data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}
}
