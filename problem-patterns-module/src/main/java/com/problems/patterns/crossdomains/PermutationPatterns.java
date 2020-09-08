package com.problems.patterns.crossdomains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.basic.algorithms.Backtracking;
import com.problems.patterns.recursion.BacktrackingPatterns;
import com.problems.patterns.recursion.DynamicProgrammingPatterns;

public class PermutationPatterns {

	Backtracking basicBacktracking = new Backtracking();

	BacktrackingPatterns backtrackingPatterns = new BacktrackingPatterns();

	DynamicProgrammingPatterns dpPatterns = new DynamicProgrammingPatterns();

	//Permutations
	public void permutaions(int[] nums) {
		basicBacktracking.permute1(nums);
		basicBacktracking.permute2(nums);
	}

	//Permutations II
	public void permutaionsII(int[] nums) {
		basicBacktracking.permuteUnique1(nums);
		basicBacktracking.permuteUnique2(nums);
	}

	//String Permutations by changing case/Letter Case Permutation
	public void letterCasePermutaion(String s) {
		backtrackingPatterns.letterCasePermutation1(s);
		backtrackingPatterns.letterCasePermutation2(s);
		backtrackingPatterns.letterCasePermutation3(s);
	}

	/* Permutation Sequence: The set [1,2,3,...,n] contains a total of n! unique permutations. By listing and labeling
	 * all of the permutations in order, we get the following sequence for n = 3: "123" "132" "213" "231" "312" "321"
	 * Given n and k, return the kth permutation sequence. 
	 * Example 1: Input: n = 3, k = 3; Output: "213";
	 * Time Complexity: O(n)
	 * Ref: https://leetcode.com/problems/permutation-sequence/discuss/22507/%22Explain-like-I'm-five%22-Java-Solution-in-O(n)
	 */
	public String getPermutation(int n, int k) {
		if (n == 0 || k == 0) return "";

		int[] fact = new int[n + 1];
		List<Integer> nums = new ArrayList<>();
		fact[0] = 1;
		for (int i = 1; i <= n; i++) {
			//Add 1 to n in nums
			nums.add(i);
			//Calculate fact and add it to corresponding index
			fact[i] = fact[i - 1] * i;
		}

		k--; //decrease k by 1
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= n; i++) {
			int index = k / fact[n - i];
			sb.append(nums.get(index));
			nums.remove(index);
			k %= fact[n - i]; //or k -= (index * fact[n-i]);
		}

