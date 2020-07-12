package com.consolidated.problems.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

public class StackProblems {
	/********************** Type1: Parse the expression/String *******************/
	//	Mini Parser - NestedInteger
	//	Nested List Weight Sum I, II - DFS/BFS

	/*
	 Conversion has below combinations:
	 	- Infix to Postfix, Infix to Prefix 
	 	- Prefix to Infix, Prefix to Postfix
	 	- Postfix to Prefix, Postfix to Infix 
	 */
	public static String infixToPostfix(String expr) {
		StringBuilder result = new StringBuilder();
		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < expr.length(); i++) {
			/*1.Scan the infix expression from left to right.*/
			char ch = expr.charAt(i);
			if (Character.isLetterOrDigit(ch)) {
				/*2.Operand: If the scanned character is an operand(letter or digit), output it.*/
				result.append(ch);

			} else if (ch == '(') {
				/*3.Open Parentheses: If the scanned character is an ‘(‘, push it to the stack.*/
				stack.push(ch);

			} else if (stack.isEmpty() || charPrecedence(ch) > charPrecedence(stack.peek())) {
				/*4.Operator(Higher Precedence): If the precedence of the scanned operator is greater than the precedence of the 
				 *  operator in the stack(or the stack is empty), push it.*/
				stack.push(ch);

			} else if (ch == ')') {
				/*5. Closed Parentheses: 
				 *   i.If the scanned character is an ‘)’, pop and output from the stack until an ‘(‘ is encountered.*/
				while (!stack.isEmpty() && stack.peek() != '(')
					result.append(stack.pop());
				// ii.Pop the closed parentheses
				if (!stack.isEmpty() && stack.peek() == '(') {
					stack.pop();
				} else {
					System.out.println("Invalid Expression");
					break;
				}
			} else {
				/*6.Operator(Lower Precedence): 
				 *  i. Pop the operator from the stack until the precedence of the scanned operator is less than equal to the precedence of
				 *     the operator residing on the top of the stack. Push the scanned operator to the stack.*/
				while (!stack.isEmpty() && charPrecedence(ch) <= charPrecedence(stack.peek()))
					result.append(stack.pop());

				/*  ii.Push the given the arithmetic symbol into the stack*/
				stack.push(ch);
			}
		}

		while (!stack.isEmpty())
			result.append(stack.pop());

