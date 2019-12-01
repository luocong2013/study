package com.zync.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * @author luoc
 * @version V1.0.0
 * @descrption 测试类
 * @date 2019/12/1 22:23
 */
public class HelloWorldClient {

    public static void main(String[] args){
        ActorSystem system = ActorSystem.create("Hello", ConfigFactory.load("samplehello.conf"));
        ActorRef a = system.actorOf(Props.create(HelloWorld.class), "helloWorld");
        System.out.println("Hello World Actor Path: " + a.path());
    }
}
