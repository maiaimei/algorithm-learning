package com.shl.medium;

import java.util.Scanner;

/**
 * 帮助 Bob 加密秘密代码
 * <p>
 * 公式： ((((S^N % 10)^M) % 1000000007))
 */
public class BobSecretCodeEncryption {

    private static final int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) {
            System.out.println(0);
            scanner.close();
            return;
        }
        int secretCode = scanner.nextInt();
        if (!scanner.hasNextInt()) {
            System.out.println(0);
            scanner.close();
            return;
        }
        int firstKey = scanner.nextInt();
        if (!scanner.hasNextInt()) {
            System.out.println(0);
            scanner.close();
            return;
        }
        int secondKey = scanner.nextInt();
        scanner.close();

        long result = encryptSecretCode(secretCode, firstKey, secondKey);
        System.out.println(result);
    }

    /**
     * 加密秘密代码的方法
     * 公式：((((S^N %10)^M)%1000000007)
     *
     * @param secretCode 秘密代码 S
     * @param firstKey   第一个键值 N
     * @param secondKey  第二个键值 M
     * @return 加密后的代码
     */
    public static long encryptSecretCode(int secretCode, int firstKey, int secondKey) {
        // 计算 S^N % 10，由于只取最后一位，利用快速幂或直接循环（因为模10后数很小）
        int base = secretCode % 10; // S的最后一位
        int firstStep = modPow(base, firstKey, 10); // 计算 (S%10)^N %10

        // 计算 (firstStep)^M % MOD
        long secondStep = modPow(firstStep, secondKey, MOD);

        return secondStep;
    }

    /**
     * 快速幂算法，计算 (base^exponent) % mod
     *
     * @param base     底数
     * @param exponent 指数
     * @param mod      模数
     * @return (base^exponent) % mod 的结果
     */
    public static int modPow(int base, int exponent, int mod) {
        if (mod == 1) {
            return 0;
        }
        long result = 1;
        long currentBase = base % mod;
        while (exponent > 0) {
            if ((exponent & 1) == 1) { // 指数为奇数
                result = (result * currentBase) % mod;
            }
            currentBase = (currentBase * currentBase) % mod; // 底数平方
            exponent >>= 1; // 指数右移一位（除以2）
        }
        return (int) result;
    }
}