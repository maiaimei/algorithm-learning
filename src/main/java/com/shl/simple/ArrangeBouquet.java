package com.shl.simple;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class represents a solution to the "Bouquet" problem.
 */
public class ArrangeBouquet {

  public static class Solution {

    public static void main(String[] args) {
      try (Scanner scanner = new Scanner(System.in)) {
        // Read the number of flower sticks (N) from input and validate it
        if (!scanner.hasNextInt()) {
          throw new IllegalArgumentException("The number of N must be an integer.");
        }
        int n = scanner.nextInt();
        if (n < 1 || n >= 1000000) {
          throw new IllegalArgumentException("The number of N must be between 1 and 1000000.");
        }

        // Read the number of flowers (K) from input and validate it
        if (!scanner.hasNextInt()) {
          throw new IllegalArgumentException("The number K must be an integer.");
        }
        int k = scanner.nextInt();
        if (k < 1 || k >= n) {
          throw new IllegalArgumentException("The number K must be between 1 and N.");
        }

        // Read the lengths of the flower sticks and validate them
        int[] sticks = new int[n];
        for (int i = 0; i < n; i++) {
          if (!scanner.hasNextInt()) {
            throw new IllegalArgumentException("The length of each flower stick must be an integer.");
          }
          sticks[i] = scanner.nextInt();
          if (sticks[i] < 1) {
            throw new IllegalArgumentException("The length of each flower stick must be positive.");
          }
        }

        // Sort the first K sticks in ascending order and the remaining N-K sticks in descending order
        Arrays.sort(sticks, 0, k);
        Arrays.sort(sticks, k, n);
        reverse(sticks, k, n - 1);

        // Print the sorted flower stick lengths
        System.out.println(Arrays.stream(sticks).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
      }
    }

    /**
     * Reverses the elements of an array between the specified indices.
     *
     * @param arr       the array to be reversed
     * @param fromIndex the index of the first element to be reversed
     * @param toIndex   the index of the last element to be reversed
     */
    private static void reverse(int[] arr, int fromIndex, int toIndex) {
      while (fromIndex < toIndex) {
        int temp = arr[fromIndex];
        arr[fromIndex] = arr[toIndex];
        arr[toIndex] = temp;
        fromIndex++;
        toIndex--;
      }
    }
  }
}
