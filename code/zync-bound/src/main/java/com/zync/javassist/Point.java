package com.zync.javassist;

/**
 * @author luocong
 * @version V1.0.0
 * @description
 * @date 2020/6/10 16:00
 */
public class Point {

    private int x;
    private int y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
}
