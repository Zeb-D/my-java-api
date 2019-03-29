package com.yd.java.algorithm;

/**
 * 假设当前 有个数组 [1,3,0,5,7,1,2,0]
 * 假设我们制定 一个数字X=1 为无效数字，现在提供一个算法进行整理出：3，0，5，7，2，0，1，1
 * <p>
 * 大概是将无效的数字放到最后面，前面都按照原来有序向前迁移
 * </p>
 *
 * @author Yd on 2019-03-22
 * @description 标记整理
 */
public class SignSort {

    //最慢的标记往前移
    public static void slowlyComplete(int a[], int flag) {
        long currentTime = System.nanoTime();
        for (int i = 0; i < a.length; i++) {
            if (a[i] == flag) {
                int index = i;
                for (int j = i + 1; j < a.length; j++) {
                    if (a[j] != flag) {
                        index = j;
                        break;
                    }
                }
                if (index != i) {
                    //index与i 进行交换
                    int temp = a[i];
                    a[i] = a[index];
                    a[index] = temp;
                }
            }
        }
        System.out.println("\n slowly complete cost time: " + (System.nanoTime() - currentTime));
    }

    public static void bitComplete(int[] a, int flag) {
        long currentTime = System.nanoTime();
        long bit = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == flag) {
                bit = bit + (1 << i);
                System.out.print(i);
            }
        }
        System.out.println("\nbit-->" + bit);
        int start = 0;
        for (int i = 0; i < a.length; i++) {
            if (((1 << i) & bit) != (1 << i)) {
                a[start] = a[i];
                start++;
            }
        }
        for (int i = start; i < a.length; i++) {
            a[i] = flag;
        }
        System.out.println("\n bit complete cost time: " + (System.nanoTime() - currentTime));
    }

    public static void main(String[] args) {
        int[] a = {1, 1, 0, 5, 7, 1, 2, 0, 2, 3, 45, 1, 0, 2, 1, 1};
        slowlyComplete(a, 1);
        log(a);
        bitComplete(a, 0);
        log(a);
    }

    public static void log(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
        }
        System.out.println();
    }

    public void slectSort(int[] a) {
        int preIdx, current;
        for (int i = 1; i < a.length; i++) {
            preIdx = i - 1;
            current = a[i];
            while (preIdx >= 0 && a[preIdx] > current) {

            }

        }

    }

}
