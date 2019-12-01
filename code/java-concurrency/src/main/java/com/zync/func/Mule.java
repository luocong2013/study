package com.zync.func;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption
 * @date 2019/11/26 21:42
 */
public class Mule implements IHorse, IDonkey, IAnimal {

    /**
     * 该方法需要重写，不然找不到，因为IHorse和IDonkey都用这个方法。否则编译器会报错
     */
    @Override
    public void run() {
        IHorse.super.run();
    }

    @Override
    public void eat() {
        System.out.println("Mule eat");
    }

    public static void main(String[] args){
        Mule mule = new Mule();
        mule.run();
        mule.breath();
    }
}
