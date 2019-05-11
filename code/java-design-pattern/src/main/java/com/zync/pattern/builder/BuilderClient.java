package com.zync.pattern.builder;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 建造者模式-测试方法
 * @date 2019/5/11 15:28
 */
public class BuilderClient {

    public static void main(String[] args){
        MealBuilder builder = new MealBuilder();

        Meal vegMeal = builder.prepareVegMeal();
        System.out.println("Veg Meal");
        vegMeal.showItems();
        System.out.println("Total Cost : " + vegMeal.getCost());

        Meal nonVegMeal = builder.prepareNonVegMeal();
        System.out.println("\n\nNon-Veg Meal");
        nonVegMeal.showItems();
        System.out.println("Total Cost : " + nonVegMeal.getCost());
    }
}
