package com.consolidated.problems.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.common.model.Box;
import com.common.model.TreeNode;

/* Grokking Dynamic Programming Patterns for Coding Interviews:
 * All the problems will be solved using 3 approaches,
 *    - Recursion
 *    - DP: Top Down Approach or Memoization 
 *    - DP: Bottom Up Approach or Tabulation
 */
public class DynamicProgramming {

	/***************************** Pattern 1: Simple Patterns *************************/

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
		TreeNode node = new TreeNode(n.data + offset);
		node.left = clone(n.left, offset);
		node.right = clone(n.right, offset);
		return node;
	}

	/*Paint Fence: 
	 * 	There is a fence with n posts, each post can be painted with one of the k colors. You have to paint all the posts such that 
	 * no more than two adjacent fence posts have the same color. Return the total number of ways you can paint the fence.
	 */

	/* Solution1:
	 * The key to solve this problem is finding this relation.f(n)=(k-1)(f(n-1)+f(n-2)) Assuming there are 3 posts, if
	 * the first one and the second one has the same color, then the third one has k-1 options. The first and second
	 * together has k options. If the first and the second do not have same color, the total is k * (k-1), then the
	 * third one has k options. Therefore, f(3) = (k-1)*k + k*(k-1)*k = (k-1)(k+k*k)
	 */

	public int paintFence1(int n, int k) {
		int dp[] = { 0, k, k * k, 0 };
		if (n <= 2)
			return dp[n];
		for (int i = 2; i < n; i++) {
			dp[3] = (k - 1) * (dp[1] + dp[2]);
			dp[1] = dp[2];
			dp[2] = dp[3];
		}
		return dp[3];
	}

	public int paintFence2(int n, int k) {
		if (n <= 0 || k <= 0)
			return 0;
		if (n == 1)
			return k;
		int preSame = 0, preDiff = k;
		for (int i = 1; i < n; i++) {
			int same = preDiff;
			int diff = (k - 1) * (preSame + preDiff);
			preSame = same;
			preDiff = diff;
		}
		return preSame + preDiff;
	}

	/***************************** Pattern 2: 0/1 Knapsack *************************/

	/* Partition Equal Subset Sum/Equal Subset Sum Partition/Partition problem: 
	 * It's similar to Subset Sum Problem which asks us to find if there is a subset whose sum equals to target value. 
	 * For this problem, the target value is exactly the half of sum of array.
	 */
	// Approach1: Recursive function; Time Complexity: O(2^n)
	public boolean canEqualSubsetPartition1(int arr[]) {
		int n = arr.length, sum = 0;
		for (int i = 0; i < n; i++)
			sum += arr[i];
		if (sum % 2 == 1)
			return false;
		return isSubsetSum32(arr, sum / 2);
	}

	// Approach3: Bottom Up DP - Time: O(n*sum);
	// Space: O(n*sum)
	public boolean canEqualSubsetPartition31(int[] nums) {
		int sum = 0;
		for (int n : nums)
			sum += n;
		if (sum % 2 != 0)
			return false;
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
		if (sum % 2 != 0)
			return false;
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
		if (sum == 0)
			return 1;
		if (i < 0)
			return 0;
		if (arr[i] > sum)
			return countSubsetSum1(arr, i - 1, sum);
		return countSubsetSum1(arr, i - 1, sum) + countSubsetSum1(arr, i - 1, sum - arr[i]);
	}

	// Approach3: Bottom Up DP - Time: O(n*sum); Space: O(n*sum)
	public int countSubsetSum3(int[] nums, int sum) {
		int n = nums.length, count = 0;
		boolean[] dp = new boolean[sum + 1];
		dp[0] = true;
		for (int i = 0; i < n; i++)
			for (int j = sum; j >= nums[i]; j--) {
				if (j == sum && dp[j - nums[i]])
					count++;
				else
					dp[j] = dp[j] || dp[j - nums[i]];
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
		if (i >= arr.length)
			return curr;
		if (curr + arr[i] > max)
			return backPack1(arr, max, curr, i + 1);

		return Math.max(backPack1(arr, max, curr + arr[i], i + 1), backPack1(arr, max, curr, i + 1));
	}

	// DP: Bottom Up Approach - 1D Array; Time:O(n.cap); Space:O(cap)
	public int backPack3(int cap, int[] A) {
		int[] dp = new int[cap + 1];
		for (int i = 0; i < A.length; i++) {
			for (int j = cap; j >= A[i]; j--) {
				if (j >= A[i])
					dp[j] = Math.max(dp[j], A[i] + dp[j - A[i]]);
			}
		}
		return dp[cap];
	}

	/***************************** Pattern 3: Unbounded Knapsack *************************/

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
		if (n <= 0)
			return 0;
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
		if (n <= 6)
			return n;
		int multiplier = 2, max = -1, currValue;
		for (int i = n - 3; i >= 1; i--) {
			currValue = multiplier * printMaxNoOfA1(i);
			if (currValue > max)
				max = currValue;
			multiplier++;
		}
		return max;
	}

	// Approach-2: Dynamic Programming approach
	/* The above function computes the same subproblems again and again. Re computations of same subproblems can be avoided
	 * by storing the solutions to subproblems and solving problems in bottom up manner.*/
	public int printMaxNoOfA2(int n) {
		if (n <= 6)
			return n;
		int[] result = new int[n + 1];
		for (int i = 1; i <= 6; i++)
			result[i] = i;
		for (int i = 7; i <= n; i++) {
			int multiplier = 2, currValue;
			for (int j = i - 3; j >= 1; j--) {
				currValue = multiplier * result[j];
				if (currValue > result[i])
					result[i] = currValue;
				multiplier++;
			}
		}
		return result[n];
	}

	//TODO: Move Jump Game based problem patterns

	/* Jump Game II: Minimum number of jumps to reach end. It can be solved using 
	 *   1. Recursive Approach
	 *   2. Linear Algorithm - Greedy Approach 
	 *   3. DP Approach
	 * 	 4. BFS Approach 
	 */

	// Minimum number of jumps to reach end -> Its similar to snake and ladder problems
	public int minJumps(int arr[]) {
		return minJumps(arr, 0, arr.length - 1);
	}

	// Returns minimum number of jumps to reach arr[h] from arr[l]
	public int minJumps(int arr[], int index, int n) {
		if (index >= n)
			return 0;
		if (arr[index] == 0)
			return Integer.MAX_VALUE;
		int minJumps = Integer.MAX_VALUE;
		for (int i = index + 1; i <= n && i <= index + arr[index]; i++) {
			int currJump = minJumps(arr, i, n);
			if (currJump != Integer.MAX_VALUE && currJump + 1 < minJumps) {
				minJumps = currJump + 1;
			}
		}
		return minJumps;
	}

	// Approach3: DP - Bottom up Approach; Time: O(n^2); Space: O(n)
	public int minJumps3(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n];
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[0] = 0;
		for (int i = 0; i < n - 1; i++)
			for (int j = 1; j <= nums[i] && i + j < n; j++)
				dp[i + j] = Math.min(dp[i + j], 1 + dp[i]);
		return dp[n - 1];
	}

	// Efficient Approach: Greedy Algorithm- Linear Approach
	public int minJumps4(int[] nums) {
		int currMax = 0, currEnd = 0, jumps = 0;
		int n = nums.length;

		for (int i = 0; i < n - 1; i++) {
			currMax = Math.max(currMax, i + nums[i]);
			if (i == currEnd) {
				//Edge case: if jumps are not reachable to last position
				if (i >= currMax)
					return Integer.MAX_VALUE;
				jumps++;
				currEnd = currMax;
			}
		}
		return jumps;
	}

	//TODO: Check this solution later
	public int minJumps41(int[] A) {
		if (A == null || A.length == 0)
			return -1;
		int jumps = 0, currMax = 0, currEnd = 0;
		for (int i = 0; i < A.length && i <= currEnd; i++) {
			if (i > currMax) {
				jumps++;
				currMax = currEnd;
			}
			currEnd = Math.max(currEnd, i + A[i]);
		}
		if (currEnd < A.length - 1)
			return -1;
		return jumps;
	}

	/*
	 * Jump Game I:Given an array of non-negative integers, you are initially positioned at the first index of the array. 
	 * Each element in the array represents your maximum jump length at that position. Determine if you are able to reach 
	 * the last index.
	 * 
	 */
	public boolean canJump(int[] nums) {
		int max = 0;
		for (int i = 0; i < nums.length; i++) {
			if (i > max)
				return false;
			max = Math.max(max, i + nums[i]);
		}

		return true;
	}

	/********************* Pattern 4: String-Palindromic substring/subseq Probs ***********************/

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

	// Count All Palindromic Subsequence in a given String
	// 2.DP-Top down Approach
	public int countPalindromicSubseq2(String str, int i, int j) {
		int[][] dp = new int[str.length()][str.length()];
		return countPalindromicSubseq2(str, i, j, dp);
	}

	public int countPalindromicSubseq2(String str, int i, int j, int[][] dp) {
		if (i >= str.length() || j < 0)
			return 0;
		if (dp[i][j] != -1)
			return dp[i][j];
		if ((i - j == 1) || (i - j == -1)) {
			if (str.charAt(i) == str.charAt(j))
				return dp[i][j] = 3;
			else
				return dp[i][j] = 2;
		}
		if (i == j)
			return dp[1][j] = 1;
		else if (str.charAt(i) == str.charAt(j))
			return dp[i][j] = countPalindromicSubseq2(str, i + 1, j, dp) + countPalindromicSubseq2(str, i, j - 1, dp)
					+ 1;
		else
			return dp[i][j] = countPalindromicSubseq2(str, i + 1, j, dp) + countPalindromicSubseq2(str, i, j - 1, dp)
					- countPalindromicSubseq2(str, i + 1, j - 1, dp);

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
					if (str.charAt(i) == str.charAt(j) && P[i + 1][j - 1])
						P[i][j] = true;
					dp[i][j] = dp[i][j - 1] + dp[i + 1][j] - dp[i + 1][j - 1];
					if (P[i][j] == true)
						dp[i][j] += 1;
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
					if (isAnagram(s1, s2))
						count++;
				}
			}
		}
		return count;
	}

	private static boolean isAnagram(String s1, String s2) {
		int m = s1.length(), n = s2.length();
		if (m != n)
			return false;
		if (m == 1)
			return s1.equals(s2) ? true : false;
		int[] hash = new int[26];
		for (int i = 0; i < m; i++) {
			hash[s1.charAt(i) - 'a']++;
			hash[s2.charAt(i) - 'a']--;
		}
		for (int i = 0; i < 26; i++)
			if (Math.abs(hash[i]) != 0)
				return false;
		return true;
	}

	/************************** Pattern 5: String-Substring/Subsequence Probs *******************/
	// Shortest Common Supersequence:
	// Solution: Modification of LCS;
	// 1.Recursion
	public int shortestCommonSuperSeq1(String str1, String str2) {
		return shortestCommonSuperSeq1(str1, str2, str1.length(), str2.length());
	}

	private int shortestCommonSuperSeq1(String str1, String str2, int m, int n) {
		if (m == 0)
			return n;
		if (n == 0)
			return m;
		if (str1.charAt(m - 1) == str2.charAt(n - 1))
			return 1 + shortestCommonSuperSeq1(str1, str2, m - 1, n - 1);
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
		if (i < 0 || j < 0)
			return 0;
		if (str1.charAt(i) == str2.charAt(j) && i != j)
			return 1 + longestRepeatingSubSeq1(str1, str2, i - 1, j - 1);
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
			if (p.charAt(j - 1) == '*')
				dp[0][j] = dp[0][j - 1];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
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
	public boolean wildCardMatch4(String s, String p) {
		int i = 0, j = 0, star = -1, mark = -1;
		while (i < s.length()) {
			if (j < p.length() && (p.charAt(j) == '?' || p.charAt(j) == s.charAt(i))) {
				i++;
				j++;
			} else if (j < p.length() && p.charAt(j) == '*') {
				star = j;
				mark = i;
				j++;
			} else if (star != -1) {
				j = star + 1;
				mark++;
				i = mark;
			} else {
				return false;
			}
		}
		while (j < p.length() && p.charAt(j) == '*')
			j++;
		return j == p.length();
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
		dp[0][0] = true;
		for (int j = 2; j <= n; j++)
			if (p.charAt(j - 1) == '*')
				dp[0][j] = dp[0][j - 2];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (p.charAt(j - 1) == s.charAt(i - 1) || p.charAt(j - 1) == '.') {
					dp[i][j] = dp[i - 1][j - 1];
				} else if (p.charAt(j - 1) == '*') {
					dp[i][j] = dp[i][j - 2];
					if (p.charAt(j - 2) == s.charAt(i - 1) || p.charAt(j - 2) == '.')
						dp[i][j] = dp[i][j] || dp[i - 1][j];
				} else {
					dp[i][j] = false;
				}
			}
		}
		return dp[m][n];
	}

	/***************************** Pattern 6: Array-Subsequence Probs *******************************/

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
				if (dp[m] < x)
					l = m + 1;
				else
					h = m;
			}
			dp[l] = x;
			if (l == size)
				++size;
		}
		return size;
	}

}