package com.shl.simple;

import java.util.Scanner;

public class RemovingAllVowels {

  public static class Solution {

    // Optimization: Use a static boolean array for O(1) vowel lookup, reducing both time and space complexity compared to HashSet
    private static final boolean[] IS_VOWEL = new boolean[128];

    static {
      // Mark all vowels in the ASCII table
      IS_VOWEL['a'] = true;
      IS_VOWEL['e'] = true;
      IS_VOWEL['i'] = true;
      IS_VOWEL['o'] = true;
      IS_VOWEL['u'] = true;
      IS_VOWEL['A'] = true;
      IS_VOWEL['E'] = true;
      IS_VOWEL['I'] = true;
      IS_VOWEL['O'] = true;
      IS_VOWEL['U'] = true;
    }

    public static void main(String[] args) {
      try (Scanner scanner = new Scanner(System.in)) {
        if (!scanner.hasNextLine()) {
          throw new IllegalArgumentException("Input string is required.");
        }
        String inputString = scanner.nextLine();
        String resultString = removeVowels(inputString);
        System.out.println(resultString);
      }
    }

    /**
     * Removes all vowels from the input string.
     * Optimization: Uses a static boolean array for O(1) vowel check instead of HashSet.
     * Time Complexity: O(n), where n is the length of the input string.
     * Space Complexity: O(1), as the auxiliary space does not grow with input size.
     *
     * @param input The input string containing only English letters.
     * @return The string after removing all vowels.
     */
    private static String removeVowels(String input) {
      if (input == null || input.isEmpty()) {
        // Handle null or empty input
        return "";
      }
      StringBuilder resultBuilder = new StringBuilder(input.length());
      for (int i = 0; i < input.length(); i++) {
        char currentChar = input.charAt(i);
        // O(1) check for vowel
        if (currentChar < 128 && !IS_VOWEL[currentChar]) {
          resultBuilder.append(currentChar);
        }
      }
      return resultBuilder.toString();
    }
  }
}