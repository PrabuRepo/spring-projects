package com.problems.patterns.dp;

import java.util.Arrays;

import com.common.utilities.Utils;

/*
 * Patterns covered in this class are,
 * 	1.Palindromic substring/subseq Probs
 *  2.Substring/Subsequence Probs - LCSs
 */
public class DPStringPatterns {
	/********************* Pattern 5: String-Palindromic substring/subseq Probs ***********************/
	// Longest Palindromic Subsequence:
	// 1.Recursion Approach
	public int lps1(String str) {
		return lps1(str, 0, str.length() - 1);
	}

	public int lps1(String str, int i, int j) {
		if (i == j) return 1;
		if (str.charAt(i) == str.charAt(j) && i + 1 == j) return 2;
		if (str.charAt(i) == str.charAt(j)) return lps1(str, i + 1, j - 1) + 2;
		return Math.max(lps1(str, i, j - 1), lps1(str, i + 1, j));
	}

	// 3.DP-Bottom Up Approach
	public int lps3(String str) {
		int n = str.length();
		int[][] result = new int[n][n];
		for (int i = 1; i < n; i++)
			result[i][i] = 1;
		for (int len = 2; len <= n; len++) {
			for (int i = 0; i < (n - len + 1); i++) {
				int j = i + len - 1;
				if (str.charAt(i) == str.charAt(j) && len == 2) {
					result[i][j] = 2;
				} else if (str.charAt(i) == str.charAt(j)) {
					result[i][j] = result[i + 1][j - 1] + 2;
				} else {
					result[i][j] = Math.max(result[i][j - 1], result[i + 1][j]);
				}
			}
		}

		printLPS3(result, str);

		return result[0][n - 1];
	}

	private String printLPS3(int[][] result, String str) {
		int n = str.length();
		int row = 0, col = n - 1, start = 0, end = result[0][n - 1] - 1;
		char[] seq = new char[result[0][n - 1]];
		while (row <= col) {
			if (result[row][col] > result[row][col - 1] && result[row][col] > result[row + 1][col]) {
				seq[start++] = str.charAt(col);
				seq[end--] = str.charAt(col);
				row++;
				col--;
			} else if (result[row][col] == result[row][col - 1]) {
				col--;
			} else if (result[row][col] == result[row + 1][col]) {
				row++;
			} else {
				row++;
				col--;
			}
		}
		return String.valueOf(seq);
	}

