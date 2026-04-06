package cn.maiaimei.datastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * AdjacencyListGraph 的完整测试用例
 * 展示 JUnit 5 的各种特性
 */
@DisplayName("邻接表图测试")
public class AdjacencyListGraphTest {

  private AdjacencyListGraph graph;

  // ==================== 生命周期方法 ====================

  @BeforeEach
  void setUp() {
    // 每个测试方法前执行，创建新的图实例
    graph = new AdjacencyListGraph();
  }

  @AfterEach
  void tearDown() {
    // 每个测试方法后执行，清理资源
    graph = null;
  }

  // ==================== 基础功能测试 ====================

  @Test
  @DisplayName("测试添加顶点")
  void testAddVertex() {
    // 初始状态
    assertEquals(0, graph.getVertexCount(), "新图应该没有顶点");

    // 添加单个顶点
    graph.addVertex(1);
    assertEquals(1, graph.getVertexCount());
    assertTrue(graph.hasVertex(1));

    // 添加重复顶点（应该被忽略）
    graph.addVertex(1);
    assertEquals(1, graph.getVertexCount(), "重复添加不应增加顶点数");

    // 添加多个顶点
    graph.addVertex(2);
    graph.addVertex(3);
    assertEquals(3, graph.getVertexCount());
  }

  @Test
  @DisplayName("测试添加边 - 基础场景")
  void testAddEdgeBasic() {
    // 添加边时自动创建顶点
    graph.addEdge(1, 2);

    assertEquals(2, graph.getVertexCount());
    assertEquals(1, graph.getEdgeCount());
    assertTrue(graph.hasEdge(1, 2));
    assertTrue(graph.hasEdge(2, 1), "无向图应该双向连通");

    // 添加多条边
    graph.addEdge(2, 3);
    assertEquals(2, graph.getEdgeCount());
  }

  @Test
  @DisplayName("测试添加边 - 自环边")
  void testAddEdgeSelfLoop() {
    graph.addEdge(1, 1);

    assertEquals(1, graph.getVertexCount());
    assertEquals(1, graph.getEdgeCount());
    assertTrue(graph.hasEdge(1, 1));

    // 检查邻居列表包含自己
    List<Integer> neighbors = graph.getNeighbors(1);
    assertEquals(1, neighbors.size());
    assertEquals(1, neighbors.get(0));
  }

  @Test
  @DisplayName("测试添加边 - 重复边")
  void testAddEdgeDuplicate() {
    graph.addEdge(1, 2);
    graph.addEdge(1, 2); // 重复添加

    // 无向图允许平行边（多重边）
    assertEquals(2, graph.getEdgeCount());

    List<Integer> neighbors = graph.getNeighbors(1);
    assertEquals(2, neighbors.size(), "应该有两个邻居（允许重复）");
  }

  // ==================== 删除操作测试 ====================

  @Test
  @DisplayName("测试删除边")
  void testRemoveEdge() {
    graph.addEdge(1, 2);
    graph.addEdge(2, 3);

    // 成功删除
    assertTrue(graph.removeEdge(1, 2));
    assertFalse(graph.hasEdge(1, 2));
    assertFalse(graph.hasEdge(2, 1), "无向图应该双向删除");
    assertEquals(1, graph.getEdgeCount());

    // 删除不存在的边
    assertFalse(graph.removeEdge(1, 2), "重复删除应该返回false");
    assertFalse(graph.removeEdge(99, 100), "删除不存在的顶点之间的边");
  }

  @Test
  @DisplayName("测试删除顶点")
  void testRemoveVertex() {
    // 构建图：1-2-3
    graph.addEdge(1, 2);
    graph.addEdge(2, 3);

    // 删除中间顶点
    assertTrue(graph.removeVertex(2));
    assertEquals(2, graph.getVertexCount());
    assertFalse(graph.hasVertex(2));
    assertFalse(graph.hasEdge(1, 2));
    assertFalse(graph.hasEdge(2, 3));

    // 检查剩余顶点的邻居列表已更新
    assertTrue(graph.getNeighbors(1).isEmpty());
    assertTrue(graph.getNeighbors(3).isEmpty());

    // 删除不存在的顶点
    assertFalse(graph.removeVertex(99));
  }

