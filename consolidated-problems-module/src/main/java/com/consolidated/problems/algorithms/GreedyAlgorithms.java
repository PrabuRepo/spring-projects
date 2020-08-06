package com.consolidated.problems.algorithms;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeMap;

import com.common.model.HuffmanCode;

public class GreedyAlgorithms {

	/************************* Type1: Sweepline ***************************/
	//TODO: Time Intersection
	//TODO: Number of Airplanes in the Sky

	/*************************** Type2: Optimal Solution Problem ******************/
	/* Minimum number of Coins:
	 * Given a value N, total sum you have. You have to make change for Rs. N, and there is infinite supply of each of the
	 * denominations in Indian currency
	 */
	private final int[] coins = { 1, 2, 5, 10, 20, 50, 100, 200, 500, 2000 };

	public void minNoOfCoins(int sum) {
		if (sum < 1)
			return;

		int i = coins.length - 1;
		while (sum > 0) {
			if (sum - coins[i] >= 0) {
				System.out.print(coins[i] + " ");
				sum -= coins[i];
			} else {
				i--;
			}
		}
	}

	/*
	 * Maximize Toys:
	 * Given an array consisting cost of toys. Given an integer K depicting the amount with you. Maximize the number of toys you 
	 * can have with K amount.
	 */
	// Similar to Minimum number of Coins
	public static int maximizeToys(int[] prices, int k) {
		if (k < 1)
			return 0;

		Arrays.sort(prices);
		int count = 0;

		for (int i = 0; (k > 0 && i < prices.length); i++) {
			if (k - prices[i] >= 0) {
				k -= prices[i];
				count++;
			}
		}

		return count;
	}

	/*
	 * Minimum Absolute Difference in an Array
	 * Complete the minimumAbsoluteDifference function in the editor below. It should return an integer that represents
	 * the minimum absolute difference between any pair of elements.
	 */
	static int minimumAbsoluteDifference(int[] arr) {
		Arrays.sort(arr);

		int minDiff = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length - 1; i++)
			minDiff = Math.min(minDiff, Math.abs(arr[i] - arr[i + 1]));

