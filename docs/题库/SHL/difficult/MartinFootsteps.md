# Question

Martin's father goes for a jog every morning. Martin follows him several minutes later. His father starts a position that is X1 meters away from their home and runs rectilinearly
at a constant speed of V1 meters per step for N steps.

Martin is standing at X2 meters away from his home. He wonders how fast he must run at some constant speed of V2 meters per step so as to maximize F, where F equals the number of
his father's footsteps that Martin will land on during his run. It is given that the first step that Martin will land on, from his starting position, will have been landed on by
his father.

Note that if more than one prospective velocity results in the same number of maximum common steps, output the highest prospective velocity as V2.

Write an algorithm to help Martin calculate F and V2.

**Input**

The first line of the input consists of two space-separated integers- X1and X2 representing the initial positions of Martin's father and Martin, respectively.

The second line consists of two space-separated integers- V1and N representing the velocity of the father and the number of steps taken by the father, respectively.

**Output**

Print two space-separated integers as maximum number of common footsteps F and respective speed V2

**Constraints**

1≤X1≤10⁵

0≤X2≤X1

1≤V1≤10⁴

1≤N≤10⁴

**Example**

**Input:**

3 2

2 20

**Output:**

21 1

**Explanation:**

Martin can have a maximum of 21 common footsteps with a velocity of 1 m/step.

------

# 题目

马丁的父亲每天早上都会去慢跑。马丁在几分钟后出发。他的父亲从距离家 X1 米的位置开始，并以恒定速度 V1 米/步直线跑 N 步。

马丁站在距离家 X2 米的位置。他想知道他必须以多快的恒定速度 V2 米/步跑步，才能最大化 F，其中 F 表示马丁在跑步过程中能够踩到的父亲的脚印数量。已知马丁从他的起始位置开始，踩到的第一个脚印将是父亲之前踩过的脚印。

注意：如果多个候选速度都能产生相同数量的最大共同脚印数，则输出其中最高的速度作为 V2。

请编写一个算法帮助马丁计算 F 和 V2。

**输入**

输入的第一行包含两个以空格分隔的整数 - X1 和 X2，分别表示马丁父亲和马丁的初始位置。

第二行包含两个以空格分隔的整数 - V1 和 N，分别表示父亲的速度和父亲的步数。

**输出**

输出两个以空格分隔的整数，即最大共同脚印数 F 和相应的速度 V2。

**约束条件**

1 ≤ X1 ≤ 10⁵

0 ≤ X2 ≤ X1

1 ≤ V1 ≤ 10⁴

1 ≤ N ≤ 10⁴

**示例**

**输入：**

3 2

2 20

**输出：**

21 1

**解释：**

马丁最多可以拥有 21 个共同脚印，速度为 1 米/步。

------

# 通俗题意

🌟 **a. 题目到底想让你做什么？——一句话核心目标**

> 马丁想追上他爸跑步时踩过的脚印，但不是真追人，是“踩脚印”！他要选一个自己的步速（V2），让自己能踩到他爸留下的脚印次数最多。如果好几个速度都能踩到一样多的脚印，那就选“最快的那一个速度”。

再通俗点：

你爸从离家3米的地方出发，每步走2米，一共走20步，踩了20个脚印（加上起点其实是21个位置）。你站在离家2米的地方，你每步走多少米（V2），才能让你走的时候“刚好踩中”他爸的脚印最多？最后你要输出：最多能踩中几个脚印（F），以及你该用多少米/步的速度（V2）。

📌 **b. 题目里有哪些具体规矩？——一条一条给你列明白**

我们来“拆解题目的限制和规则”，就像看游戏规则一样：

1. **你爸的起点位置（X1）**

   → 在离家1米到10万米之间（1 ≤ X1 ≤ 10⁵），不能太近也不能太远。

2. **你的起点位置（X2）**

   → 你在离家0米到你爸起点之间（0 ≤ X2 ≤ X1）。意思是你不能比你爸更靠前，你可能在家门口（0米），也可能在他后面一点点。

3. **你爸每步走多远（V1）**

   → 每步1米到1万米之间（1 ≤ V1 ≤ 10⁴）。这个数字是“步长”，不是速度单位“米/秒”，是“米/步”。

