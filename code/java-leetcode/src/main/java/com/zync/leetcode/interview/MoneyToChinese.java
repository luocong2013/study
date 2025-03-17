package com.zync.leetcode.interview;

/**
 * @author admin
 * @version 1.0
 * @description 金额转中文，招银网络科技笔试题（未通过）
 * @since 2025/3/17 18:31
 **/
public class MoneyToChinese {

    public static void main(String[] args) {
        System.out.println(toChinese(12.04f));
    }

    public static String toChinese(float num) {
        String[] numbers = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

        // 8520.2  852020
        StringBuilder builder = new StringBuilder();
        boolean b = num == (int) num;
        // 处理小数
        if (!b) {
            // 小数
            int m1 = ((int) (num * 100)) % 100;
            int mod1 = 0;
            int i1 = 1;
            do {
                mod1 = m1 % 10;
                m1 = m1 / 10;
                String unit1 = i1 == 1 ? "分" : "角";
                if (mod1 != 0) {
                    builder.insert(0, numbers[mod1] + unit1);
                }
                i1++;
            } while (m1 != 0);
            if (((int) (num * 100)) % 100 < 10) {
                builder.insert(0, "零");
            }
        }

        builder.insert(0, "元");

        // 处理整数
        int m2 = (int) num;
        int mod2 = 0;
        int i2 = 1;
        boolean hasZero = false;
        boolean onlyOneZero = false;
        do {
            mod2 = m2 % 10;
            m2 = m2 / 10;
            String unit2 = "";
            switch (i2) {
                case 2:
                    unit2 = "拾";
                    break;
                case 3:
                    unit2 = "佰";
                    break;
                case 4:
                    unit2 = "仟";
                    break;
                case 5:
                    unit2 = "万";
                    break;
                default:
                    unit2 = "";
            }

            if (mod2 == 0) {
                if (hasZero && !onlyOneZero) {
                    builder.insert(0, numbers[mod2]);
                    onlyOneZero = true;
                }
            } else {
                builder.insert(0, numbers[mod2] + unit2);
                if (i2 == 1) {
                    hasZero = true;
                }
            }
            i2++;
        } while (m2 != 0);

        return builder.toString();
    }
}
