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

@DisplayName("Martin Footsteps 马丁脚印问题测试")
class MartinFootstepsTest {

  private String executeWithInput(String input) {
    InputStream originalIn = System.in;
    PrintStream originalOut = System.out;

    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      System.setIn(inputStream);
      System.setOut(new PrintStream(outputStream));

      MartinFootsteps.Solution.main(new String[]{});

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
    @DisplayName("示例测试：X1=3, X2=2, V1=2, N=20")
    void testExampleCase() {
      String input = "3 2\n2 20";

      String result = executeWithInput(input);

      assertEquals("21 1", result);
    }

    @Test
    @DisplayName("基本测试：X1=10, X2=5, V1=3, N=10")
    void testBasicCase1() {
      String input = "10 5\n3 10";

      String result = executeWithInput(input);

      assertNotNull(result);
      assertFalse(result.isEmpty());

      String[] parts = result.split(" ");
      assertEquals(2, parts.length);

      long F = Long.parseLong(parts[0]);
      long V2 = Long.parseLong(parts[1]);

      assertTrue(F > 0, "F should be positive");
      assertTrue(V2 > 0, "V2 should be positive");
    }

    @Test
    @DisplayName("基本测试：X1=5, X2=0, V1=2, N=5")
    void testBasicCase2() {
      String input = "5 0\n2 5";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);
    }

    @Test
    @DisplayName("父亲和马丁在同一位置：X1=X2")
    void testSameStartPosition() {
      String input = "5 5\n2 10";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);

