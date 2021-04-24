package com.zync.leetcode.bytedance;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 雀魂启动
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] state = new int[9], temp = new int[9];
        for (int i = 0; i < 13; i++) {
            int num = scanner.nextInt();
            state[num - 1]++;
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (state[i] < 4) {
                System.arraycopy(state, 0, temp, 0, 9);
                temp[i]++;
                if (run(temp, 14, false)) {
                    res.add(i + 1);
                }
            }
        }

        if (res.isEmpty()) {
            System.out.println(0);
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append(res.get(0));
            for (int i = 1; i < res.size(); i++) {
                builder.append(" ");
                builder.append(res.get(i));
            }
            System.out.println(builder.toString());
        }
    }


    private static boolean run(int[] temp, int count, boolean hasRun) {
        if (count == 0) {
            return true;
        }
        if (!hasRun) {
            for (int i = 0; i < 9; i++) {
                if (temp[i] >= 2) {
                    temp[i] -= 2;
                    if (run(temp, count - 2, true)) {
                        return true;
                    }
                    temp[i] += 2;
                }
            }
            return false;
        } else {
            for (int i = 0; i < 9; i++) {
                if (temp[i] > 0) {
                    if (temp[i] >= 3) {
                        temp[i] -= 3;
                        if (run(temp, count - 3, true)) {
                            return true;
                        }
                        temp[i] += 3;
                    }
                    if (i + 2 < 9 && temp[i + 1] > 0 && temp[i + 2] > 0) {
                        temp[i]--;
                        temp[i + 1]--;
                        temp[i + 2]--;
                        if (run(temp, count - 3, true)) {
                            return true;
                        }
                        temp[i]++;
                        temp[i + 1]++;
                        temp[i + 2]++;
                    }
                }
            }
            return false;
        }
    }
}
