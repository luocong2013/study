package com.zync.pattern.builder;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 建造Meal
 * @date 2019/5/11 15:24
 */
public class MealBuilder {

    /**
     * 素食汉堡 + 可口可乐
     * @return
     */
    public Meal prepareVegMeal() {
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return meal;
    }

    /**
     * 鸡肉汉堡 + 百事可乐
     * @return
     */
    public Meal prepareNonVegMeal() {
        Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new Pepsi());
        return meal;
    }
}