  // ==================== 查询操作测试 ====================

  @Test
  @DisplayName("测试获取邻居列表")
  void testGetNeighbors() {
    // 空图
    assertTrue(graph.getNeighbors(1).isEmpty());

    // 添加边后
    graph.addEdge(1, 2);
    graph.addEdge(1, 3);
    graph.addEdge(1, 4);

    List<Integer> neighbors = graph.getNeighbors(1);
    assertEquals(3, neighbors.size());
    assertTrue(neighbors.contains(2));
    assertTrue(neighbors.contains(3));
    assertTrue(neighbors.contains(4));
  }

  @ParameterizedTest
  @CsvSource({
      "1, 2, true",   // 存在的边
      "2, 1, true",   // 反向边（无向图）
      "1, 3, false",  // 不存在的边
      "99, 1, false"  // 不存在的顶点
  })
  @DisplayName("参数化测试：判断边是否存在")
  void testHasEdge(int source, int dest, boolean expected) {
    graph.addEdge(1, 2);
    assertEquals(expected, graph.hasEdge(source, dest));
  }

  // ==================== 图遍历测试 ====================

  @Test
  @DisplayName("测试 BFS 遍历")
  void testBFS() {
    /*
     * 构建图：
     *     1
     *    / \
     *   2   3
     *   |   |
     *   4   5
     */
    graph.addEdge(1, 2);
    graph.addEdge(1, 3);
    graph.addEdge(2, 4);
    graph.addEdge(3, 5);

    List<Integer> bfsResult = graph.bfs(1);

    assertEquals(5, bfsResult.size());
    assertEquals(1, bfsResult.get(0), "BFS应该从起点开始");

    // 第二层应该是 2 和 3（顺序可能不同）
    List<Integer> level2 = bfsResult.subList(1, 3);
    assertTrue(level2.contains(2));
    assertTrue(level2.contains(3));
  }

  @Test
  @DisplayName("测试 DFS 遍历")
  void testDFS() {
    /*
     * 构建图：
     *   1 - 2 - 3
     *   |
     *   4
     */
    graph.addEdge(1, 2);
    graph.addEdge(2, 3);
    graph.addEdge(1, 4);

    List<Integer> dfsResult = graph.dfs(1);

    assertEquals(4, dfsResult.size());
    assertEquals(1, dfsResult.get(0), "DFS应该从起点开始");

    // DFS 应该深入遍历，1->2->3 或 1->4
    assertTrue(dfsResult.containsAll(List.of(1, 2, 3, 4)));
  }

  @Test
  @DisplayName("测试遍历 - 不连通图")
  void testTraversalDisconnected() {
    graph.addEdge(1, 2);
    graph.addEdge(3, 4); // 独立的连通分量

    List<Integer> bfsResult = graph.bfs(1);
    assertEquals(2, bfsResult.size());
    assertTrue(bfsResult.contains(1));
    assertTrue(bfsResult.contains(2));

    // 从1无法到达3和4
    assertFalse(bfsResult.contains(3));
  }

  @Test
  @DisplayName("测试遍历 - 起点不存在")
  void testTraversalNonExistentStart() {
    graph.addEdge(1, 2);

    List<Integer> bfsResult = graph.bfs(99);
    assertTrue(bfsResult.isEmpty());

    List<Integer> dfsResult = graph.dfs(99);
    assertTrue(dfsResult.isEmpty());
  }

  // ==================== 连通性测试 ====================

  @Test
  @DisplayName("测试顶点连通性")
  void testIsConnected() {
    graph.addEdge(1, 2);
    graph.addEdge(2, 3);

    assertTrue(graph.isConnected(1, 3), "1应该与3连通");
    assertTrue(graph.isConnected(3, 1), "连通性应该是双向的");

    graph.addVertex(4); // 孤立顶点
    assertFalse(graph.isConnected(1, 4), "1不应该与4连通");

    assertFalse(graph.isConnected(1, 99), "不存在的顶点");
  }

