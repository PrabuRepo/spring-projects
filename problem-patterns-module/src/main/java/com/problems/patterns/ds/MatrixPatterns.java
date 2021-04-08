package com.problems.patterns.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.common.model.Point;
import com.common.utilities.DisjointSet;
import com.common.utilities.Utils;
import com.problems.patterns.advds.DisjointSetProblems;
import com.problems.patterns.crossdomains.WordProblems;

public class MatrixPatterns {
	private DisjointSetProblems disjointSetProblems = new DisjointSetProblems();

	private WordProblems wordProblems = new WordProblems();

	/************** Type1: Basic Matrix Problems ***************/
	// Anti clock wise direction:
	// Apprach1: Take transpose, reverse the column & use an additional space
	public void rotate90AntiClockwise1(int[][] matrix) {
		System.out.println("Before: ");
		Utils.printMatrix(matrix);

		matrix = transpose(matrix);
		reverseColumns(matrix);

		System.out.println("After: ");
		Utils.printMatrix(matrix);
	}

	public int[][] transpose(int[][] A) {
		int row = A.length, col = A[0].length;
		int[][] transpose = new int[col][row];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				transpose[j][i] = A[i][j];
			}
		}
		return transpose;
	}

	public void reverseColumns(int[][] A) {
		int n = A.length, rowStart = 0, rowEnd = 0, temp = 0;
		for (int col = 0; col < n; col++) {
			rowStart = 0;
			rowEnd = n - 1;
			// Swap the element one by one
			while (rowStart < rowEnd) {
				temp = A[rowStart][col];
				A[rowStart][col] = A[rowEnd][col];
				A[rowEnd][col] = temp;
				rowStart++;
				rowEnd--;
			}
		}
	}

	// Approach2: without using any space
	public void rotate90AntiClockwise2(int[][] matrix) {
		System.out.println("Before: ");
		Utils.printMatrix(matrix);

		int n = matrix.length, temp = 0;
		for (int i = 0; i < n / 2; i++) {
			for (int j = i; j < n - i - 1; j++) {
				// Save the top
				temp = matrix[i][j];
				// Move right to top
				matrix[i][j] = matrix[j][n - i - 1];
				// Move bottom to right
				matrix[j][n - i - 1] = matrix[n - i - 1][n - j - 1];
				// Move left to bottom
				matrix[n - i - 1][n - j - 1] = matrix[n - j - 1][i];
				// Assign the top(from temp) to left
				matrix[n - j - 1][i] = temp;
			}
		}

		System.out.println("After: ");
		Utils.printMatrix(matrix);
	}

	// Clock wise direction:
	// Apprach1: Take transpose, reverse the row & use an additional space
	public void rotate90Clockwise1(int[][] matrix) {
		System.out.println("Before: ");
		Utils.printMatrix(matrix);

		matrix = transpose(matrix);
		reverseRows(matrix);

		System.out.println("After: ");
		Utils.printMatrix(matrix);
	}

	public void reverseRows(int[][] A) {
		int n = A.length, colStart = 0, colEnd = 0, temp = 0;
		for (int row = 0; row < n; row++) {
			colStart = 0;
			colEnd = n - 1;
			// Swap the element one by one
			while (colStart < colEnd) {
				temp = A[row][colStart];
				A[row][colStart] = A[row][colEnd];
				A[row][colEnd] = temp;
				colStart++;
				colEnd--;
			}
		}
	}

	// Approach2: without using any space
	public void rotate90Clockwise2(int[][] matrix) {

		System.out.println("Before: ");
		Utils.printMatrix(matrix);

		int n = matrix.length, temp = 0;
		for (int i = 0; i < n / 2; i++) {
			for (int j = i; j < n - i - 1; j++) {
				// Save the top
				temp = matrix[i][j];
				// Move left to top
				matrix[i][j] = matrix[n - j - 1][i];
				// Move bottom to left
				matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
				// Move right to bottom
				matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
				// Assign the top(from temp) to right
				matrix[j][n - i - 1] = temp;
			}
		}

		System.out.println("After: ");
		Utils.printMatrix(matrix);
	}

	/* Spiral Matrix:
	 * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
	 * 	Example 1:
		Input:
			[
			[ 1, 2, 3 ],
			[ 4, 5, 6 ],
			[ 7, 8, 9 ]
			]
		Output: [1,2,3,6,9,8,7,4,5]
	 */

	public int[] spiralOrder(int[][] matrix) {
		int r = matrix.length, c = matrix[0].length;
		int[] result = new int[r * c];

		if (matrix.length == 0 || matrix[0].length == 0) return result;
		int left = 0, right = c - 1, top = 0, bottom = r - 1, index = 0;

		while (top <= bottom && left <= right) {
			for (int j = left; j <= right; j++)
				result[index++] = matrix[top][j];
			top++;

			for (int i = top; i <= bottom; i++)
				result[index++] = matrix[i][right];
			right--;

			if (top > bottom || left > right) break;

			for (int j = right; j >= left; j--)
				result[index++] = matrix[bottom][j];
			bottom--;

			for (int i = bottom; i >= top; i--)
				result[index++] = matrix[i][left];
			left++;
		}

		return result;
	}

	/*
	 * Spiral Matrix II: 
	 * Given a positive integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.
	 */
	public int[][] generateMatrix(int n) {
		int l = 0, r = n - 1, t = 0, b = n - 1;
		int[][] mat = new int[n][n];
		int val = 1;
		while (l <= r && t <= b) {
			for (int j = l; j <= r; j++)
				mat[t][j] = val++;
			t++;
			for (int i = t; i <= b; i++)
				mat[i][r] = val++;
			r--;
			if (l > r || t > b) break;
			for (int j = r; j >= l; j--)
				mat[b][j] = val++;
			b--;
			for (int i = b; i >= t; i--)
				mat[i][l] = val++;
			l++;
		}

		return mat;
	}

	/************** Type3: Binary Search in Matrix ***************/
	/* Search element in a sorted matrix
	 *   - Integers in each row are sorted from left to right.
	 *   - The first integer of each row is greater than the last integer of the previous row.
	 *   Time Complexity: O(log(m*n)) => O(logm) +O(logn)
	 */
	public boolean searchMatrixI(int[][] matrix, int target) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;

		int r = matrix.length, c = matrix[0].length;
		int l = 0, h = r * c - 1, m, i, j;

		while (l <= h) {
			m = (l + h) / 2;
			i = m / c;
			j = m % c;
			if (target == matrix[i][j]) return true;
			else if (target < matrix[i][j]) h = m - 1;
			else l = m + 1;
		}
		return false;
	}

	/* Search in a row wise and column wise sorted matrix
	 *  - Integers in each row are sorted in ascending from left to right.
	 *  - Integers in each column are sorted in ascending from top to bottom.
	 *  Time Complexity: O(m+n). Assume that target is opposite corner of the matrix, then we should iterate all
	 *  the rows and columns once. So worst case time is m+n
	 */
	public boolean searchMatrixII(int[][] matrix, int target) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
		int r = matrix.length, c = matrix[0].length;
		int i = r - 1, j = 0;
		while (i >= 0 && j < c) {
			if (target == matrix[i][j]) return true;
			else if (target < matrix[i][j]) i--;
			else j++;
		}
		return false;
	}

	/************** Type5: Matrix-4/8 directions Traversals ***************/
	// Matrix 4/8 directions flow problems: These problems can be solved using DFS, BFS, Union Find, Back or simple
	// search
	private static final int[] rowSet4 = { -1, 1, 0, 0 };
	private static final int[] colSet4 = { 0, 0, -1, 1 };
	private static final int[] rowSet8 = { -1, 1, 0, 0, -1, -1, 1, 1 };
	private static final int[] colSet8 = { 0, 0, -1, 1, -1, 1, -1, 1 };
	//4 Directions 
	private static final int[][] DIRS = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

	/************** 5.1.Modify/Change the Region: ***************/
	/*Flood Fill/Paint Fill Algorithm:
	 * Flood fill Algorithm – how to implement fill() in paint?
	 * 	In MS-Paint, when we take the brush to a pixel and click, the color of the region of that pixel is replaced with 
	 * a new selected color. 
	 * Following is the problem statement to do this task:
	 * Given a 2D screen, location of a pixel in the screen ie(x,y) and a color(K), your task is to replace color of the 
	 * given pixel and all adjacent(excluding diagonally adjacent) same colored pixels with the given color K.
	 */
	//Time Complexity: O(m*n), where m-row size, n-col size
	public void floodFillAlg(int[][] m, int x, int y, int newColor) {
		int rSize = m.length, cSize = m[0].length;
		if (x < 0 || x >= rSize || y < 0 || y >= cSize) return;

		floodFill(m, newColor, m[x][y], x, y);

		// Print the result:
		for (int i = 0; i < rSize; i++)
			for (int j = 0; j < cSize; j++)
				System.out.print(m[i][j] + " ");
	}

	public void floodFill(int[][] m, int newColor, int oldColor, int i, int j) {
		int rSize = m.length, cSize = m[0].length;
		//1.Termination Condition:
		if (i < 0 || i >= rSize || j < 0 || j >= cSize || m[i][j] != oldColor) return;

		//2.Logic based on problems
		m[i][j] = newColor; // Apply the new color

		//3.Traverse to all the directions(4 or 8 based on problem)
		//3.1.Recursion
		floodFill(m, newColor, oldColor, i + 1, j);
		floodFill(m, newColor, oldColor, i - 1, j);
		floodFill(m, newColor, oldColor, i, j + 1);
		floodFill(m, newColor, oldColor, i, j - 1);

		//or 
		//3.2.Iteration + Recursion
		for (int[] dir : DIRS) {
			floodFill(m, newColor, oldColor, i + dir[0], j + dir[1]);
		}
	}

	/* Surrounded Regions: Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'. A region 
	 * is captured by flipping all 'O's into 'X's in that surrounded region.
	 *  
	 *  - Solved using, DFS, BFS & UnionFind  
	 */
	public void surroundedRegions(char[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0) return;

		int m = matrix.length, n = matrix[0].length;
		//Traverse the border(Left and Right) and change 'O' to '#'
		for (int i = 0; i < m; i++) {
			if (matrix[i][0] == 'O') {
				// surroundedRegionsDFS(matrix, i, 0);
				surroundedRegionsBFS(matrix, i, 0);
			}
			if (matrix[i][n - 1] == 'O') {
				// surroundedRegionsDFS(matrix, i, n - 1);
				surroundedRegionsBFS(matrix, i, n - 1);
			}
		}

		//Traverse the border(Top and Bottom) and change 'O' to '#'
		for (int j = 0; j < n; j++) {
			if (matrix[0][j] == 'O') {
				// surroundedRegionsDFS(matrix, 0, j);
				surroundedRegionsBFS(matrix, 0, j);
			}
			if (matrix[m - 1][j] == 'O') {
				// surroundedRegionsDFS(matrix, m - 1, j);
				surroundedRegionsBFS(matrix, m - 1, j);
			}
		}

		//Finally change all unmodified 'O' to 'X' and '#' to 'O'
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] == 'O') matrix[i][j] = 'X';
				else if (matrix[i][j] == '#') matrix[i][j] = 'O';
			}
		}

		Utils.printMatrix(matrix);

	}

	public void surroundedRegionsDFS(char[][] matrix, int i, int j) {
		int row = matrix.length, col = matrix[0].length;
		if (i < 0 || i >= row || j < 0 || j >= col || matrix[i][j] != 'O') return;

		matrix[i][j] = '#';

		surroundedRegionsDFS(matrix, i - 1, j); // up
		surroundedRegionsDFS(matrix, i + 1, j);// down
		surroundedRegionsDFS(matrix, i, j - 1);// left
		surroundedRegionsDFS(matrix, i, j + 1);// right

		//or
		for (int[] dir : DIRS)
			surroundedRegionsDFS(matrix, i + dir[0], j + dir[1]);
	}

	public void surroundedRegionsBFS(char[][] matrix, int r, int c) {
		Queue<Integer> queue = new LinkedList<>();

		int m = matrix.length, n = matrix[0].length;
		queue.add(r * n + c); // Other approach is create class with m and column, add that value in queue
		matrix[r][c] = '#';

		while (!queue.isEmpty()) {
			int top = queue.poll();
			int i = top / n;
			int j = top % n;

			for (int[] dir : DIRS) {
				int mi = i + dir[0]; //modified i & j
				int mj = j + dir[1];
				if (mi < 0 || mi >= m || mj < 0 || mj >= n || matrix[mi][mj] != 'O') continue;

				queue.add(mi * n + mj);
				matrix[mi][mj] = '#';
			}
		}
	}

	public void surroundedRegionsUF(char[][] matrix) {
		if (matrix == null || matrix.length == 0) return;

		int rows = matrix.length;
		int cols = matrix[0].length;

		// last one is dummy, all outer O are connected to this dummy
		int arrSize = rows * cols;
		DisjointSet ds = new DisjointSet(arrSize + 1);
		ds.initialize(arrSize + 1);

		int dummyNode = arrSize;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (matrix[i][j] == 'O') {
					if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
						ds.union(node(i, j, cols), dummyNode);
					} else {
						if (i > 0 && matrix[i - 1][j] == 'O') {
							ds.union(node(i, j, cols), node(i - 1, j, cols));
						}
						if (i < rows - 1 && matrix[i + 1][j] == 'O') {
							ds.union(node(i, j, cols), node(i + 1, j, cols));
						}
						if (j > 0 && matrix[i][j - 1] == 'O') {
							ds.union(node(i, j, cols), node(i, j - 1, cols));
						}
						if (j < cols - 1 && matrix[i][j + 1] == 'O') {
							ds.union(node(i, j, cols), node(i, j + 1, cols));
						}
					}
				}
			}
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (!(ds.find(node(i, j, cols)) == ds.find(dummyNode)) && matrix[i][j] == 'O') matrix[i][j] = 'X';
			}
		}

		Utils.printMatrix(matrix);
	}

	int node(int i, int j, int cols) {
		return i * cols + j;
	}

	/************** 5.2.Find the Region/Data: ***************/

	// Number of Islands I: Time: O(m*n);
	public int numIslands1(char[][] grid) {
		if (grid.length == 0) return 0;

		int row = grid.length, col = grid[0].length, count = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (grid[i][j] == '1') {
					// numIslandsDFS(grid, i, j);
					numIslandsBFS(grid, i, j);
					count++;
				}
			}
		}

		return count;
	}

	// Using DFS(left/right/up/down) movement; Time Complexity - O(m*n)
	public void numIslandsDFS(char[][] matrix, int i, int j) {
		int rSize = matrix.length, cSize = matrix[0].length;
		if (i < 0 || i >= rSize || j < 0 || j >= cSize || matrix[i][j] == '0') return;

		matrix[i][j] = '0';

		for (int[] dir : DIRS)
			numIslandsDFS(matrix, i + dir[0], j + dir[1]);
	}

	// Using BFS
	public void numIslandsBFS(char[][] grid, int r, int c) {
		Queue<Integer> queue = new LinkedList<>();

		int rowSize = grid.length, colSize = grid[0].length;
		queue.add(r * colSize + c); // Other approach is create class with row and column, add that value in queue
		grid[r][c] = '0';

		while (!queue.isEmpty()) {
			int top = queue.poll();
			int i = top / colSize;
			int j = top % colSize;
			for (int[] dir : DIRS) {
				int mi = i + dir[0]; //Modified i & j
				int mj = j + dir[1];
				if (mi < 0 || mi >= rowSize || mj < 0 || mj >= colSize || grid[mi][mj] == '0') continue;

				queue.add(mi * colSize + mj);
				grid[mi][mj] = '0';
			}
		}
	}

	// Using DisJoint Set/Union-Find DS
	public int numIslands(char[][] grid) {
		return disjointSetProblems.numIslands(grid);
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
		return disjointSetProblems.numIslands2(m, n, positions);
	}

	/*
	 * Given an m x n integers matrix, return the length of the longest increasing path in matrix.
	 * Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
	 * Output: 4
	 * Explanation: The longest increasing path is [1, 2, 6, 9].
	 */
	public int longestIncreasingPath1(int[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0) return 0;
		int max = 0, r = matrix.length, c = matrix[0].length;
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				max = Math.max(max, longestIncreasingPath1(matrix, i, j, Integer.MIN_VALUE));
			}
		}
		return max;
	}

	public int longestIncreasingPath1(int[][] matrix, int i, int j, int prev) {
		int r = matrix.length, c = matrix[0].length;
		if (i < 0 || i >= r || j < 0 || j >= c) return 0;
		if (prev >= matrix[i][j]) return 0;

		/*int result = 1 + Utils.max(longestIncreasingPath1(matrix, i + 1, j, matrix[i][j]),
				longestIncreasingPath1(matrix, i, j + 1, matrix[i][j]),
				longestIncreasingPath1(matrix, i - 1, j, matrix[i][j]),
				longestIncreasingPath1(matrix, i, j - 1, matrix[i][j]));*/
		//or
		int result = 0;
		for (int[] dir : DIRS) {
			result = Math.max(result, 1 + longestIncreasingPath1(matrix, i + dir[0], j + dir[1], matrix[i][j]));
		}
		return result;
	}

	// DP Approach - Memoization
	public int longestIncreasingPath2(int[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0) return 0;
		int max = 0, r = matrix.length, c = matrix[0].length;
		int[][] memo = new int[r][c];

		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				max = Math.max(max, longestIncreasingPath2(matrix, i, j, Integer.MIN_VALUE, memo));
			}
		}
		return max;
	}

	public int longestIncreasingPath2(int[][] matrix, int i, int j, int prev, int[][] memo) {
		int r = matrix.length, c = matrix[0].length;
		if (i < 0 || i >= r || j < 0 || j >= c || prev >= matrix[i][j]) return 0;

		// Lookup from the table
		if (memo[i][j] != 0) return memo[i][j];

		memo[i][j] = 1 + Utils.max(longestIncreasingPath2(matrix, i + 1, j, matrix[i][j], memo),
				longestIncreasingPath2(matrix, i, j + 1, matrix[i][j], memo),
				longestIncreasingPath2(matrix, i - 1, j, matrix[i][j], memo),
				longestIncreasingPath2(matrix, i, j - 1, matrix[i][j], memo));
		//or 

		for (int[] dir : DIRS) {
			memo[i][j] = Math.max(memo[i][j],
					1 + longestIncreasingPath2(matrix, i + dir[0], j + dir[1], matrix[i][j], memo));
		}
		return memo[i][j];
	}

	// Word Boggle/Word Search I:
	public void wordSearchI(char[][] board, String str) {
		wordProblems.wordSearchI(board, str);
	}

	// Word Search II:
	public void wordSearchII(char[][] board, String[] words) {
		wordProblems.wordSearchII1(board, words);
		wordProblems.wordSearchII2(board, words);
		wordProblems.wordSearchII3(board, words);
	}

	/************** 5.3.Find one path from src to dest: ***************/
	/*Rat in a Maze Problem:
	 * Consider a rat placed at (0, 0) in a square matrix m[][] of order n and has to reach the destination at (n-1, n-1). 
	 * Your task is to complete the function which returns a sorted array of strings denoting all the possible directions 
	 * which the rat can take to reach the destination at (n-1, n-1). 
	 * The directions in which the rat can move are 'U'(up), 'D'(down), 'L' (left), 'R' (right).
	 */
	public List<String> printRatMazePath(int[][] grid) {
		List<String> result = new ArrayList<>();
		dfs(grid, "", 0, 0, result);
		result.stream().forEach(k -> System.out.print(k + " "));
		return result;
	}

	public void dfs(int[][] grid, String s, int i, int j, List<String> result) {
		int rSize = grid.length, cSize = grid[0].length;

		if (i < 0 || i >= rSize || j < 0 || j >= cSize || grid[i][j] == 0 || grid[i][j] == Integer.MAX_VALUE) return;

		if (i == rSize - 1 && j == cSize - 1 && grid[i][j] == 1) result.add(s);

		grid[i][j] = Integer.MAX_VALUE;

		dfs(grid, s + "D", i + 1, j, result);
		dfs(grid, s + "L", i, j - 1, result);
		dfs(grid, s + "R", i, j + 1, result);
		dfs(grid, s + "U", i - 1, j, result);

		grid[i][j] = 1;
	}

	/*
	 * The Maze I:There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by rolling
	 * up, down, left or right, but it won't stop rolling until hitting a wall. When the ball stops, it could choose the
	 * next direction. 
	 * Given the ball's start position, the destination and the maze, determine whether the ball could stop at the destination. 
	 * The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. You may assume that the
	 * borders of the maze are all walls. The start and destination coordinates are represented by row and column indexes.
	 */
	// DFS Approach
	public boolean hasPath1(int[][] maze, int[] start, int[] destination) {
		boolean[][] visited = new boolean[maze.length][maze[0].length];
		return hasPath1(maze, visited, start[0], start[1], destination[0], destination[1]);
	}

	private boolean hasPath1(int[][] maze, boolean[][] visited, int si, int sj, int di, int dj) {
		int rSize = maze.length, cSize = maze[0].length;
		if (si == di && sj == dj) return true;
		visited[si][sj] = true;

		for (int[] d : DIRS) {
			int i = si, j = sj;
			// This looping to identify the end. "it won't stop rolling until hitting a wall"
			while (i >= 0 && i < rSize && j >= 0 && j < cSize && maze[i][j] == 0) {
				i += d[0];
				j += d[1];
			}

			// New Start: i-d[0], j-d[1]; It has reduced to adjust the increased val in while loop
			i -= d[0];
			j -= d[1];

			if (visited[i][j]) continue;
			if (hasPath1(maze, visited, i, j, di, dj)) return true;
		}

		return false;
	}

	// BFS Approach:
	public boolean hasPath2(int[][] maze, int[] start, int[] destination) {
		int rSize = maze.length, cSize = maze[0].length;
		Queue<Point> queue = new LinkedList<>();
		boolean[][] visited = new boolean[rSize][cSize];

		queue.add(new Point(start[0], start[1]));
		visited[start[0]][start[1]] = true;

		while (!queue.isEmpty()) {
			Point curr = queue.poll();

			for (int[] d : DIRS) {
				int i = curr.x, j = curr.y;
				// This looping to identify the end. "it won't stop rolling until hitting a wall"
				while (i >= 0 && i < rSize && j >= 0 && j < cSize && maze[i][j] == 0) {
					i += d[0];
					j += d[1];
				}
				// New Start: i-d[0], j-d[1]; It has reduced to adjust the increased val in while loop
				i -= d[0];
				j -= d[1];

				if (i == destination[0] && j == destination[1]) return true;
				if (visited[i][j]) continue;

				visited[i][j] = true;
				queue.add(new Point(i, j));
			}
		}

		return false;
	}
	/*
	 * The Maze II: There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by
	 * rolling up, down, left or right, but it won't stop rolling until hitting a wall. When the ball stops, it could
	 * choose the next direction.
	 * Given the ball's start position, the destination and the maze, find the shortest distance for the ball to stop at
	 * the destination. The distance is defined by the number of empty spaces traveled by the ball from the start position
	 * (excluded) to the destination (included). If the ball cannot stop at the destination, return -1. 
	 * The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. You may assume that the 
	 * borders of the maze are all walls. The start and destination coordinates are represented by row and column indexes.
	 */

	public int shortestDistance1(int[][] maze, int[] start, int[] destination) {
		int rSize = maze.length, cSize = maze[0].length;
		int[][] distance = new int[rSize][cSize];

		for (int i = 0; i < rSize * cSize; i++)
			distance[i / cSize][i % cSize] = Integer.MAX_VALUE;

		distance[start[0]][start[1]] = 0;
		shortestDistance1(maze, distance, start[0], start[1], destination[0], destination[1]);

		return distance[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1
				: distance[destination[0]][destination[1]];
	}

	private void shortestDistance1(int[][] maze, int[][] distance, int si, int sj, int di, int dj) {
		if (si == di && sj == dj) return;
		int rSize = maze.length, cSize = maze[0].length;
		for (int[] d : DIRS) {
			int row = si, col = sj;
			// This looping to identify the end. "it won't stop rolling until hitting a wall"
			while (row >= 0 && row < rSize && col >= 0 && col < cSize && maze[row][col] == 0) {
				row += d[0];
				col += d[1];
			}

			// New Start: row-d[0], col-d[1]; It has reduced to adjust the increased val in while loop
			row -= d[0];
			col -= d[1];

			int dist = distance[si][sj] + Math.abs(row - si) + Math.abs(col - si); // currdistance +no of path increased
			if (dist < distance[row][col]) {
				distance[row][col] = dist;
				shortestDistance1(maze, distance, row, col, di, dj);
			}
		}
	}

	public int shortestDistance2(int[][] maze, int[] start, int[] destination) {
		int rSize = maze.length, cSize = maze[0].length;
		PriorityQueue<Point> queue = new PriorityQueue<>((a, b) -> (a.dist - b.dist));

		int[][] distance = new int[rSize][cSize];
		for (int i = 0; i < rSize * cSize; i++)
			distance[i / cSize][i % cSize] = Integer.MAX_VALUE;

		queue.add(new Point(start[0], start[1], 0));
		while (!queue.isEmpty()) {
			Point curr = queue.poll();
			if (distance[curr.x][curr.y] <= curr.dist) // if found the path already skip it
				continue;

			distance[curr.x][curr.y] = curr.dist;
			for (int[] d : DIRS) {
				int row = curr.x, col = curr.y, dist = curr.dist;
				// This looping to identify the end. "it won't stop rolling until hitting a wall"
				while (row >= 0 && row < rSize && col >= 0 && col < cSize && maze[row][col] == 0) {
					row += d[0];
					col += d[1];
					dist++;
				}
				// New Start: row-d[0], col-d[1]; It has reduced to adjust the increased val in while loop
				row -= d[0];
				col -= d[1];
				dist--;

				queue.add(new Point(row, col, dist));
			}

		}

		return distance[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1
				: distance[destination[0]][destination[1]];
	}

	/************** 5.4.Find the dist from multiple src & dest: ***************/
	/*
	 * Find Shortest distance from a guard in a Bank: 
	 * Given a matrix that is filled with ‘O’, ‘G’, and ‘W’ where 
	 *  ‘O’ represents open space, 
	 *  ‘G’ represents guards 
	 *  ‘W’ represents walls 
	 *  in a Bank. Replace all of the O’s in the matrix with their shortest distance from a guard, without being able to
	 *  go through any walls. Also, replace the guards with 0 and walls with -1 in output matrix.
	 *  
	 *  BFS is better than DFS for this problem
	 */

	public int[][] shortestDistFromGuard(char[][] matrix) {
		int m = matrix.length, n = matrix[0].length;
		int[][] result = new int[m][n];

		//Convert input char matrix to int matrix
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] == 'G') {
					result[i][j] = 0;
				} else if (matrix[i][j] == 'W') {
					result[i][j] = -1;
				} else {
					result[i][j] = Integer.MAX_VALUE;
				}
			}
		}

		bfsSearch(result);
		//or
		dfsSearch(result);

		return result;
	}

	/*
	 * Walls and Gates: 
	 * You are given a m x n 2D grid initialized with these three possible values. 
	 *    -1 - A wall or an obstacle. 
	 *     0 - A gate. 
	 *    INF - Infinity means an empty room. 
	 * We use the value 2^31 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
	 * Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.
	 * 
	 * BFS is better than DFS for this problem
	 */
	public void wallsAndGates(int[][] rooms) {
		if (rooms == null || rooms.length == 0) return;

		bfsSearch(rooms);
		//or
		dfsSearch(rooms);

		Utils.printMatrix(rooms);
	}

	public int[][] bfsSearch(int[][] matrix) {
		int m = matrix.length, n = matrix[0].length;
		Queue<Point> queue = new LinkedList<>();

		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				if (matrix[i][j] == 0) queue.add(new Point(i, j, 0));

		bfs(queue, matrix);
		return matrix;
	}

	public void bfs(Queue<Point> queue, int[][] result) {
		int m = result.length, n = result[0].length;
		while (!queue.isEmpty()) {
			Point curr = queue.poll();
			for (int[] dir : DIRS) {
				int i = curr.x + dir[0];
				int j = curr.y + dir[1];
				if (i >= 0 && i < m && j >= 0 && j < n && result[i][j] == Integer.MAX_VALUE) {
					result[i][j] = curr.dist + 1;
					queue.add(new Point(i, j, curr.dist + 1));
				}
			}
		}
	}

	// Solution using DFS algorithm
	public void dfsSearch(int[][] rooms) {
		int r = rooms.length, c = rooms[0].length;

		for (int i = 0; i < r; i++)
			for (int j = 0; j < c; j++)
				if (rooms[i][j] == 0) dfs(rooms, i, j, 0);

		Utils.printMatrix(rooms);
	}

	public void dfs(int[][] rooms, int i, int j, int dist) {
		int rSize = rooms.length, cSize = rooms[0].length;

		if (i < 0 || i >= rSize || j < 0 || j >= cSize || rooms[i][j] < dist) return;

		rooms[i][j] = dist;

		for (int[] dir : DIRS) {
			dfs(rooms, i + dir[0], j + dir[1], dist + 1);
		}

		//or
		/*dfsSearch(rooms, i - 1, j, dist + 1);
		dfsSearch(rooms, i + 1, j, dist + 1);
		dfsSearch(rooms, i, j - 1, dist + 1);
		dfsSearch(rooms, i, j + 1, dist + 1);*/
	}

	public static void main(String[] args) {
		MatrixPatterns ob = new MatrixPatterns();
		char matrix[][] = { { 'O', 'O', 'O', 'O', 'G' }, { 'O', 'W', 'W', 'O', 'O' }, { 'O', 'O', 'O', 'W', 'O' },
				{ 'G', 'W', 'W', 'W', 'O' }, { 'O', 'O', 'O', 'O', 'G' } };

		System.out.println(Arrays.deepToString(ob.shortestDistFromGuard(matrix)));

	}

}

class Cell {
	public int i;
	public int j;
	public int val;

	public Cell(int i, int j, int val) {
		this.i = i;
		this.j = j;
		this.val = val;
	}

}