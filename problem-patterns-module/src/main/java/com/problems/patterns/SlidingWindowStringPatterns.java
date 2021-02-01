package com.problems.patterns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlidingWindowStringPatterns {

	/***************************** Type1: Sliding Window: String ******************************/
	/* Window Sliding Technique: 
	 * 	This technique shows how a nested for loop in few problems can be converted to single for loop and hence reducing the time 
	 *  complexity.
	 *  Sliding Window String Pattern: This pattern has two pointers(l & r), counter, hash array or map
	 */

	/* Minimum Window Substring/Smallest window in a string containing all the char of another string:
	 * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in
	 * complexity O(n). 
	 * Example: Input: S = "ADOBECODEBANC", T = "ABC" Output: "BANC"
	 */
	//Follows the pattern
	public String minWindow1(String str, String pat) {
		int[] hash = new int[128];
		for (char c : pat.toCharArray())
			hash[c]++;

		int l = 0, r = 0, counter = pat.length(), minLeft = -1, minWindow = Integer.MAX_VALUE;
		while (r < str.length()) {
			char c1 = str.charAt(r);
			// Decrease the counter only if char is present in pattern
			//Note: pat chars are always greater than equal to zero in hash arr, but other chars are less than equal to zero 
			if (hash[c1] > 0) counter--;
			hash[c1]--;
			// Move 'l' from zero and update minwindow & the string map
			while (counter == 0) {
				if ((r - l + 1) < minWindow) {
					minWindow = r - l + 1;
					minLeft = l;
				}
				char c2 = str.charAt(l);
				hash[c2]++;
				if (hash[c2] > 0) counter++;
				l++;
			}
			r++;
		}

		return minLeft != -1 ? str.substring(minLeft, minLeft + minWindow) : "";
	}

	public String minWindow2(String str, String pat) {
		int len1 = str.length(), len2 = pat.length();
		if (len1 == 0 || len2 == 0 || (len1 < len2)) return "";

		int[] hashStr = new int[256];
		int[] hashPat = new int[256];

		for (int i = 0; i < len2; i++)
			hashPat[pat.charAt(i)]++;

		int l = 0, r = 0, minWindow = Integer.MAX_VALUE, minLeft = -1, count = 0, index = 0;
		// Move 'r' one by one from zero
		while (r < len1) {
			index = str.charAt(r); // Get the String index
			// Increase the char count in hashStr
			hashStr[index]++;
			if (hashPat[index] != 0 && hashStr[index] <= hashPat[index]) count++;

			// Move 'l' from zero and update minwindow & the string map
			while (l <= r && count == len2) {
				if ((r - l + 1) < minWindow) {
					minWindow = r - l + 1;
					minLeft = l;
				}
				index = str.charAt(l); // Get the String index
				// Decrease the char count in strMap
				hashStr[index]--;
				if (hashStr[index] < hashPat[index]) count--;
				l++;
			}
			r++;
		}
		return minLeft != -1 ? str.substring(minLeft, minLeft + minWindow) : "";
	}

	/* Longest Substring Without Repeating Characters:
	 * Given a string, find the length of the longest substring without repeating characters. 
	 * Example 1: Input:"abcabcbb" Output: 3 Explanation: The answer is "abc", with the length of 3. 
	 * Example 2: Input: "pwwkew" Output: 3 Explanation: The answer is "wke", with the length of 3. 
	 * Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
	 */
	// Approach1: Sliding Window solution using Array to store the data
	//Follows the pattern
	public int lengthOfLongestSubstring1(String s) {
		int l = 0, r = 0, counter = 0, maxLen = 0, n = s.length();
		int[] hash = new int[128];
		while (r < n) {
			char c1 = s.charAt(r++);
			if (hash[c1] > 0) counter++;
			hash[c1]++;

			while (counter > 0) {
				char c2 = s.charAt(l);
				if (hash[c2] > 1) counter--;
				hash[c2]--;
				l++;
			}

			maxLen = Math.max(maxLen, r - l);
		}
		return maxLen;
	}

	public int lengthOfLongestSubstring2(String s) {
		if (s.length() == 0) return 0;

		int l = 0, r = 0, maxLen = 0, n = s.length();
		int[] countArr = new int[128];
		while (r < n) {
			char ch = s.charAt(r);
			if (countArr[ch] == 0) {
				countArr[ch]++;
				maxLen = Math.max(maxLen, r - l + 1);
				r++;
			} else {
				while (countArr[ch] > 0) {
					countArr[s.charAt(l)]--;
					l++;
				}
			}
		}
		return maxLen;
	}

	//Approach2: Sliding Window solution using map to store the data
	public int lengthOfLongestSubstring3(String s) {
		if (s.length() == 0) return 0;
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		int l = 0, r = 0, maxLen = 0, n = s.length();
		while (r < n) {
			char ch = s.charAt(r);
			if (!map.containsKey(ch)) {
				map.put(ch, r);
				maxLen = Math.max(maxLen, r - l + 1);
				r++;
			} else {
				l = Math.max(l, map.get(ch) + 1); //If char already present, move left index to repeating char
				map.remove(ch);
			}
		}
		return maxLen;
	}

	/*
	 * Longest Substring with At Most Two Distinct Characters:
	 * Given a string, find the longest substring that contains only two unique characters. For example, given "abcbbbbcccbdddadacb",
	 * the longest substring that contains 2 unique character is "bcbbbbcccb".
	 */
	//Follows the pattern
	public int lengthOfLongestSubstringTwoDistinct(String s) {
		int[] hash = new int[128];
		int l = 0, r = 0, maxLen = 0, counter = 0;

		while (r < s.length()) {
			char c1 = s.charAt(r);
			if (hash[c1] == 0) counter++;
			hash[c1]++;

			while (counter > 2) {
				char c2 = s.charAt(l);
				if (hash[c2] == 1) counter--;
				hash[c2]--;
				l++;
			}

			maxLen = Math.max(maxLen, r - l + 1);
			r++;
		}

		return maxLen;
	}

	public int lengthOfLongestSubstringTwoDistinct2(String s) {
		int max = 0, l = 0, r = 0, n = s.length();
		HashMap<Character, Integer> map = new HashMap<>();

		for (r = 0; r < n; r++) {
			char c1 = s.charAt(r);

			map.put(c1, map.getOrDefault(c1, 0) + 1);
			if (map.size() > 2) {
				max = Math.max(max, r - l);

				while (map.size() > 2) {
					char t = s.charAt(l);
					int count = map.get(t);
					if (count > 1) {
						map.put(t, count - 1);
					} else {
						map.remove(t);
					}
					l++;
				}
			}
		}

		return Math.max(max, n - l);
	}

	/*
	 * Longest Substring with At Most K Distinct Characters:
	 */
	//Follows the pattern
	public int lengthOfLongestSubstringKDistinct1(String s, int k) {
		int[] hash = new int[128];
		int l = 0, r = 0, maxLen = 0, counter = 0;

		while (r < s.length()) {
			char c1 = s.charAt(r);
			if (hash[c1] == 0) counter++;
			hash[c1]++;

			while (counter > k) {
				char c2 = s.charAt(l);
				if (hash[c2] == 1) counter--;
				hash[c2]--;
				l++;
			}

			maxLen = Math.max(maxLen, r - l + 1);
			r++;
		}

		return maxLen;
	}

	public int lengthOfLongestSubstringKDistinct2(String s, int k) {
		int result = 0, l = 0;
		HashMap<Character, Integer> map = new HashMap<>();
		for (int r = 0; r < s.length(); r++) {
			char ch = s.charAt(l);
			map.put(ch, map.getOrDefault(ch, 0) + 1);

			if (map.size() <= k) {
				result = Math.max(result, r - l + 1);
			} else {
				while (map.size() > k) {
					char c = s.charAt(l);
					int count = map.get(c);
					if (count == 1) {
						map.remove(c);
					} else {
						map.put(c, count - 1);
					}
					l++;
				}
			}
		}
		return result;
	}

	/*Find All Anagrams in a String/Anagram Pattern Search:
	 * Given a string s and a non-empty string p, find all the start indices of p's anagrams in s. Strings consists of
	 * lowercase English letters only and the length of both strings s and p will not be larger than 20,100. The order
	 * of output does not matter. 
	 * Example 1: Input: s: "cbaebabacd" p: "abc" Output: [0, 6] Explanation: The substring with start index = 0 is "cba", 
	 * which is an anagram of "abc". The substring with start index = 6 is "bac", which is an anagram of "abc".
	 */
	//Follows the pattern
	public List<Integer> findAnagrams1(String s, String p) {
		int l = 0, r = 0, counter = p.length();
		int[] hash = new int[128];
		List<Integer> result = new ArrayList<>();

		for (char ch : p.toCharArray())
			hash[ch]++;

		while (r < s.length()) {
			char c1 = s.charAt(r);
			if (hash[c1] > 0) counter--;
			hash[c1]--;

			while (counter == 0) {
				if ((r - l + 1) == p.length()) result.add(l);

				char c2 = s.charAt(l);
				if (hash[c2] == 0) counter++;
				hash[c2]++;
				l++;
			}
			r++;
		}

		return result;
	}

	public List<Integer> findAnagrams2(String s, String p) {
		int[] hash = new int[26];
		for (char c : p.toCharArray())
			hash[c - 'a']++;

		int l = 0, r = 0;
		List<Integer> result = new ArrayList<>();

		while (r < s.length()) {
			int index = s.charAt(r) - 'a';
			hash[index]--;

			// if we have seen the character too many times or it is a character that is not in the pattern, rewind the
			// starting index
			while (hash[index] < 0) {
				hash[s.charAt(l) - 'a']++;
				l++;
			}

			if (r - l + 1 == p.length()) {
				result.add(l);
				hash[s.charAt(l) - 'a']++;
				l++;
			}
			r++;
		}
		return result;
	}

	/* Longest Repeating Character Replacement: 
	 * Given a string that consists of only uppercase English letters, you can replace any letter in the string with
	 * another letter at most k times. Find the length of a longest substring containing all repeating letters you can
	 * get after performing the above operations. 
	 * Example: Input: s = "AABABBA", k = 1 Output: 4 Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA". 
	 * The substring "BBBB" has the longest repeating letters, which is 4.
	 */
	// Sliding Window approach
	// Time Complexity: O(n)
	public int characterReplacement1(String s, int k) {
		int[] charCount = new int[26];
		int l = 0, r = 0, maxCharCount = 0, maxLength = 0;
		while (r < s.length()) {
			char ch = s.charAt(r);
			charCount[ch - 'A']++;
			maxCharCount = Math.max(maxCharCount, charCount[ch - 'A']);
			while ((r - l + 1) - maxCharCount > k) {
				charCount[s.charAt(l) - 'A']--;
				l++;
			}
			maxLength = Math.max(maxLength, r - l + 1);
			r++;
		}
		return maxLength;
	}

	// Time Complexity: O(26n)
	public int characterReplacement2(String s, int k) {
		int n = s.length();
		if (n < k) return 0;

		// Added this to improve the performance; Solution will work without this
		int[] countArray = new int[26];
		for (int i = 0; i < n; i++)
			countArray[s.charAt(i) - 'A']++;

		int maxLen = 0;
		for (int i = 0; i < 26; i++) {
			char ch = (char) (i + 'A'); // Get the char one by one from A to Z;(input has only upper case)
			int l = 0, r = 0, count = 0;

			if (countArray[ch - 'A'] == 0) continue;

			while (r < n) {
				if (s.charAt(r) != ch) // if char mismatches, increase the count
					count++;

				while (k < count) { // Slide the window
					if (s.charAt(l) != ch) // Here, id char mismatches decrease the count
						count--;
					l++;
				}

				maxLen = Math.max(maxLen, r - l + 1); // Find the max length
				r++;
			}
		}

		return maxLen;
	}

	//TODO: Move below to consolidated module

	/* Substring with Concatenation of All Words:
	 * You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices
	 * of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening
	 * characters. 
	 * Example: Input: s = "barfoothefoobarman", words = ["foo","bar"] Output: [0,9] 
	 * Explanation: Substrings starting at index 0 and 9 are "barfoor" and "foobar" respectively. The output order does 
	 * not matter, returning [9,0] is fine too.
	 */
	public List<Integer> findSubstring(String s, String[] words) {
		List<Integer> result = new ArrayList<>();
		if (s == null || s.length() == 0 || words.length == 0) return result;

		HashMap<String, Integer> map = new HashMap<>();

		for (String word : words)
			map.put(word, map.getOrDefault(word, 0) + 1);

		int n = s.length(), len = words[0].length(), size = words.length;

		for (int i = 0; i <= n - len * size; i++) {
			HashMap<String, Integer> copy = new HashMap<>(map);
			for (int j = 0; j < size; j++) {
				String sub = s.substring(i + j * len, i + j * len + len);
				if (copy.containsKey(sub)) {
					if (copy.get(sub) == 1) copy.remove(sub);
					else copy.put(sub, copy.get(sub) - 1);
					if (copy.isEmpty()) {
						result.add(i);
						break;
					}
				} else {
					break;
				}
			}
		}

		return result;
	}

}