4. **你爸一共走几步（N）**

   → 走1步到1万步之间（1 ≤ N ≤ 10⁴）。他走完N步就停了。

5. **你走的每步长度（V2）是你自己选的**

   → 你要选一个“步长”，让你从你自己的起点出发，每步走V2米，然后尽量多踩中你爸的脚印。

6. **有个重要前提：你踩的第一个脚印，必须是你爸踩过的！**

   → 也就是说，你从你自己的位置X2出发，第一步踩下去的那个点，必须正好是你爸某一步踩过的地方。不能“乱踩”，必须“对准”。

7. **目标：最大化共同踩中的脚印数（F）**

   → 比如你爸踩了位置：X1, X1+V1, X1+2V1, ..., X1+(N-1)V1 —— 总共N个脚印位置（其实从第0步算起是N+1个点，但题目常说是N步，对应N+1个落脚点）。你要选V2，让你走的路径经过尽可能多这些点。

8. **如果多个V2能踩中一样多的脚印 → 选最大的那个V2！**

   → 比如V2=1能踩中21个，V2=2也能踩中21个 → 那就选V2=2！题目说了“最高速度优先”。

9. **输出格式：F（踩中次数） V2（你的步长）**

💡 举个例子帮你理解（就是题目给的）：

输入：

3 2 ← 爸爸从离家3米出发，你从离家2米出发

2 20 ← 爸爸每步2米，走20步

→ 爸爸踩的位置是：3, 5, 7, 9, ..., 3 + 2×19 = 41 → 共20步 → 21个点（包括起点）

你从2米出发，你每步走1米 → 你踩的位置是：2, 3, 4, 5, 6, 7, ..., 一直到你走完

你踩中的爸爸脚印：3, 5, 7, 9, ..., 41 → 一共21个！（因为从3开始到41，每次+2，共21个点）

→ 所以输出：21 1

✅ 总结一句话：

> “你从自己位置出发，选个步长V2，让你每步踩下去的位置尽可能多地‘对上’你爸踩过的位置，第一个踩的必须对上，最后输出最多能对上几个，和用哪个最快的步长。”

用户要求我"吸取经验，学会MartinFootsteps2.java的实现，从而解决计算Martin与父亲最大同步步数"，并且"不需要写代码"。这意味着我需要深入理解MartinFootsteps2.java的解题思路、数学原理和算法逻辑。

我需要：

1. 解释MartinFootsteps2.java的核心思路
2. 剖析其数学建模过程
3. 说明关键的数论应用
4. 总结算法步骤
5. 指出关键的经验教训

让我系统地分析这个解决方案。

# 解题思路

## 一、问题数学建模

**关键洞察**：将脚印对齐问题转化为**线性同余方程求解**。

**变量定义：**

- D = X1 - X2（马丁与父亲的初始距离）
- 父亲脚印位置：X1 + k·V1（k = 0, 1, 2, ..., N）
- 马丁第 t 步位置：X2 + t·V2

**核心方程**：马丁踩到父亲脚印时

```
X2 + t·V2 = X1 + k·V1
⇒ t·V2 = D + k·V1  （其中 D = X1 - X2）
```

**约束条件：**

1. **整除性**：D + k·V1 必须能被 V2 整除（因为 t 是整数）
2. **t ≥ k**：马丁的步数不能少于父亲的步数（隐含约束）
3. **k ∈ [0, N]**：父亲总共只有 N+1 个脚印

---

## 二、算法核心思想

#### 阶段1：生成候选 V2（第23-50行）

**原理**：V2 必须是 (D + k·V1) 的因数

**关键逻辑**：

```java
for(k =0..N){
position =D +k*V1
// 枚举 position 的所有因数作为候选 V2
    for(d =1..

sqrt(position)){
    if(position %d ==0){
    // d 是因数，position/d 也是因数
    // 检查 t = position / v2 >= k
    }
    }
    }
```

**优势**：无需遍历 1~10⁹ 的所有整数，**通过因数枚举精准捕获所有可能的 V2**

#### 阶段2：精确计数（第58-64行 + countValidStepsForV2）

