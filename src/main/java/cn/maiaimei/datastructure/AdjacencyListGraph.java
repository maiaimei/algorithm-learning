package cn.maiaimei.datastructure;

import java.util.*;

/**
 * 邻接表实现的无向图
 * 适合初学者理解图的基本操作
 */
public class AdjacencyListGraph {

  // 使用 Map 存储邻接表：顶点 -> 邻居列表
  private final Map<Integer, List<Integer>> adjacencyList;
  private final boolean directed; // 是否为有向图

  public AdjacencyListGraph() {
    this(false); // 默认无向图
  }

  public AdjacencyListGraph(boolean directed) {
    this.adjacencyList = new HashMap<>();
    this.directed = directed;
  }

  /**
   * 添加顶点
   */
  public void addVertex(int vertex) {
    adjacencyList.putIfAbsent(vertex, new ArrayList<>());
  }

  /**
   * 添加边
   */
  public void addEdge(int source, int destination) {
    // 自动添加不存在的顶点
    addVertex(source);
    addVertex(destination);

    // 检查是否已存在该边，避免重复添加
    if (adjacencyList.get(source).contains(destination)) {
      return;
    }

    // 添加边
    adjacencyList.get(source).add(destination);

    // 无向图需要双向添加
    if (!directed) {
      adjacencyList.get(destination).add(source);
    }
  }

  /**
   * 删除顶点（同时删除所有相关边）
   */
  public boolean removeVertex(int vertex) {
    if (!hasVertex(vertex)) {
      return false;
    }

    // 如果是无向图，需要从所有邻居的列表中删除该顶点
    if (!directed) {
      for (int neighbor : adjacencyList.get(vertex)) {
        adjacencyList.get(neighbor).remove(Integer.valueOf(vertex));
      }
    } else {
      // 有向图：需要遍历所有顶点，删除指向该顶点的边
      for (List<Integer> neighbors : adjacencyList.values()) {
        neighbors.remove(Integer.valueOf(vertex));
      }
    }

    adjacencyList.remove(vertex);
    return true;
  }

  /**
   * 删除边
   */
  public boolean removeEdge(int source, int destination) {
    if (!hasVertex(source) || !hasVertex(destination)) {
      return false;
    }

    boolean removed = adjacencyList.get(source).remove(Integer.valueOf(destination));

    if (!directed && removed) {
      adjacencyList.get(destination).remove(Integer.valueOf(source));
    }

    return removed;
  }

  /**
   * 判断是否有某个顶点
   */
  public boolean hasVertex(int vertex) {
    return adjacencyList.containsKey(vertex);
  }


  /**
   * 判断是否有某条边
   */
  public boolean hasEdge(int source, int destination) {
    return hasVertex(source) && adjacencyList.get(source).contains(destination);
  }

  /**
   * 获取顶点数量
   */
  public int getVertexCount() {
    return adjacencyList.size();
  }

  /**
   * 获取边的数量
   */
  public int getEdgeCount() {
    int count = 0;
    for (List<Integer> neighbors : adjacencyList.values()) {
      count += neighbors.size();
    }
    // 无向图每条边被计算两次，需要除以2
    return directed ? count : count / 2;
  }

  /**
   * 获取邻居列表
   */
  public List<Integer> getNeighbors(int vertex) {
    return adjacencyList.getOrDefault(vertex, new ArrayList<>());
  }

  /**
   * 获取度（无向图）或出度（有向图）
   */
  public int getDegree(int vertex) {
    return getNeighbors(vertex).size();
  }

  /**
   * 判断两个顶点是否连通
   */
  public boolean isConnected(int source, int destination) {
    if (!hasVertex(source) || !hasVertex(destination)) {
      return false;
    }
    return bfs(source).contains(destination);
  }

  /**
   * BFS 遍历
   */
  public List<Integer> bfs(int startVertex) {
    List<Integer> result = new ArrayList<>();
    if (!hasVertex(startVertex)) {
      return result;
    }

    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();

    queue.offer(startVertex);
    visited.add(startVertex);

    while (!queue.isEmpty()) {
      int current = queue.poll();
      result.add(current);

      for (int neighbor : adjacencyList.get(current)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          queue.offer(neighbor);
        }
      }
    }

    return result;
  }

  /**
   * DFS 遍历
   */
  public List<Integer> dfs(int startVertex) {
    List<Integer> result = new ArrayList<>();
    if (!hasVertex(startVertex)) {
      return result;
    }

    Set<Integer> visited = new HashSet<>();
    dfsHelper(startVertex, visited, result);
    return result;
  }

  private void dfsHelper(int vertex, Set<Integer> visited, List<Integer> result) {
    visited.add(vertex);
    result.add(vertex);

    for (int neighbor : adjacencyList.get(vertex)) {
      if (!visited.contains(neighbor)) {
        dfsHelper(neighbor, visited, result);
      }
    }
  }

}