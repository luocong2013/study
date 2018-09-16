package com.ccyw.activemq.jms.topic;

import com.ccyw.activemq.jms.Constans;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author luoc
 * @version V1.0.0
 * @description jms主题模式 - 消息消费者
 * @date 2018/6/23 19:34
 */
public class TopicConsumer {

    public static void main(String[] args) throws JMSException{
        // 1.创建一个链接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constans.USERNAME, Constans.PASSWORD, Constans.URL);
        // 2.从工厂中创建一个链接
        Connection connection = connectionFactory.createConnection();
        // 3.开启链接
        connection.start();
        // 4.创建会话，第一个参数表示是否在事务中处理，由于是演示代码所以不使用事务false，第二个参数是连接应答模式，Session.AUTO_ACKNOWLEDGE表示自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5.创建一个目标 主题
        Destination destination = session.createTopic(Constans.TOPIC_NAME);
        // 6.创建一个消费者，并指定目标
        MessageConsumer consumer = session.createConsumer(destination);

        // 7.创建一个监听器，接收消息
        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("接收消息：" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        // 注意，由于消息接收是异步的，所以不能关闭connection
    }
}
