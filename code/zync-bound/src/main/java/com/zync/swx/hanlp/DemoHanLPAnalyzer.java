package com.zync.swx.hanlp;

/**
 * Created by LC on 2017-6-2.
 */
public class DemoHanLPAnalyzer {
    public static void main(String[] args) {
        String text = "中华人民共和国很辽阔";
        for (int i = 0; i < text.length(); i++) {
            System.out.println(text.charAt(i) + "" + i + " ");
        }
        System.out.println();
    }
}
