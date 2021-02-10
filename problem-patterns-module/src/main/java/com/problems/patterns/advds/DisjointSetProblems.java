package com.problems.patterns.advds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.common.utilities.DisjointSet;

public class DisjointSetProblems {
	private static final int[][] DIRS = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

	// Using DisJoint Set/Union-Find DS
	public int numIslands(char[][] grid) {
		if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

		int m = grid.length, n = grid[0].length, count = 0;

		DisjointSet ds = new DisjointSet(m * n);

		// Count all the land or 1 in the array
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				if (grid[i][j] == '1') {
					ds.set(i * n + j, i * n + j); // rowIndex * colSize + colIndex
					count++;
				}

		// Group the connected components(meaning 1's in the 4 directions)
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (grid[i][j] == '0') continue;
				int node1 = i * n + j;
				// Check in four directions
				for (int[] dir : DIRS) {
					int mi = i + dir[0];
					int mj = j + dir[1];
					if (mi < 0 || mi >= m || mj < 0 || mj >= n || grid[mi][mj] == '0') continue;

					int node2 = mi * n + mj;
					if (!ds.union(node1, node2)) count--;
				}
			}
		}

		return count;
	}

	/*
	 * Number of Islands II: 
	 *     A 2d grid map of m rows and n columns is initially filled with water. We may perform an addLand operation which turns 
	 * the water at position (row, col) into a land. Given a list of positions to operate, count the number of islands after each
	 *  addLand operation. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. 
	 *  You may assume all four edges of the grid are all surrounded by water.
	 *  
	 * Solution using Union-Find:
	 * UNION operation is only changing the root parent so the running time is O(1).
	 * FIND operation is proportional to the depth of the tree. If N is the number of points added, the average running time is O(logN), and a sequence of 4N operations take O(NlogN). 
	 * If there is no balancing, the worse case could be O(N^2).
	 */

	public List<Integer> numIslands2(int m, int n, int[][] positions) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int count = 0;

		// Create the disjoint set
		DisjointSet ds = new DisjointSet(m * n);
		Arrays.fill(ds.nodes, -1);// Initially set -1s in all the set

		// Traverse edges one by one
		for (int[] p : positions) {
			count++;
			int node1 = p[0] * n + p[1]; // i * rowSize + j
			ds.set(node1, node1);// set root to be itself for each node

			for (int[] dir : DIRS) {
				int i = p[0] + dir[0];
				int j = p[1] + dir[1];
				int node2 = i * n + j;
				if (i < 0 || i >= m || j < 0 || j >= n || ds.nodes[node2] == -1) continue;

				if (!ds.union(node1, node2)) count--;
			}
			result.add(count);
		}
		return result;
	}
}