对每个候选 V2，求解满足所有约束的 k 的个数。

---

## 三、数论核心：线性同余方程

**方程变换**：

```
D + k·V1 ≡ 0 (mod v2)
⇒ k·V1 ≡ -D (mod v2)
```

**数论工具**：

1. **解的存在性**：当且仅当 `gcd(V1, v2) | D` 时有解
2. **解的通式**：若存在解，则所有解构成等差数列
   ```
   k = k₀ + j·m  （其中 m = v2/g，g = gcd(V1, v2)）
   ```

**求解步骤**（第110-130行）：

```java
g =

gcd(V1, v2)
if(D %g !=0)return 0  // 无解

m =v2 /g  // 解的周期
V1' = V1 / g
D' = D / g

// 求解 k₀: V1'·k₀ ≡ -D' (mod m)
k₀ =(-D' * inv(V1',m))
mod m
```

**模逆元计算**（第165-189行）：
使用扩展欧几里得算法求 `inv(V1', m)`，这是求解线性同余方程的关键。

---

## 四、约束条件整合

**区间裁剪**（第83-104行）：

```java
// 下界 kMin：保证位置为正
if(D >0)kMin =0
    else if(D ==0)kMin =1  // 跳过 position=0
    else kMin =

floor(-D/V1) +1

// 上界 kMax：保证 t ≥ k
kMax =N
if(v2 >V1){
// 由 t = (D + k·V1)/v2 ≥ k 推导得
kMax =

min(N, floor(D/(v2-V1)))
    }

// 在 [kMin, kMax] 区间内统计满足 k = k₀ + j·m 的解个数
```

**计数公式**（第132-145行）：

```java
// 找到第一个 ≥ kMin 的解
firstK =k₀ +

ceil((kMin -k₀)/m)*m

if(firstK >kMax)return 0
    return

floor((kMax -firstK)/m)+1
```

---

## 五、关键经验总结

#### 1. **从暴力枚举到数学优化**

- 不是遍历所有 V2，而是**通过因数分解生成候选值**
- 时间复杂度从 O(V1·N) 降到 O(N·√max(D+k·V1))

#### 2. **约束条件的数学表达**

- 将"t ≥ k"转化为**不等式约束**，通过 kMax 限制解的范围
- 位置为正 → kMin 限制
- 整除性 → 线性同余方程

#### 3. **数论工具的威力**

- **GCD**：判断解的存在性
- **模逆元**：求解特解 k₀
- **等差数列**：描述解的完整集合

#### 4. **边界处理的严谨性**

- 分情况讨论 D > 0, D = 0, D < 0
- 特殊处理 v2 > V1 的情况
- 避免除以零和负数陷阱

---

## 六、算法流程图

```
输入 X1, X2, V1, N
↓
计算 D = X1 - X2
↓
阶段1：生成候选 V2
├─ 遍历 k = 0..N
│  ├─ position = D + k·V1
│  └─ 枚举 position 的所有因数 d
│     └─ 若 position/d ≥ k，加入候选集
↓
阶段2：精确计数
├─ 对每个候选 v2
│  ├─ 计算 kMin（位置为正）
│  ├─ 计算 kMax（t ≥ k 且 ≤ N）
│  ├─ 检查 D % gcd(V1,v2) == 0
│  ├─ 求周期 m = v2/g
│  ├─ 求模逆元 inv(V1/g, m)
│  ├─ 计算特解 k₀
│  └─ 统计 [kMin, kMax] 内解个数
│
└─ 保留最大值（相同取最大 v2）
↓
输出 bestF 和 bestV2
```

---

## 七、核心数学公式

**最终计数公式**：

```
F(v2) = |{ k ∈ [kMin, kMax] | k ≡ k₀ (mod m) }|
      = floor((kMax - firstK)/m) + 1
其中：
- m = v2 / gcd(V1, v2)
- k₀ 是方程 k·V1 ≡ -D (mod v2) 的最小非负解
- firstK = min{ k ≥ kMin | k ≡ k₀ (mod m) }
```

这个公式**精确、无遗漏地**计算了满足所有约束的脚印对数，是算法正确的数学保证。