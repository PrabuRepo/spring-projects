package com.consolidated.problems.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.common.model.Box;
import com.common.model.TreeNode;

/* All the below problems are solved in 4 approaches such as,
 *    1.Recursion -  Time: exponential time O(2^n), space complexity is O(n) which is used to store the recursion stack.
 *    2.DP: Top Down Approach or Memoization - Time and space complexity is same as memoization array size.
 *    3.DP: Bottom Up Approach or Tabulation - Time & space complexity is O(n^2) or O(n)
 *    4.Memory Optimization - This will be same time as Bottom up approach, but space efficient. Eg: Two variable approach
 */
public class DynamicProgramming {

	/***************************** Pattern 1: Rolling Array/Fibonacci Patterns *********************/
	//TODO: Number factors
	//TODO: Minimum jumps with fee

	// Min Cost Climbing Stairs
	public int minCostClimbingStairs(int[] cost) {
		int curr = 0, prev = 0;
		for (int i = 0; i < cost.length; i++) {
			int tmp = curr;
			curr = cost[i] + Math.min(curr, prev);
			prev = tmp;
		}
		return Math.min(curr, prev);
	}

	//Unique Binary Search Trees I - DP
	public int numTrees(int n) {
		int[] dp = new int[n + 1];
		dp[0] = dp[1] = 1;

		for (int i = 2; i <= n; i++) {
			for (int j = 1; j <= i; j++) {
				dp[i] += dp[j - 1] * dp[i - j];
			}
		}
		return dp[n];
	}

	//Unique Binary Search Trees II - DP
	public List<TreeNode> generateTrees(int n) {
		List<TreeNode>[] result = new List[n + 1];
		result[0] = new ArrayList<TreeNode>();
		if (n == 0) {
			return result[0];
		}

		result[0].add(null);
		for (int len = 1; len <= n; len++) {
			result[len] = new ArrayList<TreeNode>();
			for (int j = 0; j < len; j++) {
				for (TreeNode nodeL : result[j]) {
					for (TreeNode nodeR : result[len - j - 1]) {
						TreeNode node = new TreeNode(j + 1);
						node.left = nodeL;
						node.right = clone(nodeR, j + 1);
						result[len].add(node);
					}
				}
			}
		}
		return result[n];
	}

	private TreeNode clone(TreeNode n, int offset) {
		if (n == null) {
			return null;
		}
		TreeNode node = new TreeNode(n.val + offset);
		node.left = clone(n.left, offset);
		node.right = clone(n.right, offset);
		return node;
	}

	/***************************** Pattern 2: Pattern Name?? *************************/

	/***************************** Pattern 3: 0/1 Knapsack *************************/

	/* Partition Equal Subset Sum/Equal Subset Sum Partition/Partition problem: 
	 * It's similar to Subset Sum Problem which asks us to find if there is a subset whose sum equals to target value. 
	 * For this problem, the target value is exactly the half of sum of array.
	 */
	// Approach1: Recursive function; Time Complexity: O(2^n)
	public boolean canEqualSubsetPartition1(int arr[]) {
		int n = arr.length, sum = 0;
		for (int i = 0; i < n; i++)
			sum += arr[i];
		if (sum % 2 == 1) return false;
		return isSubsetSum32(arr, sum / 2);
	}

