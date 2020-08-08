package com.basic.datastructures.operations;

import java.util.List;

public interface GraphOperations {

	// DG - Directed Graph
	// UG - Undirected Graph
	public void buildDirectedGraph(int[][] edges);

	public void buildUndirectedGraph(int[][] edges);

	public void buildWeightedDG(int[][] edges);

	public void buildWeightedUG(int[][] edges);

	public int findNumberOfNodes(int[][] edges);

	public void printGraph();

	public List<Integer> dfsRecursive(int source);

	// DFS Iterative Approach using Stack
	public List<Integer> dfsIterative(int source);

	public int dfsDisconnectedGraph();

	// BFS Iterative Approach using Queue
	public List<Integer> bfsIterative(int source);

	public int bfsDisconnectedGraph();

	/* Topological Sort:
	 * Topological sorting for Directed Acyclic Graph (DAG) is a linear ordering of vertices such that for "every
	 * directed edge uv, vertex u comes before v in the ordering". Topological Sorting for a graph is not possible if the
	 * graph is not a DAG.
	 * Fact: A DAG G has at least one vertex with in-degree 0 and one vertex with out-degree 0.
	 *   Approach1: DFS Algorithm
	 *   Approach2: BFS Algorithm (Kahn's Algorithm)
	 */
	public List<Integer> topologicalSortDfs();

	/* BFS Algorithm (Kahn's Algorithm) for Topological Sorting:
	 * A DAG G has at least one vertex with in-degree 0 and one vertex with out-degree 0.
	 */
	public List<Integer> topologicalSortBfs();

	public boolean detectCycleInDG();

	public boolean detectCycleInUG();

	// MST - Minimum Spanning Tree
	/* A minimum spanning tree (MST) or minimum weight spanning tree for a weighted, connected and undirected graph is a spanning tree with
	 * weight less than or equal to the weight of every other spanning tree. The weight of a spanning tree is the sum of weights given to 
	 * each edge of the spanning tree. A minimum spanning tree has (V – 1) edges where V is the number of vertices in the given graph.
	 */
	public void mstPrimsAlg();

	public void mstKruskalsAlg();

	// SP - Shortest Path Alg
	public void spDijikstraAlg(int source);

	/*
	 * Dijkstra follows Greed Alg; Bellmanford alg follows DP algorithms and it finds shortest paths from src to all vertices in the given graph.
	 * Dijkstra doesn’t work for Graphs with negative weight edges, Bellman-Ford works for such graphs. Time complexity of Bellman-Ford is 
	 * O(VE), which is more than Dijkstra.
	 * 
	 * How does this work? Like other Dynamic Programming Problems, the algorithm calculate shortest paths in bottom-up manner. It first
	 * calculates the shortest distances which have at-most one edge in the path. Then, it calculates shortest paths with at-most 2 edges, 
	 * and so on. After the i-th iteration of outer loop, the shortest paths with at most i edges are calculated. There can be maximum 
	 * |V| – 1 edges in any simple path, that is why the outer loop runs |v| – 1 times.
	 */
	public void spBellmanFordAlg(int source);

	/* The Floyd Warshall Algorithm (Dynamic Programming) is for solving the All Pairs Shortest Path problem. The problem is 
	 * to find shortest distances between every pair of vertices in a given edge weighted directed Graph.
	 */
	public void spFloydWarshallAlg();

}
