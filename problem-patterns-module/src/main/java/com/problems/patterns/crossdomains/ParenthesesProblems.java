package com.problems.patterns.crossdomains;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class ParenthesesProblems {
	public static void main(String[] args) {
		//System.out.println(balancedParentheses("()[]{}"));

		generateParentheses(3);
	}

	//Valid Parentheses/Balanced Brackets
	//Using stack: Time-O(n), Space-O(n)
	public static boolean balancedParentheses(String s) {
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

	/* Generate Parentheses - Backtracking 
	 * Write a function to generate all combinations of well-formed parentheses.
	 */
	public static List<String> generateParentheses(int n) {
		if (n <= 0) return null;
		List<String> res = new ArrayList<>();
		backtrack14(0, 0, n, res, "");
		res.stream().forEach(k -> System.out.print(k + ", "));
		return res;
	}

	public static void backtrack14(int op, int cp, int n, List<String> res, String str) {
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
			for (int j = i + 1; j < n; j += 2) {
				if (isValid1(s, i, j)) {
					max = Math.max(max, j - i + 1);
				}
			}
		}
		return max;
	}

	//Approach2: Dynamic Programming Approach
	//Time:O(n), Space:O(n)
	public int longestValidParentheses2(String s) {
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

	//Approach3 Using Stack - Time:O(n), Space:O(n)
	public int longestValidParentheses3(String s) {
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
	public int longestValidParentheses(String s) {
		int l = 0, r = 0, max = 0, n = s.length();
		//Forward traversal -> for cases (())))
		for (int i = 0; i < n; i++) {
			char ch = s.charAt(i);
			if (ch == '(') l++;
			if (ch == ')') r++;

			if (l == r) max = Math.max(max, 2 * r); //2*l
			else if (r > l) l = r = 0;
		}

		//Reverse traversal -> For cases: (((())
		//Alternative for reverse traversal: Reverse the string and use the forward reversal
		l = r = 0;
		for (int i = n - 1; i >= 0; i--) {
			char ch = s.charAt(i);
			if (ch == '(') l++;
			if (ch == ')') r++;

			if (l == r) max = Math.max(max, 2 * r); //2*l
			else if (l > r) l = r = 0;
		}

		return max;
	}

	//Using Stack: Time-O(n), Space-O(n)
	public boolean isValid1(String s, int l, int h) {
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

	//Using Counter: Time-O(n), Space-O(1)
	public boolean isValid2(String s, int l, int h) {
		int count = 0;
		for (int i = l; i <= h; i++) {
			char ch = s.charAt(i);
			if (ch == '(') {
				count++;
			} else if (count > 0 && ch == ')') {
				count--;
			} else {
				return false;
			}
		}
		return count == 0;
	}

	//Remove Invalid Parentheses - DFS/BFS
	//DFS Approach: Time: O(2^n), Space:O(1)
	public List<String> removeInvalidParentheses1(String s) {
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

	//Simple BFS Approach - Time-O(2^n), Space-O(n)
	public List<String> removeInvalidParentheses2(String s) {
		List<String> result = new ArrayList<>();
		Queue<String> queue = new LinkedList<>();
		Set<String> visited = new HashSet<>();

		boolean level = false;
		queue.add(s);
		visited.add(s);

		while (!queue.isEmpty()) {
			String curr = queue.poll();

			if (isValid(curr)) {
				result.add(curr);
				level = true;
			}

			if (level) continue;

			for (int i = 0; i < curr.length(); i++) {
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

	//Efficient BFS Approach - Time-O(2^n), Space-O(1)
	//Similar to DFS
	public List<String> removeInvalidParentheses(String s) {
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

	boolean isValid(String s) {
		int count = 0;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(') count++;
			if (c == ')' && count-- == 0) return false;
		}

		return count == 0;
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
