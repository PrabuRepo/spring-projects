package com.others.codesignal;

import java.util.PriorityQueue;

public class CodeSignalProblems {

	public int[] bestRhombicAreaFrame(int[][] matrix, int radius) {
		int m = matrix.length, n = matrix[0].length;
		PriorityQueue<Integer> queue = new PriorityQueue<>();

		for (int i = radius - 1; i <= m - radius; i++) {
			for (int j = radius - 1; j < n - radius; j++) {
				int area = findArea(matrix, i, j, radius);
				if (queue.contains(area)) continue;
				queue.add(area);
				if (queue.size() > 3) queue.poll();
			}
		}

		int[] result = new int[queue.size()];
		for (int i = result.length - 1; i >= 0 && !queue.isEmpty(); i--) {
			result[i] = queue.poll();
		}
		return result;
	}

	private int findArea(int[][] matrix, int i, int j, int radius) {
		int sum = 0;
		for (int t = 1, row = i, col = j - radius; t <= radius; t++) {
			sum += matrix[row--][col++];
		}

		for (int t = 1, row = i, col = j + radius; t <= radius; t++) {
			sum += matrix[row++][col--];
		}

		for (int t = 1, row = i - radius, col = j; t <= radius; t++) {
			sum += matrix[row++][col++];
		}

		for (int t = 1, row = i + radius, col = j; t <= radius; t++) {
			sum += matrix[row--][col--];
		}

		//Corners added twice, delete the corners 
		sum -= (matrix[i - radius][j] + matrix[i + radius][j] + matrix[i][j - radius] + matrix[i][j + radius]);

		return sum;
	}

	public static void main(String[] args) {

	}
}
