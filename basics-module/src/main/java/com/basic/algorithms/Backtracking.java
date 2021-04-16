package com.basic.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.common.utilities.Utils;

/*
 * Backtracking: Subsequence/Subsets, Combination, Combination Sum and Permutation.
 * These recursion problems can be solved using "Auxiliary Buffer" technique.
 * 	1.Termination case: If the size reaches the specific condition, 
 *    then print or add aux buffer into the result
 * 	2.Find the candidates/num from starting index
 *  3.Place the candidate/num into the aux buffer 
 *  4.Recurse to the next index.  
 */

//TODO: Write backtrack methods using array; Refer: interviewcamp videos
public class Backtracking {

	public void methodConsolidations(int[] nums, String str) {
		subsets1(nums);
		subsets2(nums);
		subsetsWithDup(nums);

		subSequence(str);

		combinations(4, 2);

		int target = 12;
		combinationSum(nums, target);
		combinationSum2(nums, target);
		combinationSum3(4, 2);

		permute1(nums);
		permute2(nums);

		permuteUnique1(nums);
		permuteUnique2(nums);
	}

	/*
	 * Subsets/Subsequence of Array: Time : O(2^n)
	 */
	public List<List<Integer>> subsets1(int[] nums) {
		List<List<Integer>> res = new ArrayList<>();
		backtrack1(res, new ArrayList<>(), nums, 0);
		res.forEach(k -> System.out.print(k + ", "));
		return res;
	}

	private void backtrack1(List<List<Integer>> res, List<Integer> buffer, int[] nums, int startIndex) {
		res.add(new ArrayList<>(buffer));
		for (int i = startIndex; i < nums.length; i++) {
			buffer.add(nums[i]);
			backtrack1(res, buffer, nums, i + 1);
			buffer.remove(buffer.size() - 1);
		}
	}

	/* Using Bit Manipulations:
	 *  Bit manipulation works because, No of possible numbers for n bits(2^n) == no of sequences/subsets for n chars in string(2^n)
	 */
	public List<List<Integer>> subsets2(int[] nums) {
		int noofSeq = (int) Math.pow(2, nums.length);
		List<List<Integer>> res = new ArrayList<>();
		for (int i = 0; i < noofSeq; i++) {
			List<Integer> temp = new ArrayList<>();
			for (int j = 0; j < nums.length; j++) {
				/*Returns true if and only if the designated bit is set. (Computes ((this & (1<<n)) != 0).)*/
				if ((i & (1 << j)) != 0) {
					temp.add(nums[j]);
				}
			}
			res.add(temp);
		}
		return res;
	}

	/* SubsetsII(Subset/Subseq of arr contains duplicates):
	 */
	public List<List<Integer>> subsetsWithDup(int[] nums) {
		List<List<Integer>> res = new ArrayList<>();
		Arrays.sort(nums);
		backtrack2(res, new ArrayList<>(), nums, 0);
		res.forEach(k -> System.out.print(k + ", "));
		return res;
	}

	private void backtrack2(List<List<Integer>> res, List<Integer> buffer, int[] nums, int startIndex) {
		res.add(new ArrayList<>(buffer));
		for (int i = startIndex; i < nums.length; i++) {
			//condition to avoid the duplicate
			if (i > startIndex && nums[i] == nums[i - 1]) continue;
			buffer.add(nums[i]);
			backtrack2(res, buffer, nums, i + 1);
			buffer.remove(buffer.size() - 1);
		}
	}

	// Subsets/Subseq of a string: Time: O(2^n)
	public List<List<Character>> subSequence(String str) {
		List<List<Character>> res = new ArrayList<>();
		backtrack(res, new ArrayList<>(), str, 0);
		res.forEach(k -> System.out.print(k + ", "));
		return res;
	}

	private void backtrack(List<List<Character>> res, List<Character> buffer, String str, int startIndex) {
		res.add(new ArrayList<>(buffer));
		for (int i = startIndex; i < str.length(); i++) {
			buffer.add(str.charAt(i));
			backtrack(res, buffer, str, i + 1);
			buffer.remove(buffer.size() - 1);
		}
	}

	/* Combinations: Formula: n!/k!(n-k)!
	 * 
	 * Time = n!/k!(n-k)!
	 */
	public List<List<Integer>> combinations(int n, int k) {
		List<List<Integer>> res = new ArrayList<>();
		backtrack3(n, k, 1, new ArrayList<>(), res);
		res.forEach(val -> System.out.print(val + ", "));
		return res;
	}

	public void backtrack3(int n, int k, int startVal, List<Integer> buffer, List<List<Integer>> res) {
		if (buffer.size() == k) {
			res.add(new ArrayList<>(buffer));
		} else {
			for (int i = startVal; i <= n; i++) {
				buffer.add(i);
				backtrack3(n, k, i + 1, buffer, res);
				buffer.remove(buffer.size() - 1);
			}
		}
	}

	/* Combination Sum :
	 * Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates 
	 * where the chosen numbers sum to target. You may return the combinations in any order.
	 * The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the frequency of at least 
	 * one of the chosen numbers is different.
	 */
	public List<List<Integer>> combinationSum(int[] nums, int target) {
		List<List<Integer>> res = new ArrayList<>();
		Arrays.sort(nums);
		backtrack4(res, new ArrayList<>(), nums, target, 0);
		res.forEach(val -> System.out.print(val + ", "));
		return res;
	}

