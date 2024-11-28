//给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。 
//
// 有效字符串需满足： 
//
// 
// 左括号必须用相同类型的右括号闭合。 
// 左括号必须以正确的顺序闭合。 
// 每个右括号都有一个对应的相同类型的左括号。 
// 
//
// 
//
// 示例 1： 
//
// 
// 输入：s = "()" 
// 
//
// 输出：true 
//
// 示例 2： 
//
// 
// 输入：s = "()[]{}" 
// 
//
// 输出：true 
//
// 示例 3： 
//
// 
// 输入：s = "(]" 
// 
//
// 输出：false 
//
// 示例 4： 
//
// 
// 输入：s = "([])" 
// 
//
// 输出：true 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 10⁴ 
// s 仅由括号 '()[]{}' 组成 
// 
//
// Related Topics 栈 字符串 👍 4592 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>(16);
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        char[] chs = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (Character ch : chs) {
            if (!map.containsKey(ch)) {
                stack.push(ch);
            } else {
                Character item = map.get(ch);
                if (stack.isEmpty() || !item.equals(stack.pop())) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
//leetcode submit region end(Prohibit modification and deletion)
