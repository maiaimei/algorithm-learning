package com.shl.simple;

import java.util.Scanner;

public class CountElementsLessThanK {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int length = sc.nextInt();
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = sc.nextInt();
        }
        int k = sc.nextInt();
        sc.close();
        int count = 0;
        for (int i = 0; i < length; i++) {
            if (arr[i] < k) {
                count++;
            }
        }
        System.out.println(count);
    }
}
