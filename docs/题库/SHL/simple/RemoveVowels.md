# Question

The vowels in the English alphabet are: (a, e, i, o, u, A, E, I, O, U). Write an algorithm to eliminate all vowels from a given string.

## Input

The input consists of the given string.

## Output

Print a string after removing all the vowels from the given string.

## Constraints

The given string contains English alphabets only.

## Example

**Input:**

```
MynameisAnthony
```

**Output:**

```
Mynmsnthny
```

**Explanation:**

After removing the vowels, the string is `Mynmsnthny`.

# 题目

英语字母表中的元音字母是：(a, e, i, o, u, A, E, I, O, U)。请编写一个算法，从给定字符串中删除所有元音字母。

## 输入

输入包含给定的字符串。

## 输出

输出删除所有元音字母后的字符串。

## 约束条件

给定字符串仅包含英文字母。

## 示例

**输入：**

```
MynameisAnthony
```

**输出：**

```
Mynmsnthny
```

**说明：**

删除所有元音字母后，字符串变为 `Mynmsnthny`。

# 通俗题意

## 题目到底想让你做什么？——一句话讲清楚核心

> 就是让你写个程序（或算法），把一句话里的所有“元音字母”（a/e/i/o/u 和它们的大写 A/E/I/O/U）统统删掉，只留下剩下的字母，然后把结果打印出来。

举个栗子：

输入是 "MynameisAnthony" → 删掉所有 a,e,i,o,u（大小写都算） → 剩下 "Mynmsnthny" → 输出这个。

## 题目里的具体规矩/限制条件（一条条列清楚，不能漏）

1. **你要处理的对象**：一个字符串（可以理解成一句话或一串字母）

   → 比如 "HelloWorld"、"MynameisAnthony" 这种。

2. **你要删掉哪些字母？**

   → **元音字母**：小写 a, e, i, o, u

   → **还有大写**：A, E, I, O, U

   → 其他字母（比如 b, c, d, F, G 等）统统保留！

3. **输出什么？**

   → 把删完元音之后剩下的字母，按原顺序拼起来，打印出来。

4. **有没有什么限制？**

   → **只含英文字母！**（题目明确说了 “contains English alphabets only”）

   → 不会出现数字、空格、标点符号（比如 !, ?, . 等）

   → 所以你不用考虑处理其他字符，专心删元音就行。

5. **输入输出格式？**

   → 输入就是一行字符串（比如 MynameisAnthony）

   → 输出也是一行字符串（删完元音后的结果）

   → 不用加额外说明文字，直接输出结果。

💡 **通俗比喻帮你理解：**

想象你有一串字母磁贴（比如冰箱贴），上面写着 “MynameisAnthony”。

现在老师说：“把所有 a/e/i/o/u（包括大写）的贴纸都撕掉，剩下的贴纸按顺序摆好，拍张照给我。”

你照做后，剩下的就是 “Mynmsnthny” —— 这就是你要输出的答案！

📌 **总结一句话记住：**

“给你一串纯英文字母，把 a/e/i/o/u（大小写）全扔掉，剩下啥就输出啥。”