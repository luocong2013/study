//给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。 
//
// 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。 
//
// 你可以假设除了整数 0 之外，这个整数不会以零开头。 
//
// 
//
// 示例 1： 
//
// 
//输入：digits = [1,2,3]
//输出：[1,2,4]
//解释：输入数组表示数字 123。
// 
//
// 示例 2： 
//
// 
//输入：digits = [4,3,2,1]
//输出：[4,3,2,2]
//解释：输入数组表示数字 4321。
// 
//
// 示例 3： 
//
// 
//输入：digits = [9]
//输出：[1,0]
//解释：输入数组表示数字 9。
//加 1 得到了 9 + 1 = 10。
//因此，结果应该是 [1,0]。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= digits.length <= 100 
// 0 <= digits[i] <= 9 
// 
//
// Related Topics 数组 数学 👍 1478 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] plusOne(int[] digits) {
        int mod = 0, k = digits.length;
        int[] temp = new int[k + 1];
        for (int i = digits.length - 1; i >= 0; i--) {
            int sum = digits[i] + mod;
            if (i == digits.length - 1) {
                sum++;
            }
            if (sum >= 10) {
                mod = 1;
                temp[k] = sum % 10;
            } else {
                mod = 0;
                temp[k] = sum;
            }
            k--;
        }
        if (mod == 1) {
            temp[0] = 1;
            return temp;
        }
        int[] result = new int[digits.length];
        for (int i = 0; i < digits.length; i++) {
            result[i] = temp[i+1];
        }
        return result;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
