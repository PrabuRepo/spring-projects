package com.basic.datastructures;

import java.util.ArrayList;
import java.util.List;

import com.common.utilities.Utils;

//TODO: Think about what to be here????
public class Matrix {

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

	public void reverseMatrixColumn(int[][] A) {
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

	public void reverseMatrixRow(int[][] A) {
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

	// Anti clock wise direction:
	// Apprach1: Take transpose, reverse the column & use an additional space
	public void rotateRightMatrix1(int[][] matrix) {
		System.out.println("Before: ");
		Utils.printMatrix(matrix);

		matrix = transpose(matrix);
		reverseMatrixColumn(matrix);

		System.out.println("After: ");
		Utils.printMatrix(matrix);
	}

	// Approach2: without using any space
	public void rotateRightMatrix2(int[][] matrix) {
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
	public void rotateLeftMatrix1(int[][] matrix) {
		System.out.println("Before: ");
		Utils.printMatrix(matrix);

		matrix = transpose(matrix);
		reverseMatrixRow(matrix);

		System.out.println("After: ");
		Utils.printMatrix(matrix);
	}

	// Approach2: without using any space
	public void rotateLeftMatrix2(int[][] matrix) {

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
	public List<Integer> spiralOrder(int[][] matrix) {
		List<Integer> result = new ArrayList<Integer>();

		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return result;

		int r = matrix.length, c = matrix[0].length;
		int left = 0, right = c - 1, top = 0, bottom = r - 1;

		while (top <= bottom && left <= right) { // or result.size() < r*c
			// From left to right
			for (int j = left; j <= right; j++)
				result.add(matrix[top][j]);
			top++;

			// From top to bottom
			for (int i = top; i <= bottom; i++)
				result.add(matrix[i][right]);
			right--;

			if (top > bottom || left > right) break;

			// From right to left
			for (int j = right; j >= left; j--)
				result.add(matrix[bottom][j]);
			bottom--;

			// From bottom to top
			for (int i = bottom; i >= top; i--)
				result.add(matrix[i][left]);
			left++;
		}

		return result;
	}

	static int[] spiralCopy(int[][] matrix) {
		int r = matrix.length, c = matrix[0].length;
		int[] result = new int[r * c];

		if (matrix.length == 0 || matrix[0].length == 0) return result;
		int left = 0, right = c - 1, top = 0, bottom = r - 1, index = 0;

		while (top <= bottom && left <= right) { // or result.size() < r*c
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

	// Matrix Multiplication
	public int[][] matrixMul(int[][] a, int[][] b) {
		int m = a.length, n = b[0].length;
		int[][] result = new int[m][n];

		if (a[0].length != b.length) return result;

		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				for (int k = 0; k < b.length; k++) // k<b.length or k<a[0].length
					result[i][j] += a[i][k] * b[k][j];

		return result;
	}

	/*Sparse Matrix Multiplication:
	 * Optimized Method: We can see that when a_ik is 0, there is no need to compute b_kj. So we switch the inner two 
	 * loops and add a 0-checking condition. 
	 * Since the matrix is sparse, time complexity is ~O(n^2) which is much faster than O(n^3).
	 */
	public int[][] sparseMatrixMul(int[][] a, int[][] b) {
		int m = a.length, n = b[0].length;
		int[][] result = new int[m][n];

		if (a[0].length != b.length) return result;

		for (int i = 0; i < m; i++) {
			for (int k = 0; k < b.length; k++) {
				if (a[i][k] == 0) continue;
				for (int j = 0; j < n; j++)
					result[i][j] += a[i][k] * b[k][j];
			}
		}

		return result;
	}

}