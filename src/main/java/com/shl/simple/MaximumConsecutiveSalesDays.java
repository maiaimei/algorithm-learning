package com.shl.simple;

import java.util.Scanner;

public class MaximumConsecutiveSalesDays {

  public static class Solution {

    /**
     * Main method to read input, validate parameters, and calculate the maximum trading days.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

      try (Scanner scanner = new Scanner(System.in)) {
        // Read and validate number of towns
        if (!scanner.hasNextInt()) {
          throw new IllegalArgumentException("Invalid input for number of towns.");
        }
        int townCount = scanner.nextInt();
        // Validate the constraint of town count (1 ≤ N ≤ 5×10⁴)
        if (townCount < 1 || townCount > 50000) {
          throw new IllegalArgumentException("Invalid input for number of towns. Must be between 1 and 50000.");
        }

        // Calculate total selection times and find maximum single town times
        long totalSelectTimes = 0;
        int maxSingleTownTimes = 0;

        for (int i = 0; i < townCount; i++) {
          // Read and validate selection times for each town
          if (!scanner.hasNextInt()) {
            throw new IllegalArgumentException("Invalid input for selection times of town " + (i + 1));
          }
          int selectTimes = scanner.nextInt();
          if (selectTimes < 0) {
            throw new IllegalArgumentException("Invalid input for selection times of town " + (i + 1) + ". Must be non-negative.");
          }

          totalSelectTimes += selectTimes;
          if (selectTimes > maxSingleTownTimes) {
            maxSingleTownTimes = selectTimes;
          }
        }

        // Validate total selection times
        if (totalSelectTimes > 100000) {
          throw new IllegalArgumentException("Total selection times exceed the maximum allowed (100000).");
        }

        // Calculate the maximum trading days based on the core logic
        long maxTradingDays = calculateMaximumConsecutiveSalesDays(maxSingleTownTimes, totalSelectTimes);

        // Output the result
        System.out.println(maxTradingDays);
      }
    }

    /**
     * Calculate the maximum number of consecutive sales days based on greedy strategy.
     * <p>
     * Design thinking:
     * - We want to maximize consecutive sales days by avoiding consecutive visits to same town
     * - If the most frequent town can be fully interleaved with others, we can use all selections
     * - Otherwise, we're limited by the gap-filling constraint
     * <p>
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     *
     * @param maxSingleTownTimes the maximum selection times of any single town
     * @param totalSelectTimes   the sum of all towns' maximum selection times
     * @return the maximum number of trading days
     */
    private static long calculateMaximumConsecutiveSalesDays(int maxSingleTownTimes, long totalSelectTimes) {
      // Greedy strategy: Check if maximum town can be fully interleaved
      // Condition: maxTimes <= (totalTimes - maxTimes) + 1
      // This means: the most frequent town's visits can be separated by other towns
      if (maxSingleTownTimes <= totalSelectTimes - maxSingleTownTimes + 1) {
        return totalSelectTimes;
      } else {
        // When one town dominates, we can only alternate between it and others
        // Maximum days = 2 * (other towns' times) + 1 (the dominant town in the middle)
        return 2 * (totalSelectTimes - maxSingleTownTimes) + 1;
      }
    }

  }
}
