package com.problems.patterns.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BacktrackingPatterns {

	/********************* 1.Backtracking – Auxiliary Buffer Technique I ***********************/
	// Combination Sum-II
	public List<List<Integer>> combinationSum2(int[] nums, int target) {
		List<List<Integer>> res = new ArrayList<>();
		Arrays.sort(nums);
		backtrack5(res, new ArrayList<>(), nums, target, 0);
		res.stream().forEach(k -> System.out.print(k + ", "));
		return res;
	}

	private void backtrack5(List<List<Integer>> res, List<Integer> buffer, int[] nums, int target, int startIndex) {
		if (target < 0) return;
		else if (target == 0) res.add(new ArrayList<>(buffer));
		else {
			for (int i = startIndex; i < nums.length; i++) {
				if (i > startIndex && nums[i] == nums[i - 1]) continue;
				buffer.add(nums[i]);
				backtrack5(res, buffer, nums, target - nums[i], i + 1);
				buffer.remove(buffer.size() - 1);
			}
		}
	}

	// Combination Sum-III
	public List<List<Integer>> combinationSum3(int n, int k) {
		List<List<Integer>> res = new ArrayList<>();
		backtrack6(n, k, 1, new ArrayList<>(), res);
		res.stream().forEach(data -> System.out.print(data + ", "));
		return res;
	}

	public void backtrack6(int sum, int k, int startIndex, List<Integer> buffer, List<List<Integer>> res) {
		if (buffer.size() == k && sum == 0) res.add(new ArrayList<>(buffer));
		else if (buffer.size() >= k || sum < 0) return;
		else {
			for (int i = startIndex; i <= 9; i++) {
				buffer.add(i);
				backtrack6(sum - i, k, i + 1, buffer, res);
				buffer.remove(buffer.size() - 1);
			}
		}
	}

	// Factor Combinations
	public List<List<Integer>> getFactors(int n) {
		List<List<Integer>> res = new ArrayList<>();
		backtrack7(n, 2, res, new ArrayList<>());
		res.stream().forEach(data -> System.out.print(data + ", "));
		return res;
	}

	public void backtrack7(int n, int startIndex, List<List<Integer>> res, List<Integer> buffer) {
		if (n == 1) {
			res.add(new ArrayList<>(buffer));
		} else {
			for (int i = startIndex; i <= n; i++) {
				if (n % i != 0) continue;
				buffer.add(i);
				backtrack7(n / i, i, res, buffer);
				buffer.remove(buffer.size() - 1);
			}
		}
	}

	/*
	 *  Letter Case Permutation:
	 *  Examples: Input: S = "a1b2" Output: ["a1b2", "a1B2", "A1b2", "A1B2"]
	 */
	// Approach1: DFS
	public List<String> letterCasePermutation1(String S) {
		List<String> result = new ArrayList<>();
		backtrack(S.toCharArray(), 0, result);
		return result;
	}

	public void backtrack(char[] arr, int i, List<String> result) {
		if (i == arr.length) {
			result.add(new String(arr));
		} else if (Character.isDigit(arr[i])) {
			backtrack(arr, i + 1, result);
		} else {
			arr[i] = Character.toLowerCase(arr[i]);
			backtrack(arr, i + 1, result);
			arr[i] = Character.toUpperCase(arr[i]);
			backtrack(arr, i + 1, result);
		}
	}

	// Approach2: BFS
	public List<String> letterCasePermutation2(String S) {
		Queue<String> queue = new LinkedList<>();
		queue.add(S);

		for (int i = 0; i < S.length(); i++) {
			if (Character.isDigit(S.charAt(i))) continue;
			int size = queue.size();
			while (size-- > 0) {
				char[] arr = queue.poll().toCharArray();
				arr[i] = Character.toLowerCase(arr[i]);
				queue.add(new String(arr));
				arr[i] = Character.toUpperCase(arr[i]);
				queue.add(new String(arr));
			}
		}
		return new ArrayList<String>(queue);
	}

	// Approach3: Using Bit Manipulation
	public List<String> letterCasePermutation3(String S) {
		int n = S.length();
		int size = 1 << n; // or Math.pow(2, n);
		HashSet<String> set = new HashSet<>();
		S = S.toLowerCase();
		for (int i = 0; i < size; i++) {
			char[] arr = S.toCharArray();
			for (int j = 0; j < n; j++) {
				if (Character.isLetter(arr[j]) && (i >> j & 1) == 1) {
					arr[j] = (char) (arr[j] - 32); // or Character.toUpperCase(arr[j]);
				}
			}
			set.add(String.valueOf(arr));
		}
		return new ArrayList<String>(set);
	}

	/********************* 2.Backtracking – Auxiliary Buffer Technique problems ***********************/
	/* Palindrome Partitioning: 
	 * Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible palindrome
	 *  partitioning of s.
	 *  [["aa","b"], ["a","a","b"]] 
	 */
	//Using Backtracking(DFS) Algorithm - TC:O(n(2^n))
	public List<List<String>> partition1(String s) {
		List<List<String>> res = new ArrayList<>();
		backtrack12(res, new ArrayList<>(), s, 0);
		res.stream().forEach(val -> System.out.print(val + ", "));
		return res;
	}

	public void backtrack12(List<List<String>> res, List<String> buffer, String s, int startIndex) {
		if (startIndex == s.length()) res.add(new ArrayList<>(buffer));
		else {
			for (int i = startIndex; i < s.length(); i++) {
				if (isPalindrome(s, startIndex, i)) {
					buffer.add(s.substring(startIndex, i + 1));
					backtrack12(res, buffer, s, i + 1);
					buffer.remove(buffer.size() - 1);
				}
			}
		}
	}

	public boolean isPalindrome(String s, int low, int high) {
		while (low < high) {if (s.charAt(low++) != s.charAt(high--)) return false;}
		return true;
	}

	//Using DP & BackTracking(DFS) Algorithm 
	//TC:O(n^2 +2^n) = sTC:O(2^n)
	public List<List<String>> partition2(String s) {
		List<List<String>> result = new ArrayList<>();
		int n = s.length();
		//use longest palindromic substring solution
		boolean[][] dp = new boolean[n][n];
		for (int len = 1; len <= n; len++) {
			for (int i = 0; i <= n - len; i++) {
				int j = i + len - 1;
				if (s.charAt(i) == s.charAt(j)) {
					if (len == 1 || len == 2) dp[i][j] = true;
					else dp[i][j] = dp[i + 1][j - 1];
				}
			}
		}
		//Apply Backtracking algorithm
		backtrackPartition(dp, s, result, new ArrayList<>(), 0);
		return result;
	}

	public void backtrackPartition(boolean[][] dp, String s, List<List<String>> result, List<String> tempList,
			int startIndex) {
		if (s.length() == startIndex) {
			result.add(new ArrayList<>(tempList));
		} else {
			for (int i = startIndex; i < s.length(); i++) {
				if (dp[startIndex][i]) {
					tempList.add(s.substring(startIndex, i + 1));
					backtrackPartition(dp, s, result, tempList, i + 1);
					tempList.remove(tempList.size() - 1);
				}
			}
		}
	}

	/*Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could
	 represent.
	 Combination: Eg: 236 -> 3C1*3C1*3C1 -> 3*3*3 -> 27 combinations
	 */
	public List<String> letterCombinations(String num) {
		if (num.length() == 0) return new ArrayList<>();
		Map<Character, String> map = new HashMap<>();
		map.put('2', "abc");
		map.put('3', "def");
		map.put('4', "ghi");
		map.put('5', "jkl");
		map.put('6', "mno");
		map.put('7', "pqrs");
		map.put('8', "tuv");
		map.put('9', "wxyz");
		map.put('0', "");
		List<String> res = new ArrayList<>();
		backtrack13(num, 0, map, new StringBuilder(), res);
		return res;
	}

	private void backtrack13(String num, int index, Map<Character, String> phoneNoMap, StringBuilder buffer,
			List<String> res) {
		//1.Termination Case
		if (index >= num.length()) {
			res.add(buffer.toString());
		} else {
			//2.Find candidates
			String letters = phoneNoMap.get(num.charAt(index));
			//3.Place candidate in buffer
			for (int i = 0; i < letters.length(); i++) {
				buffer.append(letters.charAt(i));
				//4.Recurse to next index
				backtrack13(num, index + 1, phoneNoMap, buffer, res);
				buffer.deleteCharAt(index);
			}
		}
	}

	// Generate IP Addresses/Restore IP Addresses
	public List<String> restoreIpAddresses1(String s) {
		List<String> res = new ArrayList<>();
		for (int i = 1; i <= 3; i++)
			for (int j = 1; j <= 3; j++)
				for (int k = 1; k <= 3; k++)
					for (int l = 1; l <= 3; l++)
						if (i + j + k + l == s.length()) {
							String a = s.substring(0, i);
							if (Integer.parseInt(a) > 255 || (a.charAt(0) == '0' && a.length() > 1)) continue;
							String b = s.substring(i, i + j);
							if (Integer.parseInt(b) > 255 || (b.charAt(0) == '0' && b.length() > 1)) continue;
							String c = s.substring(i + j, i + j + k);
							if (Integer.parseInt(c) > 255 || (c.charAt(0) == '0' && c.length() > 1)) continue;
							String d = s.substring(i + j + k, i + j + k + l);
							if (Integer.parseInt(d) > 255 || (d.charAt(0) == '0' && d.length() > 1)) continue;
							res.add(a + "." + b + "." + c + "." + d);
						}
		return res;
	}

	// Approach2: Recursive Method; DFS Search
	public List<String> restoreIpAddresses2(String s) {
		List<String> result = new ArrayList<String>();
		restoreIp(s, result, 0, "", 0);
		result.stream().forEach(k -> System.out.print(k + ", "));
		return result;
	}

	private void restoreIp(String ip, List<String> result, int currIndex, String ipCombinations, int count) {
		if (count == 4 && currIndex == ip.length()) result.add(ipCombinations.toString());
		if (count == 4) return;

		for (int i = 1; i <= 3; i++) {
			if (currIndex + i > ip.length()) break;
			String s = ip.substring(currIndex, currIndex + i);
			if (Integer.parseInt(s) > 255 || (s.startsWith("0") && s.length() > 1)) continue;
			restoreIp(ip, result, currIndex + i, ipCombinations + s + (count < 3 ? "." : ""), count + 1);
		}
	}
}