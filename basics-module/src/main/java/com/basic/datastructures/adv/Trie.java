package com.basic.datastructures.adv;

import java.util.ArrayList;
import java.util.List;

import com.basic.datastructures.adv.operations.TrieOperations;
import com.common.model.TrieNode;

/*
 * Trie or Retrieval or prefix tree is a tree data structure, which is used for retrieval of a key in a dataset
 * of strings. There are various applications of this very efficient data structure such as :
 *    - Auto Complete
 *    - Spell Checker
 *    - IP Routing(Longest Prefix Search)
 *    - T9 Predective Search
 *    - Solving Word Games - Word Boggle
 *    
 *  Time Complexity for Insert, Search: O(m); m - length of string
 *  Space Complexity: O(alphabet_size * len of string * no of string)
 *  
 *  Why Trie?
 *   - Finding all keys with a common prefix.
 *   - Enumerating a dataset of strings in lexicographical order. 
 *   
 *   This class implemented "Add and Search Word" using TRIE data structure.
 */
public class Trie {
	public static void main(String[] args) {
		Trie ob = new Trie();
		ob.testTrieNodeUsingArray();
		// ob.testTrieNodeUsingMap();
	}

	public void testTrieNodeUsingArray() {
		// Input keys (use only 'a' through 'z' and lower case)
		String keys[] = { "the", "mhe", "a", "there", "answer", "any", "by", "bye", "their" };
		Trie1 trie = new Trie1();

		System.out.println("Insert the list of strings: ");
		for (int i = 0; i < keys.length; i++)
			trie.add(keys[i]);

		System.out.println("Search the words: ");
		System.out.println("the --- " + trie.search("the"));
		System.out.println("these --- " + trie.search("these"));
		System.out.println("their --- " + trie.search("their"));
		System.out.println("thaw --- " + trie.search("thaw"));

		System.out.println("Search the prefix: ");
		System.out.println("an --- " + trie.search("an"));
		System.out.println("an --- " + trie.prefixSearch("an"));
		System.out.println("by --- " + trie.search("th"));
		System.out.println("by --- " + trie.prefixSearch("th"));

		System.out.println("Wildcard Match: Search with special chars: ");
		System.out.println(".he --- " + trie.wildcardMatch(".he"));
		System.out.println("t.e --- " + trie.wildcardMatch("t.e"));

		System.out.println("Auto Suggestions/Auto Complete: ");
		trie.typeAheadSearch("th");
		trie.typeAheadSearch("yrt");
		trie.typeAheadSearch("bye");
		trie.typeAheadSearch("a");

		System.out.println("Delete the string: ");
		trie.remove("mhe");
		System.out.println("after deletion..");
		System.out.println("an --- " + trie.search("mhe"));

	}

	public void testTrieNodeUsingMap() {
		Trie2 trie = new Trie2();
		// Input keys (use only 'a' through 'z' and lower case)
		String keys[] = { "the", "mhe", "a", "these", "answer", "any", "by", "bye", "their" };

		for (int i = 0; i < keys.length; i++)
			trie.add(keys[i]);

		// Search for different keys
		if (trie.search("the") == true) System.out.println("the --- " + "Present in trie");
		else System.out.println("the --- " + "Not present in trie");

		if (trie.search("these") == true) System.out.println("these --- " + "Present in trie");
		else System.out.println("these --- " + "Not present in trie");

		if (trie.search("their") == true) System.out.println("their --- " + "Present in trie");
		else System.out.println("their --- " + "Not present in trie");

		if (trie.search("thaw") == true) System.out.println("thaw --- " + "Present in trie");
		else System.out.println("thaw --- " + "Not present in trie");

		// Delete Operation
		System.out.println("After delete operation");
		trie.remove("the");

		if (trie.search("the") == true) System.out.println("the --- " + "Present in trie");
		else System.out.println("the --- " + "Not present in trie");

		trie.remove("these");

		if (trie.search("these") == true) System.out.println("these --- " + "Present in trie");
		else System.out.println("these --- " + "Not present in trie");
	}
}

/**
 * Trie Implementation using Array:
 */
class Trie1 implements TrieOperations {

	TrieNode root;

	public Trie1() {
		root = new TrieNode();
	}

	@Override
	public void add(String word) {
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
		current.word = word;
	}

	@Override
	public boolean search(String word) {
		TrieNode result = wordSearch(word);
		return result == null ? false : (result != null && result.isEndOfWord);
	}

	@Override
	public boolean prefixSearch(String prefix) {
		TrieNode result = wordSearch(prefix);
		return result == null ? false : true;
	}