	private void backtrack4(List<List<Integer>> res, List<Integer> buffer, int[] nums, int target, int startIndex) {
		if (target < 0) return;
		else if (target == 0) res.add(new ArrayList<>(buffer));
		else {
			for (int i = startIndex; i < nums.length; i++) {
				buffer.add(nums[i]);
				backtrack4(res, buffer, nums, target - nums[i], i); // here using 'i' to repeat the same number
				buffer.remove(buffer.size() - 1);
			}
		}
	}

	/* Combination Sum-II:
	 * Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates
	 * where the candidate numbers sum to target. Each number in candidates may only be used once in the combination.
	 * The solution set must not contain duplicate combinations.
	 */
	public List<List<Integer>> combinationSum2(int[] nums, int target) {
		List<List<Integer>> res = new ArrayList<>();
		Arrays.sort(nums);
		backtrack5(res, new ArrayList<>(), nums, target, 0);
		res.stream().forEach(k -> System.out.print(k + ", "));
		return res;
	}

	private void backtrack5(List<List<Integer>> res, List<Integer> buffer, int[] nums, int target, int startIndex) {
		if (target < 0) return;
		else if (target == 0) res.add(new ArrayList<>(buffer));
		else {
			for (int i = startIndex; i < nums.length; i++) {
				if (i > startIndex && nums[i] == nums[i - 1]) continue;
				buffer.add(nums[i]);
				backtrack5(res, buffer, nums, target - nums[i], i + 1); // here using 'i+1' to not to repeat same number
				buffer.remove(buffer.size() - 1);
			}
		}
	}

	/* Combination Sum-III
	 */
	public List<List<Integer>> combinationSum3(int n, int k) {
		List<List<Integer>> res = new ArrayList<>();
		backtrack6(n, k, 1, new ArrayList<>(), res);
		res.stream().forEach(data -> System.out.print(data + ", "));
		return res;
	}

	public void backtrack6(int sum, int k, int startVal, List<Integer> buffer, List<List<Integer>> res) {
		if (buffer.size() == k && sum == 0) res.add(new ArrayList<>(buffer));
		else if (buffer.size() >= k || sum < 0) return;
		else {
			for (int i = startVal; i <= 9; i++) {
				buffer.add(i);
				backtrack6(sum - i, k, i + 1, buffer, res);
				buffer.remove(buffer.size() - 1);
			}
		}
	}

	/*Permutations I- Time Complexity: O(n!)
	 */
	// Approach1: Time Complexity: O(n!)
	public List<List<Integer>> permute1(int[] nums) {
		List<List<Integer>> res = new ArrayList<>();
		backtrack8(res, new ArrayList<>(), nums);
		res.forEach(k -> System.out.print(k + ", "));
		return res;
	}

	private void backtrack8(List<List<Integer>> res, List<Integer> buffer, int[] nums) {
		if (buffer.size() == nums.length) {
			res.add(new ArrayList<>(buffer));
		} else {
			for (int i = 0; i < nums.length; i++) {
				if (buffer.contains(nums[i])) continue;
				buffer.add(nums[i]);
				backtrack8(res, buffer, nums);
				buffer.remove(buffer.size() - 1);
			}
		}
	}

	// Approach2:
	public List<List<Integer>> permute2(int[] nums) {
		List<List<Integer>> res = new ArrayList<>();
		backtrack9(nums, 0, res);
		res.forEach(k -> System.out.print(k + ", "));
		return res;
	}

	private void backtrack9(int[] nums, int s, List<List<Integer>> res) {
		if (s >= nums.length) {
			List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
			res.add(list);
		} else {
			for (int i = s; i < nums.length; i++) {
				Utils.swap(nums, s, i); // Arrange
				backtrack9(nums, s + 1, res);
				Utils.swap(nums, s, i); // Rearrange to original
			}
		}
	}

	/* Permutations II (contains duplicates) :
	 */
	// Approach-1
	public List<List<Integer>> permuteUnique1(int[] nums) {
		List<List<Integer>> list = new ArrayList<>();
		Arrays.sort(nums);
		backtrack10(list, new ArrayList<>(), nums, new boolean[nums.length]);
		list.forEach(val -> System.out.print(val + ", "));
		return list;
	}

	private void backtrack10(List<List<Integer>> list, List<Integer> buffer, int[] nums, boolean[] used) {
		if (buffer.size() == nums.length) {
			list.add(new ArrayList<>(buffer));
		} else {
			for (int i = 0; i < nums.length; i++) {
				if (used[i] || i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
				used[i] = true;
				buffer.add(nums[i]);
				backtrack10(list, buffer, nums, used);
				used[i] = false;
				buffer.remove(buffer.size() - 1);
			}
		}
	}

	// Approach-2
	public List<List<Integer>> permuteUnique2(int[] nums) {
		List<List<Integer>> res = new ArrayList<>();
		backtrack11(nums, 0, res);
		res.forEach(k -> System.out.print(k + ", "));
		return res;
	}

	public void backtrack11(int[] nums, int s, List<List<Integer>> res) {
		if (s >= nums.length) {
			List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
			res.add(list);
		} else {
			Set<Integer> appeared = new HashSet<>();
			for (int i = s; i < nums.length; i++) {
				if (appeared.add(nums[i])) {
					Utils.swap(nums, s, i);
					backtrack11(nums, s + 1, res);
					Utils.swap(nums, s, i);
				}
			}
		}
	}

	public static void main(String[] args) {
		Backtracking ob = new Backtracking();
		int[] nums = { 1, 1, 3 };
		ob.permute2(nums);
		ob.permuteUnique2(nums);
	}

}