package com.basic.datastructures.adv;

import java.util.ArrayList;
import java.util.List;

import com.basic.datastructures.adv.operations.TrieOperations;
import com.common.model.TrieNode;

/*
 * A trie is a tree-like data structure whose nodes store the letters of an alphabet. By structuring the nodes in a 
 * particular way, words and strings can be retrieved from the structure by traversing down a branch path of the tree.
 * 
 * Trie or Retrieval or prefix tree is a tree data structure, which is used for retrieval of a key in a dataset
 * of strings. There are various applications of this very efficient data structure such as :
 *    - Auto Complete
 *    - Spell Checker
 *    - IP Routing(Longest Prefix Search)
 *    - T9 Predective Search
 *    - Solving Word Games - Word Boggle
 *    
 *  For single string:
 *  	Time Complexity for Insert, Search: O(m); m - length of string/word
 *  	Space Complexity: O(alphabet_size * m)
 *  For collection of string:
 *  	Time Complexity for Insert, Search: O(m * n); n is no of string/words 
 *  	Space Complexity: O(alphabet_size * m * n)
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
		//ob.testTrieNodeUsingArray();
		ob.testTrieNodeUsingMap();
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
		trie.remove("the");
		trie.remove("the");
		System.out.println("after deletion..");
		System.out.println("the --- " + trie.search("the"));
		System.out.println("their --- " + trie.search("their"));
		System.out.println("these --- " + trie.search("these"));

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

		System.out.println("Wildcard Match: Search with special chars: ");
		System.out.println(".he --- " + trie.wildcardMatch(".he"));
		System.out.println("t.e --- " + trie.wildcardMatch("t.e"));
		System.out.println("answ.t --- " + trie.wildcardMatch("answ.t"));

		// Delete Operation
		System.out.println("After delete operation");
		trie.remove("the");

		if (trie.search("the") == true) System.out.println("the --- " + "Present in trie");
		else System.out.println("the --- " + "Not present in trie");

		System.out.println("Delete the string: ");
		trie.remove("the");
		trie.remove("the");
		System.out.println("after deletion..");
		System.out.println("the --- " + trie.search("the"));
		System.out.println("their --- " + trie.search("their"));
		System.out.println("these --- " + trie.search("these"));
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
		return result != null ? result.isEndOfWord : false;
	}

	@Override
	public boolean prefixSearch(String prefix) {
		TrieNode result = wordSearch(prefix);
		return result != null ? true : false;
	}

	private TrieNode wordSearch(String word) {
		TrieNode curr = root;
		for (int i = 0; i < word.length(); i++) {
			int index = word.charAt(i) - 'a';
			curr = curr.children[index];
			if (curr == null) return null;
		}
		return curr;
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

	//TypeAhead Search or Auto Suggestions or Auto Complete
	@Override
	public List<String> typeAheadSearch(String prefix) {

		List<String> words = new ArrayList<>();

		TrieNode node = wordSearch(prefix);

		if (node != null) {
			getWords(node, words);
			words.forEach(k -> System.out.println(k));
		}
		return words;
	}

	private void getWords(TrieNode node, List<String> words) {
		if (node == null) return;
		if (node.isEndOfWord) words.add(node.word);

		for (int i = 0; i < 26; i++) {
			if (node.children[i] != null) {
				getWords(node.children[i], words);
			}
		}
	}

	@Override
	public void remove(String word) {
		delete(root, word, 0);
	}

	private TrieNode delete(TrieNode node, String str, int index) {
		if (node == null) return null;

		if (index == str.length()) {
			node.isEndOfWord = false;
			//This check is to delete the last node, if there is no child node
			if (isEmpty(node)) node = null;
			return node;
		}

		if (index >= str.length()) return node;

		int chIndex = str.charAt(index) - 'a';
		node.children[chIndex] = delete(node.children[chIndex], str, index + 1);
		//This check is to delete the remaining nodes which doesn't have any child nodes.
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
		for (char ch : word.toCharArray()) {
			curr = curr.childNodes.computeIfAbsent(ch, node -> new TrieNode());
			// or
			/*if (!curr.childNodes.containsKey(ch)) {
				curr.childNodes.put(ch, new TrieNode());
			}
			curr = curr.childNodes.get(ch);*/
		}
		curr.isEndOfWord = true;
		curr.word = word;
	}

	@Override
	public boolean search(String word) {
		TrieNode result = wordSearch(word);
		return result != null ? result.isEndOfWord : false;
	}

	@Override
	public boolean prefixSearch(String prefix) {
		TrieNode result = wordSearch(prefix);
		return result != null ? true : false;
	}

	private TrieNode wordSearch(String word) {
		TrieNode curr = root;
		for (char ch : word.toCharArray()) {
			curr = curr.childNodes.get(ch);
			if (curr == null) return null;
		}
		return curr;
	}

	@Override
	public boolean wildcardMatch(String word) {
		return dfs(root, word, 0);
	}

	private boolean dfs(TrieNode curr, String word, int index) {
		if (index == word.length() && curr.isEndOfWord) return true;
		if (index >= word.length()) return false;

		char ch = word.charAt(index);
		if (ch == '.') {
			for (char ch2 : curr.childNodes.keySet()) {
				if (dfs(curr.childNodes.get(ch2), word, index + 1)) return true;
			}
		} else {
			TrieNode next = curr.childNodes.get(ch);
			if (next != null) return dfs(next, word, index + 1);
		}
		return false;
	}

	@Override
	public void remove(String word) {
		delete(root, word, 0);
	}

	public boolean delete(TrieNode current, String word, int index) {
		if (word.length() == index && current.isEndOfWord) {
			// Reset EOW to false
			current.isEndOfWord = false;
			/*If last node of the word has childNodes, then simple reset the EOW.
			  If last node doesn't have childNodes, then below cond satisfies and keep deleting the node below*/
			return current.childNodes.size() == 0;
		}

		if (index >= word.length()) return false;

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

	//TypeAhead Search or Auto Suggestions or Auto Complete
	@Override
	public List<String> typeAheadSearch(String prefix) {
		List<String> words = new ArrayList<>();

		TrieNode node = wordSearch(prefix);

		if (node != null) {
			getWords(node, words);
			words.forEach(k -> System.out.println(k));
		}
		return words;
	}

	private void getWords(TrieNode node, List<String> words) {
		if (node == null) return;

		if (node.isEndOfWord) words.add(node.word);

		for (char ch : node.childNodes.keySet()) {
			getWords(node.childNodes.get(ch), words);
		}
	}

}