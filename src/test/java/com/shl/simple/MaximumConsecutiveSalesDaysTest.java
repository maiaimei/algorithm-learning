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

@DisplayName("MaximumConsecutiveSalesDays 最大连续销售天数测试")
class MaximumConsecutiveSalesDaysTest {

  private String executeWithInput(String input) {
    InputStream originalIn = System.in;
    PrintStream originalOut = System.out;

    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      System.setIn(inputStream);
      System.setOut(new PrintStream(outputStream));

      MaximumConsecutiveSalesDays.Solution.main(new String[]{});

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
    @DisplayName("示例测试：3个城镇，次数分别为7,2,3")
    void testExampleCase() {
      String input = "3\n7 2 3";

      String result = executeWithInput(input);

      assertEquals("11", result);
    }

    @Test
    @DisplayName("基本测试：4个城镇，次数均衡分布")
    void testBalancedDistribution() {
      String input = "4\n3 3 3 3";

      String result = executeWithInput(input);

      assertEquals("12", result);
    }

    @Test
    @DisplayName("基本测试：5个城镇，有一个主导城镇")
    void testOneDominantTown() {
      String input = "5\n8 2 2 2 2";

      String result = executeWithInput(input);

      assertEquals("16", result);
    }

    @Test
    @DisplayName("基本测试：3个城镇，两个相同最大值")
    void testTwoMaxTowns() {
      String input = "3\n5 5 2";

      String result = executeWithInput(input);

      assertEquals("12", result);
    }

    @Test
    @DisplayName("基本测试：多个城镇，次数递减")
    void testDecreasingCounts() {
      String input = "5\n10 8 6 4 2";

      String result = executeWithInput(input);

      assertEquals("30", result);
    }

    @Test
    @DisplayName("基本测试：多个城镇，次数递增")
    void testIncreasingCounts() {
      String input = "5\n2 4 6 8 10";

      String result = executeWithInput(input);

      assertEquals("30", result);
    }
  }

  @Nested
  @DisplayName("边界条件测试")
  class BoundaryConditions {

    @Test
    @DisplayName("最小N值：只有一个城镇")
    void testSingleTown() {
      String input = "1\n5";

      String result = executeWithInput(input);

      assertEquals("1", result);
    }

    @Test
    @DisplayName("最小N值：只有一个城镇，次数为1")
    void testSingleTownMinCount() {
      String input = "1\n1";

      String result = executeWithInput(input);

      assertEquals("1", result);
    }

    @Test
    @DisplayName("两个城镇：次数相等")
    void testTwoTownsEqual() {
      String input = "2\n5 5";

      String result = executeWithInput(input);

      assertEquals("10", result);
    }

    @Test
    @DisplayName("两个城镇：次数不等")
    void testTwoTownsUnequal() {
      String input = "2\n7 3";

      String result = executeWithInput(input);

      assertEquals("7", result);
    }

    @Test
    @DisplayName("两个城镇：一个远大于另一个")
    void testTwoTownsLargeDifference() {
      String input = "2\n100 1";

      String result = executeWithInput(input);

      assertEquals("3", result);
    }

    @Test
    @DisplayName("临界情况：maxTimes = otherTimes + 1")
    void testCriticalCase1() {
      String input = "2\n6 5";

      String result = executeWithInput(input);

      assertEquals("11", result);
    }

    @Test
    @DisplayName("临界情况：maxTimes = otherTimes + 2")
    void testCriticalCase2() {
      String input = "2\n7 5";

      String result = executeWithInput(input);

      assertEquals("11", result);
    }

    @Test
    @DisplayName("临界情况：maxTimes = otherTimes")
    void testCriticalCase3() {
      String input = "2\n5 5";

      String result = executeWithInput(input);

      assertEquals("10", result);
    }

    @Test
    @DisplayName("大量城镇：N=50000，每个城镇次数为2")
    void testManyTownsSmallCounts() {
      int n = 50000;
      StringBuilder input = new StringBuilder();
      input.append(n).append("\n");
      for (int i = 0; i < n; i++) {
        if (i > 0) {
          input.append(" ");
        }
        input.append(2);
      }

      long startTime = System.currentTimeMillis();
      String result = executeWithInput(input.toString());
      long endTime = System.currentTimeMillis();

      assertTrue(endTime - startTime < 5000, "Execution time should be less than 5 seconds");
      assertEquals("100000", result);
    }

    @Test
    @DisplayName("总次数达到上限：Σcᵢ = 100000")
    void testTotalCountAtLimit() {
      String input = "2\n50000 50000";

      String result = executeWithInput(input);

      assertEquals("100000", result);
    }
  }

  @Nested
  @DisplayName("特殊输入场景测试")
  class SpecialCases {

    @Test
    @DisplayName("所有城镇次数相同：均匀分布")
    void testAllSameCounts() {
      String input = "5\n4 4 4 4 4";

      String result = executeWithInput(input);

      assertEquals("20", result);
    }

    @Test
    @DisplayName("一个城镇占绝对优势")
    void testExtremeDominance() {
      String input = "3\n50 1 1";

      String result = executeWithInput(input);

      assertEquals("5", result);
    }

    @Test
    @DisplayName("多个城镇次数为1")
    void testMultipleOnes() {
      String input = "10\n1 1 1 1 1 1 1 1 1 1";

      String result = executeWithInput(input);

      assertEquals("10", result);
    }

