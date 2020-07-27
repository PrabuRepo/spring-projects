package com.problems.patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.common.model.TrieNode;

public class TrieProblems {

	/* Valid Word Square:
	 * Given a sequence of words, check whether it forms a valid word square.
	 * For example, the word sequence ["ball","area","lead","lady"] forms a word square because each word reads the same both horizontally and vertically.
	 Example 1:
	Input:
		[
		  "abcd",
		  "bnrt",
		  "crmy",
		  "dtye"
		]
	Output: true
	 */
	public boolean validWordSquare(List<String> words) {
		if (words == null || words.size() == 0)
			return true;
		int m = words.size();
		for (int i = 0; i < m; i++) {
			int n = words.get(i).length();
			for (int j = 0; j < n; j++)
				if (j >= m || m != n || words.get(i).charAt(j) != words.get(j).charAt(i))
					return false;
		}
		return true;
	}

	/*
	 * Word Squares:
	 * Given a set of words (without duplicates), find all word squares you can build from them.
	 * A sequence of words forms a valid word square if the kth row and column read the exact same string, where 0 <=k < max(numRows, numColumns).
	 */
	TrieNode root;

	public List<List<String>> wordSquares(String[] words) {
		List<List<String>> ans = new ArrayList<>();
		if (words == null || words.length == 0)
			return ans;
		int len = words[0].length();
		// Build Trie
		buildTrie(words);
		// Search Words
		List<String> ansBuilder = new ArrayList<>();
		for (String w : words) {
			ansBuilder.add(w);
			search(len, ans, ansBuilder);
			ansBuilder.remove(ansBuilder.size() - 1);
		}
		ans.stream().forEach(k -> System.out.print(k + ", "));
		return ans;
	}

	void buildTrie(String[] words) {
		root = new TrieNode();
		for (String w : words) {
			TrieNode cur = root;
			for (char ch : w.toCharArray()) {
				int idx = ch - 'a';
				if (cur.children[idx] == null)
					cur.children[idx] = new TrieNode();
				cur.children[idx].startWith.add(w);
				cur = cur.children[idx];
			}
		}
	}

	private void search(int len, List<List<String>> ans, List<String> ansBuilder) {
		if (ansBuilder.size() == len) {
			ans.add(new ArrayList<>(ansBuilder));
			return;
		}

		int idx = ansBuilder.size();
		StringBuilder prefixBuilder = new StringBuilder();
		for (String s : ansBuilder)
			prefixBuilder.append(s.charAt(idx));
		List<String> startWith = findByPrefix(prefixBuilder.toString());
		for (String sw : startWith) {
			ansBuilder.add(sw);
			search(len, ans, ansBuilder);
			ansBuilder.remove(ansBuilder.size() - 1);
		}
	}

	List<String> findByPrefix(String prefix) {
		List<String> ans = new ArrayList<>();
		TrieNode cur = root;
		for (char ch : prefix.toCharArray()) {
			int idx = ch - 'a';
			if (cur.children[idx] == null)
				return ans;

			cur = cur.children[idx];
		}
		ans.addAll(cur.startWith);
		return ans;
	}

	// Word Boggle/Word Search I:
	public void wordSearchI() {
		WordProblems.wordSearchI(null, null);
	}

	// Word Search II:
	public void wordSearchII() {
		WordProblems.wordSearchII1(null, null);
		WordProblems.wordSearchII2(null, null);
		WordProblems.wordSearchII3(null, null);
	}

	//TODO: Move below to Consolidated Module

	/* Longest Common Prefix using Trie: Given a set of strings, find the longest common prefix.
	 *  Input  : {“geeksforgeeks”, “geeks”, “geek”, “geezer”}	Output : "gee"
	 *  Input  : {"apple", "ape", "april"} 	Output : "ap"
	 */
	int index;

	public String longestCommonPrefix(String[] arr) {
		TrieNode pCrawl = new TrieNode();
		for (String str : arr)
			insert(pCrawl, str);
		StringBuilder prefix = new StringBuilder();
		while (countChildren(pCrawl) == 1 && !pCrawl.isEndOfWord) {
			pCrawl = pCrawl.children[index];
			prefix.append((char) ('a' + index));
		}
		return prefix.toString();
	}

	private int countChildren(TrieNode node) {
		int count = 0;
		for (int i = 0; i < 26; i++) {
			if (node.children[i] != null) {
				count++;
				index = i;
			}
		}
		return count;
	}

	/* Palindrome Pairs:
	 * Given a list of unique words, find all pairs of distinct indices (i, j) in the given list, so
	 * that the concatenation of the two words, i.e. words[i] + words[j] is a palindrome.
	 */
	/*
	 * 1) Create an empty Trie.
	2) Do following for every word:-
	a) Insert reverse of current word.
	b) Also store up to which index it is 
	   a palindrome.
	3) Traverse list of words again and do following 
	   for every word.
	a) If it is available in Trie then return true
	b) If it is partially available
	     Check the remaining word is palindrome or not 
	     If yes then return true that means a pair
	     forms a palindrome.
	     Note: Position upto which the word is palindrome
	           is stored because of these type of cases.
	 */
	// Approach1: Using HashMap
	public List<List<Integer>> palindromePairs(String[] words) {
		Map<String, Integer> map = new HashMap<>();
		int n = words.length;
		List<List<Integer>> result = new ArrayList<>();
		for (int i = 0; i < n; i++)
			map.put(words[i], i);
		if (map.containsKey("")) {
			int index = map.get("");
			for (int i = 0; i < n; i++) {
				if (isPalindrome(words[i])) {
					if (i == index)
						continue;
					result.add(Arrays.asList(index, i));
					result.add(Arrays.asList(i, index));
				}
			}
		}
		for (int i = 0; i < n; i++) {
			String revStr = new StringBuilder(words[i]).reverse().toString();
			if (map.containsKey(revStr)) {
				int revIndex = map.get(revStr);
				if (i == revIndex)
					continue;
				result.add(Arrays.asList(i, revIndex));
			}
		}
		for (int i = 0; i < n; i++) {
			String curr = words[i];
			for (int j = 1; j < curr.length(); j++) {
				if (isPalindrome(curr.substring(0, j))) {
					String revStr = new StringBuilder(curr.substring(j)).reverse().toString();
					if (map.containsKey(revStr)) {
						int index = map.get(revStr);
						if (index == i)
							continue;
						result.add(Arrays.asList(index, i));
					}
				}
				if (isPalindrome(curr.substring(j))) {
					String revStr = new StringBuilder(curr.substring(0, j)).reverse().toString();
					if (map.containsKey(revStr)) {
						int index = map.get(revStr);
						if (index == i)
							continue;
						result.add(Arrays.asList(i, index));
					}
				}
			}
		}
		return result;
	}

