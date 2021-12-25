package com.yd.java.algorithm;

/**
 * 如果两个正整数，除了1以外，没有其他公因子，我们就称这两个数是互质关系（coprime）。
 * 比如，15和32没有公因子，所以它们是互质关系。这说明，不是质数也可以构成互质关系。
 * <p>
 * <p>
 * 　1. 任意两个质数构成互质关系，比如13和61。
 * <p>
 * 　　2. 一个数是质数，另一个数只要不是前者的倍数，两者就构成互质关系，比如3和10。
 * <p>
 * 　　3. 如果两个数之中，较大的那个数是质数，则两者构成互质关系，比如97和57。
 * <p>
 * 　　4. 1和任意一个自然数是都是互质关系，比如1和99。
 * <p>
 * 　　5. p是大于1的整数，则p和p-1构成互质关系，比如57和56。
 * <p>
 * 　　6. p是大于1的奇数，则p和p-2构成互质关系，比如17和15。
 *
 * @author created by Zeb灬D on 2020-03-23 19:45
 */
public class Coprime {
    public static void main(String[] args) {
        System.out.println(coprime(16));
    }

    public static int coprime(int n) {
        // 1、如果n=1，则 φ(1) = 1 。因为1与任何数（包括自身）都构成互质关系。
        if (n == 1) {
            return 1;
        }
        // 2、如果n是质数，则 φ(n)=n-1 。因为质数与小于它的每一个数，都构成互质关系。
        // 比如5与1、2、3、4都构成互质关系。
        if (isPrime(n)) {
            return n - 1;
        }
        // 3、如果n是质数的某一个次方，即 n = p^k (p为质数，k为大于等于1的整数)，则
        // 比如 φ(8) = φ(2^3) =2^3 - 2^2 = 8 -4 = 4。
        int sqrts = 0;
        int res = n;
        for (int i = 2; i <= Math.sqrt(n) && isPrime(i); ) {
            if (res % i == 0) {
                sqrts++;
                res = res / i;
                if (res <= i) {
                    break;
                }
            } else {
                sqrts = 0;
                i++;
            }
        }
        if (sqrts != 0) {
            return (int) (Math.pow(res, sqrts+1) - Math.pow(res, sqrts));
        }

        return -1;
    }

    // 是否为质数
    public static boolean isPrime(int prime) {
        if (bloomFilter.contains(String.valueOf(prime))) {
            return true;
        } else if (MutilPrime.isPrime(prime)) {
            bloomFilter.add(String.valueOf(prime));
            return true;
        }
        return false;
    }

    private static BloomFilter bloomFilter = new BloomFilter();

}