		return minDiff;
	}

	/*
	 * Max length chain/Maximum Length of Pair Chain: 
	 * Time Complexity: O(nlogn)
	 */
	public int findLongestChain(int[][] pairs) {
		int count = 1, i = 0, j = 1;
		Arrays.sort(pairs, (a, b) -> a[0] - b[0]);
		while (i < pairs.length && j < pairs.length) {
			if (pairs[i][1] < pairs[j][0]) {
				count++;
				i = j;
				j++;
			} else {
				if (pairs[i][1] > pairs[j][1])
					i = j;
				j++;
			}
		}

		return count;
	}

	/****************************** Type3: Possibilities ****************************/

	/*
	 * Largest number possible:
	 * Given two numbers 'N' and 'S' , find the largest number that can be formed with 'N' digits and whose sum
	 * of digits should be equals to 'S'.
	 * Input:2 9; Output: 90
	 * Input:3 20; Output: 992 
	 */
	public String largestNumber(int n, int sum) {
		if (sum == 0 || sum > n * 9)
			return "-1";
		StringBuilder sb = new StringBuilder();
		while (n-- > 0) {
			if (sum > 9) {
				sum -= 9;
				sb.append(9);
			} else {
				sb.append(sum % 10);
				sum -= sum % 10;
			}
		}
		return sb.toString();
	}

	/*
	 *  Maximum Swap: Given a non-negative integer, you could swap two digits at most once to get the maximum valued number.
	 *  Return the maximum valued number you could get.
	 */
	// Approach 1: Traverse from right to left index and find the max & min index to swap;
	public int maximumSwap(int num) {
		if (num < 10)
			return num;

		char[] digits = String.valueOf(num).toCharArray();

		int l = 0, h = 0, maxIndex = digits.length - 1;
		for (int i = digits.length - 2; i >= 0; i--) {
			if (digits[i] == digits[maxIndex])
				continue;

			if (digits[i] > digits[maxIndex]) {
				maxIndex = i;
			} else {
				h = maxIndex;
				l = i;
			}
		}

		if (l != h) {
			char temp = digits[l];
			digits[l] = digits[h];
			digits[h] = temp;
		}

		return Integer.valueOf(new String(digits));
	}

	/* Create Maximum Number:
	 * Given two arrays of length m and n with digits 0-9 representing two numbers. Create the maximum number of length
	 * k <= m + n from digits of the two. The relative order of the digits from the same array must be preserved. Return 
	 * an array of the k digits.
	 * Note: You should try to optimize your time and space complexity.
	 * Input: nums1 = [3, 4, 6, 5]; nums2 = [9, 1, 2, 5, 8, 3]; k = 5
	 * Output:[9, 8, 6, 5, 3]
	 */
	/*Solution: In short we can first solve 2 simpler problem
	 *   - Create the maximum number of one array
	 *   - Create the maximum number of two array using all of their digits.
	 */
	public int[] maxNumber(int[] nums1, int[] nums2, int k) {
		int get_from_nums1 = Math.min(nums1.length, k);
		int[] maxNumber = new int[k];
		for (int i = Math.max(k - nums2.length, 0); i <= get_from_nums1; i++) {
			int[] res1 = new int[i];
			int[] res2 = new int[k - i];
			int[] res = new int[k];
			// Create maximum number array
			res1 = maxArray(nums1, i);
			res2 = maxArray(nums2, k - i);
			int pos1 = 0, pos2 = 0, tpos = 0;
			// Merge the max arrays
			while (res1.length > 0 && res2.length > 0 && pos1 < res1.length && pos2 < res2.length) {
				if (compare(res1, pos1, res2, pos2))
					res[tpos++] = res1[pos1++];
				else
					res[tpos++] = res2[pos2++];
			}
			while (pos1 < res1.length)
				res[tpos++] = res1[pos1++];
			while (pos2 < res2.length)
				res[tpos++] = res2[pos2++];

			// Finally choose the maximum number combinations
			if (!compare(maxNumber, 0, res, 0))
				maxNumber = res;
		}

		return maxNumber;
	}

	public int[] maxArray(int[] nums, int k) {
		int[] res = new int[k];
		int len = 0;
		for (int i = 0; i < nums.length; i++) {
			while (len > 0 && len + nums.length - i > k && res[len - 1] < nums[i]) {
				len--;
			}
			if (len < k)
				res[len++] = nums[i];
		}
		return res;
	}

	public boolean compare(int[] nums1, int start1, int[] nums2, int start2) {
		for (; start1 < nums1.length && start2 < nums2.length; start1++, start2++) {
			if (nums1[start1] > nums2[start2])
				return true;
			if (nums1[start1] < nums2[start2])
				return false;
		}
		return start1 != nums1.length;
	}

	/* Remove K Digits: 
	 * Given a non-negative integer num represented as a string, remove k digits from the number so that the 
	 * new number is the smallest possible. 
	 * Note: The length of num is less than 10002 and will be >= k. The given num does not contain any leading zero.
	 * Input: num = "1432219", k = 3; Output: "1219"
	 * Input: num = "10200", k = 1; Output: "200"
	 */public String removeKdigits(String num, int k) {
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < num.length(); i++) {
			while (!stack.isEmpty() && k > 0 && stack.peek() > num.charAt(i)) {
				stack.pop();
				k--;
			}
			stack.push(num.charAt(i));
		}

		// For corner cases same number like "44444"
		while (k > 0) {
			stack.pop();
			k--;
		}

		StringBuilder sb = new StringBuilder();
		for (char ch : stack)
			sb.append(ch);

		// Remove leading zeros
		while (sb.length() > 1 && sb.charAt(0) == '0')
			sb.deleteCharAt(0);

		return sb.length() == 0 ? "0" : sb.toString();
	}

	/*Find Permutation:
	 * By now, you are given a secret signature consisting of character 'D' and 'I'. 'D' represents a decreasing
	 * relationship between two numbers, 'I' represents an increasing relationship between two numbers. And our secret
	 * signature was constructed by a special integer array, which contains uniquely all the different number from 1 to
	 * n (n is the length of the secret signature plus 1). For example, the secret signature "DI" can be constructed by
	 * array [2,1,3] or [3,1,2], but won't be constructed by array [3,2,4] or [2,1,3,4], which are both illegal
	 * constructing special string that can't represent the "DI" secret signature.
	 * On the other hand, now your job is to find the lexicographically smallest permutation of [1, 2, ... n] could refer
	 * to the given secret signature in the input. 
	 * Example 1: Input: "I" Output: [1,2] Explanation: [1,2] is the only legal initial special string can construct secret 
	 * signature "I", where the number 1 and 2 construct an increasing relationship. 
	 * Example 2: Input: "DI" Output: [2,1,3] Explanation: Both [2,1,3] and [3,1,2] can construct the secret signature "DI",
	 * but since we want to find the one with the smallest lexicographical permutation, you need to output [2,1,3] Note: The 
	 * input string will only contain the character 'D' and 'I'.
	 */
	// Approach1: Assign the elements in forward & reverse direction
	public int[] findPermutation1(String s) {
		int num = 1, l = 0, r = 0;
		int[] result = new int[s.length() + 1];

		while (l < result.length) {
			if (l == s.length() || s.charAt(l) == 'I') {
				result[l++] = num++; // Store number in forward direction
			} else {
				r = l;
				while (r < s.length() && s.charAt(r) == 'D')
					r++;
				for (int i = r; i >= l; i--)
					result[i] = num++; // Store number in reverse direction
				l = r + 1;
			}
		}
		return result;
	}

	// Approach2: Assign the elements first, after that reverse the elements when char is 'D'.
	public int[] findPermutation2(String s) {
		int n = s.length(), arr[] = new int[n + 1];
		// Assign the elements
		for (int i = 0; i <= n; i++)
			arr[i] = i + 1; // sorted

		// Reverse the elements, if there in any 'D'
		for (int h = 0; h < n; h++) {
			if (s.charAt(h) == 'D') {
				int l = h;
				while (h < n && s.charAt(h) == 'D')
					h++;
				reverse(arr, l, h);
			}
		}
		return arr;
	}

	void reverse(int[] arr, int l, int h) {
		while (l < h) {
			arr[l] ^= arr[h];
			arr[h] ^= arr[l];
			arr[l] ^= arr[h];
			l++;
			h--;
		}
	}

	/*Next Permutation
	 * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
	 * If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
	 * The replacement must be in-place and use only constant extra memory.
	 * Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
	 * 1,2,3 -> 1,3,2
	 * 3,2,1 -> 1,2,3
	 * 1,1,5 -> 1,5,1
	 */

	//Time: O(n), Space: O(1)
	public void nextPermutation(int[] nums) {
		if (nums.length <= 1)
			return;

		int i = nums.length - 1;
		//1.Find first increasing seq from the right
		while (i > 0) {
			if (nums[i - 1] < nums[i])
				break;
			i--;
		}

		if (i > 0) {
			//2.Find first max value which is greater than (i-1)th index value from the last index.
			int maxIndex = nums.length - 1;
			while (maxIndex >= i) {
				if (nums[maxIndex] > nums[i - 1])
					break;
				maxIndex--;
			}
			swap(nums, i - 1, maxIndex);
		}

		//3.Reverse the values from index i to n-1;
		reverse(nums, i);
	}

	private void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	private void reverse(int[] nums, int l) {
		int h = nums.length - 1;
		while (l < h) {
			swap(nums, l, h);
			l++;
			h--;
		}
	}

	/*************************** Type4: Other Greedy Problems ******************/
	/*
	 * Huffman Coding & Decoding:
	 *    Huffman coding is a lossless data compression algorithm. The idea is to assign variable-length codes to input characters, 
	 * lengths of the assigned codes are based on the frequencies of corresponding characters. The most frequent character gets the 
	 * smallest code and the least frequent character gets the largest code.
	 * Time complexity: O(nlogn) where n is the number of unique characters
	 */
	public void huffmanCoding(String str) {
		// 1.Build map from input string, like key-> char & value->freq of chars
		Map<Character, Integer> charFreqMap = findCharFreq(str);

		// 2.Create a Priority Queue, which is used to build min heap based on char frequency
		PriorityQueue<HuffmanCode> queue = new PriorityQueue<>(charFreqMap.size(), new Comparator<HuffmanCode>() {
			public int compare(HuffmanCode o1, HuffmanCode o2) {
				return o1.count - o2.count;
			}
		});

		// 3.Create a leaf node for each unique character and build a min heap of all leaf nodes
		for (Entry<Character, Integer> entry : charFreqMap.entrySet()) {
			HuffmanCode hc = new HuffmanCode(entry.getKey(), entry.getValue());
			queue.add(hc);
		}

		// Repeat steps #4 and #5 until the heap contains only one node. The remaining node is the root node and the
		// tree is
		// complete.
		while (queue.size() > 1) {
			// 4.Extract two nodes with the minimum frequency from the min heap.
			HuffmanCode first = queue.poll(); // Extract first min
			HuffmanCode second = queue.poll(); // Extract second min

			// 5.Create a new internal node with frequency equal to the sum of the two nodes frequencies. Make the first
			// extracted
			// node as its left child and the other extracted node as its right child. Add this node to the min heap.
			HuffmanCode huffmanCode = new HuffmanCode('-', first.count + second.count);
			huffmanCode.left = first;
			huffmanCode.right = second;
			queue.add(huffmanCode);
		}

		Map<Character, String> encodedMap = new TreeMap<>();

		// 6.Encode the given string: String to Binary Conversion
		HuffmanCode root = queue.peek();
		encodeString(root, "", encodedMap);

		// 7.Concatenate all the binary in char order of input string.
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			builder.append(encodedMap.get(ch));
		}
		System.out.println("Huffman encoded data:" + builder.toString());

		// Huffman decoding
		System.out.println("Huffman decoded data:" + huffmanDecoding(root, builder.toString()));
	}

	// Build map from input string, like key-> char & value->freq of chars
	public Map<Character, Integer> findCharFreq(String str) {
		Map<Character, Integer> charFreqMap = new TreeMap<>();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			int freq = charFreqMap.get(ch) == null ? 0 : charFreqMap.get(ch);
			charFreqMap.put(ch, ++freq);
		}
		return charFreqMap;
	}

	/*
	 * Traverse the tree formed starting from the root. Maintain an auxiliary array. 
	 * While moving to the left child, write 0 to the array. While moving to the right child, write 1 to the array. 
	 * Print the array when a leaf node is encountered and also .
	 */
	public void encodeString(HuffmanCode root, String binaryStr, Map<Character, String> encodedData) {
		if (root.left == null && root.right == null && Character.isLetter(root.c)) {
			System.out.println(root.c + " : " + binaryStr + " : " + root.count);
			encodedData.put(root.c, binaryStr);
			return;
		}

		encodeString(root.left, binaryStr + "0", encodedData);
		encodeString(root.right, binaryStr + "1", encodedData);
	}

	/*
	 * To decode the encoded data we require the Huffman tree and binary encoded data.
	 */
	public String huffmanDecoding(HuffmanCode root, String encodedString) {
		HuffmanCode curr = root;
		StringBuilder decodedString = new StringBuilder();
		// We start from root and do following until a leaf is found.
		for (int i = 0; i < encodedString.length(); i++) {
			char ch = encodedString.charAt(i);
			// 1.If current bit is 0, we move to left node of the tree.
			if (ch == '0')
				curr = curr.left;
			else // 2.If the bit is 1, we move to right node of the tree.
				curr = curr.right;

			// 3.If during traversal, we encounter a leaf node, append character of that particular leaf node and then
			// again
			// continue the iteration of the encoded data starting from step 1.
			if (curr.left == null && curr.right == null) {
				decodedString.append(curr.c);
				curr = root;
			}
		}
		return decodedString.toString();
	}
}