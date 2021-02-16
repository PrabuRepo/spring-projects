package com.problems.patterns.crossdomains;

import com.basic.algorithms.MathProblems;
import com.common.model.ListNode;
import com.problems.patterns.BacktrackingPatterns;
import com.problems.patterns.StringProblems;
import com.problems.patterns.dp.DPStringPatterns;
import com.problems.patterns.ds.FastAndSlowPtrPatterns;

public class PalindromePatterns {

	StringProblems stringProblems = new StringProblems();

	MathProblems mathProblems = new MathProblems();

	FastAndSlowPtrPatterns fastAndSlowPtrPatterns = new FastAndSlowPtrPatterns();

	DPStringPatterns dpStringPatterns = new DPStringPatterns();

	BacktrackingPatterns backtrackingPatterns = new BacktrackingPatterns();

	//Palindrome Number  
	public void palindromeNumber(int num) {
		mathProblems.isPalindrome(num);
	}

	//Valid Palindrome I
	public void validPalindromeI(String str) {
		stringProblems.isPalindrome11(str);
		stringProblems.isPalindrome12(str);
		stringProblems.isPalindrome13(str);
	}

	//Valid Palindrome II
	public void validPalindromeII(String str) {
		stringProblems.validPalindrome2(str);
	}

	//Find the Closest Palindrome
	public String closestPalindrome(String n) {
		int order = (int) Math.pow(10, n.length() / 2);//Order used to eliminate half of digits
		Long num = Long.valueOf(new String(n));
		Long mirrorNum = mirror(num); //Same mid
		//Two Cases: 1.Increase 1 to mid;  2.handles input like 9, 99...
		Long mirrorLarger = mirror((num / order) * order + order);
		//Two Cases: 1.Decrease 1 to mid & 2.handles input like 10, 100...
		Long mirrorSmaller = mirror((num / order) * order - 1);

		if (mirrorNum > num) {
			mirrorLarger = (long) Math.min(mirrorNum, mirrorLarger);
		} else if (mirrorNum < num) {
			mirrorSmaller = (long) Math.max(mirrorNum, mirrorSmaller);
		}
		return String.valueOf(num - mirrorSmaller <= mirrorLarger - num ? mirrorSmaller : mirrorLarger);
	}

	Long mirror(Long ans) {
		char[] a = String.valueOf(ans).toCharArray();
		int i = 0;
		int j = a.length - 1;
		while (i < j) {
			a[j--] = a[i++];
		}
		return Long.valueOf(new String(a));
	}

	//Palindrome Linked List
	public void palindromeLinkedList(ListNode head) {
		fastAndSlowPtrPatterns.isPalindrome1(head);
		fastAndSlowPtrPatterns.isPalindrome2(head);
	}

	//TODO: Check these problems
	//Trie: Palindrome Pairs    
	//KMP Algorithm: Shortest Palindrome 

	//Longest Palindromic Subsequence   
	public void palindromicSubsequence(String str) {
		dpStringPatterns.lps1(str);
		dpStringPatterns.lps3(str);
	}

	//Longest Palindromic Substring   
	public void palindromicSubstring(String str) {
		dpStringPatterns.lpSubstr1(str);
		dpStringPatterns.lpSubstr3(str);
	}

	//Palindrome Partitioning
	public void palindromePartitioning(String str) {
		backtrackingPatterns.partition1(str);
		backtrackingPatterns.partition2(str);
	}

	//Palindrome Partitioning II
	public void palindromePartitioningII(String str) {
		dpStringPatterns.minCut1(str);
		dpStringPatterns.minCut2(str);
	}

	//TODO: Solve below problems
	//Minimum Deletions in a String to make it a Palindrome
	//Form a Palindrome(min no of chars needed to form palindrome)
	//Count of Palindromic Subsequence
	//Count of Palindromic Substrings/Palindromic Substrings

}