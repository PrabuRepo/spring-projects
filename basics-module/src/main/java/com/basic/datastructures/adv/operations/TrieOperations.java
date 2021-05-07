package com.basic.datastructures.adv.operations;

import java.util.List;

public interface TrieOperations {

	/** Add: Add a word into the trie. */
	public void add(String word);

	/** Search Whole Word: Returns if the word is in the trie. */
	public boolean search(String word);

	/** Prefix Search: Returns if there is any word in the trie that starts with the given prefix. */
	public boolean prefixSearch(String prefix);

	/**
	 * Wildcard Match: Returns if the word is in the data structure. A word could contain the dot
	 * character '.' to represent any one letter.
	 */
	public boolean wildcardMatch(String word);

	/** Remove: Remove the given string. */
	public void remove(String word);

	/** Auto Suggestions: Auto-complete feature using Trie */
	/* Type ahead strings or Auto-complete or Auto Suggestions feature using Trie:
	 * We are given a Trie with a set of strings stored in it. Now the user types in a prefix of his search query, we
	 * need to give him all recommendations to auto-complete his query based on the strings stored in the Trie. We
	 * assume that the Trie stores past searches by the users.
	 * For example if the Trie store {“abc”, “abcd”, “aa”, “abbbaba”} and the User types in “ab” 
	 * then he must be shown {“abc”, “abcd”, “abbbaba”}.
	 * Time: O(prefixLen + noOfStringInDictionary);
	 */
	public List<String> typeAheadSearch(String prefix);

}