    @Test
    @DisplayName("混合场景：有零散分布的城镇")
    void testMixedScenario() {
      String input = "6\n15 3 7 2 9 4";

      String result = executeWithInput(input);

      assertEquals("40", result);
    }

    @Test
    @DisplayName("刚好可以完全交错：maxTimes = sum/2 + 0.5")
    void testPerfectInterleaving() {
      String input = "2\n5 4";

      String result = executeWithInput(input);

      assertEquals("9", result);
    }

    @Test
    @DisplayName("无法完全交错：需要限制天数")
    void testImperfectInterleaving() {
      String input = "2\n10 3";

      String result = executeWithInput(input);

      assertEquals("7", result);
    }

    @Test
    @DisplayName("三个城镇：两个小值一个大值")
    void testThreeTownsOneLarge() {
      String input = "3\n20 2 3";

      String result = executeWithInput(input);

      assertEquals("11", result);
    }

    @Test
    @DisplayName("四个城镇：等差数列")
    void testArithmeticSequence() {
      String input = "4\n2 4 6 8";

      String result = executeWithInput(input);

      assertEquals("20", result);
    }
  }

  @Nested
  @DisplayName("参数化测试")
  class ParameterizedTests {

    private static Stream<Arguments> provideTestCases() {
      return Stream.of(
          Arguments.of("3\n7 2 3", "11"),
          Arguments.of("1\n5", "1"),
          Arguments.of("2\n5 5", "10"),
          Arguments.of("2\n7 3", "7"),
          Arguments.of("4\n3 3 3 3", "12"),
          Arguments.of("5\n8 2 2 2 2", "16"),
          Arguments.of("3\n50 1 1", "5"),
          Arguments.of("2\n100 1", "3"),
          Arguments.of("2\n6 5", "11"),
          Arguments.of("2\n7 5", "11")
      );
    }

    @ParameterizedTest(name = "{index}: 输入={0}, 期望输出={1}")
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
      String input = "0\n";

      assertThrows(IllegalArgumentException.class, () -> {
        executeWithInput(input);
      });
    }

    @Test
    @DisplayName("N不在有效范围内：N=50001")
    void testInvalidN_TooLarge() {
      String input = "50001\n1";

      assertThrows(IllegalArgumentException.class, () -> {
        executeWithInput(input);
      });
    }

    @Test
    @DisplayName("城镇次数为负数")
    void testNegativeCount() {
      String input = "3\n5 -1 3";

      assertThrows(IllegalArgumentException.class, () -> {
        executeWithInput(input);
      });
    }

    @Test
    @DisplayName("总次数超过上限：Σcᵢ > 100000")
    void testTotalCountExceedsLimit() {
      String input = "2\n60000 50000";

      assertThrows(IllegalArgumentException.class, () -> {
        executeWithInput(input);
      });
    }

    @Test
    @DisplayName("输入格式错误：缺少城镇数量")
    void testMissingTownCount() {
      String input = "\n5 3 2";

      assertThrows(Exception.class, () -> {
        executeWithInput(input);
      });
    }

    @Test
    @DisplayName("输入格式错误：城镇次数不足")
    void testInsufficientTownCounts() {
      String input = "3\n5 3";

      assertThrows(Exception.class, () -> {
        executeWithInput(input);
      });
    }
  }

  @Nested
  @DisplayName("格式验证测试")
  class FormatValidationTests {

    @Test
    @DisplayName("输出格式：纯数字，无多余空格")
    void testOutputFormat() {
      String input = "3\n7 2 3";

      String result = executeWithInput(input);

      assertFalse(result.startsWith(" "), "Output should not start with space");
      assertFalse(result.endsWith(" "), "Output should not end with space");
      assertFalse(result.contains("  "), "Output should not contain consecutive spaces");
    }

    @Test
    @DisplayName("输出为纯数字")
    void testOutputContainsOnlyDigits() {
      String input = "4\n3 3 3 3";

      String result = executeWithInput(input);

      assertTrue(result.matches("\\d+"), "Output should contain only digits");
    }

    @Test
    @DisplayName("输出非负整数")
    void testOutputNonNegative() {
      String input = "5\n8 2 2 2 2";

      String result = executeWithInput(input);

      long value = Long.parseLong(result);
      assertTrue(value >= 0, "Output should be non-negative");
    }
  }

  @Nested
  @DisplayName("性能测试")
  class PerformanceTests {

    @Test
    @DisplayName("大规模输入：N=50000，总次数=100000")
    void testLargeScaleInput() {
      int n = 50000;
      StringBuilder input = new StringBuilder();
      input.append(n).append("\n");
      for (int i = 0; i < n; i++) {
        if (i > 0) {
          input.append(" ");
        }
        input.append(2);
      }

      long startTime = System.currentTimeMillis();
      String result = executeWithInput(input.toString());
      long endTime = System.currentTimeMillis();

      assertTrue(endTime - startTime < 1000, "Execution time should be less than 1 second for O(1) algorithm");
      assertEquals("100000", result);
    }

    @Test
    @DisplayName("极端不平衡：一个城镇占主导")
    void testExtremeUnbalanced() {
      int n = 50000;
      StringBuilder input = new StringBuilder();
      input.append(n).append("\n");
      input.append(50001);
      for (int i = 1; i < n; i++) {
        input.append(" 1");
      }

      long startTime = System.currentTimeMillis();
      String result = executeWithInput(input.toString());
      long endTime = System.currentTimeMillis();

      assertTrue(endTime - startTime < 1000, "Execution time should be less than 1 second");
      assertEquals("99999", result);
    }
  }
}

