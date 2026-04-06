# Question

Moche Goldberg starts trading in N towns (numbered 1 to N). Every day he sells his products in one of the towns. The towns that are chosen on any two successive days should be
different and a town i can be chosen at most ci times.

Write an algorithm to determine the number of days when he can operate following the above-mentioned rules.

## Input

The first line of the input consists of an integer N, representing the number of towns.

The next line consists of N space-separated integers - c₁, c₂, ..., cₙ representing the number of times each town can be chosen.

## Output

Print an integer representing the maximum number of days during which the salesman can work.

## Constraints

1 ≤ N ≤ 5 * 10⁴

1 ≤ cᵢ ≤ N

Σ cᵢ ≤ 10⁵

1 ≤ i ≤ N

## Example

**Input:**

3

7 2 3

**Output:**

11

**Explanation:**

The first, second and third towns are chosen 7, 2 and 3 times respectively.

The different towns are selected on successive days in a sequence: first, second, first, third, first, second, first, third, first, third, first.

So, the maximum number of days during which a salesman can sell is 11.

# 题目

莫切·戈德堡在 N 个城镇（编号从 1 到 N）开始销售产品。他每天在一个城镇销售产品。任意连续两天所选的城镇必须不同，且城镇 i 最多只能被选择 ci 次。

请编写一个算法，计算他按照上述规则最多能工作多少天。

## 输入

第一行包含一个整数 N，代表城镇的数量。

下一行包含 N 个以空格分隔的整数 — c₁, c₂, ..., cₙ，代表每个城镇最多可被选择的次数。

## 输出

输出一个整数，表示销售员最多可以工作的天数。

## 约束条件

1 ≤ N ≤ 5 × 10⁴

1 ≤ cᵢ ≤ N

Σ cᵢ ≤ 10⁵

1 ≤ i ≤ N

## 示例

**输入：**

3

7 2 3

**输出：**

11

**解释：**

第一个、第二个、第三个城镇分别被选择了 7 次、2 次、3 次。

连续的每一天选择不同的城镇，序列为：第一个，第二个，第一个，第三个，第一个，第二个，第一个，第三个，第一个，第三个，第一个。

因此，销售员最多可以销售 11 天。

# 通俗题意

📌 一句话总结这题在干啥：

> **你是一个销售员，要去 N 个城镇轮流卖东西，每天只能去一个镇，而且不能连续两天去同一个镇。每个镇有“最多能去几次”的上限。你要安排一个最长的行程表，使得你尽可能多地卖东西（也就是工作天数最多）。
**

## 题目到底想让我做什么？

**很简单：你有 N 个城镇，每个城镇能去的次数是有限的（比如镇1最多去7次，镇2最多去2次……），你每天选一个城镇卖货，但不能连着两天去同一个镇。你要安排一个“最久”的行程——也就是最多能卖多少天？**

比如例子中：3个镇，次数是 [7,2,3] → 最多能卖11天。

## 题目里的具体规矩（限制条件）

1. **每天只能选一个城镇卖货。**

   你不能一天跑两个镇，也不能休息（题目默认你每天都得工作，除非没合法安排了）。

2. **相邻两天不能去同一个城镇！**

   第一天去了镇A，第二天必须换镇B或C……不能第二天还去A。

3. **每个城镇有“使用次数上限”。**

   比如镇1最多只能去7次，去完7次后就不能再去了；镇2只能去2次，用完就废。

4. **目标：让总天数尽可能长！**

   不是让你随便排，而是找“最优安排”，让能卖货的天数最多。

5. **数据规模提醒（写程序时要注意）：**

    - 最多有5万个城镇（N ≤ 50000）

    - 每个城镇最多可选次数不超过 N（≤50000）

    - 所有城镇的总可选次数加起来不超过10万（Σcᵢ ≤ 10⁵）

      这个限制很关键！说明你不能暴力枚举，得用贪心或数学方法。

📌 总结一句话（给你背下来）：

> **在“不能连着去同一个镇 + 每个镇有次数上限”的前提下，安排一个最长的日程表，算出最多能卖几天。**

# 解题思路

## 🔍 核心观察：

这是一个**贪心 + 构造性问题**，关键是“如何安排顺序使得天数最大”。

我们不能暴力枚举（因为N可能达5万），必须找数学规律或贪心策略。

------

## 🧠 关键洞察：

设最大值为 maxC = max(c₁, c₂, ..., cₙ)

设总和为 total = c₁ + c₂ + ... + cₙ

**极端情况分析**：

### 情况1：如果 maxC > total - maxC + 1

→ 举例：c = [5,1,1] → maxC=5, total=7, total-maxC=2

→ 5 > 2 + 1 → 5 > 3 → 成立

此时，你最多只能安排：2 * (total - maxC) + 1 天

为什么？因为你必须用“其他城镇”来隔开“最大城镇”，每插一个其他城镇，最多可以夹两个最大城镇（如：M-O-M-O-M），最后如果还有剩，只能再放一个M。

所以最大天数 = 2 * (total - maxC) + 1

### 情况2：如果 maxC ≤ total - maxC + 1

→ 举例：c = [3,3,3] → maxC=3, total=9, total-maxC=6 → 3 ≤ 6+1 → 成立

→ 此时可以完全交错排列，没有瓶颈，最多能安排 total 天！

**为什么？**

因为最大频率城镇不会“溢出”，你可以把所有城镇穿插安排，总能保证相邻不重复。

------

## ✅ 最优解法（贪心 + 数学推导）：

1. 计算 total = 所有cᵢ之和

2. 计算 maxC = 最大的cᵢ

3. 如果 maxC > total - maxC + 1 → 返回 2*(total - maxC) + 1

   否则 → 返回 total

------

## 📊 复杂度分析：

- 时间复杂度：O(N) —— 遍历数组求和+找最大值
- 空间复杂度：O(1) —— 只用几个变量

------

## ⚖️ 与其他方法对比：

| 方法       | 思路                   | 时间       | 空间   | 是否可行    | 说明                       |
|----------|----------------------|----------|------|---------|--------------------------|
| 暴力DFS/回溯 | 枚举所有排列               | O(N!)    | O(N) | ❌ 超时    | N最大5万，完全不可行              |
| 模拟贪心构造   | 每次选剩余次数最多且≠上一个的城镇    | O(total) | O(N) | ⚠️ 可行但慢 | total最大10万，勉强可过，但不如数学法优雅 |
| 数学推导（推荐） | 利用 maxC 和 total 关系公式 | O(N)     | O(1) | ✅ 最优    | 精准、简洁、高效                 |
