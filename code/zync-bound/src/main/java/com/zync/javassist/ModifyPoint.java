package com.zync.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author luocong
 * @version V1.0.0
 * @description
 * @date 2020/6/10 16:00
 */
public class ModifyPoint {

    public void modifyMethod() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ct = pool.getCtClass("com.zync.javassist.Point");
        CtMethod m = ct.getDeclaredMethod("move");
        m.insertBefore("{System.out.print(\"dx: \" + $1);System.out.println(\" dy: \" + $2);}");
        m.insertAfter("{System.out.println($0.x);System.out.println($0.y);}");

        ct.writeFile();

        Class<?> clazz = ct.toClass();
        Method move = clazz.getMethod("move", int.class, int.class);
        Constructor<?> con = clazz.getConstructor(int.class, int.class);
        move.invoke(con.newInstance(1, 2), 1, 2);
    }



    public static void main(String[] args) throws Exception {
        ModifyPoint point = new ModifyPoint();
        point.modifyMethod();
    }
}