		System.out.println("infixToPostfix: " + result);
		return result.toString();
	}

	public void infixToPrefix(String str) {
		StringBuilder builder1 = new StringBuilder(str);
		/*1.Reverse the infix expression i.e A+B*C will become C*B+A. Note while reversing 
		    each ‘(‘ will become ‘)’ and each ‘)’ becomes ‘(‘.*/
		builder1.reverse();
		// System.out.println("before change:" + builder1);
		for (int i = 0; i < builder1.length(); i++) {
			if (builder1.charAt(i) == '(')
				builder1.setCharAt(i, ')');
			else if (builder1.charAt(i) == ')')
				builder1.setCharAt(i, '(');
		}
		System.out.println("After change:" + builder1);
		// System.out.println("Infix-Prefix uses Infix-Postfix algorithm.....");

		/*2.Obtain the postfix expression of the modified expression i.e CB*A+.*/
		String postFixExpr = infixToPostfix(builder1.toString());

		/*3.Reverse the postfix expression. Hence in our example prefix is +A*BC.*/
		StringBuilder builder2 = new StringBuilder(postFixExpr);
		System.out.println("infixToPrefix:" + builder2.reverse());
	}

	public static void prefixToInfix(String expr) {
		Stack<String> stack = new Stack<>();

		for (int i = expr.length() - 1; i >= 0; i--) {
			char ch = expr.charAt(i);
			if (Character.isLetterOrDigit(ch)) {
				stack.push(String.valueOf(ch));
			} else {
				stack.push("(" + stack.pop() + ch + stack.pop() + ")");
			}
		}
		System.out.println("prefixToInfix:" + stack.pop());
	}

	public static void prefixToPostfix(String expr) {
		Stack<String> stack = new Stack<>();

		for (int i = expr.length() - 1; i >= 0; i--) {
			char ch = expr.charAt(i);
			if (Character.isLetterOrDigit(ch)) {
				stack.push(String.valueOf(ch));
			} else {
				stack.push(stack.pop() + stack.pop() + ch);
			}
		}
		System.out.println("prefixToPostfix:" + stack.pop());
	}

	public static void postfixToInfix(String expr) {
		Stack<String> stack = new Stack<>();

		for (int i = 0; i < expr.length(); i++) {
			char ch = expr.charAt(i);
			if (Character.isLetterOrDigit(ch)) {
				stack.push(String.valueOf(ch));
			} else {
				String val1 = stack.pop();
				String val2 = stack.pop();
				stack.push("(" + val2 + ch + val1 + ")");
			}
		}
		System.out.println("postfixToInfix:" + stack.pop());
	}

	public static void postfixToPrefix(String expr) {
		Stack<String> stack = new Stack<>();

		for (int i = 0; i < expr.length(); i++) {
			char ch = expr.charAt(i);
			if (Character.isLetterOrDigit(ch)) {
				stack.push(String.valueOf(ch));
			} else {
				String val1 = stack.pop();
				String val2 = stack.pop();
				stack.push(ch + val2 + val1);
			}
		}
		System.out.println("postfixToPrefix:" + stack.pop());
	}

	//	Decode String
	public String decodeString(String s) {
		String result = "";
		Stack<Integer> countStack = new Stack<>();
		Stack<String> valStack = new Stack<>();
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i))) {
				int start = i;
				while (Character.isDigit(s.charAt(i + 1)))
					i++;
				countStack.push(Integer.parseInt(s.substring(start, i + 1)));
			} else if (s.charAt(i) == '[') {
				valStack.push(result);
				result = "";
			} else if (s.charAt(i) == ']') {
				StringBuilder sb = new StringBuilder(valStack.pop());
				int repeatTimes = countStack.pop();
				while (repeatTimes-- > 0)
					sb.append(result);
				result = sb.toString();
			} else {
				result += s.charAt(i);
			}
		}
		return result;
	}

	//	Longest Absolute File Path - Array/Stack
	/*
	 * Note: For example, if s = "\t\t\tdirname", then s.lastIndexOf("\t") will be 2, the number of "\t" will be 3. 
	 * If a diretory name does not contain"\t", then s.lastIndexOf("\t") will be -1, the number of "\t" will be 0.
	 */
	// Approach1: Using Array
	public int lengthLongestPath(String input) {
		String[] files = input.split("\n");
		int[] stack = new int[files.length + 1];
		stack[0] = 0;
		int maxLength = 0;
		for (String str : files) {
			int level = str.lastIndexOf("\t") + 1;
			// (prev len) + (curr str length) - (\t len) + (1 for forward slash)
			int currLength = stack[level] + str.length() - level + 1;
			stack[level + 1] = currLength;
			// check if it is file and currLength-1 for removing slash
			if (str.contains("."))
				maxLength = Math.max(maxLength, currLength - 1);
		}

		return maxLength;
	}

	// Approach2: Using Stack
	public int lengthLongestPath2(String input) {
		Stack<Integer> stack = new Stack<>();
		stack.push(0); // "dummy" length
		int maxLen = 0;

		for (String s : input.split("\n")) {
			int lev = s.lastIndexOf("\t") + 1; // number of "\t"
			while (lev + 1 < stack.size())
				stack.pop(); // find parent
			int len = stack.peek() + s.length() - lev + 1; // remove "/t", add"/"
			stack.push(len);
			// check if it is file
			if (s.contains("."))
				maxLen = Math.max(maxLen, len - 1);
		}

		return maxLen;
	}

	//	Remove Duplicate Letters - Stack/Hashing
	/*
	 * Given a string which contains only lowercase letters, remove duplicate letters so that every letter appear once and 
	 * only once. You must make sure your result is the smallest in lexicographical order among all possible results.
	 * Example 1: Input: "bcabc", Output: "abc";
	 * Example 2: Input: "cbacdcbc", Output: "acdb";
	 */
	// Using Hashing, Time Complexity: O(n)
	public String removeDuplicateLetters1(String s) {
		int[] hash = new int[26];
		for (int i = 0; i < s.length(); i++)
			hash[s.charAt(i) - 'a']++;
		HashSet<Character> visited = new HashSet<>();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			hash[ch - 'a']--;
			if (visited.contains(ch))
				continue;
			while (result.length() > 0 && ch < result.charAt(result.length() - 1)
					&& hash[result.charAt(result.length() - 1) - 'a'] > 0) {
				visited.remove(result.charAt(result.length() - 1));
				result.deleteCharAt(result.length() - 1);
			}
			result.append(ch);
			visited.add(ch);
		}
		return result.toString();
	}

	// Using Stack, Time Complexity: O(n)
	public String removeDuplicateLetters(String s) {
		int[] hash = new int[26];
		for (int i = 0; i < s.length(); i++)
			hash[s.charAt(i) - 'a']++;
		boolean[] visited = new boolean[26];
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			hash[ch - 'a']--;
			if (visited[ch - 'a'])
				continue;
			while (!stack.isEmpty() && ch < stack.peek() && hash[stack.peek() - 'a'] > 0) {
				visited[stack.peek() - 'a'] = false;
				stack.pop();
			}
			stack.push(ch);
			visited[ch - 'a'] = true;
		}
		StringBuilder result = new StringBuilder();
		for (char ch : stack)
			result.append(ch);
		return result.toString();
	}

	/********************** Type2: Monotonic Stack Problems *******************/
	//Daily Temperatures
	public int[] dailyTemperatures(int[] T) {
		int[] result = new int[T.length];
		Stack<Integer> stack = new Stack<>();
		for (int i = T.length - 1; i >= 0; i--) {
			while (!stack.isEmpty() && T[stack.peek()] <= T[i]) {
				stack.pop();
			}
			result[i] = stack.isEmpty() ? 0 : stack.peek() - i;
			stack.push(i);
		}
		return result;
	}

	//Sum of Subarray Minimums
	//Approach1: Bruteforce Approach
	public int sumSubarrayMins1(int[] A) {
		if (A.length == 0)
			return 0;
		int M = 1000000007;
		int sum = 0, n = A.length, min = 0;
		for (int i = 0; i < n; i++) {
			min = Integer.MAX_VALUE;
			for (int j = i; j < n; j++) {
				min = Math.min(min, A[j]);
				sum = (sum + min) % M;
			}
		}

		return sum;
	}

	/* Using stack:
		intuition of the solution is,
		    result = sum(A[i] * f(i)) 
		    where f(i) is the number of subarrays, in which A[i] is the minimum.
		In below,
		    A[curr] minimum value at the curr index
		    (l-curr) -> No of subarrays in left side
		    (curr-r) -> No of subarrays in right side
	*/
	public int sumSubarrayMins(int[] A) {
		if (A.length == 0)
			return 0;

		int M = 1000000007;
		int sum = 0, n = A.length, l = 0, curr = 0;
		Stack<Integer> stack = new Stack<>();

		for (int r = 0; r <= n; r++) {
			while (!stack.isEmpty() && (r == n || A[stack.peek()] >= A[r])) {
				curr = stack.pop();
				l = stack.isEmpty() ? -1 : stack.peek();
				sum = (sum + A[curr] * (l - curr) * (curr - r)) % M;
			}
			stack.push(r);
		}

		return sum;
	}

	/*The stock span problem is a financial problem where we have a series of n daily price quotes for a stock and we need to calculate span of 
	 * stock’s price for all n days. 
	 * The span Si of the stock’s price on a given day i is defined as the maximum number of consecutive days just before the given day, for 
	 * which the price of the stock on the current day is less than or equal to its price on the given day.
	 */
	// Brute force method
	public void stockSpan1(int[] prices) {
		int[] span = new int[prices.length];
		Arrays.fill(span, 1);
		for (int i = 1; i < prices.length; i++) {
			for (int j = i - 1; j >= 0 && prices[j] < prices[i]; j--) {
				span[i]++;
			}
		}
		System.out.println("Prices: " + Arrays.toString(prices));
		System.out.println("Span: " + Arrays.toString(span));
	}

	// Approach2: Using stack; Linear time approach
	public void stockSpan2(int[] prices) {
		int[] span = new int[prices.length];
		Stack<Integer> stack = new Stack<>();

		stack.push(0); // Add the price index in stack
		span[0] = 1; // First index should be one

		// Calculate span values for rest of the elements
		for (int i = 1; i < prices.length; i++) {

			/*Pop elements from stack while stack is not empty and top of stack is smaller than price[i]*/
			while (!stack.isEmpty() && prices[stack.peek()] < prices[i])
				stack.pop();

			/*If stack becomes empty, then price[i] is greater than all elements on left of it, i.e., price[0], price[1],..price[i-1]. 
			  Else price[i] is greater than elements after top of stack*/
			span[i] = stack.isEmpty() ? i + 1 : i - stack.peek();

			// Push the index into the stack
			stack.push(i);

		}
		System.out.println("Prices: " + Arrays.toString(prices));
		System.out.println("Span: " + Arrays.toString(span));
	}

	/********************** Type3: Stack design problems *******************/
	//	Implement Stack using Queues
	//	Sort Stack - Recursion/Temp Task
	//	Three in One - MultiStack
	//	Stack of Plates

	//	Implement Queue using Stacks/Queue via Stacks 
	Stack<Integer> s1 = new Stack<Integer>();
	Stack<Integer> s2 = new Stack<Integer>();

	// Queue using 2 Stack:Insert
	public Stack<Integer> enqueue(int data) {
		s1.push(data); // Insert the data into s1
		return s1;
	}

	// Queue using 2 Stack:Remove
	public int dequeue() {
		shiftStacks();
		return s2.isEmpty() ? -1 : s2.pop();
	}

	public int peek() {
		shiftStacks();
		return s2.isEmpty() ? -1 : s2.peek();
	}

	// Move values from s1 to s2
	public void shiftStacks() {
		if (s2.isEmpty()) {
			while (!s1.isEmpty())
				s2.push(s1.pop());
		}
	}

	public void displayQueue() {
		display(s2);
		display(s1);
	}

	public void display(Stack<Integer> stack) {
		ListIterator<Integer> listIterator = stack.listIterator();
		while (listIterator.hasNext())
			System.out.print(listIterator.next() + " ");
	}

	/***************** Operations on Stack *****************/
	// Reverse a stack using recursion - start
	public void reverseStack(Stack<Integer> stack) {
		System.out.println("Before reverse:");
		display(stack);
		reverseStackUtil(stack);
		System.out.println("\nAfter Reverse:");
		display(stack);
	}

	/*  Time Complexity to reverse the stack: 
	 *     insertAtBottom - O(n)
	 *     reverseStackUtil - O(n) * O(n) -> O(n^2)
	 *     OverAll Time Complexity: O(n^2) 
	 *     Space Complexity to reverse the stack: 
	 *     insertAtBottom - O(n)
	 *     reverseStackUtil - O(n) + O(n) -> O(2n)
	 *     OverAll Time Complexity: O(n) 
	 */
	private void reverseStackUtil(Stack<Integer> stack) {
		if (stack.isEmpty())
			return;
		int element = stack.pop();
		// Recursive call to make empty stack
		reverseStackUtil(stack);
		// Send element one by one and set into stack
		insertAtBottom(stack, element);
	}

	private void insertAtBottom(Stack<Integer> stack, int element) {
		if (stack.isEmpty()) {
			stack.push(element);
		} else {
			/*Keep popping the element till stack empty and then insert the top element in the bottom*/
			int data = stack.pop();
			insertAtBottom(stack, element);
			stack.push(data);
		}
	}
	// Reverse a stack using recursion - end

	// Its similar to reverse the stack using recursion
	public void sortStack(Stack<Integer> stack) {
		System.out.println("Before Sort:");
		stack.stream().forEach(k -> System.out.print(k + " "));
		sort(stack);
		System.out.println("\nAfter Sort:");
		stack.stream().forEach(k -> System.out.print(k + " "));
	}

	private void sort(Stack<Integer> stack) {
		if (stack.isEmpty())
			return;
		int element = stack.pop();
		// Recursive call to remove all the element from the stack
		sort(stack);
		// Arrange element increasing order one by one
		sort(stack, element);
	}

	private void sort(Stack<Integer> stack, int element) {
		if (stack.isEmpty() || element < stack.peek()) {
			stack.push(element);
		} else {
			/*Keep popping the element till element greater than element in the stack and then insert in ascending order in stack*/
			int data = stack.pop();
			sort(stack, element);

			stack.push(data);
		}
	}

	// Sort a stack using a temporary stack
	public void sortStackUsingTempStack(Stack<Integer> stack) {
		System.out.println("Before Sort:");
		stack.stream().forEach(k -> System.out.print(k + " "));

		Stack<Integer> tempStack = new Stack<>();
		while (!stack.isEmpty()) {
			int next = stack.pop();
			// while temporary stack is not empty and top of stack is greater than next
			while (!tempStack.isEmpty() && tempStack.peek() > next) {
				// Pop from temp stack and push into input stack
				stack.push(tempStack.pop());
			}
			tempStack.push(next);
		}

		// Copy the elements back to original stack
		while (!tempStack.isEmpty())
			stack.push(tempStack.pop());

		System.out.println("\nAfter Sort:");
		stack.stream().forEach(k -> System.out.print(k + " "));
	}

	// Delete middle element of a stack
	public void deleteMiddleElementInStack(Stack<Integer> stack) {
		System.out.println("Before removing the middle element:");
		stack.stream().forEach(k -> System.out.print(k + " "));

		deleteMiddleElement(stack, stack.size() / 2, 0);

		System.out.println("\nAfter removing the middle element:");
		stack.stream().forEach(k -> System.out.print(k + " "));
	}

	private void deleteMiddleElement(Stack<Integer> stack, int mid, int curr) {
		if (stack.isEmpty()) {
			return;
		} else if (curr == mid) {
			stack.pop();
			return;
		}
		int data = stack.pop();
		deleteMiddleElement(stack, mid, curr + 1);
		stack.push(data);
	}

	/********************** Util Methods ********************/
	private static int charPrecedence(char ch) {
		switch (ch) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		case '^':
			return 3;
		default:
			return -1;
		}
	}

	/***************************** Other Problems ******************************************/
	// Complete the freqQuery function below.
	static List<Integer> freqQuery(List<List<Integer>> queries) {
		Map<Integer, Integer> map = new HashMap<>();
		Map<Integer, List<Integer>> countMap = new HashMap<>();
		List<Integer> result = new ArrayList<>();
		for (List<Integer> query : queries) {
			int opt = query.get(0), val = query.get(1);
			if (opt == 1) {
				map.put(val, map.getOrDefault(val, 0) + 1);
				int count = map.get(val);
				// Update the countMap
				if (countMap.get(count) != null) {
					if (countMap.get(count).size() == 1)
						countMap.remove(count);
					else
						countMap.get(count).remove((Integer) val);
				}
				// Add the value
				if (!countMap.containsKey(count))
					countMap.put(count, new ArrayList<>());
				countMap.get(count).add(val);
			} else if (opt == 2) {
				if (!map.containsKey(val))
					continue;
				int count = map.get(val);
				// Update the map
				if (map.get(val) == 1)
					map.remove(val);
				else
					map.put(val, map.get(val) - 1);
				// Update the countMap
				if (countMap.get(count).size() == 1)
					countMap.remove(count);
				else
					countMap.get(count).remove((Integer) val);
			} else {
				if (countMap.containsKey(val))
					result.add(1);
				else
					result.add(0);
			}
		}
		return result;
	}

	public void towersOfHanoi(int n) {
		// Create 3 empty stacks
		Stack<Integer> sourceStack = new Stack<>(), destStack = new Stack<>(), auxStack = new Stack<>();
		// MAx no of moves
		int totalMoves = (int) Math.pow(2, n) - 1;
		char s = 'S', d = 'D', a = 'A';

		// If number of disks is even, then interchange destination pole and auxiliary pole
		if (n % 2 == 0) {
			char temp = d;
			d = a;
			a = temp;
		}

		// Insert all the disk(Number) from last to first
		for (int i = n; i > 0; i--)
			sourceStack.push(i);

		// Iterate disks(elements) one by one and move b/w poles S, D & A.
		for (int i = 1; i <= totalMoves; i++) {
			if (i % 3 == 1) {
				moveDisks1(sourceStack, destStack, s, d); // S <-> D
			} else if (i % 3 == 2) {
				moveDisks1(sourceStack, auxStack, s, a); // S <-> A
			} else if (i % 3 == 0) {
				moveDisks1(destStack, auxStack, d, a); // D <-> A
			}
		}
	}

	private void moveDisks1(Stack<Integer> sourceStack, Stack<Integer> destStack, char source, char dest) {
		if (sourceStack.isEmpty() || (!destStack.empty() && sourceStack.peek() > destStack.peek())) {
			sourceStack.push(destStack.pop());
			displayMove(dest, source, sourceStack.peek());
		} else {
			destStack.push(sourceStack.pop());
			displayMove(source, dest, destStack.peek());
		}
	}

	private void displayMove(char s, char d, int disk) {
		System.out.println("Disk: " + disk + " from " + s + " destination " + d);
	}

	private void moveDisks2(Stack<Integer> sourceStack, Stack<Integer> destStack, char source, char dest) {
		int topElementInSource = sourceStack.isEmpty() ? 0 : sourceStack.pop();
		int topElementInDest = destStack.isEmpty() ? 0 : destStack.pop();

		if (topElementInSource == 0) {
			sourceStack.push(topElementInDest);
			displayMove(dest, source, topElementInDest);
		} else if (topElementInDest == 0) {
			destStack.push(topElementInSource);
			displayMove(source, dest, topElementInSource);
		} else if (topElementInSource < topElementInDest) {
			destStack.push(topElementInDest);
			destStack.push(topElementInSource);
			displayMove(source, dest, topElementInSource);
		} else {
			sourceStack.push(topElementInSource);
			sourceStack.push(topElementInDest);
			displayMove(dest, source, topElementInDest);
		}
	}

}