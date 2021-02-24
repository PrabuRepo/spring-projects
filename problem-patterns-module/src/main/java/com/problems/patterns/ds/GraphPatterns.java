package com.problems.patterns.ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import com.problems.patterns.crossdomains.CloneProblems;
import com.problems.patterns.crossdomains.WordProblems;

/* Imp Note for Graph Problems:
 * Use AdjList for the problems which has nodes starts from 0 to n-1.
 * 	 - LinkedList Array and boolean array to track the visited nodes
 * Use Map for irregular nodes like any random no, alphabet and string.
 *   - Map<Key, Set<Type> and hashset to track the visited nodes
 */
public class GraphPatterns {

	private WordProblems wordProblems = new WordProblems();

	private CloneProblems cloneProblems = new CloneProblems();

	/*************************** Type1: Graph DS Problems *******************/
	public void cloneGraph() {
		cloneProblems.cloneGraph1(null);
		cloneProblems.cloneGraph2(null);
		cloneProblems.cloneGraph3(null);
	}

	/*************************** Type2: Graph Cycle *******************/

	// Course Schedule I:
	//Topo Sort - BFS Approach - Using LinkedList array
	public boolean canFinish11(int numCourses, int[][] prerequisites) {
		List<Integer>[] adjList = new LinkedList[numCourses];
		Queue<Integer> queue = new LinkedList<>();
		int[] indegree = new int[numCourses];

		for (int i = 0; i < numCourses; i++)
			adjList[i] = new LinkedList<>();

		for (int[] pair : prerequisites) {
			//Note: src - pair[1], dest - pair[0]
			adjList[pair[1]].add(pair[0]);
			indegree[pair[0]]++;
		}

		for (int i = 0; i < indegree.length; i++)
			if (indegree[i] == 0) queue.offer(i);

		int count = 0;
		while (!queue.isEmpty()) {
			int course = queue.poll();
			count++;
			for (int adj : adjList[course])
				if (--indegree[adj] == 0) queue.offer(adj);
		}

		return count == numCourses;
	}

	//Topo Sort - BFS Approach - using Map
	public boolean canFinish12(int numCourses, int[][] prerequisites) {
		Map<Integer, Set<Integer>> graph = new HashMap<>();
		Queue<Integer> queue = new LinkedList<>();
		int[] indegree = new int[numCourses]; //or use Map

		for (int[] pair : prerequisites) {
			//src - pair[1], dest - pair[0]
			graph.putIfAbsent(pair[1], new HashSet<Integer>());
			graph.get(pair[1]).add(pair[0]);
			indegree[pair[0]]++;
		}

		for (int i = 0; i < indegree.length; i++)
			if (indegree[i] == 0) queue.offer(i);

		int count = 0;
		while (!queue.isEmpty()) {
			int course = queue.poll();
			count++;
			if (graph.get(course) == null) continue;
			for (int adj : graph.get(course))
				if (--indegree[adj] == 0) queue.offer(adj);
		}

		return count == numCourses;
	}

	//DFS Approach - Using LinkedList Array
	public boolean canFinish21(int noOfcourses, int[][] prerequisites) {
		// validation
		if (noOfcourses == 0 || prerequisites.length == 0) return true;

		// build graph
		LinkedList<Integer>[] graph = new LinkedList[noOfcourses];
		buildGraph(graph, prerequisites);

		// Check the graph has cycle using DFS
		return !hasCycle(graph, noOfcourses);
	}

	//DFS Approach - Using Map
	public boolean canFinish22(int noOfcourses, int[][] preReq) {
		// validation
		if (noOfcourses == 0 || preReq.length == 0) return true;

		// build graph
		Map<Integer, Set<Integer>> graph = new HashMap<>();
		for (int[] edge : preReq) {
			graph.putIfAbsent(edge[1], new HashSet<Integer>());
			graph.get(edge[1]).add(edge[0]);
		}

		Set<Integer> visited = new HashSet<>();
		Set<Integer> recStack = new HashSet<>();
		for (int i = 0; i < noOfcourses; i++) {
			if (!visited.contains(i)) {
				if (hasCycle(graph, i, visited, recStack)) return false;
			}
		}

		return true;
	}

