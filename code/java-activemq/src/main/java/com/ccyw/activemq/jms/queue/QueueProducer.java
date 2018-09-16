package com.ccyw.activemq.jms.queue;

import com.ccyw.activemq.jms.Constans;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author luoc
 * @version V1.0.0
 * @description jms队列模式 - 消息生产者
 * @date 2018/6/23 18:59
 */
public class QueueProducer {

    public static void main(String[] args) throws JMSException{
        // 1.创建一个链接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constans.USERNAME, Constans.PASSWORD, Constans.URL);
        // 2.从工厂中创建一个链接
        Connection connection = connectionFactory.createConnection();
        // 3.开启链接
        connection.start();
        // 4.创建会话，第一个参数表示是否在事务中处理，由于是演示代码所以不使用事务false，第二个参数是连接应答模式，Session.AUTO_ACKNOWLEDGE表示自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5.创建一个目标 队列
        Destination destination = session.createQueue(Constans.QUEUE_NAME);
        // 6.创建一个生产者，并指定目标
        MessageProducer producer = session.createProducer(destination);

        for (int i = 1; i <= 100; i++) {
            // 7.创建消息
            TextMessage textMessage = session.createTextMessage("test" + i);
            // 8.发送消息
            producer.send(textMessage);
            System.out.println("发送消息：" + textMessage.getText());
        }
        // 9.关闭链接
        connection.close();
    }
}
