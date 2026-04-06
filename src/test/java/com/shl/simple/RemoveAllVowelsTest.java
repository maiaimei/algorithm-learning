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

@DisplayName("RemoveVowels 删除元音字母测试")
class RemoveAllVowelsTest {

  private String executeWithInput(String input) {
    InputStream originalIn = System.in;
    PrintStream originalOut = System.out;

    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      System.setIn(inputStream);
      System.setOut(new PrintStream(outputStream));

      RemoveAllVowels.Solution.main(new String[]{});

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
    @DisplayName("示例测试：MynameisAnthony -> Mynmsnthny")
    void testExampleCase() {
      String input = "MynameisAnthony";

      String result = executeWithInput(input);

      assertEquals("Mynmsnthny", result);
    }

    @Test
    @DisplayName("基本测试：HelloWorld -> HllWrld")
    void testBasicCase1() {
      String input = "HelloWorld";

      String result = executeWithInput(input);

      assertEquals("HllWrld", result);
    }

    @Test
    @DisplayName("基本测试：Programming -> Prgrmmng")
    void testBasicCase2() {
      String input = "Programming";

      String result = executeWithInput(input);

      assertEquals("Prgrmmng", result);
    }

    @Test
    @DisplayName("包含所有元音字母：aeiouAEIOU -> 空字符串")
    void testAllVowels() {
      String input = "aeiouAEIOU";

      String result = executeWithInput(input);

      assertEquals("", result);
    }

    @Test
    @DisplayName("不包含元音字母：bcdfg -> bcdfg")
    void testNoVowels() {
      String input = "bcdfg";

      String result = executeWithInput(input);

      assertEquals("bcdfg", result);
    }

    @Test
    @DisplayName("混合大小写：HeLLo WoRLd -> HLL WRLD")
    void testMixedCase() {
      String input = "HeLLoWoRLd";

      String result = executeWithInput(input);

      assertEquals("HLLWRLd", result);
    }
  }

  @Nested
  @DisplayName("边界条件测试")
  class BoundaryConditions {

    @Test
    @DisplayName("单个元音字母：a -> 空字符串")
    void testSingleVowel() {
      String input = "a";

      String result = executeWithInput(input);

      assertEquals("", result);
    }

    @Test
    @DisplayName("单个辅音字母：b -> b")
    void testSingleConsonant() {
      String input = "b";

      String result = executeWithInput(input);

      assertEquals("b", result);
    }

    @Test
    @DisplayName("两个字符：ab -> b")
    void testTwoCharacters() {
      String input = "ab";

      String result = executeWithInput(input);

      assertEquals("b", result);
    }

    @Test
    @DisplayName("全部为大写元音：AEIOU -> 空字符串")
    void testAllUpperVowels() {
      String input = "AEIOU";

      String result = executeWithInput(input);

      assertEquals("", result);
    }

    @Test
    @DisplayName("全部为小写元音：aeiou -> 空字符串")
    void testAllLowerVowels() {
      String input = "aeiou";

      String result = executeWithInput(input);

      assertEquals("", result);
    }

    @Test
    @DisplayName("全部为辅音字母：BCDFG -> BCDFG")
    void testAllConsonants() {
      String input = "BCDFG";

      String result = executeWithInput(input);

      assertEquals("BCDFG", result);
    }

    @Test
    @DisplayName("较长字符串测试")
    void testLongString() {
      String input = "EducationIsImportantButImplementationIsMoreImportant";

      String result = executeWithInput(input);

      assertEquals("dctnsmprtntBtmplmnttnsMrmprtnt", result);
    }
  }

  @Nested
  @DisplayName("特殊输入场景测试")
  class SpecialCases {

    @Test
    @DisplayName("只有大写字母：HELLO -> HLL")
    void testOnlyUpperCase() {
      String input = "HELLO";

      String result = executeWithInput(input);

      assertEquals("HLL", result);
    }

    @Test
    @DisplayName("只有小写字母：hello -> hll")
    void testOnlyLowerCase() {
      String input = "hello";

      String result = executeWithInput(input);

      assertEquals("hll", result);
    }

    @Test
    @DisplayName("交替大小写：AaEeIiOoUu -> 空字符串")
    void testAlternatingCase() {
      String input = "AaEeIiOoUu";

      String result = executeWithInput(input);

      assertEquals("", result);
    }

    @Test
    @DisplayName("元音和辅音交替：BaBeBiBoBu -> BBBBB")
    void testVowelConsonantAlternating() {
      String input = "BaBeBiBoBu";

      String result = executeWithInput(input);

      assertEquals("BBBBB", result);
    }