	private boolean isPalindrome(String str) {
		int l = 0, r = str.length() - 1;
		while (l < r) {
			if (str.charAt(l) != str.charAt(r))
				return false;
			l++;
			r--;
		}
		return true;
	}

	// Check this prob:
	// Function to check if a palindrome pair exists
	public boolean checkPalindromePair(List<String> vect) {
		// Consider each pair one by one
		for (int i = 0; i < vect.size() - 1; i++) {
			for (int j = i + 1; j < vect.size(); j++) {
				String check_str = "";

				// concatenate both strings
				check_str = check_str + vect.get(i) + vect.get(j);

				// check if the concatenated string is
				// palindrome
				if (isPalindrome(check_str))
					return true;
			}
		}
		return false;
	}

	/* Minimum Unique Word Abbreviation:
	 * A string such as "word" contains the following abbreviations: ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd",
	 * "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"] 
	 * Given a target string and a set of strings in a dictionary, find an abbreviation of this target string with the 
	 * smallest possible length such that it does not conflict with abbreviations of the strings in the dictionary. 
	 * Each number or letter in the abbreviation is considered length = 1. 
	 * For example, the abbreviation "a32bc" has length = 4. Examples: "apple", ["blade"] -> "a4" (because "5" or
	 * "4e" conflicts with "blade") "apple", ["plain", "amber", "blade"] -> "1p3" (other valid answers include "ap3", 
	 * "a3e", "2p2", "3le", "3l1").
	 */
	public String minAbbreviation(String target, String[] dictionary) {
		Set<String> visited = new HashSet<>();
		PriorityQueue<Abbr2> q = new PriorityQueue<>((a, b) -> a.len - b.len);
		int len = target.length();
		String first = "";

		for (int i = 0; i < len; i++)
			first += "*";

		q.offer(new Abbr2(first, 1));
		while (!q.isEmpty()) {
			Abbr2 ab = q.poll();
			String abbr = ab.abbr;
			boolean conflict = false;
			for (String word : dictionary) {
				if (word.length() == len && isConflict(abbr, word)) {
					conflict = true;
					break;
				}
			}
			if (conflict)
				generateAbbr(target, abbr, visited, q);
			else
				return NumAbbr(abbr);
		}

		return null;
	}

	boolean isConflict(String abbr, String str) {
		for (int i = 0; i < abbr.length(); i++)
			if (abbr.charAt(i) != '*' && str.charAt(i) != abbr.charAt(i))
				return false;
		return true;
	}

	void generateAbbr(String str, String abbr, Set<String> visited, PriorityQueue<Abbr2> q) {
		char[] temp = abbr.toCharArray();
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] == '*') {
				temp[i] = str.charAt(i);
				String next = new String(temp);
				if (!visited.contains(next)) {
					q.offer(new Abbr2(next, abbrLength(next)));
					visited.add(next);
				}
				temp[i] = '*';
			}
		}
	}

	int abbrLength(String abbr) {
		int ret = 0, star = 0;
		for (char c : abbr.toCharArray()) {
			if (c >= 'a' && c <= 'z') {
				ret += 1 + star;
				star = 0;
			} else if (c == '*') {
				star = 1;
			}
		}
		return ret + star;
	}

	String NumAbbr(String abbr) {
		String ret = "";
		int count = 0;
		for (char c : abbr.toCharArray()) {
			if (c >= 'a' && c <= 'z') {
				if (count > 0) {
					ret += count;
					count = 0;
				}
				ret += c;
			} else {
				count++;
			}
		}
		if (count > 0)
			ret += count;
		return ret;
	}

	/******************************* Trie Operations ***********************************/
	public void insert(TrieNode root, String word) {
		int index;
		TrieNode current = root;
		for (int i = 0; i < word.length(); i++) {
			index = word.charAt(i) - 'a';
			if (current.children[index] == null) {
				current.children[index] = new TrieNode();
			}
			current = current.children[index];
		}
		// Set last node of isEndOfWord as true, to mark it as leaf
		current.isEndOfWord = true;
	}

	// Build Trie for word Boogle. Here we add word in the end of char, instead of isEndOfWord flag
	public TrieNode insert2(TrieNode root, String word) {
		TrieNode curr = root;
		for (int i = 0; i < word.length(); i++) {
			int index = word.charAt(i) - 'a';
			if (curr.children[index] == null)
				curr.children[index] = new TrieNode();
			curr = curr.children[index];
		}
		curr.word = word; // Add word; isEndOfWord is not required for this case
		return root;
	}
}

class Abbr2 {
	String abbr;
	int len;

	Abbr2(String abbr, int len) {
		this.abbr = abbr;
		this.len = len;
	}
}