		return sb.toString();
	}

	/*Next Permutation
	 * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
	 * If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
	 * The replacement must be in-place and use only constant extra memory.
	 * Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
	 * 1,2,3 -> 1,3,2
	 * 3,2,1 -> 1,2,3
	 * 1,1,5 -> 1,5,1
	 */

	//Time: O(n), Space: O(1)
	public void nextPermutation(int[] nums) {
		if (nums.length <= 1) return;

		int i = nums.length - 1;
		//1.Find first increasing seq from the right
		while (i > 0) {
			if (nums[i - 1] < nums[i]) break;
			i--;
		}

		if (i > 0) {
			//2.Find first max value which is greater than (i-1)th index value from the last index.
			int maxIndex = nums.length - 1;
			while (maxIndex >= i) {
				if (nums[maxIndex] > nums[i - 1]) break;
				maxIndex--;
			}
			swap(nums, i - 1, maxIndex);
		}

		//3.Reverse the values from index i to n-1;
		reverse(nums, i, nums.length - 1);
	}

	//TODO: Find Permutation -> Understand this problem
	/*Find Permutation:
	 * By now, you are given a secret signature consisting of character 'D' and 'I'. 'D' represents a decreasing
	 * relationship between two numbers, 'I' represents an increasing relationship between two numbers. And our secret
	 * signature was constructed by a special integer array, which contains uniquely all the different number from 1 to
	 * n (n is the length of the secret signature plus 1). For example, the secret signature "DI" can be constructed by
	 * array [2,1,3] or [3,1,2], but won't be constructed by array [3,2,4] or [2,1,3,4], which are both illegal
	 * constructing special string that can't represent the "DI" secret signature.
	 * On the other hand, now your job is to find the lexicographically smallest permutation of [1, 2, ... n] could refer
	 * to the given secret signature in the input. 
	 * Example 1: Input: "I" Output: [1,2] Explanation: [1,2] is the only legal initial special string can construct secret 
	 * signature "I", where the number 1 and 2 construct an increasing relationship. 
	 * Example 2: Input: "DI" Output: [2,1,3] Explanation: Both [2,1,3] and [3,1,2] can construct the secret signature "DI",
	 * but since we want to find the one with the smallest lexicographical permutation, you need to output [2,1,3] Note: The 
	 * input string will only contain the character 'D' and 'I'.
	 */
	// Approach1: Assign the elements in forward & reverse direction
	public int[] findPermutation1(String s) {
		int num = 1, l = 0, r = 0;
		int[] result = new int[s.length() + 1];

		while (l < result.length) {
			if (l == s.length() || s.charAt(l) == 'I') {
				result[l++] = num++; // Store number in forward direction
			} else {
				r = l;
				while (r < s.length() && s.charAt(r) == 'D') r++;
				for (int i = r; i >= l; i--)
					result[i] = num++; // Store number in reverse direction
				l = r + 1;
			}
		}
		return result;
	}

	// Approach2: Assign the elements first, after that reverse the elements when char is 'D'.
	public int[] findPermutation2(String s) {
		int n = s.length(), arr[] = new int[n + 1];
		// Assign the elements
		for (int i = 0; i <= n; i++)
			arr[i] = i + 1; // sorted

		// Reverse the elements, if there in any 'D'
		for (int h = 0; h < n; h++) {
			if (s.charAt(h) == 'D') {
				int l = h;
				while (h < n && s.charAt(h) == 'D') h++;
				reverse(arr, l, h);
			}
		}
		return arr;
	}

	private void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	private void reverse(int[] nums, int l, int h) {
		while (l < h) {
			swap(nums, l, h);
			l++;
			h--;
		}
	}

	/*
	 * 2.Check Permutation: 
	 *     Given two strings, write a method to decide if one is a permutation of the other.
	 */
	// Approach1: Sort the strings and compare
	public boolean checkPermutation1(String str1, String str2) {
		if (str1.length() != str2.length()) return false;
		return sort(str1).equals(sort(str2));
	}

	private String sort(String str) {
		char[] ch = str.toCharArray();
		Arrays.sort(ch);
		return new String(ch);
	}

	// Approach2: using hashing
	public boolean checkPermutation2(String str1, String str2) {
		if (str1.length() != str2.length()) return false;

		int[] charCount = new int[128];
		for (int i = 0; i < str1.length(); i++)
			charCount[str1.charAt(i)]++;

		for (int i = 0; i < str2.length(); i++)
			if (--charCount[str2.charAt(i)] < 0) return false;

		return true;
	}

	/*
	 * 4.Palindrome Permutation: Given a string, write a function to check if it is a permutation of a palindrome. A palindrome is
	 * a word or phrase that is the same forwards and backwards. A permutation is a rearrangement of letters. The palindrome does 
	 * not need to be limited to just dictionary words.
	 * EXAMPLE - 
	 *   Input: Tact Coa 
	 *   Output: True (permutations:"taco cat'; "atco cta'; etc.)
	 */
	// Using Hashing - It handles lower case, upper case & special char's-> Time Complexity: O(n)
	public boolean canPermutePalindrome1(String s) {
		HashSet<Character> app = new HashSet<Character>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (app.contains(c)) {
				app.remove(c);
			} else {
				app.add(c);
			}
		}
		return app.size() <= 1;
	}

	public boolean canPermutePalindrome2(String str) {
		int[] charCount = new int[26];
		for (int i = 0; i < str.length(); i++) {
			char ch = upperToLowerCase(str.charAt(i));
			if (ch >= 'a' && ch <= 'z') charCount[ch - 'a']++;
		}

		int oddCount = 0;
		for (int i = 0; i < str.length(); i++) {
			char ch = upperToLowerCase(str.charAt(i));
			if (ch >= 'a' && ch <= 'z') {
				if (charCount[ch - 'a'] % 2 == 1) oddCount++;
			}
		}
		return (oddCount > 1) ? false : true;
	}

	private char upperToLowerCase(char ch) {
		if (ch >= 'A' && ch <= 'Z') return (char) (ch + 25 + 7);
		return ch;
	}

	//TODO:  Palindrome Permutation II 
	public void palindromePermuatationII() {
	}

	/*Permutation in String:
	 * Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. In other
	 * words, one of the first string's permutations is the substring of the second string. 
	 * Example 1: Input:s1 = "ab" s2 = "eidbaooo" Output:True Explanation: s2 contains one permutation of s1 ("ba").
	 */
	// Using Sliding Window
	public boolean checkInclusion(String s1, String s2) { // Here s1 is Pattern, s2 is whole string
		int l = 0, r = 0;
		int[] hash = new int[26];

		for (int i = 0; i < s1.length(); i++)
			hash[s1.charAt(i) - 'a']++;

		while (r < s2.length()) {
			int index = s2.charAt(r) - 'a';
			hash[index]--;

			while (hash[index] < 0) {
				hash[s2.charAt(l) - 'a']++;
				l++;
			}

			if (r - l + 1 == s1.length()) return true;
			r++;
		}

		return false;
	}

	// Combination Sum IV  - Permutation Problem
	public void combinationSumIV(int[] nums, int target) {
		dpPatterns.combinationSum41(nums, target);
		dpPatterns.combinationSum42(nums, target);
		dpPatterns.combinationSum43(nums, target);
	}

}