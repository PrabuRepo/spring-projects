package com.problems.patterns.ds;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.common.utilities.Utils;

public class StackPatterns {

	/********************** Type1: Parse the expression/String *******************/
	// Evaluate Post Fix Expression or Polish Notation
	public void evaluatePosfixExpression(String expr) {
		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < expr.length(); i++) { // Scan the given expression and do following for every scanned
													// element.
			char ch = expr.charAt(i);
			if (Character.isDigit(ch)) { // If the element is a number, push it into the stack
				stack.push(Character.getNumericValue(ch));
				// stack.push(Integer.valueOf(ch - '0'));
				// System.out.println(Integer.valueOf('0')); //48
			} else { /*If the element is a operator, pop operands for the operator from stack. Evaluate the operator and push the result back to the stack*/
				int val1 = stack.pop();
				int val2 = stack.pop();
				int result = 0;
				switch (ch) {
				case '+':
					result = val2 + val1;
					break;
				case '-':
					result = val2 - val1;
					break;
				case '*':
					result = val2 * val1;
					break;
				case '/':
					result = val2 / val1;
					break;
				case '^':
					result = pow(val2, val1);
					break;
				}
				stack.push(result);
			}
		}
		System.out.println("Value:" + stack.pop());
	}

	private static int pow(int a, int b) {
		if (b == 1)
			return a;
		return a * pow(a, b - 1);
	}

	//TODO:  Compare this solution with Post Fix Evaluation
	// Evaluate Reverse Polish/Postfix Notation
	public int evalRPN(String[] tokens) {
		Stack<Integer> stack = new Stack<>();
		String str = null;
		int val1 = 0, val2 = 0;
		for (int i = 0; i < tokens.length; i++) {
			str = tokens[i];
			if (str.equals("/") || str.equals("-") || str.equals("*") || str.equals("+")) {
				val2 = stack.pop();
				val1 = stack.pop();
				stack.push(arithmeticOperation(str.charAt(0), val1, val2));
			} else {
				stack.push(Integer.valueOf(str));
			}
		}
		return (!stack.isEmpty() && stack.size() == 1) ? stack.pop() : 0;
	}

	// Without using stack
	int i;

	public int evalRPN2(String[] tokens) {
		i = tokens.length - 1;
		return eval(tokens);
	}

	int eval(String[] ss) {
		switch (ss[i--]) {
		case "+":
			return eval(ss) + eval(ss);
		case "-":
			return -eval(ss) + eval(ss);
		case "*":
			return eval(ss) * eval(ss);
		case "/":
			return (int) (1.0 / eval(ss) * eval(ss));
		default:
			return Integer.valueOf(ss[i + 1]);
		}
	}

	public boolean isValid(String s) {
		Stack<Character> stack = new Stack<>();
		char ch;
		for (int i = 0; i < s.length(); i++) {
			ch = s.charAt(i);
			if (ch == '(' || ch == '{' || ch == '[') {
				stack.push(ch);
			} else {
				if (!stack.isEmpty() && ((ch == ')' && stack.peek() == '(') || (ch == ']' && stack.peek() == '[')
						|| (ch == '}' && stack.peek() == '{')))
					stack.pop();
				else
					return false;
			}
		}

		return stack.isEmpty();
	}

	//Evaluate infix expression/Basic Calculator I, II, III
	/*Evaluate Infix Expression:
	 *  - Basic Calculator I
	 *  - Basic Calculator II
	 *  - Basic Calculator III
	 */

	/* Basic Calculator I: Implement a basic calculator to evaluate a simple expression string. The expression string may
	 * contain open ( and closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces .
	 * Input: "1 + 1" Output: 2 
	 * Input: " 2-1 + 2 " Output: 3
	 */
	public int calculator1(String s) {
		int n = s.length(), sign = 1, result = 0;
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < n; i++) {
			char ch = s.charAt(i);
			if (Character.isDigit(ch)) {
				int digit = (int) ch - '0';
				while (i + 1 < n && Character.isDigit(s.charAt(i + 1))) { // Collect all the digits from the input
					digit = digit * 10 + s.charAt(i + 1) - '0';
					i++;
				}
				result += digit * sign;
			} else if (ch == '+') {
				sign = 1;
			} else if (ch == '-') {
				sign = -1;
			} else if (ch == '(') {
				stack.push(result);
				stack.push(sign);
				result = 0;
				sign = 1;
			} else if (ch == ')') {
				result = result * stack.pop() + stack.pop(); // 1st pops the sign, 2nd pops the prev calculation
			}
		}
		return result;
	}

	/*
	 * Basic Calculator II:
	 * Implement a basic calculator to evaluate a simple expression string. The expression string contains only non-negative
	 * integers, +, -, *, / operators and empty spaces . The integer division should truncate toward zero.
	 * Input: "3+2*2" Output: 7 
	 * Input: " 3/2 " Output: 1 
	 * Input: " 3+5 / 2 " Output: 5
	 */

	// Approach1: Using Stack
	public int calculator21(String s) {
		int n = s.length(), result = 0, num = 0;
		Stack<Integer> stack = new Stack<>();
		char oper = '+';
		for (int i = 0; i < n; i++) {
			char ch = s.charAt(i);
			if (ch == ' ') {
				continue;
			} else if (Character.isDigit(ch)) {
				num = (int) ch - '0';
				while (i + 1 < n && Character.isDigit(s.charAt(i + 1))) { // Collect all the digits from the input
					num = num * 10 + s.charAt(i + 1) - '0';
					i++;
				}
				if (oper == '+')
					stack.push(num);
				else if (oper == '-')
					stack.push(-num);
				else if (oper == '*')
					stack.push(stack.pop() * num);
				else if (oper == '/')
					stack.push(stack.pop() / num);
			} else {
				oper = ch;
				result = 0;
			}
		}

		while (!stack.isEmpty())
			result += stack.pop();
		return result;
	}

	// Approach2: Without using Stack
	public int calculator22(String s) {
		int n = s.length(), result = 0, num = 0, prevVal = 0;
		char oper = '+';
		for (int i = 0; i < n; i++) {
			char ch = s.charAt(i);
			if (ch == ' ') {
				continue;
			} else if (Character.isDigit(ch)) {
				num = (int) ch - '0';
				while (i + 1 < n && Character.isDigit(s.charAt(i + 1))) { // Collect all the digits from the input
					num = num * 10 + s.charAt(i + 1) - '0';
					i++;
				}
				if (oper == '+') {
					result += prevVal;
					prevVal = num;
				} else if (oper == '-') {
					result += prevVal;
					prevVal = -num;
				} else if (oper == '*') {
					prevVal = prevVal * num;
				} else if (oper == '/') {
					prevVal = prevVal / num;
				}
			} else {
				oper = ch;
			}
		}

		return result + prevVal;
	}

	/*Basic Calculator III:
	 * Implement a basic calculator to evaluate a simple expression string. The expression string may contain open ( and
	 * closing parentheses ), the plus + or minus sign -, non-negative integers and empty spaces . The expression string
	 * contains only non-negative integers, +, -, *, / operators , open ( and closing parentheses ) and empty spaces . The
	 * integer division should truncate toward zero.
	 * "1 + 1" = 2
	 * " 6-4 / 2 " = 4
	 * "2*(5+5*2)/3+(6/2+8)" = 21
	 */
	public int calculator3(String s) {
		Stack<Character> operStack = new Stack<>();
		Stack<Integer> valStack = new Stack<>();
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char ch = s.charAt(i);
			if (ch == ' ')
				continue;
			else if (Character.isDigit(ch)) {
				int start = i;
				while (i + 1 < n && Character.isDigit(s.charAt(i + 1)))
					i++;
				valStack.push(Integer.valueOf(s.substring(start, i + 1)));
			} else if (ch == '(') {
				operStack.push('(');
			} else if (ch == ')') {
				while (operStack.peek() != '(')
					valStack.push(arithmeticOperation(operStack.pop(), valStack.pop(), valStack.pop()));
				operStack.pop();
			} else {
				while (!operStack.isEmpty() && (operStack.peek() == '*' || operStack.peek() == '/'))
					valStack.push(arithmeticOperation(operStack.pop(), valStack.pop(), valStack.pop()));
				operStack.push(ch);
			}
		}

		while (!operStack.isEmpty())
			valStack.push(arithmeticOperation(operStack.pop(), valStack.pop(), valStack.pop()));

		return valStack.pop();
	}

	private int arithmeticOperation(char ch, int b, int a) {
		int result = 0;
		switch (ch) {
		case '+':
			result = a + b;
			break;
		case '-':
			result = a - b;
			break;
		case '*':
			result = a * b;
			break;
		case '/':
			result = a / b;
			break;
		}
		return result;
	}

	//Simplify Path
	public String simplifyPath(String path) {
		Stack<String> stack = new Stack<>();

		for (String str : path.split("/")) {
			if (!stack.isEmpty() && str.equals(".."))
				stack.pop();
			else if (!str.equals("") && !str.equals(".") && !str.equals(".."))
				stack.push(str);
		}

		StringBuilder result = new StringBuilder();
		for (String str : stack)
			result.append("/").append(str);

		return stack.isEmpty() ? "/" : result.toString();
	}

	/********************** Type2: Monotonic Stack problems *******************/
	/*
	 * 	Monotonic stack is actually a stack. It just uses some ingenious logic to keep the elements in the stack orderly
	 * (monotone increasing or monotone decreasing) after each new element putting into the stack. Well,sounds like a heap?
	 * No, monotonic stack is not widely used. It only deals with one typical problem, which is called Next Greater Element
	 * The monotonically increasing stack can find the first element from the left that is smaller than the current number.
	 * The monotonically decreasing stack can find the first element from the left that is larger than the current number.
	 * The monotonous stack mainly answers several questions like this.
	 * 		The next element larger than the current element
	 * 		The previous element larger than the current element
	 * 		The next element smaller than the current element
	 * 		The previous element smaller than the current element
	 * Tips: You can store indexes in the stack, or you can directly store elements
	 */

	//monotonous increasing stack: elements in the monotonous increase stack keeps an increasing order.
	public void monotonicIncreasingStack(int[] arr) {
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) {
			while (!stack.empty() && stack.peek() > arr[i]) {
				stack.pop();
			}
			stack.push(arr[i]);
		}
	}

	//monotonous decreasing stack: elements in the monotonous decrease stack keeps an decreasing order.
	public void monotonicDecreasingStack(int[] arr) {
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) {
			while (!stack.empty() && stack.peek() < arr[i]) {
				stack.pop();
			}
			stack.push(arr[i]);
		}
	}

	//Next Greater Element I
	// Approach1: BruteForce Approach -> Time Complexity: O(n^2)
	public static void nextGreaterElementI1(int[] a) {
		int next;
		for (int i = 0; i < a.length; i++) {
			next = -1;
			for (int j = i + 1; j < a.length; j++) {
				if (a[i] < a[j]) {
					next = a[j];
					break;
				}
			}
			System.out.println(a[i] + " -- " + next);
		}
	}

	// Approach2: Using Stack; Time Complexity: O(n), with additional stack space
	//Loop once, we can get the Next Greater Number of a normal array.
	public int[] nextGreaterElementI2(int[] nums1, int[] nums2) {
		if (nums2.length == 0 || nums1.length == 0)
			return new int[0];

		int[] result = new int[nums1.length];
		Stack<Integer> stack = new Stack<>();
		Map<Integer, Integer> map = new HashMap<>();

		for (int i = 0; i < nums2.length; i++) {
			while (!stack.isEmpty() && stack.peek() < nums2[i])
				map.put(stack.pop(), nums2[i]);
			stack.push(nums2[i]);
		}

		/*while (!stack.isEmpty())
			map.put(stack.pop(), -1);*/

		for (int i = 0; i < nums1.length; i++)
			result[i] = map.getOrDefault(nums1[i], -1);

		return result;
	}

	//Next Greater Element II: Time-O(n), Space-O(n)
	public int[] nextGreaterElementsII(int[] nums) {
		if (nums.length == 0)
			return new int[0];

		Stack<Integer> stack = new Stack<>();
		int n = nums.length;
		for (int i = n - 1; i >= 0; i--) {
			stack.push(nums[i]);
		}

		int[] result = new int[n];
		for (int i = n - 1; i >= 0; i--) {
			while (!stack.isEmpty() && stack.peek() <= nums[i]) {
				stack.pop();
			}
			result[i] = stack.isEmpty() ? -1 : stack.peek();
			stack.push(nums[i]);
		}

		return result;
	}

	//Online Stock Span
	public void stockSpan(int[] prices) {
		//0-price, 1-consecutive days count
		Stack<int[]> stack = new Stack<>();
		for (int i = 0; i < prices.length; i++) {
			int count = 1;
			while (!stack.isEmpty() && stack.peek()[0] <= prices[i]) {
				count += stack.pop()[1];
			}
			stack.push(new int[] { prices[i], count });
			System.out.println(i + " - " + count);
		}
	}

	// Largest Rectangle in Histogram
	// Approach1: BruteForce Approach: Time Complexity-O(n^2)
	public int largestRectangleArea1(int[] h) {
		int max = 0, width;
		int minHeight;

		for (int i = 0; i < h.length; i++) {
			width = 1;
			minHeight = Integer.MAX_VALUE;
			for (int j = i; j >= 0; j--, width++) {
				minHeight = Math.min(minHeight, h[j]);
				max = Math.max(max, width * minHeight);
			}
		}
		return max;
	}

	// Using Divide & Conquer Algorithm:
	/*
	 * worst case time complexity of this algorithm becomes O(n^2). In worst case, we always have (n-1) elements in one side and 0 
	 * elements in other side and if the finding minimum takes O(n) time, we get the recurrence similar to worst case of Quick Sort. 
	 * How to find the minimum efficiently? Range Minimum Query using Segment Tree can be used for this. We build segment tree of the
	 * given histogram heights. Once the segment tree is built, all range minimum queries take O(Logn) time.
	 * 
	 * Overall Time = Time to build Segment Tree + Time to recursively find maximum area; 
	 * Time to build segment tree is O(n). Let the time to recursively find max area be T(n). It can be written as following.
	 * T(n) = O(Logn) + T(n-1)
	 */
	public int largestRectangleArea2(int[] heights) {
		if (heights.length == 0)
			return 0;
		return largestRectangleArea2(heights, 0, heights.length - 1);
	}

	public int largestRectangleArea2(int[] heights, int l, int h) {
		if (l > h)
			return 0;
		if (l == h)
			return heights[l];

		// Rewrite this using Range Minimum query
		int m = findMin(heights, l, h);

		return Utils.max(largestRectangleArea2(heights, l, m - 1), largestRectangleArea2(heights, m + 1, h),
				(h - l + 1) * heights[m]);
	}

	private int findMin(int[] a, int l, int h) {
		int minIndex = l;
		for (int i = l + 1; i <= h; i++)
			if (a[i] < a[minIndex])
				minIndex = i;

		return minIndex;
	}

	// Using Stack
	public int largestRectangleArea31(int[] heights) {
		if (heights == null || heights.length == 0)
			return 0;

		Stack<Integer> stack = new Stack<Integer>();
		int maxArea = 0, n = heights.length;
		for (int i = 0; i <= n; i++) {
			while (!stack.isEmpty() && (i == n || heights[stack.peek()] >= heights[i])) {
				int h = heights[stack.pop()];
				int w = stack.isEmpty() ? i : i - stack.peek() - 1;
				maxArea = Math.max(maxArea, h * w);
			}
			stack.push(i);
		}
		return maxArea;
	}

	public int largestRectangleArea32(int[] heights) {
		int n = heights.length;
		if (n == 0)
			return 0;
		Stack<Integer> stack = new Stack<>();
		int i = 0, width = 0, maxArea = 0, topIndex = 0;

		while (i < n || !stack.isEmpty()) {
			if (stack.isEmpty() || i < n && (heights[stack.peek()] <= heights[i])) {
				stack.push(i++); // Store the index
			} else {
				topIndex = stack.pop(); // Get the top value, this will be used below to get height
				width = stack.isEmpty() ? i : (i - stack.peek() - 1); // i - peek/prev in the stack
				maxArea = Math.max(maxArea, heights[topIndex] * width);
			}
		}

		return maxArea;
	}

	//Trapping Rain Water -using Monotonic Stack 
	public int trappingRainWater(int[] height) {
		if (height.length <= 1)
			return 0;

		Stack<Integer> stack = new Stack<>();
		int n = height.length, water = 0;

		for (int i = 0; i < n; i++) {
			while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
				int prev = stack.pop();
				if (stack.isEmpty())
					break;
				int minHeight = Math.min(height[stack.peek()], height[i]);
				water += (minHeight - height[prev]) * (i - stack.peek() - 1);
			}
			stack.push(i);
		}

		return water;
	}

	/********************** Type3: Stack design problems *******************/

}