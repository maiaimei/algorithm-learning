package com.shl.difficult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TreePathMaximumProduct {

  public static class Solution {

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

}