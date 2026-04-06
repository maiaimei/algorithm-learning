# Arya 的树路径最大乘积问题

------

## Question

Arya is attempting to solve a math problem. In this problem, she is given a tree with N nodes, indexed from 1 to N where the root node is indexed as 1. Each node of the tree has a
defined value. She wants to trace a path from one leaf to another leaf in such a way that will award her the maximum score for that path. The score of a path is defined as the
product of node values along the path.

### Input

The first line of the input consists of an integer N, representing the number of nodes in the tree.

The second line consists N space-separated integers representing the value of each node in the tree.

The next N-1 lines consist of two space-separated integers - a and b, representing the indices of the starting node and ending node of an edge of the tree.

### Output

Print an integer representing the maximum possible score.

### Constraints

1≤N≤10⁴

−10³≤value≤10³; where value is the value of a node

### Example

**Input:**

4

-1 2 3 2

1 2

1 3

3 4

**Tree structure diagram:**

```
    1(-1)
    /   \
  2(2)   3(3)
         /
       4(2)
```

**Output:**

-12

**Explanation:**

There is only one route from leaf 2 to leaf 4, as there are only 2 leaves.

(2)->(1): Score = 2 * -1 = -2

(2)->(1)->(3): Score = -2 * 3 = -6

(2)->(1)->(3)->(4): Score = -6 * 2 = -12

## 题目描述

Arya 正在尝试解决一个数学问题。在这个问题中，她得到了一棵包含 N 个节点的树，节点编号从 1 到 N，其中根节点的编号为
1。树中的每个节点都有一个定义好的值。她希望找到一条从一个叶子节点到另一个叶子节点的路径，使得这条路径能够为她带来最大得分。路径的得分定义为该路径上所有节点值的乘积。

### 输入格式

输入的第一行包含一个整数 N，表示树中节点的数量。

第二行包含 N 个以空格分隔的整数，表示树中每个节点的值。

接下来的 N-1 行，每行包含两个以空格分隔的整数 a 和 b，表示树的一条边的起始节点和结束节点的编号。

### 输出格式

输出一个整数，表示可能的最大得分。

### 约束条件

1 ≤ N ≤ 10⁴

−10³ ≤ value ≤ 10³；其中节点值是某个节点的值

### 示例

**输入：**

4

-1 2 3 2

1 2

1 3

3 4

**树形结构示意图：**

```
    1(-1)
    /   \
  2(2)   3(3)
         /
       4(2)
```

**输出：**

-12

**解释：**

由于树中只有 2 个叶子节点，从叶子节点 2 到叶子节点 4 只有一条路径。

(2)->(1)：得分 = 2 * -1 = -2

(2)->(1)->(3)：得分 = -2 * 3 = -6

(2)->(1)->(3)->(4)：得分 = -6 * 2 = -12

------

## 通俗题意

### **a. 题目到底想让我做什么？**

简单来说：

**给一棵树（N 个节点，1 号是根），每个节点上有个正数或负数。**

**你要找出从任意一个“叶子”（没有孩子的节点）走到另一个“叶子”的路径，把这条路径上的所有节点值乘起来，得到一个“得分”。**

**任务是找出所有可能路径中，得分最高的那个，输出这个最高得分。**

举个例子：

树是这样的（括号里是节点值）：

```
1(-1)
 /   \
2(2)  3(3)
      /
    4(2)
```

叶子节点只有 **2** 和 **4**（因为它们没有孩子）。

从叶子 2 到叶子 4 的路径是：**2 → 1 → 3 → 4**。

得分的算法是：

2 × (-1) = -2

-2 × 3 = -6

-6 × 2 = -12

所以最终得分是 **-12**。

这也是题目样例给出的答案。

------

### **b. 题目里的具体规矩（条件和限制）**

我给你一条一条列清楚，就像游戏规则一样。

#### **1. 数据输入规则**

- **第一行**：N（节点个数）。
- **第二行**：N 个整数，按顺序是节点 1 到 N 的值（值可以是负的、正的、零）。
- **后面 N-1 行**：每行两个数 a, b，表示 a 和 b 之间有一条边。
- 树的结构就是通过这些边确定的，而且**根节点固定是 1**（不过解题时根节点是谁不重要）。

#### **2. 路径规则**

- **起点和终点必须是叶子节点**（叶子节点 = 在树里只有一个邻居的节点）。
- **路径是一条简单路径**：不能走重复的节点，必须沿着树的边走。
- **要计算整条路径上所有节点值的乘积**，包括起点和终点以及中间经过的所有节点。
- **目标**：找乘积最大的路径（可能结果是负数，也可能很大，也可能为 0）。

#### **3. 数值范围（限制）**

- 节点数 N 最多 **1 万**。
- 节点值的范围：**-1000 到 1000**（含两端）。
- 乘积可能会非常大（因为最多走 N 个节点，每个绝对值最大 1000），所以编程时可能要用 **64 位整数**（long long）存中间结果，不能直接用 int。

#### **4. 注意点**

- 路径可以是两个不同的叶子，也可以是**同一个叶子**当起点和终点（如果它只有一个邻居的话，但这种情况得分就是它自己的值）。
- 负值 × 负值会变正，所以要同时考虑“最大正乘积”和“最小负乘积”，因为可能用负负得正来获得更大的正数。
- 树是**无环连通图**，所以任意两点之间只有一条简单路径。

### **一句话总结**

> 给你一棵树，每个节点有个整数（可正可负可零），找一个从一个叶子到另一个叶子的路径，把所有节点值乘起来，结果要最大。

**关键难点**：

由于有负数，不能只存“最大值”，还要存“最小值”（最负的），因为在父节点组合时，可能“最负的 × 负数”变成很大的正数。