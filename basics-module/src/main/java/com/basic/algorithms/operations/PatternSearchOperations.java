package com.basic.algorithms.operations;

public interface PatternSearchOperations {

	// Naive Pattern Searching; Time complexity:O(mn) = Exactly O(m(n-m))
	public void naivePatternSearching(String str, String pattern);

	// KMP(Knuth Morris Pratt) Algorithm
	public void KMPAlgorithm(String str, String pattern);

	// Rabin-Karp Algorithm
	public void rabinKarpAlgorithm(String str, String pattern);

	// Finite Automata
	public void finiteAutomata(String str, String pattern);

	//Z Algorithm
	public void zAlgorithm(String str, String pattern);
}
