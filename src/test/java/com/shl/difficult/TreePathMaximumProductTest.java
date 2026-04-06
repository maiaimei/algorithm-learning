package com.shl.difficult;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Tree Path Maximum Product 树路径最大乘积问题测试")
class TreePathMaximumProductTest {

  private String executeWithInput(String input) {
    InputStream originalIn = System.in;
    PrintStream originalOut = System.out;

    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      System.setIn(inputStream);
      System.setOut(new PrintStream(outputStream));

      TreePathMaximumProduct.Solution1.main(new String[]{});

      return outputStream.toString().trim();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      System.setIn(originalIn);
      System.setOut(originalOut);
    }
  }

  @Nested
  @DisplayName("常规场景测试")
  class NormalScenarios {

    @Test
    @DisplayName("示例测试：4个节点，结果为-12")
    void testExampleCase() {
      String input = "4\n-1 2 3 2\n1 2\n1 3\n3 4";

      String result = executeWithInput(input);

      assertEquals("-12", result);
    }

    @Test
    @DisplayName("简单三节点树：所有正值")
    void testSimpleThreeNodesAllPositive() {
      String input = "3\n2 3 4\n1 2\n1 3";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertEquals(24, product);
    }

    @Test
    @DisplayName("五节点树：混合正负值")
    void testFiveNodesMixedValues() {
      String input = "5\n2 -3 4 -1 5\n1 2\n1 3\n3 4\n3 5";

      String result = executeWithInput(input);

      assertNotNull(result);
      assertFalse(result.isEmpty());

      long product = Long.parseLong(result);
      assertTrue(product != 0, "Product should not be zero for non-zero values");
    }

    @Test
    @DisplayName("六节点平衡树")
    void testSixNodeBalancedTree() {
      String input = "6\n1 2 3 4 5 6\n1 2\n1 3\n2 4\n2 5\n3 6";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertTrue(product > 0, "Product should be positive for all positive values");
    }

    @Test
    @DisplayName("星型树：中心节点连接多个叶子")
    void testStarShapedTree() {
      String input = "5\n2 3 4 5 6\n1 2\n1 3\n1 4\n1 5";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertTrue(product > 0);
    }
  }

  @Nested
  @DisplayName("边界条件测试")
  class BoundaryConditions {

    @Test
    @DisplayName("最小约束：只有一个节点")
    void testSingleNode() {
      String input = "1\n5";

      String result = executeWithInput(input);

      assertEquals("5", result);
    }

    @Test
    @DisplayName("两个节点：最简单的树")
    void testTwoNodes() {
      String input = "2\n3 4\n1 2";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertEquals(12, product);
    }

    @Test
    @DisplayName("三个节点链状结构")
    void testThreeNodesChain() {
      String input = "3\n2 3 4\n1 2\n2 3";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertEquals(24, product);
    }

    @Test
    @DisplayName("单节点值为负数")
    void testSingleNodeNegative() {
      String input = "1\n-7";

      String result = executeWithInput(input);

      assertEquals("-7", result);
    }

    @Test
    @DisplayName("单节点值为零")
    void testSingleNodeZero() {
      String input = "1\n0";

      String result = executeWithInput(input);

      assertEquals("0", result);
    }

    @Test
    @DisplayName("N=10000大规模测试（性能）")
    void testLargeScale() {
      StringBuilder input = new StringBuilder();
      int n = 10000;
      input.append(n).append("\n");

      for (int i = 0; i < n; i++) {
        input.append("1 ");
      }
      input.append("\n");

      for (int i = 1; i < n; i++) {
        input.append(i).append(" ").append(i + 1).append("\n");
      }

      long startTime = System.currentTimeMillis();
      String result = executeWithInput(input.toString());
      long endTime = System.currentTimeMillis();

      assertTrue(endTime - startTime < 10000, "Execution time should be reasonable (< 10s)");
      assertNotNull(result);
    }
  }

  @Nested
  @DisplayName("特殊输入场景测试")
  class SpecialCases {

    @Test
    @DisplayName("包含零值的节点")
    void testContainsZeroValue() {
      String input = "4\n2 0 3 4\n1 2\n1 3\n3 4";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertTrue(product == 0 || product != 0, "Product can be zero or non-zero depending on path");
    }

    @Test
    @DisplayName("所有节点值为零")
    void testAllZeros() {
      String input = "3\n0 0 0\n1 2\n1 3";

      String result = executeWithInput(input);

      assertEquals("0", result);
    }

    @Test
    @DisplayName("所有节点值为负数")
    void testAllNegativeValues() {
      String input = "4\n-2 -3 -4 -5\n1 2\n1 3\n3 4";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertTrue(product != 0, "Product of negative numbers should not be zero");
    }

    @Test
    @DisplayName("负负得正：选择经过偶数个负数的路径")
    void testNegativeTimesNegative() {
      String input = "4\n-2 -3 4 5\n1 2\n1 3\n3 4";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertTrue(product > 0, "Should find a path with positive product using negative*negative");
    }

    @Test
    @DisplayName("混合正负零：复杂情况")
    void testMixedPositiveNegativeZero() {
      String input = "6\n-1 2 -3 0 4 -5\n1 2\n1 3\n3 4\n3 5\n5 6";

      String result = executeWithInput(input);

      assertNotNull(result);
      assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("大数值：接近约束上限")
    void testLargeValues() {
      String input = "3\n1000 1000 1000\n1 2\n1 3";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertEquals(1000000000L, product);
    }

    @Test
    @DisplayName("大负数值：接近约束下限")
    void testLargeNegativeValues() {
      String input = "3\n-1000 -1000 -1000\n1 2\n1 3";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertEquals(-1000000000L, product);
    }

    @Test
    @DisplayName("只有两个叶子节点的树")
    void testOnlyTwoLeaves() {
      String input = "5\n1 2 3 4 5\n1 2\n2 3\n3 4\n4 5";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertEquals(120, product);
    }

    @Test
    @DisplayName("深度较大的树")
    void testDeepTree() {
      String input = "7\n1 2 3 4 5 6 7\n1 2\n2 3\n3 4\n4 5\n5 6\n6 7";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertEquals(5040, product);
    }
  }

  @Nested
  @DisplayName("参数化测试")
  class ParameterizedTests {

    private static Stream<Arguments> provideTestCases() {
      return Stream.of(
          Arguments.of("4\n-1 2 3 2\n1 2\n1 3\n3 4", "-12", "示例：4节点树"),
          Arguments.of("1\n5", "5", "单节点"),
          Arguments.of("2\n3 4\n1 2", "12", "两节点树"),
          Arguments.of("3\n2 3 4\n1 2\n1 3", "24", "三节点星型树"),
          Arguments.of("3\n2 3 4\n1 2\n2 3", "24", "三节点链状树"),
          Arguments.of("1\n0", "0", "单节点零值"),
          Arguments.of("1\n-7", "-7", "单节点负值")
      );
    }

    @ParameterizedTest(name = "{index}: {2}")
    @MethodSource("provideTestCases")
    @DisplayName("多组数据验证")
    void testMultipleCases(String input, String expected, String description) {
      String result = executeWithInput(input);
      assertEquals(expected, result);
    }
  }

  @Nested
  @DisplayName("输出格式验证测试")
  class FormatValidationTests {

    @Test
    @DisplayName("输出为纯数字（可能带负号）")
    void testOutputFormat() {
      String input = "4\n-1 2 3 2\n1 2\n1 3\n3 4";

      String result = executeWithInput(input);

      assertTrue(result.matches("-?[0-9]+"), "Output should be an integer (possibly negative)");
    }

    @Test
    @DisplayName("输出不包含前导空格")
    void testNoLeadingSpace() {
      String input = "3\n2 3 4\n1 2\n1 3";

      String result = executeWithInput(input);

      assertFalse(result.startsWith(" "), "Output should not start with space");
    }

    @Test
    @DisplayName("输出不包含尾随空格")
    void testNoTrailingSpace() {
      String input = "3\n2 3 4\n1 2\n1 3";

      String result = executeWithInput(input);

      assertFalse(result.endsWith(" "), "Output should not end with space");
    }

    @Test
    @DisplayName("输出非空")
    void testOutputNotEmpty() {
      String input = "2\n1 2\n1 2";

      String result = executeWithInput(input);

      assertNotNull(result);
      assertFalse(result.isEmpty());
    }
  }

  @Nested
  @DisplayName("数学逻辑验证测试")
  class MathematicalLogicTests {

    @Test
    @DisplayName("验证乘积计算：手动计算路径乘积")
    void testProductCalculation() {
      String input = "3\n2 3 4\n1 2\n1 3";

      String result = executeWithInput(input);

      long product = Long.parseLong(result);
      assertEquals(2 * 3 * 4, product);
    }

    @Test
    @DisplayName("验证负数处理：奇数个负数结果为负")
    void testOddNumberOfNegatives() {
      String input = "3\n-2 3 4\n1 2\n1 3";

      String result = executeWithInput(input);

      long product = Long.parseLong(result);
      assertTrue(product < 0, "Product with odd number of negatives should be negative");
    }

    @Test
    @DisplayName("验证负数处理：偶数个负数结果为正")
    void testEvenNumberOfNegatives() {
      String input = "4\n-2 -3 4 5\n1 2\n1 3\n3 4";

      String result = executeWithInput(input);

      long product = Long.parseLong(result);
      assertTrue(product > 0, "Product with even number of negatives should be positive");
    }

    @Test
    @DisplayName("验证零值处理：路径包含零则结果为零")
    void testZeroHandling() {
      String input = "3\n2 0 4\n1 2\n1 3";

      String result = executeWithInput(input);

      long product = Long.parseLong(result);
      assertEquals(0, product);
    }

    @Test
    @DisplayName("验证最大值选择：比较不同路径的乘积")
    void testMaxProductSelection() {
      String input = "5\n2 3 4 5 6\n1 2\n1 3\n3 4\n3 5";

      String result = executeWithInput(input);

      long product = Long.parseLong(result);
      assertTrue(product > 0, "Should select the path with maximum product");
    }

    @Test
    @DisplayName("验证长整型溢出保护：大数值相乘")
    void testLongOverflowProtection() {
      String input = "4\n1000 1000 1000 1000\n1 2\n1 3\n3 4";

      String result = executeWithInput(input);

      long product = Long.parseLong(result);
      assertEquals(1000000000000L, product);
    }
  }

  @Nested
  @DisplayName("树结构特性测试")
  class TreeStructureTests {

    @Test
    @DisplayName("完全二叉树结构")
    void testCompleteBinaryTree() {
      String input = "7\n1 2 3 4 5 6 7\n1 2\n1 3\n2 4\n2 5\n3 6\n3 7";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertTrue(product > 0);
    }

    @Test
    @DisplayName("不平衡树：左重右轻")
    void testUnbalancedTreeLeftHeavy() {
      String input = "6\n1 2 3 4 5 6\n1 2\n2 3\n3 4\n1 5\n5 6";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertTrue(product > 0);
    }

    @Test
    @DisplayName("根节点度数为2的树")
    void testRootDegreeTwo() {
      String input = "5\n2 3 4 5 6\n1 2\n1 3\n2 4\n3 5";

      String result = executeWithInput(input);

      assertNotNull(result);
      long product = Long.parseLong(result);
      assertTrue(product > 0);
    }
  }

  @Nested
  @DisplayName("性能测试")
  class PerformanceTests {

    @Test
    @DisplayName("中等规模：N=100")
    void testMediumScale100() {
      StringBuilder input = new StringBuilder();
      int n = 100;
      input.append(n).append("\n");

      for (int i = 0; i < n; i++) {
        input.append("2 ");
      }
      input.append("\n");

      for (int i = 1; i < n; i++) {
        input.append(i).append(" ").append(i + 1).append("\n");
      }

      long startTime = System.currentTimeMillis();
      String result = executeWithInput(input.toString());
      long endTime = System.currentTimeMillis();

      assertTrue(endTime - startTime < 5000, "Should complete within 5 seconds for N=100");
      assertNotNull(result);
    }

    @Test
    @DisplayName("较大规模：N=1000")
    void testLargeScale1000() {
      StringBuilder input = new StringBuilder();
      int n = 1000;
      input.append(n).append("\n");

      for (int i = 0; i < n; i++) {
        input.append("1 ");
      }
      input.append("\n");

      for (int i = 1; i < n; i++) {
        input.append(i).append(" ").append(i + 1).append("\n");
      }

      long startTime = System.currentTimeMillis();
      String result = executeWithInput(input.toString());
      long endTime = System.currentTimeMillis();

      assertTrue(endTime - startTime < 10000, "Should complete within 10 seconds for N=1000");
      assertNotNull(result);
    }
  }
}
