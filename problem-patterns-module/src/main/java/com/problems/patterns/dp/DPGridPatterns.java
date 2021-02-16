package com.problems.patterns.dp;

public class DPGridPatterns {
	/************** Type4: DP in Matrix ***************/
	// Unique Paths: Recursive Approach
	public int uniquePaths(int m, int n) {
		if (m == 1 && n == 1) return 1;
		if (m < 0 || n < 0) return 0;
		return uniquePaths(m - 1, n) + uniquePaths(n - 1, m);
		// Including diagonal
		// return uniquePaths(m-1,n)+uniquePaths(n-1,m)+uniquePaths(m-1. n-1);
	}

	//DP: Top Down or Memoization
	public int uniquePaths2(int m, int n) {
		int[][] memo = new int[m][n];
		return uniquePaths(m - 1, n - 1, memo);
	}

	private int uniquePaths(int m, int n, int[][] memo) {
		if (m == 0 && n == 0) return 1;
		if (m < 0 || n < 0) return 0;
		if (memo[m][n] > 0) return memo[m][n];

		memo[m][n] = uniquePaths(m - 1, n, memo) + uniquePaths(m, n - 1, memo);
		return memo[m][n];
	}

	//DP: Bottom Up 
	public int uniquePaths3(int m, int n) {
		if (m == 0 && n == 0) return 0;

		int[][] dp = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i == 0 || j == 0) {
					dp[i][j] = 1;
				} else {
					// For 2 directions
					dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
					// For 3 directions
					// dp[i][j] = dp[i][j - 1] + dp[i - 1][j]+ dp[i - 1][j - 1];
				}
			}
		}

		return dp[m - 1][n - 1];
	}

	//Recursive Approach:
	public int uniquePathsWithObstacles1(int[][] obstacleGrid) {
		int r = obstacleGrid.length, c = obstacleGrid[0].length;
		if (r == 0 && c == 0) return 0;
		return uniquePathsWithObstacles(obstacleGrid, r - 1, c - 1);
	}

	public int uniquePathsWithObstacles(int[][] a, int i, int j) {
		// Here 1 means obstacle, 0 means empty path
		if (i < 0 || j < 0 || a[i][j] == 1) return 0;

		if (i == 0 && j == 0) return 1;

		return uniquePathsWithObstacles(a, i - 1, j) + uniquePathsWithObstacles(a, i, j - 1);
	}

	//DP: Top Down or Memoization
	public int uniquePathsWithObstacles2(int[][] obstacleGrid) {
		int r = obstacleGrid.length, c = obstacleGrid[0].length;
		if (r == 0 && c == 0) return 0;
		int[][] memo = new int[r][c];
		return uniquePathsWithObstacles(obstacleGrid, r - 1, c - 1, memo);
	}

	public int uniquePathsWithObstacles(int[][] a, int i, int j, int[][] memo) {
		// Here 1 means obstacle, 0 means empty path
		if (i < 0 || j < 0 || a[i][j] == 1) return 0;

		if (i == 0 && j == 0) return 1;

		if (memo[i][j] > 0) return memo[i][j];

		memo[i][j] = uniquePathsWithObstacles(a, i - 1, j, memo) + uniquePathsWithObstacles(a, i, j - 1, memo);
		return memo[i][j];
	}

	//DP: Bottom Up 
	public int uniquePathsWithObstacles3(int[][] a) {
		if (a.length == 0 && a[0].length == 0) return 0;

		int m = a.length, n = a[0].length;
		if (a[0][0] == 1 || a[m - 1][n - 1] == 1) return 0;
		int[][] dp = new int[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				// Here 1 means obstacle, 0 means empty path
				if (a[i][j] == 1) continue;

				if (i == 0 && j == 0) {
					dp[i][j] = 1;
				} else if (i == 0) {
					dp[i][j] = dp[i][j - 1];
				} else if (j == 0) {
					dp[i][j] = dp[i - 1][j];
				} else {
					// For 2 directions
					dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
					// For 3 directions
					// dp[i][j] = dp[i][j - 1] + dp[i - 1][j]+ dp[i - 1][j - 1];
				}
			}
		}
		return dp[m - 1][n - 1];
	}

	// Recursive approach
	public int minPathSum1(int[][] grid) {
		if (grid.length == 0 && grid[0].length == 0) return 0;
		return minPathSum(grid, grid.length - 1, grid[0].length - 1);
	}

	public int minPathSum(int[][] grid, int i, int j) {
		if (i < 0 || j < 0) return Integer.MAX_VALUE;
		if (i == 0 && j == 0) return grid[i][j];

		return grid[i][j] + Math.min(minPathSum(grid, i - 1, j), minPathSum(grid, i, j - 1));
	}

	//DP - Top down or memoization:
	public int minPathSum2(int[][] grid) {
		if (grid.length == 0 && grid[0].length == 0) return 0;
		int m = grid.length, n = grid[0].length;
		int[][] memo = new int[m][n];
		return minPathSum(grid, m - 1, n - 1, memo);
	}

	public int minPathSum(int[][] grid, int i, int j, int[][] memo) {
		if (i < 0 || j < 0) return Integer.MAX_VALUE;
		if (i == 0 && j == 0) return grid[i][j];
		if (memo[i][j] > 0) return memo[i][j];

		memo[i][j] = grid[i][j] + Math.min(minPathSum(grid, i - 1, j, memo), minPathSum(grid, i, j - 1, memo));
		return memo[i][j];
	}

	// DP - Bottom up
	public int minPathSum3(int[][] grid) {
		if (grid.length == 0 && grid[0].length == 0) return 0;
		int r = grid.length, c = grid[0].length;
		int[][] dp = new int[r][c];

		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				if (i == 0 && j == 0) {
					dp[i][j] = grid[i][j];
				} else if (i == 0) {
					dp[i][j] = grid[i][j] + dp[i][j - 1];
				} else if (j == 0) {
					dp[i][j] = grid[i][j] + dp[i - 1][j];
				} else {
					dp[i][j] = grid[i][j] + Math.min(dp[i][j - 1], dp[i - 1][j]);
					// dp[i][j] = grid[i][j] + min(dp[i][j - 1], dp[i - 1][j], dp[i - 1][j - 1]);
				}
			}
		}
		return dp[r - 1][c - 1];
	}

}
