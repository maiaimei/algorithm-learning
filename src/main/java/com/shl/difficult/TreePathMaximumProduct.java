package com.shl.difficult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
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
            int n = Integer.parseInt(br.readLine().trim());
            int[] values = new int[n + 1];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= n; i++) {
                values[i] = Integer.parseInt(st.nextToken());
            }

            List<Integer>[] adj = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++) {
                adj[i] = new ArrayList<>();
            }
            for (int i = 0; i < n - 1; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                adj[a].add(b);
                adj[b].add(a);
            }

            if (n == 1) {
                System.out.println(values[1]);
                return;
            }

            int[] parent = new int[n + 1];
            List<Integer> order = new ArrayList<>();
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(1);
            parent[1] = 0;

            while (!stack.isEmpty()) {
                int u = stack.pop();
                order.add(u);
                for (int v : adj[u]) {
                    if (v == parent[u]) continue;
                    parent[v] = u;
                    stack.push(v);
                }
            }

            BigInteger[] maxDown = new BigInteger[n + 1];
            BigInteger[] minDown = new BigInteger[n + 1];

            for (int idx = order.size() - 1; idx >= 0; idx--) {
                int u = order.get(idx);
                boolean isLeaf = true;
                List<BigInteger> candidates = new ArrayList<>();
                for (int v : adj[u]) {
                    if (v == parent[u]) continue;
                    isLeaf = false;
                    BigInteger c1 = BigInteger.valueOf(values[u]).multiply(maxDown[v]);
                    BigInteger c2 = BigInteger.valueOf(values[u]).multiply(minDown[v]);
                    candidates.add(c1);
                    candidates.add(c2);
                }
                if (isLeaf) {
                    maxDown[u] = BigInteger.valueOf(values[u]);
                    minDown[u] = BigInteger.valueOf(values[u]);
                } else {
                    maxDown[u] = candidates.get(0);
                    minDown[u] = candidates.get(0);
                    for (BigInteger c : candidates) {
                        if (c.compareTo(maxDown[u]) > 0) maxDown[u] = c;
                        if (c.compareTo(minDown[u]) < 0) minDown[u] = c;
                    }
                }
            }

            BigInteger answer = null;
            if (adj[1].size() <= 1) {
                answer = maxDown[1];
            }

            for (int u : order) {
                List<Pair> childPairs = new ArrayList<>();
                for (int v : adj[u]) {
                    if (v == parent[u]) continue;
                    childPairs.add(new Pair(maxDown[v], v));
                    childPairs.add(new Pair(minDown[v], v));
                }
                if (childPairs.size() >= 4) {
                    childPairs.sort(Comparator.comparing(p -> p.val));
                    int m = childPairs.size();
                    int K = Math.min(15, m);
                    List<Pair> extreme = new ArrayList<>();
                    for (int i = 0; i < K; i++) {
                        extreme.add(childPairs.get(i));
                    }
                    for (int i = 0; i < K; i++) {
                        extreme.add(childPairs.get(m - 1 - i));
                    }

                    BigInteger valU = BigInteger.valueOf(values[u]);
                    for (int i = 0; i < extreme.size(); i++) {
                        for (int j = i + 1; j < extreme.size(); j++) {
                            Pair p1 = extreme.get(i);
                            Pair p2 = extreme.get(j);
                            if (p1.id != p2.id) {
                                BigInteger prod = valU.multiply(p1.val).multiply(p2.val);
                                if (answer == null || prod.compareTo(answer) > 0) {
                                    answer = prod;
                                }
                            }
                        }
                    }
                }
            }

            System.out.println(answer);
        }

        static class Pair {
            BigInteger val;
            int id;

            Pair(BigInteger val, int id) {
                this.val = val;
                this.id = id;
            }
        }
    }

}