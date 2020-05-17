package com.zync.leetcode.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @user luoc
 * @date 2020/5/17 12:13
 * @description
 **/
public class ValidSpeck {

    public static void main(String[] args) {
        String str = "[()][[]{}{}{}[]()]";
        System.out.println(isValid(str));
    }

    // [()]
    private static boolean isValid(String str) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<>(16);
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        char[] chs = str.toCharArray();
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
