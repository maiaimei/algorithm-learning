# Question

**Emma wishes to give her father a bouquet for his birthday. She asks for help from her mother Rosy. Rosy gives N flower sticks numbered 1 to N to Emma and tells her to arrange them in the bouquet in a particular order. She asks Emma to arrange the first K flower sticks in the order of increasing length and the remaining sticks in the order of decreasing length.**

Write an algorithm to find the final arrangement of the flower sticks in the bouquet.

## Input

The first line of the input consists of two space-separated integers - *num* and *random*, representing the number of flower sticks (N) and the number K given by Rosy to Emma, respectively.

The second line consists of N space-separated integers representing the length of the flower sticks.

## Output

Print N space-separated integers representing the final arrangement of the flower sticks in the bouquet.

## Constraints

*random < num*

*0 < num < 10⁶*

## Example

**Input:**

```
8 3

11 7 5 10 46 23 16 8
```

**Output:**

```
5 7 11 46 23 16 10 8
```

**Explanation:**

Emma has to arrange the first three flower sticks in an increasing order of the length and remaining sticks in the decreasing order of the length.

The final order of flower sticks in the bouquet is [5, 7, 11, 46, 23, 16, 10, 8].

------

# 题目

艾玛希望在她父亲生日时送他一束花。她向母亲罗西求助。罗西给了艾玛 N 根编号为 1 到 N 的花枝，并告诉她要按照特定的顺序排列在花束中。她要求艾玛将前 K 根花枝按长度递增的顺序排列，剩下的花枝按长度递减的顺序排列。

请编写一个算法，找出花束中花枝的最终排列顺序。

## 输入

输入的第一行包含两个用空格分隔的整数 —— *num* 和 *random*，分别代表花枝的数量（N）和罗西给艾玛的数字 K。

第二行包含 N 个用空格分隔的整数，表示每根花枝的长度。

## 输出

输出 N 个用空格分隔的整数，表示花束中花枝的最终排列顺序。

## 约束条件

*random < num*

*0 < num < 10⁶*

## 示例

**输入：**

```
8 3

11 7 5 10 46 23 16 8
```

**输出：**

```
5 7 11 46 23 16 10 8
```

**解释：**

艾玛必须将前三根花枝按长度递增排列，其余花枝按长度递减排列。

花束中花枝的最终顺序是 [5, 7, 11, 46, 23, 16, 10, 8]。

------

# 通俗题意

## 题目到底想让我做什么？（最核心的问题）

简单来说，题目就是让你把一堆花枝排个队。这堆花枝总共有 N 根，你要把前 K 根按“从小到大”排好，剩下的那些（从第 K+1 根到最后一根）按“从大到小”排好。最后把排好的顺序输出来就行。

举个例子：你有 8 根花枝，长度是 11、7、5、10、46、23、16、8，K 是 3。那你就要把前 3 根（11、7、5）排成从小到大 → 5、7、11；后面 5 根（10、46、23、16、8）排成从大到小 → 46、23、16、10、8。最后拼起来就是：5 7 11 46 23 16 10 8。

## 题目里有哪些具体的规矩？（条件、要求、限制）

我一条条给你列清楚，保证你一看就懂：

1. **输入格式**：
   - 第一行是两个数：N（花枝总数）和 K（前 K 根要从小到大排），中间用空格隔开。
   - 第二行是 N 个数字，代表每根花枝的长度，也是用空格隔开。
2. **输出格式**：
   - 输出一行，N 个数字，用空格隔开，就是排好后的最终顺序。
3. **排序规则**：
   - 前 K 根花枝 → 必须按**长度从小到大**排序。
   - 后 N-K 根花枝 → 必须按**长度从大到小**排序。
   - 注意：是“前 K 根”和“剩下的”，不是按编号，是按位置（第1到第K个，然后第K+1到第N个）。
4. **约束条件**（做题时必须遵守）：
   - K 一定小于 N（因为 *random < num*，也就是 K < N）。
   - N 必须大于 0 且小于 100 万（*0 < num < 10⁶*），所以数据可能很大，程序要写得高效点。
5. **注意点**：
   - 输入的花枝长度是“原始顺序”，不是已经排好序的，你得自己排序。
   - 排序只针对“前 K 个”和“后 N-K 个”这两段，不是整体排序。
   - 输出必须是按排好后的顺序，不能改顺序或漏掉任何一根。

✅ 总结一句话：

**你拿到一堆花枝长度，把前 K 根从小到大排，剩下的从大到小排，然后按顺序输出。**