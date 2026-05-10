package cn.leetcode.simple;

import java.util.Scanner;

/**
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * <p>
 * 示例 1：
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 * <p>
 * 示例 2：
 * 输入：l1 = [], l2 = []
 * 输出：[]
 * <p>
 * 示例 3：
 * 输入：l1 = [], l2 = [0]
 * 输出：[0]
 * <p>
 * 提示：
 * 两个链表的节点数目范围是 [0, 50]
 * -100 <= Node.val <= 100
 * l1 和 l2 均按 非递减顺序 排列
 */
public class LeetCode21MergeTwoSortedLists {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 读取第一个链表的节点值
        System.out.println("请输入第一个链表的节点值（用空格分隔，回车结束）：");
        String line1 = scanner.nextLine();
        ListNode list1 = buildList(line1);

        // 读取第二个链表的节点值
        System.out.println("请输入第二个链表的节点值（用空格分隔，回车结束）：");
        String line2 = scanner.nextLine();
        ListNode list2 = buildList(line2);

        // 打印原始链表
        System.out.print("链表1: ");
        printList(list1);
        System.out.print("链表2: ");
        printList(list2);

        // 合并链表
        Solution solution = new LeetCode21MergeTwoSortedLists().new Solution();
        ListNode mergedList = solution.mergeTwoLists(list1, list2);

        // 打印合并后的链表
        System.out.print("合并后: ");
        printList(mergedList);

        scanner.close();
    }

    /**
     * 从字符串构建链表
     *
     * @param input 以空格分隔的数字字符串
     * @return 构建的链表头节点
     */
    private static ListNode buildList(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }

        String[] values = input.trim().split("\\s+");
        if (values.length == 0) {
            return null;
        }

        // 创建虚拟头节点
        ListNode dummy = new LeetCode21MergeTwoSortedLists().new ListNode(0);
        ListNode current = dummy;

        for (String value : values) {
            try {
                int val = Integer.parseInt(value);
                current.next = new LeetCode21MergeTwoSortedLists().new ListNode(val);
                current = current.next;
            } catch (NumberFormatException e) {
                System.err.println("无效的数字格式: " + value);
            }
        }

        return dummy.next;
    }

    /**
     * 打印链表
     *
     * @param head 链表头节点
     */
    private static void printList(ListNode head) {
        ListNode current = head;
        System.out.print("[");
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(", ");
            }
            current = current.next;
        }
        System.out.println("]");
    }

    class Solution {
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            ListNode dummy = new ListNode(0);
            ListNode cur = dummy;
            while (list1 != null && list2 != null) {
                if (list1.val < list2.val) {
                    cur.next = list1;
                    list1 = list1.next;
                } else {
                    cur.next = list2;
                    list2 = list2.next;
                }
                cur = cur.next;
            }
            cur.next = list1 != null ? list1 : list2;
            return dummy.next;
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