	private boolean hasCycle(Map<Integer, Set<Integer>> graph, int v, Set<Integer> visited, Set<Integer> recStack) {
		// If this condition satisfies, then graph contains cycle
		if (recStack.contains(v)) return true;

		if (visited.contains(v)) return false;

		// Mark vertex as visited and set recursion stack
		visited.add(v);
		recStack.add(v);

		if (graph.get(v) != null) {
			for (int adjVertex : graph.get(v)) {
				if (hasCycle(graph, adjVertex, visited, recStack)) return true;
			}
		}
		// Reset the recursion stack 
		recStack.remove(v);
		return false;
	}

	private void buildGraph(LinkedList<Integer>[] graph, int[][] preReq) {
		int noOfEdges = preReq.length, node;
		for (int i = 0; i < noOfEdges; i++) {
			node = preReq[i][0];
			if (graph[node] == null) graph[node] = new LinkedList<>();
			graph[node].add(preReq[i][1]);
		}
	}

	private boolean hasCycle(LinkedList<Integer>[] graph, int n) {
		boolean[] visited = new boolean[n], recursionStack = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				if (hasCycle(graph, i, visited, recursionStack)) return true;
			}
		}
		return false;
	}

	private boolean hasCycle(LinkedList<Integer>[] adjList, int vertex, boolean[] visited, boolean[] recursionStack) {
		// If this condition satisfies, then graph contains cycle
		if (recursionStack[vertex]) return true;

		if (visited[vertex]) return false;

		// Mark vertex as visited and set recursion stack
		visited[vertex] = true;
		recursionStack[vertex] = true;

		if (adjList[vertex] != null) {
			ListIterator<Integer> iter = adjList[vertex].listIterator();
			while (iter.hasNext()) {
				int adjVertex = iter.next();
				if (hasCycle(adjList, adjVertex, visited, recursionStack)) return true;
			}
		}
		// Reset the recursion stack array
		recursionStack[vertex] = false;
		return false;

	}

	/*************************** Type3: Topo Sort(DFS/BFS(Kahn’s)) *******************/
	//	Course Schedule II - Find course order - Using topo sort(reverse)
	//BFS Solution: Time: O(V+E) and Space: O(V+E)
	public int[] findOrder(int n, int[][] prereq) {
		//n - no of courses
		if (n == 0) return null;
		Map<Integer, Set<Integer>> graph = new HashMap<>();
		Queue<Integer> queue = new LinkedList<Integer>();
		int[] indegree = new int[n], order = new int[n];
		int index = 0;

		for (int[] edge : prereq) {
			graph.putIfAbsent(edge[1], new HashSet<Integer>());
			graph.get(edge[1]).add(edge[0]);
			indegree[edge[0]]++;
		}

		for (int i = 0; i < n; i++) {
			if (indegree[i] == 0) {
				// Add the course to the order because it has no prerequisites.
				queue.add(i);
			}
		}

		while (!queue.isEmpty()) {
			int v = queue.poll();
			order[index++] = v;
			if (graph.get(v) == null) continue;
			for (int adjVertex : graph.get(v)) {
				if (--indegree[adjVertex] == 0) queue.add(adjVertex);
			}
		}

		return (index == n) ? order : new int[0];
	}

	// Reconstruct Itinerary - Map & Priority Queue & Topo Sort
	//Time: O(V+E), Space: O(V+E)
	public List<String> findItinerary(String[][] tickets) {
		Map<String, PriorityQueue<String>> map = new HashMap<>();
		for (String[] ticket : tickets) {
			map.putIfAbsent(ticket[0], new PriorityQueue<>());
			map.get(ticket[0]).add(ticket[1]);
		}
		LinkedList<String> result = new LinkedList<>();
		reconstructItinerary("JFK", map, result);
		return result;
	}

	// Using DFS, but remove the visted data from the priority queue
	public void reconstructItinerary(String s, Map<String, PriorityQueue<String>> map, LinkedList<String> result) {
		PriorityQueue<String> queue = map.get(s);
		while (queue != null && !queue.isEmpty()) {
			reconstructItinerary(queue.poll(), map, result);
		}
		result.addFirst(s);
	}

	/* Alien Dictionary:
	 * Given a sorted dictionary of an alien language, find order of characters Given a sorted dictionary (array of words)
	 * of an alien language, find order of characters in the language.
	 */
	//Alien Order using BFS algorithm	
	/*
	 * The first step to create a graph takes O(n + alhpa) time where n is number of given words and alpha is number of characters in given alphabet.
	 * The second step is also topological sorting. Note that there would be alpha vertices and at-most (n-1) edges in the graph. The time complexity 
	 * of topological sorting is O(V+E) which is O(n + aplha) here. So overall time complexity is O(n + aplha) + O(n + aplha) which is O(n + aplha).
	 * Time: O(V+E), Space: O(V+E)
	 */
	public String alienOrder1(String[] words) {
		Map<Character, Set<Character>> graph = new HashMap<>();
		Map<Character, Integer> inDegree = new HashMap<>();

		//Build Adjacent Graph using Map
		build(graph, inDegree, words);

		//Find order using topo sort DFS algorithm:
		return topoSortBFS(graph, inDegree);
	}

	private void build(Map<Character, Set<Character>> graph, Map<Character, Integer> inDegree, String[] words) {
		//Initialize the graph and indegree map
		for (String word : words) {
			for (char ch : word.toCharArray()) {
				graph.putIfAbsent(ch, new HashSet<>());
				inDegree.putIfAbsent(ch, 0);
			}
		}

		for (int i = 0; i < words.length - 1; i++) {
			String words1 = words[i];
			String words2 = words[i + 1];
			int j = 0;
			while (j < Math.min(words1.length(), words2.length())) {
				char ch1 = words1.charAt(j), ch2 = words2.charAt(j);
				if (ch1 != ch2) {
					graph.get(ch1).add(ch2);
					inDegree.put(ch2, inDegree.get(ch2) + 1);
					break;
				}
				j++;
			}
		}
	}

	private String topoSortBFS(Map<Character, Set<Character>> graph, Map<Character, Integer> inDegree) {
		Queue<Character> queue = new LinkedList<>();
		for (char c : inDegree.keySet()) { // Build queue with item of inDegree==0: means no edge points towards it.
			if (inDegree.get(c) == 0) queue.offer(c);
		}

		StringBuffer sb = new StringBuffer();
		while (!queue.isEmpty()) {
			char c = queue.poll();
			sb.append(c);
			for (char edgeNode : graph.get(c)) { // reduce edge degrees count since node:graph.get(c) has been processed
				inDegree.put(edgeNode, inDegree.get(edgeNode) - 1);
				if (inDegree.get(edgeNode) == 0) queue.offer(edgeNode);
			}
		}

		if (sb.length() != graph.size()) return "";
		return sb.toString();
	}

	//Alien Order using DFS algorithm	
	public String alienOrder2(String[] words) {
		if (words == null || words.length == 0) return "";

		Map<Character, List<Character>> graph = new HashMap<>();
		Map<Character, Integer> visited = new HashMap<>();
		StringBuilder sb = new StringBuilder();

		// Build graph, and visited map.
		buildGraph(graph, visited, words);

		// Topological sort with dfs
		for (char c : graph.keySet()) {
			if (!dfs(graph, visited, sb, c)) {
				return "";
			}
		}

		return sb.toString();
	}

	private boolean dfs(Map<Character, List<Character>> graph, Map<Character, Integer> visited, StringBuilder sb,
			Character c) {
		if (visited.get(c) == 1) return true;
		if (visited.get(c) == -1) return false;

		visited.put(c, -1);
		for (char edgeNode : graph.get(c)) {
			if (!dfs(graph, visited, sb, edgeNode)) {
				return false;
			}
		}
		visited.put(c, 1);
		sb.insert(0, c); // leaf element, add to buffer
		return true;
	}

	private void buildGraph(Map<Character, List<Character>> graph, Map<Character, Integer> visited, String[] words) {
		// Create nodes
		for (String word : words) {
			for (char c : word.toCharArray()) {
				if (!graph.containsKey(c)) {
					graph.put(c, new ArrayList<>());
					visited.put(c, 0);
				}
			}
		}

		// Build edges
		for (int i = 0; i < words.length - 1; i++) {
			int index = 0;
			while (index < words[i].length() && index < words[i + 1].length()) {
				char c1 = words[i].charAt(index);
				char c2 = words[i + 1].charAt(index);
				if (c1 != c2) {
					graph.get(c1).add(c2);
					break;
				}
				index++;
			}
		}
	}

	/*************************** Type4: Graph Traversals(BFS/DFS/UF) *******************/
	//Minimum Height Trees - BFS
	/*
	some statement for tree in graph theory:
	  (1) A tree is an undirected graph in which any two vertices are connected by exactly one path.
	  (2) Any connected graph who has n nodes with n-1 edges is a tree.
	  (3) The degree of a vertex of a graph is the number of edges incident to the vertex.
	  (4) A leaf is a vertex of degree 1. An internal vertex is a vertex of degree at least 2.
	  (5) A path graph is a tree with two or more vertices that is not branched at all.
	  (6) A tree is called a rooted tree if one vertex has been designated the root.
	  (7) The height of a rooted tree is the number of edges on the longest downward path between root and a leaf.
	
	Solution:(implementation is similar to the "BFS topological sort"): remove nodes from leave to root.     
	  1.We start from every end, by end we mean vertex of degree 1 (aka leaves). We let the pointers move the same speed. When two
	    pointers meet, we keep only one of them, until the last two pointers meet or one step away we then find the roots. 
	  2. It is easy to see that the last two pointers are from the two ends of the longest path in the graph.
	  3. Remove the leaves, update the degrees of inner vertexes. Then remove the new leaves. Doing so level by level until there 
	     are 2 or 1 nodes left.
	  The time complexity and space complexity are both O(n). Note that for a tree we always have V = n, E = n-1.        
	*/
	public List<Integer> findMinHeightTrees(int n, int[][] edges) {
		List<Integer> leaves = new ArrayList<>();
		if (n == 1) {
			leaves.add(0);
			return leaves;
		}
		if (n == 0 || edges.length == 0 || edges[0].length == 0) return leaves;
		List<Integer>[] adjList = buildAdjListUndirectedGraph(n, edges);
		for (int i = 0; i < n; i++)
			if (adjList[i].size() == 1) leaves.add(i);
		List<Integer> newLeaves;
		while (n > 2) {
			n -= leaves.size();
			newLeaves = new ArrayList<>();
			for (Integer node : leaves) {
				int neighbor = adjList[node].iterator().next();
				adjList[neighbor].remove(node);
				if (adjList[neighbor].size() == 1) newLeaves.add(neighbor);
			}
			leaves = newLeaves;
		}
		return leaves;
	}

	// d.Build undirected graph from given input(edges); where n - No of vertices, edges - Edge list
	public LinkedList<Integer>[] buildAdjListUndirectedGraph(int n, int[][] edges) {
		if (n == 0 || edges.length == 0) return null;
		LinkedList<Integer>[] adjList = new LinkedList[n];
		for (int i = 0; i < n; i++)
			adjList[i] = new LinkedList<>();
		for (int i = 0; i < edges.length; i++) {
			adjList[edges[i][0]].add(edges[i][1]);
			adjList[edges[i][1]].add(edges[i][0]);
		}
		return adjList;
	}

	// Same Solution, but Graph represented using Set instead of array
	public List<Integer> findMinHeightTrees2(int n, int[][] edges) {
		if (n == 1) return Collections.singletonList(0);
		List<Set<Integer>> adj = new ArrayList<>(n);
		for (int i = 0; i < n; ++i)
			adj.add(new HashSet<>());
		for (int[] edge : edges) {
			adj.get(edge[0]).add(edge[1]);
			adj.get(edge[1]).add(edge[0]);
		}
		List<Integer> leaves = new ArrayList<>();
		for (int i = 0; i < n; ++i)
			if (adj.get(i).size() == 1) leaves.add(i);
		while (n > 2) {
			n -= leaves.size();
			List<Integer> newLeaves = new ArrayList<>();
			for (int i : leaves) {
				int j = adj.get(i).iterator().next();
				adj.get(j).remove(i);
				if (adj.get(j).size() == 1) newLeaves.add(j);
			}
			leaves = newLeaves;
		}
		return leaves;
	}

	/* The Celebrity Problem:
	 * In a party of N people, only one person is known to everyone. Such a person may be present in the party, if yes,
	 * (s)he doesn’t know anyone in the party. We can only ask questions like “does A know B? “. Find the stranger
	 * (celebrity) in minimum number of questions.
	 */

	/*The Celebrity Problem
	 *  1. Using Graph
	 *  2. Using Stack
	 *  3. Efficient Approach(Without any data structure)
	 */
	// Using Graph: Time Complexity: O(n^2)
	public int celebrityProblem1(int[][] input, int n) {
		if (n == 0) return -1;

		int[] indegree = new int[n], outdegree = new int[n];

		// Find indegree, outdegree
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (input[i][j] == 1) {
					outdegree[i]++;
					indegree[j]++;
				}
			}
		}

		for (int i = 0; i < n; i++)
			if (indegree[i] == n - 1 && outdegree[i] == 0) return i;

		return -1;
	}

	// Using Stack
	public int celebrityProblem2(int[][] input, int n) {
		if (n == 0) return -1;

		Stack<Integer> stack = new Stack<>();

		// Push all the celebrities into a stack.
		for (int i = 0; i < n; i++)
			stack.push(i);

		// Pop off top two persons from the stack, discard one person based on status
		while (stack.size() > 1) {
			int a = stack.pop();
			int b = stack.pop();

			if (input[a][b] == 1) stack.push(b);
			else stack.push(a);
		}

		// Check the remained person in stack doesn’t have acquaintance with anyone else.
		int celeb = stack.pop();
		for (int i = 0; i < n; i++) {
			// check celebrity in all the rows, if cond satisfies return -1;
			if (i != celeb && (input[i][celeb] != 1 || input[celeb][i] != 0)) return -1;
		}

		return celeb;
	}

	// Efficient Approach(Without any data structure): Time Complexity: O(n)
	public int celebrityProblem3(int[][] input, int n) {
		if (n == 0) return -1;

		int celeb = 0;

		for (int j = 0; j < n; j++)
			if (input[celeb][j] == 1) // Find the celebrity from first row
				celeb = j;

		for (int i = 0; i < n; i++) {
			// check celebrity in all the rows, if cond satisfies return -1;
			if (i != celeb && (input[i][celeb] != 1 || input[celeb][i] != 0)) return -1;
		}

		return celeb;
	}

	public boolean knows(int[][] M, int i, int j) {
		return M[i][j] == 1 ? true : false;
	}

	public int findJudge(int N, int[][] trust) {
		if (N == 0) return -1;
		int[][] matrix = new int[N][N];

		// Build the Matrix using edges
		for (int[] edge : trust)
			matrix[edge[0] - 1][edge[1] - 1] = 1;

		Stack<Integer> stack = new Stack<>();

		for (int i = 0; i < N; i++)
			stack.push(i);

		while (stack.size() > 1) {
			int i = stack.pop();
			int j = stack.pop();
			if (matrix[i][j] == 1) stack.push(j);
			else stack.push(i);
		}

		int judge = stack.pop();

		for (int i = 0; i < N; i++) {
			if (i != judge && (matrix[judge][i] == 1 || matrix[i][judge] != 1)) return -1;
		}

		return judge + 1;
	}

	//	Word Ladder I, II - BFS -> Refer: WordProblem Patterns
	public void wordLadderI() {
		wordProblems.wordLadderI1(null, null, null);
		wordProblems.wordLadderI2(null, null, null);
	}

	public void wordLadderII() {
		wordProblems.wordLadderII(null, null, null);
	}

}