    @Test
    @DisplayName("连续元音字母：Beautiful -> Btfl")
    void testConsecutiveVowels() {
      String input = "Beautiful";

      String result = executeWithInput(input);

      assertEquals("Btfl", result);
    }

    @Test
    @DisplayName("连续辅音字母：Strengths -> Strngths")
    void testConsecutiveConsonants() {
      String input = "Strengths";

      String result = executeWithInput(input);

      assertEquals("Strngths", result);
    }

    @Test
    @DisplayName("首尾都是元音：apple -> ppl")
    void testStartEndWithVowel() {
      String input = "apple";

      String result = executeWithInput(input);

      assertEquals("ppl", result);
    }

    @Test
    @DisplayName("首尾都是辅音：book -> bk")
    void testStartEndWithConsonant() {
      String input = "book";

      String result = executeWithInput(input);

      assertEquals("bk", result);
    }

    @Test
    @DisplayName("单个重复元音：aaaaa -> 空字符串")
    void testRepeatedVowel() {
      String input = "aaaaa";

      String result = executeWithInput(input);

      assertEquals("", result);
    }

    @Test
    @DisplayName("单个重复辅音：bbbbb -> bbbbb")
    void testRepeatedConsonant() {
      String input = "bbbbb";

      String result = executeWithInput(input);

      assertEquals("bbbbb", result);
    }
  }

  @Nested
  @DisplayName("参数化测试")
  class ParameterizedTests {

    private static Stream<Arguments> provideTestCases() {
      return Stream.of(
          Arguments.of("MynameisAnthony", "Mynmsnthny"),
          Arguments.of("HelloWorld", "HllWrld"),
          Arguments.of("Programming", "Prgrmmng"),
          Arguments.of("Algorithm", "lgrthm"),
          Arguments.of("Computer", "Cmptr"),
          Arguments.of("Software", "Sftwr"),
          Arguments.of("Developer", "Dvlpr"),
          Arguments.of("aeiou", ""),
          Arguments.of("AEIOU", ""),
          Arguments.of("bcdfg", "bcdfg"),
          Arguments.of("BCDFG", "BCDFG"),
          Arguments.of("Hello", "Hll"),
          Arguments.of("WORLD", "WRLD"),
          Arguments.of("Queue", "Q"),
          Arguments.of("Rhythm", "Rhythm")
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
  @DisplayName("性能测试")
  class PerformanceTests {

    @Test
    @DisplayName("长字符串性能测试：10000个字符")
    void testLongStringPerformance() {
      StringBuilder inputBuilder = new StringBuilder();
      for (int i = 0; i < 10000; i++) {
        inputBuilder.append((char) ('a' + i % 26));
      }
      String input = inputBuilder.toString();

      long startTime = System.currentTimeMillis();
      String result = executeWithInput(input);
      long endTime = System.currentTimeMillis();

      assertTrue(endTime - startTime < 1000, "Execution time should be less than 1 second");
      assertNotNull(result);
    }

    @Test
    @DisplayName("极长字符串性能测试：100000个字符")
    void testVeryLongStringPerformance() {
      StringBuilder inputBuilder = new StringBuilder();
      for (int i = 0; i < 100000; i++) {
        inputBuilder.append((char) ('a' + i % 26));
      }
      String input = inputBuilder.toString();

      long startTime = System.currentTimeMillis();
      String result = executeWithInput(input);
      long endTime = System.currentTimeMillis();

      assertTrue(endTime - startTime < 2000, "Execution time should be less than 2 seconds");
      assertNotNull(result);
    }
  }

  @Nested
  @DisplayName("格式验证测试")
  class FormatValidationTests {

    @Test
    @DisplayName("输出不含多余空格或换行")
    void testOutputFormat() {
      String input = "HelloWorld";

      String result = executeWithInput(input);

      assertFalse(result.contains(" "), "Output should not contain spaces");
      assertFalse(result.contains("\n"), "Output should not contain newlines");
      assertFalse(result.contains("\r"), "Output should not contain carriage returns");
    }

    @Test
    @DisplayName("输出保持原始顺序")
    void testOutputMaintainsOrder() {
      String input = "abcdef";

      String result = executeWithInput(input);

      assertEquals("bcdf", result);
      assertEquals('b', result.charAt(0));
      assertEquals('c', result.charAt(1));
      assertEquals('d', result.charAt(2));
      assertEquals('f', result.charAt(3));
    }
  }
}
