package com.zync.pattern.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 菜单
 * @date 2019/5/11 15:18
 */
public class Meal {

    private List<Item> items = new ArrayList<>();

    /**
     * 添加食品
     * @param item
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * 计算总共花费
     * @return
     */
    public float getCost() {
        float cost = 0;
        for (Item item : items) {
            cost += item.price();
        }
        return cost;
    }

    /**
     * 显示食品项
     */
    public void showItems() {
        for (Item item : items) {
            System.out.print("Item : " + item.name());
            System.out.print(", Packing : " + item.packing().pack());
            System.out.println(", Price : " + item.price());
        }
    }
}
