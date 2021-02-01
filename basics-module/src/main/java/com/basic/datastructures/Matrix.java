package com.basic.datastructures;

//TODO: Think about what to be here????
public class Matrix {

	//2D Array basics
	public void basicApis() {
		//2D array initialization
		//2D array sorting 
		//2D array swapping 

		//Rotate Image  or Rotate Matrix
		//Spiral Matrix I, II or Spirally traversing a matrix

	}

	//Clean up below:

	/*************** Matrix Basic Problems ************************/
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

	// Matrix Multiplication
	public int[][] matrixMul(int[][] a, int[][] b) {
		int m1 = a.length, n1 = a[0].length;
		int m2 = b.length, n2 = b[0].length;
		int[][] result = new int[m1][n2];

		/* Matrix Mul condition: 
		   		Given Matrix: a(m1 X n1), b(m2 X n2)
		   		To perform multiplication, n1 == m2  and 
		   		Result size: m1 X n2
		 */
		if (n1 != m2) return result;

		for (int i = 0; i < m1; i++)
			for (int j = 0; j < n2; j++)
				for (int k = 0; k < n1; k++) // k -> n1 or m2
					result[i][j] += a[i][k] * b[k][j];

		return result;
	}

	/*Sparse Matrix Multiplication:
	 * Optimized Method: We can see that when a_ik is 0, there is no need to compute b_kj. So we switch the inner two 
	 * loops and add a 0-checking condition. 
	 * Since the matrix is sparse, time complexity is ~O(n^2) which is much faster than O(n^3).
	 */
	public int[][] sparseMatrixMul(int[][] a, int[][] b) {
		/*int m = a.length, n = b[0].length;
		int[][] result = new int[m][n];*/

		int m1 = a.length, n1 = a[0].length;
		int m2 = b.length, n2 = b[0].length;
		int[][] result = new int[m1][n2];

		if (n1 != m2) return result;

		for (int i = 0; i < m1; i++) {
			for (int k = 0; k < n1; k++) { // k -> n1 or m2
				if (a[i][k] == 0) continue;
				for (int j = 0; j < n2; j++)
					result[i][j] += a[i][k] * b[k][j];
			}
		}

		return result;
	}

	//Eg: https://www.geeksforgeeks.org/zigzag-or-diagonal-traversal-of-matrix/
	public void printMatrixDiagonally(int[][] matrix) {
		int r = matrix.length, c = matrix[0].length;

		for (int k = 0; k < r; k++) {
			int i = k, j = 0;
			while (i >= 0 && j <= k) {
				System.out.print(matrix[i][j] + " ");
				i--;
				j++;
			}
			System.out.println();
		}

		for (int k = 1; k < c; k++) {
			int i = r - 1, j = k;
			while (i >= 0 && j < c) {
				System.out.print(matrix[i][j] + " ");
				i--;
				j++;
			}
			System.out.println();
		}
	}

}