  // ==================== 有向图测试 ====================

  @Test
  @DisplayName("测试有向图")
  void testDirectedGraph() {
    AdjacencyListGraph directedGraph = new AdjacencyListGraph(true);

    directedGraph.addEdge(1, 2);

    assertTrue(directedGraph.hasEdge(1, 2));
    assertFalse(directedGraph.hasEdge(2, 1), "有向图不应该有反向边");
    assertEquals(1, directedGraph.getEdgeCount());

    // 添加反向边
    directedGraph.addEdge(2, 1);
    assertEquals(2, directedGraph.getEdgeCount());
  }

  @Test
  @DisplayName("测试有向图删除顶点")
  void testDirectedGraphRemoveVertex() {
    AdjacencyListGraph directedGraph = new AdjacencyListGraph(true);
    directedGraph.addEdge(1, 2);
    directedGraph.addEdge(3, 2); // 3指向2

    directedGraph.removeVertex(2);

    // 检查1和3的出边列表中不再包含2
    assertFalse(directedGraph.hasEdge(1, 2));
    assertFalse(directedGraph.hasEdge(3, 2));
  }

  // ==================== 边界条件测试 ====================

  @Test
  @DisplayName("测试空图操作")
  void testEmptyGraph() {
    assertEquals(0, graph.getVertexCount());
    assertEquals(0, graph.getEdgeCount());
    assertFalse(graph.hasVertex(1));
    assertFalse(graph.hasEdge(1, 2));
    assertTrue(graph.getNeighbors(1).isEmpty());
  }

  @Test
  @DisplayName("测试单顶点图")
  void testSingleVertex() {
    graph.addVertex(1);

    assertEquals(1, graph.getVertexCount());
    assertEquals(0, graph.getEdgeCount());
    assertEquals(0, graph.getDegree(1));

    List<Integer> bfs = graph.bfs(1);
    assertEquals(1, bfs.size());
    assertEquals(1, bfs.get(0));
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 1, 100, -1, Integer.MAX_VALUE})
  @DisplayName("测试各种顶点值")
  void testVariousVertexValues(int vertex) {
    graph.addVertex(vertex);
    assertTrue(graph.hasVertex(vertex));
  }

  // ==================== 复杂场景测试 ====================

  @Test
  @DisplayName("测试复杂图结构")
  void testComplexGraph() {
    // 构建一个包含环和多个连通分量的图
    // 分量1: 1-2-3-1（环）
    // 分量2: 4-5

    graph.addEdge(1, 2);
    graph.addEdge(2, 3);
    graph.addEdge(3, 1); // 形成环
    graph.addEdge(4, 5);

    assertEquals(5, graph.getVertexCount());
    assertEquals(4, graph.getEdgeCount());

    // 检查环
    assertTrue(graph.hasEdge(1, 3));
    assertTrue(graph.hasEdge(3, 1));

    // 检查连通性
    assertTrue(graph.isConnected(1, 2));
    assertFalse(graph.isConnected(1, 4));
  }

  @Test
  @DisplayName("测试度计算")
  void testDegree() {
    // 星型结构：1连接2,3,4,5
    graph.addEdge(1, 2);
    graph.addEdge(1, 3);
    graph.addEdge(1, 4);
    graph.addEdge(1, 5);

    assertEquals(4, graph.getDegree(1), "中心顶点度为4");
    assertEquals(1, graph.getDegree(2), "叶子顶点度为1");
    assertEquals(0, graph.getDegree(99), "不存在的顶点度为0");
  }

  // ==================== 异常和并发测试 ====================

  @Test
  @DisplayName("测试修改返回的邻居列表不影响原图")
  void testNeighborListIsolation() {
    graph.addEdge(1, 2);
    graph.addEdge(1, 3);

    List<Integer> neighbors = graph.getNeighbors(1);
    neighbors.clear(); // 尝试清空返回的列表

    // 原图应该不受影响
    assertEquals(2, graph.getNeighbors(1).size());
  }
}