	// Longest Palindromic Substring:
	/* Method 1(Brute	Force):
	 * The simple approach is to check each substring whether the substring is a palindrome or not. We can run three
	 * loops, the outer two loops pick all substrings one by one by fixing the corner characters, the inner loop checks
	 * whether the picked substring is palindrome or not.
	 * Time complexity: O ( n^3 )
	 */
	public String lpSubstr1(String str) {
		int max = -1, n = str.length();
		String maxString = null, subString;
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				subString = str.substring(i, j + 1);
				if (isPalindrome(subString) && max < (j - i + 1)) {
					max = j - i + 1;
					maxString = subString;
				}
			}
		}
		return maxString;
	}

	// 2. Using DP-Bottom Up Approach:
	public String lpSubstr3(String str) {
		int n = str.length(), max = 1, start = 0;
		boolean[][] table = new boolean[n][n];
		for (int i = 0; i < n; i++)
			table[i][i] = true;
		for (int len = 2; len <= n; len++) {
			for (int i = 0; i <= n - len; i++) {
				int j = i + len - 1;
				if (str.charAt(i) == str.charAt(j) && (len == 2 || table[i + 1][j - 1])) {
					table[i][j] = true;
					if (len > max) {
						start = i;
						max = len;
					}
				}
			}
		}
		return str.substring(start, start + max);
	}

	public boolean isPalindrome(String str) {
		int l = 0, h = str.length() - 1;
		while (l < h) {
			if (str.charAt(l++) != str.charAt(h--)) return false;
		}
		return true;
	}

	/*
	 * Palindrome Partitioning II:
	 *   Given a string s, partition s such that every substring of the partition is a palindrome. Return the minimum cuts needed for 
	 *   a palindrome partitioning of s.*/
	//Approach 1: Time O(n^2), Space: O(n^2)
	public int minCut1(String s) {
		int n = s.length();
		if (n <= 1) return 0;
		boolean[][] dp = new boolean[n][n];
		int[] cut = new int[n];

		for (int r = 0; r < n; r++) {
			cut[r] = r;
			for (int l = 0; l <= r; l++) {
				if (s.charAt(l) == s.charAt(r) && (r - l <= 1 || dp[l + 1][r - 1])) {
					dp[l][r] = true;
					//if l == 0 means substring(0, r) is palindrome, so no cut is needed
					if (l < 0) cut[r] = 0;
					else cut[r] = Math.min(cut[r], cut[l - 1] + 1);
				}
			}
		}
		return cut[n - 1];
	}

	//Approach 2: Similiar to above approach, except boolean array to mainatain palindrome
	//Time O(n^2), Space: O(n)
	public int minCut2(String s) {
		int n = s.length();
		int[] cuts = new int[n];

		for (int i = 0; i < n; i++)
			cuts[i] = i;

		for (int i = 0; i < n; i++) {
			checkPalindrome(s, cuts, i, i); //To handle odd length palindrome
			checkPalindrome(s, cuts, i, i + 1); //To handle even length palindrome
		}

		return cuts[n - 1];
	}

	private void checkPalindrome(String s, int[] cuts, int l, int r) {
		int n = cuts.length;

		while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
			//if l == 0 means substring(0, r) is palindrome, so no cut is needed
			if (l == 0) cuts[r] = 0;
			else cuts[r] = Math.min(cuts[r], cuts[l - 1] + 1);

			l--;
			r++;
		}
	}

	/************************** Pattern 6: String-Substring/Subsequence Probs *******************/
	// Longest Common Substring:
	// 1.Recursion Approach:
	public int lcStr1(String s1, String s2) {
		return lcStr1(s1, s2, s1.length() - 1, s2.length() - 1, 0);
	}

	public int lcStr1(String s1, String s2, int i, int j, int count) {
		if (i < 0 || j < 0) return count;
		if (s1.charAt(i) == s2.charAt(j)) return lcStr1(s1, s2, i - 1, j - 1, count + 1);
		return Utils.max(count, lcStr1(s1, s2, i - 1, j, 0), lcStr1(s1, s2, i, j - 1, 0));
	}

	// 2.DP:Bottom Up Approach:Time Complexity-O(m.n)
	public int lcStr3(String s1, String s2) {
		int m = s1.length(), n = s2.length();
		int[][] dp = new int[m][n];
		int max = 0, row = 0, col = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (s1.charAt(i) == s2.charAt(j)) {
					if (i == 0 || j == 0) {
						dp[i][j] = 1;
					} else {
						dp[i][j] = dp[i - 1][j - 1] + 1;
					}
					if (max < dp[i][j]) {
						max = dp[i][j];
						row = i;
						col = j;
					}
				}
			}
		}
		//s1 - row or s2 - col
		printLCStr(dp, row, col, s1);
		return max;
	}

	public String printLCStr(int[][] dp, int row, int col, String s) {
		String subStr = "";
		while (row >= 0 && col >= 0 && dp[row][col] != 0) {
			subStr = s.charAt(row) + subStr;
			row--;
			col--;
		}
		return subStr;
	}

	// Longest Common subsequence:
	// 1.Recursive approach
	public int lcs1(String s1, String s2) {
		return lcs1(s1, s2, s1.length() - 1, s2.length() - 1);
	}

	private int lcs1(String s1, String s2, int i, int j) {
		if (i < 0 || j < 0) return 0;
		if (s1.charAt(i) == s2.charAt(j)) return 1 + lcs1(s1, s2, i - 1, j - 1);
		return Math.max(lcs1(s1, s2, i - 1, j), lcs1(s1, s2, i, j - 1));
	}

	// 2.DP:Top Down approach
	public int lcs2(String s1, String s2) {
		int[][] dp = new int[s1.length()][s2.length()];
		for (int[] row : dp)
			Arrays.fill(row, -1);
		return lcs2(s1, s2, s1.length() - 1, s2.length() - 1, dp);
	}

	private int lcs2(String s1, String s2, int i, int j, int[][] dp) {
		if (i < 0 || j < 0) return 0;
		if (dp[i][j] != -1) return dp[i][j];
		if (s1.charAt(i) == s2.charAt(j)) {
			return dp[i][j] = lcs2(s1, s2, i - 1, j - 1, dp) + 1;
		}
		return dp[i][j] = Math.max(lcs2(s1, s2, i - 1, j, dp), lcs2(s1, s2, i, j - 1, dp));
	}

	// 3.DP Bottom Up Approach
	public int lcs3(String s1, String s2) {
		int m = s1.length(), n = s2.length();
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
					dp[i][j] = 1 + dp[i - 1][j - 1];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}
		printLCS(dp, s1, s2);
		return dp[m][n];
	}

	// Print the longest common sub sequence
	private void printLCS(int[][] dp, String s1, String s2) {
		int i = s1.length(), j = s2.length();
		int longSeqCount = dp[i][j];
		char[] result = new char[longSeqCount];
		int index = longSeqCount;
		while (i > 0 && j > 0) {
			if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
				result[--index] = s1.charAt(i - 1);
				i--;
				j--;
			} else if (dp[i - 1][j] > dp[i][j - 1]) {
				i--;
			} else {
				j--;
			}
		}
		System.out.print("SubSequence:");
		for (int k = 0; k < longSeqCount; k++) {
			System.out.print(result[k] + "-");
		}
	}

	// Edit Distance: Find minimum number of edits (operations) required to convert ‘str1’ into ‘str2’.
	// Recursion Approach
	public int minDistance1(String s1, String s2) {
		return minDistance(s1, s2, s1.length() - 1, s2.length() - 1);
	}

	public int minDistance(String s1, String s2, int i, int j) {
		if (i < 0) return j + 1;
		if (j < 0) return i + 1;
		if (s1.charAt(i) == s2.charAt(j)) return minDistance(s1, s2, i - 1, j - 1);

		return 1 + Utils.min(minDistance(s1, s2, i, j - 1), //  represents insert operation
				minDistance(s1, s2, i - 1, j), // represents delete operation
				minDistance(s1, s2, i - 1, j - 1)); // represents replace operation
	}

	// DP-Bottom up Approach
	public int minDistance(String s1, String s2) {
		int m = s1.length(), n = s2.length();
		if (m == 0 && n == 0) return 0;
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0) dp[i][j] = j;
				else if (j == 0) dp[i][j] = i;
				else if (s1.charAt(i - 1) == s2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
				else dp[i][j] = 1 + Utils.min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]);
			}
		}
		return dp[m][n];
	}

	/*
	 * Interleaving String:
	 *  Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
	 *  Example: 
	 *  	Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac" Output: true
	 *  	Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc" Output: false
	 */
	// Recursive Approach
	public boolean isInterleave1(String s1, String s2, String s3) {
		if ((s1.length() + s2.length()) != s3.length()) return false;
		return isInterleave(s1, s2, s3, 0, 0);
	}

	public boolean isInterleave(String s1, String s2, String s3, int i, int j) {
		if (i == s1.length() && j == s2.length() && i + j == s3.length()) return true;
		if (i + j == s3.length()) return false;

		return ((i < s1.length() && s1.charAt(i) == s3.charAt(i + j) && isInterleave(s1, s2, s3, i + 1, j))
				|| (j < s2.length() && s2.charAt(j) == s3.charAt(i + j) && isInterleave(s1, s2, s3, i, j + 1)));
	}

	// DP-Bottom Up Approach
	public boolean isInterleave3(String s1, String s2, String s3) {
		int n1 = s1.length(), n2 = s2.length();
		if ((n1 + n2) != s3.length()) return false;

		boolean[][] dp = new boolean[n1 + 1][n2 + 1];
		for (int i = 0; i <= n1; i++) {
			for (int j = 0; j <= n2; j++) {
				if (i == 0 && j == 0) {
					dp[i][j] = true;
				} else if (i == 0) {
					dp[i][j] = dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1);
				} else if (j == 0) {
					dp[i][j] = dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1);
				} else {
					dp[i][j] = (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1))
							|| (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1));
				}
			}
		}
		return dp[n1][n2];
	}

}