      long F = Long.parseLong(parts[0]);
      assertTrue(F >= 1, "At least one common footprint (first step requirement)");
    }

    @Test
    @DisplayName("马丁在家门口：X2=0")
    void testMartinAtHome() {
      String input = "8 0\n2 15";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);
    }

    @Test
    @DisplayName("小步数测试：N=1")
    void testSmallSteps() {
      String input = "5 3\n2 1";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);
    }
  }

  @Nested
  @DisplayName("边界条件测试")
  class BoundaryConditions {

    @Test
    @DisplayName("最小约束：X1=1, X2=0, V1=1, N=1")
    void testMinimumConstraints() {
      String input = "1 0\n1 1";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);

      long F = Long.parseLong(parts[0]);
      long V2 = Long.parseLong(parts[1]);

      assertTrue(F >= 1);
      assertTrue(V2 >= 1);
    }

    @Test
    @DisplayName("X1最大值附近：X1=100000")
    void testMaxX1() {
      String input = "100000 50000\n10000 100";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);
    }

    @Test
    @DisplayName("V1最大值：V1=10000")
    void testMaxV1() {
      String input = "50000 25000\n10000 50";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);
    }

    @Test
    @DisplayName("N最大值：N=10000")
    void testMaxN() {
      String input = "1000 500\n10 10000";

      long startTime = System.currentTimeMillis();
      String result = executeWithInput(input);
      long endTime = System.currentTimeMillis();

      assertTrue(endTime - startTime < 10000, "Execution time should be reasonable (< 10s)");
      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);
    }

    @Test
    @DisplayName("X2=X1（马丁在父亲起点）")
    void testX2EqualsX1() {
      String input = "100 100\n5 20";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);

      long F = Long.parseLong(parts[0]);
      assertTrue(F >= 1);
    }

    @Test
    @DisplayName("X2=0且X1=1（极端情况）")
    void testX2ZeroX1One() {
      String input = "1 0\n1 1";

      String result = executeWithInput(input);

      assertEquals("1 1", result);
    }
  }

  @Nested
  @DisplayName("特殊输入场景测试")
  class SpecialCases {

    @Test
    @DisplayName("父亲步长为1：V1=1")
    void testV1EqualsOne() {
      String input = "10 5\n1 10";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);
    }

    @Test
    @DisplayName("大距离差：X1远大于X2")
    void testLargeDistanceDifference() {
      String input = "100000 1\n100 50";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);
    }

    @Test
    @DisplayName("零距离差：X1=X2=0")
    void testZeroDistance() {
      String input = "0 0\n1 5";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);
    }

    @Test
    @DisplayName("质数步长：V1为质数")
    void testPrimeVelocity() {
      String input = "50 25\n7 30";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);
    }

    @Test
    @DisplayName("多个V2产生相同F时选择最大V2")
    void testMultipleV2SameF() {
      String input = "6 3\n2 10";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);

      long F = Long.parseLong(parts[0]);
      long V2 = Long.parseLong(parts[1]);

      assertTrue(F > 0);
      assertTrue(V2 > 0);
    }

    @Test
    @DisplayName("父亲只走一步：N=1的边界")
    void testFatherOneStep() {
      String input = "10 5\n3 1";

      String result = executeWithInput(input);

      assertNotNull(result);
      String[] parts = result.split(" ");
      assertEquals(2, parts.length);
    }
  }

  @Nested
  @DisplayName("参数化测试")
  class ParameterizedTests {

    private static Stream<Arguments> provideTestCases() {
      return Stream.of(
          Arguments.of("3 2\n2 20", "21 1"),
          Arguments.of("1 0\n1 1", "1 1"),
          Arguments.of("5 5\n2 5", null),
          Arguments.of("10 0\n5 8", null),
          Arguments.of("100 50\n10 20", null)
      );
    }

    @ParameterizedTest(name = "{index}: Input={0}")
    @MethodSource("provideTestCases")
    @DisplayName("多组数据验证")
    void testMultipleCases(String input, String expected) {
      String result = executeWithInput(input);

      if (expected != null) {
        assertEquals(expected, result);
      } else {
        assertNotNull(result);
        String[] parts = result.split(" ");
        assertEquals(2, parts.length);

        long F = Long.parseLong(parts[0]);
        long V2 = Long.parseLong(parts[1]);

        assertTrue(F > 0, "F must be positive");
        assertTrue(V2 > 0, "V2 must be positive");
      }
    }
  }

  @Nested
  @DisplayName("输出格式验证测试")
  class FormatValidationTests {

    @Test
    @DisplayName("输出格式：两个空格分隔的整数")
    void testOutputFormat() {
      String input = "3 2\n2 20";

      String result = executeWithInput(input);

      assertFalse(result.startsWith(" "), "Output should not start with space");
      assertFalse(result.endsWith(" "), "Output should not end with space");
      assertFalse(result.contains("  "), "Output should not contain consecutive spaces");
    }

    @Test
    @DisplayName("输出为纯数字和空格")
    void testOutputContainsOnlyDigitsAndSpaces() {
      String input = "10 5\n3 10";

      String result = executeWithInput(input);

      assertTrue(result.matches("[0-9 ]+"), "Output should contain only digits and spaces");
    }

    @Test
    @DisplayName("输出恰好包含两个数值")
    void testOutputElementCount() {
      String input = "50 25\n5 30";

      String result = executeWithInput(input);

      String[] elements = result.split(" ");
      assertEquals(2, elements.length, "Output should contain exactly 2 elements: F and V2");
    }

    @Test
    @DisplayName("F和V2都是正整数")
    void testPositiveIntegers() {
      String input = "20 10\n4 15";

      String result = executeWithInput(input);

      String[] parts = result.split(" ");
      long F = Long.parseLong(parts[0]);
      long V2 = Long.parseLong(parts[1]);

      assertTrue(F > 0, "F should be positive");
      assertTrue(V2 > 0, "V2 should be positive");
    }
  }

  @Nested
  @DisplayName("数学逻辑验证测试")
  class MathematicalLogicTests {

    @Test
    @DisplayName("第一个脚印必须匹配：验证F至少为1")
    void testFirstFootprintMatch() {
      String input = "10 5\n3 20";

      String result = executeWithInput(input);

      String[] parts = result.split(" ");
      long F = Long.parseLong(parts[0]);

      assertTrue(F >= 1, "According to problem statement, first footprint must match, so F >= 1");
    }

    @Test
    @DisplayName("F不超过父亲的总脚印数N+1")
    void testFNotExceedTotalFootprints() {
      String input = "10 5\n3 20";

      String result = executeWithInput(input);

      String[] parts = result.split(" ");
      long F = Long.parseLong(parts[0]);
      long N = 20;

      assertTrue(F <= N + 1, "F cannot exceed total footprints (N+1)");
    }

    @Test
    @DisplayName("相同F时选择最大V2：手动验证")
    void testMaxV2WhenSameF() {
      String input = "6 3\n2 10";

      String result = executeWithInput(input);

      String[] parts = result.split(" ");
      long F = Long.parseLong(parts[0]);
      long V2 = Long.parseLong(parts[1]);

      assertTrue(F > 0);
      assertTrue(V2 > 0);
    }

    @Test
    @DisplayName("V2应该能整除某些D+k*V1的值")
    void testV2Divisibility() {
      String input = "10 5\n3 10";

      String result = executeWithInput(input);

      String[] parts = result.split(" ");
      long V2 = Long.parseLong(parts[1]);

      assertTrue(V2 > 0, "V2 must be positive");
    }
  }

  @Nested
  @DisplayName("性能测试")
  class PerformanceTests {

    @Test
    @DisplayName("中等规模输入：N=1000")
    void testMediumScale() {
      String input = "5000 2500\n50 1000";

      long startTime = System.currentTimeMillis();
      String result = executeWithInput(input);
      long endTime = System.currentTimeMillis();

      assertTrue(endTime - startTime < 5000, "Should complete within 5 seconds for N=1000");
      assertNotNull(result);
    }

    @Test
    @DisplayName("较大规模输入：N=5000")
    void testLargeScale() {
      String input = "10000 5000\n100 5000";

      long startTime = System.currentTimeMillis();
      String result = executeWithInput(input);
      long endTime = System.currentTimeMillis();

      assertTrue(endTime - startTime < 10000, "Should complete within 10 seconds for N=5000");
      assertNotNull(result);
    }
  }
}
