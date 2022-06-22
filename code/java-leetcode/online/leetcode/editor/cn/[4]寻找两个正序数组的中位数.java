//给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。 
//
// 算法的时间复杂度应该为 O(log (m+n)) 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums1 = [1,3], nums2 = [2]
//输出：2.00000
//解释：合并数组 = [1,2,3] ，中位数 2
// 
//
// 示例 2： 
//
// 
//输入：nums1 = [1,2], nums2 = [3,4]
//输出：2.50000
//解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
// 
//
// 
//
// 
//
// 提示： 
//
// 
// nums1.length == m 
// nums2.length == n 
// 0 <= m <= 1000 
// 0 <= n <= 1000 
// 1 <= m + n <= 2000 
// -10⁶ <= nums1[i], nums2[i] <= 10⁶ 
// 
// Related Topics 数组 二分查找 分治 👍 5504 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int a = nums1.length;
        int b = nums2.length;

        int mid = (a + b) / 2;
        int prev = 0;
        int curr = 0;

        for (int i = 0, j = 0, k = 0; i <= mid; i++) {
            prev = curr;
            if (j == a) {
                curr = nums2[k];
                k++;
                continue;
            }
            if (k == b) {
                curr = nums1[j];
                j++;
                continue;
            }
            if (nums1[j] < nums2[k]) {
                curr = nums1[j];
                j++;
            } else {
                curr = nums2[k];
                k++;
            }
        }

        boolean bool = (a + b) % 2 == 0;
        return bool ? (double)(prev + curr) / 2 : (double)curr;
    }

}
//leetcode submit region end(Prohibit modification and deletion)
