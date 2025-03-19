//给你两个二进制字符串 a 和 b ，以二进制字符串的形式返回它们的和。 
//
// 
//
// 示例 1： 
//
// 
//输入:a = "11", b = "1"
//输出："100" 
//
// 示例 2： 
//
// 
//输入：a = "1010", b = "1011"
//输出："10101" 
//
// 
//
// 提示： 
//
// 
// 1 <= a.length, b.length <= 10⁴ 
// a 和 b 仅由字符 '0' 或 '1' 组成 
// 字符串如果不是 "0" ，就不含前导零 
// 
//
// Related Topics 位运算 数学 字符串 模拟 👍 1282 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String addBinary(String a, String b) {
        StringBuilder builder = new StringBuilder();
        int mod = 0;
        for (int i = a.length() - 1, k = b.length() - 1; i >= 0 || k >= 0; i--, k--) {
            int n1 = 0, n2 = 0;
            if (i >= 0) {
                n1 = a.charAt(i) - '0';
            }
            if (k >= 0) {
                n2 = b.charAt(k) - '0';
            }
            int n = n1 + n2 + mod;
            builder.insert(0, n & 1);
            mod = n > 1 ? 1 : 0;
        }
        if (mod == 1) {
            builder.insert(0, mod);
        }
        return builder.toString();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
