package com.yd.java.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * 求一个自数数 由多个质数相乘 的集合
 * @author Yd on 2018-06-24
 */
public class MutilPrime {
	public static boolean isPrime(int n) {
		if (n < 2)
			return false;
		if (n == 2)
			return true;
		for (int i = 2; i <= Math.sqrt(n); i++)
			if (n % i == 0)
				return false;
		return true;
	}

	public static List<Integer> primeList(int n) {
		List<Integer> result = new ArrayList<>();
		for (int i = 2; i <= n; i++)
			if (isPrime(i))
				result.add(i);
		return result;
	}

	// 递归求 N内的质数之乘 ，40=2*2*2*5
	public static void recusion(int a, List<Integer> temp, Set<List<Integer>> resultSet, List<Integer> result) {
		for (int i = 0; i < temp.size(); i++) {
			List<Integer> resultClone = new ArrayList<>(result);
			if (a / temp.get(i) == 1 && a % temp.get(i) == 0) {
				resultClone.add(temp.get(i));
				Collections.sort(resultClone);//先排序 去重
				resultSet.add(resultClone);
//				System.out.println(resultClone);
			} else if (a / temp.get(i) > 1 && a % temp.get(i) == 0) {
				resultClone.add(temp.get(i));
				recusion(a / temp.get(i), temp, resultSet, resultClone);
			}
		}
	}

	public static Set<List<Integer>> add(List<Integer> list, Set<List<Integer>> resultSet) {
		resultSet.add(list);
		return resultSet;
	}

	public static void main(String[] args) {
		int m = 40;
		System.out.println(isPrime(25));
		System.out.println(primeList(m));

		List<Integer> primeList = primeList(m);
		List<Integer> result = new ArrayList<Integer>();
		Set<List<Integer>> resultSet = new HashSet<List<Integer>>();
		recusion(m, primeList, resultSet, result);
		System.out.println(resultSet);

	}

}
