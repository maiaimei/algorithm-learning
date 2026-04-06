package com.shl.simple;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Bouquet 花束排列测试")
class ArrangeBouquetTest {

  private String executeWithInput(String input) {
    InputStream originalIn = System.in;
    PrintStream originalOut = System.out;

    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      System.setIn(inputStream);
      System.setOut(new PrintStream(outputStream));

      ArrangeBouquet.Solution.main(new String[]{});

      return outputStream.toString().trim();
    } finally {
      System.setIn(originalIn);
      System.setOut(originalOut);
    }
  }

  @Nested
  @DisplayName("常规场景测试")
  class NormalScenarios {

    @Test
    @DisplayName("示例测试：8根花枝，前3根递增，后5根递减")
    void testExampleCase() {
      String input = "8 3\n11 7 5 10 46 23 16 8";

      String result = executeWithInput(input);

      assertEquals("5 7 11 46 23 16 10 8", result);
    }

    @Test
    @DisplayName("基本测试：6根花枝，前2根递增，后4根递减")
    void testBasicCase1() {
      String input = "6 2\n9 3 7 1 5 8";

      String result = executeWithInput(input);

      assertEquals("3 9 8 7 5 1", result);
    }

    @Test
    @DisplayName("基本测试：5根花枝，前3根递增，后2根递减")
    void testBasicCase2() {
      String input = "5 3\n4 2 8 6 1";

      String result = executeWithInput(input);

      assertEquals("2 4 8 6 1", result);
    }

    @Test
    @DisplayName("所有元素相同：5根花枝长度都为10")
    void testAllSameLengths() {
      String input = "5 2\n10 10 10 10 10";

      String result = executeWithInput(input);

      assertEquals("10 10 10 10 10", result);
    }

    @Test
    @DisplayName("已排序情况：前K个已递增，后N-K个已递减")
    void testAlreadySorted() {
      String input = "6 3\n1 2 3 9 7 5";

      String result = executeWithInput(input);

      assertEquals("1 2 3 9 7 5", result);
    }

    @Test
    @DisplayName("逆序情况：前K个递减，后N-K个递增")
    void testReverseOrder() {
      String input = "6 3\n5 4 3 1 2 6";

      String result = executeWithInput(input);

      assertEquals("3 4 5 6 2 1", result);
    }
  }

  @Nested
  @DisplayName("边界条件测试")
  class BoundaryConditions {

    @Test
    @DisplayName("最小N值：N=2, K=1")
    void testMinimumN() {
      String input = "2 1\n5 3";

      String result = executeWithInput(input);

      assertEquals("5 3", result);
    }

    @Test
    @DisplayName("K=1：只有第一根单独处理")
    void testKEqualsOne() {
      String input = "5 1\n7 3 9 1 5";

      String result = executeWithInput(input);

      assertEquals("7 9 5 3 1", result);
    }

    @Test
    @DisplayName("K=N-1：几乎全部递增排序")
    void testKNMinusOne() {
      String input = "5 4\n8 3 5 1 9";

      String result = executeWithInput(input);

      assertEquals("1 3 5 8 9", result);
    }

    @Test
    @DisplayName("较大数组测试：N=1000")
    void testMediumArray() {
      int n = 1000;
      int k = 500;
      StringBuilder input = new StringBuilder();
      input.append(n).append(" ").append(k).append("\n");
      for (int i = 0; i < n; i++) {
        if (i > 0) {
          input.append(" ");
        }
        input.append(n - i);
      }

      long startTime = System.currentTimeMillis();
      String result = executeWithInput(input.toString());
      long endTime = System.currentTimeMillis();

      assertTrue(endTime - startTime < 5000, "Execution time should be less than 5 seconds");

      String[] parts = result.split(" ");
      assertEquals(n, parts.length);

      for (int i = 0; i < k - 1; i++) {
        assertTrue(Integer.parseInt(parts[i]) <= Integer.parseInt(parts[i + 1]),
            "First K elements should be in ascending order");
      }
      for (int i = k; i < n - 1; i++) {
        assertTrue(Integer.parseInt(parts[i]) >= Integer.parseInt(parts[i + 1]),
            "Remaining elements should be in descending order");
      }
    }
  }

  @Nested
  @DisplayName("特殊输入场景测试")
  class SpecialCases {

    @Test
    @DisplayName("包含最大值：花枝长度为1000000")
    void testWithMaxValue() {
      String input = "3 2\n1000000 1 500000";

      String result = executeWithInput(input);

      assertEquals("1 1000000 500000", result);
    }

    @Test
    @DisplayName("包含最小值：花枝长度为1")
    void testWithMinValue() {
      String input = "4 2\n1 100 50 200";

      String result = executeWithInput(input);

      assertEquals("1 100 200 50", result);
    }

    @Test
    @DisplayName("随机顺序：无明显规律的输入")
    void testRandomOrder() {
      String input = "7 4\n42 17 93 8 56 31 74";

      String result = executeWithInput(input);

      assertEquals("8 17 42 93 74 56 31", result);
    }

    @Test
    @DisplayName("两段各自已排序但整体未排序")
    void testTwoSortedSegments() {
      String input = "7 4\n1 3 5 10 8 6 4";

      String result = executeWithInput(input);

      assertEquals("1 3 5 10 8 6 4", result);
    }

    @Test
    @DisplayName("只有两个元素")
    void testOnlyTwoElements() {
      String input = "2 1\n10 20";

      String result = executeWithInput(input);

      assertEquals("10 20", result);
    }
  }

  @Nested
  @DisplayName("参数化测试")
  class ParameterizedTests {

    private static Stream<Arguments> provideTestCases() {
      return Stream.of(
          Arguments.of("8 3\n11 7 5 10 46 23 16 8", "5 7 11 46 23 16 10 8"),
          Arguments.of("5 2\n1 2 3 4 5", "1 2 5 4 3"),
          Arguments.of("5 3\n5 4 3 2 1", "3 4 5 2 1"),
          Arguments.of("2 1\n10 20", "10 20"),
          Arguments.of("4 2\n100 50 25 75", "50 100 75 25"),
          Arguments.of("6 3\n15 3 22 8 11 6", "3 15 22 11 8 6")
      );
    }

    @ParameterizedTest(name = "{index}: {0} -> {1}")
    @MethodSource("provideTestCases")
    @DisplayName("多组数据验证")
    void testMultipleCases(String input, String expected) {
      String result = executeWithInput(input);
      assertEquals(expected, result);
    }
  }

  @Nested
  @DisplayName("异常输入测试")
  class InvalidInputTests {

    @Test
    @DisplayName("N不在有效范围内：N=0")
    void testInvalidN_Zero() {
      String input = "0 0\n";

      assertThrows(IllegalArgumentException.class, () -> {
        executeWithInput(input);
      });
    }

    @Test
    @DisplayName("N不在有效范围内：N=1000000")
    void testInvalidN_TooLarge() {
      String input = "1000000 1\n1";

      assertThrows(IllegalArgumentException.class, () -> {
        executeWithInput(input);
      });
    }

    @Test
    @DisplayName("K不在有效范围内：K=0")
    void testInvalidK_Zero() {
      String input = "5 0\n1 2 3 4 5";

      assertThrows(IllegalArgumentException.class, () -> {
        executeWithInput(input);
      });
    }

    @Test
    @DisplayName("K不在有效范围内：K>=N")
    void testInvalidK_EqualsN() {
      String input = "5 5\n1 2 3 4 5";

      assertThrows(IllegalArgumentException.class, () -> {
        executeWithInput(input);
      });
    }

    @Test
    @DisplayName("花枝长度为负数")
    void testNegativeStickLength() {
      String input = "3 2\n5 -1 3";

      assertThrows(IllegalArgumentException.class, () -> {
        executeWithInput(input);
      });
    }

    @Test
    @DisplayName("花枝长度为零")
    void testZeroStickLength() {
      String input = "3 2\n5 0 3";

      assertThrows(IllegalArgumentException.class, () -> {
        executeWithInput(input);
      });
    }
  }

  @Nested
  @DisplayName("格式验证测试")
  class FormatValidationTests {

    @Test
    @DisplayName("输出格式：空格分隔，无多余空格")
    void testOutputFormat() {
      String input = "5 2\n3 1 4 5 2";

      String result = executeWithInput(input);

      assertFalse(result.startsWith(" "), "Output should not start with space");
      assertFalse(result.endsWith(" "), "Output should not end with space");
      assertFalse(result.contains("  "), "Output should not contain consecutive spaces");
    }

    @Test
    @DisplayName("输出元素数量正确")
    void testOutputElementCount() {
      String input = "10 4\n9 2 7 4 6 1 8 3 5 10";

      String result = executeWithInput(input);

      String[] elements = result.split(" ");
      assertEquals(10, elements.length, "Output should contain exactly N elements");
    }

    @Test
    @DisplayName("输出为纯数字和空格")
    void testOutputContainsOnlyDigitsAndSpaces() {
      String input = "4 2\n8 3 6 1";

      String result = executeWithInput(input);

      assertTrue(result.matches("[0-9 ]+"), "Output should contain only digits and spaces");
    }
  }
}