	private TrieNode wordSearch(String word) {
		int index;
		TrieNode current = root;

		for (int i = 0; i < word.length(); i++) {
			index = word.charAt(i) - 'a';
			if (current.children[index] == null) return null;

			current = current.children[index];
		}
		return current;
	}

	@Override
	public boolean wildcardMatch(String word) {
		return dfs(root, word, 0);
	}

	private boolean dfs(TrieNode node, String word, int index) {
		if (node.isEndOfWord && word.length() == index) return true;

		if (index >= word.length()) return false;

		char ch = word.charAt(index);

		if ('.' == ch) {
			for (int i = 0; i < 26; i++) {
				if (node.children[i] != null) {
					if (dfs(node.children[i], word, index + 1)) return true;
				}
			}
		} else {
			TrieNode next = node.children[ch - 'a'];
			if (next != null) return dfs(next, word, index + 1);
		}

		return false;
	}

	@Override
	public void typeAheadSearch(String prefix) {
		TrieNode node = wordSearch(prefix);

		if (node == null) {
			System.out.println("Prefix not found!");
		} else {
			List<String> suggestions = new ArrayList<>();
			autoSuggestions(node, suggestions);
			suggestions.stream().forEach(k -> System.out.print(k + "-> "));
			System.out.println();
		}
	}

	// Autocomplete or Autosuggestions
	private void autoSuggestions(TrieNode node, List<String> suggestions) {
		if (node == null) return;
		if (node.isEndOfWord) suggestions.add(node.word);

		for (int i = 0; i < 26; i++) {
			if (node.children[i] != null) {
				autoSuggestions(node.children[i], suggestions);
			}
		}
	}

	@Override
	public void remove(String word) {
		delete(root, word, 0);
	}

	public TrieNode delete(TrieNode node, String str, int index) {
		if (node == null) return null;

		if (index == str.length()) {
			node.isEndOfWord = false;
			if (!node.isEndOfWord && isEmpty(node)) node = null;
			return node;
		}

		int chIndex = str.charAt(index) - 'a';
		node.children[chIndex] = delete(node.children[chIndex], str, index + 1);

		if (!node.isEndOfWord && isEmpty(node)) node = null;

		return node;
	}

	private boolean isEmpty(TrieNode root) {
		if (root == null) return true;

		for (int i = 0; i < 26; i++)
			if (root.children[i] != null) return false;

		return true;
	}

}

class Trie2 implements TrieOperations {
	private TrieNode root; // Using Map Impl

	public Trie2() {
		root = new TrieNode();
	}

	@Override
	public void add(String word) {
		TrieNode curr = root;
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			curr = curr.childNodes.computeIfAbsent(ch, c -> new TrieNode());
			/* TrieNode childNode = curr.childNodes.get(ch);
			if (childNode == null) {
				childNode = new TrieNode();
				curr.childNodes.put(ch, childNode);
			}
			curr = childNode;*/
		}
		curr.isEndOfWord = true;
	}

	@Override
	public boolean search(String word) {
		TrieNode current = root;
		for (int i = 0; i < word.length(); i++) {
			TrieNode childNode = current.childNodes.get(word.charAt(i));
			if (childNode == null) return false;
			current = childNode;
		}
		return current.isEndOfWord;
	}

	@Override
	public boolean prefixSearch(String prefix) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wildcardMatch(String word) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void remove(String word) {
		delete(root, word, 0);
	}

	public boolean delete(TrieNode current, String word, int index) {
		if (word.length() == index) {
			// If EOW is false, then word is not present in the Trie DS
			/*if (!current.isEndOfWord)
				return false;*/
			// Reset EOW to false
			current.isEndOfWord = false;
			/*If last node of the word has childNodes, then simple reset the EOW.
			  If last node doesn't have childNodes, then below cond satisfies and keep deleting the node below*/
			return current.childNodes.size() == 0;
		}

		char ch = word.charAt(index);
		TrieNode next = current.childNodes.get(ch);
		// if char is not present in the node, then next will be null.
		if (next == null) return false;

		// Recursive call for every char in the word
		boolean deleteNodeFlag = delete(next, word, index + 1);
		// If this deleteNodeFlag is true, then remove one by one from the map.
		if (deleteNodeFlag) {
			current.childNodes.remove(ch);
			return current.childNodes.size() == 0;
		}

		return false;
	}

	@Override
	public void typeAheadSearch(String prefix) {
		// TODO Auto-generated method stub

	}

}