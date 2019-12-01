package com.zync.akka;

import akka.actor.UntypedAbstractActor;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 欢迎者
 * @date 2019/12/1 22:16
 */
public class Greeter extends UntypedAbstractActor {

    public static enum Msg {
        GREET, DONE
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message == Msg.GREET) {
            System.out.println("Hello World!");
            getSender().tell(Msg.DONE, getSelf());
        } else {
            unhandled(message);
        }
    }
}
