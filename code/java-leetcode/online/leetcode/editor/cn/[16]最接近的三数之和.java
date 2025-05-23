//给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。 
//
// 返回这三个数的和。 
//
// 假定每组输入只存在恰好一个解。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [-1,2,1,-4], target = 1
//输出：2
//解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2)。
// 
//
// 示例 2： 
//
// 
//输入：nums = [0,0,0], target = 1
//输出：0
//解释：与 target 最接近的和是 0（0 + 0 + 0 = 0）。 
//
// 
//
// 提示： 
//
// 
// 3 <= nums.length <= 1000 
// -1000 <= nums[i] <= 1000 
// -10⁴ <= target <= 10⁴ 
// 
//
// Related Topics 数组 双指针 排序 👍 1672 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        int result = nums[0] + nums[1] + nums[2];
        int minAbs = Math.abs(result - target);
        int n = nums.length;

        Arrays.sort(nums);

        for (int k = 0; k < n - 2; k++) {
            int i = k + 1, j = n - 1;
            while (i < j) {
                int itemSum = nums[k] + nums[i] + nums[j];
                if (itemSum == target) {
                    return target;
                } else if (itemSum < target) {
                    int itemAbs = target - itemSum;
                    if (itemAbs < minAbs) {
                        result = itemSum;
                        minAbs = itemAbs;
                    }
                    i++;
                } else {
                    int itemAbs = itemSum - target;
                    if (itemAbs < minAbs) {
                        result = itemSum;
                        minAbs = itemAbs;
                    }
                    j--;
                }
            }
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
