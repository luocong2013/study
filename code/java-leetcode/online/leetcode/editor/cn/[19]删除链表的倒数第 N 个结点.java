//给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。 
//
// 
//
// 示例 1： 
// 
// 
//输入：head = [1,2,3,4,5], n = 2
//输出：[1,2,3,5]
// 
//
// 示例 2： 
//
// 
//输入：head = [1], n = 1
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：head = [1,2], n = 1
//输出：[1]
// 
//
// 
//
// 提示： 
//
// 
// 链表中结点的数目为 sz 
// 1 <= sz <= 30 
// 0 <= Node.val <= 100 
// 1 <= n <= sz 
// 
//
// 
//
// 进阶：你能尝试使用一趟扫描实现吗？ 
//
// Related Topics 链表 双指针 👍 2981 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return head;
        }

        int sz = 0;
        ListNode next = head;
        while (next != null) {
            sz++;
            next = next.next;
        }

        int index = sz - n;
        int i = 0;
        ListNode result = new ListNode(0);
        ListNode resultNext = result;
        do {
            if (index != i) {
                resultNext.next = new ListNode(head.val);
                resultNext = resultNext.next;
            }
            i++;
        } while ((head = head.next) != null);

        return result.next;

        // 一次遍历方式
        //int count = 0;
        //ListNode cur = head;
        //ListNode tail = head;
        //while (cur != null) {
        //    count++;
        //    if (count > n + 1) {
        //        tail = tail.next;
        //    }
        //    cur = cur.next;
        //}
        //
        //if (count == n) {
        //    head = head.next;
        //} else {
        //    tail.next = tail.next.next;
        //}
        //
        //return head;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