	// Approach3: Bottom Up DP - Time: O(n*sum);
	// Space: O(n*sum)
	public boolean canEqualSubsetPartition31(int[] nums) {
		int sum = 0;
		for (int n : nums)
			sum += n;
		if (sum % 2 != 0) return false;
		sum /= 2;
		int n = nums.length;
		boolean[][] dp = new boolean[n + 1][sum + 1];
		for (int i = 0; i < n; i++)
			dp[i][0] = true;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= sum; j++) {
				if (j < nums[i - 1]) {
					dp[i][j] = dp[i - 1][j];
				} else {
					dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
				}
			}
		}
		return dp[n][sum];
	}

	/* Approach3: Bottom Up DP - 
	 * Time: O(n*sum); Space: O(sum)
	 */
	public boolean canEqualSubsetPartition32(int[] nums) {
		int sum = 0;
		for (int n : nums)
			sum += n;
		if (sum % 2 != 0) return false;
		sum /= 2;
		int n = nums.length;
		boolean[] dp = new boolean[sum + 1];
		dp[0] = true;
		for (int i = 0; i < n; i++)
			for (int j = sum; j >= nums[i]; j--)
				dp[j] = dp[j] || dp[j - nums[i]];
		return dp[sum];
	}

	/* Minimum sum partition/Minimum Subset Sum Difference:
	 * Given an array, the task is to divide it into two sets S1 and S2 such that the absolute difference between their
	 * sums is minimum. Returns minimum possible difference between sums of two subsets 
	 * Input: 36 7 46 40 -> Sets: {}, {}
	 * Output: 23
	 */

	// Approach1: Recursive Function; Time Complexity: O(2^n)
	public int minSumPartition1(int[] arr) {
		int n = arr.length, sum = 0;
		for (int i = 0; i < n; i++)
			sum += arr[i];
		return minSumPartition1(arr, n - 1, 0, sum);
	}

	// Function to find the minimum sum
	public int minSumPartition1(int arr[], int i, int subsetSum, int sumTotal) {
		if (i < 0) {
			int remaining = sumTotal - subsetSum;
			return Math.abs(remaining - subsetSum);
		}
		return Math.min(minSumPartition1(arr, i - 1, subsetSum + arr[i], sumTotal),
				minSumPartition1(arr, i - 1, subsetSum, sumTotal));
	}

	// Approach3: Using DP - Bottom Up Approach
	public int minSumPartition3(int[] arr) {
		int n = arr.length, sum = 0;
		for (int a : arr)
			sum += a;
		boolean[][] dp = new boolean[n + 1][sum + 1];
		for (int i = 0; i <= n; i++)
			dp[i][0] = true;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= sum; j++) {
				if (j < arr[i - 1]) {
					dp[i][j] = dp[i - 1][j];
				} else {
					dp[i][j] = dp[i - 1][j] || dp[i - 1][j - arr[i - 1]];
				}
			}
		}
		for (int j = sum / 2; j >= 0; j--)
			if (dp[n][j]) {
				System.out.println(j);
				return sum - 2 * j;
			}
		return 0;
	}

	/*
	 * Count of Subset Sum/Perfect Sum Problem:
	 * 
	 */
	// Approach1: Using Recursive Function; Time Complexity: O(2^n)
	public int countSubsetSum1(int arr[], int sum) {
		return countSubsetSum1(arr, arr.length - 1, sum);
	}

	private int countSubsetSum1(int arr[], int i, int sum) {
		if (sum == 0) return 1;
		if (i < 0) return 0;
		if (arr[i] > sum) return countSubsetSum1(arr, i - 1, sum);
		return countSubsetSum1(arr, i - 1, sum) + countSubsetSum1(arr, i - 1, sum - arr[i]);
	}

	// Approach3: Bottom Up DP - Time: O(n*sum); Space: O(n*sum)
	public int countSubsetSum3(int[] nums, int sum) {
		int n = nums.length, count = 0;
		boolean[] dp = new boolean[sum + 1];
		dp[0] = true;
		for (int i = 0; i < n; i++)
			for (int j = sum; j >= nums[i]; j--) {
				if (j == sum && dp[j - nums[i]]) count++;
				else dp[j] = dp[j] || dp[j - nums[i]];
			}

		return count;
	}

	/*
	 * Given n items with size Ai, an integer m denotes the size of a backpack. How full you can fill this backpack?
	 * Example 1: Input:  [3,4,8,5], backpack size=10; Output:  9
	 * Example 2: Input:  [2,3,5,7], backpack size=12; Output:  12
	 */
	// 1.Recursive Approach:
	public int backPack1(int m, int[] A) {
		return backPack1(A, m, 0, 0);
	}

	public int backPack1(int[] arr, int max, int curr, int i) {
		if (i >= arr.length) return curr;
		if (curr + arr[i] > max) return backPack1(arr, max, curr, i + 1);

		return Math.max(backPack1(arr, max, curr + arr[i], i + 1), backPack1(arr, max, curr, i + 1));
	}

	// DP: Bottom Up Approach - 1D Array; Time:O(n.cap); Space:O(cap)
	public int backPack3(int cap, int[] A) {
		int[] dp = new int[cap + 1];
		for (int i = 0; i < A.length; i++) {
			for (int j = cap; j >= A[i]; j--) {
				if (j >= A[i]) dp[j] = Math.max(dp[j], A[i] + dp[j - A[i]]);
			}
		}
		return dp[cap];
	}

	/***************************** Pattern 4: Unbounded Knapsack *************************/

	// Rod cutting
	/*public int cutRod(int price[], int n) {
		if (n <= 0) return 0;
		int max_val = Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {			
			int prev = price[i] + cutRod(price, n - i - 1);
			max_val = Math.max(max_val, prev);
		}
		return max_val;
	}*/

	// Rod cutting
	public int cutRod1(int price[]) {
		return cutRod1(price, price.length);
	}

	public int cutRod1(int price[], int n) {
		if (n <= 0) return 0;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			System.out.print(i + " ");
			max = Math.max(max, price[i] + cutRod1(price, n - i - 1));
		}
		return max;
	}

	public int cutRod2(int[] prices) {
		int len = prices.length;
		int[][] dp = new int[len + 1][len + 1];
		for (int i = 1; i <= len; i++)
			for (int j = 1; j <= len; j++)
				dp[i][j] = (j < i) ? dp[i - 1][j] : Math.max(dp[i - 1][j], prices[i - 1] + dp[i][j - i]);
		return dp[len][len];
	}

	// Approach-3: Bottom Up Approach - 1D array
	public int cutRod3(int price[]) {
		int dp[] = new int[price.length + 1];
		for (int i = 1; i <= price.length; i++) {
			for (int j = i; j <= price.length; j++) {
				dp[j] = Math.max(dp[j], dp[j - i] + price[i - 1]);
			}
		}
		return dp[price.length];
	}

	// How to print maximum number of A’s using given four keys. Print Max number of As using Ctrl-A, Ctrl-C, Crtl-V
	// Approach-1: Recursive approach
	public int printMaxNoOfA1(int n) {
		if (n <= 6) return n;
		int multiplier = 2, max = -1, currValue;
		for (int i = n - 3; i >= 1; i--) {
			currValue = multiplier * printMaxNoOfA1(i);
			if (currValue > max) max = currValue;
			multiplier++;
		}
		return max;
	}

	// Approach-2: Dynamic Programming approach
	/* The above function computes the same subproblems again and again. Re computations of same subproblems can be avoided
	 * by storing the solutions to subproblems and solving problems in bottom up manner.*/
	public int printMaxNoOfA2(int n) {
		if (n <= 6) return n;
		int[] result = new int[n + 1];
		for (int i = 1; i <= 6; i++)
			result[i] = i;
		for (int i = 7; i <= n; i++) {
			int multiplier = 2, currValue;
			for (int j = i - 3; j >= 1; j--) {
				currValue = multiplier * result[j];
				if (currValue > result[i]) result[i] = currValue;
				multiplier++;
			}
		}
		return result[n];
	}

	/********************* Pattern 5: String-Palindromic substring/subseq Probs ***********************/

	/* Minimum number of deletions to make a string palindrome:
	 * Given a string of size ‘n’. The task is to remove or delete minimum number of characters from the string so that
	 * the resultant string is palindrome.
	 *  Solution: Use Longest Palindromic Subsequence
	 */
	public int minimumNumberOfDeletions(String str) {
		int n = str.length();
		int len = lps3(str);
		return (n - len);
	}

	//Form a Palindrome (min no of chars needed to form palindrome)
	public int findMinInsertion1(String s) {
		return findMinInsertion1(s, 0, s.length() - 1);
	}

	public int findMinInsertion1(String s, int l, int h) {
		if (l > h) return Integer.MAX_VALUE;
		if (l == h) return 0;
		if (l == h - 1) return (s.charAt(l) == s.charAt(h)) ? 0 : 1;

		if (s.charAt(l) == s.charAt(h)) return findMinInsertion1(s, l + 1, h - 1);

		return Math.min(findMinInsertion1(s, l, h - 1), findMinInsertion1(s, l + 1, h));
	}

	int findMinInsertions3(String s) {
		int n = s.length();
		int dp[][] = new int[n][n];

		for (int len = 1; len < n; ++len)
			for (int i = 0, j = len; j < n; ++i, ++j)
				if (s.charAt(i) == s.charAt(j)) {
					dp[i][j] = dp[i + 1][j - 1];
				} else {
					dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
				}

		return dp[0][n - 1];
	}

	// Count All Palindromic Subsequence in a given String
	// 2.DP-Top down Approach
	public int countPalindromicSubseq2(String str, int i, int j) {
		int[][] dp = new int[str.length()][str.length()];
		return countPalindromicSubseq2(str, i, j, dp);
	}

	public int countPalindromicSubseq2(String str, int i, int j, int[][] dp) {
		if (i >= str.length() || j < 0) return 0;
		if (dp[i][j] != -1) return dp[i][j];
		if ((i - j == 1) || (i - j == -1)) {
			if (str.charAt(i) == str.charAt(j)) return dp[i][j] = 3;
			else return dp[i][j] = 2;
		}
		if (i == j) {
			return dp[1][j] = 1;
		} else if (str.charAt(i) == str.charAt(j)) {
			return dp[i][j] = countPalindromicSubseq2(str, i + 1, j, dp) + countPalindromicSubseq2(str, i, j - 1, dp)
					+ 1;
		} else {
			return dp[i][j] = countPalindromicSubseq2(str, i + 1, j, dp) + countPalindromicSubseq2(str, i, j - 1, dp)
					- countPalindromicSubseq2(str, i + 1, j - 1, dp);
		}

	}

	// 3.DP-Bottom Up Approach
	public int countPalindromicSubseq3(String str) {
		int N = str.length();
		int[][] cps = new int[N + 1][N + 1];
		for (int i = 0; i < N; i++)
			cps[i][i] = 1;
		for (int L = 2; L <= N; L++) {
			for (int i = 0; i < N; i++) {
				int k = L + i - 1;
				if (k < N) {
					if (str.charAt(i) == str.charAt(k)) {
						cps[i][k] = cps[i][k - 1] + cps[i + 1][k] + 1;
					} else {
						cps[i][k] = cps[i][k - 1] + cps[i + 1][k] - cps[i + 1][k - 1];
					}
				}
			}
		}
		return cps[0][N - 1];
	}

	//TODO: Change the logic using only one 2D array
	// Count of Palindromic Substrings
	public int CountPalindromicSubstrings(String str) {
		int n = str.length();
		int dp[][] = new int[n][n];
		boolean P[][] = new boolean[n][n];
		for (int i = 0; i < n; i++)
			P[i][i] = true;
		for (int gap = 1; gap < n; gap++) {
			for (int i = 0; i < n - gap; i++) {
				int j = gap + i;
				if (str.charAt(i) == str.charAt(j) && gap == 1) {
					P[i][i + 1] = true;
					dp[i][j] = 1;
				} else {
					if (str.charAt(i) == str.charAt(j) && P[i + 1][j - 1]) P[i][j] = true;
					dp[i][j] = dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1];
					if (P[i][j] == true) dp[i][j] += 1;
				}
			}
		}
		return dp[0][n - 1];
	}

	/* Sherlock and Anagrams:
	 * Two strings are anagrams of each other if the letters of one string can be rearranged to form the other string. 
	 * Given a string, find the number of pairs of substrings of the string that are anagrams of each other.
	 * Eg: abba => The list of all anagrammatic pairs is {a,a}, {ab, ba}, {b,b}, {abb, bba}; Count: 4
	 */
	// Approach1: Brute Force Approach:
	static int sherlockAndAnagrams(String s) {
		int n = s.length(), count = 0;
		for (int len = 1; len < n; len++) {
			for (int i = 0; i < n - len + 1; i++) {
				String s1 = s.substring(i, i + len);
				for (int j = i + 1; j < n - len + 1; j++) {
					String s2 = s.substring(j, j + len);
					if (isAnagram(s1, s2)) count++;
				}
			}
		}
		return count;
	}

	private static boolean isAnagram(String s1, String s2) {
		int m = s1.length(), n = s2.length();
		if (m != n) return false;
		if (m == 1) return s1.equals(s2) ? true : false;
		int[] hash = new int[26];
		for (int i = 0; i < m; i++) {
			hash[s1.charAt(i) - 'a']++;
			hash[s2.charAt(i) - 'a']--;
		}
		for (int i = 0; i < 26; i++)
			if (Math.abs(hash[i]) != 0) return false;
		return true;
	}

	/************************** Pattern 6: String-Substring/Subsequence Probs *******************/
	// Shortest Common Supersequence:
	// Solution: Modification of LCS;
	// 1.Recursion
	public int shortestCommonSuperSeq1(String str1, String str2) {
		return shortestCommonSuperSeq1(str1, str2, str1.length(), str2.length());
	}

	private int shortestCommonSuperSeq1(String str1, String str2, int m, int n) {
		if (m == 0) return n;
		if (n == 0) return m;
		if (str1.charAt(m - 1) == str2.charAt(n - 1)) return 1 + shortestCommonSuperSeq1(str1, str2, m - 1, n - 1);
		return 1 + Math.min(shortestCommonSuperSeq1(str1, str2, m - 1, n),
				shortestCommonSuperSeq1(str1, str2, m, n - 1));
	}

	// 3.DP Bottom Up Approach:
	public int shortestCommonSuperSeq31(String s1, String s2) {
		int m = s1.length(), n = s2.length();
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
					dp[i][j] = 1 + dp[i - 1][j - 1];
				} else {
					dp[i][j] = 1 + Math.min(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}
		return dp[m][n];
	}

	// 3.DP Bottom Up Approach:
	/* Directly using LCS Solution:
	 * Length of the shortest supersequence  = (Sum of lengths of given two strings) - (Length of LCS of two given strings) 
	 */

	// Directly using LCS Solution:
	int shortestSuperSequence(String X, String Y) {
		int m = X.length();
		int n = Y.length();
		int l = lcs3(X, Y);
		return (m + n - l);
	}

	// Longest Repeating Subsequence:
	/* Solution: Modification of Longest Common Subsequence problem. The idea is to find the LCS(str,
	 * str)where str is the input string with the restriction that when both the characters are same, they shouldn’t be
	 * on the same index in the two strings.
	 */
	// 1.Recursive approach
	public int longestRepeatingSubSeq1(String str1, String str2) {
		return longestRepeatingSubSeq1(str1, str2, str1.length() - 1, str2.length() - 1);
	}

	private int longestRepeatingSubSeq1(String str1, String str2, int i, int j) {
		if (i < 0 || j < 0) return 0;
		if (str1.charAt(i) == str2.charAt(j) && i != j) return 1 + longestRepeatingSubSeq1(str1, str2, i - 1, j - 1);
		return Math.max(longestRepeatingSubSeq1(str1, str2, i - 1, j), longestRepeatingSubSeq1(str1, str2, 1, j - 1));
	}

	// 3.DP Bottom Up Approach
	public int longestRepeatingSubSeq3(String str) {
		int n = str.length();
		int[][] dp = new int[n + 1][n + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (str.charAt(i - 1) == str.charAt(j - 1) && i != j) {
					dp[i][j] = 1 + dp[i - 1][j - 1];
				} else {
					dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
				}
			}
		}
		return dp[n][n];
	}

	/*
	 * Minimum Deletions and Insertions to Transform a String into another:
	 * Given two strings ‘str1’ and ‘str2’ of size m and n respectively. The task is to remove/delete and insert
	 * minimum number of characters from/in str1 so as to transform it into str2. Convert Str1 to Str2;
	 * Input : str1 = "heap", str2 = "pea" 
	 * Output : Minimum Deletion = 2 and Minimum Insertion = 1
	 * Explanation: p and h deleted from heap. Then, p is inserted at the beginning.
	 * Solution: Use LCS solution
	 */
	public void printMinDelAndInsert3(String str1, String str2) {
		int len1 = str1.length();
		int len2 = str2.length();
		int lcsLen = lcs3(str1, str2);
		System.out.println("Minimum number of " + "deletions = ");
		System.out.println(len1 - lcsLen);
		System.out.println("Minimum number of " + "insertions = ");
		System.out.println(len2 - lcsLen);
	}

	/*
	 * Wild card Matching:
	 * Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*'.
	'?' Matches any single character.
	'*' Matches any sequence of characters (including the empty sequence).
	 */
	// Approach1: Recursion
	// Approach2: Using DP-Top Down Approach
	// Approach3: Using DP-Bottom Up Approach- Time: O(mn), Space: O(mn)
	public boolean wildCardMatch3(String s, String p) {
		int m = s.length(), n = p.length();
		boolean[][] dp = new boolean[m + 1][n + 1];
		dp[0][0] = true;
		for (int j = 2; j <= n; j++)
			if (p.charAt(j - 1) == '*') dp[0][j] = dp[0][j - 1];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				//Here i-1, j-1 are curr index
				if (p.charAt(j - 1) == s.charAt(i - 1) || p.charAt(j - 1) == '?') {
					dp[i][j] = dp[i - 1][j - 1];
				} else if (p.charAt(j - 1) == '*') {
					dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
				} else {
					dp[i][j] = false;
				}

			}
		}
		return dp[m][n];
	}

	// Approach4: Linear Time Solution
	public int isMatch(final String s, final String p) {
		int m = s.length(), n = p.length();
		int i = 0, j = 0, star = -1, mark = 0;
		while (i < m) {
			if (j < n && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '?')) {
				i++;
				j++;
			} else if (j < n && p.charAt(j) == '*') {
				star = j;
				mark = i;
				j++;
			} else if (star != -1) {
				j = star + 1;
				i = ++mark;
			} else {
				return 0;
			}
		}

		while (j < n && p.charAt(j) == '*') j++;

		return j == n ? 1 : 0;
	}

	/*
	 * Regular Expression Matching:
	 * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
	 *  '.' Matches any single character.
	 *  '*' Matches zero or more of the preceding element.
	 */

	// Approach1: Recursion
	// Approach2: Using DP-Top Down Approach
	// Approach3: Using DP-Bottom Up Approach- Time: O(mn), Space: O(mn) - Similar to WildCard Matching Prob
	public boolean regExMatch3(String s, String p) {
		int m = s.length(), n = p.length();
		boolean[][] dp = new boolean[m + 1][n + 1];
		// Base case: For both s & p are empty
		dp[0][0] = true;
		for (int j = 2; j <= n; j++)
			if (p.charAt(j - 1) == '*') dp[0][j] = dp[0][j - 2];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				// Note: Here i-1, j-1 is curr index, i-2, j-2 is prev index
				if (p.charAt(j - 1) == s.charAt(i - 1) || p.charAt(j - 1) == '.') {
					dp[i][j] = dp[i - 1][j - 1];
				} else if (p.charAt(j - 1) == '*') {
					dp[i][j] = dp[i][j - 2]; //This is for 0 occurrence of the prev char
					if (p.charAt(j - 2) == s.charAt(i - 1) || p.charAt(j - 2) == '.')
						dp[i][j] = dp[i][j] || dp[i - 1][j];
				} else {
					dp[i][j] = false;
				}
			}
		}
		return dp[m][n];
	}

	/***************************** Pattern 7: Array-Subsequence Probs *******************************/

	// Minimum number of deletions to make a sorted sequence:
	/* Approach1: 
	 * A simple solution is to remove all subsequences one by one and check if remaining set of elements are in sorted
	 * order or not. Time complexity of this solution is exponential.
	 */

	// Approach3:
	/* An efficient approach uses the concept of finding the length of the longest increasing subsequence of a given
	 * sequence.
	 */

	public int minimumNumberOfDeletions(int arr[], int n) {
		// Find longest increasing subsequence
		int len = LIS4(arr);

		// After removing elements other than the lis, we get sorted sequence.
		return (n - len);
	}

	// Longest Zig-Zag Subsequence or Longest Alternating subsequence

	// Approach3: DP-Bottom Up Approach
	public int longestZigZagSubsequence(int arr[], int n) {
		int dp[][] = new int[n][2];

		for (int i = 0; i < n; i++)
			dp[i][0] = dp[i][1] = 1;

		int res = 1;

		for (int i = 1; i < n; i++) {
			for (int j = 0; j < i; j++) {
				if (arr[j] < arr[i] && dp[i][0] < dp[j][1] + 1) {
					dp[i][0] = dp[j][1] + 1;
				}

				if (arr[j] > arr[i] && dp[i][1] < dp[j][0] + 1) {
					dp[i][1] = dp[j][0] + 1;
				}
			}

			if (res < Math.max(dp[i][0], dp[i][1])) {
				res = Math.max(dp[i][0], dp[i][1]);
			}
		}

		return res;
	}

	/* Box Stacking:
	 * You are given a set of n types of rectangular 3-D boxes, where the i^th box has height h(i), width w(i) and depth
	 * d(i) (all real numbers).
	 * You want to create a stack of boxes which is as tall as possible, but you can only stack a box on top of another box 
	 * if the dimensions of the 2-D base of the lower box are each strictly larger than those of the 2-D base of the higher
	 * box. Of course, you can rotate a box so that any side functions as its base. It is also allowable to use multiple 
	 * instances of the same type of box.
	 */
	public int boxStacking(int[][] dimensions) {
		// 1.Create all rotations of boxes, always keep length greater or equal to width
		Box[] boxes = new Box[3 * dimensions.length];
		for (int i = 0; i < dimensions.length; i++) {
			boxes[i * 3] = createBox(dimensions[i][0], dimensions[i][1], dimensions[i][2]);
			boxes[i * 3 + 1] = createBox(dimensions[i][1], dimensions[i][0], dimensions[i][2]);
			boxes[i * 3 + 2] = createBox(dimensions[i][2], dimensions[i][1], dimensions[i][0]);
		}

		// 2.Sort: Decreasing Order based on (length * width)
		Arrays.sort(boxes, (a, b) -> ((b.length * b.width)) - (a.length * a.width));

		// 3.Find the max height using Longest Increasing Sequence algorithm
		int dp[] = new int[boxes.length];
		int result[] = new int[boxes.length];

		for (int i = 0; i < dp.length; i++) {
			dp[i] = boxes[i].height;
			result[i] = i;
		}

		int max = dp[0];
		for (int i = 1; i < dp.length; i++) {
			for (int j = 0; j < i; j++) {
				if (boxes[i].length < boxes[j].length && boxes[i].width < boxes[j].width) {
					if (dp[i] < dp[j] + boxes[i].height) {
						// dp[] array used to find max height.
						dp[i] = dp[j] + boxes[i].height;
						// result[] array used to find the dimensions
						result[i] = j;
					}
				}
			}
			max = Math.max(max, dp[i]);
		}

		return max;
	}

	private Box createBox(int height, int side1, int side2) {
		Box box = new Box();
		box.height = height;
		box.length = Math.max(side1, side2);
		box.width = Math.min(side1, side2);
		return box;
	}

	/******************************* Others: Uncategorized ********************/
	/*
	 * Text Justification - Leetcode:
	 * Given an array of words and a width maxWidth, format the text such that each line has exactly maxWidth characters 
	 * and is fully (left and right) justified.
	 * You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra 
	 * spaces ' ' when necessary so that each line has exactly maxWidth characters.
	 */
	//Time: O(n*maxWidth); Space: O(lines*maxWidth); where n - no of words; lines - no of lines in the result
	public List<String> fullJustify(String[] words, int maxWidth) {
		int l = 0, r = 0, n = words.length;
		List<String> result = new ArrayList<>();
		while (l < n) {
			//1.Find the right pointer of words based on maxWidth
			int len = words[l].length();
			r = l + 1;
			//Here r-l-1 denotes no of space between words
			while (r < n && (len + words[r].length() + (r - l - 1)) < maxWidth) {
				len += words[r].length();
				r++;
			}

			//2.Justify the words
			result.add(justify(words, l, r - 1, len, maxWidth));
			l = r;
		}
		return result;
	}

	private String justify(String[] words, int l, int r, int wordsLength, int maxWidth) {
		//If it is single word in a line
		if (r - l == 0) return padResult(words[l], maxWidth);

		boolean isLastLine = r == words.length - 1;
		int numSpaces = r - l;
		int totalSpace = maxWidth - wordsLength;

		String space = isLastLine ? " " : whitespace(totalSpace / numSpaces);
		int extraSpace = isLastLine ? 0 : totalSpace % numSpaces;

		StringBuilder result = new StringBuilder();
		for (int i = l; i <= r; i++) {
			result.append(words[i]).append(space).append(extraSpace-- > 0 ? " " : "");
		}

		return padResult(result.toString().trim(), maxWidth);
	}

	private String padResult(String word, int maxWidth) {
		return word + whitespace(maxWidth - word.length());
	}

	public String whitespace(int numSpaces) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numSpaces; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	/*
	 * Text Justification/Word Wrap Problem - GeeksforGeeks:
	 * Given a sequence of words, and a limit on the number of characters that can be put in one line (line width). Put line breaks in the
	 * given sequence such that the lines are printed neatly. Assume that the length of each word is smaller than the line width. 
	 * The word processors like MS Word do task of placing line breaks. The idea is to have balanced lines. In other words, not have few 
	 * lines with lots of extra spaces and some lines with small amount of extra spaces.
	 * Eg: For example, consider the following string and line width M = 15
	 *        "Geeks for Geeks presents word wrap problem"
	 *     Following is the optimized arrangement of words in 3 lines
	 *     		Geeks for Geeks
	 *     		presents word
	 *     		wrap problem 
	 */
	/*
	 * Approach1: The greedy solution is to place as many words as possible in the first line. Then do the same thing for the second line 
	 * and so on until all words are placed. This solution gives optimal solution for many cases, but doesn’t give optimal solution in all cases. 
	 * Despite being sub-optimal in some cases, the greedy approach is used by many word processors like MS Word and OpenOffice.org Writer.
	 * 
	 * Approach2: Recursive & DP
	 * The problem is to minimize the following total cost.
	 *  	Cost of a line = (Number of extra spaces in the line)^3 or (Number of extra spaces in the line)^2
	 *  	Total Cost = Sum of costs for all lines
	 * Please note that the total cost function is not sum of extra spaces, but sum of cubes (or square is also used) of extra spaces. 
	 * The idea behind this cost function is to balance the spaces among lines. For example, consider the following two arrangement of same 
	 * set of words: 
	 * 	1. There are 3 lines. One line has 3 extra spaces and all other lines have 0 extra spaces. Total extra spaces = 3 + 0 + 0 = 3. 
	 * 	   Total cost = 3*3*3 + 0*0*0 + 0*0*0 = 27.
	 * 	2. There are 3 lines. Each of the 3 lines has one extra space. Total extra spaces = 1 + 1 + 1 = 3. Total cost = 1*1*1 + 1*1*1 + 1*1*1 = 3.
	 * Ref: https://www.geeksforgeeks.org/word-wrap-problem-dp-19/
	 */

	//Using Greedy Algorithm:
	public String justify1(String words[], int width) {
		StringBuilder result = new StringBuilder();

		int i = 0, j = 0, n = words.length;
		while (i < n) {
			int len = words[i].length();
			result.append(words[i]);
			j = i + 1;
			while (j < n && len + words[j].length() + (j - i - 1) < width) {
				result.append(" ").append(words[j]);
				len += words[j].length();
				j++;
			}
			if (j != n) result.append(" ");
			result.append("\n");
			i = j;
		}

		return result.toString();
	}

	//Using DP:
	public String justify2(String words[], int width) {
		int cost[][] = new int[words.length][words.length];

		//next 2 for loop is used to calculate cost of putting words from i to j in one line. If words don't fit in one line then we put
		//Integer.MAX_VALUE there.
		for (int i = 0; i < words.length; i++) {
			cost[i][i] = width - words[i].length();
			for (int j = i + 1; j < words.length; j++) {
				cost[i][j] = cost[i][j - 1] - words[j].length() - 1;
			}
		}

		for (int i = 0; i < words.length; i++) {
			for (int j = i; j < words.length; j++) {
				cost[i][j] = cost[i][j] < 0 ? Integer.MAX_VALUE : (int) Math.pow(cost[i][j], 2);
			}
		}

		//minCost from i to len is found by trying j between i to len and checking which one has min value
		int minCost[] = new int[words.length];
		int result[] = new int[words.length];
		for (int i = words.length - 1; i >= 0; i--) {
			minCost[i] = cost[i][words.length - 1];
			result[i] = words.length;
			for (int j = words.length - 1; j > i; j--) {
				if (cost[i][j - 1] == Integer.MAX_VALUE) continue;

				if (minCost[i] > minCost[j] + cost[i][j - 1]) {
					minCost[i] = minCost[j] + cost[i][j - 1];
					result[i] = j;
				}
			}
		}
		System.out.println("Minimum cost is " + minCost[0]);
		return getResult(words, result);
	}

	private String getResult(String[] words, int[] result) {
		int i = 0;
		int j;
		//finally put all words with new line added in string buffer and print it.
		StringBuilder builder = new StringBuilder();
		do {
			j = result[i];
			for (int k = i; k < j; k++) {
				builder.append(words[k] + " ");
			}
			builder.append("\n");
			i = j;
		} while (j < words.length);
		return builder.toString();
	}

	/***************************** Methods for reusage *******************/
	// Time: O(n*sum); Space: O(sum)
	public boolean isSubsetSum32(int[] arr, int sum) {
		boolean dp[] = new boolean[sum + 1];
		dp[0] = true;
		for (int i = 0; i < arr.length; i++)
			for (int j = sum; j >= arr[i]; j--)
				dp[j] = dp[j] || dp[j - arr[i]];
		return dp[sum];
	}

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

		return result[0][n - 1];
	}

	public int lcs3(String s1, String s2) {
		int m = s1.length(), n = s2.length();
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0 || j == 0) {
					dp[i][j] = 0;
				} else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
					dp[i][j] = 1 + dp[i - 1][j - 1];
				} else {
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}
		return dp[m][n];
	}

	public int LIS4(int[] nums) {
		int[] dp = new int[nums.length];
		int size = 0;
		for (int x : nums) {
			int l = 0, h = size;
			while (l != h) {
				int m = (l + h) / 2;
				if (dp[m] < x) l = m + 1;
				else h = m;
			}
			dp[l] = x;
			if (l == size) ++size;
		}
		return size;
	}

}