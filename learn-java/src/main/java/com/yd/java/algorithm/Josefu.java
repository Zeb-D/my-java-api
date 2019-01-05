package com.yd.java.algorithm;

import java.util.Arrays;

//约瑟夫环问题M个人、N报数
public class Josefu {

	public static void josefu(int M,int N){//M个人，报N出列
		int sum=0;
		for(int i=2;i<=N;i++) sum=(sum+M)%i;
		System.out.println(sum+1);
	}
	
	public static void main(String[] args) {
		int M = 3, N = 3;
		int[] a = new int[M];
		for (int i = 0; i < M; i++)
			a[i] = i + 1;
		System.out.println(Arrays.toString(a));
		int sum = 0, index = 0;
		for (int i = 0; sum < M - 1;) {
			if (a[i] == -1) {
				if (i == M - 1) {
					i = 0;
				} else {
					i++;
				}
				continue;
			} else {
				index++;
				if (index % N == 0) {
					sum++;
					a[i] = -1;
					index = 0;
				}
			}
			if (i == M - 1) {
				i = 0;
			} else {
				i++;
			}
		}

		System.out.println(Arrays.toString(a));
		for (int i = 0; i < M; i++)
			if (a[i] != -1)
				System.out.println(a[i]);
		
		josefu(4,3);
	}
}
