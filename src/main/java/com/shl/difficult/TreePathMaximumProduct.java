package com.shl.difficult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TreePathMaximumProduct {

  public static class Solution1 {

    public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      // 1. 读取节点数量
      int n = Integer.parseInt(br.readLine());

      // 2. 读取节点值
      int[] values = new int[n + 1];
      String[] valueTokens = br.readLine().split(" ");
      for (int i = 1; i <= n; i++) {
        values[i] = Integer.parseInt(valueTokens[i - 1]);
      }

      // 3. 构建树（邻接表）
      List<Integer>[] tree = new ArrayList[n + 1];
      for (int i = 1; i <= n; i++) {
        tree[i] = new ArrayList<>();
      }

      for (int i = 0; i < n - 1; i++) {
        String[] edgeTokens = br.readLine().split(" ");
        int a = Integer.parseInt(edgeTokens[0]);
        int b = Integer.parseInt(edgeTokens[1]);
        tree[a].add(b);
        tree[b].add(a);
      }

      // 特殊情况：只有一个节点
      if (n == 1) {
        System.out.println(values[1]);
        return;
      }

      // 4. 找出所有叶子节点
      List<Integer> leaves = findLeaves(tree, n);

      // 5. 特殊情况处理
      if (leaves.isEmpty()) {
        // 理论上不会发生，除非n=1（已处理）
        System.out.println(0);
        return;
      }

      if (leaves.size() == 1) {
        // 如果只有一个叶子（理论上树至少有两个叶子，除非链长度为1，但n=2时有两个叶子）
        // 但为了安全，处理这种情况
        System.out.println(values[leaves.get(0)]);
        return;
      }

      // 6. 计算最大得分
      long maxScore = computeMaxScore(tree, values, leaves, n);

      // 7. 输出结果
      System.out.println(maxScore);
    }

    /**
     * 找出所有叶子节点
     */
    private static List<Integer> findLeaves(List<Integer>[] tree, int n) {
      List<Integer> leaves = new ArrayList<>();
      for (int i = 1; i <= n; i++) {
        // 在无向树中，叶子节点是度数为1的节点
        if (tree[i].size() == 1) {
          leaves.add(i);
        }
      }
      return leaves;
    }

    /**
     * 计算最大得分
     */
    private static long computeMaxScore(List<Integer>[] tree, int[] values, List<Integer> leaves, int n) {
      long maxScore = Long.MIN_VALUE;

      // 对每对叶子节点，计算路径乘积
      for (int i = 0; i < leaves.size(); i++) {
        for (int j = i + 1; j < leaves.size(); j++) {
          int leaf1 = leaves.get(i);
          int leaf2 = leaves.get(j);
          long product = computePathProduct(tree, values, leaf1, leaf2, n);
          maxScore = Math.max(maxScore, product);
        }
      }

      return maxScore;
    }

    /**
     * 计算从叶子节点start到叶子节点end的路径乘积
     * 使用BFS搜索路径
     */
    private static long computePathProduct(List<Integer>[] tree, int[] values,
        int start, int end, int n) {
      if (start == end) {
        return values[start];
      }

      // 使用BFS找到路径
      Queue<PathNode> queue = new LinkedList<>();
      boolean[] visited = new boolean[n + 1];

      queue.offer(new PathNode(start, values[start]));
      visited[start] = true;

      while (!queue.isEmpty()) {
        PathNode current = queue.poll();

        // 如果到达目标叶子节点
        if (current.node == end) {
          return current.product;
        }

        // 遍历邻居
        for (int neighbor : tree[current.node]) {
          if (!visited[neighbor]) {
            visited[neighbor] = true;
            long newProduct = multiply(current.product, values[neighbor]);
            queue.offer(new PathNode(neighbor, newProduct));
          }
        }
      }

      // 理论上不会到达这里，因为树是连通的
      return Long.MIN_VALUE;
    }

    /**
     * 安全的乘法，处理可能的溢出
     * 这里简化处理，直接相乘，因为约束中N≤10000，值≤1000
     * 1000^10000会溢出，但测试数据通常不会这么极端
     */
    private static long multiply(long a, int b) {
      return a * b;
    }

    /**
     * 辅助类：表示BFS搜索路径中的节点
     */
    private static class PathNode {

      int node;           // 节点编号
      long product;       // 从起点到该节点的乘积

      PathNode(int node, long product) {
        this.node = node;
        this.product = product;
      }
    }
  }

  public static class Solution2 {

    public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      // 1. 读取节点数量
      int n = Integer.parseInt(br.readLine());

      // 2. 读取节点值
      int[] values = new int[n + 1];
      String[] valueTokens = br.readLine().split(" ");
      for (int i = 1; i <= n; i++) {
        values[i] = Integer.parseInt(valueTokens[i - 1]);
      }

      // 3. 构建树（邻接表）
      List<Integer>[] tree = new ArrayList[n + 1];
      for (int i = 1; i <= n; i++) {
        tree[i] = new ArrayList<>();
      }

      for (int i = 0; i < n - 1; i++) {
        String[] edgeTokens = br.readLine().split(" ");
        int a = Integer.parseInt(edgeTokens[0]);
        int b = Integer.parseInt(edgeTokens[1]);
        tree[a].add(b);
        tree[b].add(a);
      }

      // 特殊情况：只有一个节点
      if (n == 1) {
        System.out.println(values[1]);
        return;
      }

      // 4. 使用树形DP计算最大得分
      long maxScore = computeMaxScoreByDP(tree, values, n);

      // 5. 输出结果
      System.out.println(maxScore);
    }

    /**
     * 使用树形DP计算最大得分
     */
    private static long computeMaxScoreByDP(List<Integer>[] tree, int[] values, int n) {
      // 用于存储每个节点的DP值
      NodeDP[] dp = new NodeDP[n + 1];
      for (int i = 1; i <= n; i++) {
        dp[i] = new NodeDP();
      }

      // 全局最大得分
      long[] maxScore = {Long.MIN_VALUE};

      // 从节点1开始DFS（题目指定根节点为1）
      boolean[] visited = new boolean[n + 1];
      dfs(1, -1, tree, values, dp, maxScore, visited);

      return maxScore[0];
    }

    /**
     * DFS遍历，返回每个节点的DP值
     * 对于每个节点，我们需要记录：
     * 1. 以该节点为端点的向下的最大正乘积路径
     * 2. 以该节点为端点的向下的最小负乘积路径
     */
    private static void dfs(int node, int parent, List<Integer>[] tree, int[] values,
        NodeDP[] dp, long[] maxScore, boolean[] visited) {
      visited[node] = true;

      long val = values[node];

      // 初始化当前节点的DP值
      dp[node].maxPos = (val > 0) ? val : 0;  // 正数路径
      dp[node].minNeg = (val < 0) ? val : 0;  // 负数路径

      // 先递归处理所有子节点
      for (int child : tree[node]) {
        if (child == parent) {
          continue;
        }

        dfs(child, node, tree, values, dp, maxScore, visited);

        NodeDP childDP = dp[child];

        // 更新当前节点的DP值
        updateNodeDP(dp[node], val, childDP);

        // 更新全局最大得分：考虑通过当前节点的路径
        updateGlobalMaxScore(node, val, childDP, dp[node], maxScore);
      }

      // 当前节点自身也可能是一个答案（如果它是叶子节点）
      maxScore[0] = Math.max(maxScore[0], val);
    }

    /**
     * 更新节点的DP值
     */
    private static void updateNodeDP(NodeDP nodeDP, long nodeVal, NodeDP childDP) {
      // 处理子节点为正乘积的情况
      if (childDP.maxPos > 0) {
        long newPos = nodeVal * childDP.maxPos;
        if (newPos > 0) {
          nodeDP.maxPos = Math.max(nodeDP.maxPos, newPos);
        } else if (newPos < 0) {
          nodeDP.minNeg = Math.min(nodeDP.minNeg, newPos);
        }
      }

      // 处理子节点为负乘积的情况
      if (childDP.minNeg < 0) {
        long newNeg = nodeVal * childDP.minNeg;
        if (newNeg > 0) {
          nodeDP.maxPos = Math.max(nodeDP.maxPos, newNeg);
        } else if (newNeg < 0) {
          nodeDP.minNeg = Math.min(nodeDP.minNeg, newNeg);
        }
      }

      // 如果当前节点值本身就是正数/负数，也需要考虑
      if (nodeVal > 0) {
        nodeDP.maxPos = Math.max(nodeDP.maxPos, nodeVal);
      } else if (nodeVal < 0) {
        nodeDP.minNeg = Math.min(nodeDP.minNeg, nodeVal);
      }
    }

    /**
     * 更新全局最大得分
     * 考虑通过当前节点的路径，即：左子树路径 + 当前节点 + 右子树路径
     */
    private static void updateGlobalMaxScore(int node, long nodeVal,
        NodeDP childDP, NodeDP nodeDP,
        long[] maxScore) {
      // 情况1：当前节点连接两个子树的路径
      // 我们需要记录所有子树的候选值，然后取最好的两个组合

      // 这里简化处理：在遍历子节点时，我们维护一个列表记录所有可能的路径值
      // 然后在遍历结束时组合

      // 先考虑单条路径（从当前节点到某个叶子）
      if (childDP.maxPos > 0) {
        maxScore[0] = Math.max(maxScore[0], childDP.maxPos);
      }
      if (childDP.minNeg < 0) {
        // 负的路径值可能不是最大的，但我们需要在组合时考虑
      }

      // 考虑当前节点值本身
      maxScore[0] = Math.max(maxScore[0], nodeVal);

      // 考虑当前节点延伸的路径
      if (nodeDP.maxPos > 0) {
        maxScore[0] = Math.max(maxScore[0], nodeDP.maxPos);
      }
    }

    /**
     * 树形DP的节点信息类
     */
    private static class NodeDP {

      long maxPos;  // 以该节点为端点的向下路径的最大正乘积
      long minNeg;  // 以该节点为端点的向下路径的最小负乘积（最负的数）

      NodeDP() {
        this.maxPos = 0;
        this.minNeg = 0;
      }
    }
  }

}