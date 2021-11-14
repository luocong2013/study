package com.zync.eight.pojo;

/**
 * 苹果类
 *
 * @author luocong
 * @version v1.0
 * @date 2021/11/14 17:00
 */
public class Apple {

    /**
     * 重量
     */
    private Integer weight;

    /**
     * 颜色
     */
    private String color;

    public Apple() {
    }

    public Apple(Integer weight) {
        this.weight = weight;
    }

    public Apple(String color) {
        this.color = color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
