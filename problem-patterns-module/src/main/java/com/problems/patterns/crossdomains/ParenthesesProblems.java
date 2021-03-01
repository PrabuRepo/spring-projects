package com.problems.patterns.crossdomains;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class ParenthesesProblems {
	/* Valid or Well Formed Parentheses
	 *   You have been asked to Write an algorithm to find Whether Given the Sequence of parentheses are well formed. 
	 * Eg:
	 *  Is {}{ well formed ??? :false
	 *  Is }{ well formed ??? :false
	 *  Is {{{{}}}}{}{}{}{}{}{}{}{}{}{}{{{}}} well formed ??? :true
	 *   
	 * Approach:(This solution works only for same type of symbols. Eg: { or ( or [) 
	 * 	Idea is to have two counters, one for open parentheses '{' and one for close '}'
	 *  Read one character at a time and increment one of the counters
	 *  If any given point of time count of close parentheses is greater than the open one, return false
	 *  If at the end both counters are equal, return true 
	 */

	public void isWellFormed(String str) {
		isWellFormed11(str);
		isWellFormed12(str, 0, str.length() - 1);

		isWellFormed21(str);
		isWellFormed22(str.toCharArray());
		isWellFormed23(str, 0, str.length() - 1);
		isWellFormed24(str);
	}

	//Approach1: Using Stack: Time-O(n), Space-O(n)
	public Boolean isWellFormed11(String str) {
		if (str == null) return false;
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '{') {
				stack.push(c);
			} else if (c == '}' && !stack.isEmpty()) {
				stack.pop();
			} else {
				return false;
			}
		}

		return stack.isEmpty();
	}

	public boolean isWellFormed12(String s, int l, int h) {
		Stack<Character> stack = new Stack<Character>();
		for (int i = l; i <= h; i++) {
			if (s.charAt(i) == '(') {
				stack.push('(');
			} else if (!stack.empty() && stack.peek() == '(') {
				stack.pop();
			} else {
				return false;
			}
		}
		return stack.empty();
	}

	//Approach2: Using Counter: Time-O(n), Space-O(1)
	public Boolean isWellFormed21(String str) {
		if (str == null) return false;
		int op = 0, cp = 0;
		for (char ch : str.toCharArray()) {
			if (ch == '{') op++;
			else cp++;

			if (cp > op) return false;
		}

		return (op == cp);
	}

	public boolean isWellFormed22(char[] arr) {
		int balance = 0;
		for (char c : arr) {
			if (c == '(') balance++;
			else balance--;
			if (balance < 0) return false;
		}
		return (balance == 0);
	}

	public boolean isWellFormed23(String s, int l, int h) {
		int count = 0;
		for (int i = l; i <= h; i++) {
			char ch = s.charAt(i);
			if (ch == '(') count++;
			else count--;

			if (count < 0) return false;
		}
		return count == 0;
	}

	boolean isWellFormed24(String s) {
		return isWellFormed23(s, 0, s.length() - 1);
	}

	//Valid Multiple Parentheses/Balanced Brackets 
	//Using stack: Time-O(n), Space-O(n)
	public boolean balancedParentheses1(String s) {
		Stack<Character> stack = new Stack<>();
		char ch;
		for (int i = 0; i < s.length(); i++) {
			ch = s.charAt(i);
			if (ch == '(' || ch == '{' || ch == '[') {
				stack.push(ch);
			} else if (!stack.isEmpty() && ((ch == ')' && stack.peek() == '(') || (ch == ']' && stack.peek() == '[')
					|| (ch == '}' && stack.peek() == '{'))) {
						stack.pop();
					} else {
						return false;
					}
		}

		return stack.isEmpty();
	}

	public boolean balancedParentheses2(String input) {
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == '(') {
				stack.push(')');
			}
			if (c == '[') {
				stack.push(']');
			}
			if (c == '{') {
				stack.push('}');
			} else if (c == ')' || c == '}' || c == ']') {
				if (stack.isEmpty() || stack.pop() != c) return false;
			}
		}
		//if stack is empty at this point, return true
		return stack.isEmpty();
	}

	/* Generate Parentheses - Backtracking 
	 * Write a function to generate all combinations of well-formed parentheses.
	 */
	/*
	 * Approach1: Using Auxiliary Buffer: Similar to "Letter Case Permutation" Problem
	 * Time: O(n2^n), Space: O(n2^n)
	 */
	public List<String> generateParenthesis(int n) {
		List<String> combinations = new ArrayList<>();
		generateAll(new char[2 * n], 0, combinations);
		return combinations;
	}

	public void generateAll(char[] buffer, int index, List<String> result) {
		if (index == buffer.length) {
			if (isWellFormed22(buffer)) result.add(new String(buffer));
		} else {
			buffer[index] = '(';
			generateAll(buffer, index + 1, result);
			buffer[index] = ')';
			generateAll(buffer, index + 1, result);
		}
	}

	/* Approach2:
	 * Instead of adding '(' or ')' every time as in Approach 1, let's only add them when we know it will remain a valid sequence.
	 * We can do this by keeping track of the number of opening and closing brackets we have placed so far. We can start an opening 
	 * bracket if we still have one (of n) left to place. And we can start a closing bracket if it would not exceed the number of 
	 * opening brackets.
	 * Time: O(2^n) (Say exponential time complexity, because so much mathematical operations required to find time complexity).
	 * Space: O(2n) 2n -> 'str' space. Its not 'res' space. 
	 */
	public List<String> generateParentheses(int n) {
		if (n <= 0) return null;
		List<String> res = new ArrayList<>();
		backtrack14(0, 0, n, res, "");
		res.stream().forEach(k -> System.out.print(k + ", "));
		return res;
	}

	public void backtrack14(int op, int cp, int n, List<String> res, String str) {
		if (op == n && cp == n) {
			res.add(str);
		} else {
			if (op < n) backtrack14(op + 1, cp, n, res, str + "(");
			if (cp < op) backtrack14(op, cp + 1, n, res, str + ")"); //Closed paren should be less than open paren
		}
	}

	//Longest Valid Parentheses - Stack/DP
	//Approach1: Brute Force Approach
	// Time:O(n^3), Space:O(1)
	public int longestValidParentheses1(String s) {
		int n = s.length(), max = 0;
		for (int i = 0; i < n; i++) {
			//Here j is incremented by 2, because valid parentheses length should be even
			for (int j = i + 1; j < n; j += 2) {
				if (isWellFormed23(s, i, j)) {
					max = Math.max(max, j - i + 1);
				}
			}
		}
		return max;
	}

	//Approach2: Using Stack - Time:O(n), Space:O(n)
	public int longestValidParentheses2(String s) {
		Stack<Integer> stack = new Stack<>();
		stack.push(-1);
		int max = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				stack.push(i);
			} else {
				stack.pop();
				if (!stack.isEmpty()) max = Math.max(max, i - stack.peek());
				else stack.push(i);
			}
		}
		return max;
	}

	//Two pointer - Forward & Backward Navigation
	//Time: O(n), Space:O(1)
	public int longestValidParentheses3(String s) {
		int op = 0, cp = 0, max = 0, n = s.length();
		//Forward traversal -> for cases (()))); FT doesnt work for "(((())"
		for (int i = 0; i < n; i++) {
			char ch = s.charAt(i);
			if (ch == '(') op++;
			else cp++;

			if (op == cp) max = Math.max(max, 2 * cp); //2*op
			else if (cp > op) op = cp = 0;
		}

		//Reverse traversal -> For cases: (((())op RT doesnt work for "(())))"
		//Alternative for reverse traversal: Reverse the string and use the forward reversal
		op = cp = 0;
		for (int i = n - 1; i >= 0; i--) {
			char ch = s.charAt(i);
			if (ch == '(') op++;
			else cp++;

			if (op == cp) max = Math.max(max, 2 * cp); //2*op
			else if (op > cp) op = cp = 0; // Condition different from previous one
		}

		return max;
	}

	//Approach4: Dynamic Programming Approach -> This is optional 
	//Time: O(n), Space:O(n)
	public int longestValidParentheses4(String s) {
		int n = s.length(), max = 0;
		int[] dp = new int[n];

		for (int i = 1; i < n; i++) {
			if (s.charAt(i) == '(') continue;

			if (s.charAt(i - 1) == '(') { //This condition for "()()"
				dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
			} else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') { //This condition for "((()))"
				dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
			}
			max = Math.max(max, dp[i]);
		}
		return max;
	}

	//Remove Invalid Parentheses - DFS/BFS
	//Simple BFS Approach - Time-O(2^n), Space-O(n)
	public List<String> removeInvalidParentheses1(String s) {
		List<String> result = new ArrayList<>();
		Queue<String> queue = new LinkedList<>();
		Set<String> visited = new HashSet<>();

		queue.add(s);
		visited.add(s);
		while (!queue.isEmpty()) {
			String curr = queue.poll();

			if (isWellFormed24(curr)) {
				result.add(curr);
				continue;
			}

			for (int i = 0; i < curr.length(); i++) {
				//Ignore if char is not a parentheses
				if (curr.charAt(i) != '(' && curr.charAt(i) != ')') continue;

				String newStr = curr.substring(0, i) + curr.substring(i + 1);
				if (!visited.contains(newStr)) {
					queue.add(newStr);
					visited.add(newStr);
				}
			}
		}
		return result;
	}

	//DFS Approach: Time: O(2^n), Space:O(1)
	public List<String> removeInvalidParentheses2(String s) {
		List<String> result = new ArrayList<>();
		char[] paren = new char[] { '(', ')' };
		dfs(s, result, 0, 0, paren);
		return result;
	}

	private void dfs(String s, List<String> result, int last_i, int last_j, char[] paren) {
		int i = last_i, count = 0;
		while (i < s.length()) {
			if (s.charAt(i) == paren[0]) count++;
			if (s.charAt(i) == paren[1]) count--;
			if (count < 0) break;
			i++;
		}

		if (count >= 0) {
			// extra ')' is detected. We now have to detect extra '(' by reversing the string.
			String reversed = new StringBuilder(s).reverse().toString();
			if (paren[0] == '(') {
				char[] revParen = new char[] { ')', '(' };
				dfs(reversed, result, 0, 0, revParen);
			} else {
				result.add(reversed);
			}
		} else {
			// extra ')' is detected and we have to do something
			for (int j = last_j; j <= i; j++) {
				if (s.charAt(j) == paren[1] && (j == last_j || s.charAt(j - 1) != paren[1])) {
					String newStr = s.substring(0, j) + s.substring(j + 1);
					dfs(newStr, result, i, j, paren);
				}
			}
		}
	}

	//Efficient BFS Approach - Time-O(2^n), Space-O(1)
	//Similar to DFS
	public List<String> removeInvalidParentheses3(String s) {
		List<String> result = new ArrayList<>();
		Queue<Tuple> queue = new LinkedList<>();
		char[] paren = new char[] { '(', ')' };
		queue.add(new Tuple(s, 0, 0, paren));

		while (!queue.isEmpty()) {
			Tuple curr = queue.poll();
			int i = curr.lastI, count = 0;
			String str = curr.str;
			paren = curr.paren;

			while (i < curr.str.length()) {
				if (str.charAt(i) == paren[0]) count++;
				if (str.charAt(i) == paren[1]) count--;
				if (count < 0) break;
				i++;
			}

			if (count >= 0) {
				String revStr = new StringBuilder(str).reverse().toString();
				if (paren[0] == '(') {
					char[] revParen = new char[] { ')', '(' };
					queue.add(new Tuple(revStr, 0, 0, revParen));
				} else {
					result.add(revStr);
				}
			} else {
				for (int j = curr.lastJ; j < str.length(); j++) {
					if (str.charAt(j) == paren[1] && (j == curr.lastJ || str.charAt(j - 1) != paren[1])) {
						String newStr = str.substring(0, j) + str.substring(j + 1);
						queue.add(new Tuple(newStr, i, j, curr.paren));
					}
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {

		ParenthesesProblems ob = new ParenthesesProblems();
		//System.out.println(ob.balancedParentheses("()[]{}"));
		ob.generateParentheses(3);
	}

}

class Tuple {
	public String str;
	public int lastI;
	public int lastJ;
	public char[] paren;

	public Tuple(String s, int i, int j, char[] paren) {
		this.str = s;
		this.lastI = i;
		this.lastJ = j;
		this.paren = paren;
	}
}
